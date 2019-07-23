package app.gathering_rss.d2_campus_fest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FeederApi {
    @GET("{userCode}")
    Call<Rss> getChannel(@Path("userCode") String userCode);
}
