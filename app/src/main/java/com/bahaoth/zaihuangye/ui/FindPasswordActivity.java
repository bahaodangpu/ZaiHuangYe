package com.bahaoth.zaihuangye.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.bahaoth.zaihuangye.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 道谊戎 on 2016/2/27.
 */
public class FindPasswordActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.tv_toolbar)
    TextView tv_toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);
        ButterKnife.bind(this);
        tv_toolbar.setText("找回密码");
        tv_toolbar.setTextColor(Color.WHITE);
        toolbar.setTitle(" ");
        toolbar.inflateMenu(R.menu.base_toolbar_menu);

    }
}
