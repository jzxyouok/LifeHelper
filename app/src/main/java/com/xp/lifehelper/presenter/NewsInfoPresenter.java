package com.xp.lifehelper.presenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.xp.lifehelper.bean.NewsInfo;
import com.xp.lifehelper.contract.NewsInfoContract;
import com.xp.lifehelper.util.Api;
import com.xp.lifehelper.util.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by xp on 2017/5/23.
 */
public class NewsInfoPresenter implements NewsInfoContract.Presenter {
    private Activity activity;
    private NewsInfoContract.View view;
    private String id;

    public NewsInfoPresenter(Activity activity,String id,NewsInfoContract.View view) {
        this.id = id;
        this.activity = activity;
        this.view = view;
    }

    @Override
    public void loadData() {
        view.showLoading();
        HttpUtil.sendHttpRequest(Api.INFO_NEWS_URL+id, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.stopLoading();
                        view.showError();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson=new Gson();
                String json=response.body().string();
                try{
                    final NewsInfo newsInfo = gson.fromJson(json, NewsInfo.class);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.stopLoading();
                            view.showData(newsInfo);
                        }
                    });
                }catch (Exception e){
                    view.stopLoading();
                    view.showError();
                }
            }
        });
    }

    @Override
    public void start() {
        loadData();
    }
}
