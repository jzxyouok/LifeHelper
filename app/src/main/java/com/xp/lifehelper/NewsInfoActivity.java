package com.xp.lifehelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.xp.lifehelper.bean.NewsInfo;
import com.xp.lifehelper.contract.NewsInfoContract;
import com.xp.lifehelper.presenter.NewsInfoPresenter;

/**
 * Created by xp on 2017/5/23.
 */
public class NewsInfoActivity extends AppCompatActivity implements NewsInfoContract.View{
    private ImageView imageView;
    private WebView webView;
    private NestedScrollView scrollView;
    private CollapsingToolbarLayout toolbarLayout;
    private SwipeRefreshLayout refreshLayout;
    private NewsInfoContract.Presenter presenter;
    private Toolbar toolbar;
    private String title;
    private String image;
    private NewsInfo newsInfo;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        String id = getIntent().getStringExtra("id");
        image = getIntent().getStringExtra("image");
        title = getIntent().getStringExtra("title");



        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        presenter=new NewsInfoPresenter(this,id,this);
        presenter.start();
        toolbarLayout= (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        toolbarLayout.setTitle(title);
        webView = (WebView)findViewById(R.id.web_view);
        webView.setScrollbarFadingEnabled(true);
        imageView = (ImageView) findViewById(R.id.image);
        Glide.with(this).load(image).into(imageView);

        scrollView = (NestedScrollView) findViewById(R.id.scrollView);
        //能够和js交互
        webView.getSettings().setJavaScriptEnabled(true);
        //缩放,设置为不能缩放可以防止页面上出现放大和缩小的图标
        webView.getSettings().setBuiltInZoomControls(false);
        //缓存
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        //开启DOM storage API功能
        webView.getSettings().setDomStorageEnabled(true);
        //开启application Cache功能
        webView.getSettings().setAppCacheEnabled(false);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }

        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.start();
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_newsinfo,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
                case R.id.share:
                    if(newsInfo==null){
                        Toast.makeText(NewsInfoActivity.this, "请加载完毕后再试", Toast.LENGTH_SHORT).show();
                    }else{
                        share(newsInfo.getTitle(),newsInfo.getShare_url());
                    }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void share(String tit,String shareUrl) {
        UMWeb umWeb=new UMWeb(shareUrl);//分享的链接需要以http开头
        umWeb.setTitle(tit);
        umWeb.setThumb(new UMImage(this,image));//缩略图
        umWeb.setDescription(title);
        new ShareAction(NewsInfoActivity.this)
//                .withText("hello")//QQ不支持春文本
                .withMedia(umWeb)
                .setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN)
                .setCallback(umShareListener)
                .open();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
        }
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(NewsInfoActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(NewsInfoActivity.this,platform + " 分享失败啦"+t.getMessage(), Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(NewsInfoActivity.this,platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    public void stopLoading() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void showError() {

    }

//    @Override
//    public void showData(String result) {
//        String css="<link rel=\"stylesheet\" href=\"file:///android_asset/zhihu.css\" type=\"text/css\" />";
//        webView.loadDataWithBaseURL("x-data://base",css+result,"text/html","utf-8",null);
//    }
    @Override
    public void showData(NewsInfo newsInfo) {
        this.newsInfo=newsInfo;
        String css="<link rel=\"stylesheet\" href=\"file:///android_asset/zhihu.css\" type=\"text/css\" />";
        webView.loadDataWithBaseURL("x-data://base",css+newsInfo.getBody(),"text/html","utf-8",null);
    }
    @Override
    public void setPresenter(NewsInfoContract.Presenter presenter) {
        this.presenter=presenter;
    }
}
