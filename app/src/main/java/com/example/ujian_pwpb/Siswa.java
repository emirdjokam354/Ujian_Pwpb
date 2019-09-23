package com.example.ujian_pwpb;

public class Siswa {
    String siswaId;
    String judul;
    String deskripsi;
    String time;

    public Siswa(){

    }

    public Siswa(String id, String judul, String deskripsi, String time) {
        this.siswaId = id;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.time = time;
    }

    public String getSiswaId() {
        return siswaId;
    }

    public String getJudul() {
        return judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getTime() {
        return time;
    }
}
