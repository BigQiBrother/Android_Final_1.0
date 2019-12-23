package hznu.edu.cn.android_final.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.sackcentury.shinebuttonlib.ShineButton;

import java.util.List;
import java.util.Random;

import hznu.edu.cn.android_final.R;
import hznu.edu.cn.android_final.beans.DynamicItem;
import hznu.edu.cn.android_final.beans.RaidersItem;

public class DynamicAdapter extends ArrayAdapter<DynamicItem> {

    private int resourceId; // 当前子项的布局id
    public DynamicAdapter(Context context, int textViewResourceId, List<DynamicItem> objects){    // 重写父类构造方法
        super(context,textViewResourceId,objects);
        this.resourceId=textViewResourceId;
    }
    class ViewHolder{   // 定义ViewHolder保存id，无需多次findViewById
        ImageView iv_head;
        TextView tv_send_name;
        TextView tv_send_level;
        TextView tv_sex;
        TextView tx_sendrank;
        TextView tx_sendtime;
        TextView tx_sendcontent;
        ImageView iv_sendimage1;
        ImageView iv_sendimage2;
        ImageView iv_sendimage3;
        TextView tv_commit_num;
        TextView tv_likenum;
        ShineButton btn_like;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent){        // 重写父类getView()方法
//      final int final_position = position;            // 将position设成final便于在内部事件监听类中使用
        String[] hero_position_list={"上单","打野","中单","下路","辅助"};
        String hero_position="";
        for(int i = 0; i<new Random().nextInt(2)+1; i++){
            String str=hero_position_list[new Random().nextInt(5)];
            if(hero_position.indexOf(str)==-1){
                hero_position+=str+" ";
            }
            else{
                continue;
            }
        }

        final DynamicItem dynamicItem=getItem(position);            // 由position获取fruit链表中当前子项对应的fruit对象

        View view;
        final ViewHolder viewHolder;

        if(convertView==null) {                         // 使用缓存记录
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);   // 获取当前子项布局
            viewHolder = new ViewHolder();                                              // 将控件实例保存到viewHolder中
            viewHolder.iv_head=(ImageView) view.findViewById(R.id.iv_head);
            viewHolder.tv_send_name=(TextView)view.findViewById((R.id.tv_send_name));
            viewHolder.tv_send_level=(TextView)view.findViewById(R.id.tv_send_level);
            viewHolder.tv_sex=(TextView)view.findViewById(R.id.tv_sex);
            viewHolder.tx_sendrank=(TextView)view.findViewById(R.id.tx_sendrank);
            viewHolder.tx_sendtime=(TextView)view.findViewById(R.id.tx_sendtime);
            viewHolder.tx_sendcontent=(TextView)view.findViewById(R.id.tx_sendcontent);
            viewHolder.iv_sendimage1=(ImageView) view.findViewById(R.id.iv_sendimage1);
            viewHolder.iv_sendimage2=(ImageView) view.findViewById(R.id.iv_sendimage2);
            viewHolder.iv_sendimage3=(ImageView) view.findViewById(R.id.iv_sendimage3);
            viewHolder.tv_commit_num=(TextView)view.findViewById(R.id.tv_commit_num);
            viewHolder.tv_likenum=(TextView)view.findViewById(R.id.tv_likenum);
            viewHolder.btn_like=(ShineButton) view.findViewById(R.id.btn_like);

            view.setTag(viewHolder);                                                    // 将viewHolder保存到view中
        }
        else{
            view=convertView;                           // view赋值缓存
            viewHolder=(ViewHolder) view.getTag();      // 获取之前view中保存的viewHolder对象.
        }
        String path = "http://10.0.2.2:8888/Images/news/";
//        Log.d("getView", "getView: "+newsItem.getImage_name());
        Glide.with(parent.getContext()).load(path+dynamicItem.getHead()).into(viewHolder.iv_head);
        Glide.with(parent.getContext()).load(path+dynamicItem.getSend_image1()).into(viewHolder.iv_sendimage1);
        Glide.with(parent.getContext()).load(path+dynamicItem.getSend_image2()).into(viewHolder.iv_sendimage2);
        Glide.with(parent.getContext()).load(path+dynamicItem.getSend_image2()).into(viewHolder.iv_sendimage3);
        viewHolder.tv_send_name.setText(dynamicItem.getSend_name());
        //viewHolder.iv_news_small.setImageResource(R.drawable.bg_ice);
        viewHolder.tv_send_level.setText(dynamicItem.getSend_level());
        viewHolder.tv_sex.setText(dynamicItem.getSend_sex());
        if("女".equals(dynamicItem.getSend_sex())){
            viewHolder.tv_sex.setTextColor(getContext().getResources().getColor(R.color.colorAccent));
            viewHolder.tv_sex.setText(" ♀ ");
        }
        else{
            viewHolder.tv_sex.setTextColor(getContext().getResources().getColor(R.color.account_pressed_true));
            viewHolder.tv_sex.setText(" ♂ ");
        }

        viewHolder.tx_sendrank.setText(dynamicItem.getSend_rank());
        viewHolder.tx_sendtime.setText(dynamicItem.getSend_time());
        viewHolder.tx_sendcontent.setText(dynamicItem.getSend_content());
        viewHolder.iv_sendimage1.setImageResource(R.drawable.lunbo1);
        viewHolder.iv_sendimage2.setImageResource(R.drawable.lunbo3);
        viewHolder.iv_sendimage3.setImageResource(R.drawable.lunbo1);
        viewHolder.tv_commit_num.setText(String.valueOf(dynamicItem.getCommit_num()));
        viewHolder.tv_likenum.setText(String.valueOf(dynamicItem.getLike_num()));

        viewHolder.btn_like.setOnCheckStateChangeListener(new ShineButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View view, boolean checked) {
                if(checked){
                    viewHolder.tv_likenum.setTextColor(getContext().getResources().getColor(R.color.bottom_bar_click));
                    dynamicItem.setCommit_num(dynamicItem.getLike_num()+1);
                    viewHolder.tv_likenum.setText(String.valueOf(dynamicItem.getLike_num()));
                }
                else{
                    viewHolder.tv_likenum.setTextColor(getContext().getResources().getColor(R.color.black_alpha_128));
                    dynamicItem.setCommit_num(dynamicItem.getLike_num()-1);
                    viewHolder.tv_likenum.setText(String.valueOf(dynamicItem.getLike_num()));
                }
            }
        });
        return view;    // 每个子项被滚动到屏幕内的时候会调用getView()方法

    }
}