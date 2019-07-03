package com.lusberc.billwallet.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lusberc.billwallet.Models.Bill;
import com.lusberc.billwallet.R;

import java.util.ArrayList;
import java.util.List;

public class BillsAdapterView extends ArrayAdapter<Bill> {

    int mLayoutId;
    private List<Bill> list = new ArrayList<Bill>();
    private Context _context;
    private FirebaseFirestore db;
    private static final String TAG = "Bill Adapter::";

    public BillsAdapterView(Context context, int layoutId, List<Bill> items) {
        super(context, layoutId, items);
        list = items;
        _context = context;
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

        deleteConfig(position, view);

        return view;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Bill getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public void remove(@Nullable Bill object) {

    }

    public void deleteConfig(final int position, View view){
        FloatingActionButton btnDelete = view.findViewById(R.id.btnDeleteBill);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(db == null) db = FirebaseFirestore.getInstance();
                Bill bill = getItem(position);
                list.remove(bill);
                db.collection("Facturas")
                        .document(bill.getUserID())
                        .collection("Facturas")
                        .document(bill.getImageName())
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                notifyDataSetChanged();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error deleting document", e);
                                notifyDataSetInvalidated();
                            }
                        });
            }
        });
    }
}