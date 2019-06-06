package com.charbelay.listofshame.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.charbelay.listofshame.DetailPost;
import com.charbelay.listofshame.Model.DataProviderFirebase;
import com.charbelay.listofshame.Presenter.MapPresenter;
import com.charbelay.listofshame.R;
import com.charbelay.listofshame.Upload;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

/**
 * Created by Charbel on 2019-05-21.
 */
public class MapFragmentView extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    GoogleMap mGoogleMap;
    MapPresenter mapPresenter;
    MapView mMapView;
    View mView;
    Context context;

    private List<Upload> mUploads;


    public MapFragmentView(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapPresenter = new MapPresenter(this);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView= inflater.inflate(R.layout.map_test,container,false);
        return mView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView = mView.findViewById(R.id.map);
        if(mMapView!=null){
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        context = getContext();
        DataProviderFirebase dpf = DataProviderFirebase.createMeMyDataProvider();
        dpf.pleaseTakeMyReferenceIAmMap(this);
        dpf.getMeThatData();
//        mapPresenter.getInitialFlag();
        mGoogleMap = googleMap;
    }

    public void pleaseGetYourData(List<Upload> mUploads){
        this.mUploads=mUploads;
        MapsInitializer.initialize(context);
        mGoogleMap.setOnMarkerClickListener((GoogleMap.OnMarkerClickListener) this);
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng Bey = new LatLng(33.8938,35.5018);
        CameraPosition Beirut = CameraPosition.builder().target(Bey).zoom(11).bearing(0).tilt(45).build();
        mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Beirut));
        for(Upload up : mUploads){
            double lat = up.getLatitude();
            double lon = up.getLongitude();
            LatLng latLng = new LatLng(lat,lon);
            mGoogleMap.addMarker(new MarkerOptions().position(latLng).title(up.getComment()));

        }
    }


    public void resultFlag(LatLng latLng){
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10.2f));
        MapsInitializer.initialize(context);
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap.addMarker(new MarkerOptions().position(latLng).title("Beirut"));
        CameraPosition JisrElBacha = CameraPosition.builder().target(latLng).zoom(16).bearing(0).tilt(45).build();
        mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(JisrElBacha));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        for(Upload up:mUploads){
            if(up.getComment().equals(marker.getTitle())){
                Intent intent = new Intent(getContext(), DetailPost.class);
                intent.putExtra("upload",up);
                getContext().startActivity(intent);
            }
        }
        return true;
    }
}
