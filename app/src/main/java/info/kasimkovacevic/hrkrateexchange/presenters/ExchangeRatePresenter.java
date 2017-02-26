package info.kasimkovacevic.hrkrateexchange.presenters;

import java.util.Date;
import java.util.List;

import info.kasimkovacevic.hrkrateexchange.data.RestApi;
import info.kasimkovacevic.hrkrateexchange.data.RestClientRouter;
import info.kasimkovacevic.hrkrateexchange.data.database.realm.RealmUtil;
import info.kasimkovacevic.hrkrateexchange.data.models.ExchangeRateModel;
import info.kasimkovacevic.hrkrateexchange.utils.DateUtil;
import info.kasimkovacevic.hrkrateexchange.utils.NetworkUtil;
import info.kasimkovacevic.hrkrateexchange.views.ExchangeRateView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static info.kasimkovacevic.hrkrateexchange.utils.NetworkUtil.HTTP_STATUS_OK;

/**
 * @author Kasim Kovacevic
 */
public class ExchangeRatePresenter implements Presenter {

    private ExchangeRateView mExchangeRateView;
    private List<ExchangeRateModel> mExchangeRates;
    private Call mCall;

    @Override
    public void onResume() {
        loadData();
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroyView() {
        if (mCall != null) mCall.cancel();
    }

    @Override
    public void setView(Object view) {
        mExchangeRateView = (ExchangeRateView) view;
    }

    /**
     * Load list of {@link ExchangeRateModel} from database if is available, if not load data from api and store in database
     * update view with loaded list
     */
    public void loadData() {
        Date databaseDate = DateUtil.getCurrentDateWithoutTime(DateUtil.DATE_FORMAT_YYYY_MM_DD);
        if (databaseDate != null) {
            mExchangeRates = RealmUtil.getDataFromRealm(databaseDate);
        }
        if (mExchangeRates != null && mExchangeRates.size() > 0) {
            mExchangeRateView.showData(mExchangeRates);
        } else {
            RestApi restApi = RestClientRouter.get();
            String date = DateUtil.getFormattedDate(new Date(), DateUtil.DATE_FORMAT_YYYY_MM_DD);
            mCall = restApi.loadCurrenciesForDate(date);
            mCall.enqueue(new Callback<List<ExchangeRateModel>>() {
                @Override
                public void onResponse(Call<List<ExchangeRateModel>> call, Response<List<ExchangeRateModel>> response) {
                    if (response.code() == HTTP_STATUS_OK) {
                        mExchangeRates = response.body();
                        RealmUtil.prepareAndSaveDataToRealm(mExchangeRates);
                        mExchangeRateView.showData(mExchangeRates);
                    } else {
                        NetworkUtil.showError(mExchangeRateView, response.code());
                    }
                }

                @Override
                public void onFailure(Call<List<ExchangeRateModel>> call, Throwable t) {
                    mExchangeRateView.showInternetConnectionError();
                }
            });
        }
    }
}
