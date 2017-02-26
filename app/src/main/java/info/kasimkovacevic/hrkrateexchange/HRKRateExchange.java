package info.kasimkovacevic.hrkrateexchange;

import android.app.Application;

import io.realm.Realm;

/**
 * @author Kasim Kovacevic
 */
public class HRKRateExchange extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
