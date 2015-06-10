package memoryheroeses.menglin9.washington.edu.memoryheroes;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MemoryChoice extends ActionBarActivity {

    Button flashcards;
    Button checking;
    Button mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_choice);

        flashcards = (Button) findViewById(R.id.btn_flashcards);
        checking = (Button) findViewById(R.id.btn_testing);
        mode = (Button) findViewById(R.id.btn_intel);

        flashcards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "flashcards!!! =)",
                  //      Toast.LENGTH_LONG).show();

                Intent next = new Intent(MemoryChoice.this, Flashcard_Content.class);
                //next.putExtra("position", position);
                startActivity(next);
            }
        });

        checking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "checking!!! =)",
                //        Toast.LENGTH_LONG).show();
                Intent next = new Intent(MemoryChoice.this, Check_content.class);
                startActivity(next);
            }
        });

        mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final memoryApp mApp = (memoryApp) getApplication();
                if (mApp.getIntelM()) {
                    mApp.setIntelM(false);
                    mode.setText("Intelligent Mode");
                } else if (!mApp.getIntelM()) {
                    mApp.setIntelM(true);
                    mApp.makeICards();
                    mode.setText("General Mode");
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_memory_choice, menu);
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
