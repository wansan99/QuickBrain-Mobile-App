package com.example.mdproject;

import android.provider.BaseColumns;

public class TutorialContract {

    private TutorialContract() {}

    public static class TutorialTable implements BaseColumns {
        public static final String TABLE_NAME = "trackTutorial";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_T1 = "T1";
        public static final String COLUMN_T2 = "T2";
        public static final String COLUMN_T3 = "T3";
        public static final String COLUMN_T4 = "T4";
    }
}
