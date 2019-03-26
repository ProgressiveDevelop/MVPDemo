package yuekaoti.mvpdemo;

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

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements View.OnClickListener {
    //View
    private View view;
    private EditText etPhone, etPass;
    private TextView tvResult;
    //Model


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
                String phone = etPhone.getText().toString().trim();
                String pass = etPass.getText().toString().trim();

                break;
            default:
                break;
        }
    }

    /**
     * 更新结果
     *
     * @param msg 结果信息
     */
    @SuppressLint("SetTextI18n")
    private void updateResult(String msg) {
        tvResult.setText(getString(R.string.text_login_result) + ":" + msg);
    }
}

