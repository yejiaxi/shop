import com.alibaba.dubbo.config.annotation.Reference;
import com.jiaxi.shop.api.IGoodService;
import com.jiaxi.shop.api.IOrderService;
import com.jiaxi.shop.order.OrderServiceApplication;
import com.jiaxi.shop.order.mapper.TradeOrderMapper;
import com.jiaxi.shop.pojo.TradeGoods;

import com.jiaxi.shop.pojo.TradeOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.math.BigDecimal;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=OrderServiceApplication.class)
public class MybatisTest {
    @Autowired
    private IOrderService orderService;

    @Test
    public void confirmOrder() throws IOException {

        Long coupouId = 345988230098857984L;
        Long goodsId = 345959443973935104L;
        Long userId = 345963634385633280L;

        TradeOrder order = new TradeOrder();
        order.setGoodsId(goodsId);
        order.setUserId(userId);
        order.setCouponId(coupouId);
        order.setAddress("北京");
        order.setGoodsNumber(1);
        order.setGoodsPrice(new BigDecimal(1000));
        order.setShippingFee(BigDecimal.ZERO);
        order.setOrderAmount(new BigDecimal(1000));
        order.setMoneyPaid(new BigDecimal(100));
        orderService.confirmOrder(order);

        System.in.read();

    }


}
