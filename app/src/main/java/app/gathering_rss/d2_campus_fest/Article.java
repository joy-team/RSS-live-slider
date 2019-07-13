package app.gathering_rss.d2_campus_fest;

import java.util.ArrayList;

public class Article {
    private String title;
    private String link;
    private String pubDate;
    private ArrayList<String> contentUrls;

    public Article() {
    }

    public Article(String title, String link, String pubDate, ArrayList<String> contentUrls) {
        this.title = title;
        this.link = link;
        this.pubDate = pubDate;
        this.contentUrls = contentUrls;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public ArrayList<String> getContentUrls() {
        return contentUrls;
    }

    public void setContentUrls(ArrayList<String> contentUrls) {
        this.contentUrls = contentUrls;
    }
}
