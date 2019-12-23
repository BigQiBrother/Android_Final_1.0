package hznu.edu.cn.android_final.model;

import android.os.Bundle;
import android.os.Message;

import org.json.JSONArray;

import hznu.edu.cn.android_final.fragment.DynamicFragment;
import hznu.edu.cn.android_final.fragment.NewsFragment;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DynamicModel {
    // 调用servlet验证登录
    public static void sendRequestWithOkHttp_for_dynamic() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody=new FormBody.Builder().build();
                    Request request1=new Request.Builder().url("http://10.0.2.2:8888/JSP_Final/GetDynamic_Servlet").post(requestBody).build();
                    Response response = client.newCall(request1).execute();
                    String responseData = response.body().string();
//                    Log.d("123", "run: "+responseData);
                    JSONArray obj = new JSONArray(responseData);
                    Bundle bundle=new Bundle();
                    bundle.putString("data",responseData);
                    Message msg = new Message();
                    msg.what=3;
                    msg.setData(bundle);
                    DynamicFragment.mHandler.sendMessage(msg);//向Handler发送消息，
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
