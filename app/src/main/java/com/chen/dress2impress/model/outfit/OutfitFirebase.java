package com.chen.dress2impress.model.outfit;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
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
    final static String OUTFIT_COLLECTION = "outfits";

    public static void getAllOutfitsSince(long since, final OutfitModel.Listener<List<Outfit>> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Timestamp ts = new Timestamp(since, 0);
        db.collection(OUTFIT_COLLECTION).whereGreaterThanOrEqualTo("lastUpdated", ts)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Outfit> data = null;
                if (task.isSuccessful()) {
                    data = new LinkedList<Outfit>();
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        Map<String, Object> json = doc.getData();
                        Outfit outfit = factory(doc.getId(), json);
                        data.add(outfit);
                    }
                }
                listener.onComplete(data);
            }
        });
    }

    public static void addOutfit(final Outfit outfit, final OutfitModel.Listener<Outfit> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(OUTFIT_COLLECTION).add(toJson(outfit)).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (listener != null) {
                    DocumentReference doc = task.getResult();
                    outfit.id = doc.getId();
                    listener.onComplete(outfit);
                }
            }
        });
    }

    public static void updateOutfit(Outfit outfit, final OutfitModel.Listener<Boolean> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> outfitJson = toJson(outfit);
        db.collection(OUTFIT_COLLECTION).document(outfit.id).set(outfitJson, SetOptions.merge())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (listener != null) {
                            listener.onComplete(task.isSuccessful());
                        }
                    }
                });
    }

    public static void deleteOutfit(String outfitId, final OutfitModel.Listener<Boolean> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(OUTFIT_COLLECTION).document(outfitId).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (listener != null) {
                    listener.onComplete(task.isSuccessful());
                }
            }
        });
    }

    private static Outfit factory(String id, Map<String, Object> json) {
        String title = (String) json.get("title");
        String imageUrl = (String) json.get("imageUrl");
        String description = (String) json.get("description");
        String ownerId = (String) json.get("ownerId");
        String ownerName = (String) json.get("ownerName");
        Outfit outfit = new Outfit(id, ownerId, ownerName, title, imageUrl, description);
        Timestamp ts = (Timestamp) json.get("lastUpdated");
        if (ts != null) outfit.lastUpdated = ts.getSeconds();
        return outfit;
    }

    private static Map<String, Object> toJson(Outfit outfit) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("title", outfit.title);
        result.put("imageUrl", outfit.imageUrl);
        result.put("description", outfit.description);
        result.put("lastUpdated", FieldValue.serverTimestamp());
        result.put("ownerName", outfit.ownerName);
        result.put("ownerId", outfit.ownerId);
        return result;
    }
}
