package info.kasimkovacevic.hrkrateexchange.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.kasimkovacevic.hrkrateexchange.R;
import info.kasimkovacevic.hrkrateexchange.data.models.ExchangeRateModel;

/**
 * @author Kasim Kovacevic
 */
public class ExchangeRateViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_currency)
    protected TextView mCurrencyTextView;

    @BindView(R.id.tv_unit)
    protected TextView mUnitTextView;

    @BindView(R.id.tv_buying_rate)
    protected TextView mBuyingRateTextView;

    @BindView(R.id.tv_median_rate)
    protected TextView mMedianRateTextView;

    @BindView(R.id.tv_selling_rate)
    protected TextView mSellingRateTextView;

    public ExchangeRateViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


    /**
     * This method is used for bind view
     *
     * @param exchangeRateModel represents data for binding
     */
    public void bind(ExchangeRateModel exchangeRateModel) {
        mCurrencyTextView.setText(exchangeRateModel.getCurrencyCode());
        mUnitTextView.setText(String.valueOf(exchangeRateModel.getUnitValue()));
        mBuyingRateTextView.setText(exchangeRateModel.getBuyingRate());
        mMedianRateTextView.setText(exchangeRateModel.getMedianRate());
        mSellingRateTextView.setText(exchangeRateModel.getSellingRate());
    }

}
