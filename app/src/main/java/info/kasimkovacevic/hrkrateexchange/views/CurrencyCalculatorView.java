package info.kasimkovacevic.hrkrateexchange.views;

import java.util.List;

/**
 * @author Kasim Kovacevic
 */
public interface CurrencyCalculatorView extends BaseView {

    void showCurrencies(List<String> currencies);

    void showCalculationResult(String result);

}
