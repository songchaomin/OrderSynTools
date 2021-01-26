package com.kuka;

import com.kuka.event.OrderStatusSynEvent;
import com.kuka.event.OrderSynEvent;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.kuka.dao")
@ComponentScan(basePackages = {"com.kuka.*"})
public class OrderSynTools
{
    public static void main( String[] args )
    {
        SpringApplication springApplication=new SpringApplication(OrderSynTools.class);
        ConfigurableApplicationContext context = springApplication.run(args);
        context.publishEvent(new OrderSynEvent(new Object()));
        context.publishEvent(new OrderStatusSynEvent(new Object()));
    }
}
