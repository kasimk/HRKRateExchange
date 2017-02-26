package info.kasimkovacevic.hrkrateexchange.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import info.kasimkovacevic.hrkrateexchange.R;
import info.kasimkovacevic.hrkrateexchange.adapters.viewholders.ExchangeRateViewHolder;
import info.kasimkovacevic.hrkrateexchange.data.models.ExchangeRateModel;

/**
 * @author Kasim Kovacevic
 */
public class ExchangeRateListAdapter extends RecyclerView.Adapter<ExchangeRateViewHolder> {


    private List<ExchangeRateModel> mExchangeRates;

    public ExchangeRateListAdapter(List<ExchangeRateModel> exchangeRates) {
        this.mExchangeRates = exchangeRates;
    }


    @Override
    public ExchangeRateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_exchange_rate, parent, false);
        return new ExchangeRateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExchangeRateViewHolder holder, int position) {
        holder.bind(mExchangeRates.get(position));
    }

    @Override
    public int getItemCount() {
        return mExchangeRates != null ? mExchangeRates.size() : 0;
    }

    /**
     * This method is used for updating list of items. notifyDataSetChanged is called after setting provided list value
     *
     * @param exchangeRates represent value for update
     */
    public void setExchangeRates(List<ExchangeRateModel> exchangeRates) {
        this.mExchangeRates = exchangeRates;
        notifyDataSetChanged();
    }
}
