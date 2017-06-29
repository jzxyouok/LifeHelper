package com.xp.lifehelper.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xp.lifehelper.NewsInfoActivity;
import com.xp.lifehelper.R;
import com.xp.lifehelper.bean.News;

import java.util.List;

/**
 * Created by xp on 2017/5/23.
 */
public class BannerAdapter extends PagerAdapter  {
    private Context context;
    private List<News.TopStory> topStories;
    private LayoutInflater inflater;

    public BannerAdapter(Context context,List<News.TopStory> topStories) {
        this.context = context;
        this.topStories = topStories;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
//        return topStories.size();
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final int tempPosition=position%topStories.size();
        View view=inflater.inflate(R.layout.item_news_top_vp,container,false);
        ImageView imageView =(ImageView)view.findViewById(R.id.image);
        TextView textView =(TextView)view.findViewById(R.id.title);
        Glide.with(context).load(topStories.get(tempPosition).image).into(imageView);
        textView.setText(topStories.get(tempPosition).title);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,NewsInfoActivity.class);
                intent.putExtra("id",topStories.get(tempPosition).id+"");
                intent.putExtra("image",topStories.get(tempPosition).image);
                intent.putExtra("title",topStories.get(tempPosition).title);
                context.startActivity(intent);
            }
        });
        container.addView(view);
        return view;
    }
}
