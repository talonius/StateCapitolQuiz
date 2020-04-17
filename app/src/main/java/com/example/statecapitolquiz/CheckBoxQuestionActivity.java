package com.example.statecapitolquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The CheckBoxQuestionActivity is responsible for constructing a question using
 * a collection of check boxes, collecting the answers to the questions, and
 * returning the correctness of the answer to the QuestionCoordinatorActivity.
 */
public class CheckBoxQuestionActivity extends AppCompatActivity {
    /**
     * This boolean variable is used to track whether we have shown the user whether
     * or not their answer is correct.  It's used to manage the two different phases
     * that we'll find ourselves in while inside this activity.
     */
    private boolean isSecondPhase = false;

    /**
     * Tracking variable to help track whether or not the user answered the question
     * correctly.  (This allows the variable to be passed back to the
     * QuestionCoordinatorActivity.)
     */
    private boolean isAnswerCorrect = false;

    /**
     * Holds the first QuizState object that is the target of the current question.
     */
    private QuizState quizState1 = null;

    /**
     * Holds the second QuizState object that is the target of the current question.
     */
    private QuizState quizState2 = null;

    /**
     * Fires when the activity is created.  Responsible for reading the number
     * of questions to be asked from the invoking activity and setting up the
     * first question.
     *
     * @param savedInstanceState The saved instance state to rehydrate.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_box_question);
        quizState1 = getIntent().getParcelableExtra("quizState1");
        quizState2 = getIntent().getParcelableExtra("quizState2");

        SetupQuestion();
    }

    /**
     * Handles setting the enabled state of the button and it's caption.
     *
     * @param enabled Whether or not the button should be enabled.
     * @param text The caption that should appear on the button.
     */
    protected void SetButton(boolean enabled, String text) {
        Button b = findViewById(R.id.next_button);
        b.setEnabled(enabled);
        b.setText(text);
    }

    /**
     * Helper function to handle the common task of setting a TextView's Text property.
     *
     * @param id The ID of the TextView to have the text set.
     * @param text The text to set for the TextView.
     */
    protected void SetTextViewText(int id, String text) {
        TextView tv = findViewById(id);
        tv.setText(text);
    }

    /**
     * SetupQuestion is responsible for picking a QuizState object from
     * the available states (those that have not been used during the current
     * application execution) and the configured number of answers.
     */
    protected void SetupQuestion() {
        Resources res = getResources();

        // Reset the variable tracking whether we've told the user that they're correct.
        isSecondPhase = false;

        // Set the query TextView up to display the question.
        SetTextViewText(R.id.query, res.getString(R.string.query_prefix_two_states, quizState1.getStateName(), quizState2.getStateName()));

        SetupCheckBoxes();
        SetButton(false, res.getString(R.string.caption_check_answer));
        SetTextViewText(R.id.answer_state, "");
    }

    /**
     * Sets up the child check boxes representing the answers.
     */
    protected void SetupCheckBoxes() {
        ArrayList<QuizState> availableAnswers = new ArrayList<>(QuizState.GetQuizStates());
        QuizState.RemoveFromArrayList(availableAnswers, quizState1);
        QuizState.RemoveFromArrayList(availableAnswers, quizState2);

        ArrayList<QuizState> answers = new ArrayList<>();
        answers.add(quizState1);
        answers.add(quizState2);

        // Randomly add incorrect answers.
        int NUMBER_OF_ANSWERS = 5;
        while(answers.size() != NUMBER_OF_ANSWERS) {
            answers.add(QuizState.SelectRandomAndRemove(availableAnswers));
        }

        Collections.shuffle(answers);

        LinearLayout ll = findViewById(R.id.answers);

        // The size of the answers ArrayList controls how many RadioButtons are instantiated.
        for(QuizState qs : answers) {
            CheckBox cb = new CheckBox(this);
            cb.setText(qs.getCapitolName());
            ll.addView(cb);

            cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckIfButtonShouldBeEnabled();
                }
            });
        }
    }

    /**
     * Verifies whether we have one or more check boxes checked, and if so, enable
     * the button so the user can choose to proceed.  If not, disable the button.
     */
    protected void CheckIfButtonShouldBeEnabled() {
        LinearLayout ll = findViewById(R.id.answers);
        boolean isChecked = false;

        for(int i = 0; i < ll.getChildCount(); i++) {
            View vw = ll.getChildAt(i);

            if (vw instanceof CheckBox) {
                CheckBox cb = (CheckBox) vw;

                if (cb.isChecked()) {
                    isChecked = true;
                }
            }
        }

        SetButton(isChecked, getResources().getString(R.string.caption_check_answer));
    }

    /**
     * This function cycles through all of the defined check boxes and determines
     * whether their state is correct or not based on the current quiz state
     * selection.
     *
     * @return True if all of the selection states are appropriate.
     */
    protected boolean CheckBoxesAreCorrect() {
        LinearLayout ll = findViewById(R.id.answers);
        boolean questionCorrect = true;

        // This next chunk of code highlights the correct answer in green.
        for(int i = 0; i < ll.getChildCount(); i++) {
            View v = ll.getChildAt(i);

            if (v instanceof CheckBox) {
                CheckBox cb = (CheckBox) v;
                boolean checkBoxIsCorrect = CheckBoxIsCorrect(cb);
                if (!checkBoxIsCorrect) {
                    questionCorrect = false;
                }
            }
        }

        return questionCorrect;
    }

    /**
     * Determines whether a provided check box is in the correct state
     * based on the currently selected quiz states.
     *
     * @param cb The checkbox to be validated.
     * @return True if the checkbox is in the correct state, false otherwise.
     */
    protected boolean CheckBoxIsCorrect(CheckBox cb) {
        // Do we match either of the answers?
        boolean textMatches = (cb.getText() == quizState1.getCapitolName()
                            || cb.getText() == quizState2.getCapitolName());

        // Are we checked?
        boolean isChecked = cb.isChecked();

        // Change the text of the check box to green if it matches and we're highlighting.
        if(textMatches) {
            cb.setTextColor(Color.GREEN);
        } else if (isChecked) {
            cb.setTextColor(Color.RED);
        }

        // If we matched we want to be checked.  That returns a true.
        // If we don't match and we're not checked, that returns a true.
        // If we don't match and we're checked, that returns a false.
        // If we matched and we're not checked, that returns a false.
        return ((isChecked && textMatches) || (!isChecked && !textMatches));
    }

    /**
     * Handles deciding whether the answer given was correct or not, and the corresponding
     * display of the status.
     */
    protected void HandleSecondPhase() {
        isSecondPhase = true;
        isAnswerCorrect = CheckBoxesAreCorrect();

        int color = isAnswerCorrect ? Color.GREEN : Color.RED;

        Resources res = getResources();
        String statusText = res.getString(isAnswerCorrect ? R.string.status_correct : R.string.status_incorrect);

        TextView status = findViewById(R.id.answer_state);
        status.setTextColor(color);
        status.setText(statusText);

        SetButton(true, res.getString(R.string.caption_next_question));
    }

    /**
     * This function is called both when the user clicks "Check Answer" and when
     * the user clicks "Next Question".
     *
     * @param view The parent view holding the button that was clicked.
     */
    public void activity_radio_group_question_AnswerOnClick(View view) {
        if (!isSecondPhase) {
            HandleSecondPhase();
        } else {
            Intent intent = new Intent(this, QuestionCoordinatorActivity.class);
            intent.putExtra("callingActivity", ActivityConstants.RADIO_GROUP_QUESTION_ACTIVITY);
            intent.putExtra("questionCorrect", isAnswerCorrect);
            startActivity(intent);
        }
    }
}
