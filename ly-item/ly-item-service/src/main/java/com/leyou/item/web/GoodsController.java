package com.leyou.item.web;

import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import com.leyou.item.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @Author: taoqianlilang
 * @Description:
 * @Date: Created in 下午 11:43 2020/2/28
 * @Modified By:
 */
@RestController
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping("spu/page")
    public ResponseEntity<PageResult<Spu>> querySpuByPage(
            @RequestParam(value = "key",required = false) String key,
            @RequestParam(value = "saleable",required = false) Boolean saleable,
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rows",defaultValue = "5") Integer rows
    ){
        PageResult<Spu> pageResult = goodsService.querySpuByPage(key, saleable, page, rows);
        return ResponseEntity.ok(pageResult);
    }
/**
 *
 * @Description: 添加商品信息
 *
 * @auther: taoqianlilang
 * @date: 下午 5:27 2020/2/29
 * @param: [spu]
 * @return: org.springframework.http.ResponseEntity<java.lang.Void>
 *
 */
    @PostMapping("goods")
    public ResponseEntity<Void> insertGoods(@RequestBody Spu spu){
        goodsService.insertGoods(spu);
        return ResponseEntity.ok().build();
    }

    @PutMapping("goods")
    public ResponseEntity<Void> updateGoods(@RequestBody Spu spu){
        goodsService.updateGoods(spu);
        return ResponseEntity.ok().build();
    }
    /**
     *
     * @Description: 通过商品id查detail
     *
     * @auther: taoqianlilang
     * @date: 下午 9:44 2020/2/29
     * @param: [id]
     * @return: org.springframework.http.ResponseEntity<com.leyou.item.pojo.SpuDetail>
     *
     */
    @GetMapping("spu/detail/{id}")
    public ResponseEntity<SpuDetail> querySpuDetailBySupId(@PathVariable("id") Long id){
        return ResponseEntity.ok(goodsService.querySpuDetailBySupId(id));
    }
    /**
     *
     * @Description: 通过商品id查询多条sku
     *
     * @auther: taoqianlilang
     * @date: 下午 10:00 2020/2/29
     * @param: [id]
     * @return: org.springframework.http.ResponseEntity<java.util.List<com.leyou.item.pojo.Sku>>
     *
     */
    @GetMapping("sku/list")
    public ResponseEntity<List<Sku>> querySkuBySupId(@RequestParam("id") Long id){
        return ResponseEntity.ok(goodsService.querySkuBySupId(id));
    }


}
