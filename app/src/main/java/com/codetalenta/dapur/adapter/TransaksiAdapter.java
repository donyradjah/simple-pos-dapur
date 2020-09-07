package com.codetalenta.dapur.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codetalenta.dapur.R;
import com.codetalenta.dapur.model.Transaksi;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class TransaksiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_KATEGORI = 0;
    public static final int TYPE_Transaksi = 1;

    private ArrayList<Transaksi> transaksis;
    private Context context;
    private Activity activity;
    public OnClickButtonListener buttonListener;

    public TransaksiAdapter(ArrayList<Transaksi> transaksis, Context context, Activity activity, OnClickButtonListener buttonListener) {
        this.transaksis = transaksis;
        this.context = context;
        this.activity = activity;
        this.buttonListener = buttonListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Here Inflating your header view
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaksi, parent, false);
        return new TransaksiViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        final TransaksiViewHolder TransaksiViewHolder = (TransaksiViewHolder) holder;
        Transaksi transaksi = (Transaksi) transaksis.get(position);

        TransaksiViewHolder.namaPemesan.setText(transaksi.getNamaPemesan());
        TransaksiViewHolder.totalBeli.setText(formatRupiah((double) transaksi.getTotalPembelian()));
        TransaksiViewHolder.waktuPesan.setText(transaksi.getWaktuPesan());

        switch (transaksi.getStatus()) {
            case "proses":
                TransaksiViewHolder.btnStatus.setBackgroundColor(activity.getResources().getColor(R.color.proses));
                TransaksiViewHolder.btnStatus.setText("Proses");
                break;
            case "selesai":
                TransaksiViewHolder.btnStatus.setBackgroundColor(activity.getResources().getColor(R.color.selesai));
                TransaksiViewHolder.btnStatus.setText("Selesai");
                break;
            case "batal":
                TransaksiViewHolder.btnStatus.setBackgroundColor(activity.getResources().getColor(R.color.batal));
                TransaksiViewHolder.btnStatus.setText("Batal");
                break;
            case "belum-bayar":
            default:
                TransaksiViewHolder.btnStatus.setBackgroundColor(activity.getResources().getColor(R.color.belum_bayar));
                TransaksiViewHolder.btnStatus.setText("Belum Bayar");
                break;
        }


    }


    @Override
    public int getItemCount() {
        return transaksis.size();
    }


    public class TransaksiViewHolder extends RecyclerView.ViewHolder {
        TextView namaPemesan, waktuPesan, totalBeli;
        Button btnStatus;


        public TransaksiViewHolder(View view) {
            super(view);

            namaPemesan = view.findViewById(R.id.namaPemesan);
            waktuPesan = view.findViewById(R.id.waktuPesan);
            totalBeli = view.findViewById(R.id.totalBeli);
            btnStatus = view.findViewById(R.id.btnStatus);

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
