package com.xp.lifehelper.holder;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xp.lifehelper.R;
import com.xp.lifehelper.adapter.NewsAdapter;
import com.xp.lifehelper.bean.News;

/**
 * Created by xp on 2017/5/23.
 */
public class DefaultViewHolder extends BaseViewHolder<News.Story>{
    private TextView titleView;
    private ImageView imageView;
    private CardView cardView;
    private NewsAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(NewsAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public DefaultViewHolder(View view, Context context) {
        super(view);
        titleView= (TextView) view.findViewById(R.id.title);
        imageView= (ImageView) view.findViewById(R.id.image);
        cardView= (CardView) view.findViewById(R.id.card_view);
    }
    @Override
    public void bindData(final News.Story story) {
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(story);
            }
        });
        titleView.setText(story.title);
        Glide.with(imageView.getContext()).load(story.images.get(0)).placeholder(R.mipmap.ic_launcher).into(imageView);
    }
}
