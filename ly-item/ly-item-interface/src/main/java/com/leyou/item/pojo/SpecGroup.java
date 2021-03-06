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
 * @date: 下午 4:26 2020/2/29
 * @param:
 * @return:
 *
 */
@Data
@Table(name = "tb_spec_group")
public class SpecGroup {
    @Id
    @KeySql(useGeneratedKeys = true)
    private  Long id;
    private  Long cid;
    private  String name;
}
