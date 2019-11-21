package com.joyfulshark.trymlkit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionLabelDetector;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dog_and_cat);
        FirebaseVisionImage fbVisionImage = FirebaseVisionImage.fromBitmap(bitmap);
        FirebaseVisionLabelDetector detector = FirebaseVision.getInstance().getVisionLabelDetector();
        detector.detectInImage(fbVisionImage)
               .addOnSuccessListener(
                       new OnSuccessListener<List<FirebaseVisionLabel>>() {
                           @Override
                           public void onSuccess(List<FirebaseVisionLabel> labels) {
                               ArrayList<String> labelNames = new ArrayList<>();
                               for (FirebaseVisionLabel label : labels) {
                                   labelNames.add(label.getLabel());
                               }
                               Toast.makeText(MainActivity.this, TextUtils.join(", ", labelNames), Toast.LENGTH_SHORT).show();
                           }
                       })
               .addOnFailureListener(
                       new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {
                               Toast.makeText(MainActivity.this, "Labeling failed!", Toast.LENGTH_SHORT).show();
                           }
                       });
    }
}

