package com.example.tmd.hi_20172.activity.map;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tmd.hi_20172.R;
import com.example.tmd.hi_20172.activity.TreeDetail;
import com.example.tmd.hi_20172.model.Tree;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.tmd.hi_20172.activity.map.GetDirectionsData.listRoutesPolylines;
import static com.example.tmd.hi_20172.activity.map.GetDirectionsData.alternativePattern;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnPolylineClickListener {

    private static final String MARKER_TREE = "MARKER_TREE";
    public static final String TREE = "TREE";
    private static final int REQUEST_TREE_DETAIL_ACT = 15;
    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    int PROXIMITY_RADIUS = 10000;
    double latitude, longitude;
    double end_latitude, end_longitude;

    // TODO: 06/04/2018
    Map<String, Tree> trees = new HashMap();
    Map<String, Marker> markers = new HashMap<>();
    Map<String, Tree> tour = new HashMap<>();
    private Button btnBatDauTuoi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        //Check if Google Play Services Available or not
        if (!CheckGooglePlayServices()) {
            Log.d("onCreate", "Finishing test case since Google Play Services are not available");
            finish();
        } else {
            Log.d("onCreate", "Google Play Services available.");
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        btnBatDauTuoi = findViewById(R.id.bat_dau_tuoi);
    }

    private boolean CheckGooglePlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,
                        0).show();
            }
            return false;
        }
        return true;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

        mMap.setOnMarkerClickListener(this);
        mMap.setOnPolylineClickListener(this);

        // TODO: 06/04/2018
        fakePosition();
        addTrees();

    }

    // TODO: 06/04/2018
    private void fakePosition() {
        // fake position in Bach Khoa
        latitude = 21.005019;
        longitude = 105.843644;
        LatLng latLng = new LatLng(latitude, longitude);// Bach Khoa
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        mCurrLocationMarker = mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17));
    }

    // TODO: 06/04/2018
    private void addTrees() {
        // TODO: 06/04/2018
        List<Tree> tempTree = new ArrayList<>();
        tempTree.add(new Tree(new LatLng(21.004730, 105.844619), R.mipmap.ic_tree));
        tempTree.add(new Tree(new LatLng(21.004433, 105.846986), R.mipmap.ic_tree));
        tempTree.add(new Tree(new LatLng(21.007478, 105.847441), R.mipmap.ic_tree));
        tempTree.add(new Tree(new LatLng(21.006916, 105.842095), R.mipmap.ic_tree));
        tempTree.add(new Tree(new LatLng(21.007312, 105.843096), R.mipmap.ic_tree));
        tempTree.add(new Tree(new LatLng(21.004701, 105.842026), R.mipmap.ic_tree));
        tempTree.add(new Tree(new LatLng(21.003840, 105.841967), R.mipmap.ic_tree));
        tempTree.add(new Tree(new LatLng(21.003700, 105.843545), R.mipmap.ic_tree));
        tempTree.add(new Tree(new LatLng(21.006808, 105.845926), R.mipmap.ic_tree));
        tempTree.add(new Tree(new LatLng(21.005520, 105.852976), R.mipmap.ic_tree));
        tempTree.add(new Tree(new LatLng(21.015181, 105.847832), R.mipmap.ic_tree));
        for (Tree tree : tempTree) {
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(tree.getLatlon().latitude, tree.getLatlon().longitude))
                    .snippet(MARKER_TREE)
                    .icon(BitmapDescriptorFactory.fromResource(tree.getIcon()))
                    .title(String.valueOf(tree.getId())));
            tree.setId(marker.getId());
            trees.put(marker.getId(), tree);
            markers.put(marker.getId(), marker);
        }
    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    public void onClick(View v) {
        Object dataTransfer[] = new Object[2];
        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
        switch (v.getId()) {
            // TODO: 06/04/2018

            case R.id.bat_dau_tuoi:
                // xoa duong di truoc day
                for (List<Polyline> route : listRoutesPolylines) {
                    for (Polyline polyline : route) {
                        polyline.remove();
                    }
                }
                listRoutesPolylines.clear();
                dataTransfer = new Object[3];
                List<Tree> stopover = new ArrayList(tour.values());
                String url = getDirectionsUrl(stopover);
                GetDirectionsData getDirectionsData = new GetDirectionsData();
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;
                getDirectionsData.execute(dataTransfer);
                break;
//        }
        }
    }

    // TODO: 06/04/2018
    private String getDirectionsUrl(List<Tree> stopover) {
        StringBuilder googleDirectionsUrl = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?");
        googleDirectionsUrl.append("origin=" + latitude + "," + longitude);
        googleDirectionsUrl.append("&destination=" + stopover.get(stopover.size() - 1).getLatlon().latitude + "," + stopover.get(stopover.size() - 1).getLatlon().longitude);
        googleDirectionsUrl.append("&alternatives=true");
        googleDirectionsUrl.append("&mode=walking");
        if (stopover.size() > 1) {
            googleDirectionsUrl.append("&waypoints=optimize:true");
            for (Tree tree : stopover) {
                googleDirectionsUrl.append("|via:" + tree.getLatlon().latitude + "," + tree.getLatlon().longitude);
            }
        }
        googleDirectionsUrl.append("&key=" + "AIzaSyCAcfy-02UHSu2F6WeQ1rhQhkCr51eBL9g");
        Log.e("MY_TAG", "getDirectionsUrl: " + googleDirectionsUrl.toString());
        return googleDirectionsUrl.toString();
    }

    private String getDirectionsUrl() {
        StringBuilder googleDirectionsUrl = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?");
        googleDirectionsUrl.append("origin=" + latitude + "," + longitude);
        googleDirectionsUrl.append("&destination=" + end_latitude + "," + end_longitude);
        googleDirectionsUrl.append("&key=" + "AIzaSyCAcfy-02UHSu2F6WeQ1rhQhkCr51eBL9g");

        return googleDirectionsUrl.toString();
    }

    private String getUrl(double latitude, double longitude, String nearbyPlace) {
        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=" + latitude + "," + longitude);
        googlePlacesUrl.append("&radius=" + PROXIMITY_RADIUS);
        googlePlacesUrl.append("&type=" + nearbyPlace);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + "AIzaSyBj-cnmMUY21M0vnIKz0k3tD3bRdyZea-Y");
        Log.d("getUrl", googlePlacesUrl.toString());
        return (googlePlacesUrl.toString());
    }


    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        // TODO: 06/04/2018
