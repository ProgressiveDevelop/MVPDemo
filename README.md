# MVPDemo
##### 1、MVC的缺点
>上一篇实例了解了一下MVC的使用，以及总结了MVC的缺点，项目地址：[MVCDemo](https://github.com/ProgressiveDevelop/MVCDemo)
	
>缺点：View对Model的依赖，会导致View也包含了业务逻辑；Controller的职责不断增加，以致变得庞大臃肿。
  
##### 2、MVP模式
![MVP模式图](https://raw.githubusercontent.com/ProgressiveDevelop/MVPDemo/master/img/mvp1.png)
>MVP模式是从MVC演变而来，主要解决View与Model的耦合问题，是View和Model彻底分离。
  
>MVP框架由3部分组成：View负责显示，Presenter负责逻辑处理，Model提供数据。

>View:负责绘制UI元素、与用户进行交互(在Android中体现为Activity)

>Model:负责存储、检索、操纵数据(有时也实现一个Model interface用来降低耦合)

>Presenter:作为View与Model交互的中间纽带，处理与用户交互的负责逻辑。

MVP的Presenter是框架的控制者，承担了大量的逻辑操作,将Activity复杂的逻辑处理移至类（Presenter）中时，Activity其实就是MVP模式中的View，它负责UI元素的初始化，建立UI元素与Presenter的关联（Listener之类），同时自己也会处理一些简单的逻辑（复杂的逻辑交由 Presenter处理）。

##### 3、MVP通信流程
>各部分之间的通信，都是双向的，View 与 Model 不发生联系，而是通过与Presenter交互来与Model间接交互；Presenter与View的交互是通过接口来进行的，因为更有利于添加单元测试。

+ 1、View接受用户的交互请求

+ 2、View将请求转交给Presenter

+ 3、Presenter操作Model进行数据库更新

+ 4、数据更新之后，Model通知Presenter数据发生变化

+ 5、Presenter更新View的数据

##### 4、MVP开发中的变种
>在开发中，MVP的实现一部分倾向于在View中放置简单的逻辑，在Presenter放置复杂的逻辑；另一部分倾向于在Presenter中放置全部的逻辑。这两种分别被称为：Passive View(被动视图)和Superivising Controller(超级控制器)。

>在Passive View中，为了减少UI组件的行为，使用Controller不仅控制用户事件的响应，而且将结果更新到View上。可以集中测试Controller，减小View出问题的风险。

>在Superivising Controller中的Controller既处理用户输入的响应，又操作View处理View的复杂逻辑。

>其中使用最广泛的是Passive View模式，即被动视图。在这种模式下，View和Model之间不能直接交互，View通过Presenter与Model打交道。Presenter接受View的UI请求，完成简单的UI处理逻辑，并调用Model进行业务处理，并调用View将相应的结果反映出来。View直接依赖Presenter，但是Presenter间接依赖View，它直接依赖的是View实现的接口。

>在Passive View的模式中，View是被动的，Presenter是主动的，View仅仅是用户交互请求的汇报者，响应用户交互相关的逻辑和流程；对于绑定到View上的数据，是Presenter主动“推”给View的；Presenter才是整个体系的协调者，它根据处理用于交互的逻辑给View和Model安排工作。

##### 5、MVP实例(基于[MVCDemo](https://github.com/ProgressiveDevelop/MVCDemo)的改进)
![MVP实例图](https://raw.githubusercontent.com/ProgressiveDevelop/MVPDemo/master/img/mvp_demo.png)
  
>模拟用户登录：当手机界面上，用户点击登录按钮，获取用户输入的账号和密码后，提交数据到服务器，服务器处理完成后响应，显示用户登录的结果。

```
//定义业务接口
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

```
```
//定义Model实现
package yuekaoti.mvpdemo.model;

import android.text.TextUtils;

import yuekaoti.mvpdemo.custominterface.LoginListener;
import yuekaoti.mvpdemo.model.baseInferface.IUser;

/**
 * @version 1.0
 * @auth JackHappiness
 * @date 2019/3/27
 * @summary : 用户业务逻辑具体实现
 */
public class UserModel implements IUser {
    /**
     * 用户登录
     *
     * @param userPhone 手机
     * @param userPass  密码
     * @param listener  监听器
     */
    @Override
    public void login(String userPhone, String userPass, LoginListener listener) {
        if (TextUtils.isEmpty(userPhone)) {
            listener.fail("手机号不能为空");
        } else {
            if (TextUtils.isEmpty(userPass)) {
                listener.fail("密码不能为空不能为空");
            } else {
                //网络登录
                int code = postLogin(userPhone, userPass);
                if (code == 200) {
                    listener.success("登录成功");
                } else {
                    listener.fail("登录失败,请稍候重试");
                }
            }
        }
    }

    /**
     * 网络登录
     *
     * @param userPhone 手机
     * @param userPass  密码
     * @return 结果码
     */
    private int postLogin(String userPhone, String userPass) {
        return 200;
    }
}

```
```
//定义View 接口
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

```
```
//定义Presenter，连接View和Model
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

```
```
//View 实现
package yuekaoti.mvpdemo.view;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import yuekaoti.mvpdemo.R;
import yuekaoti.mvpdemo.presenter.UserPresenter;

/**
 * MVP 实例
 */
public class MainActivityFragment extends Fragment implements View.OnClickListener, IUserView {
    //View
    private View view;
    private EditText etPhone, etPass;
    private TextView tvResult;
    //Presenter
    private UserPresenter userPresenter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //创建Presenter
        userPresenter = new UserPresenter(this);
        //初始化控件
        etPhone = view.findViewById(R.id.etPhone);
        etPass = view.findViewById(R.id.etPass);
        Button btnLogin = view.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        tvResult = view.findViewById(R.id.tvResult);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //登录 view
            case R.id.btnLogin:
                userPresenter.login();
                break;
            default:
                break;
        }
    }

    @Override
    public String getUserPhone() {
        return etPhone.getText().toString().trim();
    }

    @Override
    public String getUserPass() {
        return etPass.getText().toString().trim();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void updateView(String result) {
        tvResult.setText(getString(R.string.text_login_result) + ":" + result);
    }

    @Override
    public void showTip(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }
}

```
简单分析：从代码中可以看出，Activity从Controller中解放出来，没有复杂的逻辑代码，只负责与用户的交互；Model(UserModel)的改动不会影响Activity，比如随意更换网络请求框架而Activity无需修改；

##### 6、MVP优点
>1、View(Activity)和Model解耦，互不影响

>2、方便进行对Presenter单元测试

##### 7、MVP的进一步演进 MVVM

##### 8、参考资料
[Android App的设计架构：MVC,MVP,MVVM与架构经验谈](https://www.tianmaying.com/tutorial/AndroidMVC)

[mvc mvp mvvp区别](https://wenzongliang.iteye.com/blog/2240284)