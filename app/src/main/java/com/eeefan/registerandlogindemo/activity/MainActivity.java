package com.eeefan.registerandlogindemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.eeefan.registerandlogindemo.KeyConstance;
import com.eeefan.registerandlogindemo.R;
import com.eeefan.registerandlogindemo.UserPreference;
import com.eeefan.registerandlogindemo.base.BaseActivity;
import com.eeefan.registerandlogindemo.base.BaseApplication;
import com.eeefan.registerandlogindemo.utils.UserBaseInfo;

public class MainActivity extends BaseActivity {
    @BindView(R.id.tv_main_username)
    TextView userNameTv;
    @BindView(R.id.btn_main_logout)
    Button logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        setUserInfo();
    }

    private void setUserInfo() {
        UserBaseInfo baseInfo = BaseApplication.getInstance().getBaseUser();
        userNameTv.setText(baseInfo.getNickname());
    }

    private void logout() {
        UserPreference.save(KeyConstance.IS_LOGIN_KEY, false);
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
//        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }

    @OnClick(R.id.btn_main_logout)
    public void onViewClicked() {
        logout();
    }
}
