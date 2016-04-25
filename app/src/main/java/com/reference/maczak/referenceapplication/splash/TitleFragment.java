package com.reference.maczak.referenceapplication.splash;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reference.maczak.referenceapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TitleFragment extends Fragment {


    public TitleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_title, container, false);
    }


}
