package com.codetalenta.dapur.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {

    Context context;
    SharedPreferences mSettings;

    public Session(Context context) {
        this.context = context;
        this.mSettings = PreferenceManager.getDefaultSharedPreferences(this.context);
    }

    public void add(String name, int i) {
        SharedPreferences.Editor editor = this.mSettings.edit();
        editor.putInt(name, i);
        editor.apply();
    }

    public void add(String name, String i) {
        SharedPreferences.Editor editor = this.mSettings.edit();
        editor.putString(name, i);
        editor.apply();
    }

    public void add(String name, Float i) {
        SharedPreferences.Editor editor = this.mSettings.edit();
        editor.putFloat(name, i);
        editor.apply();
    }

    public void add(String name, Boolean i) {
        SharedPreferences.Editor editor = this.mSettings.edit();
        editor.putBoolean(name, i);
        editor.apply();
    }

    public void add(String name, Long i) {
        SharedPreferences.Editor editor = this.mSettings.edit();
        editor.putLong(name, i);
        editor.apply();
    }

    public void remove(String name) {
        SharedPreferences.Editor editor = this.mSettings.edit();
        editor.remove(name);
        editor.apply();
    }

    public void destroy() {
        SharedPreferences.Editor editor = this.mSettings.edit();
        editor.clear();
        editor.apply();
    }

    public int getInt(String name) {
        return this.mSettings.getInt(name, 0);
    }

    public String getString(String name) {
        return this.mSettings.getString(name, "");
    }

    public Float getFloat(String name) {
        return this.mSettings.getFloat(name, 0);
    }

    public Boolean getBoolean(String name) {
        return this.mSettings.getBoolean(name, false);
    }

    public Long getLong(String name) {
        return this.mSettings.getLong(name, 0);
    }
}
