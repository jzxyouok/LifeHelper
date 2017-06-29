package com.xp.lifehelper.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.xp.lifehelper.R;
import com.xp.lifehelper.util.DateUtil;

/**
 * Created by xp on 2017/5/23.
 */
public class DataViewHolder extends BaseViewHolder<String> {
    private TextView dataText;
    public DataViewHolder(View view,Context context) {
        super(view);
        dataText= (TextView) view.findViewById(R.id.data);
    }

    @Override
    public void bindData(String date) {
        dataText.setText(DateUtil.format(date));
    }
}
