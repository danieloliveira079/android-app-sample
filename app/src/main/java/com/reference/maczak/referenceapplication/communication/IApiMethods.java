package com.reference.maczak.referenceapplication.communication;

import com.reference.maczak.referenceapplication.user.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Bandi on 2016.04.06..
 */
public interface IApiMethods {

    @GET("/users")
    Call<ArrayList<User>> getUsers();

}
