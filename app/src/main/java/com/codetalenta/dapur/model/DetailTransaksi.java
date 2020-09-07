package com.codetalenta.dapur.model;

public class DetailTransaksi {

    int transaksiId, produkId, harga, jumlah;
    String namaProduk, kategoriProduk, kodeProduk;

    public DetailTransaksi() {
    }

    public DetailTransaksi(int transaksiId, int produkId, int harga, int jumlah, String namaProduk, String kategoriProduk, String kodeProduk) {
        this.transaksiId = transaksiId;
        this.produkId = produkId;
        this.harga = harga;
        this.jumlah = jumlah;
        this.namaProduk = namaProduk;
        this.kategoriProduk = kategoriProduk;
        this.kodeProduk = kodeProduk;
    }

    public int getTransaksiId() {
        return transaksiId;
    }

    public void setTransaksiId(int transaksiId) {
        this.transaksiId = transaksiId;
    }

    public int getProdukId() {
        return produkId;
    }

    public void setProdukId(int produkId) {
        this.produkId = produkId;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public String getKategoriProduk() {
        return kategoriProduk;
    }

    public void setKategoriProduk(String kategoriProduk) {
        this.kategoriProduk = kategoriProduk;
    }

    public String getKodeProduk() {
        return kodeProduk;
    }

    public void setKodeProduk(String kodeProduk) {
        this.kodeProduk = kodeProduk;
    }
}
