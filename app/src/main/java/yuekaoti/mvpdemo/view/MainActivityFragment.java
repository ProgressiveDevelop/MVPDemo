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

