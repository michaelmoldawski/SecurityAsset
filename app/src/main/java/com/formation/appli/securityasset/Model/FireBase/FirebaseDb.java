package com.formation.appli.securityasset.Model.FireBase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * Created by michael on 13-07-17.
 */

public class FirebaseDb {

    FirebaseDatabase database;
    DatabaseReference myRef;

    private FirebaseDb(FirebaseDatabase database) {
        this.database = FirebaseDatabase.getInstance();
        this.myRef = database.getReference("ID");
    }

    //public void getUserKey(String email) {
//
    //    Query queryRef = databaseRef.child(Constants.USERS_KEY)
    //            .orderByChild(Constants.USERS_EMAIL)
    //            .equalTo(email);
//
    //    queryRef.addChildEventListener(new ChildEventListener() {
    //        @Override
    //        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
    //            //TODO auto generated
    //        }
//
    //        @Override
    //        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
    //            //TODO auto generated;
    //        }
//
    //        @Override
    //        public void onChildRemoved(DataSnapshot dataSnapshot) {
    //            //TODO auto generated;
    //        }
//
    //        @Override
    //        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
    //            //TODO auto generated
    //        }
//
    //        @Override
    //        public void onCancelled(DatabaseError databaseError) {
    //            //TODO auto generated
    //        }
    //    });
    //}
}
