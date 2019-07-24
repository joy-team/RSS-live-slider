package app.gathering_rss.d2_campus_fest;

import com.tickaroo.tikxml.TikXml;
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;

/*
*
* TODO:
*       - Create API with user code
*       - Implement function for notifying new article
*       - Implement function for processing description to specific contents url
*       - Implement function for processing pubDate with custom format
*       - Implement function for loading image
*       - Implement function for loading video
*
*/

public class Feeder {

    private final String xml;
    private final Retrofit retrofit;
    private final FeederApi api;
    private static final OkHttpClient client = createClient();

    public Feeder(String xml) {
        this.xml = xml;
        this.retrofit = new Retrofit.Builder()
                .baseUrl(Constant.RSS_URL)
                .addConverterFactory(TikXmlConverterFactory.create(
                        new TikXml.Builder().exceptionOnUnreadXml(false).build()))
                .client(client)
                .build();
        this.api = this.retrofit.create(FeederApi.class);
    }

    private static OkHttpClient createClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);
        return builder.build();
    }

    public Call<Rss> callRss() {
        Call<Rss> callRss = api.getRss(this.xml);
        return callRss;
    }
}
