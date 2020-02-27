package com.leyou.item.service;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.item.mapper.SpecGroupMapper;
import com.leyou.item.pojo.SpecGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class SpecificationService {

    @Autowired
    private SpecGroupMapper specGroupMapper;

    public List<SpecGroup> querySpecGroupByCid(Long cid) {
        SpecGroup specGroup=new SpecGroup();
        specGroup.setCid(cid);
        List<SpecGroup> select = specGroupMapper.select(specGroup);

        if (CollectionUtils.isEmpty(select)){
            throw new LyException(ExceptionEnum.SPEC_GROUP_NOT_FIND);
        }
        return select;
    }
    public void updateSpecGroupName(SpecGroup specGroup) {
        int i = specGroupMapper.updateByPrimaryKey(specGroup);
        if (i!=1){
            throw new LyException(ExceptionEnum.UPDATE_DEFAULT);
        }
    }

    public void deleteGroup(Long id) {
          SpecGroup group=new SpecGroup();
          group.setId(id);
          int i = specGroupMapper.delete(group);
          if (i!=1){
              throw new LyException(ExceptionEnum.DELETE_ERROR);
          }
    }

    public void  insertGroup(SpecGroup specGroup) {
        int insert = specGroupMapper.insert(specGroup);
        if (insert!=1){
            throw new LyException(ExceptionEnum.INSERT_ERROR);
        }
    }
}
