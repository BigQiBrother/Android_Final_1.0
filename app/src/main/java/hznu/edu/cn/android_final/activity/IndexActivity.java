package hznu.edu.cn.android_final.activity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;


import java.util.ArrayList;
import java.util.List;


import de.hdodenhof.circleimageview.CircleImageView;
import hznu.edu.cn.android_final.R;
import hznu.edu.cn.android_final.adapter.FragmentViewPagerAdapter;
import hznu.edu.cn.android_final.fragment.DynamicFragment;
import hznu.edu.cn.android_final.fragment.NewsFragment;
import hznu.edu.cn.android_final.fragment.RaidersFragment;
import hznu.edu.cn.android_final.utils.ContainerViewPager;
import hznu.edu.cn.android_final.utils.MyViewPager;

public class IndexActivity extends FragmentActivity implements View.OnClickListener,NavigationView.OnNavigationItemSelectedListener {

    public static final int TAB_HOME = 0;
    public static final int TAB_PROJECTS = 1;
    public static final int TAB_STUDYS = 2;
    public static final int TAB_USER_CENTER = 3;

    public ContainerViewPager viewPager;

    private LinearLayout ll_news;
    private LinearLayout ll_dynamic;
    private LinearLayout ll_raiders;
    private TextView tv_news;
    private TextView tv_dynamic;
    private TextView tv_raiders;
    private ImageView iv_news;
    private ImageView iv_dynamic;
    private ImageView iv_raiders;

    NewsFragment newsFragment;
    DynamicFragment dynamicFragment;
    RaidersFragment raidersFragment;

    // 侧边栏
    private DrawerLayout mDrawerLayout;
    private TextView tvMain;
    private NavigationView mNavigationView;
    private View headerView;
    private ImageView ivIcon;
    private TextView tvName;

    // Toolbar
    private CircleImageView tv_toolbar;
    private TextView tx_toolbar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide);
        initView();
        addPageChangeListener();



        //          隐藏菜单item
