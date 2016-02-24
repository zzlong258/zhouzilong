package com.example.bb;

import java.io.IOException;
import java.io.InputStream;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private TextView tv;
    private ImageView iv;
    private OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        tv = (TextView) findViewById(R.id.tv);
        iv = (ImageView) findViewById(R.id.iv);

        postHttp();
        postHttp2();
        DownLoadImg();
    }
    
    public void postHttp(){
    	String url = "http://www.hengboit.com/json/json_list.php?cid=36&num=20";
    	//定义一个请求
    	Request request = new Request.Builder().url(url).build();
    	try {
			final Response response = client.newCall(request).execute();
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						tv.setText(response.body().string());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    public void postHttp2(){
    	String url = "http://api.bwphp.com/public/login";
    	//定义一个请求体
    	FormEncodingBuilder builder = new FormEncodingBuilder()
    	.add("user_data", "18033862235").add("user_pwd", "123456");
    	//定义网络请求
    	Request request = new Request.Builder().url(url)
    			.post(builder.build()).build();
    	//执行请求
    	client.newCall(request).enqueue(new Callback() {
			
			@Override
			public void onResponse(Response arg0) throws IOException {
				// TODO Auto-generated method stub
				if(arg0.isSuccessful()){
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
						}
					});
				}
			}
			
			@Override
			public void onFailure(Request arg0, IOException arg1) {
				// TODO Auto-generated method stub
				
			}
		});
    	
    }
    
    private void DownLoadImg(){
    	String url = "http://www.hengboit.com/images/wutu5.jpg";
    	//定义网络请求
    	Request request = new Request.Builder().url(url).build();
    	client.newCall(request).enqueue(new Callback() {
			
			@Override
			public void onResponse(Response arg0) throws IOException {
				// TODO Auto-generated method stub
				InputStream is = arg0.body().byteStream();
				
				updateimage(BitmapFactory.decodeStream(is));
			}
			
			@Override
			public void onFailure(Request arg0, IOException arg1) {
				// TODO Auto-generated method stub
				
			}
		});
    	
    }
    
    //显示图片的方法
    private void updateimage(final Bitmap bitmap){
    	iv.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(bitmap != null){
					iv.setImageBitmap(bitmap);
				}else{
					iv.setImageResource(R.drawable.ic_launcher);
				}
			}
		});
    }

}