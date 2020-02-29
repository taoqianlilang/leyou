package com.leyou.item.service;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.item.mapper.SpecGroupMapper;
import com.leyou.item.mapper.SpecParamMapper;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class SpecificationService {

    @Autowired
    private SpecGroupMapper specGroupMapper;

    @Autowired
    private SpecParamMapper specParamMapper;

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
              throw new LyException(ExceptionEnum.DELETE_DEFAULT);
          }
    }

    public void  insertGroup(SpecGroup specGroup) {
        int insert = specGroupMapper.insert(specGroup);
        if (insert!=1){
            throw new LyException(ExceptionEnum.INSERT_DEFAULT);
        }
    }

    public List<SpecParam> querySpecParamByList(Long gid, Long cid, Boolean searching) {
        SpecParam param=new SpecParam();
        param.setGroupId(gid);
        param.setCid(cid);
        param.setSearching(searching);
        List<SpecParam> select = specParamMapper.select(param);
        if (CollectionUtils.isEmpty(select)){
            throw new LyException(ExceptionEnum.FIND_DEFAULT);
        }
        return select;
    }

    public void insertSpecParam(SpecParam specParam) {
        int i = specParamMapper.insert(specParam);
        if (i!=1){
            throw new LyException(ExceptionEnum.INSERT_DEFAULT);
        }
    }

    public void deleteSpecParamById(Long id) {
        SpecParam param=new SpecParam();
        param.setId(id);
        int delete = specParamMapper.delete(param);
        if (delete!=1){
            throw new LyException(ExceptionEnum.DELETE_DEFAULT);
        }
    }

    public void updateSpecParam(SpecParam specParam) {
        int i = specParamMapper.updateByPrimaryKey(specParam);
        if (i!=1){
            throw new LyException(ExceptionEnum.UPDATE_DEFAULT);
        }
    }
}
