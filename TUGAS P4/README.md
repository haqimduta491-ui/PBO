# Sistem Manajemen Perpustakaan Mini (Java)

Aplikasi konsol berbasis Java untuk mengelola koleksi buku, anggota, dan transaksi peminjaman, sekaligus menghasilkan analisis sederhana tentang aktivitas perpustakaan. Proyek ini dibuat untuk menerapkan konsep OOP, tipe data, struktur kontrol, exception, assertion, serta manipulasi `String` dan `Character`.

---

## 1. Fitur

| No | Menu | Penjelasan |
|----|------|-----------|
| 1 | Tambah Buku | Menambah buku baru (judul, penulis, tahun terbit, kategori). ID dibuat otomatis. |
| 2 | Daftar Buku | Menampilkan seluruh koleksi beserta status **Tersedia / Dipinjam**. |
| 3 | Cari Buku | Mencari berdasarkan **judul atau kategori** tanpa memperhatikan huruf besar/kecil. |
| 4 | Pinjam Buku | Meminjam buku dengan ID anggota dan ID buku. Anggota baru otomatis didaftarkan. |
| 5 | Kembalikan Buku | Mengembalikan buku yang sedang dipinjam anggota. |
| 6 | Laporan Perpustakaan | Ringkasan statistik dan analisis aktivitas. |
| 7 | Keluar | Menutup aplikasi. |

---

## 2. Struktur Proyek

```
library
├── model
│   ├── Book.java
│   └── Member.java
├── service
│   └── LibraryService.java
├── exception
│   ├── BookNotFoundException.java
│   ├── BookAlreadyBorrowedException.java
│   └── BorrowLimitExceededException.java
└── main
    └── MainApp.java
```

Pembagian tanggung jawab:

- **model**: representasi data (buku dan anggota) tanpa logika bisnis yang rumit.
- **service**: seluruh logika bisnis (pencarian, peminjaman, analisis, laporan).
- **exception**: kondisi kesalahan khusus domain perpustakaan.
- **main**: interaksi pengguna (menu dan input lewat `Scanner`). Bagian ini tidak berisi aturan bisnis.

---

## 3. Penjelasan Class

### `Book` (package `model`)

Menyimpan data satu buku.

| Atribut | Tipe | Keterangan |
|---------|------|-----------|
| `id` | `int` (final) | Dibuat otomatis dari `static int counter`. |
| `judul` | `String` | Judul buku. |
| `penulis` | `String` | Nama penulis. |
| `tahunTerbit` | `int` | Tahun terbit. |
| `kategori` | `String` | Kategori buku. |
| `tersedia` | `boolean` | Status ketersediaan (`true` = bisa dipinjam). |
| `jumlahDipinjam` | `int` | Berapa kali buku ini dipinjam (bahan analisis). |

Constructor `Book(judul, penulis, tahunTerbit, kategori)` mengisi atribut dan mengatur `tersedia = true`.

### `Member` (package `model`)

Menyimpan data anggota.

| Atribut | Tipe | Keterangan |
|---------|------|-----------|
| `id` | `String` | ID anggota (huruf/angka, disimpan huruf besar). |
| `nama` | `String` | Nama anggota. |
| `daftarPinjaman` | `ArrayList<Book>` | Buku yang **sedang** dipinjam. |
| `totalPeminjaman` | `int` | Total riwayat pinjam (dipakai untuk mencari anggota paling aktif). |

Konstanta `MAKS_PINJAMAN = 3` menetapkan batas jumlah buku yang boleh dipinjam bersamaan.

### `LibraryService` (package `service`)

Berisi data dan logika utama:

- `ArrayList<Book> daftarBuku` untuk koleksi buku.
- `HashMap<String, Member> daftarAnggota` untuk anggota (kunci = ID anggota).
- `int totalPinjaman` untuk jumlah seluruh transaksi peminjaman.

Method penting:

| Method | Fungsi |
|--------|--------|
| `tambahBuku(...)` | Membuat objek `Book` dan memasukkannya ke `ArrayList`. |
| `cariBuku(kataKunci)` | Mengubah kata kunci dan judul/kategori ke huruf kecil dengan `toLowerCase()`, lalu mencocokkan dengan `contains()`. |
| `hitungBukuPerKategori()` | Looping seluruh buku dan menghitung jumlah per kategori. |
| `ambilAtauDaftarkan(id, nama)` | Mengambil anggota yang sudah ada atau mendaftarkan anggota baru. |
| `validasiId(id)` | Memeriksa tiap karakter ID dengan `Character.isLetterOrDigit()`. |
| `pinjamBuku(member, idBuku)` | Memproses peminjaman (lihat bagian 5). |
| `kembalikanBuku(member, idBuku)` | Memproses pengembalian. |
| `bukuPalingSeringDipinjam()` | Mencari buku dengan `jumlahDipinjam` terbesar. |
| `anggotaPalingAktif()` | Mencari anggota dengan `totalPeminjaman` terbesar. |
| `kategoriPalingPopuler()` | Menjumlahkan peminjaman per kategori, lalu mengambil yang tertinggi. |
| `buatLaporan()` | Menyusun teks laporan lengkap. |
| `formatKapital(teks)` | Mengubah teks menjadi huruf kapital di awal tiap kata. |

### `MainApp` (package `main`)

Titik masuk program. Menampilkan menu dalam loop `while` dan memilih aksi dengan `switch`. Semua exception ditangkap di sini dan diubah menjadi pesan yang mudah dibaca. Method bantu `bacaInt()` terus meminta ulang input sampai pengguna memasukkan angka yang valid.

---

## 4. Custom Exception

