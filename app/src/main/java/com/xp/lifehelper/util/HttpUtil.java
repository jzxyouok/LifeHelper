package com.xp.lifehelper.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by xp on 2017/5/22.
 */
public class HttpUtil {
    public static  void sendHttpRequest(String url,okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(url).build();
        client.newCall(request).enqueue(callback);
    }
}
