package com.jiaxi.shop.coupon.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.jiaxi.common.constant.ShopCode;
import com.jiaxi.common.exception.CastException;
import com.jiaxi.shop.api.ICouponService;
import com.jiaxi.shop.coupon.mapper.TradeCouponMapper;
import com.jiaxi.shop.entity.Result;
import com.jiaxi.shop.pojo.TradeCoupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Service(interfaceClass = ICouponService.class)
public class CouponServiceImpl implements ICouponService {
    @Autowired
    private  TradeCouponMapper  tradeCouponMapper;
    @Override
    public TradeCoupon findOne(Long coupouId) {
        if(coupouId==null){
            CastException.cast(ShopCode.SHOP_REQUEST_PARAMETER_VALID);
        }
        return tradeCouponMapper.selectByPrimaryKey(coupouId);
    }

    @Override
    public Result updateCouponStatus(TradeCoupon coupon) {
        if(coupon==null||coupon.getCouponId()==null){
            CastException.cast(ShopCode.SHOP_REQUEST_PARAMETER_VALID);
        }
        //更新优惠券状态
        tradeCouponMapper.updateByPrimaryKey(coupon);
        return new Result(ShopCode.SHOP_SUCCESS.getSuccess(),ShopCode.SHOP_SUCCESS.getMessage());
    }
}
