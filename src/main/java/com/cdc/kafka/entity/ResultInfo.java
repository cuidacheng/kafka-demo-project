package com.cdc.kafka.entity;

import com.cdc.kafka.IKafkaErrorEnum;
import com.cdc.kafka.constant.KafkaErrorEnumConstant;
import com.cdc.kafka.exception.KafkaException;
import org.springframework.stereotype.Component;

/**
 * {阐述类的作用}
 *
 * @author: cuidc
 * @date: 2020-09-22 00:58
 */

@Component
public class ResultInfo {
    private static final ThreadLocal<ResultInfoHandleEntity> resultContext = new ThreadLocal<>();

    private static void addResultContext() {
        if (resultContext.get() == null) {
            ResultInfoHandleEntity resultInfoHandleEntity = new ResultInfoHandleEntity();
            resultInfoHandleEntity.setResultInfo(KafkaErrorEnumConstant.SUCCESS);
            resultContext.set(resultInfoHandleEntity);
        }
    }

    public String getErrorCode() {
        addResultContext();
        return resultContext.get().getErrorCode();
    }

    public String getErrorMsg() {
        addResultContext();
        return resultContext.get().getErrorMsg();
    }

    public void setResultInfo(IKafkaErrorEnum errorEnum) {
        ResultInfoHandleEntity resultInfoHandleEntity = new ResultInfoHandleEntity();
        resultInfoHandleEntity.setErrorCode(errorEnum.getErrorCode());
        resultInfoHandleEntity.setErrorCode(errorEnum.getErrorMsg());
        resultContext.set(resultInfoHandleEntity);
    }

    public void setResultInfo(KafkaException kafkaException) {
        ResultInfoHandleEntity resultInfoHandleEntity = new ResultInfoHandleEntity();
        resultInfoHandleEntity.setErrorCode(kafkaException.getErrorCode());
        resultInfoHandleEntity.setErrorCode(kafkaException.getErrorMsg());
        resultContext.set(resultInfoHandleEntity);
    }
}
