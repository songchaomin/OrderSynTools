package com.kuka.controller;

import cn.hutool.core.date.DateUtil;
import com.kuka.domain.SchedulerJob;
import com.kuka.scheduler.services.SchedulerService;
import com.kuka.services.SpkfkService;
import com.kuka.utils.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
public class SynInventoryController {

    @Autowired
    private SpkfkService spkfkService;
    /**
     * 启动售前订单自动流转定时任务
     */
    @ResponseBody
    @GetMapping("synInventory/start")
    public void startOrderAssign() {

        spkfkService.synItems();
    }



}
