package com.example.innowit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ViewUploads extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;

    private StorageReference mStorageReference;
    private List<Upload> mUploads;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_uploads);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mUploads = new ArrayList<>();

        mStorageReference = FirebaseStorage.getInstance().getReference("uploads");
        //mStorageReference

    }

}





        /*mStorageReference = FirebaseStorage.getInstance().getReference().child("picture/Screenshot (1).png");

        try{
            final File localFile = File.createTempFile("Screenshot (1)", "png");
            mStorageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(ViewUploads.this,"Pictures Retrieved",Toast.LENGTH_SHORT);
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    ((ImageView)findViewById(R.id.imageView4)).setImageBitmap(bitmap);
                    ((ImageView)findViewById(R.id.imageView5)).setImageBitmap(bitmap);
                    ((ImageView)findViewById(R.id.imageView6)).setImageBitmap(bitmap);
                    ((ImageView)findViewById(R.id.imageView7)).setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ViewUploads.this,"Error Occurred",Toast.LENGTH_SHORT);
                }
            });

        }catch(Exception e){
            e.printStackTrace();
        }*/


