package info.kasimkovacevic.hrkrateexchange.presenters;

/**
 * @author Kasim Kovacevic
 */
public interface Presenter<T> {

    void onResume();

    void onPause();

    void onDestroyView();

    void setView(T view);

}
