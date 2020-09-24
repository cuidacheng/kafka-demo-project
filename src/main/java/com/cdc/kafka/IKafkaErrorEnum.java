package com.cdc.kafka;

/**
 * 错误码接口方法
 *
 * @author cuidc
 * @date 2020-09-21
 */
public interface IKafkaErrorEnum {
    /**
     * 获取错误码
     * @return
     */
    String getErrorCode();

    /**
     * 获取错误信息
     * @return
     */
    String getErrorMsg();
}
