package hznu.edu.cn.android_final.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hznu.edu.cn.android_final.R;
import hznu.edu.cn.android_final.adapter.RaidersAdapter;
import hznu.edu.cn.android_final.beans.NewsItem;
import hznu.edu.cn.android_final.beans.RaidersItem;
import hznu.edu.cn.android_final.model.HeroModel;
import hznu.edu.cn.android_final.model.RaidersModel;
import hznu.edu.cn.android_final.ui.EchartView;
import hznu.edu.cn.android_final.utils.EchartOptionUtil;

public class RaidersHero_Activity extends AppCompatActivity implements View.OnClickListener {
    public static Handler mHandler;
    // toolbar
    private ImageView iv_back;
    private TextView tx_toolbar;
    private ImageView iv_more;

    private ImageView tv_hero;

    // 胜率按钮
    private LinearLayout ll_winrate;
    private TextView tx_winrate;
    private View view_winrate;

    // 登场率按钮
    private LinearLayout ll_appearancerate;
    private TextView tx_appearancerate;
    private View view_appearancerate;

    // 禁用率按钮
    private LinearLayout ll_banrate;
    private TextView tx_banrate;
    private View view_banrate;

    // 数据
    private TextView tx_rank;
    private TextView tx_rate;
    String win_rank = String.valueOf(new Random().nextInt(134)+1);
    String appearance_rank = String.valueOf(new Random().nextInt(134)+1);
    String ban_rank = String.valueOf(new Random().nextInt(134)+1);
    String[] dayrate =new String [5];

    // 英雄
    private TextView tx_position;
    private TextView tx_hero;
    RaidersItem raidersItem;


    // 折线图
    private EchartView lineChart;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.raiders_hero);
        Intent intent = getIntent();
        initView();

        // 实例化一个Bundle
        Bundle bundle = intent.getExtras();
        //获取里面的Persion里面的数据
        raidersItem = (RaidersItem) bundle.getSerializable("raidersItem");
        // Log.d("newsItem", "onCreate: "+raidersItem.getSource()+newsItem.getTime());
        tx_toolbar.setText(raidersItem.getHero());
        tx_hero.setText(raidersItem.getHero());
        if(Integer.parseInt(raidersItem.getId())<20){
            tv_hero.setImageResource(R.drawable.tienan);
        }
        else if(Integer.parseInt(raidersItem.getId())<40){
            tv_hero.setImageResource(R.drawable.hero_ruiwen);
        }
        else if(Integer.parseInt(raidersItem.getId())<60){
            tv_hero.setImageResource(R.drawable.nuoshou );
        }
        else if(Integer.parseInt(raidersItem.getId())<80){
            tv_hero.setImageResource(R.drawable.anni);
        }
        else if(Integer.parseInt(raidersItem.getId())<100){
            tv_hero.setImageResource(R.drawable.nvqiang);
        }
        else if(Integer.parseInt(raidersItem.getId())<120){
            tv_hero.setImageResource(R.drawable.hanbing);
        }
        else if(Integer.parseInt(raidersItem.getId())<140){
            tv_hero.setImageResource(R.drawable.daomei);
        }
        tx_rank.setText(win_rank);
        tx_rate.setText(raidersItem.getWin_rate());
        Log.d("raidersItem", "onCreate: "+raidersItem+raidersItem.getId());
        initHero("winrate",raidersItem.getId());
