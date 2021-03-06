/*
 * Copyright (c) 2017. www.hrsoft.net  Inc. All rights reserved.
 */

package net.hrsoft.voter;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import net.hrsoft.voter.account.activity.LoginActivity;
import net.hrsoft.voter.account.model.User;
import net.hrsoft.voter.constant.CacheKey;
import net.hrsoft.voter.util.CacheUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Application 类
 *
 * @author yuanzeng
 * @since 17/1/22 上午11:21
 * email yuanzeng@hrsoft.net
 */

public class VoterApplication extends Application {

    public static final boolean DEBUG = BuildConfig.DEBUG;
    private static net.hrsoft.voter.VoterApplication instance;
    private static CacheUtil cache;
    private static List<Activity> activityList = new ArrayList<>();
    private static long currentLockId;
    private static final String TAG = "VoterApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
    }


    /**
     * 获取 Application 实例
     *
     * @return application 实例
     */
    public static net.hrsoft.voter.VoterApplication getInstance() {
        return instance;
    }

    /**
     * 获取缓存实例
     *
     * @return 缓存类实例
     */
    public static CacheUtil getCache() {
        if (cache == null) {
            cache = CacheUtil.get(VoterApplication.getInstance().getCacheDir());
        }
        return cache;
    }

    /**
     * activity生命周期回调方法
     */
    private ActivityLifecycleCallbacks mActivityLifecycleCallbacks = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            addActivity(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            removeActivity(activity);
        }
    };

    /**
     * 添加Activity
     *
     * @param activity activity
     */
    private void addActivity(Activity activity) {
        activityList.add(activity);
    }

    /**
     * 移除Activity
     *
     * @param activity activity
     */
    private void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    /**
     * 清除所有Activity
     */
    public void clearAllActivity() {
        for (Activity activity : activityList) {
            if (activity != null && !activity.isFinishing())
                activity.finish();
        }
    }

    /**
     * 清除当前所有Activity，并跳转到登录页面
     */
    public void goLoginActivity() {
        clearAllActivity();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * 退出应用
     */
    public void exitApp() {
        clearAllActivity();
    }

    /**
     * 获取当前的用户信息
     *
     * @return User
     */
    public User getUser() {
        try {
            User user = (User) getCache().getSerializableObj(CacheKey.USER);
            if (user != null) {
                return user;
            } else {
                goLoginActivity();
            }
        } catch (Exception e) {
            goLoginActivity();
        }
        return null;
    }


}
