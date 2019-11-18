package com.jiaxi.shop.user.mapper;

import com.jiaxi.shop.pojo.TradeUserMoneyLog;
import com.jiaxi.shop.pojo.TradeUserMoneyLogExample;
import com.jiaxi.shop.pojo.TradeUserMoneyLogKey;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface TradeUserMoneyLogMapper {
    int countByExample(TradeUserMoneyLogExample example);

    int deleteByExample(TradeUserMoneyLogExample example);

    int deleteByPrimaryKey(TradeUserMoneyLogKey key);

    int insert(TradeUserMoneyLog record);

    int insertSelective(TradeUserMoneyLog record);

    List<TradeUserMoneyLog> selectByExample(TradeUserMoneyLogExample example);

    TradeUserMoneyLog selectByPrimaryKey(TradeUserMoneyLogKey key);

    int updateByExampleSelective(@Param("record") TradeUserMoneyLog record, @Param("example") TradeUserMoneyLogExample example);

    int updateByExample(@Param("record") TradeUserMoneyLog record, @Param("example") TradeUserMoneyLogExample example);

    int updateByPrimaryKeySelective(TradeUserMoneyLog record);

    int updateByPrimaryKey(TradeUserMoneyLog record);
}