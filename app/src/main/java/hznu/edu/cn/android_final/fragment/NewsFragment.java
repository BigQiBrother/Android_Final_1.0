package hznu.edu.cn.android_final.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;


import com.youth.banner.Banner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hznu.edu.cn.android_final.R;
import hznu.edu.cn.android_final.activity.IndexActivity;
import hznu.edu.cn.android_final.activity.LoginActivity;
import hznu.edu.cn.android_final.activity.NewsInActivity;
import hznu.edu.cn.android_final.adapter.NewsAdapter;
import hznu.edu.cn.android_final.beans.NewsItem;
import hznu.edu.cn.android_final.model.NewsModel;
import hznu.edu.cn.android_final.utils.GlideImageLoader;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NewsFragment extends Fragment implements View.OnClickListener {

    public static Handler mHandler;

    private IndexActivity indexActivity;

    private ScrollView sc_news;
    NewsAdapter adapter;
    ListView listView;


    View mainView = null;
    private List<NewsItem> newsItemsList=new ArrayList<NewsItem>();   // 定义NewsItem对象顺序链表

    @SuppressLint("HandlerLeak")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        if (mainView == null) {
            mainView = inflater.inflate(R.layout.fragment_news, container, false);
        }
        initView();
        lunbo();
        adapter=new NewsAdapter(indexActivity,R.layout.news_listview_item,newsItemsList); // 使用自定义适配器
        initNews();// 数据初始化
        IndexActivity indexActivity = (IndexActivity) getActivity();
        listView = (ListView)mainView.findViewById(R.id.lv_news);      // 加载listView控件
        listView.setAdapter(adapter);   // listView使用适配器
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsItem newsItem=newsItemsList.get(position);
                newsItem.setVisited(true);
                adapter.notifyDataSetChanged();
                Bundle bundle = new Bundle();
                bundle.putSerializable("newsItem", (Serializable) newsItem);
                Intent intent =new Intent(getContext(), NewsInActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                //Toast.makeText(getActivity(),newsItem.getNews_title(),Toast.LENGTH_SHORT).show();
            }
        });

        return mainView;
    }
    private void lunbo(){
        Banner banner = (Banner) mainView.findViewById(R.id.banner);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        Integer[] images={R.drawable.lunbo1,R.drawable.lunbo2,R.drawable.lunbo3,R.drawable.lunbo4};
        banner.setImages(Arrays.asList(images));
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }
    private  void initView(){
        indexActivity=(IndexActivity) getActivity();
        sc_news=(ScrollView)mainView.findViewById(R.id.sc_news);

    }

    @SuppressLint("HandlerLeak")
    private void initNews(){
        NewsModel.sendRequestWithOkHttp_for_news();
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //操作界面
                super.handleMessage(msg);
                switch (msg.what){
                    case 1:
                        // Log.d("asdasd", "handleMessage: "+msg.getData().get("data"));
                        try {
                            JSONArray jsonArray = new JSONArray(msg.getData().get("data").toString());

                            for(int i=0;i<jsonArray.length();i++){

                                JSONObject jsonobj = (JSONObject)jsonArray.get(i);
                                NewsItem newsItem = new NewsItem(jsonobj.getString("content"),jsonobj.getString("content_all"),jsonobj.getString("image"),jsonobj.getString("time"),jsonobj.getString("source"),false);
                                newsItemsList.add(newsItem);

                            }
                            adapter=new NewsAdapter(indexActivity,R.layout.news_listview_item,newsItemsList); // 使用自定义适配器
                            listView.setAdapter(adapter);   // listView使用适配器
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("handleMessage", "handleMessage: "+newsItemsList.get(10).getImage_name());

                        break;
                }
            }
        };
    }


    public void ScrollToTop() {
        sc_news.post(new Runnable() {

            @Override
            public void run() {
                sc_news.post(new Runnable() {
                    public void run() {
                        // 返回顶部
                        sc_news.fullScroll(ScrollView.FOCUS_UP);
                    }
                });
            }
        });
    }


    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.tv_toolbar:
//                Log.d("abc", "onClick: 123");
//                if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
//                    mDrawerLayout.closeDrawer(GravityCompat.START);
//                }else {
//                    mDrawerLayout.openDrawer(GravityCompat.START);
//                }
//        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
   }


}
