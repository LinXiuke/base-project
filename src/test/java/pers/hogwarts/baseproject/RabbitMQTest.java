package pers.hogwarts.baseproject;

import com.zero.common.rabbit.Product;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description:
 * @Author: Hogwarts
 * @Date: 2018/12/13
 */


public class RabbitMQTest {

    @Autowired
    private Product product;

    @Test
    public void send() {
        for (int i = 0; i < 10; i++) {
            product.send1("t1--" + i);
            product.send2("t2--" + i);
        }
    }
}
