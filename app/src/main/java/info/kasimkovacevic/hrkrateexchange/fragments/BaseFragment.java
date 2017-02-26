package info.kasimkovacevic.hrkrateexchange.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import info.kasimkovacevic.hrkrateexchange.BuildConfig;
import info.kasimkovacevic.hrkrateexchange.R;
import info.kasimkovacevic.hrkrateexchange.presenters.Presenter;
import info.kasimkovacevic.hrkrateexchange.views.BaseView;

/**
 * @author Kasim Kovacevic
 */
public abstract class BaseFragment extends Fragment implements BaseView {

    private Unbinder unbinder;

    protected abstract Presenter getPresenter();

    protected abstract int getLayoutId();

    protected abstract BaseView getFragmentView();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        getPresenter().setView(this);
        onViewInitialized(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getPresenter() != null) getPresenter().onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getPresenter() != null) getPresenter().onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (getPresenter() != null) getPresenter().onDestroyView();
    }

    protected void onViewInitialized(View view) {

    }

    @Override
    public void showAppMadeBadRequestError() {
        showError(getResources().getString(R.string.bad_request_error, BuildConfig.CONTACT_EMAIL));
    }

    @Override
    public void showInternetConnectionError() {
        showError(getResources().getString(R.string.connection_error));
    }

    @Override
    public void showServerUnavailableError() {
        showError(getString(R.string.server_unavailable_error));
    }

    @Override
    public void showServerError() {
        showError(getString(R.string.server_error));
    }

}
