package com.eeefan.registerandlogindemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.eeefan.registerandlogindemo.*;
import com.eeefan.registerandlogindemo.base.BaseActivity;
import com.eeefan.registerandlogindemo.base.BaseApplication;
import com.eeefan.registerandlogindemo.utils.UserBaseInfo;

public class MainActivity extends BaseActivity {

    private UserBaseInfo baseInfo = null;
    private TextView userNameTv = null;
    private Button logoutBtn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        setUserInfo();
        logoutBtn = (Button) findViewById(R.id.btn_main_logout);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }

    private void setUserInfo() {
        baseInfo = BaseApplication.getInstance().getBaseUser();
        userNameTv = (TextView) findViewById(R.id.tv_main_username);
        userNameTv.setText(baseInfo.getNickname());
    }

    private void logout() {
        UserPreference.save(KeyConstance.IS_LOGIN_KEY, false);
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, com.eeefan.registerandlogindemo.activity.LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
//        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }
}
