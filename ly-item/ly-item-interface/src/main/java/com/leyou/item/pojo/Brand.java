package com.leyou.item.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
/**
 *
 * @Description:
 *
 * @auther: taoqianlilang
 * @date: 下午 4:27 2020/2/29
 * @param:
 * @return:
 *
 */
@Table(name = "tb_brand")
@Data
public class Brand {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private String name;//品牌名称
    private String image;//品牌图片
    private Character letter;
}
