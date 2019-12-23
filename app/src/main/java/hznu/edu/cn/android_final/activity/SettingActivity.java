package hznu.edu.cn.android_final.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Printer;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;

import hznu.edu.cn.android_final.R;
import hznu.edu.cn.android_final.beans.NewsItem;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    // Toolbar
    private ImageView iv_back;

    // 设置主体
    private LinearLayout ll_bindRegion;
    private LinearLayout ll_bindPhone;
    private LinearLayout ll_message_push;
    private LinearLayout ll_cache;
    private LinearLayout ll_about;
    private TextView tx_Region;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Intent intent=getIntent();

        initView();
    }
    private void initView(){
        // 绑定控件
        iv_back=(ImageView)findViewById(R.id.iv_back);
        ll_bindRegion=(LinearLayout)findViewById(R.id.ll_bindRegion);
        ll_bindPhone=(LinearLayout)findViewById(R.id.ll_bindPhone);
        ll_cache=(LinearLayout)findViewById(R.id.ll_cache);
        ll_about=(LinearLayout)findViewById(R.id.ll_about) ;
        tx_Region=(TextView)findViewById(R.id.tx_Region);
        ll_message_push=(LinearLayout)findViewById(R.id.ll_message_push);
//        tv_news_content_all=(TextView)findViewById(R.id.tv_news_content_all) ;
//        tv_news_title=(TextView)findViewById(R.id.tv_news_title) ;
//        tv_news_source=(TextView)findViewById(R.id.tv_news_source) ;
//        tv_news_time=(TextView)findViewById(R.id.tv_news_time) ;
//        iv_news_in=(ImageView)findViewById(R.id.iv_news_in) ;

        // 注册点击事件
        iv_back.setOnClickListener(this);
        ll_bindRegion.setOnClickListener(this);
        ll_bindPhone.setOnClickListener(this);
        ll_message_push.setOnClickListener(this);
        ll_cache.setOnClickListener(this);
        ll_about.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                //Log.d("onClickback", "onClick: back");
                finish();
                break;
            case R.id.ll_bindRegion:
                //Log.d("onClickmore", "onClick: more");
                new XPopup.Builder(SettingActivity.this).asBottomList("请选择大区", new String[]{"诺克萨斯","德玛西亚","艾欧尼亚","比尔吉沃特", "祖安","巨神峰","无畏先锋","裁决之地","黑色玫瑰", "钢铁烈阳","恕瑞玛","水晶之痕","征服之海","扭曲丛林", "巨龙之巢","皮城警备","男爵领域"},
                        new OnSelectListener() {
                            @Override
                            public void onSelect(int position, String text) {
                                //Toast.makeText(SettingActivity.this,text,Toast.LENGTH_SHORT).show();
                                tx_Region.setText(text);
                            }
                        }) .show();
                break;
            case R.id.ll_message_push:
                startActivity(new Intent(SettingActivity.this,SettingMessageActivity.class));
                break;
            case R.id.ll_cache:
                startActivity(new Intent(SettingActivity.this,SettingCacheActivity.class));
                break;
            case R.id.ll_about:
                startActivity(new Intent(SettingActivity.this,SettingAboutActivity.class));
                break;
        }
    }
}
