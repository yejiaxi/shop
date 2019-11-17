package com.jiaxi.shop.goods.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.jiaxi.shop.api.IGoodService;
import com.jiaxi.shop.goods.mapper.TradeGoodsMapper;
import com.jiaxi.shop.pojo.TradeGoods;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class GoodsServiceImpl implements IGoodService {
    @Autowired
    private TradeGoodsMapper tradeGoodsMapper;
    @Override
    public TradeGoods findOne(Long goodsId) {
        return  tradeGoodsMapper.selectByPrimaryKey(goodsId);
    }
}
