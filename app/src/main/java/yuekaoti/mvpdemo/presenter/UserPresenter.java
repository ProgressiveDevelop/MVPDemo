package yuekaoti.mvpdemo.presenter;

import yuekaoti.mvpdemo.custominterface.LoginListener;
import yuekaoti.mvpdemo.model.UserModel;
import yuekaoti.mvpdemo.view.IUserView;

/**
 * @version 1.0
 * @auth JackHappiness
 * @date 2019/3/27
 * @summary : 连接用户登录View与Model
 */
public class UserPresenter {
    private IUserView userView;
    private UserModel userModel;

    public UserPresenter(IUserView userView) {
        this.userView = userView;
        this.userModel = new UserModel();
    }

    /**
     * 登录
     */
    public void login() {
        userView.showLoading();
        userModel.login(userView.getUserPhone(), userView.getUserPass(), new LoginListener() {
            @Override
            public void success(String result) {
                userView.hideLoading();
                userView.updateView(result);
            }

            @Override
            public void fail(String errorMsg) {
                userView.hideLoading();
                userView.showTip(errorMsg);
            }
        });
    }
}
