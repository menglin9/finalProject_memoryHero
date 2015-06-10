package memoryheroeses.menglin9.washington.edu.memoryheroes;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class Check_content extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_content);
        final memoryApp mApp = (memoryApp) getApplication();
        //mApp.setFlashCardIndex(0);
        mApp.setScore(0);
        loadCheckFragment();
    }

    public void loadCheckFragment() {
        memoryApp mApp = (memoryApp) getApplication();
        mApp.setChecked(false);

        FragmentManager checkManager = getFragmentManager();
        FragmentTransaction checkT = checkManager.beginTransaction();

        //Bundle topicBundle = new Bundle();
        //topicBundle.putInt("position", position);

        //overView is a fragment
        Check_fragment checkFragment = new Check_fragment();
        //checkFragment.setArguments(topicBundle);

        checkT.add(R.id.container, checkFragment);
        checkT.commit();
    }

    public void startOver() {
        Intent next = new Intent(Check_content.this, MainActivity.class);
        startActivity(next);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_check_content, menu);
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
}
