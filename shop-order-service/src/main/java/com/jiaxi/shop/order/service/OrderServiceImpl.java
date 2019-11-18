package com.jiaxi.shop.order.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.jiaxi.common.constant.ShopCode;
import com.jiaxi.common.exception.CastException;
import com.jiaxi.common.utils.IDWorker;
import com.jiaxi.shop.api.ICouponService;
import com.jiaxi.shop.api.IGoodService;
import com.jiaxi.shop.api.IOrderService;
import com.jiaxi.shop.api.IUserService;
import com.jiaxi.shop.entity.Result;
import com.jiaxi.shop.order.mapper.TradeOrderMapper;
import com.jiaxi.shop.pojo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Slf4j
@Component
@Service(interfaceClass = IOrderService.class)
public class OrderServiceImpl implements IOrderService {
    @Reference
    private IGoodService goodsService;
    @Reference
    private IUserService userService;

    @Reference
    private ICouponService couponService;
    @Autowired
    private TradeOrderMapper tradeOrderMapper;
    @Autowired
    private IDWorker idWorker;
    /**
     * 确认订单
     * @param tradeOrder
     * @return
     */
    @Override
    public Result confirmOrder(TradeOrder tradeOrder) {
        //校验订单
        checkOrder(tradeOrder);
        //生成预订单
        long preOrderID = createPreOrder(tradeOrder);
        try {
            //扣减库存
            reduceGoodNum(tradeOrder);
            //扣除优惠券
            reduceCoupon(tradeOrder);
            //扣除余额
            reduceUserMoney(tradeOrder);
            //确认订单
            updateOrderStatus(tradeOrder);
        }catch (Exception e){

        }finally {

        }



        //返回成功状态
        return null;
    }

    /**
     * 修改订单状态
     * @param order
     */
    private void updateOrderStatus(TradeOrder order) {
        order.setOrderStatus(ShopCode.SHOP_ORDER_CONFIRM.getCode());
        order.setPayStatus(ShopCode.SHOP_ORDER_PAY_STATUS_NO_PAY.getCode());
        order.setConfirmTime(new Date());
        int r = tradeOrderMapper.updateByPrimaryKey(order);
        if(r<=0){
            CastException.cast(ShopCode.SHOP_ORDER_CONFIRM_FAIL);
        }
        log.info("订单:"+order.getOrderId()+"确认订单成功");
    }

    /**
     * 扣减余额
     * @param order
     */
    private void reduceUserMoney(TradeOrder order) {
        if(order.getMoneyPaid()!=null && order.getMoneyPaid().compareTo(BigDecimal.ZERO)==1){
            TradeUserMoneyLog userMoneyLog = new TradeUserMoneyLog();
            userMoneyLog.setOrderId(order.getOrderId());
            userMoneyLog.setUserId(order.getUserId());
            userMoneyLog.setUseMoney(order.getMoneyPaid());
            userMoneyLog.setMoneyLogType(ShopCode.SHOP_USER_MONEY_PAID.getCode());
            Result result = userService.updateMoneyPaid(userMoneyLog);
            if(result.getSuccess().equals(ShopCode.SHOP_FAIL.getSuccess())){
                CastException.cast(ShopCode.SHOP_USER_MONEY_REDUCE_FAIL);
            }
            log.info("订单:"+order.getOrderId()+",扣减余额成功");
        }
    }

    /**
     * 扣减优惠券
     * @param order
     */
    private void reduceCoupon(TradeOrder order) {
        if(order.getCouponId()!=null){
            TradeCoupon coupon = couponService.findOne(order.getCouponId());
            coupon.setOrderId(order.getOrderId());
            coupon.setIsUsed(ShopCode.SHOP_COUPON_ISUSED.getCode());
            coupon.setUsedTime(new Date());

            //更新优惠券状态
            Result result =  couponService.updateCouponStatus(coupon);
            if(result.getSuccess().equals(ShopCode.SHOP_FAIL.getSuccess())){
                CastException.cast(ShopCode.SHOP_COUPON_USE_FAIL);
            }
            log.info("订单:"+order.getOrderId()+",使用优惠券");
        }
    }

    /**
     * 扣减库存
     * @param order
     */
    private void reduceGoodNum(TradeOrder order) {
        TradeGoodsNumberLog goodsNumberLog = new TradeGoodsNumberLog();
        goodsNumberLog.setOrderId(order.getOrderId());
        goodsNumberLog.setGoodsId(order.getGoodsId());
        goodsNumberLog.setGoodsNumber(order.getGoodsNumber());
        Result result = goodsService.reduceGoodsNum(goodsNumberLog);
        if(result.getSuccess().equals(ShopCode.SHOP_FAIL.getSuccess())){
            CastException.cast(ShopCode.SHOP_REDUCE_GOODS_NUM_FAIL);
        }
        log.info("订单:"+order.getOrderId()+"扣减库存成功");
    }

