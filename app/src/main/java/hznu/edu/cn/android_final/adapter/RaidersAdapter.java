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

import java.util.List;
import java.util.Random;

import hznu.edu.cn.android_final.R;
import hznu.edu.cn.android_final.beans.NewsItem;
import hznu.edu.cn.android_final.beans.RaidersItem;


public class RaidersAdapter extends ArrayAdapter<RaidersItem> {
    //设置图片圆角角度
    RoundedCorners roundedCorners= new RoundedCorners(20);
    //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
    RequestOptions options=RequestOptions.bitmapTransform(roundedCorners);

    private int resourceId; // 当前子项的布局id
    public RaidersAdapter(Context context, int textViewResourceId, List<RaidersItem> objects){    // 重写父类构造方法
        super(context,textViewResourceId,objects);
        this.resourceId=textViewResourceId;
    }
    class ViewHolder{   // 定义ViewHolder保存id，无需多次findViewById
        TextView tv_count;
        ImageView iv_raiders_small;
        TextView tv_hero;
        TextView tv_position;
        TextView tv_winrate;
        TextView tv_appearancerate;
        TextView tv_banrate;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent){        // 重写父类getView()方法
//      final int final_position = position;            // 将position设成final便于在内部事件监听类中使用
        String[] hero_position_list={"上单","打野","中单","下路","辅助"};
        String hero_position="";
        for(int i=0;i<new Random().nextInt(2)+1;i++){
            String str=hero_position_list[new Random().nextInt(5)];
            if(hero_position.indexOf(str)==-1){
                hero_position+=str+" ";
            }
            else{
                continue;
            }
        }

        RaidersItem raidersItem=getItem(position);            // 由position获取fruit链表中当前子项对应的fruit对象

        View view;
        ViewHolder viewHolder;
        if(convertView==null) {                         // 使用缓存记录
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);   // 获取当前子项布局
            viewHolder = new ViewHolder();                                              // 将控件实例保存到viewHolder中
            viewHolder.tv_count=(TextView) view.findViewById(R.id.tv_count);
            viewHolder.iv_raiders_small=(ImageView)view.findViewById((R.id.iv_raiders_small));
            viewHolder.tv_hero=(TextView)view.findViewById(R.id.tv_hero);
            viewHolder.tv_position=(TextView)view.findViewById(R.id.tv_position);
            viewHolder.tv_winrate=(TextView)view.findViewById(R.id.tv_winrate);
            viewHolder.tv_appearancerate=(TextView)view.findViewById(R.id.tv_appearancerate);
            viewHolder.tv_banrate=(TextView)view.findViewById(R.id.tv_banrate);

            view.setTag(viewHolder);                                                    // 将viewHolder保存到view中
        }
        else{
            view=convertView;                           // view赋值缓存
            viewHolder=(ViewHolder) view.getTag();      // 获取之前view中保存的viewHolder对象.
        }
        if(raidersItem.getVisited()){
            viewHolder.tv_hero.setTextColor(getContext().getResources().getColor(R.color.listitem_visited_color));
        }
//        String path = "http://10.0.2.2:8888/Images/news/";
////        Log.d("getView", "getView: "+newsItem.getImage_name());
//        Glide.with(parent.getContext()).load(path+newsItem.getImage_name()).into(viewHolder.iv_news_small);
        viewHolder.tv_count.setText(raidersItem.getCount());
        viewHolder.iv_raiders_small.setImageResource(R.drawable.bg_yasuo);
        //viewHolder.iv_news_small.setImageResource(R.drawable.bg_ice);
        viewHolder.tv_hero.setText(raidersItem.getHero());
        viewHolder.tv_position.setText(hero_position);
        viewHolder.tv_winrate.setText(raidersItem.getWin_rate());
        viewHolder.tv_appearancerate.setText((raidersItem.getAppearance_rate()));
        viewHolder.tv_banrate.setText((raidersItem.getBan_rate()));
        return view;    // 每个子项被滚动到屏幕内的时候会调用getView()方法

    }
}
