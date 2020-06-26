package com.stjy.baselib.common;


import android.text.TextUtils;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SPUtils;
import com.stjy.baselib.model.UserBean;


/**
 * 登录的用户
 */
public class LoginUser {

    private static final String USER_JSON = AppConstants.USER_JSON;
    private static final String USER_LOGIN = "USER_LOGIN";
    //上次登录的账号
    private volatile static LoginUser instance;
    public static LoginUser getInstance() {
        if (instance == null) {
            synchronized (LoginUser.class) {
                if (instance == null) {
                    instance = new LoginUser();
                }
            }
        }
        return instance;
    }

    /**
     * 获取登录token
     */
    public String getToken() {
        return SPUtils.getInstance(USER_JSON).getString(AppConstants.KEY_TOKEN, null);
    }

    /**
     * 保存登录token
     */
    public void setToken(String token) {
        if (!TextUtils.isEmpty(token)) {
            SPUtils.getInstance(USER_JSON).put(AppConstants.KEY_TOKEN, token);
        }
    }

    /**
     * 删除登录token
     */
    public void deleteToken() {
        SPUtils.getInstance(USER_JSON).remove(AppConstants.KEY_TOKEN);
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public UserBean getUser() {
        String userJson = SPUtils.getInstance(USER_JSON).getString(AppConstants.USER_DATA);
        return GsonUtils.fromJson(userJson, UserBean.class);
    }

    /**
     * 保存用户信息
     *
     * @param userJson
     */
    public void setUser(UserBean userJson) {
        if (userJson != null) {
            SPUtils.getInstance(USER_JSON).put(AppConstants.USER_DATA, GsonUtils.toJson(userJson));
        }
    }

    /**
     * 删除登录的用户信息
     */
    public void deleteUser() {
        SPUtils.getInstance(USER_JSON).remove(AppConstants.USER_DATA);

    }

    /**
     * 清除所有用户信息
     */
    public void clearUser() {
        SPUtils.getInstance(USER_JSON).clear();
    }

    /**
     * 设置经度
     *
     * @param longitude
     */
    public void setLongitude(float longitude) {
        SPUtils.getInstance(USER_JSON).put("longitude", longitude);
    }

    /**
     * 设置纬度
     *
     * @param latitude
     */
    public void setLatitude(float latitude) {
        SPUtils.getInstance(USER_JSON).put("latitude", latitude);
    }

    /**
     * 获取经度
     *
     * @return
     */
    public float getLongitude() {
        return SPUtils.getInstance(USER_JSON).getFloat("longitude", 0.0f);
    }

    /**
     * 获取纬度
     *
     * @return
     */
    public float getLatitude() {
        return SPUtils.getInstance(USER_JSON).getFloat("latitude", 0.0f);
    }

    /**
     * 获取是否原继续的用户
     * * @return
     */
    public boolean isOldContinueUser() {
        return SPUtils.getInstance(USER_JSON).getBoolean("isOldContinueUser");
    }

    /**
     * 设置获取是否原继续的用户
     * * @return
     */
    public void setOldContinueUser(boolean bool) {
        SPUtils.getInstance(USER_JSON).put("isOldContinueUser", bool);
    }

    /**
     * 获取继续教育是否有报名
     * * @return
     */
    public boolean isOldContinueApply() {
        return SPUtils.getInstance(USER_JSON).getBoolean("isOldContinueApply");
    }

    /**
     * 设置继续教育是否有报名
     * * @return
     */
    public void setOldContinueApply(boolean bool) {
        SPUtils.getInstance(USER_JSON).put("isOldContinueApply", bool);
    }

    /**
     * 获取继续教育是否有报名
     * * @return
     */
    public boolean isContinueApply() {
        return SPUtils.getInstance(USER_JSON).getBoolean("isContinueApply", false);
    }

    /**
     * 设置继续教育是否有报名
     * * @return
     */
    public void setContinueApply(boolean bool) {
        SPUtils.getInstance(USER_JSON).put("isContinueApply", bool);
    }

    /**
     * 获取是否有待审核的报名
     * * @return
     */
    public boolean isVerify() {
        return SPUtils.getInstance(USER_JSON).getBoolean("isVerify");
    }

    /**
     * 设置是否有待审核的报名
     * * @return
     */
    public void setVerify(boolean bool) {
        SPUtils.getInstance(USER_JSON).put("isVerify", bool);
    }

    /**
     * 获取是否显示新手指南
     * * @return
     */
    public boolean isShowGuide() {
        return SPUtils.getInstance(USER_JSON).getBoolean("isShowGuide", true);
    }

    /**
     * 设置是否显示新手指南
     * * @return
     */
    public void setShowGuide(boolean bool) {
        SPUtils.getInstance(USER_JSON).put("isShowGuide", bool);
    }


    public void setPhone(String phone) {
        SPUtils.getInstance(USER_LOGIN).put("user_phone", phone);
    }

    public void setAccount(String account) {
        SPUtils.getInstance(USER_LOGIN).put("user_account", account);
    }

    public void setLastLoginAccount(String account) {
        SPUtils.getInstance(USER_LOGIN).put("user_lastloginaccount", account);
    }

    public void setAccountPwd(String password) {
        SPUtils.getInstance(USER_LOGIN).put("user_password", password);
    }

    public String getPhone() {
        return SPUtils.getInstance(USER_LOGIN).getString("user_phone", null);
    }

    public String getAccount() {
        return SPUtils.getInstance(USER_LOGIN).getString("user_account", null);
    }

    public String getAccountPwd() {
        return SPUtils.getInstance(USER_LOGIN).getString("user_password", null);
    }

    public String getLastLoginAccount() {
        return SPUtils.getInstance(USER_LOGIN).getString("user_lastloginaccount", "");
    }
}
