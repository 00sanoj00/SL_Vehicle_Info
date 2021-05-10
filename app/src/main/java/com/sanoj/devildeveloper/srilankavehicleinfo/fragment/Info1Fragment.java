package com.sanoj.devildeveloper.srilankavehicleinfo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.sanoj.devildeveloper.srilankavehicleinfo.R;

public class Info1Fragment extends Fragment {


    public Info1Fragment() {
    }
    public static com.sanoj.devildeveloper.srilankavehicleinfo.fragment.Info1Fragment newInstance() {
        com.sanoj.devildeveloper.srilankavehicleinfo.fragment.Info1Fragment fragment = new com.sanoj.devildeveloper.srilankavehicleinfo.fragment.Info1Fragment();
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
        return inflater.inflate(R.layout.fragment_info1, container, false);
    }
}
