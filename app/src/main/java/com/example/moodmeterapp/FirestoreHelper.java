package com.example.moodmeterapp;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.Collections;



public class FirestoreHelper {

    private FirebaseFirestore db; // ref to entire database
    private CollectionReference dataRef;
    private ArrayList<Mood> moodArrayList= new ArrayList<>();

    public FirestoreHelper() {
        db = FirebaseFirestore.getInstance();
        dataRef = db.collection("moods");

        // This listener will listen for whenever the data is updated.  When that occurs, the arraylist
        // is cleared out and it is refreshed with the updated data.

        dataRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                // clear out the array list so that none of the events are duplicated in the display
                moodArrayList.clear();

                // this for each loop will get each Document Snapshot from the query, and one at a time,
                // convert them to an object of the Event class and then add them to the array list

                for (QueryDocumentSnapshot doc: value) {
                    Mood mood = doc.toObject(Mood.class);
                    moodArrayList.add(mood);
                }
            }
        });

    }

    public void addMood(Mood mood) {
        // use .add when you don't have a unique ID number for each document.  This will generate
        // one for you.  If you did have a unique ID number, then you would use set.
        moodArrayList.add(mood);
        db.collection("moods")
                .add(mood) // adds an event without a key defined yet
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    // documentReference contains a reference to the newly created Document if done successfully
                    public void onSuccess(DocumentReference documentReference) {
                        db.collection("moods").document(documentReference.getId())
                                .update("key", documentReference.getId());  // sets the DocID key for the Event that was just added
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("FirestoreHelper Error", "Error adding document", e);
                    }
                });
    }

    public ArrayList<Mood> getMoodArrayList() {
        Collections.sort(moodArrayList);
        return moodArrayList;
    }

}
