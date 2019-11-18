package com.jiaxi.shop.pay.service;

import com.jiaxi.shop.api.IPayService;
import com.jiaxi.shop.entity.Result;
import com.jiaxi.shop.pojo.TradePay;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;

public class PayServiceImpl implements IPayService {
    @Override
    public Result createPayment(TradePay tradePay) {
        return null;
    }

    @Override
    public Result callbackPayment(TradePay tradePay) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        return null;
    }
}
