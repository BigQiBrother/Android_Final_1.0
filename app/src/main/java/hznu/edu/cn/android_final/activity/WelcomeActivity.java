package hznu.edu.cn.android_final.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import hznu.edu.cn.android_final.R;

public class WelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //执行在主线程
                //启动主页面
                startActivity(new Intent(WelcomeActivity.this,IndexActivity.class));
                //关闭当前页面
                finish();
            }
        },2000);
    }
}
