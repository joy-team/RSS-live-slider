package app.gathering_rss.d2_campus_fest;

import android.util.Log;

import com.tickaroo.tikxml.annotation.PropertyElement;
import com.tickaroo.tikxml.annotation.Xml;

import java.util.ArrayList;

@Xml
public class Article {

    @PropertyElement
    private String title;

    @PropertyElement
    private String link;

    @PropertyElement
    private String description;

    @PropertyElement
    private String pubDate;

    private ArrayList<String> imgUrls;

    private ArrayList<String> vidUrls;

    public Article() {
    }

    public Article(String title, String link, String description, String pubDate) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.pubDate = pubDate;
    }

    public void setUrls() {
        imgUrls = new ArrayList<>();
        vidUrls = new ArrayList<>();

        /* Processing description */
        try {
            String[] strArr = this.description.split("<");
            for (String split: strArr) {
                if (split.length() > 0) {
                    if (split.charAt(0) == 'i') {
                        String[] strArr2 = split.split("\"");
                        imgUrls.add(strArr2[1]);
                    } else if (split.charAt(0) == 'v') {
                        String[] strArr2 = split.split("\"");
                        vidUrls.add(strArr2[1]);
                    }
                }
            }
        } catch (Exception e) {
            Log.d("Article", "" + e);
        }
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        setUrls();
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public ArrayList<String> getImgUrls() {
        return imgUrls;
    }

    public ArrayList<String> getVidUrls() {
        return vidUrls;
    }
}
