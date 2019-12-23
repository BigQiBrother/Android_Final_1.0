package hznu.edu.cn.android_final.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import hznu.edu.cn.android_final.R;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener, TextWatcher  {

    private ImageButton mIbNavigationBack;
    private LinearLayout mLlLoginPull;
    private View mLlLoginLayer;
    private EditText mEtLoginUsername;
    private EditText et_register_password;
    private ImageView iv_register_password_del;
    private EditText et_register_password_twice;
    private ImageView iv_register_password_twice_del;
    private LinearLayout mLlLoginUsername;
    private ImageView mIvLoginUsernameDel;
    private Button bt_register_submit;
    private LinearLayout mLlLoginpassword;


    private LinearLayout ll_register_two_password;

    private ImageView mIvLoginLogo;
    private LinearLayout mLayBackBar;
    private EditText et_register_phone;
    private LinearLayout ll_register_phone;
    private ImageView iv_register_phone_del;

    private EditText et_register_vercode; // 获取验证码
    private LinearLayout ll_register_sms_code;
    private TextView tv_register_sms_call;
    private CheckBox cb_protocol;

    //全局变量
    private String response_vercode;
    private Toast mToast;

    private int mLogoHeight;
    private int mLogoWidth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_register_step_one);

        findViewById(R.id.ib_navigation_back).setOnClickListener(this);
        initView();

    }
    private void initView(){

        //登录层、下拉层、其它登录方式层
        mLlLoginLayer = findViewById(R.id.ll_login_layer);
        mLlLoginPull = findViewById(R.id.ll_login_pull);
        ll_register_two_password=findViewById(R.id.ll_register_two_password);
        ll_register_phone=findViewById(R.id.ll_register_phone);
        ll_register_sms_code=findViewById(R.id.ll_register_sms_code);


        //导航栏+返回按钮
        mLayBackBar = findViewById(R.id.ly_retrieve_bar);
        mIbNavigationBack = findViewById(R.id.ib_navigation_back);

        //logo
        mIvLoginLogo = findViewById(R.id.iv_login_logo);

        //username
        mLlLoginUsername = findViewById(R.id.ll_login_username);
        mEtLoginUsername = findViewById(R.id.et_login_username);
        mIvLoginUsernameDel = findViewById(R.id.iv_login_username_del);

        //passwd
        mLlLoginpassword = findViewById(R.id.ll_register_password);
        et_register_password = findViewById(R.id.et_register_password);
        iv_register_password_del = findViewById(R.id.iv_register_password_del);
        et_register_password_twice = findViewById(R.id.et_register_password_twice);
        iv_register_password_twice_del = findViewById(R.id.iv_register_password_twice_del);

        et_register_password_twice=findViewById(R.id.et_register_password_twice);

        // 手机、验证码

        et_register_phone=findViewById(R.id.et_register_phone);
        et_register_vercode=findViewById(R.id.et_register_vercode);
        iv_register_phone_del=findViewById(R.id.iv_register_phone_del);
        tv_register_sms_call=findViewById(R.id.tv_register_sms_call);
        //提交、注册
        bt_register_submit = findViewById(R.id.bt_register_submit);

        //注册点击事件
        mLlLoginPull.setOnClickListener(this);
        mIbNavigationBack.setOnClickListener(this);
        mEtLoginUsername.setOnClickListener(this);
        mIvLoginUsernameDel.setOnClickListener(this);
        bt_register_submit.setOnClickListener(this);
        et_register_password.setOnClickListener(this);
        et_register_password_twice.setOnClickListener(this);
        et_register_phone.setOnClickListener(this);
        et_register_vercode.setOnClickListener(this);

        iv_register_password_del.setOnClickListener(this);
        iv_register_password_twice_del.setOnClickListener(this);
        iv_register_phone_del.setOnClickListener(this);
        tv_register_sms_call.setOnClickListener(this);

        //注册焦点事件
        //mLayBackBar.getViewTreeObserver().addOnGlobalLayoutListener(this);
        mEtLoginUsername.setOnFocusChangeListener(this);
        et_register_password.setOnFocusChangeListener(this);
        et_register_password_twice.setOnFocusChangeListener(this);
        et_register_phone.setOnFocusChangeListener(this);
        et_register_vercode.setOnFocusChangeListener(this);
        // 注册文本改变事件

        et_register_password.addTextChangedListener(this);
        mEtLoginUsername.addTextChangedListener(this);
        et_register_password_twice.addTextChangedListener(this);
        et_register_phone.addTextChangedListener(this);
        et_register_vercode.addTextChangedListener(this);

    }

     // 按钮点击事件
     @Override
     public void onClick(View view) {
         switch (view.getId()) {
             case R.id.ib_navigation_back:
                 //返回
                 finish();
                 break;
             // 焦点改变
             case R.id.et_login_username:
                 et_register_password.clearFocus();
                 et_register_password_twice.clearFocus();
                 et_register_phone.clearFocus();
                 et_register_vercode.clearFocus();
                 mEtLoginUsername.setFocusableInTouchMode(true);
                 mEtLoginUsername.requestFocus();
                 break;
             case R.id.et_register_password:
                 mEtLoginUsername.clearFocus();
                 et_register_password_twice.clearFocus();
                 et_register_phone.clearFocus();
                 et_register_vercode.clearFocus();
                 et_register_password.setFocusableInTouchMode(true);
                 et_register_password.requestFocus();
                 break;
             case R.id.et_register_password_twice:
                 mEtLoginUsername.clearFocus();
                 et_register_password.clearFocus();
                 et_register_phone.clearFocus();
                 et_register_vercode.clearFocus();
                 et_register_password_twice.setFocusableInTouchMode(true);
                 et_register_password_twice.requestFocus();
             case R.id.et_register_phone:
                 mEtLoginUsername.clearFocus();
                 et_register_password.clearFocus();
                 et_register_password_twice.clearFocus();
                 et_register_vercode.clearFocus();
                 et_register_phone.setFocusableInTouchMode(true);
                 et_register_phone.requestFocus();
             case R.id.et_register_vercode:
                 mEtLoginUsername.clearFocus();
                 et_register_password.clearFocus();
                 et_register_password_twice.clearFocus();
                 et_register_phone.clearFocus();
                 et_register_vercode.setFocusableInTouchMode(true);
                 et_register_vercode.requestFocus();
             case R.id.iv_login_username_del:
                 //清空用户名
                 mEtLoginUsername.setText(null);
                 break;
             case R.id.iv_register_password_del:
                 //清空密码
                 et_register_password.setText(null);
                 break;
             case R.id.iv_register_password_twice_del:
                 //清空密码
                 et_register_password_twice.setText(null);
                 break;
             case R.id.iv_register_phone_del:
                 //清空手机
                 et_register_phone.setText(null);
                 break;
                 // 发送验证码
             case R.id.tv_register_sms_call:
                 String phone = et_register_phone.getText().toString().trim();
                 String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位
                 if(!phone.matches(telRegex)){
                     showToast("请输入正确的手机号！");
                     et_register_phone.setText(null);
                 }
                 else{
                     Log.d("phone", "onClick: "+phone);
                     SendSms(phone);
                 }

                 break;
             case R.id.bt_register_submit:
                 //注册
                 loginRequest();
                 break;

             case R.id.ll_login_layer:
             case R.id.ll_login_pull:
                 mLlLoginPull.animate().cancel();
                 mLlLoginLayer.animate().cancel();

                 float progress = (mLlLoginLayer.getTag() != null && mLlLoginLayer.getTag() instanceof Float) ? (float) mLlLoginLayer.getTag() : 1;
                 int time = (int) (360 * progress);

                 if (mLlLoginPull.getTag() != null) {
                     mLlLoginPull.setTag(null);
                 } else {
                     mLlLoginPull.setTag(true);
                 }
                 break;
             default:
                 break;
         }
     }

    //焦点改变
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        int id = v.getId();
        if (id == R.id.et_login_username) {
            if (hasFocus) {
                mLlLoginUsername.setActivated(true);
                mLlLoginpassword.setActivated(false);
                ll_register_two_password.setActivated(false);
                ll_register_phone.setActivated(false);
                ll_register_sms_code.setActivated(false);
            }
        }
        else if(id==R.id.et_register_password) {
            if (hasFocus) {

                mLlLoginUsername.setActivated(false);
                mLlLoginpassword.setActivated(true);
                ll_register_two_password.setActivated(false);
                ll_register_phone.setActivated(false);
                ll_register_sms_code.setActivated(false);
            }
        }
        else if(id==R.id.et_register_password_twice){
            if (hasFocus) {
                mLlLoginpassword.setActivated(false);
                mLlLoginUsername.setActivated(false);
                ll_register_two_password.setActivated(true);
                ll_register_phone.setActivated(false);
                ll_register_sms_code.setActivated(false);
            }
        }
        else if(id==R.id.et_register_phone){
            if (hasFocus) {
                mLlLoginpassword.setActivated(false);
                mLlLoginUsername.setActivated(false);
                ll_register_two_password.setActivated(false);
                ll_register_phone.setActivated(true);
                ll_register_sms_code.setActivated(false);
            }
        }
        else if(id==R.id.et_register_vercode){
            if (hasFocus) {
                mLlLoginpassword.setActivated(false);
                mLlLoginUsername.setActivated(false);
                ll_register_two_password.setActivated(false);
                ll_register_phone.setActivated(false);
                ll_register_sms_code.setActivated(true);
            }
        }
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    //用户名密码输入事件
    @Override
    public void afterTextChanged(Editable s) {
        String username = mEtLoginUsername.getText().toString().trim();
        String password = et_register_password.getText().toString().trim();
        String password_twice = et_register_password_twice.getText().toString().trim();
        String phone=et_register_phone.getText().toString().trim();

        //是否显示清除按钮
        if (username.length() > 0) {
            mIvLoginUsernameDel.setVisibility(View.VISIBLE);
        } else {
            mIvLoginUsernameDel.setVisibility(View.INVISIBLE);
        }
        if (password.length() > 0) {
            iv_register_password_del.setVisibility(View.VISIBLE);
        } else {
            iv_register_password_del.setVisibility(View.INVISIBLE);
        }
        if(password_twice.length()>0){
            iv_register_password_twice_del.setVisibility(View.VISIBLE);
        } else {
            iv_register_password_twice_del.setVisibility(View.INVISIBLE);
        }
        if(phone.length()>0){
            iv_register_phone_del.setVisibility(View.VISIBLE);
        } else {
            iv_register_phone_del.setVisibility(View.INVISIBLE);
        }

        //注册按钮是否可用
        if (!TextUtils.isEmpty(password) && !TextUtils.isEmpty(username)&&!TextUtils.isEmpty(password_twice)) {
            bt_register_submit.setBackgroundResource(R.drawable.bg_login_submit);
            bt_register_submit.setTextColor(getResources().getColor(R.color.white));
        } else {
            bt_register_submit.setBackgroundResource(R.drawable.bg_login_submit_lock);
            bt_register_submit.setTextColor(getResources().getColor(R.color.account_lock_font_color));
        }
    }

    //注册

    private void loginRequest() {
        String username = mEtLoginUsername.getText().toString().trim();
        String password = et_register_password.getText().toString().trim();
        String password_twice=et_register_password_twice.getText().toString().trim();
        String phone=et_register_phone.getText().toString().trim();
        String vercode=et_register_vercode.getText().toString().trim();
        if(TextUtils.isEmpty(username)){
            showToast("用户名不能为空！");
        }
        else if(TextUtils.isEmpty(password)){
            showToast("密码不能为空！");
        }
        else if(!password.equals(password_twice)){
            showToast("2次密码不相同！");
        }
        else if(TextUtils.isEmpty(vercode)){
            showToast("验证码不能为空");
        }
        else if(!response_vercode.equals(vercode)){
            showToast("请输入正确的验证码！");
        }
        else{
            sendRequestWithOkHttp_for_login(username,password,phone,vercode);
        }
    }

    // 调用servlet发送短信验证码
    private void SendSms(final String phone) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Looper.prepare();
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody=new FormBody.Builder().add("phone",phone).build();
                    Request request1=new Request.Builder().url("http://10.0.2.2:8888/JSP_Final/SendSms_Servlet").post(requestBody).build();
                    Response response = client.newCall(request1).execute();
                    String response_vercode = response.body().string();

                    if(!"".equals(response_vercode)){
                        Toast.makeText(RegisterActivity.this,"验证码发送成功！",Toast.LENGTH_SHORT).show();
                    }
                    Looper.loop();
                    

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // 调用servlet验证登录
    private void sendRequestWithOkHttp_for_login(final String username, final String password, final String phone, final String vercode) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Looper.prepare();
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody=new FormBody.Builder().add("username",username) .add("password",password).add("phone",phone).add("vercode",vercode).build();
                    Request request1=new Request.Builder().url("http://10.0.2.2:8888/JSP_Final/Register_Servlet").post(requestBody).build();
                    Response response = client.newCall(request1).execute();
                    String responseData = response.body().string();
                    if ("existed".equals(responseData)){
                        Toast.makeText(RegisterActivity.this,"用户名已存在！",Toast.LENGTH_SHORT).show();

                    }
                    else if("true".equals(responseData)){
                        Toast.makeText(RegisterActivity.this,"注册成功!返回登录界面！",Toast.LENGTH_SHORT).show();
                        Intent intent =new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Log.d("error", "run: "+responseData);
                    }
                    Looper.loop();
                    Log.d("123456", "run: "+responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    /**
     * 显示Toast
     *
     * @param msg 提示信息内容
     */
    private void showToast(String msg) {
        if (null != mToast) {
            mToast.setText(msg);
        } else {
            mToast = Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT);
        }

        mToast.show();
    }

}