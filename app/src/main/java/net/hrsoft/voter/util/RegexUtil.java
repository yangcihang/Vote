/*
 * Copyright (c) 2017. www.hrsoft.net  Inc. All rights reserved.
 */

package net.hrsoft.voter.util;


import net.hrsoft.voter.constant.Config;

import java.util.regex.Pattern;

/**
 * 正则工具类
 *
 * @author yuanzeng
 * @since 17/1/24 下午7:19
 */
public class RegexUtil {
    public static boolean isMobileNum(String mobile) {
        return Pattern.matches(Config.MOBILE_REGEX, mobile);
    }
}
