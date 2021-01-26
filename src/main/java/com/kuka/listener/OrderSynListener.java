package com.kuka.listener;

import com.kuka.domain.SchedulerJob;
import com.kuka.event.OrderSynEvent;
import com.kuka.scheduler.services.SchedulerService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderSynListener implements ApplicationListener<OrderSynEvent> {
    @Autowired
    private SchedulerService schedulerService;
    @Override
    public void onApplicationEvent(OrderSynEvent event)
    {
        log.info("【订单同步定时任务开启中】");
        SchedulerJob schedulerJob = new SchedulerJob();
        schedulerJob.setClassName("com.kuka.scheduler.job.OrderSynJob");
        schedulerJob.setJobName("orderSyn");
        schedulerJob.setJobGroup("order");
        schedulerJob.setTriggerName("orderSynTrigger");
        schedulerJob.setTriggerGroup("order");
        schedulerJob.setCronExpression("0 0/30 * * * ? *");
        try {
            schedulerService.runJob(schedulerJob);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
