package com.lusberc.billwallet.Fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.Navigation;

import com.lusberc.billwallet.R;

public class Fragment1 extends Fragment {

    final private String TAG = "Fragment 1";

    public Fragment1() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_fragment1, container, false);
        setupUI(view);
        return view;
    }

    private void setupUI(View view) {
        FloatingActionButton btn_next = view.findViewById(R.id.btn_fgmt1_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"Button Fragment_1 clicked!!");
                Navigation.findNavController(v)
                        .navigate(R.id.fragment1to2);
            }
        });
    }
}
