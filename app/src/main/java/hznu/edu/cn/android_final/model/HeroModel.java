package hznu.edu.cn.android_final.model;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import hznu.edu.cn.android_final.activity.IndexActivity;
import hznu.edu.cn.android_final.activity.LoginActivity;
import hznu.edu.cn.android_final.activity.RaidersHero_Activity;
import hznu.edu.cn.android_final.fragment.NewsFragment;
import hznu.edu.cn.android_final.fragment.RaidersFragment;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HeroModel {
    // 调用servlet验证登录
    public static void sendRequestWithOkHttp_for_hero(final String type,final String id) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody=new FormBody.Builder().add("type",type).add("id",id).build();
                    Request request1=new Request.Builder().url("http://10.0.2.2:8888/JSP_Final/DayWinrate_Servlet").post(requestBody).build();
                    Response response = client.newCall(request1).execute();
                    String responseData = response.body().string();
                    // Log.d("123", "run: "+responseData);
                    // JSONObject object=new JSONObject(responseData);
                    Bundle bundle=new Bundle();
                    bundle.putString("data",responseData);
                    Message msg = new Message();
                    msg.what=2;
                    msg.setData(bundle);
                    RaidersHero_Activity.mHandler.sendMessage(msg);//向Handler发送消息，
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}