package com.eeefan.registerandlogindemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.eeefan.registerandlogindemo.*;
import com.eeefan.registerandlogindemo.base.BaseActivity;
import com.eeefan.registerandlogindemo.base.BaseApplication;
import com.eeefan.registerandlogindemo.http.HttpResponeCallBack;
import com.eeefan.registerandlogindemo.http.RequestApiData;
import com.eeefan.registerandlogindemo.utils.UserBaseInfo;
import com.eeefan.registerandlogindemo.utils.ValidateUtils;

public class LoginActivity extends BaseActivity implements HttpResponeCallBack {

    @BindView(R.id.login_account)
    EditText loginAccount;
    @BindView(R.id.login_password)
    EditText loginPassword;
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.register_btn)
    Button registerBtn;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        String userAccount = UserPreference.read(KeyConstance.IS_USER_ACCOUNT, null);//软件还没有保持账号
        String userPassword = UserPreference.read(KeyConstance.IS_USER_PASSWORD, null);

        if (!TextUtils.isEmpty(userAccount)) {
            loginAccount.setText(userAccount);
            loginPassword.setText(userPassword);
        }
    }

    @OnClick(R.id.login_btn)
    public void onLoginBtnClicked() {
        String account = loginAccount.getText().toString();//账号
        String password = loginPassword.getText().toString();//密码
        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(password)
                && ValidateUtils.isEmail(account)) {
            RequestApiData.getInstance().getLoginData(account, password, UserBaseInfo.class, LoginActivity.this);
        } else {
            Toast.makeText(LoginActivity.this, "账号或者密码有误", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.register_btn)
    public void onRegisterBtnClicked() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        LoginActivity.this.startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void onResponeStart(String apiName) {
        // TODO Auto-generated method stub

        if (UrlConstance.KEY_LOGIN_INFO.equals(apiName)) {
            Toast.makeText(LoginActivity.this, "正在加载数据中", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoading(String apiName, long count, long current) {
        // TODO Auto-generated method stub
        Toast.makeText(LoginActivity.this, "Loading...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(String apiName, Object object) {
        // TODO Auto-generated method stub
        if (UrlConstance.KEY_LOGIN_INFO.equals(apiName)) {
            //邮箱登录返回数据
            if (object != null && object instanceof UserBaseInfo) {
                UserBaseInfo info = (UserBaseInfo) object;
                if (info.getRet().equals(Constant.KEY_SUCCESS)) {

                    //登录成功，保存登录信息
                    BaseApplication.getInstance().setBaseUser(info);//保存到Application中

                    //保存到SP中
                    UserPreference.save(KeyConstance.IS_USER_ID, String.valueOf(info.getUserid()));

                    UserPreference.save(KeyConstance.IS_USER_ACCOUNT, info.getEmail());
                    UserPreference.save(KeyConstance.IS_USER_PASSWORD, loginPassword.getText().toString());
                    UserPreference.save(KeyConstance.IS_LOGIN_KEY, true);


                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
//                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();

                } else {
                    Log.e("TAG", "info=" + info.toString());
                    if (info.getErrcode().equals(Constant.KEY_NO_REGIST)) {
                        Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, info.getMsg(), Toast.LENGTH_SHORT).show();
                        Log.e("TAG", "info.getMsg()=" + info.getMsg());
                    }

                }
            }
        }
    }

    @Override
    public void onFailure(String apiName, Throwable t, int errorNo,
                          String strMsg) {
        // TODO Auto-generated method stub
        Toast.makeText(LoginActivity.this, "Failure", Toast.LENGTH_SHORT).show();
    }



}
