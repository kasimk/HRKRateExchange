package info.kasimkovacevic.hrkrateexchange.broadcastreceivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Date;

import info.kasimkovacevic.hrkrateexchange.services.UpdateDataService;

/**
 * This broadcast receiver is registered for three actions:
 * -   ACTION_BOOT_COMPLETED
 * -   ACTION_TIME_CHANGED
 * -   ACTION_TIMEZONE_CHANGED
 * On any of this actions, receiver schedule task which is update data every 24H
 * If system clock time iz changed alarm manager will stop http://stackoverflow.com/q/21985529
 *
 * @author Kasim Kovacevic
 */
public class BootCompletedAndTimeChangeBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction()) || Intent.ACTION_TIME_CHANGED.equals(intent.getAction()) || Intent.ACTION_TIMEZONE_CHANGED.equals(intent.getAction())) {
            Intent i = new Intent(context, UpdateDataService.class);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getService(context, 2000, i, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, new Date().getTime() + 1000, AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }
}
