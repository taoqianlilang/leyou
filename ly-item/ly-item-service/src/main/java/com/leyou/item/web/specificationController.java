package com.leyou.item.web;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 *
 * @Description: 
 * 
 * @auther: taoqianlilang
 * @date: 2:00 2020/2/28 
 * @param: 
 * @return: 
 *
 */
@RestController
@RequestMapping("spec")
public class specificationController {
    @Autowired
    private SpecificationService specificationService;

/**
 *
 * @Description:根据分类id查询规格组 
 * 
 * @auther: taoqianlilang
 * @date: 2:01 2020/2/28 
 * @param: [cid]
 * @return: org.springframework.http.ResponseEntity<java.util.List<com.leyou.item.pojo.SpecGroup>>
 *
 */
    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>> querySpecGroupByCid(@PathVariable("cid") Long cid ){
        return ResponseEntity.ok(specificationService.querySpecGroupByCid(cid));
    }

/**
 *
 * @Description: 更改组名称
 * 
 * @auther: taoqianlilang
 * @date: 2:02 2020/2/28
 * @param: [specGroup]
 * @return: org.springframework.http.ResponseEntity<java.lang.Void>
 *
 */
    @PutMapping("group")
    public ResponseEntity<Void> updateSpecGroupName(@RequestBody SpecGroup specGroup){
        specificationService.updateSpecGroupName(specGroup);
        return ResponseEntity.ok().build();
    }
    /**
     *
     * @Description: 删除组
     *
     * @auther: taoqianlilang
     * @date: 2:34 2020/2/28
     * @param: [id]
     * @return: org.springframework.http.ResponseEntity<java.lang.Void>
     *
     */
    @DeleteMapping("group/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable("id") Long id){
        specificationService.deleteGroup(id);
        return ResponseEntity.ok().build();
    }
    /**
     *
     * @Description: 增加分组
     *
     * @auther: taoqianlilang
     * @date: 2:42 2020/2/28
     * @param: [specGroup]
     * @return: org.springframework.http.ResponseEntity<java.lang.Void>
     *
     */

    @PostMapping("group")
    public  ResponseEntity<Void> insertGroup(@RequestBody SpecGroup specGroup){
        specificationService.insertGroup(specGroup);
        return ResponseEntity.ok().build();
    }




}
