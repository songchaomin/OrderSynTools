package com.kuka.listener;

import com.kuka.domain.SchedulerJob;
import com.kuka.event.BackOrderStatusSynEvent;
import com.kuka.scheduler.services.SchedulerService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BackOrderStatusSynListener implements ApplicationListener<BackOrderStatusSynEvent> {
    @Autowired
    private SchedulerService schedulerService;
    @Override
    public void onApplicationEvent(BackOrderStatusSynEvent event)
    {
        log.info("【订单同步状态定时任务开启中】");
        SchedulerJob schedulerJob = new SchedulerJob();
        schedulerJob.setClassName("com.kuka.scheduler.job.BackOrderStatusJob");
        schedulerJob.setJobName("backorderStatusSyn");
        schedulerJob.setJobGroup("backorderStatus");
        schedulerJob.setTriggerName("backorderStatusSynTrigger");
        schedulerJob.setTriggerGroup("backorderStatus");
        schedulerJob.setCronExpression("0 0 0/1 * * ? *");
        try {
            schedulerService.runJob(schedulerJob);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
