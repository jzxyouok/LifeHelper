package com.xp.lifehelper.contract;

import com.xp.lifehelper.base.BasePresenter;
import com.xp.lifehelper.base.BaseView;
import com.xp.lifehelper.bean.NewsInfo;

/**
 * Created by xp on 2017/5/23.
 */
public class NewsInfoContract {
    public interface View extends BaseView<Presenter> {
        void stopLoading();
        void showLoading();
        void showError();
        void showData(NewsInfo newsInfo);
    }
    public interface Presenter extends BasePresenter {
        void loadData();
    }
}
