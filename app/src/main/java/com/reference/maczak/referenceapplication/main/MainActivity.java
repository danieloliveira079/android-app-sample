package com.reference.maczak.referenceapplication.main;

import android.content.Context;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.reference.maczak.referenceapplication.R;
import com.reference.maczak.referenceapplication.communication.IApiMethods;
import com.reference.maczak.referenceapplication.main.cluster.UserClusterItem;
import com.reference.maczak.referenceapplication.main.cluster.UserClusterRenderer;
import com.reference.maczak.referenceapplication.user.User;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback, ClusterManager.OnClusterItemClickListener<UserClusterItem>, ClusterManager.OnClusterClickListener<UserClusterItem>{

    private static final String API_URL = "http://jsonplaceholder.typicode.com";

    private GoogleMap mMap;
    private ClusterManager<UserClusterItem> userClusterManager;
    private com.sothree.slidinguppanel.SlidingUpPanelLayout slidingUpPanelLayout;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        slidingUpPanelLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        hideBottomPanel();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

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
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                hideBottomPanel();
            }
        });

        userClusterManager = new ClusterManager<>(this, mMap);
        userClusterManager.setRenderer(new UserClusterRenderer(this, mMap, userClusterManager, getLayoutInflater().inflate(R.layout.multi_profile, slidingUpPanelLayout, false)));

        mMap.setOnMarkerClickListener(userClusterManager);
        mMap.setOnCameraChangeListener(userClusterManager);

        userClusterManager.setOnClusterItemClickListener(this);
        userClusterManager.setOnClusterClickListener(this);

        downloadUserList();
    }

    private void downloadUserList() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IApiMethods methods = retrofit.create(IApiMethods.class);

        Call<ArrayList<User>> call = methods.getUsers();
        showLoadingMark();
        call.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                hideLoadingMark();
                if (response != null && response.body() != null && response.body().size() > 0) {
                    showUserMarkers(response.body());
                } else {
                    showErrorMessage();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                hideLoadingMark();
                showErrorMessage();
            }
        });

    }

    private void showErrorMessage(){
        Snackbar snackbar;
        snackbar = Snackbar.make(slidingUpPanelLayout, getText(R.string.something_went_wrong), Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(getColor(this, R.color.white));
        snackbar.show();
    }

    private void hideBottomPanel(){
        if(slidingUpPanelLayout != null){
            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        }
    }

    private void collapseBottomPanel(){
        if(slidingUpPanelLayout != null && slidingUpPanelLayout.getPanelState() != SlidingUpPanelLayout.PanelState.HIDDEN){
            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        }
    }

    private void showCollapsedBottomPanel(){
        if(slidingUpPanelLayout != null && slidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.HIDDEN){
            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        }
    }

    private void showUserMarkers(ArrayList<User> users){
        for (User user : users){
            UserClusterItem userClusterItem = new UserClusterItem(user);
            userClusterManager.addItem(userClusterItem);
        }
        userClusterManager.cluster();
    }

    @Override
    public boolean onClusterItemClick(UserClusterItem userClusterItem) {
        fillUserInfos(userClusterItem.getUser());
        showCollapsedBottomPanel();
        return true;
    }

    @Override
    public boolean onClusterClick(Cluster<UserClusterItem> cluster) {
        collapseBottomPanel();
        LatLngBounds.Builder builder = LatLngBounds.builder();
        for (ClusterItem item : cluster.getItems()) {
            builder.include(item.getPosition());
        }
        final LatLngBounds bounds = builder.build();
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
        return true;
    }

    @Override
    public void onBackPressed() {
        if(slidingUpPanelLayout.getPanelState() != SlidingUpPanelLayout.PanelState.HIDDEN){
            hideBottomPanel();
        } else {
            super.onBackPressed();
        }
    }

    private void fillUserInfos(User user){
        ((TextView) slidingUpPanelLayout.findViewById(R.id.user_name)).setText(user.name);
        ((TextView) slidingUpPanelLayout.findViewById(R.id.user_email)).setText(user.email);
        ((TextView) slidingUpPanelLayout.findViewById(R.id.user_city)).setText(user.address.city);
        ((TextView) slidingUpPanelLayout.findViewById(R.id.user_username)).setText(user.userName);
        ((TextView) slidingUpPanelLayout.findViewById(R.id.user_phone)).setText(user.phone);
        ((TextView) slidingUpPanelLayout.findViewById(R.id.user_website)).setText(user.website);
    }

    private void showLoadingMark() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.show();
    }

    private void hideLoadingMark() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    private int getColor(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            return getColor(context, id);
        } else {
            return context.getResources().getColor(id);
        }
    }

}
