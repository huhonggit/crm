package com.crm.auth.service;

import com.crm.auth.po.SysMenu;
import com.crm.auth.po.SysRole;
import com.crm.auth.vo.MenuTree;
import com.crm.auth.vo.SysRoleVO;
import com.crm.common.enums.StatusType;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class SysRoleService {

    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private SysMenuService sysMenuService;

    /**
     * 保存角色
     *
     * @param role
     * @return
     */
    public SysRole saveRole(SysRole role) {
        role.setCreateTime(new Date());
        mongoTemplate.save(role);
        return role;
    }

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    public Boolean deleteRole(String id) {
        SysRole sysRole = new SysRole();
        sysRole.setId(id);
        DeleteResult deleteResult = mongoTemplate.remove(sysRole);
        return deleteResult.getDeletedCount() > 0;
    }

    /**
     * 批量获取角色
     *
     * @param ids
     * @return
     */
    public List<SysRole> getRoleByIds(List<String> ids) {
        if (ids == null || ids.size() == 0) {
            return new ArrayList<>();
        }
        return mongoTemplate.find(Query.query(Criteria.where("_id").in(ids)), SysRole.class);
    }

    /**
     * 加载所有的角色
     *
     * @return
     */
    public List<SysRole> loadAllRole() {
        return mongoTemplate.findAll(SysRole.class);
    }

    /**
     * 加载所有启用的角色
     *
     * @return
     */
    public List<SysRole> loadEnableRole() {
        return mongoTemplate.find(new Query(Criteria.where("status").is(StatusType.enable)),SysRole.class);
    }

    public List<SysRole> loadRoles(SysRole role) {
        Query query = new Query();
        if (StringUtils.isNotEmpty(role.getName())) {
            Pattern pattern = Pattern.compile("^.*" + role.getName() + ".*$", Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("name").regex(pattern));
        }

        return mongoTemplate.find(query,SysRole.class);

    }

    public SysRole getRoleById(String id) {
        return mongoTemplate.findById(id,SysRole.class);
    }

    public SysRoleVO getRoleVoById(String id) {
        SysRole role = getRoleById(id);
        SysRoleVO roleVO = new SysRoleVO();
        BeanUtils.copyProperties(role, roleVO);
        roleVO.setCheckMenuId(getCheckMenu(role));
        return roleVO;
    }

    /**
     * 重新计算需要选中的节点。因为如果父节点是全选中状态，后面重新配置了一个新的子节点，父节点就变为了半选中。
     *
     * @param role
     * @return
     */
    private List<String> getCheckMenu(SysRole role) {
        List<String> checkMenuIds = new ArrayList<>();
        List<SysMenu> checkMenuList = sysMenuService.getMenuByIds(role.getMenuIds());
        List<MenuTree> checkTree = sysMenuService.computeMenuTree(checkMenuList);
        compareChile(checkTree, checkMenuIds);
        return checkMenuIds;
    }

    /**
     * 递归只获取叶子节点 前端tree只需要勾选叶子节点
     *
     * @param checkTree    checkTree
     * @param checkMenuIds checkMenuIds
     * @author huhong
     * @date 2019-06-12 11:52
     */
    private void compareChile(List<MenuTree> checkTree, List<String> checkMenuIds) {
        checkTree.forEach(item -> {
            if (item.getChildren().size() > 0) {
                compareChile(item.getChildren(), checkMenuIds);
            } else {
                checkMenuIds.add(item.getId());
            }
        });

    }


    public UpdateResult deleteMenu(String id) {
        Update update = new Update();
        update.pull("menuIds", id);
        UpdateResult updateResult = mongoTemplate.updateMulti(new Query(), update,SysRole.class);
        return updateResult;
    }
}
