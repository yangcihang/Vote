/*
 * Copyright (c) 2017. www.hrsoft.net  Inc. All rights reserved.
 */

package net.hrsoft.voter.network;

/**
 * API 错误码
 *
 * @author yuanzeng
 * @since 17/1/22 下午6:40
 */
public class APICode {
    public static final int SERVER_RESPONSE_CODE = 400;

    public static final int SUCCESS = 0;
    public static final int BadRequest = 10000;
    public static final int PasswordError = 10001;
    public static final int IllegalMobile = 10002;
    public static final int IllegalClient = 10003;
    public static final int IllegalType = 10004;
    public static final int IllegalVerifyCode = 10005;
    public static final int VerifyCodeTimeOut = 10006;
    public static final int IllegalBindingEmail = 10007;
    public static final int EmailSentMistake = 10008;
    public static final int UserNotExist = 20001;
    public static final int UserExisted = 20003;
    public static final int NeedVerifyQA = 20004;
    public static final int VerifyQAMistake = 20005;
    public static final int EmailExisted = 20006;
    public static final int NeedLogin = 20007;
    public static final int TokenExpires = 20008;
    public static final int QAIsNull = 20009;
    public static final int PushSettingsIsNull = 20010;
    public static final int UserNotThisLock = 20011;
    public static final int UserHasThisLock = 20012;
    public static final int EmailIsNull = 20013;
    public static final int NoRelationExisted = 20014;
    public static final int SecureCodeTimeOut = 20015;
    public static final int LockNotExist = 30001;
    public static final int IllegalBinding = 30002;
    public static final int IllegalSerial = 30003;
    public static final int RepeatBinding = 30004;
    public static final int IllegalAuthSeed = 30005;
    public static final int BindNotExist = 30006;
    public static final int NoValidTmpPassword = 30007;
    public static final int LockHasLocked = 30008;
    public static final int ShareKeyNotExist = 40001;
    public static final int IllegalShare = 40002;
    public static final int ForeverKeyExisted = 40003;
    public static final int ShareKeyNoTimes = 40004;
    public static final int ShareKeyTimeOut = 40005;
    public static final int HasTempShareKey = 40006;
}
