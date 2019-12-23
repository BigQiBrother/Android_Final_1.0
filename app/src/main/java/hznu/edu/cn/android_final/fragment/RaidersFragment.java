package hznu.edu.cn.android_final.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import hznu.edu.cn.android_final.R;
import hznu.edu.cn.android_final.activity.IndexActivity;
import hznu.edu.cn.android_final.activity.NewsInActivity;
import hznu.edu.cn.android_final.activity.RaidersHero_Activity;
import hznu.edu.cn.android_final.adapter.RaidersAdapter;
import hznu.edu.cn.android_final.beans.RaidersItem;
import hznu.edu.cn.android_final.model.RaidersModel;
import hznu.edu.cn.android_final.utils.SortComparator_Appearancerate;
import hznu.edu.cn.android_final.utils.SortComparator_Banrate;
import hznu.edu.cn.android_final.utils.SortComparator_Winrate;

public class RaidersFragment extends Fragment implements View.OnClickListener, TextWatcher {

    public static Handler mHandler;

    private IndexActivity indexActivity;
    private ScrollView sc_raiders;
    RaidersAdapter adapter;
    ListView listView;

    private EditText et_search;
    private ImageView iv_do_search;
    private Button btn_all;
    private Button btn_winrate;
    private Button btn_appearancerate;
    private Button btn_banrate;


    String choose_type="all";

