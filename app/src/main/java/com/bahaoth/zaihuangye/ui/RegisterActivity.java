package com.bahaoth.zaihuangye.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bahaoth.zaihuangye.R;
import com.bahaoth.zaihuangye.user.User;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by 道谊戎 on 2016/2/27.
 */
public class RegisterActivity extends BaseActivity {


    Context context=RegisterActivity.this;
    private static final String SMS_MODEL_NAME="在荒野注册短信验证码";
    private static final int MY_ALLOW_READ_PHONE=1;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_toolbar)
    TextView tv_toolbar;


    @Bind(R.id.ed_register_phonenumber)
    EditText ed_phoneNum;
    @Bind(R.id.ed_register_username) EditText ed_username;
    @Bind(R.id.ed_register_password1) EditText ed_password1;
    @Bind(R.id.ed_register_password2) EditText ed_password2;
    @Bind(R.id.ed_register_smscode) EditText ed_smscode;

    @Bind(R.id.bt_register_getsmscode)
    Button bt_getsmsCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        tv_toolbar.setText("注册");
        tv_toolbar.setTextColor(Color.WHITE);
        toolbar.setTitle(" ");
        toolbar.inflateMenu(R.menu.base_toolbar_menu);



    }

    @OnClick(R.id.bt_register_getsmscode)
    public void getSmsCode(){

        int permissionCheck= ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.READ_PHONE_STATE);

        if(permissionCheck!= PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity.this,Manifest.permission.READ_PHONE_STATE)){
                toast("can get the permission again");
            }else{
                ActivityCompat.requestPermissions(RegisterActivity.this,new String[]{Manifest.permission.READ_PHONE_STATE},MY_ALLOW_READ_PHONE);
            }
        }



    }
    @OnClick(R.id.bt_register_ok)
    public void register(){
        String phoneNumber=ed_phoneNum.getText().toString();
        String userName=ed_username.getText().toString();
        String password1=ed_password1.getText().toString();
        String password2=ed_password2.getText().toString();
        String smsCode=ed_smscode.getText().toString();

        if(phoneNumber==null||phoneNumber.length()!=11){
            toast("手机号不正确");
            return;
        }

        if(userName==null){
            toast("请输入用户名");
            return;
        }
        if(password1==null){
            toast("请输入密码");
            return;
        }
        if(!password1.equals(password2)){
            toast("两次输入密码不一样");
            return;
        }

        //登验证验证码
        final User newUser=new User();
        newUser.setUsername(userName);
        newUser.setPassword(password1);
        newUser.setMobilePhoneNumber(phoneNumber);
        newUser.signOrLogin(context, smsCode, new SaveListener() {
            @Override
            public void onSuccess() {
                toast("注册成功");
                Log.i("test", "" + newUser.getUsername() + "-" + newUser.getAge() + "-" + newUser.getObjectId());
                startActivity(new Intent(RegisterActivity.this, LoginByAccount.class));
                finish();
            }

            @Override
            public void onFailure(int i, String s) {
                toast("错误码：" + i + ",错误原因：" + s);

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case MY_ALLOW_READ_PHONE:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    sendMessage();
                }else
                {
                    //permission denied
                }
                return;
        }
    }
    public void sendMessage(){

        String phoneNumber=ed_phoneNum.getText().toString();
        if(phoneNumber==null||phoneNumber.length()!=11){
            toast("手机号不正确");
            return;
        }
        toast("ALLOW");
        BmobQuery<User> query=new BmobQuery<User>();
        query.addWhereEqualTo("mobilePhoneNumber", phoneNumber);

        query.findObjects(context, new FindListener<User>() {
            @Override
            public void onSuccess(List<User> list) {
                if (!list.isEmpty()) {
                    toast("该电话号码已经注册");
                    bt_getsmsCode.setClickable(true);
                    return;
                }
            }

            @Override
            public void onError(int i, String s) {
                toast("查询手机号" + s);
            }
        });

        BmobSMS.requestSMSCode(context, phoneNumber, SMS_MODEL_NAME, new RequestSMSCodeListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e == null) {
                    toast("验证码是" + integer);
                    MyCounter counter = new MyCounter(60000, 1000, bt_getsmsCode);
                    counter.start();
                } else {
                    toast("wrong" + e);
                }
            }
        });

    }

    class MyCounter extends CountDownTimer{
        Button mButton;

        public MyCounter(long millisInFuture, long countDownInterval,Button bt) {
            super(millisInFuture, countDownInterval);
            this.mButton=bt;

        }
        @Override
        public void onTick(long l) {
            mButton.setClickable(false);
            mButton.setBackgroundColor(getResources().getColor(R.color.light_gray));
            mButton.setText("再次发送"+"（"+l/1000+")");
        }

        @Override
        public void onFinish() {
            mButton.setText("获取验证码");
            mButton.setBackgroundColor(getResources().getColor(R.color.deep_skyblue));
            mButton.setClickable(true);
        }
    }
}
