package android.bignerdranch.com.geoquiz;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends Activity {

    private TextView questionView;
    private Button yesButton;
    private Button noButton;
    private Button nextButton;
    private Button prevButton;

    private int currentQuestionIndex = 0;
    private Question[] questionsForView = new Question[] {
        new Question(R.string.question_1, true),
        new Question(R.string.question_2, true),
        new Question(R.string.question_3, false),
        new Question(R.string.question_4, false),
        new Question(R.string.question_5, true),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //Getting a reference to an object with a resourceId.
        questionView = (TextView) findViewById(R.id.question_text);
        yesButton    = (Button)   findViewById(R.id.yes_button);
        noButton     = (Button)   findViewById(R.id.no_button);
        nextButton   = (Button)   findViewById(R.id.next_button);
        prevButton   = (Button)   findViewById(R.id.prev_button);

        //Setup View
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
                updateQuestion();
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentQuestionIndex = (questionsForView.length + currentQuestionIndex - 1) % questionsForView.length;
                updateQuestion();
            }
        });
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

        if (answerIsTrue && userPressedTrue) {
            toastId = R.string.correct_toast;
        } else {
            toastId = R.string.incorrect_toast;
        }

        Toast.makeText(this, toastId, Toast.LENGTH_SHORT).show();
    }
}
