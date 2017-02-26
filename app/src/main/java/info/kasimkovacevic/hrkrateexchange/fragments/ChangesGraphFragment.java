package info.kasimkovacevic.hrkrateexchange.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import info.kasimkovacevic.hrkrateexchange.R;
import info.kasimkovacevic.hrkrateexchange.data.models.ExchangeRateModel;
import info.kasimkovacevic.hrkrateexchange.presenters.ChangesGraphPresenter;
import info.kasimkovacevic.hrkrateexchange.presenters.Presenter;
import info.kasimkovacevic.hrkrateexchange.views.BaseView;
import info.kasimkovacevic.hrkrateexchange.views.ChangesGraphView;

/**
 * @author Kasim Kovacevic
 */
public class ChangesGraphFragment extends BaseFragment implements ChangesGraphView, AdapterView.OnItemSelectedListener {

    private ChangesGraphPresenter mChangesGraphPresenter;
    private ArrayAdapter<String> currenciesAdapter;
    @BindView(R.id.lc_exchange_rate)
    protected LineChart mExchangeRateChart;
    @BindView(R.id.sp_currencies)
    protected Spinner mCurrenciesSpinner;
    @BindView(R.id.tv_error)
    protected TextView mErrorTextView;
    @BindView(R.id.rl_data_container)
    protected RelativeLayout mDataContainerRelativeLayout;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mChangesGraphPresenter = new ChangesGraphPresenter();
    }

    @Override
    protected void onViewInitialized(View view) {
        super.onViewInitialized(view);
        mExchangeRateChart.setTouchEnabled(true);
        mExchangeRateChart.setDragEnabled(true);
        mExchangeRateChart.setScaleEnabled(true);
        mCurrenciesSpinner.setOnItemSelectedListener(this);
        mChangesGraphPresenter.loadCurrencies();
    }

    @Override
    protected Presenter getPresenter() {
        return mChangesGraphPresenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_changes_graph;
    }

    @Override
    protected BaseView getFragmentView() {
        return this;
    }

    @Override
    public void showData(List<ExchangeRateModel> rates) {
        mErrorTextView.setVisibility(View.GONE);
        mDataContainerRelativeLayout.setVisibility(View.VISIBLE);
        List<Entry> entries = new ArrayList<>();
        final ArrayList<String> dates = new ArrayList<>();
        for (int i = 0; i < rates.size(); i++) {
            float value = Float.valueOf(rates.get(i).getMedianRate()).floatValue();
            dates.add(rates.get(i).getDate());
            entries.add(new Entry(i, value, rates.get(i).getDate()));
        }
        LineDataSet dataSet = new LineDataSet(entries, getResources().getString(R.string.exchange_rate));
        dataSet.setDrawFilled(true);
        dataSet.setLineWidth(5f);
        int accentColorId = ContextCompat.getColor(getContext(), R.color.colorAccent);
        dataSet.setValueTextColor(accentColorId);
        dataSet.setValueTextSize(10f);
        LineData lineData = new LineData(dataSet);
        XAxis xAxis = mExchangeRateChart.getXAxis();
        xAxis.setDrawLabels(true);
        xAxis.setTextColor(accentColorId);
        //set date labels on x axis
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return dates.get((int) value);
            }
        });
        xAxis.setAvoidFirstLastClipping(false);
        //show 5 labels at once, on smaller devices 7 labels overlapping each other
        xAxis.setLabelCount(5, true);


        YAxis yAxisLeft = mExchangeRateChart.getAxis(YAxis.AxisDependency.LEFT);
        yAxisLeft.setTextColor(accentColorId);
        YAxis yAxisRight = mExchangeRateChart.getAxis(YAxis.AxisDependency.RIGHT);
        yAxisRight.setTextColor(accentColorId);

        mExchangeRateChart.setData(lineData);
        mExchangeRateChart.animateX(500);
    }


    @Override
    public void showCurrencies(List<String> currencies) {
        currenciesAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, currencies);
        currenciesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCurrenciesSpinner.setAdapter(currenciesAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mChangesGraphPresenter.onCurrencySelected(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //DO NOTHING
    }

    @Override
    public void showError(String error) {
        mDataContainerRelativeLayout.setVisibility(View.GONE);
        mErrorTextView.setVisibility(View.VISIBLE);
        mErrorTextView.setText(error);
    }
}
