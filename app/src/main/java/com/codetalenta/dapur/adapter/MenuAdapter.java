package com.codetalenta.dapur.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codetalenta.dapur.R;
import com.codetalenta.dapur.helper.BaseApiService;
import com.codetalenta.dapur.helper.UrlApi;
import com.codetalenta.dapur.model.ItemMenu;
import com.codetalenta.dapur.model.Kategori;
import com.codetalenta.dapur.model.Produk;
import com.kinda.alert.KAlertDialog;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_KATEGORI = 0;
    public static final int TYPE_PRODUK = 1;

    private ArrayList<ItemMenu> menus;
    private Context context;
    private Activity activity;

    private KAlertDialog pDialog;
    private BaseApiService apiService;

    public MenuAdapter(ArrayList<ItemMenu> menus, Context context, Activity activity) {
        this.menus = menus;
        this.context = context;
        this.activity = activity;

        this.pDialog = new KAlertDialog(activity, KAlertDialog.PROGRESS_TYPE);
        this.pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        this.pDialog.setTitleText("Sedang memproses ..");
        this.pDialog.setCancelable(false);

        this.apiService = UrlApi.getAPIService();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_KATEGORI) {
            // Here Inflating your recyclerview item layout
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
            return new KategoriViewHolder(itemView);
        } else if (viewType == TYPE_PRODUK) {
            // Here Inflating your header view
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produk, parent, false);
            return new ProdukViewHolder(itemView);
        } else return null;
    }

    void gantiStatus(int id, String status) {
        if (!pDialog.isShowing()) {
            pDialog.show();
        }

        apiService.gantiStatus(id, status).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    if (response.isSuccessful()) {
                        try {
                            String data = response.body().string();
                            Log.d("LOGIN", data);
                            JSONObject result = new JSONObject(data);

                            Log.d("LOGIN", result.getString("success"));
                            if (!result.getBoolean("success")) {
                                pDialog.dismiss();

                            } else {
                                pDialog.dismiss();

                            }

                        } catch (JSONException | IOException | NullPointerException e) {
                            e.printStackTrace();
                            pDialog.dismiss();
                        }
                    } else {
                        pDialog.dismiss();

                    }
                } else {
                    pDialog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                pDialog.dismiss();
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        if (holder instanceof KategoriViewHolder) {
            KategoriViewHolder headerViewHolder = (KategoriViewHolder) holder;
            Kategori kategori = (Kategori) menus.get(position);

            headerViewHolder.txtMenu.setText(kategori.getKategori());
        } else if (holder instanceof ProdukViewHolder) {
            final ProdukViewHolder produkViewHolder = (ProdukViewHolder) holder;
            final Produk produk = (Produk) menus.get(position);
            String urlGambar = UrlApi.BASE_URL_API + "upload/produk/" + produk.getGambarProduk();

            produkViewHolder.txtNamaProduk.setText(produk.getNamaProduk());


            if (produk.getStatus().equals("kosong")) {
                produkViewHolder.switchStatus.setChecked(false);
            } else {
                produkViewHolder.switchStatus.setChecked(true);

            }


            produkViewHolder.txtHargaProduk.setText(formatRupiah((double) produk.getHarga()));
            Picasso.with(context).load(urlGambar).error(R.drawable.produk).into(produkViewHolder.imgProduk);

            produkViewHolder.switchStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    boolean nilaiSekarang = produk.getStatus().equals("tersedia");
                    Log.d("sekarang", nilaiSekarang ? "sedia" : "kosong");
                    Log.d("baru", b ? "sedia" : "kosong");
                    if (b) {
                        gantiStatus(produk.getId(), "tersedia");
                    } else {
                        gantiStatus(produk.getId(), "kosong");
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return menus.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return menus.size();
    }

    public class KategoriViewHolder extends RecyclerView.ViewHolder {
        TextView txtMenu;

        public KategoriViewHolder(View view) {
            super(view);

            txtMenu = view.findViewById(R.id.txtMenu);
        }
    }

    public class ProdukViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduk;
        TextView txtNamaProduk, txtHargaProduk;
        Switch switchStatus;

        public ProdukViewHolder(View view) {
            super(view);

            imgProduk = view.findViewById(R.id.imgProduk);
            txtNamaProduk = view.findViewById(R.id.txtNamaProduk);
            txtHargaProduk = view.findViewById(R.id.txtHargaProduk);
            switchStatus = view.findViewById(R.id.switchStatus);

        }
    }

    private String formatRupiah(Double number) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }

}
