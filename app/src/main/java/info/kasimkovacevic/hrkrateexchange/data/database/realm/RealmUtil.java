package info.kasimkovacevic.hrkrateexchange.data.database.realm;

import android.text.TextUtils;

import java.util.Date;
import java.util.List;

import info.kasimkovacevic.hrkrateexchange.data.models.ExchangeRateModel;
import info.kasimkovacevic.hrkrateexchange.utils.DateUtil;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Helper class for {@link Realm}
 *
 * @author Kasim Kovacevic
 */
public class RealmUtil {

    private static final String DATE_COLUMN_LABEL = "databaseDate";
    private static final String CURRENCY_COLUMN_LABEL = "currencyCode";


    private static void saveDataToRealm(final List<? extends RealmObject> list) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(list);
        realm.commitTransaction();
        realm.close();
    }

    /**
     * Query {@link ExchangeRateModel} from database for provided date
     *
     * @param date represent instance of {@link Date}
     * @return list of exchange rates for provided date
     */
    public static List<ExchangeRateModel> getDataFromRealm(Date date) {
        Realm realm = Realm.getDefaultInstance();
        List<ExchangeRateModel> results = realm.copyFromRealm(realm.where(ExchangeRateModel.class)
                .equalTo(DATE_COLUMN_LABEL, date)
                .findAll());
        realm.close();
        return results;
    }

    /**
     * Query {@link ExchangeRateModel} from database for provided period and provided currency
     *
     * @param from     represent start date for query
     * @param to       represent end date for query
     * @param currency represent currency code for query
     * @return list of exchange rates for provided period and currency
     */
    public static List<ExchangeRateModel> getDataFromRealm(Date from, Date to, String currency) {
        Realm realm = Realm.getDefaultInstance();
        List<ExchangeRateModel> results = realm.copyFromRealm(realm.where(ExchangeRateModel.class).equalTo(CURRENCY_COLUMN_LABEL, currency).beginGroup().greaterThanOrEqualTo(DATE_COLUMN_LABEL, from).lessThanOrEqualTo(DATE_COLUMN_LABEL, to).endGroup().findAllSorted(DATE_COLUMN_LABEL, Sort.ASCENDING));
        realm.close();
        return results;
    }


    /**
     * Delete {@link ExchangeRateModel} which represent data for dates older than provided date
     *
     * @param date represent date for query
     */
    public static void clearOldData(Date date) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<ExchangeRateModel> results = realm.where(ExchangeRateModel.class).lessThan(DATE_COLUMN_LABEL, date).findAll();
        realm.beginTransaction();
        for (ExchangeRateModel model : results) {
            model.deleteFromRealm();
        }
        realm.commitTransaction();
        realm.close();
    }

    /**
     * Set all necessary fields and save exchange rates to database
     *
     * @param mExchangeRates represent list of {@link ExchangeRateModel} for saving in database
     */
    public static void prepareAndSaveDataToRealm(List<ExchangeRateModel> mExchangeRates) {
        if (mExchangeRates != null && mExchangeRates.size() > 0) {
            prepareDates(mExchangeRates);
            setIds(mExchangeRates);
            saveDataToRealm(mExchangeRates);
        }
    }

    /**
     * Set all necessary fields and save exchange rates to database
     *
     * @param mExchangeRates represent list of {@link ExchangeRateModel} for saving in database
     * @param currency       represent currency code for setting on provided exchange rates
     */
    public static void prepareAndSaveDataToRealm(List<ExchangeRateModel> mExchangeRates, String currency) {
        if (mExchangeRates != null && mExchangeRates.size() > 0) {
            prepareDates(mExchangeRates);
            setCurrencies(mExchangeRates, currency);
            setIds(mExchangeRates);
            saveDataToRealm(mExchangeRates);
        }
    }

    private static void setIds(List<ExchangeRateModel> mExchangeRates) {
        if (mExchangeRates != null && mExchangeRates.size() > 0) {
            for (ExchangeRateModel exchangeRateModel : mExchangeRates) {
                exchangeRateModel.setId(exchangeRateModel.getCurrencyCode() + exchangeRateModel.getDate());
            }
        }
    }

    private static void prepareDates(List<ExchangeRateModel> mExchangeRates) {
        if (mExchangeRates != null && mExchangeRates.size() > 0) {
            for (ExchangeRateModel exchangeRateModel : mExchangeRates) {
                if (TextUtils.isEmpty(exchangeRateModel.getDate())) {
                    exchangeRateModel.setDate(DateUtil.getFormattedDate(new Date(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
                }
                exchangeRateModel.setDatabaseDate(DateUtil.parseDate(exchangeRateModel.getDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));

            }
        }
    }


    private static void setCurrencies(List<ExchangeRateModel> mExchangeRates, String currency) {
        if (mExchangeRates != null && mExchangeRates.size() > 0) {
            for (ExchangeRateModel exchangeRateModel : mExchangeRates) {
                exchangeRateModel.setCurrencyCode(currency);
            }
        }
    }


}