| Exception | Kapan dilempar |
|-----------|----------------|
| `BookNotFoundException` | ID buku tidak ada di koleksi, atau buku yang dikembalikan tidak ada dalam daftar pinjaman anggota. |
| `BookAlreadyBorrowedException` | Buku yang diminta sedang dipinjam orang lain. |
| `BorrowLimitExceededException` | Anggota sudah meminjam 3 buku dan mencoba meminjam lagi. Menyimpan nilai batas lewat `getBatas()`. |

Ketiganya turunan `Exception` (*checked exception*), sehingga compiler memaksa pemanggil menangani atau meneruskannya.

---

## 5. Alur Peminjaman Buku

```
Pengguna memilih menu 4
   │
   ├─ Validasi format ID (huruf/angka saja)  ──► IllegalArgumentException jika salah
   ├─ Cari anggota; jika belum ada → daftarkan (minta nama)
   │
   └─ LibraryService.pinjamBuku(member, idBuku)
        1. assert data anggota valid
        2. Buku ada?               tidak → BookNotFoundException
        3. Buku tersedia?          tidak → BookAlreadyBorrowedException
        4. Pinjaman anggota < 3?   tidak → BorrowLimitExceededException
        5. Ubah status buku, tambah statistik, catat ke daftar pinjaman anggota
```

Urutan pemeriksaan mengikuti urutan pada soal: buku tidak ditemukan, buku sudah dipinjam, lalu batas pinjaman.

---

## 6. Assertion

Sebelum transaksi, `pinjamBuku()` dan `kembalikanBuku()` memastikan data anggota valid:

```java
assert member != null : "Anggota tidak boleh null";
assert member.getId() != null && !member.getId().isBlank() : "ID anggota tidak valid";
assert member.getNama() != null && !member.getNama().isBlank() : "Nama anggota tidak valid";
```

> **Penting:** Java menonaktifkan assertion secara default. Jalankan program dengan opsi `-ea` (*enable assertions*) agar pemeriksaan ini aktif.

---

## 7. Penerapan Konsep

| Konsep | Penerapan dalam program |
|--------|-------------------------|
| Class & Object | `Book`, `Member`, `LibraryService`; objek dibuat dengan `new`. |
| Constructor | `Book(...)` dan `Member(...)` mengisi atribut awal. |
| Method | Getter/setter, `cariBuku()`, `pinjamBuku()`, `buatLaporan()`, dan lainnya. |
| Package | `library.model`, `library.service`, `library.exception`, `library.main`. |
| Variabel | Variabel instance, `static` (`counter`), dan `final` (`MAKS_PINJAMAN`). |
| Tipe data primitive | `int`, `boolean`, `char`. |
| Tipe data reference | `String`, `ArrayList<Book>`, `HashMap<String, Member>`, `TreeMap`. |
| Kondisional | `if`/`else`, `switch`, operator ternary. |
| Looping | `for`, `for-each`, `while` (menu dan pembacaan input). |
| Exception | 3 custom exception, `try`–`catch` (termasuk multi-catch), `throws`. |
| Assertion | `assert` pada validasi anggota. |
| Character | `Character.isLetterOrDigit()`, `isWhitespace()`, `toUpperCase()`, `toLowerCase()`. |
| String | `toLowerCase()`, `contains()`, `trim()`, `isBlank()`, `toUpperCase()`, `StringBuilder`, `String.format()`. |

---

## 8. Cara Menjalankan

**Prasyarat:** JDK 17 atau lebih baru (diuji dengan JDK 21).

Dari folder yang berisi direktori `library`:

```bash
javac -d out $(find library -name "*.java")
java -ea -cp out library.main.MainApp
```

Di Windows (Command Prompt):

```bat
dir /s /b library\*.java > sources.txt
javac -d out @sources.txt
java -ea -cp out library.main.MainApp
```

Di IntelliJ IDEA atau Eclipse: buka folder proyek, lalu isi `-ea` pada *VM options* di Run Configuration.

---

## 9. Contoh Penggunaan

Program sudah berisi 5 buku contoh (Teknologi, Novel, Sejarah). Contoh alur:

```
Pilih menu: 4
ID anggota (huruf/angka): a01
Anggota baru. Nama: andi
Anggota terdaftar: A01 - Andi (sedang pinjam: 0)
ID buku: 1
Berhasil! Andi meminjam buku ID 1.
```

Contoh pesan kesalahan:

```
Gagal: Buku "Pemrograman Java Dasar" sedang dipinjam.
Gagal: Buku dengan ID 99 tidak ditemukan.
Gagal (batas 3 buku): Andi sudah meminjam 3 buku (batas maksimum).
Input tidak valid: ID anggota hanya boleh huruf/angka (karakter tidak valid: '-').
```

Contoh laporan (menu 6):

```
========== LAPORAN PERPUSTAKAAN ==========
Total buku          : 5
Total anggota       : 2
Total pinjaman      : 3
Buku terpopuler     : Pemrograman Java Dasar (1x)
Anggota paling aktif: Andi (3 pinjaman)
Kategori terpopuler : Teknologi (2x dipinjam)

Jumlah buku per kategori:
  - Novel: 2
  - Sejarah: 1
  - Teknologi: 2
==========================================
```

---

## 10. Batasan & Pengembangan Lanjutan

- Data hanya tersimpan di memori; semua data hilang saat program ditutup.
- Belum ada fitur hapus/ubah buku, tanggal jatuh tempo, atau denda.
- Jika beberapa buku atau anggota memiliki nilai tertinggi yang sama, yang ditampilkan adalah yang pertama ditemukan.

Ide pengembangan: penyimpanan ke file/database, riwayat transaksi lengkap, denda keterlambatan, dan pencarian berdasarkan penulis.
