package com.charbelay.listofshame.Presenter;

import com.charbelay.listofshame.Model.MapAndLocationModel;
import com.charbelay.listofshame.View.MapFragmentView;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Charbel on 2019-05-21.
 */
public class MapPresenter {
    MapFragmentView mapFragmentView;
    MapAndLocationModel mapAndLocationModel;


    public MapPresenter(MapFragmentView mapFragmentView){
        this.mapFragmentView=mapFragmentView;
        this.mapAndLocationModel= new MapAndLocationModel(this);
    }

    public void getInitialFlag(){
        mapAndLocationModel.getInitialFlag(mapFragmentView.getContext());
    }

    public void resultFlag(LatLng latLng){
        mapFragmentView.resultFlag(latLng);
    }
}
