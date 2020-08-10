package com.chen.dress2impress.model.outfit;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class OutfitFirebase {

    public static void addOutfit(Outfit outfit, final OutfitModel.Listener<Boolean> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("outfits").document(outfit.getId()).set(toJson(outfit)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (listener != null) {
                    listener.onComplete(task.isSuccessful());
                }
            }
        });
    }

    public static void getAllOutfits(final OutfitModel.Listener<List<Outfit>> listener) {
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

    public static void deleteOutfit(String outfitId, final OutfitModel.Listener<Boolean> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("outfits").document(outfitId).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (listener != null) {
                    listener.onComplete(task.isSuccessful());
                }
            }
        });
    }

    public static void updateOutfit(Outfit outfit, final OutfitModel.Listener<Boolean> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> outfitJson = toJson(outfit);
        db.collection("outfits").document(outfit.id).set(outfitJson, SetOptions.merge())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (listener != null) {
                            listener.onComplete(task.isSuccessful());
                        }
                    }
                });
    }

    private static Outfit factory(Map<String, Object> json) {
        String id = (String) json.get("id");
        String title = (String) json.get("title");
        String imageUrl = (String) json.get("imageUrl");
        String description = (String) json.get("description");
        Outfit outfit = new Outfit(id, title, imageUrl, description);
        Timestamp ts = (Timestamp) json.get("lastUpdated");
        if (ts != null) outfit.lastUpdated = ts.getSeconds();
        return outfit;
    }

    private static Map<String, Object> toJson(Outfit outfit) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", outfit.id);
        result.put("name", outfit.title);
        result.put("imageUrl", outfit.imageUrl);
        result.put("description", outfit.description);
        result.put("lastUpdated", FieldValue.serverTimestamp());
        return result;
    }
}