//        Log.d("onLocationChanged", "entered");
//
//        mLastLocation = location;
//        if (mCurrLocationMarker != null) {
//            mCurrLocationMarker.remove();
//        }
//
//        latitude = location.getLatitude();
//        longitude = location.getLongitude();
//
//
//        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(latLng);
//        markerOptions.draggable(true);
//        markerOptions.title("Current Position");
//        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
//        mCurrLocationMarker = mMap.addMarker(markerOptions);
//
//        //move map camera
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(17));
//
//
//        Toast.makeText(MapsActivity.this, "Your Current Location", Toast.LENGTH_LONG).show();
//
//
//        //stop location updates
//        if (mGoogleApiClient != null) {
//            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
//            Log.d("onLocationChanged", "Removing Location Updates");
//        }

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.getSnippet() != null && marker.getSnippet().equals(MARKER_TREE)) {
            Intent intent = new Intent(this, TreeDetail.class);
            Tree tree = trees.get(marker.getId());
            if (tree != null) {
                intent.putExtra(TREE, tree);
                startActivityForResult(intent, REQUEST_TREE_DETAIL_ACT);
            }
        }
        return true;
    }


    // TODO: 06/04/2018
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TREE_DETAIL_ACT) {
            if (resultCode == Activity.RESULT_OK) {
                Tree tree = data.getParcelableExtra("result");
                trees.put(tree.getId(), tree);
//                Toast.makeText(this, "" + tree.isChoose(), Toast.LENGTH_SHORT).show();
                Marker marker = markers.get(tree.getId());
                if (marker != null) {
                    if (tree.isChoose()) {
                        marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_checked));
                        tour.put(tree.getId(), tree);
                    } else {
                        marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_tree));
                        tour.remove(tree.getId());
                    }
                }
            }
        }
        checkTour();
    }

    private void checkTour() {
        // neu Tour != null thi hien nut de bat dau tuoi
        if (tour.isEmpty()) {
            btnBatDauTuoi.setVisibility(View.GONE);
        } else {
            btnBatDauTuoi.setVisibility(View.VISIBLE);
        }
        Toast.makeText(this, "" + tour.size(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPolylineClick(Polyline polyline) {
        // chuyen het ve ...
        for (List<Polyline> route : listRoutesPolylines) {
            for (Polyline polyline1 : route) {
                polyline1.setPattern(alternativePattern);
            }
        }
        // boi dam shortest route
        for (int i = 0; i < listRoutesPolylines.size(); i++) {
            for (Polyline polyline1 : listRoutesPolylines.get(i)) {
                if (polyline1.getId().equals(polyline.getId())) {
                    for (Polyline polyline2 : listRoutesPolylines.get(i)) {
                        polyline2.setPattern(null);
                    }
                    return;
                }
            }
        }
    }
}


