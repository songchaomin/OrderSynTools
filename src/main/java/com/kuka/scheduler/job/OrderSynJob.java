package com.kuka.scheduler.job;

import com.kuka.services.SalOrderService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderSynJob implements Job {
    @Autowired
    private SalOrderService salOrderService;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("开始同步订单");
        salOrderService.synOrder();
        log.info("同步订单结束");
    }
}
