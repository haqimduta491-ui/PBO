package library.main;

import library.exception.BookAlreadyBorrowedException;
import library.exception.BookNotFoundException;
import library.exception.BorrowLimitExceededException;
import library.model.Book;
import library.model.Member;
import library.service.LibraryService;

import java.util.ArrayList;
import java.util.Scanner;

public class MainApp {
    private static final Scanner input = new Scanner(System.in);
    private static final LibraryService service = new LibraryService();

    public static void main(String[] args) {
        isiDataAwal();
        boolean jalan = true;

        while (jalan) {
            tampilkanMenu();
            int pilihan = bacaInt("Pilih menu: ");
            switch (pilihan) {
                case 1 -> tambahBuku();
                case 2 -> daftarBuku();
                case 3 -> cariBuku();
                case 4 -> pinjamBuku();
                case 5 -> kembalikanBuku();
                case 6 -> System.out.println("\n" + service.buatLaporan());
                case 7 -> {
                    jalan = false;
                    System.out.println("Terima kasih. Sampai jumpa!");
                }
                default -> System.out.println("Menu tidak valid.");
            }
        }
        input.close();
    }

    private static void tampilkanMenu() {
        System.out.println("\n===== PERPUSTAKAAN MINI =====");
        System.out.println("1. Tambah Buku");
        System.out.println("2. Daftar Buku");
        System.out.println("3. Cari Buku");
        System.out.println("4. Pinjam Buku");
        System.out.println("5. Kembalikan Buku");
        System.out.println("6. Laporan Perpustakaan");
        System.out.println("7. Keluar");
    }

    private static void tambahBuku() {
        String judul = bacaString("Judul        : ");
        String penulis = bacaString("Penulis      : ");
        int tahun = bacaInt("Tahun terbit : ");
        String kategori = bacaString("Kategori     : ");

        if (judul.isEmpty() || penulis.isEmpty() || kategori.isEmpty()) {
            System.out.println("Judul, penulis, dan kategori tidak boleh kosong.");
            return;
        }
        Book b = service.tambahBuku(judul, penulis, tahun, kategori);
        System.out.println("Buku ditambahkan: " + b);
    }

    private static void daftarBuku() {
        ArrayList<Book> semua = service.getDaftarBuku();
        if (semua.isEmpty()) {
            System.out.println("Belum ada buku.");
            return;
        }
        System.out.println("\n--- Daftar Buku ---");
        for (Book b : semua) {
            System.out.println(b);
        }
    }

    private static void cariBuku() {
        String kunci = bacaString("Kata kunci (judul/kategori): ");
        ArrayList<Book> hasil = service.cariBuku(kunci);
        if (hasil.isEmpty()) {
            System.out.println("Tidak ada buku yang cocok.");
            return;
        }
        System.out.println("Ditemukan " + hasil.size() + " buku:");
        for (Book b : hasil) {
            System.out.println(b);
        }
    }

    private static void pinjamBuku() {
        try {
            String id = bacaString("ID anggota (huruf/angka): ");
            LibraryService.validasiId(id);

            Member m = service.cariAnggota(id);
            if (m == null) {
                String nama = bacaString("Anggota baru. Nama: ");
                if (nama.isEmpty()) {
                    System.out.println("Nama tidak boleh kosong.");
                    return;
                }
                m = service.ambilAtauDaftarkan(id, nama);
                System.out.println("Anggota terdaftar: " + m);
            }
            int idBuku = bacaInt("ID buku: ");
            service.pinjamBuku(m, idBuku);
            System.out.println("Berhasil! " + m.getNama() + " meminjam buku ID " + idBuku + ".");
        } catch (BookNotFoundException | BookAlreadyBorrowedException e) {
            System.out.println("Gagal: " + e.getMessage());
        } catch (BorrowLimitExceededException e) {
            System.out.println("Gagal (batas " + e.getBatas() + " buku): " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Input tidak valid: " + e.getMessage());
        }
    }

    private static void kembalikanBuku() {
        try {
            String id = bacaString("ID anggota: ");
            LibraryService.validasiId(id);
            Member m = service.cariAnggota(id);
            if (m == null) {
                System.out.println("Anggota tidak ditemukan.");
                return;
            }
            if (m.getDaftarPinjaman().isEmpty()) {
                System.out.println(m.getNama() + " tidak sedang meminjam buku.");
                return;
            }
            System.out.println("Buku yang sedang dipinjam:");
            for (Book b : m.getDaftarPinjaman()) {
                System.out.println("  " + b);
            }
            int idBuku = bacaInt("ID buku yang dikembalikan: ");
            service.kembalikanBuku(m, idBuku);
            System.out.println("Buku berhasil dikembalikan.");
        } catch (BookNotFoundException e) {
            System.out.println("Gagal: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Input tidak valid: " + e.getMessage());
        }
    }

    // ---------- Helper input ----------

    private static String bacaString(String prompt) {
        System.out.print(prompt);
        return input.nextLine().trim();
    }

    private static int bacaInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Masukkan angka yang valid.");
            }
        }
    }

    private static void isiDataAwal() {
        service.tambahBuku("Pemrograman Java Dasar", "Budi Santoso", 2020, "Teknologi");
        service.tambahBuku("Struktur Data", "Siti Rahma", 2018, "Teknologi");
        service.tambahBuku("Laskar Pelangi", "Andrea Hirata", 2005, "Novel");
        service.tambahBuku("Bumi Manusia", "Pramoedya Ananta Toer", 1980, "Novel");
        service.tambahBuku("Sejarah Indonesia", "Agus Wijaya", 2015, "Sejarah");
    }
}
