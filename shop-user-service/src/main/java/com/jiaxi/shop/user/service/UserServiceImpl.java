package com.jiaxi.shop.user.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.jiaxi.shop.api.IUserService;
import com.jiaxi.shop.entity.Result;
import com.jiaxi.shop.pojo.TradeUser;
import com.jiaxi.shop.pojo.TradeUserMoneyLog;
import org.springframework.stereotype.Component;


@Component
@Service(interfaceClass = IUserService.class)
public class UserServiceImpl implements IUserService {
    @Override
    public TradeUser findOne(Long userId) {
        return null;
    }

    @Override
    public Result updateMoneyPaid(TradeUserMoneyLog userMoneyLog) {
        return null;
    }
}
