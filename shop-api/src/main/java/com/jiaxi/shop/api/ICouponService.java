package com.jiaxi.shop.api;

import com.jiaxi.shop.entity.Result;
import com.jiaxi.shop.pojo.TradeCoupon;

public interface ICouponService {
    /**
     * 根据ID查询优惠券对象
     * @param coupouId
     * @return
     */
    public TradeCoupon findOne(Long coupouId);

    /**
     * 更细优惠券状态
     * @param coupon
     * @return
     */
    Result updateCouponStatus(TradeCoupon coupon);
}
