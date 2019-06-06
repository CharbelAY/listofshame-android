package com.charbelay.listofshame;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
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

import com.charbelay.listofshame.Model.MapAndLocationModel;
import com.charbelay.listofshame.View.LoginView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by Charbel on 2019-04-19.
 */
public class ProfileFragment extends Fragment {


    public static final int PICK_IMAGE_REQUEST = 1;
    public static final int TAKE_PICTURE = 0;

    private Button buttonChooseImage;
    private Button buttonLogout;
    private Button OpenCamera;
    private Button buttonUploadImage;
    private ImageView imageView;
    private EditText editTextComment;
    private ProgressBar progressBar;

    private LocationManager locationManager;
    private LocationListener locationListener;
    double latitude;
    double longitude;

    private Uri ImageUri;
    private Bundle extras;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    private StorageTask areWeDoingAStorageTask;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>1 && grantResults[0]==PackageManager.PERMISSION_GRANTED){

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        buttonChooseImage = view.findViewById(R.id.chooseImage);
        LatLng latlng;
        buttonLogout = view.findViewById(R.id.LogoutButton);
//        OpenCamera = view.findViewById(R.id.TakePicture);
        buttonUploadImage = view.findViewById(R.id.UploadImage);
        imageView = view.findViewById(R.id.ImageView);
        editTextComment = view.findViewById(R.id.editTextImageComment);
        progressBar = view.findViewById(R.id.progressBar);

        locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.INTERNET
            },10);
        }

//
//        public void configureButton(){
//
//            locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
//        }

        storageReference  = FirebaseStorage.getInstance().getReference("Uploads");
        databaseReference = FirebaseDatabase.getInstance().getReference("Uploads");

        buttonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

//        OpenCamera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent,TAKE_PICTURE);
//
//            }
//        });


        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(view.getContext(), LoginView.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
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
        MapAndLocationModel malm = new MapAndLocationModel(this);
        malm.getInitialFlag(this.getContext());
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
                    if (taskSnapshot.getMetadata() != null) {
                        if (taskSnapshot.getMetadata().getReference() != null) {
                            Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                            result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageUrl = uri.toString();
                                    Upload upload =new Upload(editTextComment.getText().toString().trim(),latitude,longitude,imageUrl);
                                    String uploadId = databaseReference.push().getKey();
                                    databaseReference.child(uploadId).setValue(upload);
                                }
                            });
                        }
                    }
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

    public void takeLocation(double latitude,double longitude){
        this.latitude=latitude;
        this.longitude=longitude;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            ImageUri = data.getData();
            imageView.setImageURI(ImageUri);
        }
//        if(requestCode == TAKE_PICTURE && resultCode==RESULT_OK){
//            StorageReference storageRef = FirebaseStorage.getInstance().getReference("Uploads");
//            storageRef= storageRef.child(System.currentTimeMillis()+"."+"jpg");
//
//
//            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//            imageView.setImageBitmap(bitmap);
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
//            byte[] data1 = baos.toByteArray();
//            UploadTask uploadTask = storageRef.putBytes(data1);
//            uploadTask.addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//
//                }
//            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Toast.makeText(getContext(),"Success",Toast.LENGTH_SHORT).show();
//                }
//            });
//            Upload upload =new Upload(editTextComment.getText().toString().trim(),System.currentTimeMillis()+"."+"jpg");
//            String uploadId = databaseReference.push().getKey();
//            databaseReference.child(uploadId).setValue(upload);
//
//            }

        }

//    private String getPictureName(){
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
//        String timestamp = sdf.format(new Date());
//        return "listofshame"+timestamp+".jpg";
//    }


    }


