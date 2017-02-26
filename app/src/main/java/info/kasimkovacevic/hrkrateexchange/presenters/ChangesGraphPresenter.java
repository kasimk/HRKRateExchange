package info.kasimkovacevic.hrkrateexchange.presenters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import info.kasimkovacevic.hrkrateexchange.data.RestApi;
import info.kasimkovacevic.hrkrateexchange.data.RestClientRouter;
import info.kasimkovacevic.hrkrateexchange.data.database.realm.RealmUtil;
import info.kasimkovacevic.hrkrateexchange.data.models.ExchangeRateModel;
import info.kasimkovacevic.hrkrateexchange.utils.DateUtil;
import info.kasimkovacevic.hrkrateexchange.utils.NetworkUtil;
import info.kasimkovacevic.hrkrateexchange.views.ChangesGraphView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static info.kasimkovacevic.hrkrateexchange.utils.NetworkUtil.HTTP_STATUS_OK;

/**
 * @author Kasim Kovacevic
 */
public class ChangesGraphPresenter implements Presenter {


    private ChangesGraphView mChangeGraphView;
    private Call mCurrencyCall;
    private Call mCurrenciesCall;
    private List<String> mCurrencies;
    private List<ExchangeRateModel> mExchangeRates;
    private List<ExchangeRateModel> mExchangeRatesForCurrency;


    @Override
    public void onResume() {
    }

    @Override
    public void onPause() {
    }

    @Override
    public void onDestroyView() {
        if (mCurrenciesCall != null) mCurrenciesCall.cancel();
        if (mCurrencyCall != null) mCurrencyCall.cancel();
    }


    @Override
    public void setView(Object view) {
        mChangeGraphView = (ChangesGraphView) view;
    }

    public void onCurrencySelected(int position) {
        loadDataForCurrency(mCurrencies.get(position));
    }

    /**
     * Load list of {@link ExchangeRateModel} from database for last week for provided curency if is available, if not load data from api and store in database
     * Update view with loaded list
     *
     * @param currency represent currency code
     */

    private void loadDataForCurrency(final String currency) {
        Date dateTO = DateUtil.getCurrentDateWithoutTime(DateUtil.DATE_FORMAT_YYYY_MM_DD);
        Date dateFrom = DateUtil.getDateWeekAgoWithoutTime(DateUtil.DATE_FORMAT_YYYY_MM_DD);

        if (dateTO != null && dateFrom != null) {
            mExchangeRatesForCurrency = RealmUtil.getDataFromRealm(dateFrom, dateTO, currency);
        }
        if (mExchangeRatesForCurrency != null && mExchangeRatesForCurrency.size() == 8) {
            showExchangeRates();
        } else {
            RestApi restApi = RestClientRouter.get();
            String toDate = DateUtil.getFormattedDate(dateTO, DateUtil.DATE_FORMAT_YYYY_MM_DD);
            String fromDate = DateUtil.getFormattedDate(dateFrom, DateUtil.DATE_FORMAT_YYYY_MM_DD);
            mCurrencyCall = restApi.loadCurrencyForRange(currency, fromDate, toDate);
            mCurrencyCall.enqueue(new Callback<List<ExchangeRateModel>>() {
                @Override
                public void onResponse(Call<List<ExchangeRateModel>> call, Response<List<ExchangeRateModel>> response) {
                    if (response.code() == HTTP_STATUS_OK) {
                        mExchangeRatesForCurrency = response.body();
                        RealmUtil.prepareAndSaveDataToRealm(mExchangeRatesForCurrency, currency);
                        showExchangeRates();
                    } else {
                        NetworkUtil.showError(mChangeGraphView, response.code());
                    }
                }

                @Override
                public void onFailure(Call<List<ExchangeRateModel>> call, Throwable t) {
                    mChangeGraphView.showInternetConnectionError();
                }
            });
        }
    }

    private void showExchangeRates() {
        mChangeGraphView.showData(mExchangeRatesForCurrency);
    }


    /**
     * Load list of {@link ExchangeRateModel} from database if is available, if not load data from api and store in database
     * update view with loaded list
     */
    public void loadCurrencies() {
        Date databaseDate = DateUtil.getCurrentDateWithoutTime(DateUtil.DATE_FORMAT_YYYY_MM_DD);
        if (databaseDate != null) {
            mExchangeRates = RealmUtil.getDataFromRealm(databaseDate);
        }
        if (mExchangeRates != null && mExchangeRates.size() > 0) {
            showCurrencies();
        } else {
            RestApi restApi = RestClientRouter.get();
            String date = DateUtil.getFormattedDate(databaseDate, DateUtil.DATE_FORMAT_YYYY_MM_DD);
            mCurrenciesCall = restApi.loadCurrenciesForDate(date);
            mCurrenciesCall.enqueue(new Callback<List<ExchangeRateModel>>() {
                @Override
                public void onResponse(Call<List<ExchangeRateModel>> call, Response<List<ExchangeRateModel>> response) {
                    if (response.code() == HTTP_STATUS_OK) {
                        mExchangeRates = response.body();
                        RealmUtil.prepareAndSaveDataToRealm(mExchangeRates);
                        showCurrencies();
                    } else {
                        NetworkUtil.showError(mChangeGraphView, response.code());
                    }
                }

                @Override
                public void onFailure(Call<List<ExchangeRateModel>> call, Throwable t) {
                    mChangeGraphView.showInternetConnectionError();
                }
            });
        }
    }

    private void showCurrencies() {
        List<String> currencies = new ArrayList<>();
        for (ExchangeRateModel exchangeRateModel : mExchangeRates) {
            currencies.add(exchangeRateModel.getCurrencyCode());
        }
        mCurrencies = currencies;
        mChangeGraphView.showCurrencies(currencies);
    }

}
