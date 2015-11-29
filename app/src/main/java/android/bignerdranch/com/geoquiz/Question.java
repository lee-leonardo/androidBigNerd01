package android.bignerdranch.com.geoquiz;

/**
 * Created by leolee on 11/29/15.
 */
public class Question {
    private int textResId;
    private boolean answerTrue;

    public Question(int textResId, boolean answerTrue) {
        this.textResId  = textResId;
        this.answerTrue = answerTrue;
    }

    public boolean getAnswerTrue() {
        return answerTrue;
    }

    public int getTextResId() {
        return textResId;
    }

    public void setAnswerTrue(boolean answerTrue) {
        this.answerTrue = answerTrue;
    }

    public void setTextResId(int textResId) {
        this.textResId = textResId;
    }

    public boolean isAnswerTrue() {
        return answerTrue;
    }
}
