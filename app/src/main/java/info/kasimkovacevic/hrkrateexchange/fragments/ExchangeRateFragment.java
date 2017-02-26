package info.kasimkovacevic.hrkrateexchange.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import info.kasimkovacevic.hrkrateexchange.R;
import info.kasimkovacevic.hrkrateexchange.adapters.ExchangeRateListAdapter;
import info.kasimkovacevic.hrkrateexchange.data.models.ExchangeRateModel;
import info.kasimkovacevic.hrkrateexchange.presenters.ExchangeRatePresenter;
import info.kasimkovacevic.hrkrateexchange.presenters.Presenter;
import info.kasimkovacevic.hrkrateexchange.views.BaseView;
import info.kasimkovacevic.hrkrateexchange.views.ExchangeRateView;


/**
 * @author Kasim Kovacevic
 */
public class ExchangeRateFragment extends BaseFragment implements ExchangeRateView {


    private ExchangeRatePresenter mExchangeRatePresenter;
    private ExchangeRateListAdapter mExchangeRateListAdapter;

    @BindView(R.id.rv_exchange_rate_list)
    protected RecyclerView mExchangeRateList;

    @BindView(R.id.ll_header)
    protected LinearLayout mHeaderLinearLayout;

    @BindView(R.id.tv_error)
    protected TextView mErrorTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mExchangeRatePresenter = new ExchangeRatePresenter();
    }

    @Override
    protected Presenter getPresenter() {
        return mExchangeRatePresenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_exchange_rate;
    }

    @Override
    protected BaseView getFragmentView() {
        return this;
    }

    @Override
    protected void onViewInitialized(View view) {
        super.onViewInitialized(view);
        mExchangeRateListAdapter = new ExchangeRateListAdapter(null);
        mExchangeRateList.setAdapter(mExchangeRateListAdapter);
    }

    @Override
    public void showData(List<ExchangeRateModel> rates) {
        mErrorTextView.setVisibility(View.GONE);
        mHeaderLinearLayout.setVisibility(View.VISIBLE);
        mExchangeRateList.setVisibility(View.VISIBLE);
        mExchangeRateListAdapter.setExchangeRates(rates);
    }

    @Override
    public void showError(String error) {
        mExchangeRateList.setVisibility(View.GONE);
        mHeaderLinearLayout.setVisibility(View.GONE);
        mErrorTextView.setVisibility(View.VISIBLE);
        mErrorTextView.setText(error);
    }

}

