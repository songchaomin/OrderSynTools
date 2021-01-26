package com.kuka.controller;

import com.kuka.domain.OperatorLog;
import com.kuka.domain.ResultPageDto;
import com.kuka.domain.ResultDto;
import com.kuka.services.OperatorLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class SysLogController {

    @Autowired
    private OperatorLogService logService;

    @ResponseBody
    @PostMapping("log/operatorLog")
    public ResultPageDto<List<OperatorLog>> operatorLog(@RequestBody ResultPageDto pageDto) {
        return logService.queryOperatorLog(pageDto);
    }
}
