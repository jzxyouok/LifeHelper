package com.xp.lifehelper.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by xp on 2017/5/22.
 */
public class News {
    private String date;
    @SerializedName("top_stories")
    private List<TopStory> topStories;
    @SerializedName("stories")
    private List<Story> stories;

    public int getCount(){

        return  0;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<TopStory> getTopStories() {
        return topStories;
    }

    public void setTopStories(List<TopStory> topStories) {
        this.topStories = topStories;
    }

    public List<Story> getStories() {
        return stories;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
    }

    public class TopStory {
        public String image;
        public int id;
        public int type;
        public String title;
    }
    public class Story {
        public List<String> images;
        public String ga_prefix;
        public int id;
        public int type;
        public String title;
        public boolean multipic;
    }
}
