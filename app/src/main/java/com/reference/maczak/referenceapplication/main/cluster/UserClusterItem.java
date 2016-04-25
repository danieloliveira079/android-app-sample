package com.reference.maczak.referenceapplication.main.cluster;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;
import com.reference.maczak.referenceapplication.user.User;

/**
 * Created by Bandi on 2016.04.06..
 */
public class UserClusterItem implements ClusterItem{

    private final LatLng position;
    private User user;

    public UserClusterItem(User user){
        this.position = new LatLng(user.address.location.lat, user.address.location.lng);
        this.user = user;
    }

    @Override
    public LatLng getPosition() {
        return position;
    }

    public User getUser() {
        return user;
    }
}
