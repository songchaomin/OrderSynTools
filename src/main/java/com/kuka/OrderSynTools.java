package com.kuka;

import com.kuka.config.PropertiesConfig;
import com.kuka.event.OrderStatusSynEvent;
import com.kuka.event.OrderSynEvent;
import com.kuka.utils.EncrypAES;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;
import java.util.Objects;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.kuka.dao")
@ComponentScan(basePackages = {"com.kuka.*"})
@EnableConfigurationProperties(PropertiesConfig.class)
public class OrderSynTools
{
    public static void main( String[] args )
    {
        SpringApplication springApplication=new SpringApplication(OrderSynTools.class);
        ConfigurableApplicationContext context = springApplication.run(args);
        String sn = PropertiesConfig.sn;
        try {
            String md5 = EncrypAES.md5(EncrypAES.getLocalMac());
            if (!Objects.equals(sn,md5)){
                System.out.println("系统没有注册不允许操作，即将退出");
                System.exit(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        context.publishEvent(new OrderSynEvent(new Object()));
        context.publishEvent(new OrderStatusSynEvent(new Object()));
    }
}
