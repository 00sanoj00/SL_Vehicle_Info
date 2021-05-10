package com.sanoj.devildeveloper.srilankavehicleinfo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.sanoj.devildeveloper.srilankavehicleinfo.R;

public class Info2Fragment extends Fragment {


    public Info2Fragment() {
    }


    public static com.sanoj.devildeveloper.srilankavehicleinfo.fragment.Info2Fragment newInstance() {
        com.sanoj.devildeveloper.srilankavehicleinfo.fragment.Info2Fragment fragment = new com.sanoj.devildeveloper.srilankavehicleinfo.fragment.Info2Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info2, container, false);
    }
}
