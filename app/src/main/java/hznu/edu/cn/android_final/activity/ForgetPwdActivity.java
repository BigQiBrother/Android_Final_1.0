package hznu.edu.cn.android_final.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import hznu.edu.cn.android_final.R;

public class ForgetPwdActivity extends AppCompatActivity implements View.OnClickListener{

    private Button bt_retrieve_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_retrieve_pwd);

        findViewById(R.id.ib_navigation_back).setOnClickListener(this);
        initView();
    }
    private void initView(){
        bt_retrieve_submit=(Button)findViewById(R.id.bt_retrieve_submit);
        bt_retrieve_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_navigation_back:
                finish();
                break;
            case R.id.bt_retrieve_submit:
                Intent intent = new Intent(ForgetPwdActivity.this,ResetPwdActivity.class);
                startActivity(intent);
        }
    }
}
