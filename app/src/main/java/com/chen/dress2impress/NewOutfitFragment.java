package com.chen.dress2impress;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chen.dress2impress.model.FirebaseStorage;
import com.chen.dress2impress.model.outfit.Outfit;
import com.chen.dress2impress.model.outfit.OutfitModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class NewOutfitFragment extends Fragment {
    View view;
    ImageView imageView;
    Bitmap imageBitmap;
    TextView outfitTitle;
    TextView outfitDescription;
    ProgressBar progressbar;

    public NewOutfitFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_new_outfit, container, false);

        progressbar = view.findViewById(R.id.new_outfit_progress);
        progressbar.setVisibility(View.INVISIBLE);

        imageView = view.findViewById(R.id.new_outfit_image);
        Button uploadButton = view.findViewById(R.id.new_outfit_upload_button);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        outfitTitle = view.findViewById(R.id.new_outfit_title);
        outfitDescription = view.findViewById(R.id.new_outfit_description);

        Button saveBtn = view.findViewById(R.id.new_outfit_save_button);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOutfit();
            }
        });

        return view;
    }

    private void saveOutfit() {
        progressbar.setVisibility(View.VISIBLE);

        final String title = outfitTitle.getText().toString();
        final String description = outfitDescription.getText().toString();

        Date d = new Date();

        FirebaseStorage.uploadImage(imageBitmap, "outfit_image" + d.getTime(), new FirebaseStorage.Listener() {
            @Override
            public void onSuccess(String url) {
                Outfit outfit = new Outfit("", "", title, url, description);
                OutfitModel.instance.addOutfit(outfit, new OutfitModel.CompleteListener() {
                    @Override
                    public void onComplete() {
                        NavController navController = Navigation.findNavController(view);
                        navController.navigateUp();
                    }
                });
            }

            @Override
            public void onFail() {
                progressbar.setVisibility(View.INVISIBLE);
                Snackbar mySnackbar = Snackbar.make(view, R.string.fail_to_save_outfit, Snackbar.LENGTH_LONG);
                mySnackbar.show();
            }
        });
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void uploadImage() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = rotateImage((Bitmap) extras.get("data"));
            imageView.setImageBitmap(imageBitmap);
        }
    }

    public static Bitmap rotateImage(Bitmap source) {
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
}