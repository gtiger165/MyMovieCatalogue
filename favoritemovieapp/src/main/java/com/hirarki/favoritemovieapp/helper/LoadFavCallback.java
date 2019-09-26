package com.hirarki.favoritemovieapp.helper;

import android.database.Cursor;

public interface LoadFavCallback {
    void postExecute(Cursor favorite);
}
