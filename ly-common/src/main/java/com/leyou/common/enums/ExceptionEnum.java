package com.leyou.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnum {
    PRICE_CANNOT_BE_NULL(400,"价格不能为空"),
    CATEGORY_CANNOT_FOND(404,"商品分类没查到"),
    BRAND_NOT_FOUND(500,"品牌没查到"),
    GOODS_NOT_FOUND(500,"品牌没查到"),
    SPU_DETAIL_NOT_FOUND(500,"SPU_DETAIL没查到"),
    SPU_UPDATE_ERROR(500,"SUP更新失败"),
    DETAIL_UPDATE_ERROR(500,"DETAIL更新失败"),
    STOCK_NOT_FOUND(404,"商品库存未找到"),
    SKU_NOT_FOUND(404,"SKU找不到"),
    BRAND_SAVE_ERROR(404,"品牌新增失败"),
    SPEC_GROUP_NOT_FIND(404,"分组找不到"),
    FILE_URL_ERROR(404,"文件路径不正确"),
    FILE_TYPE_ERROR(400,"文件类型不匹配"),
    UPLOAD_FILE_ERROR(404,"上传文件失败"),
    UPDATE_DEFAULT(404,"更新失败"),
    DELETE_DEFAULT(400,"删除失败"),
    INSERT_DEFAULT(404,"插入失败"),
    FIND_DEFAULT(404,"查询失败"),
    ;
    private Integer code;
    private String massage;

}
