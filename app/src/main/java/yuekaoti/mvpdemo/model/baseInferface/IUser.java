package yuekaoti.mvpdemo.model.baseInferface;

import yuekaoti.mvpdemo.custominterface.LoginListener;

/**
 * @version 1.0
 * @auth JackHappiness
 * @date 2019/3/27
 * @summary : 定义业务接口
 */
public interface IUser {
    void login(String userPhone, String userPass, LoginListener listener);
}