    /**
     * 生成预订单
     * @param order
     */
    private long createPreOrder(TradeOrder order) {
        //1.设置订单状态为不可见
        order.setOrderStatus(ShopCode.SHOP_ORDER_NO_CONFIRM.getCode());
        //2.订单ID
        order.setOrderId(idWorker.nextId());
        //核算运费是否正确
        BigDecimal shippingFee = calculateShippingFee(order.getOrderAmount());
        if (order.getShippingFee().compareTo(shippingFee) != 0) {
            CastException.cast(ShopCode.SHOP_ORDER_SHIPPINGFEE_INVALID);
        }
        //3.计算订单总价格是否正确
        BigDecimal orderAmount = order.getGoodsPrice().multiply(new BigDecimal(order.getGoodsNumber()));
        orderAmount.add(shippingFee);
        if (orderAmount.compareTo(order.getOrderAmount()) != 0) {
            CastException.cast(ShopCode.SHOP_ORDERAMOUNT_INVALID);
        }

        //4.判断优惠券信息是否合法
        Long couponId = order.getCouponId();
        if (couponId != null) {
            TradeCoupon coupon = couponService.findOne(couponId);
            //优惠券不存在
            if (coupon == null) {
                CastException.cast(ShopCode.SHOP_COUPON_NO_EXIST);
            }
            //优惠券已经使用
            if ((ShopCode.SHOP_COUPON_ISUSED.getCode().toString())
                    .equals(coupon.getIsUsed().toString())) {
                CastException.cast(ShopCode.SHOP_COUPON_INVALIED);
            }
            order.setCouponPaid(coupon.getCouponPrice());
        } else {
            order.setCouponPaid(BigDecimal.ZERO);
        }

        //5.判断余额是否正确
        BigDecimal moneyPaid = order.getMoneyPaid();
        if (moneyPaid != null) {
            //比较余额是否大于0
            int r = order.getMoneyPaid().compareTo(BigDecimal.ZERO);
            //余额小于0
            if (r == -1) {
                CastException.cast(ShopCode.SHOP_MONEY_PAID_LESS_ZERO);
            }
            //余额大于0
            if (r == 1) {
                //查询用户信息
                TradeUser user = userService.findOne(order.getUserId());
                if (user == null) {
                    CastException.cast(ShopCode.SHOP_USER_NO_EXIST);
                }
                //比较余额是否大于用户账户余额
                if (user.getUserMoney().compareTo(order.getMoneyPaid().longValue()) == -1) {
                    CastException.cast(ShopCode.SHOP_MONEY_PAID_INVALID);
                }
                order.setMoneyPaid(order.getMoneyPaid());
            }
        } else {
            order.setMoneyPaid(BigDecimal.ZERO);
        }
        //计算订单支付总价
        order.setPayAmount(orderAmount.subtract(order.getCouponPaid())
                .subtract(order.getMoneyPaid()));
        //设置订单添加时间
        order.setAddTime(new Date());

        //保存预订单
        int r = tradeOrderMapper.insert(order);
        if (ShopCode.SHOP_SUCCESS.getCode() != r) {
            CastException.cast(ShopCode.SHOP_ORDER_SAVE_ERROR);
        }
        log.info("订单:["+order.getOrderId()+"]预订单生成成功");
        return order.getOrderId();
    }

    /**
     * 校验订单
     * @param order
     */
    private void checkOrder(TradeOrder order) {
        //1.校验订单是否存在
        if(order==null){
            CastException.cast(ShopCode.SHOP_ORDER_INVALID);
        }
        //2.校验订单中的商品是否存在
        TradeGoods goods = goodsService.findOne(order.getGoodsId());
        if(goods==null){
            CastException.cast(ShopCode.SHOP_GOODS_NO_EXIST);
        }
        //3.校验下单用户是否存在
        TradeUser user = userService.findOne(order.getUserId());
        if(user==null){
            CastException.cast(ShopCode.SHOP_USER_NO_EXIST);
        }
        //4.校验商品单价是否合法
        if(order.getGoodsPrice().compareTo(goods.getGoodsPrice())!=0){
            CastException.cast(ShopCode.SHOP_GOODS_PRICE_INVALID);
        }
        //5.校验订单商品数量是否合法
        if(order.getGoodsNumber()>=goods.getGoodsNumber()){
            CastException.cast(ShopCode.SHOP_GOODS_NUM_NOT_ENOUGH);
        }

        log.info("校验订单通过");

    }

    /**
     * 核算运费
     * @param orderAmount
     * @return
     */
    private BigDecimal calculateShippingFee(BigDecimal orderAmount) {
        if(orderAmount.compareTo(new BigDecimal(100))==1){
            return BigDecimal.ZERO;
        }else{
            return new BigDecimal(10);
        }

    }




}

