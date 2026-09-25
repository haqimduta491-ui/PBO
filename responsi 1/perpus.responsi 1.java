
package responsi;

import java.util.ArrayList;
import java.util.Scanner;

// =====================================================
// POIN 1 & 2: CLASS, OBJECT, dan CONSTRUCTOR
// =====================================================
class Buku {

    // Atribut
    String judul;
    String penulis;
    char kode;
    boolean tersedia;

    // POIN 2: Constructor
    Buku(String judul, String penulis, char kode, boolean tersedia) {
        this.judul = judul;
        this.penulis = penulis;
        this.kode = kode;
        this.tersedia = tersedia;
    }

    // Method 1
    void tampilkanInfo() {
        System.out.println("Kode    : " + kode);
        System.out.println("Judul   : " + judul);
        System.out.println("Penulis : " + penulis);

        // POIN 4: Kondisional
        if (tersedia) {
            System.out.println("Status  : Tersedia");
        } else {
            System.out.println("Status  : Sedang dipinjam");
        }
    }

    // Method 2
    void cekJudul() {

        // POIN 7: String method length()
        System.out.println("Panjang judul: " + judul.length());

        // POIN 7: String method toUpperCase()
        System.out.println("Judul kapital: " + judul.toUpperCase());
    }
}


// =====================================================
// CLASS UTAMA
// =====================================================
public class perpus {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        // POIN 3: KONSTANTA
        final int MAKS_PINJAMAN = 3;

        // POIN 8: COLLECTION (ArrayList)
        ArrayList<Buku> daftarBuku = new ArrayList<>();


        // =================================================
        // POIN 9: MEMBUAT OBJECT
        // =================================================

        Buku buku1 = new Buku(
                "Laskar Pelangi",
                "Andrea Hirata",
                'A',
                true
        );

        Buku buku2 = new Buku(
                "Bumi Manusia",
                "Pramoedya Ananta Toer",
                'B',
                false
        );

        Buku buku3 = new Buku(
                "Atomic Habits",
                "James Clear",
                'C',
                true
        );
         Buku buku4 = new Buku(
                "Islam ala Prabowo",
                "Prabowo",
                'S',
                false
         );

        // Memasukkan object ke ArrayList
        daftarBuku.add(buku1);
        daftarBuku.add(buku2);
        daftarBuku.add(buku3);
        daftarBuku.add(buku4);

        System.out.println("===== PERPUSTAKAAN =====");


        // =================================================
        // POIN 5: LOOPING
        // =================================================

        for (int i = 0; i < daftarBuku.size(); i++) {

            System.out.println("\nBuku ke-" + (i + 1));

            daftarBuku.get(i).tampilkanInfo();

            daftarBuku.get(i).cekJudul();
        }


        // =================================================
        // POIN 6: EXCEPTION HANDLING
        // =================================================

        try {

            System.out.print(
                    "\nMasukkan nomor buku yang ingin dipinjam: "
            );

            int pilihan = input.nextInt();


            // POIN 4: KONDISIONAL
            if (pilihan >= 1 && pilihan <= daftarBuku.size()) {

                Buku bukuDipilih =
                        daftarBuku.get(pilihan - 1);


                if (bukuDipilih.tersedia) {

                    System.out.println(
                            "Buku \"" + bukuDipilih.judul
                            + "\" berhasil dipinjam."
                    );

                    bukuDipilih.tersedia = false;


                    // Menggunakan konstanta
                    System.out.println(
                            "Maksimal jumlah peminjaman: "
                            + MAKS_PINJAMAN
                    );

                } else {

                    System.out.println(
                            "Maaf, buku sedang dipinjam."
                    );
                }

            } else {

                System.out.println(
                        "Nomor buku tidak tersedia."
                );
            }

        } catch (Exception e) {

            // Menangani input bukan angka
            System.out.println(
                    "Input tidak valid! Masukkan angka."
            );
        }


        input.close();
    }
}