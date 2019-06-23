package com.crm.auth.controller;

import com.crm.auth.po.SysMenu;
import com.crm.auth.service.SysMenuService;
import com.crm.auth.vo.MenuTree;
import com.crm.common.bo.JsonResult;
import com.crm.common.enums.StatusType;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/auth/menu")
public class SysMenuController {

    @Resource
    private SysMenuService sysMenuService;

    @PostMapping("/loadMenuTree")
    public JsonResult<List<MenuTree>> loadMenuTree() {
        return sysMenuService.loadMenuTree();
    }

    @PostMapping("/saveMenu")
    public JsonResult<SysMenu> saveMenu(@RequestBody SysMenu menu) {
        SysMenu sysMenu = sysMenuService.saveMenu(menu);
        if (sysMenu == null) {
            return new JsonResult<SysMenu>().setCode(500).setMsg("找不到数据");
        }
        return new JsonResult<SysMenu>().setData(sysMenu);
    }

    @GetMapping("/updateStatus")
    public JsonResult updateStatus(@RequestParam("id") String id, @RequestParam("status") StatusType status) {
        sysMenuService.updateStatus(id, status);
        return new JsonResult();
    }

    @GetMapping("/getMenuById")
    public JsonResult<SysMenu> getMenuById(@RequestParam("id") String id) {
        SysMenu menu = sysMenuService.getMenuById(id);
        return new JsonResult<SysMenu>().setData(menu);
    }

    @GetMapping("/delMenu")
    public JsonResult delMenu(@RequestParam("id") String id) {
        sysMenuService.deleteMenu(id);
        return new JsonResult();
    }


    @GetMapping("/loadButtons")
    public JsonResult<List<SysMenu>> loadButtons(@RequestParam("router") String router) {
        List<SysMenu> menus = sysMenuService.loadButtons(router);
        return new JsonResult<List<SysMenu>>().setData(menus);
    }


    @PostMapping("/uploadMenu")
    public JsonResult uploadMenu(HttpServletRequest request) throws IOException {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getServletContext());
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator<String> iter = multiRequest.getFileNames();
            String savePath = request.getServletContext().getRealPath("/");
            while (iter.hasNext()) {
                MultipartFile multipartFile = multiRequest.getFile(iter.next());
                String fileName = savePath + File.separator + UUID.randomUUID() + multipartFile.getOriginalFilename();
                File file = new File(fileName);
                multipartFile.transferTo(file);
                String menuString = FileUtils.readFileToString(file, "UTF-8");
                sysMenuService.replaceMenu(menuString);
                break;
            }
        }
        return new JsonResult();
    }
}
