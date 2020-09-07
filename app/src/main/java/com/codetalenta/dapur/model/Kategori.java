package com.codetalenta.dapur.model;

import java.util.ArrayList;

public class Kategori implements ItemMenu {

    private int id;
    private String kategori;
    ArrayList<Produk> produks;

    public Kategori() {
    }

    public Kategori(int id, String kategori) {
        this.id = id;
        this.kategori = kategori;
    }

    public ArrayList<Produk> getProduks() {
        return produks;
    }

    public void setProduks(ArrayList<Produk> produks) {
        this.produks = produks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    @Override
    public int getType() {
        return TYPE_KATEGORI;
    }
}
