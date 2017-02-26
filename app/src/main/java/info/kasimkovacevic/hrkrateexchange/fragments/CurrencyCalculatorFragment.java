package info.kasimkovacevic.hrkrateexchange.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import info.kasimkovacevic.hrkrateexchange.R;
import info.kasimkovacevic.hrkrateexchange.presenters.CurrencyCalculatorPresenter;
import info.kasimkovacevic.hrkrateexchange.presenters.Presenter;
import info.kasimkovacevic.hrkrateexchange.views.AutoResizeTextView;
import info.kasimkovacevic.hrkrateexchange.views.BaseView;
import info.kasimkovacevic.hrkrateexchange.views.CurrencyCalculatorView;

public class CurrencyCalculatorFragment extends BaseFragment implements CurrencyCalculatorView, AdapterView.OnItemSelectedListener, View.OnClickListener, TextWatcher {

    private CurrencyCalculatorPresenter mCurrencyCalculatorPresenter;
    private ArrayAdapter<String> currenciesAdapter;


    @BindView(R.id.sp_currencies_one)
    protected Spinner mCurrencyOneSpinner;
    @BindView(R.id.sp_currencies_two)
    protected Spinner mCurrencyTwoSpinner;
    @BindView(R.id.et_currency_one_amount)
    protected EditText mCurrencyAmountEditText;
    @BindView(R.id.tv_currency_two_amount)
    protected AutoResizeTextView mCurrencyValueTextView;
    @BindView(R.id.iv_switch_currencies)
    protected ImageView mSwitchCurrenciesImageView;
    @BindView(R.id.ll_data_container)
    protected LinearLayout mDataContainerLinearLayout;
    @BindView(R.id.tv_error)
    protected TextView mErrorTextView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrencyCalculatorPresenter = new CurrencyCalculatorPresenter();
    }


    @Override
    protected Presenter getPresenter() {
        return mCurrencyCalculatorPresenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_currency_calculator;
    }

    @Override
    protected BaseView getFragmentView() {
        return this;
    }

    @Override
    protected void onViewInitialized(View view) {
        super.onViewInitialized(view);
        mSwitchCurrenciesImageView.setOnClickListener(this);
        mCurrencyOneSpinner.setOnItemSelectedListener(this);
        mCurrencyTwoSpinner.setOnItemSelectedListener(this);
        mCurrencyAmountEditText.addTextChangedListener(this);
        mCurrencyCalculatorPresenter.loadCurrencies();
    }

    @Override
    public void onResume() {
        super.onResume();
        mCurrencyCalculatorPresenter.onResume();
    }


    @Override
    public void showCurrencies(List<String> currencies) {
        mDataContainerLinearLayout.setVisibility(View.VISIBLE);
        mErrorTextView.setVisibility(View.GONE);
        currenciesAdapter = new ArrayAdapter<>(CurrencyCalculatorFragment.this.getContext(),
                android.R.layout.simple_spinner_item, currencies);
        currenciesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCurrencyOneSpinner.setAdapter(currenciesAdapter);
        mCurrencyTwoSpinner.setAdapter(currenciesAdapter);
    }


    @Override
    public void showError(String error) {
        mDataContainerLinearLayout.setVisibility(View.GONE);
        mErrorTextView.setVisibility(View.VISIBLE);
        mErrorTextView.setText(error);
    }

    @Override
    public void showCalculationResult(String result) {
        mCurrencyValueTextView.setText(result);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (!TextUtils.isEmpty(mCurrencyAmountEditText.getText().toString())) {
            mCurrencyCalculatorPresenter.calculate(mCurrencyOneSpinner.getSelectedItemPosition(), mCurrencyTwoSpinner.getSelectedItemPosition(), Integer.parseInt(mCurrencyAmountEditText.getText().toString()));
        } else {
            showCalculationResult("");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        int firstPosition = mCurrencyOneSpinner.getSelectedItemPosition();
        int secondPosition = mCurrencyTwoSpinner.getSelectedItemPosition();
        mCurrencyOneSpinner.setSelection(secondPosition, true);
        mCurrencyTwoSpinner.setSelection(firstPosition, true);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!TextUtils.isEmpty(mCurrencyAmountEditText.getText().toString())) {
            mCurrencyCalculatorPresenter.calculate(mCurrencyOneSpinner.getSelectedItemPosition(), mCurrencyTwoSpinner.getSelectedItemPosition(), Integer.parseInt(s.toString()));
        } else {
            showCalculationResult("");
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
