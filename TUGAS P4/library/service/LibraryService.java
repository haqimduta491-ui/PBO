package library.service;

import library.exception.BookAlreadyBorrowedException;
import library.exception.BookNotFoundException;
import library.exception.BorrowLimitExceededException;
import library.model.Book;
import library.model.Member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Logika bisnis perpustakaan: buku, anggota, transaksi, dan analisis.
 */
public class LibraryService {
    private final ArrayList<Book> daftarBuku = new ArrayList<>();
    private final HashMap<String, Member> daftarAnggota = new HashMap<>();
    private int totalPinjaman = 0;

    // ---------- Manajemen Buku ----------

    public Book tambahBuku(String judul, String penulis, int tahun, String kategori) {
        Book b = new Book(formatKapital(judul), formatKapital(penulis), tahun, formatKapital(kategori));
        daftarBuku.add(b);
        return b;
    }

    public ArrayList<Book> getDaftarBuku() {
        return daftarBuku;
    }

    public Book cariById(int id) {
        for (Book b : daftarBuku) {
            if (b.getId() == id) return b;
        }
        return null;
    }

    /** Cari berdasarkan judul ATAU kategori (tidak case-sensitive). */
    public ArrayList<Book> cariBuku(String kataKunci) {
        ArrayList<Book> hasil = new ArrayList<>();
        String kunci = kataKunci.trim().toLowerCase();
        for (Book b : daftarBuku) {
            if (b.getJudul().toLowerCase().contains(kunci)
                    || b.getKategori().toLowerCase().contains(kunci)) {
                hasil.add(b);
            }
        }
        return hasil;
    }

    /** Jumlah buku per kategori, dihitung dengan looping. */
    public Map<String, Integer> hitungBukuPerKategori() {
        Map<String, Integer> hitung = new TreeMap<>();
        for (Book b : daftarBuku) {
            int sekarang = hitung.getOrDefault(b.getKategori(), 0);
            hitung.put(b.getKategori(), sekarang + 1);
        }
        return hitung;
    }

    // ---------- Anggota ----------

    /** Ambil anggota bila sudah ada, atau daftarkan baru. */
    public Member ambilAtauDaftarkan(String id, String nama) {
        validasiId(id);
        String kunci = id.toUpperCase();
        Member m = daftarAnggota.get(kunci);
        if (m == null) {
            m = new Member(kunci, formatKapital(nama));
            daftarAnggota.put(kunci, m);
        }
        return m;
    }

    public Member cariAnggota(String id) {
        return daftarAnggota.get(id.trim().toUpperCase());
    }

