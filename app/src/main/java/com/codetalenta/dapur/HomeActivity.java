package com.codetalenta.dapur;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.codetalenta.dapur.helper.BaseApiService;
import com.codetalenta.dapur.helper.Session;
import com.codetalenta.dapur.helper.UrlApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.kinda.alert.KAlertDialog;


public class HomeActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    BaseApiService apiService;
    KAlertDialog pDialog;

    TextView nomorMeja, notifPesanan;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        nomorMeja = findViewById(R.id.nomorMeja);
        notifPesanan = findViewById(R.id.notifPesanan);

        session = new Session(this);
        apiService = UrlApi.getAPIService();

        pDialog = new KAlertDialog(this, KAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Sedang Mengambil Data ...");
        pDialog.setCancelable(false);
        pDialog.show();

        nomorMeja.setText("Dapur");
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("FIREBASE TOKEN", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        Log.d("FIREBASE TOKEN", token);
                        Toast.makeText(HomeActivity.this, token, Toast.LENGTH_SHORT).show();
                        getTransaksiBaru();
                    }
                });

        FirebaseMessaging.getInstance().subscribeToTopic("dapur")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "berhasil";
                        if (!task.isSuccessful()) {
                            msg = "gagal";
                        }
                        Log.d("FIREBASE Topic", msg);
                        Toast.makeText(HomeActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });


    }

    void getTransaksiBaru() {
        if (!pDialog.isShowing()) {
            pDialog = new KAlertDialog(HomeActivity.this, KAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Sedang Mengambil Data ...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        myRef = database.getReference("transaksi-proses");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    notifPesanan.setText(Html.fromHtml("<span style='font-size: 20px;'>Total Pesanan Baru : <b> " + snapshot.getValue().toString() + " </b></span>", Html.FROM_HTML_MODE_COMPACT));
                } else {
                    notifPesanan.setText(Html.fromHtml("<span style='font-size: 20px;'>Total Pesanan Baru : <b> " + snapshot.getValue().toString() + " </b></span>"));
                }

                pDialog.dismissWithAnimation();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                pDialog.dismissWithAnimation();
            }
        });


    }

    public void openDaftarMenu(View view) {
        Intent daftarMenu = new Intent(getApplicationContext(), DaftarMenuActivity.class);
        startActivity(daftarMenu);
    }

    public void openDaftarTransksi(View view) {
        Intent daftarTransksi = new Intent(getApplicationContext(), DaftarTransaksiActivity.class);
        startActivity(daftarTransksi);
    }
}