package com.kuka.controller;

import com.kuka.domain.ResultDto;
import com.kuka.services.SpkfkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class SynProductController {

    @Autowired
    private SpkfkService spkfkService;
    /**
     * 同步料品信息
     */
    @ResponseBody
    @GetMapping("spkfk/synItems")
    public ResultDto synItems() {
        ResultDto resultDto = spkfkService.synItems();
        return resultDto;
    }



}
