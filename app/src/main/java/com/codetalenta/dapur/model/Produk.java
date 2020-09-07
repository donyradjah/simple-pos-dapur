package com.codetalenta.dapur.model;

import java.io.Serializable;

public class Produk implements ItemMenu, Serializable {

    private String gambarProduk;
    private int harga;
    private String kategoriId;
    private String kodeProduk;
    private String namaProduk;
    private String status;
    private int id;
    private int qty;

    public Produk() {
    }

    public Produk(String gambarProduk, int harga, String kategoriId, String kodeProduk, String namaProduk, String status, int id) {
        this.gambarProduk = gambarProduk;
        this.harga = harga;
        this.kategoriId = kategoriId;
        this.kodeProduk = kodeProduk;
        this.namaProduk = namaProduk;
        this.status = status;
        this.id = id;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGambarProduk() {
        return gambarProduk;
    }

    public void setGambarProduk(String gambarProduk) {
        this.gambarProduk = gambarProduk;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public String getKategoriId() {
        return kategoriId;
    }

    public void setKategoriId(String kategoriId) {
        this.kategoriId = kategoriId;
    }

    public String getKodeProduk() {
        return kodeProduk;
    }

    public void setKodeProduk(String kodeProduk) {
        this.kodeProduk = kodeProduk;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int getType() {
        return TYPE_PRODUK;
    }
}
