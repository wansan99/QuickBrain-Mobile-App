package com.example.mdproject;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class QBDataBase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "QuickBrain.db";
    private static final int DATABASE_VERSION = 9;

    private SQLiteDatabase db;


    public QBDataBase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_PROFILE_TABLE = "CREATE TABLE " +
                ProfileContract.ProfileTable.TABLE_NAME + " ( " +
                ProfileContract.ProfileTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ProfileContract.ProfileTable.COLUMN_NAME + " TEXT, " +
                ProfileContract.ProfileTable.COLUMN_USERNAME + " TEXT, " +
                ProfileContract.ProfileTable.COLUMN_PASSWORD + " TEXT, " +
                ProfileContract.ProfileTable.COLUMN_MQS1 + " INTEGER DEFAULT 0, " +
                ProfileContract.ProfileTable.COLUMN_MQS2 + " INTEGER DEFAULT 0, " +
                ProfileContract.ProfileTable.COLUMN_MQS3 + " INTEGER DEFAULT 0, " +
                ProfileContract.ProfileTable.COLUMN_MQS4 + " INTEGER DEFAULT 0, " +
                ProfileContract.ProfileTable.COLUMN_MQS5 + " INTEGER DEFAULT 0, " +
                ProfileContract.ProfileTable.COLUMN_MQS6 + " INTEGER DEFAULT 0, " +
                ProfileContract.ProfileTable.COLUMN_MQS7 + " INTEGER DEFAULT 0, " +
                ProfileContract.ProfileTable.COLUMN_LSS1 + " INTEGER DEFAULT 0, " +
                ProfileContract.ProfileTable.COLUMN_LSS2 + " INTEGER DEFAULT 0, " +
                ProfileContract.ProfileTable.COLUMN_MGS1 + " INTEGER DEFAULT 0, " +
                ProfileContract.ProfileTable.COLUMN_MGS2 + " INTEGER DEFAULT 0, " +
                ProfileContract.ProfileTable.COLUMN_MGS3 + " INTEGER DEFAULT 0, " +
                ProfileContract.ProfileTable.COLUMN_MGS4 + " INTEGER DEFAULT 0, " +
                ProfileContract.ProfileTable.COLUMN_MGS5 + " INTEGER DEFAULT 0, " +
                ProfileContract.ProfileTable.COLUMN_MGS6 + " INTEGER DEFAULT 0, " +
                ProfileContract.ProfileTable.COLUMN_MGS7 + " INTEGER DEFAULT 0)";
        db.execSQL(SQL_CREATE_PROFILE_TABLE);

        final String SQL_CREATE_MATH_QUESTION_TABLE = "CREATE TABLE " +
                MathQuizContract.MathQuestionsTable.TABLE_NAME + " ( " +
                MathQuizContract.MathQuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MathQuizContract.MathQuestionsTable.COLUMN_QUESTION + " TEXT, " +
                MathQuizContract.MathQuestionsTable.COLUMN_CHOICE1 + " TEXT, " +
                MathQuizContract.MathQuestionsTable.COLUMN_CHOICE2 + " TEXT, " +
                MathQuizContract.MathQuestionsTable.COLUMN_CHOICE3 + " TEXT, " +
                MathQuizContract.MathQuestionsTable.COLUMN_CHOICE4 + " TEXT, " +
                MathQuizContract.MathQuestionsTable.COLUMN_TYPE + " TEXT, " +
                MathQuizContract.MathQuestionsTable.COLUMN_DIFFICULTY + " INTEGER, " +
                MathQuizContract.MathQuestionsTable.COLUMN_ANSWER_NR + " INTEGER)";
        db.execSQL(SQL_CREATE_MATH_QUESTION_TABLE);

        final String SQL_CREATE_ACHIEVEMENT_TABLE = "CREATE TABLE " +
                AchievementContract.AchievementTable.TABLE_NAME + " ( " +
                AchievementContract.AchievementTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                AchievementContract.AchievementTable.COLUMN_USERNAME + " TEXT, " +
                AchievementContract.AchievementTable.COLUMN_MQA + " INTEGER DEFAULT 0, " +
                AchievementContract.AchievementTable.COLUMN_LSA + " INTEGER DEFAULT 0, " +
                AchievementContract.AchievementTable.COLUMN_MGA + " INTEGER DEFAULT 0, " +
                AchievementContract.AchievementTable.COLUMN_ASA + " INTEGER DEFAULT 0, " +
                AchievementContract.AchievementTable.COLUMN_ATA + " INTEGER DEFAULT 0)";
        db.execSQL(SQL_CREATE_ACHIEVEMENT_TABLE);

        final String SQL_CREATE_TRACK_TUTORIAL_TABLE = "CREATE TABLE " +
                TutorialContract.TutorialTable.TABLE_NAME + " ( " +
                TutorialContract.TutorialTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TutorialContract.TutorialTable.COLUMN_USERNAME + " TEXT, " +
                TutorialContract.TutorialTable.COLUMN_T1 + " INTEGER DEFAULT 0, " +
                TutorialContract.TutorialTable.COLUMN_T2 + " INTEGER DEFAULT 0, " +
                TutorialContract.TutorialTable.COLUMN_T3 + " INTEGER DEFAULT 0, " +
                TutorialContract.TutorialTable.COLUMN_T4 + " INTEGER DEFAULT 0)";
        db.execSQL(SQL_CREATE_TRACK_TUTORIAL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ProfileContract.ProfileTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MathQuizContract.MathQuestionsTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + AchievementContract.AchievementTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TutorialContract.TutorialTable.TABLE_NAME);
        onCreate(db);
    }

    public boolean insertNewProfile(String name, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ProfileContract.ProfileTable.COLUMN_NAME, name);
        cv.put(ProfileContract.ProfileTable.COLUMN_USERNAME, username);
        cv.put(ProfileContract.ProfileTable.COLUMN_PASSWORD, password);
        long isInserted = db.insert(ProfileContract.ProfileTable.TABLE_NAME, null, cv);
        if(isInserted == -1) return false;
        else return true;
    }

    public boolean updateProfile(String name, String username, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        if(!name.equals("")) cv.put(ProfileContract.ProfileTable.COLUMN_NAME, name);
        if(!newPassword.equals("")) cv.put(ProfileContract.ProfileTable.COLUMN_PASSWORD, newPassword);
        int i = db.update(ProfileContract.ProfileTable.TABLE_NAME, cv, ProfileContract.ProfileTable.COLUMN_USERNAME + " = ?", new String[] {username});
        if(i > 0) return true;
        else return false;
    }

    public boolean checkIfUserExist(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + ProfileContract.ProfileTable.TABLE_NAME +
                                    " WHERE " + ProfileContract.ProfileTable.COLUMN_USERNAME + " = ?", new String[]{username});
        if(c.getCount() > 0) return true;
        else return false;
    }

    public boolean loginProfile(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + ProfileContract.ProfileTable.TABLE_NAME +
                                    " WHERE " + ProfileContract.ProfileTable.COLUMN_USERNAME + " = ? AND " +
                                    ProfileContract.ProfileTable.COLUMN_PASSWORD + " = ?", new String[]{username, password});
        if(c.getCount() > 0) return true;
        else return false;
    }

    public List<String> getUserInformation(String username) {
        List<String> userInfo = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + ProfileContract.ProfileTable.TABLE_NAME +
                " WHERE " + ProfileContract.ProfileTable.COLUMN_USERNAME + " = ?", new String[]{username});

        if(c.getCount() > 0){
            c.moveToFirst();
            userInfo.add(c.getString(c.getColumnIndex(ProfileContract.ProfileTable.COLUMN_NAME)));
            userInfo.add(String.valueOf(c.getInt(c.getColumnIndex(ProfileContract.ProfileTable.COLUMN_MQS1))));
            userInfo.add(String.valueOf(c.getString(c.getColumnIndex(ProfileContract.ProfileTable.COLUMN_MQS2))));
            userInfo.add(String.valueOf(c.getString(c.getColumnIndex(ProfileContract.ProfileTable.COLUMN_MQS3))));
            userInfo.add(String.valueOf(c.getString(c.getColumnIndex(ProfileContract.ProfileTable.COLUMN_MQS4))));
            userInfo.add(String.valueOf(c.getString(c.getColumnIndex(ProfileContract.ProfileTable.COLUMN_MQS5))));
            userInfo.add(String.valueOf(c.getString(c.getColumnIndex(ProfileContract.ProfileTable.COLUMN_MQS6))));
            userInfo.add(String.valueOf(c.getString(c.getColumnIndex(ProfileContract.ProfileTable.COLUMN_MQS7))));
            userInfo.add(String.valueOf(c.getString(c.getColumnIndex(ProfileContract.ProfileTable.COLUMN_LSS1))));
            userInfo.add(String.valueOf(c.getString(c.getColumnIndex(ProfileContract.ProfileTable.COLUMN_LSS2))));
            userInfo.add(String.valueOf(c.getString(c.getColumnIndex(ProfileContract.ProfileTable.COLUMN_MGS1))));
            userInfo.add(String.valueOf(c.getString(c.getColumnIndex(ProfileContract.ProfileTable.COLUMN_MGS2))));
            userInfo.add(String.valueOf(c.getString(c.getColumnIndex(ProfileContract.ProfileTable.COLUMN_MGS3))));
            userInfo.add(String.valueOf(c.getString(c.getColumnIndex(ProfileContract.ProfileTable.COLUMN_MGS4))));
            userInfo.add(String.valueOf(c.getString(c.getColumnIndex(ProfileContract.ProfileTable.COLUMN_MGS5))));
            userInfo.add(String.valueOf(c.getString(c.getColumnIndex(ProfileContract.ProfileTable.COLUMN_MGS6))));
            userInfo.add(String.valueOf(c.getString(c.getColumnIndex(ProfileContract.ProfileTable.COLUMN_MGS7))));
        }
        c.close();
        return userInfo;
    }

    public void extractQuestionsToDB(Context context) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + MathQuizContract.MathQuestionsTable.TABLE_NAME);
        ContentValues cv = new ContentValues();
        try {
            for(int i = 0; i < 7; i++) {
                String line;
                String[] data;
                InputStream is = null;
                if(i == 0) {
                    is = context.getResources().openRawResource(R.raw.addition);
                }
                else if(i == 1) {
                    is = context.getResources().openRawResource(R.raw.subtraction);
                }
                else if(i == 2) {
                    is = context.getResources().openRawResource(R.raw.multiplication);
                }
                else if(i == 3) {
                    is = context.getResources().openRawResource(R.raw.division);
                }
                else if(i == 4) {
                    is = context.getResources().openRawResource(R.raw.power);
                }
                else if(i == 5) {
                    is = context.getResources().openRawResource(R.raw.root);
                }
                else if(i == 6) {
                    is = context.getResources().openRawResource(R.raw.factorial);
                }
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                while((line = reader.readLine()) != null) {
                    data = line.split(",");
                    cv.put(MathQuizContract.MathQuestionsTable.COLUMN_QUESTION, data[0]);
                    cv.put(MathQuizContract.MathQuestionsTable.COLUMN_CHOICE1, data[1]);
                    cv.put(MathQuizContract.MathQuestionsTable.COLUMN_CHOICE2, data[2]);
                    cv.put(MathQuizContract.MathQuestionsTable.COLUMN_CHOICE3, data[3]);
                    cv.put(MathQuizContract.MathQuestionsTable.COLUMN_CHOICE4, data[4]);
                    cv.put(MathQuizContract.MathQuestionsTable.COLUMN_TYPE, data[5]);
                    cv.put(MathQuizContract.MathQuestionsTable.COLUMN_DIFFICULTY, data[6]);
                    cv.put(MathQuizContract.MathQuestionsTable.COLUMN_ANSWER_NR, data[7]);
                    db.insert(MathQuizContract.MathQuestionsTable.TABLE_NAME, null, cv);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<MathQuestion> getAllMathQuestions(int tutorialType, int difficulty) {
        List<MathQuestion> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = null;
        //query the db for math question
        if(difficulty == 1) {
            if (tutorialType == 1) {
                c = db.rawQuery("SELECT * FROM " + MathQuizContract.MathQuestionsTable.TABLE_NAME + " WHERE type = '+' AND difficulty = '1'", null);
            } else if (tutorialType == 2) {
                c = db.rawQuery("SELECT * FROM " + MathQuizContract.MathQuestionsTable.TABLE_NAME + " WHERE type = '-' AND difficulty = '1'", null);
            } else if (tutorialType == 3) {
                c = db.rawQuery("SELECT * FROM " + MathQuizContract.MathQuestionsTable.TABLE_NAME + " WHERE type = '*' AND difficulty = '1'", null);
            } else if (tutorialType == 4) {
                c = db.rawQuery("SELECT * FROM " + MathQuizContract.MathQuestionsTable.TABLE_NAME + " WHERE type = '/' AND difficulty = '1'", null);
            } else if (tutorialType == 5) {
                c = db.rawQuery("SELECT * FROM " + MathQuizContract.MathQuestionsTable.TABLE_NAME + " WHERE type = '^' AND difficulty = '1'", null);
            } else if (tutorialType == 6) {
                c = db.rawQuery("SELECT * FROM " + MathQuizContract.MathQuestionsTable.TABLE_NAME + " WHERE type = '√' AND difficulty = '1'", null);
            } else if (tutorialType == 7) {
                c = db.rawQuery("SELECT * FROM " + MathQuizContract.MathQuestionsTable.TABLE_NAME + " WHERE type = '!' AND difficulty = '1'", null);
            }
        }
        else if(difficulty == 2) {
            if (tutorialType == 1) {
                c = db.rawQuery("SELECT * FROM " + MathQuizContract.MathQuestionsTable.TABLE_NAME + " WHERE type = '+'", null);
            } else if (tutorialType == 2) {
                c = db.rawQuery("SELECT * FROM " + MathQuizContract.MathQuestionsTable.TABLE_NAME + " WHERE type = '-'", null);
            } else if (tutorialType == 3) {
                c = db.rawQuery("SELECT * FROM " + MathQuizContract.MathQuestionsTable.TABLE_NAME + " WHERE type = '*'", null);
            } else if (tutorialType == 4) {
                c = db.rawQuery("SELECT * FROM " + MathQuizContract.MathQuestionsTable.TABLE_NAME + " WHERE type = '/'", null);
            } else if (tutorialType == 5) {
                c = db.rawQuery("SELECT * FROM " + MathQuizContract.MathQuestionsTable.TABLE_NAME + " WHERE type = '^'", null);
            } else if (tutorialType == 6) {
                c = db.rawQuery("SELECT * FROM " + MathQuizContract.MathQuestionsTable.TABLE_NAME + " WHERE type = '√'", null);
            } else if (tutorialType == 7) {
                c = db.rawQuery("SELECT * FROM " + MathQuizContract.MathQuestionsTable.TABLE_NAME + " WHERE type = '!'", null);
            }
        }

        //if there is something, move to the first item
        if(c.moveToFirst()){
            do{
                //create a temp question
                MathQuestion question = new MathQuestion();
                //retrieve all the data stored in db and put in the temp question
                question.setQuestion(c.getString(c.getColumnIndex(MathQuizContract.MathQuestionsTable.COLUMN_QUESTION)));
                question.setChoice1(c.getString(c.getColumnIndex(MathQuizContract.MathQuestionsTable.COLUMN_CHOICE1)));
                question.setChoice2(c.getString(c.getColumnIndex(MathQuizContract.MathQuestionsTable.COLUMN_CHOICE2)));
                question.setChoice3(c.getString(c.getColumnIndex(MathQuizContract.MathQuestionsTable.COLUMN_CHOICE3)));
                question.setChoice4(c.getString(c.getColumnIndex(MathQuizContract.MathQuestionsTable.COLUMN_CHOICE4)));
                question.setType(c.getString(c.getColumnIndex(MathQuizContract.MathQuestionsTable.COLUMN_TYPE)));
                question.setDifficulty(c.getInt(c.getColumnIndex(MathQuizContract.MathQuestionsTable.COLUMN_DIFFICULTY)));
                question.setAnswer(c.getInt(c.getColumnIndex(MathQuizContract.MathQuestionsTable.COLUMN_ANSWER_NR)));
                //push the temp question into the list
                questionList.add(question);
                //continue if there is an item next
            }while(c.moveToNext());
        }
        c.close();
        return questionList;
    }

    public boolean updateScore(int field, int value, String username) {
        db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        switch(field) {
            case 1:
                cv.put(ProfileContract.ProfileTable.COLUMN_MQS1, value);
                break;
            case 2:
                cv.put(ProfileContract.ProfileTable.COLUMN_MQS2, value);
                break;
            case 3:
                cv.put(ProfileContract.ProfileTable.COLUMN_MQS3, value);
                break;
            case 4:
                cv.put(ProfileContract.ProfileTable.COLUMN_MQS4, value);
                break;
            case 5:
                cv.put(ProfileContract.ProfileTable.COLUMN_MQS5, value);
                break;
            case 6:
                cv.put(ProfileContract.ProfileTable.COLUMN_MQS6, value);
                break;
            case 7:
                cv.put(ProfileContract.ProfileTable.COLUMN_MQS7, value);
                break;
            case 8:
                cv.put(ProfileContract.ProfileTable.COLUMN_LSS1, value);
                break;
            case 9:
                cv.put(ProfileContract.ProfileTable.COLUMN_LSS2, value);
                break;
            case 10:
                cv.put(ProfileContract.ProfileTable.COLUMN_MGS1, value);
                break;
            case 11:
                cv.put(ProfileContract.ProfileTable.COLUMN_MGS2, value);
                break;
            case 12:
                cv.put(ProfileContract.ProfileTable.COLUMN_MGS3, value);
                break;
            case 13:
                cv.put(ProfileContract.ProfileTable.COLUMN_MGS4, value);
                break;
            case 14:
                cv.put(ProfileContract.ProfileTable.COLUMN_MGS5, value);
                break;
            case 15:
                cv.put(ProfileContract.ProfileTable.COLUMN_MGS6, value);
                break;
            case 16:
                cv.put(ProfileContract.ProfileTable.COLUMN_MGS7, value);
                break;
        }
        int i = db.update(ProfileContract.ProfileTable.TABLE_NAME, cv, ProfileContract.ProfileTable.COLUMN_USERNAME + " = ?", new String[]{username});
        if( i > 0) return true;
        else return false;
    }

    public List<LeaderboardProfiles> getLeaderBoardScore(int category) {
        List<LeaderboardProfiles> leaderBoard = new ArrayList<>();
        db = getReadableDatabase();
        String query = null;
        switch(category) {
            case 1:
                query = "SELECT " + ProfileContract.ProfileTable.COLUMN_NAME + ", " + ProfileContract.ProfileTable.COLUMN_MGS1 + " AS score FROM " + ProfileContract.ProfileTable.TABLE_NAME +
                        " ORDER BY " + ProfileContract.ProfileTable.COLUMN_MGS1 + " DESC";
                break;
            case 2:
                query = "SELECT " + ProfileContract.ProfileTable.COLUMN_NAME + ", " + ProfileContract.ProfileTable.COLUMN_MGS2 + " AS score FROM " + ProfileContract.ProfileTable.TABLE_NAME +
                        " ORDER BY " + ProfileContract.ProfileTable.COLUMN_MGS2 + " DESC";
                break;
            case 3:
                query = "SELECT " + ProfileContract.ProfileTable.COLUMN_NAME + ", " + ProfileContract.ProfileTable.COLUMN_MGS3 + " AS score FROM " + ProfileContract.ProfileTable.TABLE_NAME +
                        " ORDER BY " + ProfileContract.ProfileTable.COLUMN_MGS3 + " DESC";
                break;
            case 4:
                query = "SELECT " + ProfileContract.ProfileTable.COLUMN_NAME + ", " + ProfileContract.ProfileTable.COLUMN_MGS4 + " AS score FROM " + ProfileContract.ProfileTable.TABLE_NAME +
                        " ORDER BY " + ProfileContract.ProfileTable.COLUMN_MGS4 + " DESC";
                break;
            case 5:
                query = "SELECT " + ProfileContract.ProfileTable.COLUMN_NAME + ", " + ProfileContract.ProfileTable.COLUMN_MGS5 + " AS score FROM " + ProfileContract.ProfileTable.TABLE_NAME +
                        " ORDER BY " + ProfileContract.ProfileTable.COLUMN_MGS5 + " DESC";
                break;
            case 6:
                query = "SELECT " + ProfileContract.ProfileTable.COLUMN_NAME + ", " + ProfileContract.ProfileTable.COLUMN_MGS6 + " AS score FROM " + ProfileContract.ProfileTable.TABLE_NAME +
                        " ORDER BY " + ProfileContract.ProfileTable.COLUMN_MGS6 + " DESC";
                break;
            case 7:
                query = "SELECT " + ProfileContract.ProfileTable.COLUMN_NAME + ", " + ProfileContract.ProfileTable.COLUMN_MGS7 + " AS score FROM " + ProfileContract.ProfileTable.TABLE_NAME +
                        " ORDER BY " + ProfileContract.ProfileTable.COLUMN_MGS7 + " DESC";
                break;
            case 8:
                query = "SELECT " + ProfileContract.ProfileTable.COLUMN_NAME + ", " + ProfileContract.ProfileTable.COLUMN_LSS1 + " AS score FROM " + ProfileContract.ProfileTable.TABLE_NAME +
                        " ORDER BY " + ProfileContract.ProfileTable.COLUMN_LSS1 + " DESC";
                break;
            case 9:
                query = "SELECT " + ProfileContract.ProfileTable.COLUMN_NAME + ", " + ProfileContract.ProfileTable.COLUMN_LSS2 + " AS score FROM " + ProfileContract.ProfileTable.TABLE_NAME +
                        " ORDER BY " + ProfileContract.ProfileTable.COLUMN_LSS2 + " DESC";
                break;
            case 10:
                query = "SELECT " + ProfileContract.ProfileTable.COLUMN_NAME + ", " + ProfileContract.ProfileTable.COLUMN_MQS1 + " AS score FROM " + ProfileContract.ProfileTable.TABLE_NAME +
                        " ORDER BY " + ProfileContract.ProfileTable.COLUMN_MQS1 + " DESC";
                break;
            case 11:
                query = "SELECT " + ProfileContract.ProfileTable.COLUMN_NAME + ", " + ProfileContract.ProfileTable.COLUMN_MQS2 + " AS score FROM " + ProfileContract.ProfileTable.TABLE_NAME +
                        " ORDER BY " + ProfileContract.ProfileTable.COLUMN_MQS2 + " DESC";
                break;
            case 12:
                query = "SELECT " + ProfileContract.ProfileTable.COLUMN_NAME + ", " + ProfileContract.ProfileTable.COLUMN_MQS3 + " AS score FROM " + ProfileContract.ProfileTable.TABLE_NAME +
                        " ORDER BY " + ProfileContract.ProfileTable.COLUMN_MQS3 + " DESC";
                break;
            case 13:
                query = "SELECT " + ProfileContract.ProfileTable.COLUMN_NAME + ", " + ProfileContract.ProfileTable.COLUMN_MQS4 + " AS score FROM " + ProfileContract.ProfileTable.TABLE_NAME +
                        " ORDER BY " + ProfileContract.ProfileTable.COLUMN_MQS4 + " DESC";
                break;
            case 14:
                query = "SELECT " + ProfileContract.ProfileTable.COLUMN_NAME + ", " + ProfileContract.ProfileTable.COLUMN_MQS5 + " AS score FROM " + ProfileContract.ProfileTable.TABLE_NAME +
                        " ORDER BY " + ProfileContract.ProfileTable.COLUMN_MQS5 + " DESC";
                break;
            case 15:
                query = "SELECT " + ProfileContract.ProfileTable.COLUMN_NAME + ", " + ProfileContract.ProfileTable.COLUMN_MQS6 + " AS score FROM " + ProfileContract.ProfileTable.TABLE_NAME +
                        " ORDER BY " + ProfileContract.ProfileTable.COLUMN_MQS6 + " DESC";
                break;
            case 16:
                query = "SELECT " + ProfileContract.ProfileTable.COLUMN_NAME + ", " + ProfileContract.ProfileTable.COLUMN_MQS7 + " AS score FROM " + ProfileContract.ProfileTable.TABLE_NAME +
                        " ORDER BY " + ProfileContract.ProfileTable.COLUMN_MQS7 + " DESC";
                break;
        }
        Cursor c = db.rawQuery(query, null);
        
        if(c.moveToFirst()) {
            do {
                LeaderboardProfiles temp = new LeaderboardProfiles();
                temp.setName(c.getString(c.getColumnIndex(ProfileContract.ProfileTable.COLUMN_NAME)));
                temp.setScore(c.getString(c.getColumnIndex("score")));
                leaderBoard.add(temp);
            } while (c.moveToNext());
        }
        c.close();
        return leaderBoard;
    }

    public void createAchievementRecord(String username) {
        db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(AchievementContract.AchievementTable.COLUMN_USERNAME, username);
        db.insert(AchievementContract.AchievementTable.TABLE_NAME, null, cv);
    }

    public int getAchievementRecord(int category, String username) {
        int temp = -1;
        db = getReadableDatabase();
        String query = null;
        switch (category) {
            case 1:
                query = "SELECT " + AchievementContract.AchievementTable.COLUMN_ASA + " AS flag FROM " + AchievementContract.AchievementTable.TABLE_NAME + " WHERE " +
                        AchievementContract.AchievementTable.COLUMN_USERNAME + " = ? ";
                break;

            case 2:
                query = "SELECT " + AchievementContract.AchievementTable.COLUMN_LSA + " AS flag FROM " + AchievementContract.AchievementTable.TABLE_NAME + " WHERE "
                        + AchievementContract.AchievementTable.COLUMN_USERNAME + " = ? ";
                break;

            case 3:
                query = "SELECT " + AchievementContract.AchievementTable.COLUMN_MGA + " AS flag FROM " + AchievementContract.AchievementTable.TABLE_NAME + " WHERE "
                        + AchievementContract.AchievementTable.COLUMN_USERNAME + " = ? ";
                break;

            case 4:
                query = "SELECT " + AchievementContract.AchievementTable.COLUMN_MQA + " AS flag FROM " + AchievementContract.AchievementTable.TABLE_NAME + " WHERE "
                        + AchievementContract.AchievementTable.COLUMN_USERNAME + " = ? ";
                break;

            case 5:
                query = "SELECT " + AchievementContract.AchievementTable.COLUMN_ATA + " AS flag FROM " + AchievementContract.AchievementTable.TABLE_NAME + " WHERE "
                        + AchievementContract.AchievementTable.COLUMN_USERNAME + " = ? ";
                break;
        }
        Cursor c = db.rawQuery(query, new String[]{username});

        if(c.moveToFirst()) {
            temp = c.getInt(c.getColumnIndex("flag"));
        }
        c.close();
        return temp;
    }

    public boolean updateAchievement(int category, String username) {
        db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        switch(category) {
            case 1:
                cv.put(AchievementContract.AchievementTable.COLUMN_ASA, 1);
                break;

            case 2:
                cv.put(AchievementContract.AchievementTable.COLUMN_LSA, 1);
                break;

            case 3:
                cv.put(AchievementContract.AchievementTable.COLUMN_MGA, 1);
                break;

            case 4:
                cv.put(AchievementContract.AchievementTable.COLUMN_MQA, 1);
                break;

            case 5:
                cv.put(AchievementContract.AchievementTable.COLUMN_ATA, 1);
                break;
        }
        int i = db.update(AchievementContract.AchievementTable.TABLE_NAME, cv, AchievementContract.AchievementTable.COLUMN_USERNAME + " = ?", new String[]{username});
        if(i > 0) return true;
        else return false;
    }

    public void createTutorialRecord(String username) {
        db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TutorialContract.TutorialTable.COLUMN_USERNAME, username);
        db.insert(TutorialContract.TutorialTable.TABLE_NAME, null, cv);
    }

    public void updateTutorialRecord(int category, String username) {
        db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        if(category == 1) cv.put(TutorialContract.TutorialTable.COLUMN_T1, 1);
        if(category == 2) cv.put(TutorialContract.TutorialTable.COLUMN_T2, 1);
        if(category == 3) cv.put(TutorialContract.TutorialTable.COLUMN_T3, 1);
        if(category == 4) cv.put(TutorialContract.TutorialTable.COLUMN_T4, 1);
        db.update(TutorialContract.TutorialTable.TABLE_NAME, cv, TutorialContract.TutorialTable.COLUMN_USERNAME + " = ?", new String[]{username});
    }

    public int getTutorialProgress(String username) {
        db = getReadableDatabase();
        int temp = -1;
        Cursor c = db.rawQuery("SELECT * FROM " + TutorialContract.TutorialTable.TABLE_NAME + " WHERE " + TutorialContract.TutorialTable.COLUMN_USERNAME + " = ? ", new String[]{username});
        if(c.moveToFirst()) {
            temp = c.getInt(c.getColumnIndex(TutorialContract.TutorialTable.COLUMN_T1));
            temp += c.getInt(c.getColumnIndex(TutorialContract.TutorialTable.COLUMN_T2));
            temp += c.getInt(c.getColumnIndex(TutorialContract.TutorialTable.COLUMN_T3));
            temp += c.getInt(c.getColumnIndex(TutorialContract.TutorialTable.COLUMN_T4));
        }
        c.close();
        return temp;
    }
}
