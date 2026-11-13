# Program Pengelolaan Nilai Mahasiswa (Java OOP)

Program Java berorientasi objek untuk mengelola data nilai mahasiswa. Program membaca input dari pengguna, memvalidasi data, menyimpannya dalam `ArrayList`, lalu menyimpan rekapitulasi ke file `laporan_nilai.txt`.

## Struktur Folder

```
nilai/
├── model/
│   ├── Mahasiswa.java
│   └── NilaiInvalidException.java
├── app/
│   └── Main.java
└── README.md
```

## Persyaratan

- JDK 11 atau lebih baru (memakai `String.repeat()`).

## Cara Kompilasi dan Menjalankan

Jalankan dari dalam folder `nilai`:

```bash
javac -d out model/*.java app/*.java
java -ea -cp out app.Main
```

> Opsi `-ea` (enable assertions) wajib agar `assert` pada class `Mahasiswa` aktif. Tanpa opsi ini, JVM mengabaikan `assert`. Sebagai pengaman, `Main` juga memeriksa NIM kosong.

File `laporan_nilai.txt` dibuat di folder tempat perintah `java` dijalankan.

Jika memakai NetBeans, aktifkan assertion di *Project Properties > Run > VM Options* dengan mengisi `-ea`.

## Penjelasan File

### 1. `model/NilaiInvalidException.java`
Custom exception (turunan `Exception`) yang dilempar jika nilai kurang dari 0 atau lebih dari 100.

### 2. `model/Mahasiswa.java`
Class data dengan atribut:

| Atribut | Tipe | Keterangan |
|---|---|---|
| `nama` | `String` | Dirapikan dengan `trim()` dan `toUpperCase()` |
| `nim` | `String` | Dirapikan dengan `trim()`, harus berisi angka saja |
| `nilai` | `double` | Harus 0 sampai 100 |

Konstanta: `FINAL_PASSING_GRADE = 70.0` (`static final`).

Method utama:
- `getGrade()` memetakan nilai ke grade huruf dengan if-else.
- `isLulus()` mengembalikan `boolean`, bernilai `true` jika nilai >= 70.
- `getStatus()` mengembalikan teks `LULUS` atau `TIDAK LULUS`.
- `toString()` mengembalikan satu baris data yang sudah diformat.

Validasi pada constructor:
- `assert !nim.isEmpty() : "NIM tidak boleh kosong!";`
- Setiap karakter NIM diperiksa dengan `Character.isDigit()`. Jika ada karakter non-angka, dilempar `IllegalArgumentException`.
- Nilai di luar rentang 0 sampai 100 melempar `NilaiInvalidException`.

### 3. `app/Main.java`
Class utama yang menjalankan program:
- Membaca jumlah mahasiswa dan data tiap mahasiswa dengan `Scanner`.
- Menyimpan objek ke `ArrayList<Mahasiswa>` (`add()`, `size()`, for-each).
- Jika input salah, program menampilkan pesan error dan meminta input diulang.
- Menghitung rata-rata dan jumlah lulus dengan for-each.
- Menampilkan tabel ke layar dan menulis laporan ke file dengan `BufferedWriter` dan `FileWriter` dalam try-with-resources.
- `Scanner` ditutup pada blok `finally`.

## Skala Grade

| Nilai | Grade |
|---|---|
| 85 - 100 | A |
| 70 - < 85 | B |
| 55 - < 70 | C |
| 0 - < 55 | D |

Status **LULUS** jika nilai >= 70 (`FINAL_PASSING_GRADE`).

Skala di atas adalah asumsi. Ubah di method `getGrade()` jika dosen menetapkan skala lain.

## Contoh Penggunaan

```
Masukkan jumlah mahasiswa: 2

--- Mahasiswa ke-1 ---
Nama  : budi santoso
NIM   : 12345
Nilai : 85
Data berhasil ditambahkan.

--- Mahasiswa ke-2 ---
Nama  : ani
NIM   : 67890
Nilai : 60
Data berhasil ditambahkan.
```

Contoh isi `laporan_nilai.txt`:

```
LAPORAN NILAI MAHASISWA
======================================================================
NIM          NAMA                        NILAI  GRADE STATUS
======================================================================
12345        BUDI SANTOSO                85.00  A     LULUS
67890        ANI                         60.00  C     TIDAK LULUS
======================================================================
Jumlah Mahasiswa : 2
Rata-rata Nilai  : 72.50
Jumlah Lulus     : 1
Jumlah Tdk Lulus : 1
```

## Pemetaan ke Materi

| Pertemuan | Materi | Lokasi |
|---|---|---|
| 2 | Package, class, constructor, `final` | `Mahasiswa.java`, struktur folder |
| 3 | If-else, looping, primitive dan reference type | `getGrade()`, `isLulus()`, `Main.hitungRataRata()` |
| 4 | Custom exception, assertion, String, Character | `NilaiInvalidException.java`, constructor `Mahasiswa` |
| 5 | `ArrayList` | `Main.java` |
| 6 | `Scanner`, `BufferedWriter`, `FileWriter`, try-with-resources | `Main.java` |
