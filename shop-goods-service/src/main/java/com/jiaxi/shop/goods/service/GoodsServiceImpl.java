package com.jiaxi.shop.goods.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.jiaxi.common.constant.ShopCode;
import com.jiaxi.common.exception.CastException;
import com.jiaxi.shop.api.IGoodService;
import com.jiaxi.shop.entity.Result;
import com.jiaxi.shop.goods.mapper.TradeGoodsMapper;
import com.jiaxi.shop.goods.mapper.TradeGoodsNumberLogMapper;
import com.jiaxi.shop.pojo.TradeGoods;
import com.jiaxi.shop.pojo.TradeGoodsNumberLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Service(interfaceClass = IGoodService.class)
public class GoodsServiceImpl implements IGoodService {
    @Autowired
    private TradeGoodsMapper tradeGoodsMapper;
    @Autowired
    private TradeGoodsNumberLogMapper tradeGoodsNumberLogMaper;
    @Override
    public TradeGoods findOne(Long goodsId) {
        return  tradeGoodsMapper.selectByPrimaryKey(goodsId);
    }

    @Override
    public Result reduceGoodsNum(TradeGoodsNumberLog goodsNumberLog) {
        if (goodsNumberLog == null ||
                goodsNumberLog.getGoodsNumber() == null ||
                goodsNumberLog.getOrderId() == null ||
                goodsNumberLog.getGoodsNumber() == null ||
                goodsNumberLog.getGoodsNumber().intValue() <= 0) {
            CastException.cast(ShopCode.SHOP_REQUEST_PARAMETER_VALID);
        }
        TradeGoods goods = tradeGoodsMapper.selectByPrimaryKey(goodsNumberLog.getGoodsId());
        if(goods.getGoodsNumber()<goodsNumberLog.getGoodsNumber()){
            //库存不足
            CastException.cast(ShopCode.SHOP_GOODS_NUM_NOT_ENOUGH);
        }
        //减库存
        goods.setGoodsNumber(goods.getGoodsNumber()-goodsNumberLog.getGoodsNumber());
        tradeGoodsMapper.updateByPrimaryKey(goods);


        //记录库存操作日志
        goodsNumberLog.setGoodsNumber(-(goodsNumberLog.getGoodsNumber()));
        goodsNumberLog.setLogTime(new Date());
        tradeGoodsNumberLogMaper.insert(goodsNumberLog);

        return new Result(ShopCode.SHOP_SUCCESS.getSuccess(),ShopCode.SHOP_SUCCESS.getMessage());
    }
}
