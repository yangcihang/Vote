/*
 * Copyright (c) 2017. www.hrsoft.net  Inc. All rights reserved.
 */

package net.hrsoft.voter.network;

import android.content.Context;

import net.hrsoft.voter.VoterApplication;
import net.hrsoft.voter.util.ToastUtil;

/**
 * API请求错误码统一处理
 *
 * @author yuanzeng
 * @since 17/1/22 下午11:17
 */

public final class GlobalAPIErrorHandler {
    public static void handle(Context context, int code) {
        switch (code) {
            default:
                ToastUtil.showToast(context, "未知错误", code);
                break;
        }
    }
}
