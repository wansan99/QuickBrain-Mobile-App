package com.example.mdproject;

import android.provider.BaseColumns;

public final class MathQuizContract {

    private MathQuizContract() {}

    public static class MathQuestionsTable implements BaseColumns {
        public static final String TABLE_NAME = "mathQuiz_questions";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_CHOICE1 = "choice1";
        public static final String COLUMN_CHOICE2 = "choice2";
        public static final String COLUMN_CHOICE3 = "choice3";
        public static final String COLUMN_CHOICE4 = "choice4";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_DIFFICULTY = "difficulty";
        public static final String COLUMN_ANSWER_NR = "answer_nr";
    }
}
