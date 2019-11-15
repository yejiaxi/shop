package com.jiaxi.shop.api;

import com.jiaxi.shop.entity.Result;
import com.jiaxi.shop.pojo.TradeOrder;

public interface IOrderService {
    Result confirmOrder(TradeOrder tradeOrder);
}
