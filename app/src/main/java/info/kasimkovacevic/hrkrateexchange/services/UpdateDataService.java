package info.kasimkovacevic.hrkrateexchange.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.util.Date;
import java.util.List;

import info.kasimkovacevic.hrkrateexchange.data.RestApi;
import info.kasimkovacevic.hrkrateexchange.data.RestClientRouter;
import info.kasimkovacevic.hrkrateexchange.data.database.realm.RealmUtil;
import info.kasimkovacevic.hrkrateexchange.data.models.ExchangeRateModel;
import info.kasimkovacevic.hrkrateexchange.utils.DateUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Service used for updating data in database
 *
 * @author Kasim Kovacevic
 */
public class UpdateDataService extends IntentService {

    public UpdateDataService() {
        super("UpdateDataService");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public UpdateDataService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        List<ExchangeRateModel> mExchangeRates = null;
        Date databaseDate = DateUtil.getCurrentDateWithoutTime(DateUtil.DATE_FORMAT_YYYY_MM_DD);
        if (databaseDate != null) {
            mExchangeRates = RealmUtil.getDataFromRealm(databaseDate);
        }
        if (mExchangeRates == null || mExchangeRates.size() == 0) {
            RestApi restApi = RestClientRouter.get();
            String date = DateUtil.getFormattedDate(new Date(), DateUtil.DATE_FORMAT_YYYY_MM_DD);
            restApi.loadCurrenciesForDate(date).enqueue(new Callback<List<ExchangeRateModel>>() {
                @Override
                public void onResponse(Call<List<ExchangeRateModel>> call, Response<List<ExchangeRateModel>> response) {
                    List<ExchangeRateModel> mExchangeRates = response.body();
                    RealmUtil.prepareAndSaveDataToRealm(mExchangeRates);
                }

                @Override
                public void onFailure(Call<List<ExchangeRateModel>> call, Throwable t) {
                    Log.e(UpdateDataService.class.getSimpleName(), t.getMessage());
                }
            });
        }
        RealmUtil.clearOldData(DateUtil.getDateWeekAgoWithoutTime(DateUtil.DATE_FORMAT_YYYY_MM_DD));
    }
}
