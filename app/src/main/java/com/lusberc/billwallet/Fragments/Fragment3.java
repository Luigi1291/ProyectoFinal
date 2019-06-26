package com.lusberc.billwallet.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.lusberc.billwallet.R;

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
        TextView txtmessage = view.findViewById(R.id.txt_showmsj);
        Fragment3Args args = Fragment3Args
                .fromBundle(getArguments());
        txtmessage.setText(args.getMessage());
    }

}
