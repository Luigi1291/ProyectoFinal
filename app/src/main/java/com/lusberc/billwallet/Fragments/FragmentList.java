package com.lusberc.billwallet.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.lusberc.billwallet.Adapters.BillsAdapterView;
import com.lusberc.billwallet.Models.Bill;
import com.lusberc.billwallet.R;
import java.util.ArrayList;

public class FragmentList extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_list, container, false);
        setupUI(view);
        return view;
    }

    private void setupUI (View view) {
        //Llenar adapter con lista
        int layoutId = R.layout.bills_list_view;

        //Llamar BD lista de facturas
        ArrayList<Bill> mList = new ArrayList<>();
        
        BillsAdapterView adapter = new BillsAdapterView(view.getContext() ,layoutId, mList);

        ListView mListView = view.findViewById(R.id.listViewBills);
        mListView.setAdapter(adapter);
    }
}
