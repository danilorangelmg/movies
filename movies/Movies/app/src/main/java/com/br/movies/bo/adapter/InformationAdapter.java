package com.br.movies.bo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.br.movies.R;
import com.br.movies.domain.Buy;

import java.util.List;

/**
 * Created by danilorangel on 04/06/17.
 */

public class InformationAdapter extends RecyclerView.Adapter<InformationAdapter.InformationViewHolder> {

    private List information;
    private boolean buy;

    public InformationAdapter(List information, boolean buy) {
        this.information = information;
        this.buy = buy;
    }

    @Override
    public InformationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (buy) {
            itemView= inflater.inflate(R.layout.list_category_item_horizontal, parent, false);
        } else {
            itemView= inflater.inflate(R.layout.list_category_item_horizontal, parent, false);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(InformationViewHolder holder, int position) {
        if (buy) {
            onBindBuy(holder, position);
        } else {
            onBindRent(holder, position);
        }
    }

    public void onBindBuy(InformationViewHolder holder, int position) {
        Buy buy = (Buy) information.get(position);
        holder.txtData.setText(buy.getFormattedDate());
        holder.txtValue.setText(buy.getPrice());
        String type = buy.isAtive() ? "Plano Adicional" : "Plano";
        holder.txtType.setText(type);
    }

    public void onBindRent(InformationViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return information.size();
    }

    public class InformationViewHolder extends RecyclerView.ViewHolder {

        //rent
        private NetworkImageView imgPoster;
        private TextView txtData;
        private TextView txtValue;
        private TextView txtType;

        public InformationViewHolder(View itemView, boolean buy) {
            super(itemView);
            if (buy) {
                bindBuy(itemView);
            } else {
                bindRent(itemView);
            }
        }

        public void bindBuy(View itemView) {
            txtData = (TextView) itemView.findViewById(R.id.txt_data_buy);
            txtValue = (TextView) itemView.findViewById(R.id.txt_value_buy);
            txtType = (TextView) itemView.findViewById(R.id.txt_type_buy);
        }

        public void bindRent(View itemView) {
            imgPoster = (NetworkImageView) itemView.findViewById(R.id.moviePoster);
        }

    }


}
