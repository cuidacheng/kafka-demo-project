package com.cdc.kafka.constant;

/**
 * 错误码和错误信息
 *
 * @author cuidc
 * @date 2020-09-11
 */

public enum KafkaErrorEnumConstant {
    SUCCESS("0", "成功"),
    EXCEPTION_FAIL("999999", "未知异常"),
    EXCEPTION_TIMEOUT("999998", "超时异常"),
    EXCEPTION_EXECUTION("999997", "执行异常"),
    EXCEPTION_INTERRUPT("999996", "中断异常"),
    TOPIC_ENTITY_NULL("100001", "创建主题实体为null"),
    TOPIC_NAME_EMPTY("100002", "topic名称不能为空"),
    TOPIC_NAME_EXIST("100003", "topic名称已存在"),
    TOPIC_PARTITION_INVALID("100004", "topic分区设置无效，不能为null或小于0"),
    TOPIC_REPLICATION_INVALID("100005", "topic副本设置无效，不能为null或小于0"),
    TOPIC_DELETE_TOPIC_EMPTY("100006", "需要删除的topic为空"),
    TOPIC_EMPTY_EXIST("100007", "topic为空"),

    ;

    KafkaErrorEnumConstant(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

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
}
