package com.xp.lifehelper.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xp.lifehelper.R;
import com.xp.lifehelper.bean.News;
import com.xp.lifehelper.holder.DataViewHolder;
import com.xp.lifehelper.holder.DefaultViewHolder;
import com.xp.lifehelper.holder.MoreViewHolder;
import com.xp.lifehelper.holder.TopViewHolder;

import java.util.List;

/**
 * Created by xp on 2017/5/22.
 */
public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<News> newsList;
    private static final int ITEM_TOP=1;//头部轮播图
    private static final int ITEM_DEFAULT=2;//新闻
    private static final int ITEM_DATA=3;//时间
    private static final int ITEM_MORE=4;//加载更多
    private OnItemClickListener onItemClickListener;
    public NewsAdapter(Context context, List<News> newsList) {
        this.context = context;
        this.newsList = newsList;
    }
    public interface OnItemClickListener{
        void onItemClick(News.Story story);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        onItemClickListener=listener;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        View view;
        switch (viewType){
            case ITEM_TOP:
                view= LayoutInflater.from(context).inflate(R.layout.item_news_top,parent,false);
                holder= new TopViewHolder(view,context);
                break;
            case ITEM_DEFAULT:
                view= LayoutInflater.from(context).inflate(R.layout.item_news_default,parent,false);
                holder= new DefaultViewHolder(view,context);
                break;
            case ITEM_DATA:
                view= LayoutInflater.from(context).inflate(R.layout.item_news_data,parent,false);
                holder= new DataViewHolder(view,context);
                break;
            case ITEM_MORE:
                view= LayoutInflater.from(context).inflate(R.layout.item_news_moe,parent,false);
                holder= new MoreViewHolder(view,context);
                break;
            default:
        }
        return holder;

    }

    @Override
    public int getItemViewType(int position) {
        int firstIndexInGroup=1+1;//标题+头部
        if(position==0){
            return ITEM_TOP;
        }else if(position==getItemCount()-1){
            return ITEM_MORE;
        }else{
            for(News news:newsList){
                int size=news.getStories().size();
                int currentIndex=position-firstIndexInGroup;
                if(currentIndex==-1){//时间item
                    return ITEM_DATA;
                }else if(currentIndex<size){
                    return ITEM_DEFAULT;
                }
                firstIndexInGroup+=size+1;
            }
        }
        return -1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch (type){
            case ITEM_TOP:
                List<News.TopStory> topStories= (List<News.TopStory>) getItem(position);
                TopViewHolder topViewHolder=(TopViewHolder)holder;
                topViewHolder.bindData(topStories);
                break;
            case ITEM_DEFAULT:
                DefaultViewHolder defaultViewHolder=(DefaultViewHolder)holder;
                defaultViewHolder.setOnItemClickListener(onItemClickListener);
                News.Story story= (News.Story) getItem(position);
                defaultViewHolder.bindData(story);
                break;
            case ITEM_DATA:
                String date= (String) getItem(position);
                DataViewHolder dataViewHolder=(DataViewHolder)holder;
                dataViewHolder.bindData(date);
                break;
            case ITEM_MORE:
                break;
            default:
        }
    }

    @Override
    public int getItemCount() {
        int count=0;
        for(News news:newsList){
            count+=(news.getStories().size()+1);//标题+内容
        }
        Log.i("TAGTAG", "getItemCount: "+count);
        return count+2;//头部+底部
    }

    public Object getItem(int position){
        int firstIndexInGroup=1+1;//标题+头部
        if(position==0){
            return newsList.get(0).getTopStories();
        }else if(position==getItemCount()-1){
            return null;
        }else{
            for(News news:newsList){
                int size=news.getStories().size();
                int currentIndex=position-firstIndexInGroup;
                if(currentIndex==-1){//时间item
                    return news.getDate();
                }else if(currentIndex<size){
                    return news.getStories().get(currentIndex);
                }
                firstIndexInGroup+=size+1;
            }
        }
        return null;
    }
}
