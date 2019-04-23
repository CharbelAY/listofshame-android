package com.charbelay.listofshame;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Charbel on 2019-04-19.
 */
public class ProfileFragment extends Fragment {


    public static final int PICK_IMAGE_REQUEST = 1;
    public static final int TAKE_PICTURE       = 0;

    private Button buttonChooseImage;
    private Button OpenCamera;
    private Button      buttonUploadImage;
    private ImageView imageView;
    private EditText editTextComment;
    private ProgressBar progressBar;

    private Uri ImageUri;
    private Bundle extras;

    private StorageReference  storageReference;
    private DatabaseReference databaseReference;

    private StorageTask areWeDoingAStorageTask;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        buttonChooseImage = view.findViewById(R.id.chooseImage);
        OpenCamera        = view.findViewById(R.id.TakePicture);
        buttonUploadImage = view.findViewById(R.id.UploadImage);
        imageView         = view.findViewById(R.id.ImageView);
        editTextComment   = view.findViewById(R.id.editTextImageComment);
        progressBar       = view.findViewById(R.id.progressBar);

        String s = FirebaseAuth.getInstance().getCurrentUser().getUid();
        storageReference  = FirebaseStorage.getInstance().getReference("Uploads"+s);
        databaseReference = FirebaseDatabase.getInstance().getReference("Uploads"+s);

        buttonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        OpenCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,TAKE_PICTURE);
            }
        });

        buttonUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(areWeDoingAStorageTask!=null && areWeDoingAStorageTask.isInProgress()){
                    Toast.makeText(getContext(),"Your File is still uploading please wait",Toast.LENGTH_SHORT).show();
                }else {
                    uploadFile();
                }
            }
        });

        return view;
    }

    private String getFileExtension(Uri uri){
        ContentResolver cr = getActivity().getContentResolver();
        MimeTypeMap mtm = MimeTypeMap.getSingleton();
        return mtm.getExtensionFromMimeType(cr.getType(uri));
    }

    private void uploadFile(){
        if(ImageUri != null){
            StorageReference fileReference = storageReference.child(System.currentTimeMillis()+"."+getFileExtension(ImageUri));
            areWeDoingAStorageTask=fileReference.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(0);
                        }
                    },500);
                    Toast.makeText(getContext(),"Successfully uploaded post",Toast.LENGTH_SHORT).show();

                    Upload upload = new Upload(editTextComment.getText().toString().trim(),
                            taskSnapshot.getStorage().getDownloadUrl().toString());

                    String uploadId = databaseReference.push().getKey();
                    databaseReference.child(uploadId).setValue(upload);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    progressBar.setProgress((int)progress);
                }
            });
        }else{
            Toast.makeText(getContext(),"No Image Selected",Toast.LENGTH_SHORT).show();
        }
    }


    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            ImageUri = data.getData();
            imageView.setImageURI(ImageUri);
        }
        if(requestCode == TAKE_PICTURE && resultCode==RESULT_OK){
            extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
            Uri filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            }catch (Exception e){

            }

        }
    }


}

