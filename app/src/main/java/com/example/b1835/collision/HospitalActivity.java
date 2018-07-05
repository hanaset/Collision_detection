package com.example.b1835.collision;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.Toast;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class HospitalActivity extends Activity {

    static final String API_KEY = "0710147267ece56eb2739bdf8c327fe9";

    private final int PERMISSIONS_ACCESS_FINE_LOCATION = 1000;
    private final int PERMISSIONS_ACCESS_COARSE_LOCATION = 1001;
    private boolean isAccessFineLocation = false;
    private boolean isAccessCoarseLocation = false;
    private boolean isPermission = false;

    // GPSTracker class
    private GpsInfo gps;

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

        callPermission();
        GPSInfoCall(mapView);
    }

    void GPSInfoCall(MapView mapView){

        gps = new GpsInfo(HospitalActivity.this);
        if(!isPermission){
            callPermission();
            return;
        }

        if(gps.isGetLocation()){
            double lat = gps.getLatitude();
            double lon = gps.getLongitude();

            MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(lat, lon);
            mapView.setMapCenterPointAndZoomLevel(mapPoint, 1,true);
        }else {
            gps.showSettingsAlert();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_ACCESS_FINE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            isAccessFineLocation = true;

        } else if (requestCode == PERMISSIONS_ACCESS_COARSE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            isAccessCoarseLocation = true;
        }

        if (isAccessFineLocation && isAccessCoarseLocation) {
            isPermission = true;
        }
    }

    // 전화번호 권한 요청
    private void callPermission() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_ACCESS_FINE_LOCATION);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_ACCESS_COARSE_LOCATION);
        } else {
            isPermission = true;
        }
    }
}
