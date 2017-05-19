package com.eeefan.registerandlogindemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.eeefan.registerandlogindemo.utils.AnalyticalRegistInfo;
import com.eeefan.registerandlogindemo.utils.UserBaseInfo;
import com.eeefan.registerandlogindemo.utils.ValidateUtils;

public class RegisterActivity extends BaseActivity implements HttpResponeCallBack {

    @BindView(R.id.regist_nick)
    EditText loginNick;
    @BindView(R.id.regist_account)
    EditText email;
    @BindView(R.id.regist_password)
    EditText password;
    @BindView(R.id.regist_btn)
    Button registBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.regist_btn)
    public void onViewClicked() {
        String nick = loginNick.getText().toString();
        String emailStr = email.getText().toString();
        String passwordStr = password.getText().toString();
        if (!TextUtils.isEmpty(nick) &&
                !TextUtils.isEmpty(emailStr)
                && !TextUtils.isEmpty(passwordStr)) {
            if (ValidateUtils.isEmail(emailStr)) {//验证邮箱格式是否符合

                RequestApiData.getInstance().getRegistData(nick, emailStr, passwordStr,
                        AnalyticalRegistInfo.class, RegisterActivity.this);
            } else {
                Toast.makeText(RegisterActivity.this, "输入邮箱有误", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(RegisterActivity.this, "输入信息未完全", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onResponeStart(String apiName) {
        // TODO Auto-generated method stub
        Toast.makeText(RegisterActivity.this, "正在请求数据...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoading(String apiName, long count, long current) {
        Toast.makeText(RegisterActivity.this, "Loading...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(String apiName, Object object) {
        // TODO Auto-generated method stub
        //注册接口
        if (UrlConstance.KEY_REGIST_INFO.equals(apiName)) {
            if (object != null && object instanceof AnalyticalRegistInfo) {
                AnalyticalRegistInfo info = (AnalyticalRegistInfo) object;
                String successCode = info.getRet();
                //请求成功
                if (successCode.equals(Constant.KEY_SUCCESS)) {
                    UserBaseInfo baseUser = new UserBaseInfo();
                    baseUser.setEmail(info.getEmail());
                    baseUser.setNickname(info.getNickname());
                    baseUser.setUserhead(info.getUserhead());
                    baseUser.setUserid(String.valueOf(info.getUserid()));
                    BaseApplication.getInstance().setBaseUser(baseUser);
                    UserPreference.save(KeyConstance.IS_USER_ID, String.valueOf(info.getUserid()));
                    UserPreference.save(KeyConstance.IS_USER_ACCOUNT, info.getEmail());
                    UserPreference.save(KeyConstance.IS_USER_PASSWORD, password.getText().toString());
                    UserPreference.save(KeyConstance.IS_LOGIN_KEY, true);


                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    RegisterActivity.this.startActivity(intent);

                    Toast.makeText(RegisterActivity.this, "注册成功...", Toast.LENGTH_SHORT).show();

                    RegisterActivity.this.finish();

                } else {
                    Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onFailure(String apiName, Throwable t, int errorNo, String strMsg) {
        Toast.makeText(RegisterActivity.this, "Failure", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        super.onPause();
    }


}
