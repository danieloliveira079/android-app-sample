package com.reference.maczak.referenceapplication.user;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Bandi on 2016.04.06..
 */
public class User {

    public String name;
    @SerializedName("username")
    public String userName;
    public String email;
    public String phone;
    public String website;
    public Address address;

    public class Address {
        public String city;

        @SerializedName("geo")
        public  Location location;

        public class Location {
            public double lat;
            public double lng;
        }
    }


}
