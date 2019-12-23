package hznu.edu.cn.android_final.beans;

import java.io.Serializable;

public class NewsItem implements Serializable {
    private String news_title;
    private String news_content;
    private String image_name;
    private String time;
    private String source;
    private Boolean visited;

    public NewsItem(String news_title, String news_content, String image_name, String time, String source, Boolean visited) {
        this.news_title = news_title;
        this.news_content = news_content;
        this.image_name = image_name;
        this.time = time;
        this.source = source;
        this.visited = visited;
    }
    public Boolean getVisited() {
        return visited;
    }

    public void setVisited(Boolean visited) {
        this.visited = visited;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getNews_title() {
        return news_title;
    }

    public void setNews_title(String news_title) {
        this.news_title = news_title;
    }

    public String getNews_content() {
        return news_content;
    }

    public void setNews_content(String news_content) {
        this.news_content = news_content;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }
}
