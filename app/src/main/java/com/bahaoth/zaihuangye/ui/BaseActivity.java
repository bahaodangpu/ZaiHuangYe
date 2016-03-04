package com.bahaoth.zaihuangye.ui;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.bahaoth.zaihuangye.R;

/**
 * Created by 道谊戎 on 2016/2/26.
 */
    public class BaseActivity extends AppCompatActivity {

    public static String TAG = "在荒野";

    protected ListView mListview;
    protected BaseAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

    }

    public void toast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        Log.d(TAG, msg);
    }

    Toast mToast;


    //以下定义一个Toast对象，以便于展示Toast
    public void showToast(String text) {
        if (!TextUtils.isEmpty(text)) {
            if (mToast == null) {
                mToast = Toast.makeText(getApplicationContext(), text,
                        Toast.LENGTH_SHORT);
            } else {
                mToast.setText(text);
            }
            mToast.show();
        }
    }

    public void showToast(int resId) {
        if (mToast == null) {
            mToast = Toast.makeText(getApplicationContext(), resId,
                    Toast.LENGTH_SHORT);
        } else {
            mToast.setText(resId);
        }
        mToast.show();
    }

    public static void showLog(String msg) {
        Log.i("BmobPro", msg);
    }


}
