package app.gathering_rss.d2_campus_fest;

import java.util.ArrayList;

public class User {
    private String name;
    private String rssUrl;
    private ArrayList<Article> articles;

    public User() {
    }

    public User(String name, String rssUrl) {
        this.name = name;
        this.rssUrl = rssUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getrssUrl() {
        return rssUrl;
    }

    public void setrssUrl(String rssUrl) {
        this.rssUrl = rssUrl;
    }

    public ArrayList<Article> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }
}
