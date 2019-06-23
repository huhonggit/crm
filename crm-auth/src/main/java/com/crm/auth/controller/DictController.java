package com.crm.auth.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.crm.auth.po.Dict;
import com.crm.auth.po.DictItem;
import com.crm.auth.service.DictLoadService;
import com.crm.auth.service.DictService;
import com.crm.common.bo.JsonResult;
import com.crm.common.page.PageInfo;
import com.crm.common.page.PageObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 数据字典维护接口
 * Created by huhong on 2019年03月20日17:32:28
 */
@RestController
@RequestMapping("/auth/config/dict")
public class DictController {

    @Autowired
    DictService dictService;

    /**
     * 保存目录
     * @param name
     * @return
     */
    @RequestMapping("/saveCatalog")
    public JsonResult<Dict> saveCatalog(String name){
        Dict dict = dictService.saveCatalog(name);
        return new JsonResult<Dict>().setData(dict);
    }

    /**
     * 加载字典目录
     * @return
     */
    @RequestMapping("/loadCatalog")
    public  JsonResult loadCatalog(){
        Dict root = dictService.getRootCatalog();
        JSONObject rootJson = (JSONObject) JSON.toJSON(root);
        List<Dict> children = dictService.loadCatalog();
        rootJson.put("children",children);
        JSONArray result = new JSONArray();
        result.add(rootJson);
        return new JsonResult().setData(result);
    }

    /**
     * 保存字典
     * @param dict
     * @return
     */
    @RequestMapping("/saveDict")
    public JsonResult<Dict> saveDict(@RequestBody Dict dict){
        Dict old = dictService.getDictByCode(dict.getCode());
        if(old != null){
            return new JsonResult(500,"当前保存的code已经存在");
        }
        dict = dictService.saveDict(dict);
        return new JsonResult<Dict>().setData(dict);
    }

    @RequestMapping("/getDictById")
    public JsonResult<Dict>  getDictById(String id){
        Dict dict = dictService.getDictById(id);
        return new JsonResult<Dict>().setData(dict);
    }


    /**
     * 编辑字典
     * @param dict
     * @return
     */
    @RequestMapping("/editDict")
    public JsonResult<Dict> editDict(@RequestBody Dict dict){
        if(!dictService.checkDict(dict)){
            new JsonResult(500,"当前保存的code已经存在");
        }
        dict = dictService.editDict(dict);
        return new JsonResult<Dict>().setData(dict);
    }

    /**
     * 查找字典
     * @param pageObject
     * @return
     */
    @RequestMapping("/searchDict")
    public JsonResult searchDict(@RequestBody PageObject<Dict> pageObject){
        //对根节点进行处理
        Dict dict = pageObject.getObj();
        if(DictLoadService.ROOT_Id.equals(dict.getPid())){
            dict.setPid(null);
        }
        PageInfo<Dict> rst = dictService.searchDict(pageObject.getPage(),dict);
        return new JsonResult().setData(rst);
    }

    /**
     * 获取字典下的所有字典明细
     * @param dictId
     * @return
     */
    @RequestMapping("/getItemsByDictId")
    public JsonResult getItemsByDictId(String dictId){

        //对根节点进行处理
        List<DictItem> rst = dictService.getItemsByDictId(dictId);
        return new JsonResult().setData(rst);
    }

    /**
     * 保存字典明细
     * @param dictItem
     * @return
     */
    @RequestMapping("/saveDictItem")
    public JsonResult saveDictItem(@RequestBody DictItem dictItem){
        //去掉前端空格
        dictItem.setText(dictItem.getText().trim());
        dictItem.setValue(dictItem.getValue().trim());
        if(!dictService.checkDictItem(dictItem)){
            return new JsonResult(500,"当前保存的值或者名称已经存在");
        }
        dictItem = dictService.saveDictItem(dictItem);
        return new JsonResult().setData(dictItem);
    }
    /**
     * 修改字典明细
     * @param dictItem
     * @return
     */
    @RequestMapping("/editDictItem")
    public JsonResult editDictItem(@RequestBody DictItem dictItem){
        //去掉前端空格
        dictItem.setText(dictItem.getText().trim());
        dictItem.setValue(dictItem.getValue().trim());
        if(!dictService.checkDictItem(dictItem)){
            return new JsonResult(500,"当前修改的值或者名称已经存在");
        }
        dictItem = dictService.editDictItem(dictItem);
        return new JsonResult().setData(dictItem);
    }

    /**
     * 获取字典明细
     * @param id
     * @return
     */
    @RequestMapping("/getDictItemById")
    public JsonResult getDictItemById(String id){
        DictItem dictItem = dictService.getDictItemById(id);
        return new JsonResult().setData(dictItem);
    }

    /**
     * 前端load接口
     * @param code
     * @return
     */
    @RequestMapping("/loadDictVoByCode")
    public JsonResult loadDictVoByCode(String code){
        return new JsonResult().setData(dictService.loadDictVoByCode(code));
    }

}
