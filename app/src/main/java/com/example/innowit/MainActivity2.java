package com.example.innowit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class MainActivity2 extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private Button mButtonChooseImage, mButtonUpload;
    private TextView mTextViewShowUploads;
    //private EditText mEditTextFileName;
    private ImageView mImageView;
    //private ProgressBar mProgressBar;

    private Uri mImageUri;

    //private FirebaseAuth fAuth;
    FirebaseFirestore fstorage;
    StorageReference storageReference;

    private final int IMG_REQUEST_ID = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mButtonChooseImage = findViewById(R.id.btn_ChooseImage);
        mButtonUpload = findViewById(R.id.uploadBtn);
        mTextViewShowUploads = findViewById(R.id.showUploads);
        //mEditTextFileName = findViewById(R.id.textFileName);
        mImageView = findViewById(R.id.uploadedImage);
        //mProgressBar = findViewById(R.id.progressBar2);

        mButtonUpload.setEnabled(false);

        //fAuth = FirebaseAuth.getInstance();
        fstorage = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();


        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestImage();
            }
        });

        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveInFirebase();
            }
        });

        mTextViewShowUploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagesActivity();
            }
        });

    }

    private void requestImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),IMG_REQUEST_ID);
    }

    private void saveInFirebase(){
        if(mImageUri != null){

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Please Wait...");
            progressDialog.show();

            StorageReference reference = storageReference.child("picture/"+ UUID.randomUUID().toString());

            try {
                reference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity2.this, "Uploaded Successfully.", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity2.this, "Error Occurred." + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        progressDialog.setMessage("Uploading " + (int) progress + "%");
                        mButtonChooseImage.setEnabled(true);
                        mButtonUpload.setEnabled(false);
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMG_REQUEST_ID && resultCode == RESULT_OK && data != null && data.getData() != null){
            mImageUri = data.getData();

            try {
                Bitmap bitmapImg = MediaStore.Images.Media.getBitmap(getContentResolver(),mImageUri);
                mImageView.setImageBitmap(bitmapImg);
                mButtonChooseImage.setEnabled(false);
                mButtonUpload.setEnabled(true);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void openImagesActivity(){
        Intent intent = new Intent(this,ViewUploads.class);
        startActivity(intent);
    }


    public void showUploads(View view) {
        Intent intent = new Intent(this, ViewUploads.class);
        startActivity(intent);
    }

    public void userProfile1(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        //Intent intent = new Intent(this, Login.class);
        //startActivity(intent);

        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }


}
