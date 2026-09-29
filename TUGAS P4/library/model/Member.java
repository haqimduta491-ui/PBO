package library.model;

import java.util.ArrayList;

/**
 * Model anggota perpustakaan.
 */
public class Member {
    public static final int MAKS_PINJAMAN = 3;

    private final String id;
    private final String nama;
    private final ArrayList<Book> daftarPinjaman = new ArrayList<>();
    private int totalPeminjaman = 0;         // akumulasi riwayat pinjam

    public Member(String id, String nama) {
        this.id = id;
        this.nama = nama;
    }

    public String getId() { return id; }
    public String getNama() { return nama; }
    public ArrayList<Book> getDaftarPinjaman() { return daftarPinjaman; }
    public int getTotalPeminjaman() { return totalPeminjaman; }

    public void tambahPinjaman(Book b) {
        daftarPinjaman.add(b);
        totalPeminjaman++;
    }

    public boolean hapusPinjaman(Book b) {
        return daftarPinjaman.remove(b);
    }

    @Override
    public String toString() {
        return id + " - " + nama + " (sedang pinjam: " + daftarPinjaman.size() + ")";
    }
}
