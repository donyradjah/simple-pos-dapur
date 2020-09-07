package com.codetalenta.dapur.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TransaksiKirim {

    @SerializedName("idPerangkat")
    int idPerangkat;

    @SerializedName("uniqueId")
    String uniqueId;

    @SerializedName("namaPemesan")
    String namaPemesan;

    @SerializedName("totalPembelian")
    int totalPembelian;

    @SerializedName("produks")
    ArrayList<Produk> produks;

    public TransaksiKirim() {
    }

    public TransaksiKirim(int idPerangkat, String uniqueId, String namaPemesan, int totalPembelian, ArrayList<Produk> produks) {
        this.idPerangkat = idPerangkat;
        this.uniqueId = uniqueId;
        this.produks = produks;
        this.namaPemesan = namaPemesan;
        this.totalPembelian = totalPembelian;
    }

    public int getIdPerangkat() {
        return idPerangkat;
    }

    public void setIdPerangkat(int idPerangkat) {
        this.idPerangkat = idPerangkat;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public ArrayList<Produk> getProduks() {
        return produks;
    }

    public void setProduks(ArrayList<Produk> produks) {
        this.produks = produks;
    }

    public String getNamaPemesan() {
        return namaPemesan;
    }

    public void setNamaPemesan(String namaPemesan) {
        this.namaPemesan = namaPemesan;
    }

    public int getTotalPembelian() {
        return totalPembelian;
    }

    public void setTotalPembelian(int totalPembelian) {
        this.totalPembelian = totalPembelian;
    }
}
