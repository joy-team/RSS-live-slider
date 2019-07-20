package app.gathering_rss.d2_campus_fest;

import com.tickaroo.tikxml.TikXml;
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;

public class Feeder {

    private final String userCode;
    private final Retrofit retrofit;
    private final FeederApi api;
    private static final OkHttpClient client = createClient();

    public Feeder(String userCode) {
        this.userCode = userCode;
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

    public String getUrl() {
        return Constant.RSS_URL + this.userCode;
    }

    public Call<Rss> callRss() {
        Call<Rss> callRss = api.getChannel();
        return callRss;
    }
}
