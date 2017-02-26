package info.kasimkovacevic.hrkrateexchange.views;

import java.util.List;

import info.kasimkovacevic.hrkrateexchange.data.models.ExchangeRateModel;

/**
 * @author Kasim Kovacevic
 */
public interface ChangesGraphView extends BaseView {

    void showData(List<ExchangeRateModel> exchangeRateModelList);

    void showCurrencies(List<String> currencies);
}