    View mainView = null;
    private List<RaidersItem> raidersItemList=new ArrayList<RaidersItem>();   // 定义NewsItem对象顺序链表
    private List<RaidersItem> raidersItemList1=new ArrayList<RaidersItem>();   // 定义NewsItem对象顺序链表
    List<RaidersItem> raidersItemList_for_search=new ArrayList<RaidersItem>();   // 定义NewsItem对象顺序链表
    @SuppressLint("HandlerLeak")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (mainView == null) {
            mainView = inflater.inflate(R.layout.fragment_raiders, container, false);
        }
        initView();
        adapter=new RaidersAdapter(indexActivity,R.layout.raiders_listview_item,raidersItemList); // 使用自定义适配器
        initRaiders("android_all");// 数据初始化
        IndexActivity indexActivity = (IndexActivity) getActivity();
        listView = (ListView)mainView.findViewById(R.id.lv_raiders);      // 加载listView控件
        listView.setAdapter(adapter);   // listView使用适配器

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RaidersItem raidersItem_choose;
                if("all".equals(choose_type)){
                    raidersItem_choose=raidersItemList.get(position);
                }
                else if("search".equals(choose_type)){
                    raidersItem_choose=raidersItemList_for_search.get(position);
                }
                else{
                    raidersItem_choose=raidersItemList1.get(position);
                }
                raidersItem_choose.setVisited(true);
                adapter.notifyDataSetChanged();
                Bundle bundle = new Bundle();
                bundle.putSerializable("raidersItem", (Serializable) raidersItem_choose);
                Intent intent =new Intent(getContext(), RaidersHero_Activity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                //Toast.makeText(getActivity(),newsItem.getNews_title(),Toast.LENGTH_SHORT).show();
            }
        });

        return mainView;
    }

    private  void initView(){
        // 绑定控件
        indexActivity=(IndexActivity) getActivity();
        sc_raiders=(ScrollView)mainView.findViewById(R.id.sc_raiders);
        et_search=(EditText)mainView.findViewById(R.id.et_search);
        iv_do_search=(ImageView)mainView.findViewById(R.id.iv_do_search);
        btn_all=(Button)mainView.findViewById(R.id.btn_all);
        btn_winrate=(Button)mainView.findViewById(R.id.btn_winrate);
        btn_appearancerate=(Button)mainView.findViewById(R.id.btn_appearancerate);
        btn_banrate=(Button)mainView.findViewById(R.id.btn_banrate);

        // 注册点击事件
        et_search.setOnClickListener(this);
        iv_do_search.setOnClickListener(this);
        btn_all.setOnClickListener(this);
        btn_winrate.setOnClickListener(this);
        btn_appearancerate.setOnClickListener(this);
        btn_banrate.setOnClickListener(this);

        // 注册

        et_search.addTextChangedListener(this);

    }

    @SuppressLint("HandlerLeak")
    private void initRaiders(String search_type){
        Log.d("initRaiders", "initRaiders: ");
        RaidersModel.sendRequestWithOkHttp_for_raiders(search_type);
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
//                            Log.d("jsonArray", "handleMessage: "+jsonObject.get("data"));
                            JSONArray jsonArray = new JSONArray(jsonObject.get("data").toString());

                            for(int i=0;i<jsonArray.length();i++){

                                JSONObject jsonobj = (JSONObject)jsonArray.get(i);
                                RaidersItem raidersItem = new RaidersItem(String.valueOf(i+1),jsonobj.getString("id"),jsonobj.getString("hero"),jsonobj.getString("win_rate"),jsonobj.getString("appearance_rate"),jsonobj.getString("ban_rate"),false);
                                raidersItemList.add(raidersItem);
                                raidersItemList1.add(raidersItem);

                            }
                            adapter=new RaidersAdapter(indexActivity,R.layout.raiders_listview_item,raidersItemList); // 使用自定义适配器
                            listView.setAdapter(adapter);   // listView使用适配器
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        Log.d("handleMessage", "handleMessage: "+raidersItemList.get(10).getBan_rate());

                        break;
                }
            }
        };
    }


    public void ScrollToTop() {
        sc_raiders.post(new Runnable() {

            @Override
            public void run() {
                sc_raiders.post(new Runnable() {
                    public void run() {
                        // 返回顶部
                        sc_raiders.fullScroll(ScrollView.FOCUS_UP);
                    }
                });
            }
        });
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_all:
                choose_type="all";
                btn_all.setBackgroundColor(getResources().getColor(R.color.white));
                btn_winrate.setBackgroundColor(getResources().getColor(R.color.raiders_btn));
                btn_appearancerate.setBackgroundColor(getResources().getColor(R.color.raiders_btn));
                btn_banrate.setBackgroundColor(getResources().getColor(R.color.raiders_btn));
                btn_all.setTextColor(getResources().getColor(R.color.account_pressed_true));
                btn_winrate.setTextColor(getResources().getColor(R.color.black_alpha_128));
                btn_appearancerate.setTextColor(getResources().getColor(R.color.black_alpha_128));
                btn_banrate.setTextColor(getResources().getColor(R.color.black_alpha_128));
                for(int i=0;i<raidersItemList.size();i++){
                    raidersItemList.get(i).setCount(String.valueOf(i+1));
                }
                adapter=new RaidersAdapter(indexActivity,R.layout.raiders_listview_item,raidersItemList); // 使用自定义适配器
                listView.setAdapter(adapter);   // listView使用适配器
                break;
            case R.id.btn_winrate:
                choose_type="winrate";
                btn_all.setBackgroundColor(getResources().getColor(R.color.raiders_btn));
                btn_winrate.setBackgroundColor(getResources().getColor(R.color.white));
                btn_appearancerate.setBackgroundColor(getResources().getColor(R.color.raiders_btn));
                btn_banrate.setBackgroundColor(getResources().getColor(R.color.raiders_btn));
                btn_all.setTextColor(getResources().getColor(R.color.black_alpha_128));
                btn_winrate.setTextColor(getResources().getColor(R.color.account_pressed_true));
                btn_appearancerate.setTextColor(getResources().getColor(R.color.black_alpha_128));
                btn_banrate.setTextColor(getResources().getColor(R.color.black_alpha_128));

                Comparator comp = new SortComparator_Winrate();
                Collections.sort(raidersItemList1,comp);
                for(int i=0;i<raidersItemList1.size();i++){
                    raidersItemList1.get(i).setCount(String.valueOf(i+1));
                }
                adapter=new RaidersAdapter(indexActivity,R.layout.raiders_listview_item,raidersItemList1); // 使用自定义适配器
                listView.setAdapter(adapter);   // listView使用适配器
                break;
            case R.id.btn_appearancerate:
                choose_type="appearancerate";
                btn_all.setBackgroundColor(getResources().getColor(R.color.raiders_btn));
                btn_winrate.setBackgroundColor(getResources().getColor(R.color.raiders_btn));
                btn_appearancerate.setBackgroundColor(getResources().getColor(R.color.white));
                btn_banrate.setBackgroundColor(getResources().getColor(R.color.raiders_btn));
                btn_all.setTextColor(getResources().getColor(R.color.black_alpha_128));
                btn_winrate.setTextColor(getResources().getColor(R.color.black_alpha_128));
                btn_appearancerate.setTextColor(getResources().getColor(R.color.account_pressed_true));
                btn_banrate.setTextColor(getResources().getColor(R.color.black_alpha_128));
                Comparator comp1 = new SortComparator_Appearancerate();
                Collections.sort(raidersItemList1,comp1);
                for(int i=0;i<raidersItemList1.size();i++){
                    raidersItemList1.get(i).setCount(String.valueOf(i+1));
                }
                adapter=new RaidersAdapter(indexActivity,R.layout.raiders_listview_item,raidersItemList1); // 使用自定义适配器
                listView.setAdapter(adapter);   // listView使用适配器
                break;
            case R.id.btn_banrate:
                choose_type="banrate";
                btn_all.setBackgroundColor(getResources().getColor(R.color.raiders_btn));
                btn_winrate.setBackgroundColor(getResources().getColor(R.color.raiders_btn));
                btn_appearancerate.setBackgroundColor(getResources().getColor(R.color.raiders_btn));
                btn_banrate.setBackgroundColor(getResources().getColor(R.color.white));
                btn_all.setTextColor(getResources().getColor(R.color.black_alpha_128));
                btn_winrate.setTextColor(getResources().getColor(R.color.black_alpha_128));
                btn_appearancerate.setTextColor(getResources().getColor(R.color.black_alpha_128));
                btn_banrate.setTextColor(getResources().getColor(R.color.account_pressed_true));
                Comparator comp2 = new SortComparator_Banrate();
                Collections.sort(raidersItemList1,comp2);
                for(int i=0;i<raidersItemList1.size();i++){
                    raidersItemList1.get(i).setCount(String.valueOf(i+1));
                }
                adapter=new RaidersAdapter(indexActivity,R.layout.raiders_listview_item,raidersItemList1); // 使用自定义适配器
                listView.setAdapter(adapter);   // listView使用适配器
                break;
            case R.id.iv_do_search:
                choose_type="search";
                search();
        }
    }

    private void search(){
        String search_content=et_search.getText().toString().trim();
        boolean flag=false;
        int count=0;
        for(int i=0;i<raidersItemList.size();i++){
            if(raidersItemList.get(i).getHero().indexOf(search_content)!=-1){
                count++;
                raidersItemList.get(i).setCount(String.valueOf(count));
                raidersItemList_for_search.add(raidersItemList.get(i));
                flag=true;
            }
        }
        if(!flag){
            Toast.makeText(indexActivity,"未查询到相关数据！",Toast.LENGTH_SHORT).show();
            et_search.setText("");
        }
        adapter=new RaidersAdapter(indexActivity,R.layout.raiders_listview_item,raidersItemList_for_search); // 使用自定义适配器
        listView.setAdapter(adapter);   // listView使用适配器
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String search_content = et_search.getText().toString().trim();

        //是否显示清除按钮
        if (search_content.length() > 0) {
            iv_do_search.setVisibility(View.VISIBLE);
        } else {
            iv_do_search.setVisibility(View.INVISIBLE);
            raidersItemList_for_search.clear();
        }
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
