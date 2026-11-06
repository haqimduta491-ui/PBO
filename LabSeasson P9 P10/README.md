# Sistem Rental Kendaraan (Java OOP)

Program sederhana untuk menghitung biaya sewa kendaraan (mobil, motor, dan bus) beserta asuransinya. Program ini dibuat untuk menerapkan konsep Pemrograman Berorientasi Objek (OOP) di Java.

## Deskripsi Studi Kasus

Sebuah usaha rental memiliki beberapa jenis kendaraan. Setiap kendaraan punya tarif sewa per hari dan cara hitung biaya yang berbeda:

| Kendaraan | Rumus biaya sewa | Asuransi per hari |
|---|---|---|
| Mobil | (tarif x hari) + Rp50.000 biaya servis | Rp25.000 (Asuransi Penuh) |
| Motor | (tarif + Rp5.000 biaya helm) x hari | Rp10.000 (Asuransi Dasar) |
| Bus | (tarif + Rp300.000 biaya sopir) x hari | Rp75.000 (Asuransi Penumpang Premium) |

## Struktur Class

```
Asuransi (interface)
Kendaraan (abstract class)
 ├── Mobil  (extends Kendaraan, implements Asuransi)
 ├── Motor  (extends Kendaraan, implements Asuransi)
 └── Bus    (extends Kendaraan, implements Asuransi)
Main (class utama)
```

## Penerapan Konsep OOP

### 1. Encapsulation
- Atribut `platNomor`, `merk`, `tarifPerHari`, dan `tersedia` di class `Kendaraan` bersifat `private`.
- Diakses lewat getter (`getMerk()`, `getTarifPerHari()`, `isTersedia()`, dan lainnya).
- `setTarifPerHari()` memvalidasi nilai, tarif harus lebih dari 0.
- Status ketersediaan hanya berubah lewat method `sewa()` dan `kembalikan()`.

### 2. Inheritance
- Superclass: `Kendaraan`.
- Subclass: `Mobil`, `Motor`, `Bus` menggunakan `extends`.
- Constructor subclass memanggil `super(platNomor, merk, tarifPerHari)`.

### 3. Polymorphism
- **Overriding**: `hitungBiaya()` dan `tampilkanInfo()` di-override dengan `@Override`.
- **Overloading**: `hitungBiaya(int hari)` dan `hitungBiaya(int hari, double diskonPersen)`.
- **Runtime polymorphism**: objek `Mobil`, `Motor`, dan `Bus` disimpan dalam array bertipe `Kendaraan[]`.

### 4. Abstract Class
- `Kendaraan` adalah `abstract class`.
- Memiliki abstract method `hitungBiaya(int hari)` yang diimplementasikan oleh `Mobil`, `Motor`, dan `Bus`.

### 5. Interface
- Interface `Asuransi` berisi `hitungAsuransi(int hari)` dan `getPaketAsuransi()`.
- `Mobil`, `Motor`, dan `Bus` menggunakan `implements Asuransi` dan memenuhi semua method kontraknya.

## Struktur File

**Versi satu file** (`SatuFile/`)

```
SatuFile/
└── Main.java      (berisi semua class)
```

**Versi banyak file** (`RentalKendaraan/`)

```
RentalKendaraan/
├── Main.java
├── Kendaraan.java
├── Mobil.java
├── Motor.java
├── Bus.java
└── Asuransi.java
```

## Cara Menjalankan

Pastikan JDK sudah terpasang (cek dengan `javac -version`).

```bash
# Masuk ke folder yang dipilih, misalnya:
cd SatuFile

# Compile
javac *.java

# Jalankan
java Main
```

## Contoh Output (ringkas)

```
##### SISTEM RENTAL KENDARAAN #####

>> DAFTAR KENDARAAN
=== MOBIL ===
Plat   : AD 1234 AB
Merk   : Toyota Avanza
Tarif  : Rp350,000/hari
Status : Tersedia
Kursi  : 7 penumpang
Paket  : Asuransi Penuh (All Risk)
...

>> SIMULASI SEWA 3 HARI
Toyota Avanza
  Biaya sewa : Rp1,100,000
  Asuransi   : Rp75,000
  Total      : Rp1,175,000
...

>> DISKON 10% (METHOD OVERLOADING)
Toyota Avanza tanpa diskon : Rp1,100,000
Toyota Avanza diskon 10%   : Rp990,000

>> UBAH DATA LEWAT SETTER/METHOD
Honda Vario sedang disewa.
Tarif harus lebih dari 0!
Tarif baru Honda Vario: Rp90,000
Tersedia? false
Setelah dikembalikan, tersedia? true
```

## Objek yang Digunakan

| No | Objek | Class | Plat |
|---|---|---|---|
| 1 | Toyota Avanza | Mobil | AD 1234 AB |
| 2 | Honda Brio | Mobil | AD 5678 CD |
| 3 | Honda Vario | Motor | AD 4321 EF |
| 4 | Hino Medium Bus | Bus | AD 9999 GH |
