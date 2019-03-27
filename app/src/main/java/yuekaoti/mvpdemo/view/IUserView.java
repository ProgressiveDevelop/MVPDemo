package yuekaoti.mvpdemo.view;

/**
 * @version 1.0
 * @auth JackHappiness
 * @date 2019/3/27
 * @summary : 用户 View 接口，不关心获取数据和逻辑处理，只关注和用户交互
 */
public interface IUserView {
    //获取手机
    String getUserPhone();

    //获取密码
    String getUserPass();

    //显示对话框
    void showLoading();

    //隐藏对话框
    void hideLoading();

    /**
     * 更新视图
     *
     * @param result 结果
     */
    void updateView(String result);

    /**
     * 显示提示信息
     *
     * @param msg 信息
     */
    void showTip(String msg);
}
