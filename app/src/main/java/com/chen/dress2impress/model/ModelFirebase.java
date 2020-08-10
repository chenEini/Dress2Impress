package com.chen.dress2impress.model;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ModelFirebase {
    FirebaseFirestore db;

    public ModelFirebase() {
        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();
    }

    public void addOutfit(Outfit outfit, final Model.Listener<Boolean> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("outfits").document(outfit.getId()).set(toJson(outfit)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (listener!=null){
                    listener.onComplete(task.isSuccessful());
                }
            }
        });
    }

    public void getAllOutfits(final Model.Listener<List<Outfit>> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("outfits").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Outfit> data = null;
                if (task.isSuccessful()) {
                    data = new LinkedList<Outfit>();
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        Outfit outfit = doc.toObject(Outfit.class);
                        data.add(outfit);
                    }
                }
                listener.onComplete(data);
            }
        });
    }

    private static Outfit factory(Map<String, Object> json){
        Outfit outfit = new Outfit();
        outfit.id = (String)json.get("id");
        outfit.title = (String)json.get("title");
        outfit.imageUrl = (String)json.get("imageUrl");
        outfit.description = (String) json.get("description");
        Timestamp ts = (Timestamp)json.get("lastUpdated");
        if (ts != null) outfit.lastUpdated = ts.getSeconds();
        return outfit;
    }

    private static Map<String, Object> toJson(Outfit outfit){
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", outfit.id);
        result.put("name", outfit.title);
        result.put("imageUrl", outfit.imageUrl);
        result.put("description", outfit.description);
        result.put("lastUpdated", FieldValue.serverTimestamp());
        return result;
    }
}
