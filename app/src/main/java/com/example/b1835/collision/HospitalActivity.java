package com.example.b1835.collision;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class HospitalActivity extends Activity {

    static final String API_KEY = "0710147267ece56eb2739bdf8c327fe9";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);

        MapView mapView = new MapView(this);
        mapView.setDaumMapApiKey(API_KEY);
        ViewGroup mapViewContainer = (ViewGroup)findViewById(R.id.Map_view);
        mapViewContainer.addView(mapView);

        //mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord());  // 중심점
        mapView.setZoomLevel(7, true);
        mapView.zoomOut(true);
        mapView.zoomIn(true);
    }
}
