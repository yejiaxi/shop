package com.jiaxi.shop.api;

import com.jiaxi.shop.entity.Result;
import com.jiaxi.shop.pojo.TradeUser;
import com.jiaxi.shop.pojo.TradeUserMoneyLog;

public interface IUserService {
    TradeUser findOne(Long userId);
    Result updateMoneyPaid(TradeUserMoneyLog userMoneyLog);
}
