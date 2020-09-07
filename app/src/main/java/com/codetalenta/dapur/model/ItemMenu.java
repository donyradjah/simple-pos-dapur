package com.codetalenta.dapur.model;

public interface ItemMenu {

    public static final int TYPE_KATEGORI = 0;
    public static final int TYPE_PRODUK = 1;

    abstract public int getType();
}