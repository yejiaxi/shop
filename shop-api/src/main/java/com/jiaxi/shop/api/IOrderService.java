package com.jiaxi.shop.api;

import com.jiaxi.shop.entity.Result;
import com.jiaxi.shop.pojo.TradeOrder;

public interface IOrderService {
    /**
     * 下单接口
     * @param order
     * @return
     */
    Result confirmOrder(TradeOrder tradeOrder);
}
