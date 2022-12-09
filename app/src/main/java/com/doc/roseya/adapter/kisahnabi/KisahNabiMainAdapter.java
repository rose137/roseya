package com.doc.roseya.adapter.kisahnabi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doc.roseya.R;
import com.doc.roseya.holder.MainHolder;
import com.doc.roseya.model.kisahnabi.KisahNabiModelMain;

import java.util.List;

/**
 * Created by ROSI on 24-11-2022.
 */

public class KisahNabiMainAdapter extends RecyclerView.Adapter<MainHolder> {

    public List<KisahNabiModelMain> modelMainList;
    private Context mContext;
    private KisahNabiMainAdapter.onSelectData onSelectData;

    public interface onSelectData {
        void onSelected(KisahNabiModelMain kisahNabiModelMain);
    }

    public KisahNabiMainAdapter(Context context, List<KisahNabiModelMain> android, KisahNabiMainAdapter.onSelectData onSelectData) {
        this.mContext = context;
        this.modelMainList = android;
        this.onSelectData = onSelectData;
    }

    @NonNull
    @Override
    public MainHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_main_kisahnabi, viewGroup, false);
        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(MainHolder viewHolder, int i) {

        final KisahNabiModelMain anjay = modelMainList.get(i);

        viewHolder.txtName.setText(anjay.getName());
        viewHolder.cvList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectData.onSelected(anjay);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelMainList.size();
    }

}
