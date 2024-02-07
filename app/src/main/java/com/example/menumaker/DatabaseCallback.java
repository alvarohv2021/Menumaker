package com.example.menumaker;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public interface DatabaseCallback {
    void onCallback(DataSnapshot dataSnapshot);
    void onError(DatabaseError databaseError);
}