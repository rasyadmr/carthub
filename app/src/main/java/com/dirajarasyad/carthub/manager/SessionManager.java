package com.dirajarasyad.carthub.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.dirajarasyad.carthub.database.manager.DBUserManager;
import com.dirajarasyad.carthub.model.User;

public class SessionManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor preferenceEditor;
    private Context context;

    private static final String PREFERENCE_NAME = "auth_pref";
    private static final int PREFERENCE_MODE = Context.MODE_PRIVATE;

    private static final String AUTH_USER_ID = "user_id";
    private static final String AUTH_IS_LOGGED = "is_logged";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = this.context.getSharedPreferences(PREFERENCE_NAME, PREFERENCE_MODE);
        preferenceEditor = sharedPreferences.edit();
    }

    public void createSession(User user) {
        preferenceEditor.putString(AUTH_USER_ID, user.getId());
        preferenceEditor.putBoolean(AUTH_IS_LOGGED, true);
        preferenceEditor.apply();
    }

    public void destroySession() {
        preferenceEditor.clear().apply();
    }

    public boolean getStatus() {
        return sharedPreferences.getBoolean(AUTH_IS_LOGGED, false);
    }

    public User getUser() {
        String userId = sharedPreferences.getString(AUTH_USER_ID, null);
        DBUserManager userManager = new DBUserManager(context);
        userManager.open();
        User user = userManager.getUserById(userId);
        userManager.close();
        return user;
    }
}
