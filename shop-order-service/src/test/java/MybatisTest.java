import com.jiaxi.shop.order.OrderServiceApplication;
import com.jiaxi.shop.order.mapper.TradeOrderMapper;
import com.jiaxi.shop.pojo.TradeOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=OrderServiceApplication.class)
public class MybatisTest {
    @Autowired
    private TradeOrderMapper tradeOrderMapper;

    @Test
    public void test1(){
        TradeOrder tradeOrder = new TradeOrder();
        tradeOrder.setOrderId(123456L);
        tradeOrder.setAddress("孝感市叶庙村");
        tradeOrder.setGoodsId(12345L);
        tradeOrder.setGoodsPrice(new BigDecimal(12.12));
        int cnt = tradeOrderMapper.insert(tradeOrder);
        System.out.println(cnt);
    }


}
