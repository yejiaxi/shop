package com.jiaxi.shop.coupon.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.jiaxi.shop.api.ICouponService;
import com.jiaxi.shop.entity.Result;
import com.jiaxi.shop.pojo.TradeCoupon;
import org.springframework.stereotype.Component;

@Component
@Service(interfaceClass = ICouponService.class)
public class CouponServiceImpl implements ICouponService {
    @Override
    public TradeCoupon findOne(Long coupouId) {
        return null;
    }

    @Override
    public Result updateCouponStatus(TradeCoupon coupon) {
        return null;
    }
}
