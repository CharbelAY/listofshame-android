package com.charbelay.listofshame.Model;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.charbelay.listofshame.ListAdapter;
import com.charbelay.listofshame.Presenter.ListFragmentPresenter;
import com.charbelay.listofshame.Upload;
import com.charbelay.listofshame.View.MapFragmentView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Charbel on 2019-05-17.
 */
public class DataProviderFirebase implements IDataProviderFirebase {

    private DatabaseReference mDataBaseRef;

    ListFragmentPresenter listFragmentPresenter;

    MapFragmentView mapFragmentView;

    private List<Upload> mUploads;

    private static DataProviderFirebase dataProviderSingleton = null;


    private DataProviderFirebase(){

    }

    public static DataProviderFirebase createMeMyDataProvider(){
        if(dataProviderSingleton==null){
            dataProviderSingleton = new DataProviderFirebase();
        }
        return dataProviderSingleton;
    }

    public void pleaseTakeMyReference(ListFragmentPresenter listFragmentPresenter){
        this.listFragmentPresenter=listFragmentPresenter;
    }

    public void pleaseTakeMyReferenceIAmMap(MapFragmentView mapFragmentView){
        this.mapFragmentView=mapFragmentView;
    }

    public void getMeThatData(){
        mUploads = new ArrayList<>();
        mDataBaseRef = FirebaseDatabase.getInstance().getReference("Uploads");
        mDataBaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Upload upload = postSnapshot.getValue(Upload.class);
                    mUploads.add(upload);
                }

                if(listFragmentPresenter!=null){
                    listFragmentPresenter.pleaseGetYourData(mUploads);

                }
                if(mapFragmentView!=null){
                    mapFragmentView.pleaseGetYourData(mUploads);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listFragmentPresenter.pleaseTakeThatError(databaseError.getMessage());
            }
        });


    }
}
