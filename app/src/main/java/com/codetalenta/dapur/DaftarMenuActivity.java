package com.codetalenta.dapur;

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

import com.codetalenta.dapur.adapter.MenuAdapter;
import com.codetalenta.dapur.helper.BaseApiService;
import com.codetalenta.dapur.helper.Session;
import com.codetalenta.dapur.helper.UrlApi;
import com.codetalenta.dapur.model.ItemMenu;
import com.codetalenta.dapur.model.Kategori;
import com.codetalenta.dapur.model.Produk;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kinda.alert.KAlertDialog;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class DaftarMenuActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    BaseApiService apiService;
    KAlertDialog pDialog;
    Session session;
    MenuAdapter menuAdapter;

    RecyclerView rvListMenu;
    ImageView btnMenu;
    TextView txtMenu;

    ArrayList<ItemMenu> menus = new ArrayList<>();
    ArrayList<Kategori> kategoris = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_menu);

        session = new Session(this);
        myRef = database.getReference();
        apiService = UrlApi.getAPIService();
        menuAdapter = new MenuAdapter(menus, getApplicationContext(), DaftarMenuActivity.this);

        pDialog = new KAlertDialog(this, KAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Sedang Mengambil Data ...");
        pDialog.setCancelable(false);

        btnMenu = findViewById(R.id.btnMenu);
        txtMenu = findViewById(R.id.txtMenu);
        rvListMenu = findViewById(R.id.listMenu);

        btnMenu.setImageResource(R.drawable.ic_arrow_back);
        txtMenu.setText("Daftar Menu");
        rvListMenu.setLayoutManager(new LinearLayoutManager(this));
        rvListMenu.setHasFixedSize(true);
        rvListMenu.setItemAnimator(new DefaultItemAnimator());
        rvListMenu.setAdapter(menuAdapter);

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
            pDialog = new KAlertDialog(DaftarMenuActivity.this, KAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Sedang Mengambil Data ...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        menus.clear();
        kategoris.clear();
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                kategoris.clear();

                for (DataSnapshot data : dataSnapshot.child("kategori").getChildren()) {

                    Kategori kategori = new Kategori();
                    kategori.setId(Integer.parseInt(data.getKey()));
                    kategori.setKategori(data.child("kategori").getValue().toString());
                    kategori.setProduks(new ArrayList<Produk>());
                    kategoris.add(kategori);
                }

                for (DataSnapshot data : dataSnapshot.child("produk").getChildren()) {

                    Produk produk = new Produk();

                    produk.setId(Integer.parseInt(data.getKey()));
                    produk.setNamaProduk(data.child("namaProduk").getValue().toString());
                    produk.setHarga(Integer.parseInt(data.child("harga").getValue().toString()));
                    produk.setKodeProduk(data.child("kodeProduk").getValue().toString());
                    produk.setGambarProduk(data.child("gambarProduk").getValue().toString());
                    produk.setKategoriId(data.child("kategoriId").getValue().toString());
                    produk.setStatus(data.child("status").getValue().toString());
                    produk.setQty(0);

                    String[] kategoriId = data.child("kategoriId").getValue().toString().split(",");

                    for (String s : kategoriId) {
                        int idKategori = Integer.parseInt(s);
                        int keyKategori = cariKategoriById(idKategori);
                        if (keyKategori >= 0) {
                            ArrayList<Produk> produks = kategoris.get(keyKategori).getProduks();

                            produks.add(produk);
                            kategoris.get(keyKategori).setProduks(produks);
                        }
                    }
                }

                menus.clear();
                for (Kategori kategori : kategoris) {
                    menus.add((ItemMenu) kategori);

                    for (Produk produk : kategori.getProduks()) {
                        menus.add((ItemMenu) produk);
                    }
                }

                menuAdapter.notifyDataSetChanged();
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


    int cariKategoriById(int id) {
        int i = 0;
        for (Kategori kategori : kategoris) {
            if (kategori.getId() == id) {
                return i;
            }

            i++;
        }

        return -1;
    }


}