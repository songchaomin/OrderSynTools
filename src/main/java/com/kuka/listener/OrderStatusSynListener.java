package com.kuka.listener;

import com.kuka.domain.SchedulerJob;
import com.kuka.event.OrderStatusSynEvent;
import com.kuka.event.OrderSynEvent;
import com.kuka.scheduler.services.SchedulerService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderStatusSynListener implements ApplicationListener<OrderStatusSynEvent> {
    @Autowired
    private SchedulerService schedulerService;
    @Override
    public void onApplicationEvent(OrderStatusSynEvent event)
    {
        log.info("【订单同步状态定时任务开启中】");
        SchedulerJob schedulerJob = new SchedulerJob();
        schedulerJob.setClassName("com.kuka.scheduler.job.OrderStatusSynJob");
        schedulerJob.setJobName("orderStatusSyn");
        schedulerJob.setJobGroup("orderStatus");
        schedulerJob.setTriggerName("orderStatusSynTrigger");
        schedulerJob.setTriggerGroup("orderStatus");
        schedulerJob.setCronExpression("0 0 0/1 * * ? *");
        try {
            schedulerService.runJob(schedulerJob);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
