package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.vo.PageResult;
import com.leyou.item.mapper.SkuMapper;
import com.leyou.item.mapper.SpuDetailMapper;
import com.leyou.item.mapper.SpuMapper;
import com.leyou.item.mapper.StockMapper;
import com.leyou.item.pojo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: taoqianlilang
 * @Description:
 * @Date: Created in 下午 11:50 2020/2/28
 * @Modified By:
 */

@Service
public class GoodsService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private StockMapper stockMapper;

    public PageResult<Spu> querySpuByPage(String key, Boolean saleable, Integer page, Integer rows) {
        //分页
        PageHelper.startPage(page, rows);
        //过滤
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("title", "%" + key + "%");
        }
        if (saleable != null) {
            criteria.andEqualTo("saleable", saleable);
        }
        //排序（按跟新时间）
        example.setOrderByClause("last_update_time DESC");

        //查询
        List<Spu> spus = spuMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(spus)) {
            throw new LyException(ExceptionEnum.GOODS_NOT_FOUND);
        }
        //解析分类和品牌名称
        loadCategoryAndBrandName(spus);
        //解析分页结果
        PageInfo<Spu> info = new PageInfo<>(spus);
        return new PageResult<>(info.getTotal(), spus);
    }

    public void loadCategoryAndBrandName(List<Spu> spus) {
        for (Spu spu : spus) {
            //处理分类名称
            List<String> name = categoryService.queryByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()))
                    .stream().map(Category::getName).collect(Collectors.toList());
            spu.setCname(StringUtils.join(name, "/"));
            //处理品牌名称
            spu.setBname(brandService.queryBrandNameById(spu.getBrandId()).getName());
        }
    }

    @Transactional
    public void insertGoods(Spu spu) {
        //插入spu
        spu.setCreateTime(new Date());
        spu.setLastUpdateTime(spu.getCreateTime());
        spu.setSaleable(true);
        spu.setValid(false);
        int count = spuMapper.insert(spu);
        if (count != 1) {
            throw new LyException(ExceptionEnum.INSERT_DEFAULT);
        }
        //插入SpuDetail
        SpuDetail spuDetail = spu.getSpuDetail();
        spuDetail.setSpuId(spu.getId());
        count = spuDetailMapper.insert(spuDetail);
        if (count != 1) {
            throw new LyException(ExceptionEnum.INSERT_DEFAULT);
        }
        saveSkuAndStock(spu);
    }

    public void saveSkuAndStock(Spu spu) {
        int count;//新增Sku
        List<Stock> stockList = new ArrayList<>();
        List<Sku> skus = spu.getSkus();
        for (Sku sku : skus) {
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            sku.setSpuId(spu.getId());
            count = skuMapper.insert(sku);
            if (count != 1) {
                throw new LyException(ExceptionEnum.INSERT_DEFAULT);
            }

            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stockList.add(stock);
        }
        //新增库存(库存放在sku表中)
        count = stockMapper.insertList(stockList);
        if (count != stockList.size()) {
            throw new LyException(ExceptionEnum.INSERT_DEFAULT);
        }
    }

    public SpuDetail querySpuDetailBySupId(Long id) {
        SpuDetail spuDetail = spuDetailMapper.selectByPrimaryKey(id);
        if (spuDetail == null) {
            throw new LyException(ExceptionEnum.SPU_DETAIL_NOT_FOUND);
        }
        return spuDetail;
    }

    public List<Sku> querySkuBySupId(Long id) {
        Sku sku = new Sku();
        sku.setSpuId(id);
        List<Sku> skus = skuMapper.select(sku);
        if (CollectionUtils.isEmpty(skus)) {
            throw new LyException(ExceptionEnum.SKU_NOT_FOUND);
        }
        //查询库存
        //取出来的的id的集合
        List<Long> collect = skus.stream().map(Sku::getId).collect(Collectors.toList());
        List<Stock> stockList = stockMapper.selectByIdList(collect);
        if (CollectionUtils.isEmpty(stockList)) {
            throw new LyException(ExceptionEnum.STOCK_NOT_FOUND);
        }
        //把stock变成一个map 其中key是id value是值
        Map<Long, Integer> map = stockList.stream()
                .collect(Collectors.toMap(Stock::getSkuId, Stock::getStock));
        skus.forEach(s -> s.setStock(map.get(s.getId())));
        return skus;
    }

    public void updateGoods(Spu spu) {
        //查出sku
        Sku sku = new Sku();
        sku.setSpuId(spu.getId());
        List<Sku> skus = skuMapper.select(sku);
        if (!CollectionUtils.isEmpty(skus)) {
            //删除库存
            List<Long> collect = skus.stream().map(Sku::getId).collect(Collectors.toList());
            stockMapper.deleteByIdList(collect);
            //删除sku
            skuMapper.delete(sku);
        } else {
            throw new LyException(ExceptionEnum.SKU_NOT_FOUND);
        }
        //修改spu
        spu.setValid(null);
        spu.setLastUpdateTime(new Date());
        spu.setCreateTime(null);
        spu.setSaleable(null);

        int i = spuMapper.updateByPrimaryKeySelective(spu);
        if (i != 1) {
            throw new LyException(ExceptionEnum.SKU_NOT_FOUND);
        }
        //修改detail
        int keySelective = spuDetailMapper.updateByPrimaryKeySelective(spu.getSpuDetail());
        if (keySelective != 1) {
            throw new LyException(ExceptionEnum.DETAIL_UPDATE_ERROR);
        }
        saveSkuAndStock(spu);
    }
}