//        MenuItem item = mNavigationView.getMenu().findItem(R.id.item_1);
//        item.setVisible(false);

    }

    private void initView() {
        viewPager=(ContainerViewPager)findViewById(R.id.viewpager);

        // ----------顶部Toolbar------------
        tv_toolbar=(CircleImageView)findViewById(R.id.tv_toolbar);
//        tv_toolbar=(CircleImageView)findViewById(R.id.tv_toolbar);
        tx_toolbar=(TextView)findViewById(R.id.tx_toolbar);

        // -----------侧边栏-----------
//        ivIcon = (ImageView) headerView.findViewById(R.id.icon);
//        tvName = (TextView) headerView.findViewById(R.id.name);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
//        tvMain = (TextView) findViewById(R.id.tv_main);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);

        mNavigationView.setItemIconTintList(null);//让菜单item图片正常

        // 侧边栏菜单点击
        mNavigationView.setNavigationItemSelectedListener(this);
        tv_toolbar.setOnClickListener(this);
//        ivIcon.setOnClickListener(this);
//        tvName.setOnClickListener(this);
        /**
         * 隐藏滚动条
         */
        NavigationMenuView menuView  = (NavigationMenuView) mNavigationView.getChildAt(0);
        menuView.setVerticalScrollBarEnabled(false);
        // 禁止手势划出
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        // 开启手势划出
        //mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        // -----------底部布局-----------
        ll_news=(LinearLayout)findViewById(R.id.ll_news);
        ll_dynamic=(LinearLayout)findViewById(R.id.ll_dynamic);
        ll_raiders=(LinearLayout)findViewById(R.id.ll_raiders);
        // 底部图片
        tv_news=(TextView) findViewById(R.id.tv_news);
        tv_dynamic=(TextView)findViewById(R.id.tv_dynamic);
        tv_raiders=(TextView)findViewById(R.id.tv_raiders);
        // 底部文字
        iv_news=(ImageView)findViewById(R.id.iv_news);
        iv_dynamic=(ImageView)findViewById(R.id.iv_dynamic);
        iv_raiders=(ImageView)findViewById(R.id.iv_raiders);
        //

        // 注册点击事件
        ll_news.setOnClickListener(this);
        ll_dynamic.setOnClickListener(this);
        ll_raiders.setOnClickListener(this);
        // 侧边栏菜单点击

//        ivIcon.setOnClickListener(this);
//        tvName.setOnClickListener(this);


        List<Fragment> fragments = new ArrayList<Fragment>();

        newsFragment = new NewsFragment();

        dynamicFragment = new DynamicFragment();

        raidersFragment = new RaidersFragment();


        fragments.add(newsFragment);
        fragments.add(dynamicFragment);
        fragments.add(raidersFragment);

        this.viewPager.setOffscreenPageLimit(0);

        FragmentViewPagerAdapter adapter = new FragmentViewPagerAdapter(this.getSupportFragmentManager(), viewPager, fragments);

        tv_news.setTextColor(getResources().getColor(R.color.bottom_bar_click));
        iv_news.setImageResource(R.drawable.selector_main_bottom_tab_first_choosed);

    }

    private void addPageChangeListener() {
        viewPager.setOnPageChangeListener(new MyViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int id) {
                switch (id) {
                    case TAB_HOME:
                        tx_toolbar.setText("资讯");
                        Log.d(""+TAB_HOME, "onPageSelected: "+TAB_HOME);
                        break;
                    case TAB_PROJECTS:
                        tx_toolbar.setText("盟友圈");
                        Log.d(""+TAB_PROJECTS, "onPageSelected: "+TAB_PROJECTS);
                        break;
                    case TAB_STUDYS:
                        tx_toolbar.setText("攻略");
                        Log.d(""+TAB_STUDYS, "onPageSelected: "+TAB_STUDYS);
                        break;
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }


    public void onClick(View v) {
        switch (v.getId()) {

            // 按钮点击 根据0,1,2切换viewpager中的item
            case R.id.ll_news:
                viewPager.setCurrentItem(TAB_HOME, false);
                Log.d("123", "onClick: "+"123121");
                tv_news.setTextColor(getResources().getColor(R.color.bottom_bar_click));
                iv_news.setImageResource(R.drawable.selector_main_bottom_tab_first_choosed);
                tv_dynamic.setTextColor(getResources().getColor(R.color.bottom_bar_unclick));
                iv_dynamic.setImageResource(R.drawable.selector_main_bottom_tab_second);
                tv_raiders.setTextColor(getResources().getColor(R.color.bottom_bar_unclick));
                iv_raiders.setImageResource(R.drawable.selector_main_bottom_tab_third);
                doubleClick("0");
                break;
            case R.id.ll_dynamic:
                viewPager.setCurrentItem(TAB_PROJECTS, false);
                tv_news.setTextColor(getResources().getColor(R.color.bottom_bar_unclick));
                iv_news.setImageResource(R.drawable.selector_main_bottom_tab_first);
                tv_dynamic.setTextColor(getResources().getColor(R.color.bottom_bar_click));
                iv_dynamic.setImageResource(R.drawable.selector_main_bottom_tab_second_choosed);
                tv_raiders.setTextColor(getResources().getColor(R.color.bottom_bar_unclick));
                iv_raiders.setImageResource(R.drawable.selector_main_bottom_tab_third);
                break;
            case R.id.ll_raiders:
                viewPager.setCurrentItem(TAB_STUDYS, false);
                tv_news.setTextColor(getResources().getColor(R.color.bottom_bar_unclick));
                iv_news.setImageResource(R.drawable.selector_main_bottom_tab_first);
                tv_dynamic.setTextColor(getResources().getColor(R.color.bottom_bar_unclick));
                iv_dynamic.setImageResource(R.drawable.selector_main_bottom_tab_second);
                tv_raiders.setTextColor(getResources().getColor(R.color.bottom_bar_click));
                iv_raiders.setImageResource(R.drawable.selector_main_bottom_tab_third_choosed);
                doubleClick("2");
                break;
            case R.id.tv_toolbar:
                if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }

        }
    }

    long firstClickTime = 0;
    long secondClickTime = 0;

    public void doubleClick(String fragmentnum) {

        if (firstClickTime > 0) {
            secondClickTime = SystemClock.uptimeMillis();
            if (secondClickTime - firstClickTime < 500) {
//                LogUtils.d("***************   double click  ******************");
                Log.d("doubleClick", "老铁，双击666");
                switch (fragmentnum){
                    case "0":
                        newsFragment.ScrollToTop();
                        break;
                    case "1":
                        newsFragment.ScrollToTop();
                        break;
                    case "2":
                        raidersFragment.ScrollToTop();
                        break;

                }

                Toast.makeText(IndexActivity.this,"返回顶部",Toast.LENGTH_SHORT).show();
            }
            firstClickTime = 0;
            return;
        }

        firstClickTime = SystemClock.uptimeMillis();

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    firstClickTime = 0;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.item_1:
                //do something
                Log.d("1", "onNavigationItemSelected: 1");
                break;
            case R.id.item_2:
                //do something
                break;
            case R.id.item_6:
                Intent intent = new Intent(IndexActivity.this,SettingActivity.class);
                startActivity(intent);
                //do something
                break;
            //......
        }
        return false;
    }
}