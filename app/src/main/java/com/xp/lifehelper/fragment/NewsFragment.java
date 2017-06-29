package com.xp.lifehelper.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.xp.lifehelper.NewsInfoActivity;
import com.xp.lifehelper.R;
import com.xp.lifehelper.adapter.NewsAdapter;
import com.xp.lifehelper.bean.News;
import com.xp.lifehelper.contract.NewsContract;
import com.xp.lifehelper.presenter.NewsPresenter;

import java.util.Calendar;
import java.util.List;

/**
 * Created by xp on 2017/5/22.
 */
public class NewsFragment  extends Fragment implements NewsContract.View{
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipRefresh;
    private NewsContract.Presenter presenter;
    private FloatingActionButton floating;
    private View view;
    private NewsAdapter adapter;
    private Calendar calendar ;
    private long currentTime=0;
    private boolean loadingMore;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news, container, false);
        recyclerView= (RecyclerView) view.findViewById(R.id.recyler_view);
        floating= (FloatingActionButton) view.findViewById(R.id.floating);
        swipRefresh= (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        presenter=new NewsPresenter(getActivity(),this);
        calendar = Calendar.getInstance();
        presenter.start();
        currentTime=System.currentTimeMillis();
        swipRefresh.setColorSchemeResources(R.color.colorPrimary,R.color.colorAccent);
        swipRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentTime=System.currentTimeMillis();
                presenter.refresh();
            }
        });
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean scroll=false;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                LinearLayoutManager manager= (LinearLayoutManager) recyclerView.getLayoutManager();
                int itemCount = manager.getItemCount();
                //当前页最后一个完全显示的item
                int lastPosition = manager.findLastCompletelyVisibleItemPosition();

                if(itemCount-1==lastPosition&&scroll&&!loadingMore){
                     //加载更多
                    presenter.loadMore(currentTime,false);
                    loadingMore=true;
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                scroll=dy>0;
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar can=Calendar.getInstance();
                        can.set(year,monthOfYear,dayOfMonth);
                        currentTime=can.getTimeInMillis();
                        presenter.loadMore(currentTime,true);
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                dialog.setMaxDate(Calendar.getInstance());
                dialog.show(getActivity().getFragmentManager(),"");
            }
        });
        return view;
    }

    @Override
    public void showError() {
        loadingMore=false;
        Snackbar.make(view,"加载失败",Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void stopLoading() {
        loadingMore=false;
        swipRefresh.setRefreshing(false);
    }

    @Override
    public void showResults(List<News> newsList) {
        loadingMore=false;
        currentTime-=24*60*60*1000;
        if(adapter==null){
            adapter=new NewsAdapter(getActivity(),newsList);
            adapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(News.Story story) {
                    Intent intent=new Intent(getContext(),NewsInfoActivity.class);
                    intent.putExtra("id",story.id+"");
                    intent.putExtra("image",story.images.get(0));
                    intent.putExtra("title",story.title);
                    getActivity().startActivity(intent);
                }
            });
            recyclerView.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setPresenter(NewsContract.Presenter presenter) {
        this.presenter=presenter;
    }
}
