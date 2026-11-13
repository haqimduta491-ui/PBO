# TUGAS_P6 - Pengelola Nilai Siswa (Java)

Program Java berbasis konsol dengan gaya procedural/fungsional sederhana untuk mengelola data nilai siswa. Program membaca data dari keyboard, menyimpannya ke file teks `laporan_nilai.txt`, lalu membaca kembali file tersebut dan menampilkannya di konsol.

## Fitur

- Input jumlah siswa, nama (`String`), dan nilai (`double`) lewat `Scanner`.
- Penentuan status kelulusan otomatis di dalam loop:
  - Nilai >= 70 -> **Lulus**
  - Nilai < 70 -> **Tidak Lulus**
- Penulisan laporan ke `laporan_nilai.txt` memakai `FileWriter` dan `BufferedWriter`.
- Ringkasan di akhir file:
  - Rata-rata nilai kelas
  - Jumlah siswa lulus
  - Jumlah siswa tidak lulus
- Pembacaan ulang file memakai `FileReader` dan `BufferedReader` (`readLine()` sampai `null`).
- Validasi input (jumlah harus > 0, nilai harus 0-100, input non-angka ditangani).
- Penanganan `IOException` dan pengelolaan resource dengan **try-with-resources**.

## Persyaratan

- JDK 8 atau lebih baru
- (Opsional) NetBeans / IDE Java lain

## Struktur Proyek

```
TUGAS_P6/
├── TUGAS_P6.java        # Source code utama (berisi method main)
├── laporan_nilai.txt    # Dibuat otomatis saat program dijalankan
└── README.md
```

## Cara Menjalankan

### Lewat terminal

```bash
javac TUGAS_P6.java
java TUGAS_P6
```

Jika file memiliki baris `package ...;`, jalankan dari folder root source dengan nama class lengkap, misalnya `java nama.paket.TUGAS_P6`.

### Lewat NetBeans

1. Buka proyek, pastikan nama class dan file sama-sama `TUGAS_P6`.
2. Klik kanan file lalu pilih **Run File** (Shift+F6).
3. Ketik input langsung di jendela **Output**.

> Jika muncul error `Main method not found in class TUGAS_P6`, pastikan class `TUGAS_P6` memiliki method `public static void main(String[] args)`, atau ubah **Main Class** di *Project Properties > Run*.

## Contoh Penggunaan

**Input:**

```
Masukkan jumlah siswa: 3

Data siswa ke-1
  Nama           : Budi
  Nilai (0-100)  : 85

Data siswa ke-2
  Nama           : Sari
  Nilai (0-100)  : 60.5

Data siswa ke-3
  Nama           : Andi
  Nilai (0-100)  : 70
```

**Isi `laporan_nilai.txt` (juga ditampilkan di konsol):**

```
=== LAPORAN NILAI SISWA ===
No   Nama                      Nilai    Status
-----------------------------------------------------
1    Budi                      85.00    Lulus
2    Sari                      60.50    Tidak Lulus
3    Andi                      70.00    Lulus
-----------------------------------------------------
RINGKASAN
Rata-rata nilai kelas : 71.83
Jumlah siswa lulus    : 2
Jumlah tidak lulus    : 1
```

## Alur Program

| Langkah | Method | Keterangan |
|---------|--------|------------|
| 1 | `bacaJumlahSiswa()` | Meminta dan memvalidasi jumlah siswa |
| 2 | `tulisLaporan()` | Loop input siswa, tentukan status, tulis ke file beserta ringkasan |
| 3 | `bacaLaporan()` | Membaca file baris demi baris dan menampilkannya ke konsol |

Method pendukung: `bacaNilai()` (validasi nilai 0-100) dan `tentukanStatus()` (menentukan Lulus/Tidak Lulus).

## Konsep yang Dipakai

- Input konsol dengan `Scanner`
- File I/O: `FileWriter`, `BufferedWriter`, `FileReader`, `BufferedReader`
- Exception handling: `IOException`, `InputMismatchException`
- Resource management dengan try-with-resources
- Method statis (pendekatan procedural)

## Catatan

- File `laporan_nilai.txt` akan **ditimpa** setiap program dijalankan ulang.
- File dibuat di *working directory* tempat program dijalankan (untuk NetBeans/Maven biasanya folder root proyek).
