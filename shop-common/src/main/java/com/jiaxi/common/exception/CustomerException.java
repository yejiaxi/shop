package com.jiaxi.common.exception;


import com.jiaxi.common.constant.ShopCode;

/**
 * 自定义异常
 */
public class CustomerException extends RuntimeException{

    private ShopCode shopCode;

    public CustomerException(ShopCode shopCode) {
        this.shopCode = shopCode;
    }
}
