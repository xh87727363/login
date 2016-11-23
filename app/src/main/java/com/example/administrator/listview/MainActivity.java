package com.example.administrator.listview;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private TextView tv;
    private ImageView img;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0 :
                    tv.setText((String) msg.obj);
                    Toast.makeText(MainActivity.this,"成功",Toast.LENGTH_LONG).show();
                    break;
                case 1:
                    Toast.makeText(MainActivity.this,"失败",Toast.LENGTH_LONG).show();

            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.TextView);
        img = (ImageView) findViewById(R.id.ImageView);
        getInfo();

        img.setImageResource(R.drawable.pic);
    }
    public void getInfo(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                String str =null;
                BufferedReader br =null;
                try {
                    URL url = new URL("http://www.12306.cn/mormhweb/");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(5000);
                    conn.setConnectTimeout(5000);
                    if (conn.getResponseCode() == 200){
                        InputStream is = conn.getInputStream();
                        br = new BufferedReader(new InputStreamReader(is));
                        StringBuffer sb = new StringBuffer();
                        String line = null;
                        while((line = br.readLine()) != null){
                            sb.append(line);
                        }
                        str = sb.toString();
                        handler.sendMessage(handler.obtainMessage(0,str));
                        return;
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(br != null){
                        try {
                            br.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                handler.sendEmptyMessage(1);
            }
        }.start();
        int a =0;
        for (int i = 0; i < 10; i++) {
            a+=i;
            String string = "kaixin";
        }
    }
}
