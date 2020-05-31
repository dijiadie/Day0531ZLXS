package com.lt.day0531zlxs;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private int time = 5;
    private Button bt_home_time;
    private ImageView img_home_top;
    private ImageView img_home_bottom;
    private String info_url1;
    private Timer timer;
    private int heightPixels;
    private int widthPixels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.Base_Url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Observable<Bean> data = apiService.getData();
        data.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Bean>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Bean bean) {
                        info_url1 = bean.getResult().getInfo_url();
                        Log.i("111", "onNext: " + info_url1);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initView() {
        bt_home_time = (Button) findViewById(R.id.bt_home_time);
        img_home_top = (ImageView) findViewById(R.id.img_home_top);
        img_home_bottom = (ImageView) findViewById(R.id.img_home_bottom);
        timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time--;
                        bt_home_time.setText("跳过 " + time);
                        if (time ==3) {
                            getScreenWH();
                            img_home_top.setMaxHeight(heightPixels - 100);
                            img_home_bottom.setVisibility(View.VISIBLE);
                            bt_home_time.setVisibility(View.VISIBLE);
                            Glide.with(MainActivity.this).load(info_url1).into(img_home_top);
                        }
                    }
                });
                if (time == 1) {
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                    finish();
                }

            }
        }, 0, 1000);
        bt_home_time.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_home_time:
                startActivity(new Intent(MainActivity.this,HomeActivity.class));
                timer.cancel();
                finish();
                break;
        }
    }

    //获取屏幕宽和高
    private void getScreenWH() {
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display defaultDisplay = manager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        heightPixels = displayMetrics.heightPixels;
        widthPixels = displayMetrics.widthPixels;
    }
}