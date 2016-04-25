package com.reference.maczak.referenceapplication.main.cluster;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;
import com.reference.maczak.referenceapplication.R;

/**
 * Created by Bandi on 2016.04.05..
 */
public class UserClusterRenderer extends DefaultClusterRenderer<UserClusterItem> {
    private final IconGenerator mIconGenerator;
    private final IconGenerator mClusterIconGenerator;
    private final ImageView mImageView;
    private final int mDimension;

    public UserClusterRenderer(Context context, GoogleMap googleMap, ClusterManager<UserClusterItem> clusterManager, View layout) {
        super(context, googleMap, clusterManager);

        mIconGenerator = new IconGenerator(context);
        mClusterIconGenerator = new IconGenerator(context);
        View multiProfile = layout;
        mClusterIconGenerator.setContentView(multiProfile);
        mImageView = new ImageView(context);
        mDimension = (int) context.getResources().getDimension(R.dimen.custom_profile_image);
        mImageView.setLayoutParams(new ViewGroup.LayoutParams(mDimension, mDimension));
        int padding = 2;
        mImageView.setPadding(padding, padding, padding, padding);
        mIconGenerator.setContentView(mImageView);
    }

    @Override
    protected void onBeforeClusterItemRendered(UserClusterItem clusterItem, MarkerOptions markerOptions) {
        // Draw a single person.
        mImageView.setImageResource(R.drawable.ic_user);
        Bitmap icon = mIconGenerator.makeIcon();
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon)).title(clusterItem.getUser().name).snippet(clusterItem.getUser().address.city);


    }

    @Override
    protected void onBeforeClusterRendered(Cluster<UserClusterItem> cluster, MarkerOptions markerOptions) {
        // Draw multiple people.
        mImageView.setImageResource(R.drawable.ic_user_cluster);
        Bitmap icon = mIconGenerator.makeIcon();
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));


    }

    @Override
    protected boolean shouldRenderAsCluster(Cluster cluster) {
        // Always render clusters.
        return cluster.getSize() > 1;
    }
}
