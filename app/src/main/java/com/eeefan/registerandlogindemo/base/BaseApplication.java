package com.eeefan.registerandlogindemo.base;

import com.eeefan.registerandlogindemo.http.RequestApiData;
import com.eeefan.registerandlogindemo.utils.UserBaseInfo;

/**
 * @author wjl IT蓝豹 ItLanBaoApplication主要作用是处理一些app全局变量，
 */
public class BaseApplication extends BaseLibApplication {

    private UserBaseInfo baseUser;//用户基本信息

    private RequestApiData requestApi;
    private static BaseApplication instance;

    // 渠道号
    private String fid = "";
    // 版本号
    private String version = "";

    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);
        requestApi = RequestApiData.getInstance();
    }

    public static void setInstance(BaseApplication instance) {
        BaseApplication.instance = instance;
    }


    /**
     * 设置用户基本信息
     *
     * @param baseUser
     */
    public void setBaseUser(UserBaseInfo baseUser) {
        this.baseUser = baseUser;
    }

    public UserBaseInfo getBaseUser() {
        return this.baseUser;
    }


    /**
     * 获取ItLanBaoApplication实例
     *
     * @return
     */
    public static BaseApplication getInstance() {
        return instance;
    }

}
