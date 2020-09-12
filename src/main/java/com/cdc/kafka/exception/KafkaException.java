package com.cdc.kafka.exception;

import com.cdc.kafka.constant.KafkaErrorEnumConstant;

/**
 * 异常抛出
 *
 * @author cuidc
 * @date 2020-09-10
 */
public class KafkaException extends Exception {
    private String errorCode;
    private String errorMsg;

    public KafkaException() {
        super();
    }

    public KafkaException(String errorCode, String errorMsg) {
        super();
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public KafkaException(String errorMsg, Throwable cause) {
        super(errorMsg, cause);
        this.errorMsg = errorMsg;
    }

    public KafkaException(KafkaErrorEnumConstant kafkaErrorEnumConstant) {
        super();
        this.errorCode = kafkaErrorEnumConstant.getErrorCode();
        this.errorMsg = kafkaErrorEnumConstant.getErrorMsg();
    }

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
}
