package com.quizproject.jab.quizproject;

import android.provider.BaseColumns;

// this class helps define the database schema that will be used
public final class SchemaContract {
    private SchemaContract() {}

    /* Inner class that defines the table contents */
    public static class Users implements BaseColumns {
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_NAME_ID = _ID;
        public static final String COLUMN_NAME_EMAIL = "email";
    }

}
