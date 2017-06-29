package com.xp.lifehelper.contract;

import com.xp.lifehelper.base.BasePresenter;
import com.xp.lifehelper.base.BaseView;
import com.xp.lifehelper.bean.News;

import java.util.List;

/**
 * Created by xp on 2017/5/22.
 */
public interface NewsContract {
    interface View extends BaseView<Presenter>{
        /**
         * 加载失败
         */
        void showError();

        /**
         * 开始加载
         */
        void showLoading();

        /**
         * 停止加载
         */
        void stopLoading();

        /**
         * 显示数据
         * @param newsList
         */
        void showResults(List<News> newsList);
    }
    interface Presenter extends BasePresenter{
        void loadData(String url,boolean clearList);

        void refresh();

        void loadMore(long date,boolean isClear);

    }
}