    /** ID anggota hanya boleh huruf/angka (manipulasi Character). */
    public static void validasiId(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID anggota tidak boleh kosong.");
        }
        for (int i = 0; i < id.length(); i++) {
            if (!Character.isLetterOrDigit(id.charAt(i))) {
                throw new IllegalArgumentException(
                        "ID anggota hanya boleh huruf/angka (karakter tidak valid: '" + id.charAt(i) + "').");
            }
        }
    }

    // ---------- Transaksi ----------

    public void pinjamBuku(Member member, int idBuku)
            throws BookNotFoundException, BookAlreadyBorrowedException, BorrowLimitExceededException {
        // Assertion: data anggota harus valid sebelum transaksi (jalankan dengan -ea)
        assert member != null : "Anggota tidak boleh null";
        assert member.getId() != null && !member.getId().isBlank() : "ID anggota tidak valid";
        assert member.getNama() != null && !member.getNama().isBlank() : "Nama anggota tidak valid";

        Book buku = cariById(idBuku);
        if (buku == null) {
            throw new BookNotFoundException("Buku dengan ID " + idBuku + " tidak ditemukan.");
        }
        if (!buku.isTersedia()) {
            throw new BookAlreadyBorrowedException("Buku \"" + buku.getJudul() + "\" sedang dipinjam.");
        }
        if (member.getDaftarPinjaman().size() >= Member.MAKS_PINJAMAN) {
            throw new BorrowLimitExceededException(
                    member.getNama() + " sudah meminjam " + Member.MAKS_PINJAMAN + " buku (batas maksimum).",
                    Member.MAKS_PINJAMAN);
        }

        buku.setTersedia(false);
        buku.tambahJumlahDipinjam();
        member.tambahPinjaman(buku);
        totalPinjaman++;
    }

    public void kembalikanBuku(Member member, int idBuku) throws BookNotFoundException {
        assert member != null && member.getId() != null && !member.getId().isBlank()
                : "Data anggota tidak valid";

        Book buku = cariById(idBuku);
        if (buku == null) {
            throw new BookNotFoundException("Buku dengan ID " + idBuku + " tidak ditemukan.");
        }
        if (!member.hapusPinjaman(buku)) {
            throw new BookNotFoundException("Buku \"" + buku.getJudul()
                    + "\" tidak ada dalam daftar pinjaman " + member.getNama() + ".");
        }
        buku.setTersedia(true);
    }

    // ---------- Analisis ----------

    public int getTotalPinjaman() { return totalPinjaman; }

    public Book bukuPalingSeringDipinjam() {
        Book terbaik = null;
        for (Book b : daftarBuku) {
            if (b.getJumlahDipinjam() > 0
                    && (terbaik == null || b.getJumlahDipinjam() > terbaik.getJumlahDipinjam())) {
                terbaik = b;
            }
        }
        return terbaik;
    }

    public Member anggotaPalingAktif() {
        Member terbaik = null;
        for (Member m : daftarAnggota.values()) {
            if (m.getTotalPeminjaman() > 0
                    && (terbaik == null || m.getTotalPeminjaman() > terbaik.getTotalPeminjaman())) {
                terbaik = m;
            }
        }
        return terbaik;
    }

    /** Kategori dengan total peminjaman terbanyak. */
    public String kategoriPalingPopuler() {
        HashMap<String, Integer> pinjamPerKategori = new HashMap<>();
        for (Book b : daftarBuku) {
            pinjamPerKategori.merge(b.getKategori(), b.getJumlahDipinjam(), Integer::sum);
        }
        String populer = null;
        int max = 0;
        for (Map.Entry<String, Integer> e : pinjamPerKategori.entrySet()) {
            if (e.getValue() > max) {
                max = e.getValue();
                populer = e.getKey();
            }
        }
        return populer == null ? null : populer + " (" + max + "x dipinjam)";
    }

    public String buatLaporan() {
        StringBuilder sb = new StringBuilder();
        sb.append("========== LAPORAN PERPUSTAKAAN ==========\n");
        sb.append("Total buku          : ").append(daftarBuku.size()).append('\n');
        sb.append("Total anggota       : ").append(daftarAnggota.size()).append('\n');
        sb.append("Total pinjaman      : ").append(totalPinjaman).append('\n');

        Book b = bukuPalingSeringDipinjam();
        sb.append("Buku terpopuler     : ")
          .append(b == null ? "-" : b.getJudul() + " (" + b.getJumlahDipinjam() + "x)").append('\n');

        Member m = anggotaPalingAktif();
        sb.append("Anggota paling aktif: ")
          .append(m == null ? "-" : m.getNama() + " (" + m.getTotalPeminjaman() + " pinjaman)").append('\n');

        String kat = kategoriPalingPopuler();
        sb.append("Kategori terpopuler : ").append(kat == null ? "-" : kat).append('\n');

        sb.append("\nJumlah buku per kategori:\n");
        for (Map.Entry<String, Integer> e : hitungBukuPerKategori().entrySet()) {
            sb.append("  - ").append(e.getKey()).append(": ").append(e.getValue()).append('\n');
        }
        sb.append("==========================================");
        return sb.toString();
    }

    // ---------- Utilitas String & Character ----------

    /** Ubah "pemrograman java" -> "Pemrograman Java" (kapital di awal tiap kata). */
    public static String formatKapital(String teks) {
        StringBuilder sb = new StringBuilder();
        boolean awalKata = true;
        for (char c : teks.trim().toCharArray()) {
            if (Character.isWhitespace(c)) {
                awalKata = true;
                sb.append(c);
            } else if (awalKata) {
                sb.append(Character.toUpperCase(c));
                awalKata = false;
            } else {
                sb.append(Character.toLowerCase(c));
            }
        }
        return sb.toString();
    }
}
