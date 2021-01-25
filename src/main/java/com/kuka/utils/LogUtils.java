
package com.kuka.utils;

import com.kuka.dao.OperatorLogMapper;
import com.kuka.domain.OperatorLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LogUtils {
    @Autowired
    private OperatorLogMapper operatorLogMapper;
    public  void makeLog(String code,String msg,String type){
        OperatorLog operatorLog=new OperatorLog();
        operatorLog.setCode(code);
        operatorLog.setMsg(msg);
        operatorLog.setType(type);
        //类型 1：商品上传 2：客户上传 3：库存上传 4：订单下载 5：订单状态上传
        switch (type){
            case "1":
                operatorLog.setTypeName("商品上传");
                break;
            case "2":
                operatorLog.setTypeName("客户上传");
                break;
            case "3":
                operatorLog.setTypeName("库存上传");
                break;
            case "4":
                operatorLog.setTypeName("订单下载");
                break;
            case "5":
                operatorLog.setTypeName("订单状态上传");
                break;
        }
        operatorLog.setCreateTime(new Date());
        operatorLogMapper.insert(operatorLog);

    }
}
