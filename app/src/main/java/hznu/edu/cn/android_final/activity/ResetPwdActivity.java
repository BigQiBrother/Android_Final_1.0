package hznu.edu.cn.android_final.activity;

import android.os.Bundle;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import hznu.edu.cn.android_final.R;

public class ResetPwdActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener, TextWatcher {
    private EditText et_reset_pwd;
    private EditText et_reset_pwd_twice;
    private ImageView iv_reset_pwd_del;
    private ImageView iv_reset_pwd_twice_del;
    private Button bt_reset_submit;

    // 全局变量
    private Toast mToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_reset_pwd);

        findViewById(R.id.ib_navigation_back).setOnClickListener(this);
        initView();
    }
    private void initView(){
        // 输入密码
        et_reset_pwd=(EditText)findViewById(R.id.et_reset_pwd);
        iv_reset_pwd_del=(ImageView)findViewById(R.id.iv_reset_pwd_del);
        // 确认密码
        et_reset_pwd_twice=(EditText)findViewById(R.id.et_reset_pwd_twice);
        iv_reset_pwd_twice_del=(ImageView)findViewById(R.id.iv_reset_pwd_twice_del);
        // 确认修改
        bt_reset_submit=(Button)findViewById(R.id.bt_reset_submit);

        // 注册点击事件
        et_reset_pwd.setOnClickListener(this);
        iv_reset_pwd_del.setOnClickListener(this);
        et_reset_pwd_twice.setOnClickListener(this);
        iv_reset_pwd_twice_del.setOnClickListener(this);
        bt_reset_submit.setOnClickListener(this);

        // 注册焦点改变事件
        et_reset_pwd.setOnFocusChangeListener(this);
        iv_reset_pwd_del.setOnFocusChangeListener(this);
        et_reset_pwd_twice.setOnFocusChangeListener(this);
        iv_reset_pwd_twice_del.setOnFocusChangeListener(this);
        bt_reset_submit.setOnFocusChangeListener(this);

        // 注册内容改变事件
        et_reset_pwd.addTextChangedListener(this);
        et_reset_pwd_twice.addTextChangedListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_navigation_back:
                finish();
                break;
            case R.id.et_reset_pwd:
                et_reset_pwd_twice.clearFocus();
                et_reset_pwd.setFocusableInTouchMode(true);
                et_reset_pwd.requestFocus();
                break;
            case R.id.et_reset_pwd_twice:
                et_reset_pwd.clearFocus();
                et_reset_pwd_twice.setFocusableInTouchMode(true);
                et_reset_pwd_twice.requestFocus();
                break;
            case R.id.iv_reset_pwd_del:
                et_reset_pwd.setText(null);
                break;
            case R.id.iv_register_password_twice_del:
                et_reset_pwd_twice.setText(null);
                break;
            case R.id.bt_reset_submit:
                reset();
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String pwd = et_reset_pwd.getText().toString().trim();
        String pwd_twice = et_reset_pwd_twice.getText().toString().trim();

        //是否显示清除按钮
        if (pwd.length() > 0) {
            iv_reset_pwd_del.setVisibility(View.VISIBLE);
        } else {
            iv_reset_pwd_del.setVisibility(View.INVISIBLE);
        }
        if (pwd_twice.length() > 0) {
            iv_reset_pwd_twice_del.setVisibility(View.VISIBLE);
        } else {
            iv_reset_pwd_twice_del.setVisibility(View.INVISIBLE);
        }

        //注册按钮是否可用
        if (!TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(pwd_twice)) {
            bt_reset_submit.setBackgroundResource(R.drawable.bg_login_submit);
            bt_reset_submit.setTextColor(getResources().getColor(R.color.white));
        } else {
            bt_reset_submit.setBackgroundResource(R.drawable.bg_login_submit_lock);
            bt_reset_submit.setTextColor(getResources().getColor(R.color.account_lock_font_color));
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        int id = v.getId();

        if (id == R.id.et_reset_pwd) {
            if (hasFocus) {
                et_reset_pwd.setActivated(true);
                et_reset_pwd_twice.setActivated(false);
            }
        }
        else if(id==R.id.et_reset_pwd_twice) {
            if (hasFocus) {
                et_reset_pwd.setActivated(false);
                et_reset_pwd_twice.setActivated(true);
            }
        }
    }

    private void reset(){
        String reset_pwd=et_reset_pwd.getText().toString().trim();
        String reset_pwd_twice=et_reset_pwd_twice.getText().toString().trim();
        if(TextUtils.isEmpty(reset_pwd)){
            showToast("新密码不能为空！");
        }
        else if(!reset_pwd.equals(reset_pwd_twice)){
            showToast("2次密码不相同！");
        }
        else{
            sendRequestWithOkHttp_for_resetPwd(reset_pwd);
        }
    }
    private void sendRequestWithOkHttp_for_resetPwd(String reset_pwd){

    }
    private void showToast(String msg) {
        if (null != mToast) {
            mToast.setText(msg);
        } else {
            mToast = Toast.makeText(ResetPwdActivity.this, msg, Toast.LENGTH_SHORT);
        }
        mToast.show();
    }
}
