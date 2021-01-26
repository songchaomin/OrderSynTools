package com.kuka.services;

import com.kuka.domain.OperatorLog;
import com.kuka.domain.ResultPageDto;

import java.util.List;


public interface OperatorLogService {
    ResultPageDto<List<OperatorLog>> queryOperatorLog(ResultPageDto pageDto);
}
