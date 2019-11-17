package com.jiaxi.shop.api;

import com.jiaxi.shop.pojo.TradeGoods;

public interface IGoodService {
    TradeGoods findOne(Long goodsId);
}
