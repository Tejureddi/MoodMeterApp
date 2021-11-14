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

    private FirebaseFirestore db;
    private CollectionReference dataRef;
    private ArrayList<Mood> moodArrayList= new ArrayList<>();

    // constructor

    public FirestoreHelper() {

        // instantiate FirebaseFirestore object

        db = FirebaseFirestore.getInstance();

        // determine where to save moods in database

        dataRef = db.collection("moods");

        // Snapshot Listener refreshes data in array list

        dataRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                // clear array list

                moodArrayList.clear();

                // get documents from database and convert to mood objet

                for (QueryDocumentSnapshot doc: value) {
                    Mood mood = doc.toObject(Mood.class);
                    moodArrayList.add(mood);
                }

            }

        });

    }


    public void addMood(Mood mood) {

        // add mood to array list

        moodArrayList.add(mood);

        // add mood to database

        db.collection("moods")
                .add(mood) // adds an event without a key defined yet
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        db.collection("moods").document(documentReference.getId())

                                // sets key for newly added Mood objects

                                .update("key", documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("FirestoreHelper Error", "Error adding document", e);
                    }
                });
    }

    // returns array list of saved Mood objects

    public ArrayList<Mood> getMoodArrayList() {
        Collections.sort(moodArrayList);
        return moodArrayList;
    }

    public boolean entryExists(Mood mood) {

        boolean alreadyEntered = false;

        for (int i = 0; i < moodArrayList.size(); i++) {
            if (moodArrayList.get(i).equals(mood)) {
                alreadyEntered = true;
            }
        }

        return alreadyEntered;

    }

}