package com.jiaxi.shop.api;

import com.jiaxi.shop.entity.Result;
import com.jiaxi.shop.pojo.TradeGoods;
import com.jiaxi.shop.pojo.TradeGoodsNumberLog;

public interface IGoodService {


    /**
     * 根据ID查询商品对象
     * @param goodsId
     * @return
     */
    TradeGoods findOne(Long goodsId);

    /**
     * 扣减库存
     * @param goodsNumberLog
     * @return
     */
    Result reduceGoodsNum(TradeGoodsNumberLog goodsNumberLog);
}
