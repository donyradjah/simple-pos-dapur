package com.codetalenta.dapur;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codetalenta.dapur.adapter.TransaksiAdapter;
import com.codetalenta.dapur.helper.BaseApiService;
import com.codetalenta.dapur.helper.ClickListener;
import com.codetalenta.dapur.helper.RecyclerTouchListener;
import com.codetalenta.dapur.helper.Session;
import com.codetalenta.dapur.helper.UrlApi;
import com.codetalenta.dapur.model.Transaksi;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kinda.alert.KAlertDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static android.content.ContentValues.TAG;

public class DaftarTransaksiActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    BaseApiService apiService;
    KAlertDialog pDialog;
    Session session;
    TransaksiAdapter transaksiAdapter;

    RecyclerView rvListTransaksi;
    ImageView btnMenu;
    TextView txtMenu;

    ArrayList<Transaksi> transaksis = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_transaksi);

        session = new Session(this);
        myRef = database.getReference();
        apiService = UrlApi.getAPIService();
        transaksiAdapter = new TransaksiAdapter(transaksis, getApplicationContext(), DaftarTransaksiActivity.this, new TransaksiAdapter.OnClickButtonListener() {
        });

        pDialog = new KAlertDialog(this, KAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Sedang Mengambil Data ...");
        pDialog.setCancelable(false);


        btnMenu = findViewById(R.id.btnMenu);
        txtMenu = findViewById(R.id.txtMenu);
        rvListTransaksi = findViewById(R.id.listTransaksi);

        btnMenu.setImageResource(R.drawable.ic_arrow_back);
        txtMenu.setText("Daftar Transaksi");

        rvListTransaksi.setLayoutManager(new LinearLayoutManager(this));
        rvListTransaksi.setHasFixedSize(true);
        rvListTransaksi.setItemAnimator(new DefaultItemAnimator());
        rvListTransaksi.setAdapter(transaksiAdapter);

        rvListTransaksi.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvListTransaksi, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent detail = new Intent(DaftarTransaksiActivity.this, DetailTransaksiActivity.class);
                detail.putExtra("idTransaksi", transaksis.get(position).getIdTransaksi());

                startActivity(detail);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        init();
    }

    void init() {
        if (!pDialog.isShowing()) {
            pDialog = new KAlertDialog(DaftarTransaksiActivity.this, KAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Sedang Mengambil Data ...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        transaksis.clear();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                transaksis.clear();
                for (DataSnapshot data : dataSnapshot.child("transaksi").getChildren()) {

                    Transaksi transaksi = new Transaksi();

                    transaksi.setIdTransaksi(Integer.parseInt(data.getKey()));
                    transaksi.setPerangkatId(Integer.parseInt(data.child("perangkatId").getValue().toString()));
                    transaksi.setTotalPembelian(Integer.parseInt(data.child("totalPembelian").getValue().toString()));
                    transaksi.setTotalBayar(Integer.parseInt(data.child("totalBayar").getValue().toString()));
                    transaksi.setNamaPemesan(data.child("namaPemesan").getValue().toString());
                    transaksi.setWaktuPesan(data.child("waktuPesan").getValue().toString());
                    transaksi.setStatus(data.child("status").getValue().toString());
                    transaksi.setWaktuBayar(data.child("waktuBayar").getValue() == null ? "" : data.child("waktuBayar").getValue().toString());
                    transaksi.setWaktuSelesai(data.child("waktuSelesai").getValue() == null ? "" : data.child("waktuSelesai").getValue().toString());
                    transaksi.setNamaPerangkat(data.child("namaPerangkat").getValue().toString());
                    transaksi.setNomorMeja(data.child("nomorMeja").getValue().toString());

                    transaksis.add(transaksi);
                }

                Collections.sort(transaksis, new Comparator<Transaksi>() {
                    @Override
                    public int compare(Transaksi o1, Transaksi o2) {
                        return Integer.compare(o2.getIdTransaksi(), o1.getIdTransaksi());
                    }
                });

                transaksiAdapter.notifyDataSetChanged();
                pDialog.dismissWithAnimation();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
                pDialog.dismissWithAnimation();
            }
        });
    }
}