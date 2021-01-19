package com.kuka.controller;

import com.kuka.domain.ResultDto;
import com.kuka.services.MchkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class SynCustormerController {

    @Autowired
    private MchkService mchkService;
    /**
     * 同步客户信息
     */
    @ResponseBody
    @GetMapping("mchk/synCustomer")
    public ResultDto synCustomer() {
        ResultDto resultDto = mchkService.synCustomer();
        return resultDto;
    }



}
