package com.crm.auth.service;


import com.crm.auth.po.SysRole;
import com.crm.auth.po.SysUser;
import com.crm.auth.util.ShiroPasswordUtil;
import com.crm.auth.vo.MenuTree;
import com.crm.auth.vo.SysUserVO;
import com.crm.common.enums.StatusType;
import com.crm.common.page.BasePage;
import com.crm.common.page.PageInfo;
import com.crm.common.page.PageObject;
import com.mongodb.client.result.UpdateResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SysUserService {

    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private SysRoleService roleService;

    @Resource
    private SysMenuService menuService;

    @Resource
    private SessionDAO sessionDAO;

    /**
     * 保存用户
     *
     * @param user
     * @return
     */
    public SysUser saveUser(SysUser user) {
        if (!checkUser(user)) {
            return null;
        }
        if (StringUtils.isEmpty(user.getId())) {
            user.setPassword(ShiroPasswordUtil.encode(user.getPassword(), user.getUsername()));
            user.setStatus(StatusType.enable);
            user.setCreateTime(new Date());
            user.setPwdFlag("0");
            mongoTemplate.insert(user);
        } else {
            mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(user.getId())),
                    Update.update("username", user.getUsername())
                            .set("name", user.getName())
                            .set("roleIds", user.getRoleIds())
                            .set("createTime", user.getCreateTime())
                            .set("status", user.getStatus()),SysUser.class
            );

        }
        return user;
    }

    private boolean checkUser(SysUser user) {
        Criteria criteria = Criteria.where("username").is(user.getUsername());
        if (StringUtils.isNotEmpty(user.getId())) {
            criteria.and("_id").nin(user.getId());
        }
        List<SysUser> sysUsers = mongoTemplate.find(Query.query(criteria),SysUser.class);
        return sysUsers.size() > 0 ? false : true;
    }


    /**
     * 修改状态
     *
     * @param id
     * @param status
     * @return
     */
    public Boolean updateStatus(String id, StatusType status) {
        UpdateResult updateResult = mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(id)),
                Update.update("status", status),SysUser.class);
        if (updateResult.getModifiedCount() > 0 && status == StatusType.disable) {
            removeSession(id);
        }

        return updateResult.getModifiedCount() > 0;
    }

    /**
     * redis 踢人
     *
     * @param id id
     * @author huhong
     * @date 2019-06-01 20:46
     */
    private void removeSession(String id) {

        Collection<Session> activeSessions = sessionDAO.getActiveSessions();
        activeSessions.forEach(item -> {
            Object attribute = item.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if (attribute instanceof SimplePrincipalCollection) {
                SimplePrincipalCollection simplePrincipalCollection = (SimplePrincipalCollection) attribute;
                SysUser user = (SysUser) simplePrincipalCollection.getPrimaryPrincipal();
                if (id.equals(user.getId())) {
                    sessionDAO.delete(item);
                }
            }
        });
    }

    /**
     * 通过id获取用户
     *
     * @param id
     * @return
     */
    public SysUser getUserById(String id) {
        return mongoTemplate.findById(id,SysUser.class);
    }

    /**
     * 查询用户
     *
     * @param pageObject
     * @return
     */
    public PageInfo<SysUserVO> queryUser(PageObject<SysUser> pageObject) {
        PageInfo<SysUserVO> info = new PageInfo<>();
        Query query = new Query();
        Long total = mongoTemplate.count(query,SysUser.class);
        info.setTotal(total);
        skipLimitQuery(query, pageObject.getPage());
        List<SysUser> list = mongoTemplate.find(query,SysUser.class);
        List<SysUserVO> userList = buildUserVo(list);
        info.setList(userList);
        return info;
    }

    private List<SysUserVO> buildUserVo(List<SysUser> list) {

        if (list == null || list.size() == 0) {
            return new ArrayList<>();
        }
        List<SysRole> roles = roleService.loadAllRole();
        Map<String, SysRole> collect = roles.stream().collect(Collectors.toMap(SysRole::getId, Function.identity()));
        List<SysUserVO> result = new ArrayList<>();
        for (SysUser user : list) {
            SysUserVO vo = new SysUserVO();
            BeanUtils.copyProperties(user, vo);
            List<SysRole> roleList = new ArrayList<>();
            for (String roleId : vo.getRoleIds()) {
                if (collect.containsKey(roleId)) {
                    roleList.add(collect.get(roleId));
                }
            }
            vo.setRoleList(roleList);
            result.add(vo);
        }

        return result;
    }

    private void skipLimitQuery(Query query, BasePage page) {
        int skip = page.getSkip();
        if (page.getSkip() > 0) {
            query.skip(skip);
        }
        query.limit(page.getPageSize());
    }

    public SysUser getUserByUsername(String username) {

        return mongoTemplate.findOne(Query.query(Criteria.where("username").is(username)), SysUser.class);
    }

    public List<MenuTree> getMenuTreeByUser(SysUser user) {
        SysUser dbUser = getUserById(user.getId());
        List<String> distinctIds = menuService.getUserMenuId(dbUser);
        List<MenuTree> menus = menuService.loadMenuTreeByMenuIds(distinctIds);
        return menus;
    }


    /**
     * 修改密码
     *
     * @param user
     * @param pwd
     */
    public Boolean updatePassword(SysUser user, String pwd, boolean flag) {
        String hashPwd = ShiroPasswordUtil.encode(pwd, user.getUsername());
        UpdateResult updateResult = null;
        if (flag) {
            updateResult = mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(user.getId())),
                    Update.update("password", hashPwd).set("pwdFlag", "1"),SysUser.class);
        } else {
            updateResult = mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(user.getId())),
                    Update.update("password", hashPwd).set("pwdFlag", "0"),SysUser.class);
        }
        return updateResult.getModifiedCount() > 0;
    }
}
