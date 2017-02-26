package info.kasimkovacevic.hrkrateexchange.presenters;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import info.kasimkovacevic.hrkrateexchange.data.RestApi;
import info.kasimkovacevic.hrkrateexchange.data.RestClientRouter;
import info.kasimkovacevic.hrkrateexchange.data.database.realm.RealmUtil;
import info.kasimkovacevic.hrkrateexchange.data.models.ExchangeRateModel;
import info.kasimkovacevic.hrkrateexchange.utils.CurrencyCalculatorUtil;
import info.kasimkovacevic.hrkrateexchange.utils.DateUtil;
import info.kasimkovacevic.hrkrateexchange.utils.NetworkUtil;
import info.kasimkovacevic.hrkrateexchange.views.CurrencyCalculatorView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static info.kasimkovacevic.hrkrateexchange.utils.NetworkUtil.HTTP_STATUS_OK;

/**
 * @author Kasim Kovacevic
 */
public class CurrencyCalculatorPresenter implements Presenter {

    private static final String HRK_CURRENCY_CODE = "HRK";
    private CurrencyCalculatorView mCurrencyCalculatorView;
    private Call mCall;
    private List<String> mCurrencies;
    private List<ExchangeRateModel> mExchangeRates;

    @Override
    public void onResume() {
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
        mCurrencyCalculatorView = (CurrencyCalculatorView) view;
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
            String date = DateUtil.getFormattedDate(new Date(), DateUtil.DATE_FORMAT_YYYY_MM_DD);
            mCall = restApi.loadCurrenciesForDate(date);
            mCall.enqueue(new Callback<List<ExchangeRateModel>>() {
                @Override
                public void onResponse(Call<List<ExchangeRateModel>> call, Response<List<ExchangeRateModel>> response) {
                    if (response.code() == HTTP_STATUS_OK) {
                        mExchangeRates = response.body();
                        RealmUtil.prepareAndSaveDataToRealm(mExchangeRates);
                        showCurrencies();
                    } else {
                        NetworkUtil.showError(mCurrencyCalculatorView, response.code());
                    }
                }

                @Override
                public void onFailure(Call<List<ExchangeRateModel>> call, Throwable t) {
                    mCurrencyCalculatorView.showInternetConnectionError();
                }
            });
        }
    }

    private void showCurrencies() {
        List<String> currencies = new ArrayList<>();
        currencies.add(HRK_CURRENCY_CODE);
        for (ExchangeRateModel exchangeRateModel : mExchangeRates) {
            currencies.add(exchangeRateModel.getCurrencyCode());
        }
        mCurrencies = currencies;
        mCurrencyCalculatorView.showCurrencies(currencies);
    }

    /**
     * Calculate amount with currency converter
     *
     * @param first  represent index of currency from conversion
     * @param second represent index of currency to conversion
     * @param amount represent amount for conversion
     */
    public void calculate(int first, int second, double amount) {
        double value = 0;
        if (amount > 0) {
            if (first == second) {
                value = amount;
            } else if (mCurrencies.get(first).equals(HRK_CURRENCY_CODE) && second > 0) {
                value = CurrencyCalculatorUtil.convertFromHRK(amount, mExchangeRates.get(second - 1));
            } else if (mCurrencies.get(second).equals(HRK_CURRENCY_CODE) && first > 0) {
                value = CurrencyCalculatorUtil.convertToHRK(amount, mExchangeRates.get(first - 1));
            } else if (first > 0 && second > 0) {
                double hrkValue = CurrencyCalculatorUtil.convertToHRK(amount, mExchangeRates.get(first - 1));
                value = CurrencyCalculatorUtil.convertFromHRK(hrkValue, mExchangeRates.get(second - 1));
            }
        }
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        mCurrencyCalculatorView.showCalculationResult(df.format(value));
    }
}
