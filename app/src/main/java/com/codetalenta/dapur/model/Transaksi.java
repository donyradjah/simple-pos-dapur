package com.codetalenta.dapur.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Transaksi implements Serializable {

    int idTransaksi, perangkatId, totalPembelian, totalBayar;
    String namaPemesan, waktuPesan, status, waktuBayar, waktuSelesai, namaPerangkat, nomorMeja;

    ArrayList<DetailTransaksi> detailTransaksis;

    public Transaksi() {
    }

    public Transaksi(int idTransaksi, int perangkatId, int totalPembelian, int totalBayar, String namaPemesan, String waktuPesan, String status, String waktuBayar, String waktuSelesai, String namaPerangkat, String nomorMeja) {
        this.idTransaksi = idTransaksi;
        this.perangkatId = perangkatId;
        this.totalPembelian = totalPembelian;
        this.totalBayar = totalBayar;
        this.namaPemesan = namaPemesan;
        this.waktuPesan = waktuPesan;
        this.status = status;
        this.waktuBayar = waktuBayar;
        this.waktuSelesai = waktuSelesai;
        this.namaPerangkat = namaPerangkat;
        this.nomorMeja = nomorMeja;
    }

    public int getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(int idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public int getPerangkatId() {
        return perangkatId;
    }

    public void setPerangkatId(int perangkatId) {
        this.perangkatId = perangkatId;
    }

    public int getTotalPembelian() {
        return totalPembelian;
    }

    public void setTotalPembelian(int totalPembelian) {
        this.totalPembelian = totalPembelian;
    }

    public int getTotalBayar() {
        return totalBayar;
    }

    public void setTotalBayar(int totalBayar) {
        this.totalBayar = totalBayar;
    }

    public String getNamaPemesan() {
        return namaPemesan;
    }

    public void setNamaPemesan(String namaPemesan) {
        this.namaPemesan = namaPemesan;
    }

    public String getWaktuPesan() {
        return waktuPesan;
    }

    public void setWaktuPesan(String waktuPesan) {
        this.waktuPesan = waktuPesan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWaktuBayar() {
        return waktuBayar;
    }

    public void setWaktuBayar(String waktuBayar) {
        this.waktuBayar = waktuBayar;
    }

    public String getWaktuSelesai() {
        return waktuSelesai;
    }

    public void setWaktuSelesai(String waktuSelesai) {
        this.waktuSelesai = waktuSelesai;
    }

    public String getNamaPerangkat() {
        return namaPerangkat;
    }

    public void setNamaPerangkat(String namaPerangkat) {
        this.namaPerangkat = namaPerangkat;
    }

    public String getNomorMeja() {
        return nomorMeja;
    }

    public void setNomorMeja(String nomorMeja) {
        this.nomorMeja = nomorMeja;
    }

    public ArrayList<DetailTransaksi> getDetailTransaksis() {
        return detailTransaksis;
    }

    public void setDetailTransaksis(ArrayList<DetailTransaksi> detailTransaksis) {
        this.detailTransaksis = detailTransaksis;
    }
}
