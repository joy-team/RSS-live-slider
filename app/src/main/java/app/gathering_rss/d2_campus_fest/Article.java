package app.gathering_rss.d2_campus_fest;

import com.tickaroo.tikxml.annotation.PropertyElement;
import com.tickaroo.tikxml.annotation.Xml;

@Xml
public class Article {

    @PropertyElement
    private String link;

    @PropertyElement
    private String description;

    @PropertyElement
    private String pubDate;

    public Article() {
    }

    public Article(String link, String description, String pubDate) {
        this.link = link;
        this.description = description;
        this.pubDate = pubDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }
}
