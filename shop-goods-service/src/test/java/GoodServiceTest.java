import com.jiaxi.shop.goods.GoodsServiceApplication;
import com.jiaxi.shop.goods.mapper.TradeGoodsMapper;
import com.jiaxi.shop.pojo.TradeGoods;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=GoodsServiceApplication.class)
public class GoodServiceTest {
    @Autowired
    private TradeGoodsMapper tradeGoodsMapper ;

    @Test
    public  void findOnde(){
        TradeGoods tradeGoods = tradeGoodsMapper.selectByPrimaryKey(345959443973935104L);
        System.out.println(tradeGoods.getGoodsName());
        System.out.println("hahah");

    }

}
