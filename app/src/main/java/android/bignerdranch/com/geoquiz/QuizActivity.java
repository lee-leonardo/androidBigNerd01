package android.bignerdranch.com.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends Activity {
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_CHEAT = 0;

    private TextView questionView;
    private Button yesButton;
    private Button noButton;
    private Button nextButton;
    private Button prevButton;
    private Button cheatButton;

    private int currentQuestionIndex = 0;
    private Question[] questionsForView = new Question[] {
        new Question(R.string.question_1, true),
        new Question(R.string.question_2, true),
        new Question(R.string.question_3, false),
        new Question(R.string.question_4, false),
        new Question(R.string.question_5, true),
    };
    private boolean isCheater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Log.d(TAG, "on Create(Bundle) called");

        //Getting references to an object with a resourceId.
        questionView = (TextView) findViewById(R.id.question_text);
        yesButton    = (Button)   findViewById(R.id.yes_button);
        noButton     = (Button)   findViewById(R.id.no_button);
        nextButton   = (Button)   findViewById(R.id.next_button);
        prevButton   = (Button)   findViewById(R.id.prev_button);
        cheatButton  = (Button)   findViewById(R.id.cheat_button);

        //Setup View
        if (savedInstanceState != null) {
            //This retrieves pertinent state information via KVO.
            currentQuestionIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }
        updateQuestion();

        //Event Listeners
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });
        noButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentQuestionIndex = (currentQuestionIndex + 1) % questionsForView.length;
                isCheater = false;
                updateQuestion();
            }
        });
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentQuestionIndex = (questionsForView.length + currentQuestionIndex - 1) % questionsForView.length;
                isCheater = false;
                updateQuestion();
            }
        });
        cheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navigating to Cheat Activity - Using an Intent
                //Intent i = new Intent(QuizActivity.this, CheatActivity.class); // This is a basic Intent
                //startActivity(i);                                              // This is a basic way to start a new activity using intents

                //Passing an extra into an intent, and setting up request code state.
                boolean answerIsTrue = questionsForView[currentQuestionIndex].isAnswerTrue();
                Intent i = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                startActivityForResult(i, REQUEST_CODE_CHEAT);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
        outState.putInt(KEY_INDEX, currentQuestionIndex);
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

    private void updateQuestion() {
        int question = questionsForView[currentQuestionIndex].getTextResId();
        questionView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = questionsForView[currentQuestionIndex].isAnswerTrue();
        int toastId;

        if (isCheater) {
            toastId = R.string.judgment_toast;
        }
        else {
            if (answerIsTrue && userPressedTrue) {
                toastId = R.string.correct_toast;
            }
            else {
                toastId = R.string.incorrect_toast;
            }
        }

        Toast.makeText(this, toastId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // When the Activity is checking the response, first checks RESULT_OK
        if (resultCode != RESULT_OK) {
            // Returns if no Intent
            if (data == null) {
                return;
            }
            // Uses the static method to check if the answer was shown.
            isCheater = CheatActivity.wasAnswerShown(data);
        }
    }
}
