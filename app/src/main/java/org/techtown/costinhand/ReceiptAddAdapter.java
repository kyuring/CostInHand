package org.techtown.costinhand;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class ReceiptAddData {
    public ReceiptAddData(){}
}

public class ReceiptAddAdapter extends BaseAdapter {

    LayoutInflater layoutInflater = null;
    ArrayList<ReceiptAddData> receiptAddData;
    Context context;

    public ReceiptAddAdapter(Context context, ArrayList<ReceiptAddData> receiptAddDataArrayList) {
        this.context = context;
        receiptAddData = receiptAddDataArrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return receiptAddData.size();
    }

    @Override
    public Object getItem(int position) {
        return receiptAddData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.item_receipt_add, null);

        TextView receipt_item_remove = (TextView) view.findViewById(R.id.receipt_item_remove);
        receipt_item_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                receiptAddData.remove(position);
                // DB delete 필요
                // adapter 리플래쉬
                notifyDataSetChanged();
            }
        });


        return view;
    }
}
