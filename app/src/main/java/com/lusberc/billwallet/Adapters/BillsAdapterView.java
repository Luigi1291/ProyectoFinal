package com.lusberc.billwallet.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lusberc.billwallet.Models.Bill;
import com.lusberc.billwallet.R;

import java.util.List;

public class BillsAdapterView extends ArrayAdapter<Bill> {

    int mLayoutId;
    public BillsAdapterView(Context context, int layoutId, List<Bill> items) {
        super(context, layoutId, items);
        mLayoutId = layoutId;
    }

    @Override
    public View getView(int position,View view, ViewGroup parent) {
        Bill bill = getItem(position);
        String store = bill.getStore();
        String billDate = bill.getFechaVencimiento();
        String other = String.valueOf(bill.getMonto());

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(mLayoutId, parent, false);
        }

        TextView storeView = view.findViewById(R.id.txtStore);
        TextView billDateView = view.findViewById(R.id.txtBillDate);
        TextView otherView = view.findViewById(R.id.txtOther);

        storeView.setText(store);
        billDateView.setText(billDate);
        otherView.setText(other);

        deleteConfig(view);

        return view;
    }

    public void deleteConfig(View view){
        FloatingActionButton btnDelete = view.findViewById(R.id.btnDeleteBill);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Llamar BD eliminar factura
            }
        });
    }
}