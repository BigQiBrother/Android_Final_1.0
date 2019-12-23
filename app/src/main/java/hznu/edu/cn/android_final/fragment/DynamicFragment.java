package hznu.edu.cn.android_final.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;

import com.youth.banner.Banner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import hznu.edu.cn.android_final.R;
import hznu.edu.cn.android_final.activity.IndexActivity;
import hznu.edu.cn.android_final.activity.NewsInActivity;
import hznu.edu.cn.android_final.activity.ReleaseDynamicActivity;
import hznu.edu.cn.android_final.adapter.DynamicAdapter;
import hznu.edu.cn.android_final.adapter.NewsAdapter;
import hznu.edu.cn.android_final.beans.DynamicItem;
import hznu.edu.cn.android_final.beans.NewsItem;
import hznu.edu.cn.android_final.model.DynamicModel;
import hznu.edu.cn.android_final.model.NewsModel;
import hznu.edu.cn.android_final.ui.NoScrollListView;
import hznu.edu.cn.android_final.utils.GlideImageLoader;

public class DynamicFragment extends Fragment {

    View mainView = null;
    private Button button;

    public static Handler mHandler;

    private IndexActivity indexActivity;

    DynamicAdapter adapter;
    ListView listView;
    private ImageView iv_release;

    private List<DynamicItem> dynamicItemsList = new ArrayList<DynamicItem>();   // 定义NewsItem对象顺序链表


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mainView == null) {
            mainView = inflater.inflate(R.layout.fragment_dynamic, container, false);
        }
        initView();

        adapter = new DynamicAdapter(indexActivity, R.layout.dynamic_listview_item, dynamicItemsList); // 使用自定义适配器
//        initNews();// 数据初始化
        initDynamic();
        indexActivity = (IndexActivity) getActivity();

        listView.setAdapter(adapter);   // listView使用适配器
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                DynamicItem dynamicItem=dynamicItemsList.get(position);
//                adapter.notifyDataSetChanged();
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("dynamicItem", (Serializable) dynamicItem);
//                Intent intent =new Intent(getContext(), NewsInActivity.class);
//                intent.putExtras(bundle);
//                startActivity(intent);
//                //Toast.makeText(getActivity(),newsItem.getNews_title(),Toast.LENGTH_SHORT).show();
//            }
//        });

        return mainView;
    }

    private void initView() {
        indexActivity = (IndexActivity) getActivity();
        listView = (ListView) mainView.findViewById(R.id.lv_dynamic);      // 加载listView控件
        iv_release=(ImageView)mainView.findViewById(R.id.iv_release);
        iv_release.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(indexActivity, ReleaseDynamicActivity.class));
            }
        });
    }
    @SuppressLint("HandlerLeak")
    private void initDynamic(){
        DynamicModel.sendRequestWithOkHttp_for_dynamic();
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //操作界面
                super.handleMessage(msg);
                switch (msg.what){
                    case 3:
                        // Log.d("asdasd", "handleMessage: "+msg.getData().get("data"));
                        try {
                            JSONArray jsonArray = new JSONArray(msg.getData().get("data").toString());

                            for(int i=0;i<jsonArray.length();i++){

                                JSONObject jsonobj = (JSONObject)jsonArray.get(i);
                                DynamicItem dynamicItem = new DynamicItem(jsonobj.getString("head"),jsonobj.getString("send_name"),jsonobj.getString("send_level"),jsonobj.getString("send_sex"),jsonobj.getString("send_rank"),jsonobj.getString("send_time"),jsonobj.getString("send_content"),jsonobj.getString("send_image1"),jsonobj.getString("send_image2"),jsonobj.getString("send_image3"),jsonobj.getInt("commit_num"),jsonobj.getInt("like_num"));
                                dynamicItemsList.add(dynamicItem);

                            }
                            adapter=new DynamicAdapter(indexActivity,R.layout.dynamic_listview_item,dynamicItemsList); // 使用自定义适配器
                            listView.setAdapter(adapter);   // listView使用适配器
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        break;
                }
            }
        };
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
}