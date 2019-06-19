package com.example.taller_7_navigationdrawer.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.navigation.Navigation;

import com.example.taller_7_navigationdrawer.R;

public class Fragment2 extends Fragment {

    final private String TAG = "Fragment 1";

    public Fragment2() {
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
        View view =  inflater.inflate(R.layout.fragment_fragment2, container, false);
        setupUI(view);
        return view;
    }

    private void setupUI(View view) {
        FloatingActionButton btn_next = (FloatingActionButton) view.findViewById(R.id.btn_fgmt2_next);
        final TextInputEditText editText = (TextInputEditText) view.findViewById(R.id.txt_message);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"Button Fragment_2 clicked!!");
                String content = editText.getText().toString();
                Fragment2Directions.Fragment2to3 action =
                    Fragment2Directions.fragment2to3();
                action.setMessage(content);
                Navigation.findNavController(v).navigate(action);
            }
        });
    }

}
