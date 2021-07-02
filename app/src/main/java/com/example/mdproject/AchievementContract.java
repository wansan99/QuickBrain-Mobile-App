package com.example.mdproject;

import android.provider.BaseColumns;

public class AchievementContract {

    private AchievementContract() {}

    public static class AchievementTable implements BaseColumns {
        public static final String TABLE_NAME = "userAchievement";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_MQA = "MQA";
        public static final String COLUMN_LSA = "LSA";
        public static final String COLUMN_MGA = "MGA";
        public static final String COLUMN_ASA = "ASA";
        public static final String COLUMN_ATA = "ATA";
    }
}
