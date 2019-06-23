package com.crm.auth.service;


import com.crm.auth.po.Dict;
import com.crm.auth.po.DictItem;
import com.crm.auth.vo.DictItemVo;
import com.crm.auth.vo.DictVo;
import com.crm.common.page.BasePage;
import com.crm.common.page.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 字典服务
 *
 * @author huhong
 * @date 2019-06-23 13:36
 */
@Service
public class DictService {

    @Resource
    private MongoTemplate mongoTemplate;

    public Dict saveCatalog(String name) {
        Dict dict = new Dict();
        dict.setCode(String.valueOf(dict.getId()));
        dict.setName(name);
        dict.setInfo("目录");
        dict.setType("目录");
        dict.setPid(DictLoadService.ROOT_Id);
        dict.setDataType("string");
        mongoTemplate.save(dict);
        return dict;

    }

    public DictVo loadDictVoByCode(String code) {

        return chacheDictVo(code);
    }

    private DictVo chacheDictVo(String code) {
        Dict dict = getDictByCode(code);
        DictVo vo = null;
        if (dict != null) {
            vo = new DictVo();
            BeanUtils.copyProperties(dict, vo);
            List<DictItem> items = getItemsByDictId(vo.getId());
            List<DictItemVo> itemVos = new ArrayList<>();
            items.forEach(item -> {
                DictItemVo dictItemVo = new DictItemVo();
                dictItemVo.setText(item.getText());
                if ("int".equals(dict.getDataType())) {
                    dictItemVo.setValue(Integer.valueOf(item.getValue()));
                } else {
                    dictItemVo.setValue(item.getValue());
                }
                itemVos.add(dictItemVo);
            });
            vo.setItemList(itemVos);
        }
        return vo;
    }

    public String getTextByCodeAndValue(String code, String value) {
        DictVo dictVo = loadDictVoByCode(code);
        String text = null;
        for (DictItemVo item : dictVo.getItemList()) {
            if (value.equals(item.getValue().toString())) {
                text = item.getText();
                break;
            }
        }
        return text;
    }

    /**
     * 获取默认跟目录
     *
     * @return
     */
    public Dict getRootCatalog() {
        Dict dict = mongoTemplate.findById(DictLoadService.ROOT_Id, Dict.class);
        return dict;
    }

    /**
     * 获取目录
     *
     * @return
     */
    public List<Dict> loadCatalog() {

        List<Dict> catalog = mongoTemplate.find(Query.query(Criteria.where("pid")
                .is(DictLoadService.ROOT_Id).and("type").is("目录")), Dict.class);

        return catalog;
    }

    public Dict getDictByCode(String code) {

        return mongoTemplate.findOne(Query.query(Criteria.where("code").is(code)), Dict.class);

    }

    public Dict saveDict(Dict dict) {
        mongoTemplate.insert(dict);
        return dict;
    }

    public Dict getDictById(String id) {
        return mongoTemplate.findById(id, Dict.class);
    }

    public boolean checkDict(Dict dict) {
        Criteria where = Criteria.where("code").is(dict.getCode());
        if (!StringUtils.isEmpty(dict.getId())) {
            where.and("_id").nin(dict.getId());
        }
        List<Dict> list = mongoTemplate.find(Query.query(where), Dict.class);
        return list.size() <= 0;
    }

    public Dict editDict(Dict dict) {

        mongoTemplate.save(dict);
        return null;
    }

    public List<DictItem> getItemsByDictId(String dictId) {

        List<DictItem> list = mongoTemplate.find(Query.query(Criteria.where("dictId").is(dictId)), DictItem.class);
        list.sort(Comparator.comparingInt(DictItem::getInx));
        return list;
    }

    public boolean checkDictItem(DictItem dictItem) {
        Criteria where = Criteria.where("value").is(dictItem.getValue()).and("dictId").is(dictItem.getDictId());
        if (StringUtils.isNotEmpty(dictItem.getId())) {
            where.and("_id").nin(dictItem.getId());
        }
        List<DictItem> list = mongoTemplate.find(Query.query(where), DictItem.class);
        return list.size() <= 0;
    }

    public DictItem saveDictItem(DictItem dictItem) {
        mongoTemplate.save(dictItem);
        return dictItem;
    }

    public DictItem editDictItem(DictItem dictItem) {
        mongoTemplate.save(dictItem);
        return dictItem;
    }

    public DictItem getDictItemById(String id) {
        return mongoTemplate.findById(id,DictItem.class);
    }

    /**
     * 查询字典信息 分页数据
     *
     * @param page
     * @param dict
     * @return
     */
    public PageInfo<Dict> searchDict(BasePage page, Dict dict) {
        PageInfo<Dict> info = new PageInfo<>();
        Criteria criteria = Criteria.where("type").is("字典");
        if (!StringUtils.isEmpty(dict.getPid())) {
            criteria.and("pid").is(dict.getPid());
        }
        if (!StringUtils.isEmpty(dict.getName())) {
            Pattern pattern = Pattern.compile("^.*" + dict.getName() + ".*$", Pattern.CASE_INSENSITIVE);
            criteria.orOperator(Criteria.where("name").regex(pattern),
                    Criteria.where("code").regex(pattern));
        }

        Query query = Query.query(criteria);
        Long total = mongoTemplate.count(query,Dict.class);
        info.setTotal(total);
        skipLimitQuery(query, page);
        List<Dict> list = mongoTemplate.find(query,Dict.class);

        info.setList(list);
        return info;
    }

    private void skipLimitQuery(Query query, BasePage page) {
        int skip = page.getSkip();
        if (page.getSkip() > 0) {
            query.skip(skip);
        }
        query.limit(page.getPageSize());
    }
}
