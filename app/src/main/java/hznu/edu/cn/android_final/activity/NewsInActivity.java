package hznu.edu.cn.android_final.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;

import hznu.edu.cn.android_final.R;
import hznu.edu.cn.android_final.beans.NewsItem;

public class NewsInActivity extends AppCompatActivity implements View.OnClickListener {
    // Toolbar
    private ImageView iv_back;
    private ImageView iv_more;

    // 新闻主体
    private TextView tv_news_title;
    private TextView tv_news_source;
    private TextView tv_news_time;
    private TextView tv_news_content_all;
    private ImageView iv_news_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_in);
        Intent intent=getIntent();

        initView();

        // 实例化一个Bundle
        Bundle bundle=intent.getExtras();
        //获取里面的Persion里面的数据
        NewsItem newsItem= (NewsItem) bundle.getSerializable("newsItem");
        Log.d("newsItem", "onCreate: "+newsItem.getSource()+newsItem.getTime());
        tv_news_title.setText(newsItem.getNews_title());
        tv_news_source.setText(newsItem.getSource());
        tv_news_time.setText(newsItem.getTime());
        tv_news_content_all.setText(newsItem.getNews_content());
        String path = "http://10.0.2.2:8888/Images/news/";
//        Log.d("getView", "getView: "+newsItem.getImage_name());
        Log.d("newimage", "onCreate: "+newsItem.getImage_name());
        Glide.with(this).load(path+newsItem.getImage_name()).into(iv_news_in);
    }
    private void initView(){
        // 绑定控件
        iv_back=(ImageView)findViewById(R.id.iv_back);
        iv_more=(ImageView)findViewById(R.id.iv_more);
        tv_news_content_all=(TextView)findViewById(R.id.tv_news_content_all) ;
        tv_news_title=(TextView)findViewById(R.id.tv_news_title) ;
        tv_news_source=(TextView)findViewById(R.id.tv_news_source) ;
        tv_news_time=(TextView)findViewById(R.id.tv_news_time) ;
        iv_news_in=(ImageView)findViewById(R.id.iv_news_in) ;

        // 注册点击事件
        iv_back.setOnClickListener(this);
        iv_more.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                Log.d("onClickback", "onClick: back");
                finish();
                break;
            case R.id.iv_more:
                Log.d("onClickmore", "onClick: more");
                new XPopup.Builder(NewsInActivity.this).asBottomList("请选择一项", new String[]{"食品饮料", "文件", "家电", "服饰鞋帽", "其他"},
                    new OnSelectListener() {
                        @Override
                        public void onSelect(int position, String text) {
                            Toast.makeText(NewsInActivity.this,text,Toast.LENGTH_SHORT).show();
                        }
                    }) .show();
        }
    }
}
