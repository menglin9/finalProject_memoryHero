package memoryheroeses.menglin9.washington.edu.memoryheroes;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Flashcard_Content extends ActionBarActivity {

    memoryApp mApp;
    Button previous;
    Button next;
    Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_flashcard__content);
        loadCheckFragment();

        previous = (Button) findViewById(R.id.previous);
        next = (Button) findViewById(R.id.next);
        home = (Button) findViewById(R.id.home);

        mApp = (memoryApp) getApplication();

        setVisibility();


        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mApp.getFlashCardIndex() != 0) {
                    mApp.setFlashCardIndex(mApp.getFlashCardIndex() - 1);
                }
                function();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mApp.getFlashCardIndex() != (mApp.getFlashCardNum() - 1)) {
                    mApp.setFlashCardIndex(mApp.getFlashCardIndex() + 1);
                }
                function();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mApp.setIntelM(false);
                Intent next = new Intent(Flashcard_Content.this, MainActivity.class);
                startActivity(next);
            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_flashcard__content, menu);
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

    public void loadCheckFragment() {
        memoryApp mApp = (memoryApp) getApplication();
        mApp.setChecked(false);

        FragmentManager flashcardManager = getFragmentManager();
        FragmentTransaction flashT = flashcardManager.beginTransaction();

        //Bundle topicBundle = new Bundle();
        //topicBundle.putInt("position", position);

        //overView is a fragment
        Flashcard_fragment flashcardFragment = new Flashcard_fragment();
        //checkFragment.setArguments(topicBundle);

        flashT.add(R.id.container, flashcardFragment);
        flashT.commit();
    }

    public void onCardClick(View view)
    {
        flipCard();
    }

    private void flipCard()
    {
        View rootLayout = (View) findViewById(R.id.main_activity_root);
        View cardFace = (View) findViewById(R.id.main_activity_card_face);
        View cardBack = (View) findViewById(R.id.main_activity_card_back);

        FlipAnimation flipAnimation = new FlipAnimation(cardFace, cardBack);

        TextView front = (TextView) findViewById(R.id.front);
        //Log.e("hi", ""+mApp.getCurrentFront());
        if (!mApp.getIntelM()) {
            front.setText(mApp.getCurrentFront());
        } else {
            front.setText(mApp.getIfront());
        }
        TextView back = (TextView) findViewById(R.id.back);
        if (!mApp.getIntelM()) {
            back.setText(mApp.getCurrentBack());
        } else {
            back.setText(mApp.getIback());
        }
        //back.setText(mApp.getCurrentBack());

        if (cardFace.getVisibility() == View.GONE)
        {
            flipAnimation.reverse();
        }
        rootLayout.startAnimation(flipAnimation);


    }

    public void setVisibility() {
        if (mApp.getFlashCardIndex() == 0) {
            previous.setVisibility(View.GONE);
        } else {
            previous.setVisibility(View.VISIBLE);
        }

        if (mApp.getFlashCardIndex() == mApp.getFlashCardNum() - 1) {
            next.setVisibility(View.GONE);
        } else {
            next.setVisibility(View.VISIBLE);
        }
    }

    public void function() {
        View cardFace = (View) findViewById(R.id.main_activity_card_face);
        TextView frontP = (TextView) cardFace.findViewById(R.id.front);
        //frontP.setText(mApp.getCurrentFront());
        if (!mApp.getIntelM()) {
            frontP.setText(mApp.getCurrentFront());
        } else {
            frontP.setText(mApp.getIfront());
        }
        View cardBack = (View) findViewById(R.id.main_activity_card_back);
        TextView backP = (TextView) cardBack.findViewById(R.id.back);
        if (!mApp.getIntelM()) {
            backP.setText(mApp.getCurrentBack());
        } else {
            backP.setText(mApp.getIback());
        }
        //backP.setText(mApp.getCurrentBack());
        setVisibility();
    }
}
