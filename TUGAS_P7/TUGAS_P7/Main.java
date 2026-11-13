package app;

import model.Mahasiswa;
import model.NilaiInvalidException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static final String NAMA_FILE = "laporan_nilai.txt";
    private static final String GARIS = "=".repeat(70);
    private static final String HEADER = String.format("%-12s %-25s %7s  %-5s %s",
            "NIM", "NAMA", "NILAI", "GRADE", "STATUS");

    public static void main(String[] args) {
        ArrayList<Mahasiswa> daftar = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        try {
            int jumlah = bacaJumlah(scanner);

            // Input data mahasiswa
            while (daftar.size() < jumlah) {
                System.out.println("\n--- Mahasiswa ke-" + (daftar.size() + 1) + " ---");
                System.out.print("Nama  : ");
                String nama = scanner.nextLine();
                System.out.print("NIM   : ");
                String nim = scanner.nextLine();
                System.out.print("Nilai : ");
                String nilaiStr = scanner.nextLine();

                try {
                    // Pengaman tambahan bila assertion tidak diaktifkan (-ea)
                    if (nim.trim().isEmpty()) {
                        throw new IllegalArgumentException("NIM tidak boleh kosong!");
                    }
                    double nilai = Double.parseDouble(nilaiStr.trim());
                    daftar.add(new Mahasiswa(nama, nim, nilai));
                    System.out.println("Data berhasil ditambahkan.");
                } catch (NumberFormatException e) {
                    System.out.println("[ERROR] Nilai harus berupa angka. Ulangi input.");
                } catch (NilaiInvalidException e) {
                    System.out.println("[ERROR] " + e.getMessage() + ". Ulangi input.");
                } catch (IllegalArgumentException | AssertionError e) {
                    System.out.println("[ERROR] " + e.getMessage() + " Ulangi input.");
                }
            }

            tampilkanLayar(daftar);
            simpanLaporan(daftar);

        } finally {
            scanner.close(); // selalu dijalankan
        }
    }

    private static int bacaJumlah(Scanner scanner) {
        while (true) {
            System.out.print("Masukkan jumlah mahasiswa: ");
            try {
                int n = Integer.parseInt(scanner.nextLine().trim());
                if (n > 0) return n;
                System.out.println("[ERROR] Jumlah harus lebih dari 0.");
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Masukkan bilangan bulat.");
            }
        }
    }

    // ---- Statistik (looping for-each) ----
    private static double hitungRataRata(ArrayList<Mahasiswa> daftar) {
        double total = 0;
        for (Mahasiswa m : daftar) {
            total += m.getNilai();
        }
        return daftar.isEmpty() ? 0 : total / daftar.size();
    }

    private static int hitungLulus(ArrayList<Mahasiswa> daftar) {
        int lulus = 0;
        for (Mahasiswa m : daftar) {
            if (m.isLulus()) lulus++;
        }
        return lulus;
    }

    private static void tampilkanLayar(ArrayList<Mahasiswa> daftar) {
        System.out.println("\n" + GARIS);
        System.out.println(HEADER);
        System.out.println(GARIS);
        for (Mahasiswa m : daftar) {
            System.out.println(m);
        }
        System.out.println(GARIS);
    }

    // ---- File I/O dengan try-with-resources ----
    private static void simpanLaporan(ArrayList<Mahasiswa> daftar) {
        int lulus = hitungLulus(daftar);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NAMA_FILE))) {
            writer.write("LAPORAN NILAI MAHASISWA");
            writer.newLine();
            writer.write(GARIS);
            writer.newLine();
            writer.write(HEADER);
            writer.newLine();
            writer.write(GARIS);
            writer.newLine();

            for (Mahasiswa m : daftar) {
                writer.write(m.toString());
                writer.newLine();
            }

            writer.write(GARIS);
            writer.newLine();
            writer.write("Jumlah Mahasiswa : " + daftar.size());
            writer.newLine();
            writer.write(String.format("Rata-rata Nilai  : %.2f", hitungRataRata(daftar)));
            writer.newLine();
            writer.write("Jumlah Lulus     : " + lulus);
            writer.newLine();
            writer.write("Jumlah Tdk Lulus : " + (daftar.size() - lulus));
            writer.newLine();

            System.out.printf("Rata-rata: %.2f | Lulus: %d | Tidak lulus: %d%n",
                    hitungRataRata(daftar), lulus, daftar.size() - lulus);
            System.out.println("Laporan berhasil disimpan ke " + NAMA_FILE);
        } catch (IOException e) {
            System.out.println("[ERROR] Gagal menulis file: " + e.getMessage());
        }
    }
}
