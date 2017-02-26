package info.kasimkovacevic.hrkrateexchange.utils;

import info.kasimkovacevic.hrkrateexchange.views.BaseView;

/**
 * Created by kasimkovacevic1 on 2/26/17.
 */

public class NetworkUtil {

    public static final int HTTP_STATUS_OK = 200;
    public static final int HTTP_STATUS_BAD_REQUEST = 400;
    public static final int HTTP_STATUS_SERVER_UNAVAILABLE = 503;
    public static final int HTTP_STATUS_INTERNAL_SERVER_ERROR = 500;


    /**
     * Call show error method on {@link BaseView} according to status code
     *
     * @param baseView instance of {@link BaseView}
     * @param code     represent status code of response
     */
    public static void showError(BaseView baseView, int code) {
        if (code == HTTP_STATUS_BAD_REQUEST) {
            baseView.showAppMadeBadRequestError();
        } else if (code == HTTP_STATUS_SERVER_UNAVAILABLE) {
            baseView.showServerUnavailableError();
        } else if (code == HTTP_STATUS_INTERNAL_SERVER_ERROR) {
            baseView.showServerError();
        }
    }

}
