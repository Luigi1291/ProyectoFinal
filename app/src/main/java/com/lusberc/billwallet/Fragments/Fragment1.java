package com.lusberc.billwallet.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lusberc.billwallet.MainActivity;
import com.lusberc.billwallet.R;
import com.lusberc.billwallet.Utilities.ProgressBarCustom;

import static android.widget.Toast.LENGTH_LONG;

public class Fragment1 extends Fragment {

    //TODO: crear en esta vista campos para fecha, monto, descripcion
    // y demas funcionalidad para incluir ademas de la imagen

    final private String TAG = "Fragment 1";
    ProgressBarCustom mBar;

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

    public void showToastMsj(String message){
        Toast.makeText(getContext(), message,
                LENGTH_LONG).show();
    }


    private void setupUI(View view) {
        Button btnNuevaFoto = view.findViewById(R.id.btnNuevaFoto);
        Button btnSubirFoto = view.findViewById(R.id.btnUploadFoto);
        FloatingActionButton btn_next = view.findViewById(R.id.btn_fgmt1_next);
        final MainActivity main = (MainActivity) getActivity();
        btnNuevaFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBar.showBar(v);
                main.takePhoto(main.getCurrentFocus());
                mBar.closeBar();
            }
        });

        btnSubirFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBar.showBar(v);
                main.uploadPhoto(main.getCurrentFocus());
                mBar.closeBar();
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (main.hasUploadedImage) {
                    Navigation.findNavController(v)
                            .navigate(R.id.fragment1to2);
                } else {
                    showToastMsj("Por favor suba una imagen y confirme que es la que desea procesar");
                }

            }
        });

    }


}
