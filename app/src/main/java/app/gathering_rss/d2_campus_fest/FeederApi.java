package app.gathering_rss.d2_campus_fest;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FeederApi {
    @GET(Constant.HYUNJIN)
    Call<Rss> getChannel();
}
