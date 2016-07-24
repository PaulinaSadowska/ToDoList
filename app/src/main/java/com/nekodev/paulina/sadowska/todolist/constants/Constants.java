package com.nekodev.paulina.sadowska.todolist.constants;

/**
 * Created by Paulina Sadowska on 23.07.2016.
 */
public class Constants {

    public static final String ORIENTATION_CHANGED = "OrientationChanged";
    public static final String ORIENTATION_CHANGED_KEY = "OrientationChangedFlag";


    public class IntentExtra{
        public static final String TITLE_KEY = "TitleKey";
        public static final String USER_ID_KEY = "UserIdKey";
        public static final String ID_KEY = "IdKey";
        public static final String IS_COMPLETED_KEY = "IsCompletedKey";
    }

    public class ActivityResults{
        public static final int EDIT_TASK_RESULT = 999;
        public static final int CHANGE_FILTER_RESULT = 87;
    }
}
