package com.crm.auth.vo;

import com.crm.auth.po.Dict;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huhong on 2019年03月20日17:36:10
 */
public class DictVo extends Dict implements Serializable{

    private List<DictItemVo> itemList ;

    public List<DictItemVo> getItemList() {
        return itemList;
    }

    public void setItemList(List<DictItemVo> itemList) {
        this.itemList = itemList;
    }


    public Map<String,String> getDictMap (){
        Map<String,String> map = new HashMap<>();
        for (DictItemVo dictItemVo : itemList) {
            map.put(dictItemVo.getValue().toString(),dictItemVo.getText());
        }
        return  map;
    }
}
