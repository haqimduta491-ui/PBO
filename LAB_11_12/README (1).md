# Kasir Kantin UNS

Aplikasi kasir sederhana berbasis Java Swing, dikerjakan bertahap dari Tahap 1 sampai Tahap 4. Semua class berada dalam satu package: `lab.kasir`.

## Struktur Proyek

```
kasir-uns/
 └── lab/
      └── kasir/
           ├── Pesanan.java     Tahap 1  - class logika (subtotal, diskon)
           ├── MainKasir.java   Tahap 1+2 - class penguji (punya method main)
           ├── Kasir.java       Tahap 2  - kasir sebagai thread
           └── KasirGUI.java    Tahap 3+4 - tampilan Swing + cetak struk
```

Setiap file berisi satu class public, dan nama file sama dengan nama class-nya. Atribut class dibuat `private` (enkapsulasi) dan diakses lewat method.

## Cara Menjalankan

### Lewat IDE (NetBeans / Eclipse / IntelliJ)

1. Buat package `lab.kasir`.
2. Salin keempat file `.java` ke package tersebut.
3. Klik kanan file yang ingin dijalankan, lalu pilih **Run File**:
   - `MainKasir.java` untuk Tahap 1 dan Tahap 2
   - `KasirGUI.java` untuk Tahap 3 dan Tahap 4

### Lewat terminal

Dari folder `kasir-uns`:

```
javac -d out lab/kasir/*.java
java -cp out lab.kasir.MainKasir
java -cp out lab.kasir.KasirGUI
```

## Penjelasan Tiap Tahap

### Tahap 1: Class Logika (`Pesanan.java`, `MainKasir.java`)

`Pesanan` menyimpan `subtotal` (private) dengan method:

| Method | Fungsi |
|---|---|
| `tambahItem(double harga, int jumlah)` | menambah harga x jumlah ke subtotal |
| `getSubtotal()` | membaca subtotal |
| `hitungTotal()` | jika subtotal > 50.000, kembalikan subtotal dikurangi diskon 10%; jika tidak, kembalikan subtotal |
| `reset()` | mengembalikan subtotal ke 0 |

`MainKasir` menguji dengan data berikut:

| Menu | Harga | Jumlah |
|---|---|---|
| Nasi Goreng | 15.000 | 2 |
| Es Teh | 5.000 | 3 |
| Ayam Bakar | 25.000 | 1 |

Hasil yang diharapkan:

```
Subtotal: 45000 | Total: 45000
Subtotal: 70000 | Total: 63000
```

Setelah dua menu pertama subtotal 45.000 (tanpa diskon). Setelah menu ketiga subtotal 70.000 sehingga kena diskon 10% dan total menjadi 63.000.

### Tahap 2: Dua Kasir sebagai Thread (`Kasir.java`, `MainKasir.java`)

`Kasir` adalah class turunan `Thread`. Method `run()` mencetak kalimat seperti `Kasir Dina melayani pelanggan ke-1` untuk setiap pelanggan, dengan jeda 100 ms agar output kedua kasir tampak bergantian.

Di `MainKasir`, dua kasir (Dina dan Eko) dibuat lalu dijalankan dengan `start()`. Setelah itu `join()` dipanggil untuk keduanya, sehingga kalimat `Semua pelanggan selesai dilayani` selalu tercetak paling akhir.

Catatan hasil: urutan baris Dina dan Eko **tidak selalu sama** di setiap run, karena penjadwalan thread diatur oleh JVM dan sistem operasi.

Untuk tangkap layar Tahap 2 saja, beri komentar pada bagian `TAHAP 1` di `main`.

### Tahap 3: Tampilan GUI Kasir (`KasirGUI.java`)

Jendela dibuat dengan Java Swing, tata letak diatur manual dengan `setBounds()`.

Komponen: field Menu, Harga, Jumlah; tombol **Tambah**; area struk; tombol **Hitung Total**; dan label total.

- **Tombol Tambah:** mengambil isi field, memanggil `pesanan.tambahItem(...)`, menulis satu baris ke area struk, lalu mengosongkan field.
- **Tombol Hitung Total:** menampilkan hasil `pesanan.hitungTotal()` pada label total.
- **Validasi:** jika ada field kosong, atau harga dan jumlah bukan angka, ditampilkan pesan dengan `JOptionPane.showMessageDialog`. Kesalahan angka ditangani dengan `try-catch` pada `NumberFormatException`.

Hasil yang diharapkan: dengan data uji Tahap 1, label total menampilkan `Total: Rp 63.000` setelah ketiga menu ditambahkan.

### Tahap 4: Tombol Cetak Struk dengan Thread (`KasirGUI.java`)

Ditambahkan tombol **Cetak Struk** dan label status. Saat tombol ditekan, status berubah menjadi `Mencetak struk...`, menunggu 2 detik, lalu menjadi `Struk selesai`.

Penantian 2 detik (`Thread.sleep(2000)`) dilakukan di thread baru dengan `start()`, sehingga jendela tidak macet. Perubahan label dilakukan lewat `SwingUtilities.invokeLater` karena komponen Swing sebaiknya hanya diubah dari thread tampilan (EDT).

Bagian kode Tahap 4 ditandai komentar `// TAHAP 4`. Cara mengujinya:

1. Tekan **Cetak Struk**, status menjadi "Mencetak struk...".
2. Saat 2 detik berjalan, tekan **Tambah** atau **Hitung Total**. Tombol tetap bisa ditekan.
3. Setelah 2 detik, status berubah menjadi "Struk selesai".

## Daftar Tangkap Layar untuk Laporan

1. Output `MainKasir` Tahap 1 (45000 | 45000 dan 70000 | 63000)
2. Output `MainKasir` Tahap 2, jalankan 2 kali dan bandingkan urutannya
3. GUI dengan tiga menu yang sudah ditambahkan dan label total 63.000
4. Pesan validasi (field kosong atau input bukan angka)
5. GUI saat status "Mencetak struk..." dan setelah "Struk selesai"

## Persyaratan

- JDK 8 atau lebih baru
