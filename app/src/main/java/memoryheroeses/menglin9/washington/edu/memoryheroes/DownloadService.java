package memoryheroeses.menglin9.washington.edu.memoryheroes;

import android.app.DownloadManager;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

/**
 * Created by jimliao on 2015/6/8.
 */



public class DownloadService extends IntentService {
    private DownloadManager dm;
    private long enqueue;
    public static final int Wen_ALARM = 731;
    private static Uri url;

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    public void onCreate() { super.onCreate(); }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        Log.i("DownloadService", "entered onHandleIntent()");
        // Hooray! This method is called where the AlarmManager shouldve started the download service and we just received it here!

        Log.i("DownloadService", "should be downloading here");


        // Star the download
        dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

        DownloadManager.Request request = new DownloadManager.Request(url).setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        Log.i("Wen",url.toString());
        enqueue = dm.enqueue(request);

        Log.i("Download ID", String.valueOf(enqueue));

//        while(1==1) {
//            DownloadManager.Query query = new DownloadManager.Query();
//            query.setFilterById(enqueue);
//            Cursor c = dm.query(query);
//            if (c.moveToFirst()) {
//                int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
//                Log.i("Wen", "Status Check " + status);
//                switch (status) {
//                    case DownloadManager.STATUS_PAUSED:
////                        Log.v("Wen", "paused");
//                        break;
//                    case DownloadManager.STATUS_PENDING:
////                        Log.v("Wen", "pending");
//                        break;
//                    case DownloadManager.STATUS_RUNNING:
////                        Log.v("Wen", "runing");
//                        break;
//                    case DownloadManager.STATUS_SUCCESSFUL:
//                        Log.v("Wen", "suc");
//                        break;
//                }
//            }
//        }
    }

    public static void startOrStopAlarm(Context context, boolean on, int time) {
        Log.i("DownloadService", "startOrStopAlarm on = " + on);


//
//        Intent alarmReceiverIntent = new Intent(context, AlarmReceiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, Wen_ALARM, alarmReceiverIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        AlarmManager manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
//
//        if (on) {
//            Log.i("DownloadService", "setting alarm to " + time);
//            // Start the alarm manager to repeat
//            manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), time, pendingIntent);
//        }
//        else {
//            manager.cancel(pendingIntent);
//            pendingIntent.cancel();
//            Log.i("DownloadService", "Stopping alarm");
//        }
    }

    public static void setUrl(Uri settingUrl) {
        Log.i("Wen","set Url");
        url = settingUrl;
    }

}
