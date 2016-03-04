package com.bahaoth.zaihuangye.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bahaoth.zaihuangye.R;
import com.bahaoth.zaihuangye.user.User;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by 道谊戎 on 2016/2/27.
 */
public class LoginByAccount extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.tv_toolbar)
    TextView tv_toolbar;

    @Bind(R.id.ed_login_byphone_account)
    EditText ed_account;
    @Bind(R.id.ed_login_byphone_password)
    EditText ed_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_byaccount);
        ButterKnife.bind(this);
        tv_toolbar.setText("登录");
        tv_toolbar.setTextColor(Color.WHITE);
        toolbar.setTitle(" ");
        toolbar.inflateMenu(R.menu.base_toolbar_menu);


    }
    @OnClick(R.id.bt_login_byphone_ok)
    public void bt_login_byphone_ok(){
        String account=ed_account.getText().toString();
        String password=ed_password.getText().toString();


        if (TextUtils.isEmpty(account)) {
            showToast("账号不能为空");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            showToast("密码不能为空");
            return;
        }

        hideKeyBoard();
        final ProgressDialog progress = new ProgressDialog(LoginByAccount.this);
        progress.setMessage("正在登录中...");
        progress.setCanceledOnTouchOutside(false);
        progress.show();


        User user=new User();
        user.setUsername(account);
        user.setPassword(password);

        user.login(this, new SaveListener() {
            @Override
            public void onSuccess() {

                showToast("登陆成功");
                Intent intent = new Intent(LoginByAccount.this, MainActivity.class);
                intent.putExtra("from", "login");
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(int i, String s) {
                showToast("登陆失败" + s);
                //处理登陆失败的逻辑
                progress.dismiss();

            }
        });

    }
    @OnClick(R.id.bt_login_byphone_forgot)
    public void forgotPassword(){
        startActivity(new Intent(LoginByAccount.this,FindPasswordActivity.class));

    }
    //隐藏输入法键盘
    private void hideKeyBoard(){
        InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm.isActive()&&getCurrentFocus()!=null){
            if(getCurrentFocus().getWindowToken()!=null){
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

            }
        }
    }
}
