package com.example.tmd.hi_20172.activity.map;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tmd.hi_20172.R;
import com.example.tmd.hi_20172.activity.history.HistoryActivity;
import com.example.tmd.hi_20172.activity.LanguageActivity;
import com.example.tmd.hi_20172.activity.TreeDetail;
import com.example.tmd.hi_20172.activity.UserInfoActivity;
import com.example.tmd.hi_20172.model.StopOver;
import com.example.tmd.hi_20172.model.Tree;
import com.example.tmd.hi_20172.model.Water;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.tmd.hi_20172.activity.map.DataParser.routesInfo;
import static com.example.tmd.hi_20172.activity.map.GetDirectionsData.listRoutesPolylines;
import static com.example.tmd.hi_20172.activity.map.GetDirectionsData.alternativePattern;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnPolylineClickListener, NavigationView.OnNavigationItemSelectedListener, GoogleMap.OnInfoWindowClickListener {

    private static final String MARKER_TREE = "MARKER_TREE";
    private static final String MARKER_WATER = "MARKER_WATER";
    public static final String TREE = "TREE";
    public static final int REQUEST_TREE_DETAIL_ACT = 15;
    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    int PROXIMITY_RADIUS = 10000;
    double latitude, longitude;
    double end_latitude, end_longitude;

    // TODO: 06/04/2018
    Map<String, StopOver> stopovers = new HashMap();
    Map<String, Marker> markers = new HashMap<>();
    List<StopOver> tour = new ArrayList<>();
    List<String> keywords = new ArrayList<>();
    private Button btnCalculate, btnCancel;
    private TextView txtRouteInfo, txtNumberStopover, txtQuickChoose;
    private FloatingActionButton fab;
    private AutoCompleteTextView autocomplete;
    private List<String> sourceAutoComplete;
    private BottomSheetBehavior<View> bottomSheetBehavior;
    private View bottomSheet;
    private RecyclerView recyclerView, recyclerViewHashTag;
    private RouteAdapter routeAdapter;
    private HashTagsAdapter hashTagsAdapter;
    private boolean IS_SPRAYING = false;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;

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
        // TODO: 07/04/2018
        txtRouteInfo = findViewById(R.id.text_view_route_info);
        txtNumberStopover = findViewById(R.id.text_view_number_stopover);
        btnCancel = findViewById(R.id.button_cancel);
        btnCalculate = findViewById(R.id.button_calculate);
        txtQuickChoose = findViewById(R.id.text_view_quick_choose);
        fab = findViewById(R.id.fab);
        createBottomSheet();
        createNavigationView();
        setUpRecyclerView();
        setUpRecyclerViewHashTag();
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
        mMap.setOnInfoWindowClickListener(this);

        // TODO: 06/04/2018
//        fakePosition();
        addTrees();

    }

    // TODO: 06/04/2018
    private void fakePosition() {
        // fake position in Bach Khoa
        latitude = 21.005019;
        longitude = 105.843644;
        LatLng latLng = new LatLng(latitude, longitude);// Bach Khoa
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title("Current Position")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_arrow))
                .rotation(45)
                .anchor(0.5f, 0.5f);
        mCurrLocationMarker = mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17));
    }

    // TODO: 06/04/2018
    private void addTrees() {
        // info window
        CustomInfoWindowGoogleMap customInfoWindow = new CustomInfoWindowGoogleMap(this);
        mMap.setInfoWindowAdapter(customInfoWindow);
        // tree
        List<Tree> tempTree = new ArrayList<>();
        tempTree.add(new Tree(new LatLng(21.004730, 105.844619), R.mipmap.ic_01_do, "Rau muống", Tree.RED, R.drawable.rau_muong, "Tưới đều xung quanh gốc, chú ý không tưới quá mạnh tay."));
        tempTree.add(new Tree(new LatLng(21.004433, 105.846986), R.mipmap.ic_02_do, "Hoa hồng", Tree.RED, R.drawable.hoa_hong, "Tưới bằng bình chuyên dụng, có vòi hoa sen xung quanh gốc, trnahs tưới vào lá làm nát lá."));
        tempTree.add(new Tree(new LatLng(21.007478, 105.847441), R.mipmap.ic_03_x, "Cải cúc", Tree.GREEN, R.drawable.cai_cuc, "Tưới nhẹ tay từ trên xuống, để nước bao trùm quanh cây"));
        tempTree.add(new Tree(new LatLng(21.006916, 105.842095), R.mipmap.ic_04_ca, "Cây bàng", Tree.YELLOW, R.drawable.caybang, "Tưới nhiều vào gốc cây, cần tưới cxung quanh gốc, tưới vào buổi sáng sáng sớm là tốt nhất."));
        tempTree.add(new Tree(new LatLng(21.007312, 105.843096), R.mipmap.ic_05_x, "Hoa dâm bụt", Tree.GREEN, R.drawable.hoa_dam_but, "Tưới đều xung quanh gốc, chú ý không tưới quá mạnh tay"));
        tempTree.add(new Tree(new LatLng(21.004701, 105.842026), R.mipmap.ic_06_do, "Cây phượng", Tree.RED, R.drawable.cay_phuong, "Tưới nhiều vào gốc cây, cần tưới xung quanh gốc, tưới vào buổi sáng sáng sớm là tốt nhất."));
        tempTree.add(new Tree(new LatLng(21.003840, 105.841967), R.mipmap.ic_07_do, "Cây đa", Tree.RED, R.drawable.cay_da, "Tưới nhiều vào gốc cây, cần tưới xung quanh gốc, tưới vào buổi sáng sáng sớm là tốt nhất."));
        tempTree.add(new Tree(new LatLng(21.003700, 105.843545), R.mipmap.ic_08_x, "Cây bưởi", Tree.GREEN, R.drawable.cay_buoi, "Tưới nhiều vào gốc cây, cần tưới xung quanh gốc, tưới vào buổi sáng sáng sớm là tốt nhất."));
        tempTree.add(new Tree(new LatLng(21.006808, 105.845926), R.mipmap.ic_09_do, "Hoa loa kèn", Tree.RED, R.drawable.hoa_loa_ken, "Tưới nhẹ nhàng, dùng bình tưới chuyên dụng, nhẹ tay tránh để nát hoa."));
        tempTree.add(new Tree(new LatLng(21.005130, 105.846101), R.mipmap.ic_11_ca, "Cây chanh", Tree.YELLOW, R.drawable.cay_chanh, "Tưới tập trung vào gốc cây và lá cây, tốt nhất là vào buổi sáng để cây hấp thụ ánh sáng mặt trời."));
        tempTree.add(new Tree(new LatLng(21.011250, 105.844249), R.mipmap.ic_11_do, "Cây gạo", Tree.RED, R.drawable.cay_gao, "Tưới nhiều vào gốc cây, cần tưới xung quanh gốc, tưới vào buổi sáng sáng sớm là tốt nhất."));
        tempTree.add(new Tree(new LatLng(21.005924, 105.846553), R.mipmap.ic_12_ca, "Cây gạo", Tree.RED, R.drawable.cay_gao, "Tưới nhiều vào gốc cây, cần tưới xung quanh gốc, tưới vào buổi sáng sáng sớm là tốt nhất."));
        tempTree.add(new Tree(new LatLng(21.006298, 105.845296), R.mipmap.ic_13_x, "Cây bàng", Tree.RED, R.drawable.caybang, "Tưới nhiều vào gốc cây, cần tưới xung quanh gốc, tưới vào buổi sáng sáng sớm là tốt nhất."));
        tempTree.add(new Tree(new LatLng(21.008228, 105.841807), R.mipmap.ic_14_do, "Cây phượng", Tree.RED, R.drawable.cay_phuong, "Tưới nhiều vào gốc cây, cần tưới xung quanh gốc, tưới vào buổi sáng sáng sớm là tốt nhất."));
        tempTree.add(new Tree(new LatLng(21.009360, 105.845306), R.mipmap.ic_15_x, "Hoa hồng", Tree.RED, R.drawable.hoa_hong, "Tưới bằng bình chuyên dụng, có vòi hoa sen xung quanh gốc, trnahs tưới vào lá làm nát lá."));
        tempTree.add(new Tree(new LatLng(21.009641, 105.841498), R.mipmap.ic_16_ca, "Hoa loa kèn", Tree.RED, R.drawable.hoa_loa_ken, "Tưới nhẹ nhàng, dùng bình tưới chuyên dụng, nhẹ tay tránh để nát hoa."));
        tempTree.add(new Tree(new LatLng(21.009468, 105.841728), R.mipmap.ic_17_x, "Cây đa", Tree.RED, R.drawable.cay_da, "Tưới nhiều vào gốc cây, cần tưới xung quanh gốc, tưới vào buổi sáng sáng sớm là tốt nhất."));
        tempTree.add(new Tree(new LatLng(21.010814, 105.842599), R.mipmap.ic_18_do, "Cây chanh", Tree.YELLOW, R.drawable.cay_chanh, "Tưới tập trung vào gốc cây và lá cây, tốt nhất là vào buổi sáng để cây hấp thụ ánh sáng mặt trời."));
        tempTree.add(new Tree(new LatLng(21.006508, 105.842535), R.mipmap.ic_19_ca, "Cải cúc", Tree.GREEN, R.drawable.cai_cuc, "Tưới nhẹ tay từ trên xuống, để nước bao trùm quanh cây"));
        for (Tree tree : tempTree) {
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(tree.getLatlon().latitude, tree.getLatlon().longitude))
                    .title(MARKER_TREE)
                    .icon(BitmapDescriptorFactory.fromResource(tree.getIcon())));
            tree.setId(marker.getId());
            stopovers.put(marker.getId(), tree);
            markers.put(marker.getId(), marker);
            // info window
            marker.setTag(tree);
        }
        // nguon nuoc
        List<Water> tempWater = new ArrayList<>();
        tempWater.add(new Water(new LatLng(21.006394, 105.843622), R.mipmap.ic_water, "Nguồn nước"));
        tempWater.add(new Water(new LatLng(21.006597, 105.845587), R.mipmap.ic_water, "Nguồn nước"));
        tempWater.add(new Water(new LatLng(21.003798, 105.844516), R.mipmap.ic_water, "Nguồn nước"));
        for (Water water : tempWater) {
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(water.getLatlon().latitude, water.getLatlon().longitude))
                    .title(MARKER_TREE)
                    .icon(BitmapDescriptorFactory.fromResource(water.getIcon())));
            water.setId(marker.getId());
            stopovers.put(marker.getId(), water);
            markers.put(marker.getId(), marker);
            // info window
            marker.setTag(water);
        }
    }

    private void setUpRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view_stopovers);
        routeAdapter = new RouteAdapter(this, tour);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(routeAdapter);
    }


    private void createNavigationView() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.left_drawer);
        mNavigationView.setNavigationItemSelectedListener(this);

        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Intent i;
                        switch (item.getItemId()) {
                            case R.id.nav_user_info:
                                i = new Intent(MapsActivity.this, UserInfoActivity.class);
                                startActivity(i);
                                break;
                            case R.id.nav_language:
                                i = new Intent(MapsActivity.this, LanguageActivity.class);
                                startActivity(i);
                                break;
                            case R.id.nav_history:
                                i = new Intent(MapsActivity.this, HistoryActivity.class);
                                startActivity(i);
                                break;
                        }
                        return true;
                    }
                }
        );
        //
        setUpAutoCompleteTextView();
    }

    private void createBottomSheet() {
        bottomSheet = findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
//                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        txtQuickChoose.setVisibility(View.GONE);
//                        disableButton(btnCalculate);
//                        disableButton(btnCancel);
                        if (!IS_SPRAYING) {
                            fab.setVisibility(View.VISIBLE);
                        }
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
//                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        fab.setVisibility(View.GONE);
//                        enableButton(btnCalculate);
//                        enableButton(btnCancel);
                        txtQuickChoose.setVisibility(View.GONE);
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        if (tour.isEmpty()) {
                            txtQuickChoose.setVisibility(View.VISIBLE);
                            fab.setVisibility(View.GONE);
                        } else {
                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        }
                        break;

                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    private void setUpRecyclerViewHashTag() {
        recyclerViewHashTag = findViewById(R.id.recycler_view_hashtag);
        hashTagsAdapter = new HashTagsAdapter(this, keywords);
        recyclerViewHashTag.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewHashTag.setAdapter(hashTagsAdapter);
    }


    private void setUpAutoCompleteTextView() {
        autocomplete = findViewById(R.id.auto_complete_text_view);
        // create source
        sourceAutoComplete = new ArrayList<>();
        for (StopOver stopOver : stopovers.values()) {
            if (stopOver instanceof Tree) {
                sourceAutoComplete.add(stopOver.getName());
            }
        }
        Log.e("MY_TAG", "setUpAutoCompleteTextView: " + sourceAutoComplete.size());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, sourceAutoComplete.toArray(new String[sourceAutoComplete.size()]));
        autocomplete.setAdapter(adapter);
        autocomplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                keywords.add((String) adapterView.getItemAtPosition(i));
                hashTagsAdapter.notifyDataSetChanged();
                autocomplete.setText("");

            }
        });
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    public void setSpraying(boolean boo) {
        IS_SPRAYING = boo;
        if (IS_SPRAYING) {
            btnCalculate.setVisibility(View.GONE);
            btnCancel.setVisibility(View.VISIBLE);
            txtRouteInfo.setVisibility(View.VISIBLE);
            txtNumberStopover.setBackgroundResource(R.drawable.bg_stopover_count_red);
        } else {
            btnCalculate.setVisibility(View.VISIBLE);
            btnCancel.setVisibility(View.GONE);
            txtRouteInfo.setVisibility(View.GONE);
            txtNumberStopover.setBackgroundResource(R.drawable.bg_stopover_count_green);
            for (List<Polyline> route : listRoutesPolylines) {
                for (Polyline polyline1 : route) {
                    polyline1.remove();
                }
            }
        }

    }

    public void onClick(View v) {
        Object dataTransfer[];
        switch (v.getId()) {
            // TODO: 06/04/2018
            case R.id.button_calculate:
                // xoa duong di truoc day
                for (List<Polyline> route : listRoutesPolylines) {
                    for (Polyline polyline : route) {
                        polyline.remove();
                    }
                }
                listRoutesPolylines.clear();
                dataTransfer = new Object[3];
                String url = getDirectionsUrl(tour);
                GetDirectionsData getDirectionsData = new GetDirectionsData();
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;
                dataTransfer[2] = new GetDirectionCallBack();
                getDirectionsData.execute(dataTransfer);
                setSpraying(true);
                fab.setVisibility(View.GONE);
                break;
            case R.id.button_cancel:
                setSpraying(false);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                break;
            case R.id.image_view_open_menu:
                hideKeyboard();
                mDrawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.text_view_quick_choose:
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                for (StopOver stopOver : stopovers.values()) {
                    if (stopOver instanceof Tree && ((Tree) stopOver).getStatus() != Tree.GREEN) {
                        handleStopoverClicked(stopOver);
                    }
                }
                break;
            case R.id.text_view_clear:
                tour.clear();
                routeAdapter.updateList();
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                if (IS_SPRAYING) {
                    setSpraying(false);
                }
                for (Marker marker : markers.values()) {
                    marker.setIcon(BitmapDescriptorFactory.fromResource(stopovers.get(marker.getId()).getIcon()));
                }
                break;
            case R.id.text_view_number_stopover:
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
            case R.id.fab:
                btnCalculate.performClick();
                break;
        }
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.create_new_group:
//                onClickCreateNewGroup();
//                break;
            default:
                break;
        }
        mDrawerLayout.closeDrawer(Gravity.START);
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        if (marker.getTag() instanceof Tree) {
            Intent intent = new Intent(this, TreeDetail.class);
            intent.putExtra(TREE, (Tree) marker.getTag());
            startActivityForResult(intent, REQUEST_TREE_DETAIL_ACT);
            marker.hideInfoWindow();
        }
    }

    public class GetDirectionCallBack {
        public boolean success() {
            setSpraying(true);
            updateRouteInfo();
            return true;
        }

        public void updateRouteInfo() {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            String info = "Khoảng cách ~ " + routesInfo.get(0).get("distance") +
                    "\nThời gian đi ~ " + routesInfo.get(0).get("duration");
            txtRouteInfo.setText(info);
        }
    }

    // TODO: 06/04/2018
    private String getDirectionsUrl(List<StopOver> route) {
        getWater(route);
        StringBuilder googleDirectionsUrl = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?");
        googleDirectionsUrl.append("origin=" + latitude + "," + longitude);
        googleDirectionsUrl.append("&destination=" + route.get(route.size() - 1).getLatlon().latitude + "," + route.get(route.size() - 1).getLatlon().longitude);
        googleDirectionsUrl.append("&alternatives=true");
        googleDirectionsUrl.append("&mode=walking");
        if (route.size() > 1) {
            googleDirectionsUrl.append("&waypoints=optimize:true");
            for (StopOver stopOver : route) {
                googleDirectionsUrl.append("|via:" + stopOver.getLatlon().latitude + "," + stopOver.getLatlon().longitude);
            }
        }
        googleDirectionsUrl.append("&key=" + getResources().getString(R.string.google_maps_key));
        Log.e("MY_TAG", "getDirectionsUrl: " + googleDirectionsUrl.toString());
        return googleDirectionsUrl.toString();
    }

    private void getWater(List<StopOver> route) {
        // stopover dau tien phai la nguon nuoc
        if (!(route.get(0) instanceof Water)) {
            for (int i = 1; i < route.size(); i++) {
                if (route.get(i) instanceof Water) {
                    route.add(0, route.get(i));
                    route.get(i).setChoose(!route.get(i).isChoose());
                    markers.get(route.get(i).getId()).setIcon(
                            BitmapDescriptorFactory.fromResource(R.mipmap.ic_checked));
                    routeAdapter.updateList();
                    return;
                }
            }
            // neu trong list khong co water
            for (StopOver stopOver : stopovers.values()) {
                if (stopOver instanceof Water) {
                    route.add(0, stopOver);
                    stopOver.setChoose(!stopOver.isChoose());
                    markers.get(stopOver.getId()).setIcon(
                            BitmapDescriptorFactory.fromResource(R.mipmap.ic_checked));
                    routeAdapter.updateList();
                    return;
                }
            }
        }
    }

    private String getDirectionsUrl() {
        StringBuilder googleDirectionsUrl = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?");
        googleDirectionsUrl.append("origin=" + latitude + "," + longitude);
        googleDirectionsUrl.append("&destination=" + end_latitude + "," + end_longitude);
        googleDirectionsUrl.append("&key=" + getResources().getString(R.string.google_maps_key));

        return googleDirectionsUrl.toString();
    }

    private String getUrl(double latitude, double longitude, String nearbyPlace) {
        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=" + latitude + "," + longitude);
        googlePlacesUrl.append("&radius=" + PROXIMITY_RADIUS);
        googlePlacesUrl.append("&type=" + nearbyPlace);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + getResources().getString(R.string.google_maps_key));
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
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        latitude = location.getLatitude();
        longitude = location.getLongitude();


        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.draggable(true);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_arrow));
        markerOptions.anchor(0.5f, 0.5f);
        if (location.hasBearing()) {
            markerOptions.rotation(location.getBearing());
        } else {
            markerOptions.rotation(45);
        }
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17));
        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            Log.d("onLocationChanged", "Removing Location Updates");
        }
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

    public TextView getTxtNumberStopover() {
        return txtNumberStopover;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.getTitle() != null && marker.getTitle().equals(MARKER_TREE) || marker.getTitle().equals(MARKER_WATER)) {
            StopOver stopOver = stopovers.get(marker.getId());
            handleStopoverClicked(stopOver);
            marker.showInfoWindow();
        }
        return true;
    }

    private void handleStopoverClicked(StopOver stopOver) {
        if (stopOver != null) {
            stopOver.setChoose(!stopOver.isChoose());
            updateTour(stopOver);
        }
    }

    // TODO: 06/04/2018
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TREE_DETAIL_ACT) {
            if (resultCode == Activity.RESULT_OK) {
                Tree tree = data.getParcelableExtra("result");
                updateTour(tree);
            }
        }
    }

    public void updateTour(StopOver stopOver) {
//        stopovers.put(tree.getId(), tree);
//                Toast.makeText(this, "" + tree.isChoose(), Toast.LENGTH_SHORT).show();
        Marker marker = markers.get(stopOver.getId());
        if (marker != null) {
            if (stopOver.isChoose()) {
                marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_checked));
                tour.add(stopOver);
            } else {
                marker.setIcon(BitmapDescriptorFactory.fromResource(stopOver.getIcon()));
                tour.remove(stopOver);
            }
        }
        checkTour();
    }

    private void checkTour() {
        // neu Tour != null thi hien nut de bat dau tuoi
        if (tour.isEmpty()) {
            setSpraying(false);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        } else {
            if (IS_SPRAYING) {
                btnCalculate.performClick();
            } else if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        }
        routeAdapter.updateList();
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
                    // update tour info
                    String info = "Khoảng cách ~ " + routesInfo.get(0).get("distance") +
                            "\nThời gian đi ~ " + routesInfo.get(0).get("duration");
                    txtRouteInfo.setText(info);
                    return;
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}


