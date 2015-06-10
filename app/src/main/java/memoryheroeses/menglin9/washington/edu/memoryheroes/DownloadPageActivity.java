package memoryheroeses.menglin9.washington.edu.memoryheroes;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dropbox.chooser.android.DbxChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.app.DownloadManager.EXTRA_DOWNLOAD_ID;
import static android.app.DownloadManager.Query;


public class DownloadPageActivity extends ActionBarActivity {

    static final String APP_KEY = "zj1kedpdt1e5xmg";
    static final int DBX_CHOOSER_REQUEST = 0;
    private DbxChooser mChooser;
    private Button DBBtn;

    private DownloadManager dm;
    public String downloadFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download_page);

        //setup the Text view
        EditText inputUrl = (EditText) findViewById(R.id.urlInput);
        inputUrl.setText("http://tednewardsandbox.site44.com/questions.json");

        mChooser = new DbxChooser(APP_KEY);
        DBBtn = (Button) findViewById(R.id.db);
        DBBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBBtn.setEnabled(false);
                mChooser.forResultType(DbxChooser.ResultType.DIRECT_LINK).launch(DownloadPageActivity.this,DBX_CHOOSER_REQUEST);
            }
        });

        final Button DWNBtn= (Button) findViewById(R.id.download);
        DWNBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText urlInput = (EditText) findViewById(R.id.urlInput);

                downloadFileName = urlInput.getText().toString();
                DWNBtn.setEnabled(false);
                DownloadService.setUrl(Uri.parse(downloadFileName));
                Intent downloadServiceIntent = new Intent(DownloadPageActivity.this, DownloadService.class);
                startService(downloadServiceIntent);
            }
        });

//        Register Receiver for notice of "Dwonload has complete"
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(downLoadCmpReceiver, filter);

        final Button home= (Button) findViewById(R.id.btn_home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(DownloadPageActivity.this, MainActivity.class);
                finish();
            }
        });



    }




    //Register Receiver of Download Complete
    BroadcastReceiver downLoadCmpReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, Intent intent) {
            String action = intent.getAction();

            dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

            Log.i("Wen", "OnReceive of registered download reciever");

            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                Log.i("Wen","download complete!");
                long downloadID = intent.getLongExtra(EXTRA_DOWNLOAD_ID,0);

                //if the dwonload ID exists
                if (downloadID != 0) {
                    //check status
                    Query query = new Query();
                    query.setFilterById(downloadID);
                    Cursor c = dm.query(query);
                    if (c.moveToFirst()) {
                        int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
                        Log.i("Wen", "Status Check " + status);
                        switch (status) {
                            case DownloadManager.STATUS_PAUSED:
                            case DownloadManager.STATUS_PENDING:
                            case DownloadManager.STATUS_RUNNING:
                                break;
                            case DownloadManager.STATUS_SUCCESSFUL:

                                // The download-complete message said the download was "successfu" then run this code
                                ParcelFileDescriptor file;
                                StringBuffer strContent = new StringBuffer("");

                                try {
                                    // Get file from Download Manager (which is a system service as explained in the onCreate)
                                    file = dm.openDownloadedFile(downloadID);
                                    FileInputStream fis = new FileInputStream(file.getFileDescriptor());

                                    // YOUR CODE HERE [convert file to String here]
                                    byte[] buffer = new byte[1024];
                                    int n;

                                    while((n=fis.read(buffer)) != -1) {
                                        strContent.append(new String(buffer, 0, n));
                                    }

                                    //update data !!!!
                                    Log.i("Wen","Data update....");
                                    writeToFile(strContent.toString());
                                    //set all botton to able status

                                    Button dwnBtn = (Button)findViewById(R.id.download);
                                    dwnBtn.setEnabled(true);

                                    Button DBBtn = (Button) findViewById(R.id.db);
                                    DBBtn.setEnabled(true);

                                    // YOUR CODE HERE [write string to data/data.json]
                                    //      [hint, i wrote a writeFile method in MyApp... figure out how to call that from inside this Activity]

                                    // convert your json to a string and echo it out here to show that you did download it



                                    /*

                                    String jsonString = ....myjson...to string().... chipotle burritos.... blah
                                    Log.i("MyApp - Here is the json we download:", jsonString);

                                    */

                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case DownloadManager.STATUS_FAILED:
                                Toast.makeText(context, "download fail !!!!", Toast.LENGTH_LONG).show();

                                DownloadService.startOrStopAlarm(context, false, 0);



                                AlertDialog.Builder alertDialogBuider = new AlertDialog.Builder(context);
                                alertDialogBuider.setTitle("Download Fail !");
                                alertDialogBuider
                                        .setMessage("Do you want to retry download again ?")
                                        .setCancelable(false)
                                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                Intent downloadServiceIntent = new Intent(context, DownloadService.class);
                                                context.startService(downloadServiceIntent);
                                            }


                                        })
                                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {

                                                    }

                                                }
                                        );
                                AlertDialog alertDialog = alertDialogBuider.create();
                                alertDialog.show();




                                // YOUR CODE HERE! Your download has failed! Now what do you want it to do? Retry? Quit application? up to you!
                                break;

                        }
                    }

                }
            }

        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DBX_CHOOSER_REQUEST) {
            if (resultCode == MainActivity.RESULT_OK) {
                DbxChooser.Result result = new DbxChooser.Result(data);
                Log.v("Wen", "Link to selected file: " + result.getLink());
                Log.v("Wen", "The file name you choose" + result.getName());
                DownloadService.setUrl(result.getLink());
                downloadFileName = result.getName();


                Intent downloadServiceIntent = new Intent(this, DownloadService.class);
                this.startService(downloadServiceIntent);
            }
            else {
                Log.v("Wen", "Dropbox link error");
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void writeToFile(String data) {
        try {
            Log.i("MyApp", "writing downloaded to file");

            File file = new File(getFilesDir().getAbsolutePath(), "download.json");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data.getBytes());
            fos.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
