package com.xp.lifehelper.holder;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xp.lifehelper.R;
import com.xp.lifehelper.adapter.BannerAdapter;
import com.xp.lifehelper.bean.News;

import java.util.List;

/**
 * Created by xp on 2017/5/23.
 */
public class TopViewHolder extends BaseViewHolder<List<News.TopStory>>{
    private ViewPager viewPager;
    private LinearLayout dotsLayout;
    private LinearLayout rootLayout;
    private final Context context;

    public TopViewHolder(View view, Context context) {
        super(view);
        viewPager= (ViewPager) view.findViewById(R.id.viewpager);
        dotsLayout= (LinearLayout) view.findViewById(R.id.dot_indicator);
        rootLayout= (LinearLayout) view.findViewById(R.id.top_root_layout);
        this.context = context;
    }

    @Override
    public void bindData(final List<News.TopStory> topStories) {
        if(topStories!=null&&topStories.size()!=0){
            ViewGroup.LayoutParams layoutParams = rootLayout.getLayoutParams();
            layoutParams.height=(int)rootLayout.getContext().getResources().getDimension(R.dimen.viewpager_height);
            rootLayout.setLayoutParams(layoutParams);
            rootLayout.setVisibility(View.VISIBLE);
            int size=(int)rootLayout.getContext().getResources().getDimension(R.dimen.point_size);
            dotsLayout.removeAllViews();
            for(int i=0;i<topStories.size();i++){
                ImageView point = new ImageView(context);
                LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(size, size);
                params.rightMargin=size;
                point.setLayoutParams(params);
                point.setBackgroundResource(R.drawable.selector_point);
                point.setEnabled(i==0);
                dotsLayout.addView(point);
            }
            BannerAdapter adapter=new BannerAdapter(viewPager.getContext(),topStories);
            viewPager.setAdapter(adapter);
            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                private int lastPosition;
                public void onPageScrolled(int i, float v, int i1) {
                }
                @Override
                public void onPageSelected(int position) {
                    position = position%topStories.size();
                    dotsLayout.getChildAt(position).setEnabled(true);
                    //把上一个点设为false
                    dotsLayout.getChildAt(lastPosition).setEnabled(false);
                    lastPosition = position;
                }
                @Override
                public void onPageScrollStateChanged(int i) {
                }
            });
        }else{
            ViewGroup.LayoutParams layoutParams = rootLayout.getLayoutParams();
            layoutParams.height=0;
            rootLayout.setLayoutParams(layoutParams);
            rootLayout.setVisibility(View.GONE);
        }

    }
}
