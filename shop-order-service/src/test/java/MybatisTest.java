import com.alibaba.dubbo.config.annotation.Reference;
import com.jiaxi.shop.api.IGoodService;
import com.jiaxi.shop.order.OrderServiceApplication;
import com.jiaxi.shop.order.mapper.TradeOrderMapper;
import com.jiaxi.shop.pojo.TradeGoods;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=OrderServiceApplication.class)
public class MybatisTest {
    @Autowired
    private TradeOrderMapper tradeOrderMapper;

    @Reference
    private IGoodService goodService;
    @Test
    public void test1(){
       /* TradeOrder tradeOrder = new TradeOrder();
        tradeOrder.setOrderId(123456L);
        tradeOrder.setAddress("孝感市叶庙村");
        tradeOrder.setGoodsId(12345L);
        tradeOrder.setGoodsPrice(new BigDecimal(12.12));
        int cnt = tradeOrderMapper.insert(tradeOrder);
        System.out.println(cnt);*/
    }

    @Test
    public void findGoods(){
        TradeGoods tradeGoods = goodService.findOne(345959443973935104L);
        System.out.println(tradeGoods.getGoodsName());
    }


}
