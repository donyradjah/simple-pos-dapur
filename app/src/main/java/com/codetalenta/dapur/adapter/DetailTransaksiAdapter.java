package com.codetalenta.dapur.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codetalenta.dapur.R;
import com.codetalenta.dapur.model.DetailTransaksi;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class DetailTransaksiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_KATEGORI = 0;
    public static final int TYPE_Transaksi = 1;

    private ArrayList<DetailTransaksi> transaksis;
    private Context context;
    private Activity activity;
    public OnClickButtonListener buttonListener;

    public DetailTransaksiAdapter(ArrayList<DetailTransaksi> transaksis, Context context, Activity activity, OnClickButtonListener buttonListener) {
        this.transaksis = transaksis;
        this.context = context;
        this.activity = activity;
        this.buttonListener = buttonListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Here Inflating your header view
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_transaksi, parent, false);
        return new TransaksiViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        final TransaksiViewHolder TransaksiViewHolder = (TransaksiViewHolder) holder;
        DetailTransaksi transaksi = (DetailTransaksi) transaksis.get(position);

        TransaksiViewHolder.txtNamaProduk.setText(transaksi.getNamaProduk());
        TransaksiViewHolder.txtHargaProduk.setText(formatRupiah((double) transaksi.getHarga()));
        TransaksiViewHolder.txtQtyProduk.setText(transaksi.getJumlah() + " x");
        TransaksiViewHolder.txtTotalProduk.setText(formatRupiah((double) (transaksi.getJumlah() * transaksi.getHarga())));
    }

    @Override
    public int getItemCount() {
        return transaksis.size();
    }


    public class TransaksiViewHolder extends RecyclerView.ViewHolder {
        TextView txtNamaProduk, txtHargaProduk, txtQtyProduk, txtTotalProduk;

        public TransaksiViewHolder(View view) {
            super(view);

            txtNamaProduk = view.findViewById(R.id.txtNamaProduk);
            txtHargaProduk = view.findViewById(R.id.txtHargaProduk);
            txtQtyProduk = view.findViewById(R.id.txtQtyProduk);
            txtTotalProduk = view.findViewById(R.id.txtTotalProduk);

        }
    }

    private String formatRupiah(Double number) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }

    public interface OnClickButtonListener {

    }

}