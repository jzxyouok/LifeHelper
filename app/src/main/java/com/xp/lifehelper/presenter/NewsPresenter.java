package com.xp.lifehelper.presenter;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;
import com.xp.lifehelper.bean.News;
import com.xp.lifehelper.contract.NewsContract;
import com.xp.lifehelper.util.Api;
import com.xp.lifehelper.util.DateUtil;
import com.xp.lifehelper.util.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by xp on 2017/5/22.
 */
public class NewsPresenter implements NewsContract.Presenter {
    private Activity activity;
    private NewsContract.View view;
    private List<News> newsList;
    public NewsPresenter(Activity activity,NewsContract.View view) {
        this.activity = activity;
        this.view = view;
        view.setPresenter(this);
        newsList=new ArrayList<News>();
    }

    @Override
    public void loadData(final String url, boolean clearList) {
        if(clearList){
            newsList.clear();
        }
        HttpUtil.sendHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.showError();
                        view.stopLoading();
                    }
                });
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String result=response.body().string();
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                    Gson gson=new Gson();
                    try{
                        News news = gson.fromJson(result, News.class);
                        Log.i("null", "run: "+(news.getTopStories()==null)+url);
                        newsList.add(news);
                        view.showResults(newsList);
                        view.stopLoading();
                    }catch (Exception e){
                        view.stopLoading();
                        view.showError();
                    }
                    }
                });
            }
        });
    }

    @Override
    public void refresh() {
        loadData(Api.TODAY_NEWS_URL,true);
    }

    @Override
    public void loadMore(long date,boolean isClear) {
        date+=24*60*60*1000;//若果需要查询 11 月 18 日的消息，before 后的数字应为 20131119
        loadData(Api.BEFORE_NEWS_URL+ DateUtil.formatDateFromLong(date),isClear);
    }

    @Override
    public void start() {
        newsList.clear();
        loadData(Api.TODAY_NEWS_URL,true);
    }
}