//        String path = "http://10.0.2.2:8888/Images/news/";
//        // Log.d("newimage", "onCreate: "+newsItem.getImage_name());
//        Glide.with(this).load(path+newsItem.getImage_name()).into(iv_news_in);

        lineChart.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        lineChart.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // 最好在h5页面加载完毕后再加载数据，防止html的标签还未加载完成，不能正常显示
                refreshLineChart(dayrate);
            }
        });
    }

    private void initView() {


        lineChart = findViewById(R.id.lineChart);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tx_toolbar = (TextView) findViewById(R.id.tx_toolbar);
        iv_more = (ImageView) findViewById(R.id.iv_more);
        tv_hero = (ImageView) findViewById(R.id.tv_hero);

        // 胜率按钮
        ll_winrate = (LinearLayout) findViewById(R.id.ll_winrate);
        tx_winrate = (TextView) findViewById(R.id.tx_winrate);
        view_winrate = (View) findViewById(R.id.view_winrate);

        // 登场率按钮
        ll_appearancerate = (LinearLayout) findViewById(R.id.ll_appearancerate);
        tx_appearancerate = (TextView) findViewById(R.id.tx_appearancerate);
        view_appearancerate = (View) findViewById(R.id.view_appearancerate);

        // 禁用率按钮
        ll_banrate = (LinearLayout) findViewById(R.id.ll_banrate);
        tx_banrate = (TextView) findViewById(R.id.tx_banrate);
        view_banrate = (View) findViewById(R.id.view_banrate);

        // 数据
        tx_rank = (TextView) findViewById(R.id.tx_rank);
        tx_rate = (TextView) findViewById(R.id.tx_rate);
        // 英雄
        tx_hero = (TextView) findViewById(R.id.tx_hero);


        iv_back.setOnClickListener(this);
        tx_toolbar.setOnClickListener(this);
        iv_more.setOnClickListener(this);

        ll_winrate.setOnClickListener(this);
        ll_appearancerate.setOnClickListener(this);
        ll_banrate.setOnClickListener(this);

    }

    private void refreshLineChart(String[] dayrate) {
        Log.d("new String [20];", "refreshLineChart: "+dayrate);
        Object[] x = new Object[]{
                "12.20th", "12.21th", "12.22th", "12.23th", "12.24th"
        };
        Object[] y = new Object[]{
                dayrate[0], dayrate[1], dayrate[2], dayrate[3], dayrate[4]
        };
        lineChart.refreshEchartsWithOption(EchartOptionUtil.getLineChartOptions(x, y));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_more:
                new XPopup.Builder(RaidersHero_Activity.this).asBottomList("请选择一项", new String[]{"食品饮料", "文件", "家电", "服饰鞋帽", "其他"},
                        new OnSelectListener() {
                            @Override
                            public void onSelect(int position, String text) {
                                Toast.makeText(RaidersHero_Activity.this, text, Toast.LENGTH_SHORT).show();
                            }
                        }).show();
                break;
            case R.id.ll_winrate:
                tx_winrate.setTextColor(getResources().getColor(R.color.account_pressed_true));
                view_winrate.setVisibility(View.VISIBLE);
                tx_appearancerate.setTextColor(getResources().getColor(R.color.black_alpha_128));
                view_appearancerate.setVisibility(View.INVISIBLE);
                tx_banrate.setTextColor(getResources().getColor(R.color.black_alpha_128));
                view_banrate.setVisibility(View.INVISIBLE);
                tx_hero.setText(raidersItem.getHero());
                tx_rank.setText(win_rank);
                tx_rate.setText(raidersItem.getWin_rate());
                refreshLineChart(dayrate);
                initHero("winrate",raidersItem.getId());

                break;
            case R.id.ll_appearancerate:
                tx_winrate.setTextColor(getResources().getColor(R.color.black_alpha_128));
                view_winrate.setVisibility(View.INVISIBLE);
                tx_appearancerate.setTextColor(getResources().getColor(R.color.account_pressed_true));
                view_appearancerate.setVisibility(View.VISIBLE);
                tx_banrate.setTextColor(getResources().getColor(R.color.black_alpha_128));
                view_banrate.setVisibility(View.INVISIBLE);
                tx_hero.setText(raidersItem.getHero());
                tx_rank.setText(appearance_rank);
                tx_rate.setText(raidersItem.getAppearance_rate());
                initHero("appearancerate",raidersItem.getId());
                refreshLineChart(dayrate);
                break;
            case R.id.ll_banrate:
                tx_winrate.setTextColor(getResources().getColor(R.color.black_alpha_128));
                view_winrate.setVisibility(View.INVISIBLE);
                tx_appearancerate.setTextColor(getResources().getColor(R.color.black_alpha_128));
                view_appearancerate.setVisibility(View.INVISIBLE);
                tx_banrate.setTextColor(getResources().getColor(R.color.account_pressed_true));
                view_banrate.setVisibility(View.VISIBLE);
                tx_hero.setText(raidersItem.getHero());
                tx_rank.setText(ban_rank);
                tx_rate.setText(raidersItem.getBan_rate());
                initHero("banrate",raidersItem.getId());
                refreshLineChart(dayrate);
                break;

        }
    }
    @SuppressLint("HandlerLeak")
    private void initHero(String type,String id){
        HeroModel.sendRequestWithOkHttp_for_hero(type,id);
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //操作界面
                super.handleMessage(msg);
                switch (msg.what){
                    case 2:
                        // Log.d("asdasd", "handleMessage: "+msg.getData().get("data"));
                        try {
                            JSONObject jsonObject = new JSONObject(msg.getData().get("data").toString());
//                            Log.d("asdasd", "handleMessage: "+msg.getData().get("data"));
                            for(int i=0;i<jsonObject.length();i++){
                                dayrate[i]=jsonObject.get(("day"+(i+1))).toString();
//                                Log.d("handleMessage", "handleMessage: "+jsonObject.get(("day"+(i+1))).toString());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        break;
                }
            }
        };
    }
}
