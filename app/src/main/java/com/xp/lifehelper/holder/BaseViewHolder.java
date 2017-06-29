package com.xp.lifehelper.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by xp on 2017/5/23.
 */
public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder{
    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    /**
     * 给控件设置数据
     */
    public  abstract void bindData(T data);
}
