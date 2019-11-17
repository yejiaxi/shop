package com.jiaxi.shop.order.service;

import com.jiaxi.common.constant.ShopCode;
import com.jiaxi.common.exception.CastException;
import com.jiaxi.shop.api.IOrderService;
import com.jiaxi.shop.entity.Result;
import com.jiaxi.shop.pojo.TradeOrder;

public class OrderServiceImpl implements IOrderService {
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
        createPreOrder(tradeOrder);
        //扣减库存
        reduceGoodNum(tradeOrder);
        //扣除优惠券
        reduceCoupon(tradeOrder);
        //扣除余额
        reduceUserMoney(tradeOrder);
        //确认订单
        updateOrderStatus(tradeOrder);

        //返回成功状态
        return null;
    }

    /**
     * 修改订单状态
     * @param tradeOrder
     */
    private void updateOrderStatus(TradeOrder tradeOrder) {
    }

    /**
     * 扣减余额
     * @param tradeOrder
     */
    private void reduceUserMoney(TradeOrder tradeOrder) {
        
    }

    /**
     * 扣减优惠券
     * @param tradeOrder
     */
    private void reduceCoupon(TradeOrder tradeOrder) {

    }

    /**
     * 扣减库存
     * @param tradeOrder
     */
    private void reduceGoodNum(TradeOrder tradeOrder) {
        
    }

    /**
     * 生成预订单
     * @param tradeOrder
     */
    private void createPreOrder(TradeOrder tradeOrder) {
    }

    /**
     * 校验订单
     * @param tradeOrder
     */
    private void checkOrder(TradeOrder tradeOrder) {
        //订单是否为空
        if(tradeOrder == null){
            CastException.cast(ShopCode.SHOP_ORDER_INVALID);
        }
        //商品是否存在

    }




}

