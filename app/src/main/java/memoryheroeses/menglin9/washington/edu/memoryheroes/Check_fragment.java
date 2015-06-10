package memoryheroeses.menglin9.washington.edu.memoryheroes;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;


/**
   Created by Menglin 5/24/15
 **/

public class Check_fragment extends Fragment {

    String yourAnswer = "";
    private Activity hostActivity;
    private boolean finish = false;
    protected static final int RESULT_SPEECH = 1;
    public EditText answerT;
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;

    public Check_fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_check_fragment, container, false);
        final View vi = inflater.inflate(R.layout.fragment_check_fragment, container, false);
        final memoryApp mApp = (memoryApp) getActivity().getApplication();
        //topics = mApp.getAllCategory();

        // implement front & back
        TextView category = (TextView) vi.findViewById(R.id.category);
        category.setText(mApp.getCurrentCategory());

        TextView front = (TextView) vi.findViewById(R.id.front);
        front.setText(mApp.getCurrentFront());


        final LinearLayout result_page = (LinearLayout) vi.findViewById(R.id.result_page);

        //final EditText answerT = (EditText) vi.findViewById(R.id.back);
        answerT = (EditText) vi.findViewById(R.id.back);

        //implement voice recognision

        ImageButton btnSpeak = (ImageButton) vi.findViewById(R.id.btnSpeak);

        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(
                        RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

                try {
                    startActivityForResult(intent, RESULT_SPEECH);
                    answerT.setText("");
                } catch (ActivityNotFoundException a) {
                    //Toast t = Toast.makeText(memoryApp.getApplicationContext(),
                      //      "Opps! Your device doesn't support Speech to Text",
                        //    Toast.LENGTH_SHORT);
                    //t.show();
                }
            }
        });


        // implement help buttom
        final LinearLayout help_page = (LinearLayout) vi.findViewById(R.id.hide_help);
        final Button b_help = (Button) vi.findViewById(R.id.help);
        b_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LinearLayout help_page = (LinearLayout) vi.findViewById(R.id.hide_help);
                help_page.setVisibility(View.VISIBLE);

                RadioButton B1 = (RadioButton) vi.findViewById(R.id.answer1);
                RadioButton B2 = (RadioButton) vi.findViewById(R.id.answer2);
                RadioButton B3 = (RadioButton) vi.findViewById(R.id.answer3);
                RadioButton B4 = (RadioButton) vi.findViewById(R.id.answer4);

                if (mApp.getFlashCardNum() < 4) {
                    B1.setText(mApp.getFourChoice(mApp.getPosition()).get(0));
                    B2.setText(mApp.getFourChoice(mApp.getPosition()).get(1));
                    B3.setText(mApp.getFourChoice(mApp.getPosition()).get(2));
                    B4.setText(mApp.getFourChoice(mApp.getPosition()).get(3));
                } else {
                    ArrayList<String> test;
                    test = mApp.getAl();
                    B1.setText(test.get(0));
                    B2.setText(test.get(1));
                    B3.setText(test.get(2));
                    B4.setText(test.get(3));
                }

                final RadioGroup radioGroup = (RadioGroup) vi.findViewById(R.id.radioQuestionGroup);
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        int sId = radioGroup.getCheckedRadioButtonId();
                        RadioButton radioButton = (RadioButton) vi.findViewById(sId);
                        String choice = radioButton.getText().toString();
                        answerT.setText(choice);
                    }
                });
            }
        });

        // implement check buttom
        final Button b_check = (Button) vi.findViewById(R.id.check);
        final Button next = (Button) vi.findViewById(R.id.next);
        b_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answerT.getText().toString().equals("")) {
                    yourAnswer = answerT.getText().toString();
                    Log.e("yours111", ""+yourAnswer);
                    result_page.setVisibility(View.VISIBLE);
                    next.setVisibility(View.VISIBLE);
                    b_check.setVisibility(View.GONE);
                    b_help.setVisibility(View.GONE);
                    help_page.setVisibility(View.GONE);
                    mApp.setChecked(true);

                    TextView RoW = (TextView) vi.findViewById(R.id.result);
                    if (yourAnswer.equals(mApp.getCurrentBack())) {
                        RoW.setText("You are Correct! :)");
                        RoW.setTextColor(Color.GREEN);
                        mApp.setScore((mApp.getScore() + 1));
                    } else {
                        RoW.setText("You are Wrong, Sorry :(");
                        RoW.setTextColor(Color.RED);
                        mApp.getCurrentFlashCard().setWeight(mApp.getCurrentFlashCard().getWeight()+1);
                        //RoW.setText(mApp.getCurrentBack());
                        //Log.e("right", ""+mApp.getCurrentBack() );
                        //Log.e("yours", ""+yourAnswer);
                    }

                    TextView answerR = (TextView) vi.findViewById(R.id.answer);
                    answerR.setText(mApp.getCurrentBack());

                    TextView score = (TextView) vi.findViewById(R.id.score);
                    score.setText(mApp.getScore() + " out of " + mApp.getFlashCardNum());

                    //Button next = (Button) vi.findViewById(R.id.next);

                    if (mApp.getFlashCardIndex() +1 == mApp.getFlashCardNum() ) {
                        next.setText("Finish");
                        finish = true;
                    }

                    // implement next buttom
                    next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mApp.getChecked()) {
                                if (hostActivity instanceof Check_content) {
                                    if (!finish) {
                                        Check_content origActivity = ((Check_content) hostActivity);
                                        //origActivity.setAns(answerData);
                                        origActivity.loadCheckFragment();
                                        mApp.setFlashCardIndex(mApp.getFlashCardIndex() + 1);
                                    } else {
                                        Check_content origActivity = ((Check_content) hostActivity);
                                        origActivity.startOver();
                                    }
                                }
                            }
                        }
                    });
                }
            }
        });

        return vi;
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.hostActivity = activity;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_SPEECH: {
                if (resultCode == Activity.RESULT_OK && null != data) {

                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    answerT.setText(text.get(0));
                }
                break;
            }

        }
    }

}
