package com.lusberc.billwallet.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.storage.StorageReference;
import com.lusberc.billwallet.MainActivity;
import com.lusberc.billwallet.Maps.MapsActivityCurrentPlace;
import com.lusberc.billwallet.Models.Bill;
import com.lusberc.billwallet.Models.MyApplication;
import com.lusberc.billwallet.R;
import com.lusberc.billwallet.Utilities.GeneralValidations;
import com.lusberc.billwallet.Utilities.ProgressBarCustom;

import static android.widget.Toast.LENGTH_LONG;
import static com.lusberc.billwallet.Utilities.GeneralValidations.extractBillDate;

public class Fragment2 extends Fragment {

    final private String TAG = "Fragment 1";
    private static final String DESCRIBABLE_KEY = "bill_key";
    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    private FirebaseFirestore db;
    private FirebaseUser currentUser;
    public boolean hasStored = false;
    ProgressBarCustom mBar;

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

    private void addBillToFirebase(){
        hasStored = true;
        db.collection("Facturas")
                .document(MyApplication._mBill.getUserID())
                .collection("Facturas")
                .document(MyApplication._mBill.getImageName())
                .set(MyApplication._mBill)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void documentReference) {
                        Log.d(TAG, "Image Text added for user "+ currentUser.getDisplayName());
                        Snackbar mySnackbar = Snackbar.make(getView(),
                                "Image text added with ID:" + documentReference.toString() ,
                                Snackbar.LENGTH_SHORT);
                        //mySnackbar.setAction(R.string.undo_string, new MyUndoListener());
                        mySnackbar.show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding bill", e);
                        Snackbar mySnackbar = Snackbar.make(getView(),
                                "Error adding bill", Snackbar.LENGTH_SHORT);
                        //mySnackbar.setAction(R.string.undo_string, new MyUndoListener());
                        mySnackbar.show();
                    }
                });
    }

    public void showToastMsj(String message){
        Toast.makeText(getContext(), message,
                LENGTH_LONG).show();
    }

    private void setupUI(View view) {
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        final MainActivity main = (MainActivity) getActivity();
        final EditText fecha = view.findViewById(R.id.txtFechaCompra);
        final EditText monto = view.findViewById(R.id.txtMontoTotal);
        final EditText comercio = view.findViewById(R.id.txtComercio);

        String fechaFactura = MyApplication._mBill.getFechaVencimiento();
        if(fecha!= null && !fechaFactura.isEmpty()) fecha.setText(fechaFactura);
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .build();
        db.setFirestoreSettings(settings);



        Button btnUbicacion = view.findViewById(R.id.btnUbicacion);
        btnUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MapsActivityCurrentPlace macp = new MapsActivityCurrentPlace();
                Intent intent = new Intent(main, MapsActivityCurrentPlace.class);
                //intent.putExtra("RESULT_VALUE", resultado.toString());
                main.startActivity(intent);
            }
        });

        Button btnGuardar = view.findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!hasStored){
                    if(GeneralValidations.validateUserBillFields(v,monto,comercio,fecha)){
                        mBar.showBar(v);
                        addBillToFirebase();
                        String content = "";
                        Fragment2Directions.Fragment2to3 action =
                                Fragment2Directions.fragment2to3();
                        action.setMessage(content);
                        Navigation.findNavController(v).navigate(action);
                        mBar.closeBar();
                    }
                }else{
                    showToastMsj("Ya se ha guardado la informacion");
                }
            }
        });

        FloatingActionButton btn_next = view.findViewById(R.id.btn_fgmt2_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!hasStored){
                    if(GeneralValidations.validateUserBillFields(v,monto,comercio,fecha)){
                        mBar.showBar(v);
                        addBillToFirebase();
                        String content = "";
                        Fragment2Directions.Fragment2to3 action =
                                Fragment2Directions.fragment2to3();
                        action.setMessage(content);
                        Navigation.findNavController(v).navigate(action);
                        mBar.closeBar();
                    }
                }
            }
        });
    }


}
