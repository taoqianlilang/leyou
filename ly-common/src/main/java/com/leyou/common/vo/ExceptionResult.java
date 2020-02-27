package com.leyou.common.vo;

import com.leyou.common.enums.ExceptionEnum;
import lombok.Data;

@Data
public class ExceptionResult {
    private Integer status;
    private String message;
    private Long timeTamp;

    public ExceptionResult(ExceptionEnum em){
        this.message=em.getMassage();
        this.status=em.getCode();
        this.timeTamp=System.currentTimeMillis();
    }


}
