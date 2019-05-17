package com.charbelay.listofshame.View;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.charbelay.listofshame.ListAdapter;
import com.charbelay.listofshame.Presenter.ListFragmentPresenter;
import com.charbelay.listofshame.R;
import com.charbelay.listofshame.Upload;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Charbel on 2019-05-17.
 */
public class ListFragmentView extends Fragment {

    private List<Upload> mUploads;
    private RecyclerView recyclerView;
    ListFragmentPresenter listFragmentPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = view.findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        listFragmentPresenter = new ListFragmentPresenter(this);
        listFragmentPresenter.getUploads();
        mUploads = new ArrayList<>();


        return view;
    }

    public void yourDataHasBeenServed(List<Upload> mUploads){
        this.mUploads=mUploads;
        ListAdapter adapter = new ListAdapter(mUploads,getContext());
        recyclerView.setAdapter(adapter);
    }

}
