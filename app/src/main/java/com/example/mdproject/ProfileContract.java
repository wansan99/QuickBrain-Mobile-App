package com.example.mdproject;

import android.provider.BaseColumns;

public class ProfileContract {

    private ProfileContract() {}

    public static class ProfileTable implements BaseColumns {
        public static final String TABLE_NAME = "userProfile";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_MQS1 = "mq1";
        public static final String COLUMN_MQS2 = "mq2";
        public static final String COLUMN_MQS3 = "mq3";
        public static final String COLUMN_MQS4 = "mq4";
        public static final String COLUMN_MQS5 = "mq5";
        public static final String COLUMN_MQS6 = "mq6";
        public static final String COLUMN_MQS7 = "mq7";
        public static final String COLUMN_LSS1 = "ls1";
        public static final String COLUMN_LSS2 = "ls2";
        public static final String COLUMN_MGS1 = "mg1";
        public static final String COLUMN_MGS2 = "mg2";
        public static final String COLUMN_MGS3 = "mg3";
        public static final String COLUMN_MGS4 = "mg4";
        public static final String COLUMN_MGS5 = "mg5";
        public static final String COLUMN_MGS6 = "mg6";
        public static final String COLUMN_MGS7 = "mg7";
    }
}
