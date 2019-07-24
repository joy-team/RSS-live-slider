package app.gathering_rss.d2_campus_fest;

import android.util.Log;

import com.tickaroo.tikxml.annotation.Element;
import com.tickaroo.tikxml.annotation.Path;
import com.tickaroo.tikxml.annotation.PropertyElement;
import com.tickaroo.tikxml.annotation.Xml;

import java.util.List;

@Xml
public class Rss {

    @Path("channel")
    @PropertyElement
    private String title;

    @Path("channel")
    @PropertyElement
    private String link;

    @Path("channel/image")
    @PropertyElement(name="url")
    private String imgUrl;

    @Path("channel")
    @Element(name="item")
    private List<Article> articles;

    public Rss() {
    }

    public Rss(String title, String link, String imgUrl, List<Article> articles) {
        this.title = title;
        this.link = link;
        this.imgUrl = imgUrl;
        this.articles = articles;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        try {
            String[] strArr = title.split(" ");
            this.title = strArr[0];
        } catch (Exception e) {
            Log.d("rss", "" + e);
        }
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
