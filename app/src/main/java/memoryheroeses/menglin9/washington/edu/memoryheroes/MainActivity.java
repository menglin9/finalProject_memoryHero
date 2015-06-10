package memoryheroeses.menglin9.washington.edu.memoryheroes;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

//(ArrayList, answer) getoption(which flashcard)
public class MainActivity extends ActionBarActivity {
    public String[] topics;
    private ListView categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start();
        /*
        final memoryApp mApp = (memoryApp) getApplication();
        topics = mApp.getAllCategory();
        mApp.setFlashCardIndex(0);

        categoryList = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> items = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, topics);
        categoryList.setAdapter(items);
        categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("in", "you have clicked");
                Intent next = new Intent(MainActivity.this, MemoryChoice.class);
                //next.putExtra("position", position);
                mApp.setPosition(position);
                startActivity(next);
            }
        });

        final Button download = (Button) findViewById(R.id.btn_download);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(MainActivity.this, DownloadPageActivity.class);
                startActivity(next);
            }
        });
        */
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        start();
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

    public void start() {
        final memoryApp mApp = (memoryApp) getApplication();
        topics = mApp.getAllCategory();
        mApp.setFlashCardIndex(0);

        categoryList = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> items = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, topics);
        categoryList.setAdapter(items);
        categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("in", "you have clicked");
                Intent next = new Intent(MainActivity.this, MemoryChoice.class);
                //next.putExtra("position", position);
                mApp.setPosition(position);
                startActivity(next);
            }
        });

        final Button download = (Button) findViewById(R.id.btn_download);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(MainActivity.this, DownloadPageActivity.class);
                startActivity(next);
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.i("Wen","Resume");
    }
}
