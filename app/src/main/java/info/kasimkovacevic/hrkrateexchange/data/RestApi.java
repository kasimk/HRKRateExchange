package info.kasimkovacevic.hrkrateexchange.data;

import java.util.List;

import info.kasimkovacevic.hrkrateexchange.data.models.ExchangeRateModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author Kasim Kovacevic
 */
public interface RestApi {

    @GET("/api/v1/rates/daily")
    Call<List<ExchangeRateModel>> loadCurrenciesForDate(@Query("date") String date);

    @GET("/api/v1/rates/{currencyCode}")
    Call<List<ExchangeRateModel>> loadCurrencyForRange(@Path("currencyCode") String currencyCode, @Query("from") String fromDate, @Query("to") String toDate);

}
