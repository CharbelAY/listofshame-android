package com.charbelay.listofshame;



import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ListFragment extends Fragment {

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.RecyclerView);
        initImageBitmaps();
        ListAdapter adapter = new ListAdapter(mNames,mImageUrls,view.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        return view;
    }


    private void initImageBitmaps(){
        mImageUrls.add("https://www.bp.com/content/dam/bp/business-sites/en/global/corporate/images-jpg-png/what-we-do/worldwide/worldwide-finland.jpg.img.1440.medium.jpg");
        mNames.add("Samir");
        mImageUrls.add("https://www.bp.com/content/dam/bp/business-sites/en/global/corporate/images-jpg-png/what-we-do/worldwide/worldwide-finland.jpg.img.1440.medium.jpg");
        mNames.add("Samir");
        mImageUrls.add("https://www.bp.com/content/dam/bp/business-sites/en/global/corporate/images-jpg-png/what-we-do/worldwide/worldwide-finland.jpg.img.1440.medium.jpg");
        mNames.add("Samir");
        mImageUrls.add("https://www.bp.com/content/dam/bp/business-sites/en/global/corporate/images-jpg-png/what-we-do/worldwide/worldwide-finland.jpg.img.1440.medium.jpg");
        mNames.add("Samir");
        mImageUrls.add("https://www.bp.com/content/dam/bp/business-sites/en/global/corporate/images-jpg-png/what-we-do/worldwide/worldwide-finland.jpg.img.1440.medium.jpg");
        mNames.add("Samir");
        mImageUrls.add("https://www.bp.com/content/dam/bp/business-sites/en/global/corporate/images-jpg-png/what-we-do/worldwide/worldwide-finland.jpg.img.1440.medium.jpg");
        mNames.add("Samir");
        mImageUrls.add("https://www.bp.com/content/dam/bp/business-sites/en/global/corporate/images-jpg-png/what-we-do/worldwide/worldwide-finland.jpg.img.1440.medium.jpg");
        mNames.add("Samir");
        mImageUrls.add("https://www.bp.com/content/dam/bp/business-sites/en/global/corporate/images-jpg-png/what-we-do/worldwide/worldwide-finland.jpg.img.1440.medium.jpg");
        mNames.add("Samir");
        mImageUrls.add("https://www.bp.com/content/dam/bp/business-sites/en/global/corporate/images-jpg-png/what-we-do/worldwide/worldwide-finland.jpg.img.1440.medium.jpg");
        mNames.add("Samir");
        mImageUrls.add("https://www.bp.com/content/dam/bp/business-sites/en/global/corporate/images-jpg-png/what-we-do/worldwide/worldwide-finland.jpg.img.1440.medium.jpg");
        mNames.add("Samir");
        mImageUrls.add("https://www.bp.com/content/dam/bp/business-sites/en/global/corporate/images-jpg-png/what-we-do/worldwide/worldwide-finland.jpg.img.1440.medium.jpg");
        mNames.add("Samir");
        mImageUrls.add("https://www.bp.com/content/dam/bp/business-sites/en/global/corporate/images-jpg-png/what-we-do/worldwide/worldwide-finland.jpg.img.1440.medium.jpg");
        mNames.add("Samir");
        mImageUrls.add("https://www.bp.com/content/dam/bp/business-sites/en/global/corporate/images-jpg-png/what-we-do/worldwide/worldwide-finland.jpg.img.1440.medium.jpg");
        mNames.add("Samir");

    }

}
