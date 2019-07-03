package com.lusberc.billwallet.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.lusberc.billwallet.Adapters.BillsAdapterView;
import com.lusberc.billwallet.Models.Bill;
import com.lusberc.billwallet.R;

import java.util.ArrayList;

public class FragmentList extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseUser currentUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_list, container, false);
        setupUI(view);
        return view;
    }

    private void setupUI (final View view) {

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();


        //Llenar adapter con lista
        final int layoutId = R.layout.bills_list_view;

        //Llamar BD lista de facturas
        final ArrayList<Bill> mList = new ArrayList<>();

        db.collection("Facturas")
                .document(currentUser.getUid())
                .collection("Facturas")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("FRAGMENTLIST", document.getId() + " => " + document.getData());
                        Bill b = document.toObject(Bill.class);
                        b.setImageName(document.getId());
                        mList.add(b);
                    }
                    BillsAdapterView adapter = new BillsAdapterView(view.getContext() ,layoutId, mList);

                    ListView mListView = view.findViewById(R.id.listViewBills);
                    mListView.setAdapter(adapter);
                } else {
                    Log.d("FRAGMENTLIST", "Error getting documents: ", task.getException());
                }
            }
        });

        BillsAdapterView adapter = new BillsAdapterView(view.getContext() ,layoutId, mList);
        ListView mListView = view.findViewById(R.id.listViewBills);
        mListView.setAdapter(adapter);
    }
}
