package com.cdc.kafka.constant;

import com.cdc.kafka.IKafkaErrorEnum;

/**
 * 错误码和错误信息
 *
 * @author cuidc
 * @date 2020-09-11
 */

public enum KafkaErrorEnumConstant implements IKafkaErrorEnum {
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
    SEND_MESSAGE_TOPIC_EMPTY("100008", "发送消息主题不能为空"),
    SEND_MESSAGE_MESSAGE_EMPTY("100009", "向主题发送对消息不能为空"),
    RECEIVE_MESSAGE_TOPIC_EMPTY("100010", "接受信息的主题不能为空"),
    RECEIVE_MESSAGE_METHOD_EMPTY("100011", "接受主题消息关联的方法不能为空"),
    RECEIVE_MESSAGE_CLASS_EMPTY("100011", "接受主题消息关联的类不能为空"),

    ;

    KafkaErrorEnumConstant(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    private String errorCode;
    private String errorMsg;

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
