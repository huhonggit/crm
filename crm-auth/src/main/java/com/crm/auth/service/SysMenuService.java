package com.crm.auth.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.crm.auth.enums.MenuType;
import com.crm.auth.po.SysMenu;
import com.crm.auth.po.SysRole;
import com.crm.auth.po.SysUser;
import com.crm.auth.util.OnlineUserUtil;
import com.crm.auth.vo.MenuTree;
import com.crm.common.bo.JsonResult;
import com.crm.common.enums.StatusType;
import com.mongodb.client.result.UpdateResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysMenuService implements InitializingBean {

    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private SysRoleService roleService;

    @Resource
    private SysUserService sysUserService;


    /**
     * 加载菜单资源
     *
     * @return
     */
    public JsonResult<List<MenuTree>> loadMenuTree() {
        List<SysMenu> sysMenus = loadAll();
        List<MenuTree> menuTreeList = computeMenuTree(sysMenus);
        return new JsonResult<List<MenuTree>>().setData(menuTreeList);
    }

    public List<SysMenu> loadAll() {
        List<SysMenu> sysMenus = mongoTemplate.find(new Query(), SysMenu.class);
        return sysMenus;
    }


    /**
     * 通过list计算为树结构
     *
     * @param list
     * @return
     */
    public List<MenuTree> computeMenuTree(List<SysMenu> list) {
        List<MenuTree> result = new ArrayList<>();
        for (SysMenu menu : list) {
            if (StringUtils.isEmpty(menu.getParentId())) {
                result.add(findChildren(menu, list));
            }
        }
        return result;
    }

    /**
     * 递归填充儿子节点
     *
     * @param menu
     * @return
     */
    private MenuTree findChildren(SysMenu menu, List<SysMenu> list) {
        MenuTree menuTree = new MenuTree();
        BeanUtils.copyProperties(menu, menuTree);
        menuTree.setChildren(new ArrayList<MenuTree>());
        for (SysMenu node : list) {
            if (menu.getId().equals(node.getParentId())) {
                menuTree.getChildren().add(findChildren(node, list));
            }
        }
        menuTree.getChildren().sort(Comparator.comparing(MenuTree::getSort));
        return menuTree;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        SysMenu root = mongoTemplate.findById("root", SysMenu.class);
        if (root == null) {
            root = new SysMenu();
            root.setId("root");
            root.setName("系统根目录");
            root.setRouter("/");
            root.setStatus(StatusType.enable);
            root.setSort(1);
            root.setCreateTime(new Date());
            root.setType(MenuType.root);
            mongoTemplate.insert(root);
        }
    }

    /**
     * 保存菜单
     *
     * @return
     */
    public SysMenu saveMenu(SysMenu menu) {
        if (!checkMenu(menu)) {
            return null;
        }

        mongoTemplate.save(menu);
        return menu;
    }

    /**
     * 校验菜单，一个router只能存在一条 便于控制页面按钮权限
     *
     * @param menu menu
     * @return boolean
     * @author huhong
     * @date 2019-06-10 17:25
     */
    private boolean checkMenu(SysMenu menu) {
        if (menu.getType() == MenuType.menu) {
            Criteria criteria = Criteria.where("type").is(MenuType.menu).and("router").is(menu.getRouter());
            if (StringUtils.isNotEmpty(menu.getId())) {
                criteria.and("_id").nin(menu.getId());
            }
            List<SysMenu> list = mongoTemplate.find(Query.query(criteria), SysMenu.class);
            return list.size() > 0 ? false : true;

        } else {
            return true;
        }
    }

    /**
     * 更新状态
     *
     * @param id
     * @param status
     * @return
     */
    public Boolean updateStatus(String id, StatusType status) {
        UpdateResult updateResult = mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(id)),
                Update.update("status", status), SysMenu.class);
        return updateResult.getModifiedCount() > 0;
    }


    /**
     * 删除菜单
     *
     * @param id
     * @return
     */
    public Boolean deleteMenu(String id) {
        SysMenu sysMenu = new SysMenu();
        sysMenu.setId(id);
        mongoTemplate.remove(Query.query(Criteria.where("_id").is(id)), SysMenu.class);

        UpdateResult updateResult = roleService.deleteMenu(id);
        System.out.println(updateResult.getModifiedCount());

        return true;
    }

    /**
     * 批量获取菜单
     *
     * @param ids
     * @return
     */
    public List<SysMenu> getMenuByIds(List<String> ids) {
        return mongoTemplate.find(Query.query(Criteria.where("_id").in(ids)), SysMenu.class);
    }

    public SysMenu getMenuById(String id) {
        return mongoTemplate.findById(id, SysMenu.class);
    }


    /**
     * 获取所有的儿子节点
     *
     * @param menu
     * @param list
     * @return
     */
    public List<SysMenu> getChildren(SysMenu menu, List<SysMenu> list) {
        List<SysMenu> result = new ArrayList<>();
        for (SysMenu node : list) {
            if (menu.getId().equals(node.getParentId())) {
                result.add(findChildren(node, list));
            }
        }
        return result;
    }

    public List<MenuTree> loadMenuTreeByMenuIds(List<String> distinctIds) {
        List<SysMenu> sysMenus = getMenuByIds(distinctIds);
        List<MenuTree> menuTreeList = computeMenuTree(sysMenus);
        return menuTreeList;

    }

    /**
     * 获取按钮权限
     *
     * @param router router
     * @return java.util.List<com.cgedu.admin.auth.entity.SysMenu>
     * @author huhong
     * @date 2019-06-10 21:56
     */
    public List<SysMenu> loadButtons(String router) {
        SysUser user = OnlineUserUtil.getOnlineUser();
        SysUser userById = sysUserService.getUserById(user.getId());
        List<String> distinctIds = getUserMenuId(userById);
        SysMenu menu = mongoTemplate.findOne(Query.query(Criteria.where("type").is(MenuType.menu).and("router").is(router)),SysMenu.class);
        if (menu == null) {
            return new ArrayList<>();
        }
        List<SysMenu> buttons = mongoTemplate.find(Query.query(Criteria.where("parentId").is(menu.getId()).and("type").is(MenuType.operation).and("_id").in(distinctIds))
        ,SysMenu.class);
        return buttons;
    }

    public List<String> getUserMenuId(SysUser user) {
        List<SysRole> roles = roleService.getRoleByIds(user.getRoleIds());
        List<String> menuIds = new ArrayList<>();
        roles.forEach(item -> {
            menuIds.addAll(item.getMenuIds());
        });
        if (menuIds.size() == 0) {
            return new ArrayList<>();
        }

        List<String> distinctIds = menuIds.stream().collect(
                Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o))),
                        ArrayList::new));

        return distinctIds;
    }

    /**
     * 替换菜单
     *
     * @param menuString menuString
     * @author huhong
     * @date 2019-06-13 10:32
     */
    public void replaceMenu(String menuString) {
        JSONArray jsonArray = JSONArray.parseArray(menuString);
        List<SysMenu> collect = jsonArray.stream().map(item -> {
            SysMenu menu = null;
            System.out.println(item.toString());
            menu = JSONObject.parseObject(item.toString(), SysMenu.class);
            if (menu.getId().contains("$oid")) {
                String id = menu.getId();
                JSONObject idJson = JSONObject.parseObject(id);
                menu.setId(idJson.getString("$oid"));
            }
            return menu;
        }).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            mongoTemplate.remove(new Query(),SysMenu.class);
            mongoTemplate.insertAll(collect);
        }

    }
}
