package com.example.taller_7_navigationdrawer.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.taller_7_navigationdrawer.R;

public class Fragment3 extends Fragment {

    final private String TAG= "Fragment 3";

    public Fragment3() {
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
        View view = inflater.inflate(R.layout.fragment_fragment3, container, false);
        setupUI(view);
        return view;
    }

    private void setupUI(View view) {
        TextView txtmessage = (TextView) view.findViewById(R.id.txt_showmsj);
        Fragment3Args args = Fragment3Args
                .fromBundle(getArguments());
        txtmessage.setText(args.getMessage());
    }

}
