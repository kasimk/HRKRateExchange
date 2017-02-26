package info.kasimkovacevic.hrkrateexchange.data;


import info.kasimkovacevic.hrkrateexchange.BuildConfig;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;


/**
 * @author Kasim Kovacevic
 */
public class RestClientRouter {

    private static RestApi mRestApi;
    private static final String REST_API_URL = BuildConfig.REST_API_URL;

    static {
        setupRestClient();
    }

    private RestClientRouter() {
    }

    public static synchronized RestApi get() {
        if (mRestApi == null) {
            setupRestClient();
        }
        return mRestApi;
    }

    private static void setupRestClient() {
        OkHttpClient client = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl(REST_API_URL).client(client).build();
        mRestApi = retrofit.create(RestApi.class);
    }
}