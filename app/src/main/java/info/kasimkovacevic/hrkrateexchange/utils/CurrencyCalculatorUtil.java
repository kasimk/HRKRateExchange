package info.kasimkovacevic.hrkrateexchange.utils;

import info.kasimkovacevic.hrkrateexchange.data.models.ExchangeRateModel;

/**
 * @author Kasim Kovacevic
 */
public class CurrencyCalculatorUtil {

    /**
     * Convert amount of HRK to provided currency in {@link ExchangeRateModel}
     *
     * @param amount            represent amount of HRK
     * @param exchangeRateModel represent currency exchange rate
     * @return amount of currency in {@link ExchangeRateModel}
     */
    public static double convertToHRK(double amount, ExchangeRateModel exchangeRateModel) {
        float medianValue = Float.valueOf(exchangeRateModel.getMedianRate());
        return (amount * medianValue) / exchangeRateModel.getUnitValue();
    }

    /**
     * Convert amount to HRK from provide currency in {@link ExchangeRateModel}
     *
     * @param amount            represent amount for conversion to HRK
     * @param exchangeRateModel represent currency exchange rate
     * @return amount of currency in HRk
     */
    public static double convertFromHRK(double amount, ExchangeRateModel exchangeRateModel) {
        float medianValue = Float.valueOf(exchangeRateModel.getMedianRate());
        return (amount / medianValue) * exchangeRateModel.getUnitValue();
    }
}
