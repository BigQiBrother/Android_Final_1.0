package hznu.edu.cn.android_final.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import hznu.edu.cn.android_final.R;
import hznu.edu.cn.android_final.beans.NewsItem;
import hznu.edu.cn.android_final.utils.FileUtilcll;
import hznu.edu.cn.android_final.utils.ImageUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ReleaseDynamicActivity extends AppCompatActivity implements View.OnClickListener , TextWatcher {
    // Toolbar
    private ImageView iv_back;
    private Button btn_release;

    // 内容主体
    private EditText et_release_content;
    private ImageView iv_release_image1;
    private ImageView iv_release_image2;
    private ImageView iv_release_image3;
    private ImageView iv_release_image4;
    private ImageView iv_release_image5;
    private ImageView iv_release_image6;

    // 底部
    private ImageView iv_release_photo;
    private TextView tv_content_num;

    String path1="",path2="",path3="";
    Intent intent;

    AlertDialog.Builder back_dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_dynamic);

        initView();


    }
    private void initView(){
        // 绑定控件
        iv_back=(ImageView)findViewById(R.id.iv_back);
        btn_release=(Button)findViewById(R.id.btn_release);

        // 内容主体
        et_release_content=(EditText)findViewById(R.id.et_release_content) ;
        iv_release_image1=(ImageView)findViewById(R.id.iv_release_image1);
        iv_release_image2=(ImageView)findViewById(R.id.iv_release_image2);
        iv_release_image3=(ImageView)findViewById(R.id.iv_release_image3);
        iv_release_image4=(ImageView)findViewById(R.id.iv_release_image4);
        iv_release_image5=(ImageView)findViewById(R.id.iv_release_image5);
        iv_release_image6=(ImageView)findViewById(R.id.iv_release_image6);



        // 底部
        iv_release_photo=(ImageView)findViewById(R.id.iv_release_image6);
        tv_content_num=(TextView)findViewById(R.id.tv_content_num) ;

        // 注册点击事件
        iv_back.setOnClickListener(this);
        btn_release.setOnClickListener(this);
        et_release_content.setOnClickListener(this);
        iv_release_image1.setOnClickListener(this);
        iv_release_image2.setOnClickListener(this);
        iv_release_image3.setOnClickListener(this);
        iv_release_image4.setOnClickListener(this);
        iv_release_image5.setOnClickListener(this);
        iv_release_image6.setOnClickListener(this);
        iv_release_photo.setOnClickListener(this);

        et_release_content.addTextChangedListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                if(et_release_content.getText().length()>0){
                    back_dialog = new AlertDialog.Builder(ReleaseDynamicActivity.this);
                    back_dialog.setTitle("Progress Value");
                    back_dialog.setMessage("是否放弃本次编辑？");// 获取progress进度条的值
                    back_dialog.setCancelable(false);
                    back_dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 点击确定退出对话框
                            finish();
                        }
                    });
                    back_dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 点击取消dialog对话框
                        }
                    });
                    back_dialog.show();
                }
                else{
                    finish();
                }
                break;
            case R.id.iv_release_image1:
                iv_release_image1.setScaleType(ImageView.ScaleType.CENTER_CROP);
                intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent,2);
                iv_release_image1.setEnabled(false);
                iv_release_image2.setImageResource(R.drawable.addmore);
                iv_release_image2.setVisibility(View.VISIBLE);
            case R.id.iv_release_image2:
                iv_release_image2.setScaleType(ImageView.ScaleType.CENTER_CROP);
                intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent,3);
                iv_release_image2.setEnabled(false);
                iv_release_image3.setImageResource(R.drawable.addmore);
                iv_release_image3.setVisibility(View.VISIBLE);
            case R.id.iv_release_image3:
                iv_release_image3.setScaleType(ImageView.ScaleType.CENTER_CROP);

                intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent,4);
                iv_release_image3.setEnabled(false);
                iv_release_image4.setImageResource(R.drawable.addmore);
                iv_release_image4.setVisibility(View.VISIBLE);

            case R.id.btn_release:
                Log.d("onClickmore", "onClick: more");
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        File file = new File(path1);

                        upImage(file);

                    }
                }).start();

        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String release_content=et_release_content.getText().toString().trim();
        tv_content_num.setText(String.valueOf(1000-release_content.length()));
        if(release_content.length()>0){
            btn_release.setTextColor(getResources().getColor(R.color.bottom_bar_click));
            btn_release.setBackgroundResource(R.drawable.release_dynamic);
            btn_release.setEnabled(true);
        }
        else{
            btn_release.setTextColor(getResources().getColor(R.color.black_alpha_128));
            btn_release.setBackgroundResource(R.drawable.un_release_dynamic);
            btn_release.setEnabled(false);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            Log.d("errer", "canceled or other exception!");
            return;
        }
        if (requestCode == 1) {
            if(data!=null) {

                //获得压缩后的图片
                Bitmap bitmap =(Bitmap) data.getExtras().get("data");
                // bitmap=getBitmap(bitmap);
                bitmap=ImageUtils.getZoomImage(bitmap,20.00);
//                image=ImageUtils.bitToStr(bitmap);

                //将图片显示在图片控件中
                iv_release_image1.setImageBitmap(bitmap);

            }
        }
        else if(requestCode==2){
            if(data!=null){
                //获取图片定位符
                Uri uri = data.getData();
                Log.d("uri", "onActivityResult: "+uri);
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                    Log.d("bitmap", "onActivityResult: "+bitmap);
                    // bitmap=getBitmap(bitmap);
                    //bitmap= ImageUtils.getZoomImage(bitmap,20.00);
//                    image=ImageUtils.bitToStr(bitmap);
                    //将图片显示在图片控件中
                    iv_release_image1.setImageBitmap(bitmap);
                    //把bitmap对象转换为String
                    path1 = FileUtilcll.saveFile(this, "pic1.jpg", bitmap);
                    Log.d("path1", "onActivityResult: "+path1);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        else if(requestCode==3){
            if(data!=null){
                //获取图片定位符
                Uri uri = data.getData();
                Log.d("uri3", "onActivityResult: "+uri);
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));

                    // bitmap=getBitmap(bitmap);
                    //bitmap= ImageUtils.getZoomImage(bitmap,20.00);
//                    image=ImageUtils.bitToStr(bitmap);

                    //将图片显示在图片控件中
                    iv_release_image2.setImageBitmap(bitmap);
                    //把bitmap对象转换为String
                    path2 = FileUtilcll.saveFile(this, "pic2.jpg", bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
        else if(requestCode==4){
            if(data!=null){
                //获取图片定位符
                Uri uri = data.getData();
                Log.d("uri", "onActivityResult: "+uri);
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));

                    // bitmap=getBitmap(bitmap);
                    //bitmap= ImageUtils.getZoomImage(bitmap,20.00);
//                    image=ImageUtils.bitToStr(bitmap);

                    //将图片显示在图片控件中
                    iv_release_image3.setImageBitmap(bitmap);
                    //把bitmap对象转换为String
                    path3 = FileUtilcll.saveFile(this, "pic3.jpg", bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
    }
    // 图片压缩函数
    private   Bitmap getBitmap(Bitmap original) {
        Bitmap bitmap = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            int picWidth = options.outWidth;
            int picHeight = options.outHeight;
            WindowManager windowManager = getWindowManager();
            Display display = windowManager.getDefaultDisplay();
            int screenWidth = display.getWidth();
            int screenHeight = display.getHeight();
            options.inSampleSize = 1;
            if (picWidth > picHeight) {
                if (picWidth > screenWidth)
                    options.inSampleSize = picWidth / screenWidth;
            } else {
                if (picHeight > screenHeight)
                    options.inSampleSize = picHeight / screenHeight;
            }
            options.inJustDecodeBounds = false;

            //创建输出流
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //将原图压缩成png
            original.compress(Bitmap.CompressFormat.PNG, 100, baos);
            //转换成inputstream
            InputStream isBm = new ByteArrayInputStream(baos.toByteArray());
            //进行压缩处理
            bitmap = BitmapFactory.decodeStream(isBm, null, options);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }


    private void upImage(File fileName) {

        OkHttpClient client = new OkHttpClient();
        // form 表单形式上传
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (fileName != null) {
            // MediaType.parse() 里面是上传的文件类型。
            RequestBody body = RequestBody.create(MediaType.parse("image/*"), fileName);
            String filename = fileName.getName();
            // 参数分别为， 请求key ，文件名称 ， RequestBody
            requestBody.addFormDataPart("news", filename, body);
        }

        Request request = new Request.Builder()
                .url("http://10.0.2.2:8888/JSP_Final/SubmitImage_Servlet")
                .post(requestBody.build()).build();
        // readTimeout("请求超时时间" , 时间单位);
        client.newBuilder().readTimeout(5000, TimeUnit.MILLISECONDS).build().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                    if (response != null && response.isSuccessful()) {

                        final String json = response.body().string();
                    }
               }

        });
    }

}
