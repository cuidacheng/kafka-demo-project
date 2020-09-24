package com.cdc.kafka.entity;

import com.cdc.kafka.IKafkaErrorEnum;
import com.cdc.kafka.constant.KafkaErrorEnumConstant;

/**
 * {阐述类的作用}
 *
 * @author: cuidc
 * @date: 2020-09-22 00:49
 */

public class ResultInfoHandleEntity {
    private String errorCode;
    private String errorMsg;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public ResultInfoHandleEntity() {
        this.errorCode = KafkaErrorEnumConstant.SUCCESS.getErrorCode();
        this.errorMsg = KafkaErrorEnumConstant.SUCCESS.getErrorMsg();
    }

    public void setResultInfo(IKafkaErrorEnum kafkaErrorEnum) {
        this.errorCode = kafkaErrorEnum.getErrorCode();
        this.errorMsg = kafkaErrorEnum.getErrorMsg();
    }
}
