/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ASUS
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
public class TUGAS_P6 {


    private static final String NAMA_FILE = "laporan_nilai.txt";
    private static final double NILAI_LULUS = 70.0;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int jumlahSiswa = bacaJumlahSiswa(input);
        tulisLaporan(input, jumlahSiswa);
        bacaLaporan();

        input.close();
    }

    // Membaca jumlah siswa dengan validasi input
    private static int bacaJumlahSiswa(Scanner input) {
        int jumlah = 0;
        while (jumlah <= 0) {
            System.out.print("Masukkan jumlah siswa: ");
            try {
                jumlah = input.nextInt();
                if (jumlah <= 0) {
                    System.out.println("Jumlah harus lebih dari 0.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input harus berupa angka bulat.");
            }
            input.nextLine(); // buang sisa baris
        }
        return jumlah;
    }

    // Membaca nilai dengan validasi (0 - 100)
    private static double bacaNilai(Scanner input) {
        while (true) {
            System.out.print("  Nilai (0-100)  : ");
            try {
                double nilai = input.nextDouble();
                input.nextLine();
                if (nilai >= 0 && nilai <= 100) {
                    return nilai;
                }
                System.out.println("  Nilai harus antara 0 dan 100.");
            } catch (InputMismatchException e) {
                System.out.println("  Input harus berupa angka.");
                input.nextLine();
            }
        }
    }

    // Menentukan status kelulusan
    private static String tentukanStatus(double nilai) {
        return nilai >= NILAI_LULUS ? "Lulus" : "Tidak Lulus";
    }

    // Langkah 1: Input dari konsol dan menulis ke file
    private static void tulisLaporan(Scanner input, int jumlahSiswa) {
        double totalNilai = 0;
        int jumlahLulus = 0;
        int jumlahTidakLulus = 0;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NAMA_FILE))) {
            writer.write("=== LAPORAN NILAI SISWA ===");
            writer.newLine();
            writer.write(String.format("%-4s %-25s %-8s %-12s", "No", "Nama", "Nilai", "Status"));
            writer.newLine();
            writer.write("-----------------------------------------------------");
            writer.newLine();

            for (int i = 1; i <= jumlahSiswa; i++) {
                System.out.println("\nData siswa ke-" + i);
                System.out.print("  Nama           : ");
                String nama = input.nextLine().trim();

                double nilai = bacaNilai(input);
                String status = tentukanStatus(nilai);

                totalNilai += nilai;
                if (nilai >= NILAI_LULUS) {
                    jumlahLulus++;
                } else {
                    jumlahTidakLulus++;
                }

                writer.write(String.format("%-4d %-25s %-8.2f %-12s", i, nama, nilai, status));
                writer.newLine();
            }

            double rataRata = totalNilai / jumlahSiswa;

            writer.write("-----------------------------------------------------");
            writer.newLine();
            writer.write("RINGKASAN");
            writer.newLine();
            writer.write(String.format("Rata-rata nilai kelas : %.2f", rataRata));
            writer.newLine();
            writer.write("Jumlah siswa lulus    : " + jumlahLulus);
            writer.newLine();
            writer.write("Jumlah tidak lulus    : " + jumlahTidakLulus);
            writer.newLine();

            System.out.println("\nData berhasil disimpan ke file " + NAMA_FILE);

        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menulis file: " + e.getMessage());
        }
    }

    // Langkah 2: Membaca file dan menampilkannya ke konsol
    private static void bacaLaporan() {
        System.out.println("\n===== ISI FILE " + NAMA_FILE + " =====");

        try (BufferedReader reader = new BufferedReader(new FileReader(NAMA_FILE))) {
            String baris;
            while ((baris = reader.readLine()) != null) {
                System.out.println(baris);
            }
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat membaca file: " + e.getMessage());
        }
    }
}
    

