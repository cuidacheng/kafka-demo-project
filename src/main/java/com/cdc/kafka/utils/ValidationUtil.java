package com.cdc.kafka.utils;

import java.util.List;

/**
 * 验证判定
 *
 * @author cuidc
 * @date 2020-09-12
 *
 */
public class ValidationUtil {

    /**
     * 数组为空判定
     *
     * @param list
     * @return
     */
    public static boolean emptyList(List list) {
        if (list == null || list.isEmpty()) {
            return true;
        }
        return false;
    }
}
