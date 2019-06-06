package com.charbelay.listofshame.Model;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.charbelay.listofshame.Presenter.MapPresenter;
import com.charbelay.listofshame.ProfileFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by Charbel on 2019-05-21.
 */
public class MapAndLocationModel {
    MapPresenter mapPresenter;
    LocationManager locationManager;
    ProfileFragment pf;


    public MapAndLocationModel(MapPresenter mapPresenter){
        this.mapPresenter=mapPresenter;
    }

    public MapAndLocationModel(ProfileFragment pf){ this.pf=pf;}



    public void getInitialFlag(final Context ViewContext){

        locationManager = (LocationManager) ViewContext.getSystemService(LOCATION_SERVICE);
        final Context context = ViewContext;

        if (ActivityCompat.checkSelfPermission(ViewContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ViewContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    LatLng latLng = new LatLng(latitude, longitude);
                    if(pf!=null){
                        pf.takeLocation(latitude,longitude);
                    }
                    Geocoder geocoder = new Geocoder(ViewContext);
                    if(mapPresenter!=null){
                        mapPresenter.resultFlag(latLng);
                    }
//                    try {
//                        List<Address> adressesList = geocoder.getFromLocation(latitude,longitude,1);
//                        String str =adressesList.get(0).getCountryName()+" ";
//                        str +=adressesList.get(0).getLocality();
//
//                        mGoogleMap.addMarker(new MarkerOptions().position(latLng).title(str));
//                        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10.2f));
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                }
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
            });
        }
        else if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    LatLng latLng = new LatLng(latitude,longitude);
                    Geocoder geocoder = new Geocoder(ViewContext);
                    mapPresenter.resultFlag(latLng);

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
            });
        }


    }

    }
