package info.kasimkovacevic.hrkrateexchange.views;

/**
 * @author Kasim Kovacevic
 */
public interface BaseView {

    void showAppMadeBadRequestError();

    void showInternetConnectionError();

    void showServerUnavailableError();

    void showServerError();

    void showError(String error);

}
