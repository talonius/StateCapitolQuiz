package com.example.statecapitolquiz;

/**
 * Enumeration used to specify what activity has called another.  Could use
 * a string but the enumeration is cleaner in the long run and far less
 * prone to typos.
 */
public enum ActivityConstants {
    /**
     * MAIN_ACTIVITY indicates that the MainActivity called the target activity.
     * The MainActivity is primarily responsible for obtaining the number of
     * questions to be asked.
     */
    MAIN_ACTIVITY,
    /**
     * QUESTION_COORDINATOR_ACTIVITY indicates that the QuestionCoordinatorActivity
     * called the target activity.  The QuestionCoordinatorActivity is responsible
     * for tracking the number of questions asked, the number of questions to be
     * asked, and the number of questions correctly answered.
     */
    QUESTION_COORDINATOR_ACTIVITY,
    /**
     * RADIO_GROUP_QUESTION_ACTIVITY indicates that the RadioGroupQuestionActivity
     * called the target activity.  The RadioGroupQuestionActivity is responsible
     * for constructing a question using a radio group, collecting the answer to the
     * question, and returning the correctness of the answer to the
     * QuestionCoordinatorActivity.
     */
    RADIO_GROUP_QUESTION_ACTIVITY,
    /**
     * CHECK_BOX_QUESTION_ACTIVITY indicates that the CheckBoxQuestionActivity
     * called the target activity.  The CheckBoxQuestionActivity is responsible
     * for constructing a question using a collection of check boxes, collecting the
     * answers to the questions, and returning the correctness of the answer to the
     * QuestionCoordinatorActivity.
     */
    CHECK_BOX_QUESTION_ACTIVITY,
    /**
     * TEXT_ENTRY_QUESTION_ACTIVITY indicates that the EditTextQuestionActivity
     * called the target activity.  The EditTextQuestionActivity is responsible
     * for constructing a question using an EditText control, collecting the answer
     * to the question, and return the correctness of that answer to the
     * QuestionCoordinatorActivity.
     */
    TEXT_ENTRY_QUESTION_ACTIVITY,
    /**
     * SUMMARY_ACTIVITY indicates that the SummaryActivity called the target activity.
     * The SummaryActivity is responsible for evaluating the overall performance
     * of the user.
     */
    SUMMARY_ACTIVITY
}
