public class Main {
    public static void main(String[] args) {
        System.out.println("##### SISTEM RENTAL KENDARAAN #####\n");

        // RUNTIME POLYMORPHISM: objek subclass disimpan di referensi superclass
        Kendaraan[] daftar = new Kendaraan[4];
        daftar[0] = new Mobil("AD 1234 AB", "Toyota Avanza", 350000, 7);
        daftar[1] = new Mobil("AD 5678 CD", "Honda Brio", 300000, 5);
        daftar[2] = new Motor("AD 4321 EF", "Honda Vario", 80000, 150);
        daftar[3] = new Bus("AD 9999 GH", "Hino Medium Bus", 1200000, 30);

        // 1. Tampilkan info (method overriding -> perilaku sesuai objek asli)
        System.out.println(">> DAFTAR KENDARAAN");
        for (Kendaraan k : daftar) {
            k.tampilkanInfo();
            System.out.println();
        }

        // 2. Hitung biaya sewa (abstract method + polymorphism)
        int lamaSewa = 3;
        System.out.println(">> SIMULASI SEWA " + lamaSewa + " HARI");
        for (Kendaraan k : daftar) {
            double biaya = k.hitungBiaya(lamaSewa);
            double asuransi = 0;

            // Cek apakah objek mengimplementasikan interface Asuransi
            if (k instanceof Asuransi) {
                asuransi = ((Asuransi) k).hitungAsuransi(lamaSewa);
            }

            System.out.println(k.getMerk());
            System.out.println("  Biaya sewa : Rp" + String.format("%,.0f", biaya));
            System.out.println("  Asuransi   : Rp" + String.format("%,.0f", asuransi));
            System.out.println("  Total      : Rp" + String.format("%,.0f", biaya + asuransi));
        }

        // 3. Overloading: hitungBiaya dengan diskon
        System.out.println("\n>> DISKON 10% (METHOD OVERLOADING)");
        double tanpaDiskon = daftar[0].hitungBiaya(lamaSewa);
        double denganDiskon = daftar[0].hitungBiaya(lamaSewa, 10);
        System.out.println(daftar[0].getMerk() + " tanpa diskon : Rp" + String.format("%,.0f", tanpaDiskon));
        System.out.println(daftar[0].getMerk() + " diskon 10%   : Rp" + String.format("%,.0f", denganDiskon));

        // 4. Encapsulation: ubah status & tarif lewat method
        System.out.println("\n>> UBAH DATA LEWAT SETTER/METHOD");
        daftar[2].sewa();
        daftar[2].sewa(); // coba sewa lagi saat sudah disewa
        daftar[2].setTarifPerHari(-5000); // validasi menolak nilai negatif
        daftar[2].setTarifPerHari(90000);
        System.out.println("Tarif baru " + daftar[2].getMerk() + ": Rp"
                + String.format("%,.0f", daftar[2].getTarifPerHari()));
        System.out.println("Tersedia? " + daftar[2].isTersedia());
        daftar[2].kembalikan();
        System.out.println("Setelah dikembalikan, tersedia? " + daftar[2].isTersedia());
    }
}

// INTERFACE: kontrak yang harus dipenuhi kendaraan yang bisa diasuransikan
interface Asuransi {
    double hitungAsuransi(int hari);
    String getPaketAsuransi();
}

// ABSTRACT CLASS sekaligus SUPERCLASS
abstract class Kendaraan {
    // ENCAPSULATION: atribut private
    private String platNomor;
    private String merk;
    private double tarifPerHari;
    private boolean tersedia;

    // Constructor
    public Kendaraan(String platNomor, String merk, double tarifPerHari) {
        this.platNomor = platNomor;
        this.merk = merk;
        setTarifPerHari(tarifPerHari);
        this.tersedia = true;
    }

    // Getter
    public String getPlatNomor() { return platNomor; }
    public String getMerk() { return merk; }
    public double getTarifPerHari() { return tarifPerHari; }
    public boolean isTersedia() { return tersedia; }

    // Setter dengan validasi
    public void setTarifPerHari(double tarifPerHari) {
        if (tarifPerHari > 0) {
            this.tarifPerHari = tarifPerHari;
        } else {
            System.out.println("Tarif harus lebih dari 0!");
        }
    }

    // Perubahan status hanya lewat method yang sesuai
    public void sewa() {
        if (tersedia) {
            tersedia = false;
        } else {
            System.out.println(merk + " sedang disewa.");
        }
    }

    public void kembalikan() {
        tersedia = true;
    }

    // ABSTRACT METHOD: wajib diimplementasikan subclass
    public abstract double hitungBiaya(int hari);

    // OVERLOADING: nama sama, parameter berbeda
    public double hitungBiaya(int hari, double diskonPersen) {
        double total = hitungBiaya(hari);
        return total - (total * diskonPersen / 100);
    }

    public void tampilkanInfo() {
        System.out.println("Plat   : " + platNomor);
        System.out.println("Merk   : " + merk);
        System.out.println("Tarif  : Rp" + String.format("%,.0f", tarifPerHari) + "/hari");
        System.out.println("Status : " + (tersedia ? "Tersedia" : "Disewa"));
    }
}

// INHERITANCE: Mobil mewarisi Kendaraan, dan mengimplementasikan interface Asuransi
class Mobil extends Kendaraan implements Asuransi {
    private int jumlahPenumpang;

    public Mobil(String platNomor, String merk, double tarifPerHari, int jumlahPenumpang) {
        super(platNomor, merk, tarifPerHari); // pemanggilan super()
        this.jumlahPenumpang = jumlahPenumpang;
    }

    public int getJumlahPenumpang() { return jumlahPenumpang; }
    public void setJumlahPenumpang(int jumlahPenumpang) { this.jumlahPenumpang = jumlahPenumpang; }

    @Override
    public double hitungBiaya(int hari) {
        // Mobil: tarif dasar + biaya sopir opsional tidak ada, ditambah biaya servis tetap
        return (getTarifPerHari() * hari) + 50000;
    }

    @Override
    public double hitungAsuransi(int hari) {
        return 25000 * hari;
    }

    @Override
    public String getPaketAsuransi() {
        return "Asuransi Penuh (All Risk)";
    }

    @Override
    public void tampilkanInfo() {
        System.out.println("=== MOBIL ===");
        super.tampilkanInfo();
        System.out.println("Kursi  : " + jumlahPenumpang + " penumpang");
        System.out.println("Paket  : " + getPaketAsuransi());
    }
}

// INHERITANCE: Motor mewarisi Kendaraan, dan mengimplementasikan interface Asuransi
class Motor extends Kendaraan implements Asuransi {
    private int kapasitasCC;

    public Motor(String platNomor, String merk, double tarifPerHari, int kapasitasCC) {
        super(platNomor, merk, tarifPerHari);
        this.kapasitasCC = kapasitasCC;
    }

    public int getKapasitasCC() { return kapasitasCC; }
    public void setKapasitasCC(int kapasitasCC) { this.kapasitasCC = kapasitasCC; }

    @Override
    public double hitungBiaya(int hari) {
        // Motor: tarif per hari + biaya helm 5.000/hari
        return (getTarifPerHari() + 5000) * hari;
    }

    @Override
    public double hitungAsuransi(int hari) {
        return 10000 * hari;
    }

    @Override
    public String getPaketAsuransi() {
        return "Asuransi Dasar";
    }

    @Override
    public void tampilkanInfo() {
        System.out.println("=== MOTOR ===");
        super.tampilkanInfo();
        System.out.println("Mesin  : " + kapasitasCC + " cc");
        System.out.println("Paket  : " + getPaketAsuransi());
    }
}

// INHERITANCE: Bus mewarisi Kendaraan, dan mengimplementasikan interface Asuransi
class Bus extends Kendaraan implements Asuransi {
    private int jumlahKursi;

    public Bus(String platNomor, String merk, double tarifPerHari, int jumlahKursi) {
        super(platNomor, merk, tarifPerHari);
        this.jumlahKursi = jumlahKursi;
    }

    public int getJumlahKursi() { return jumlahKursi; }
    public void setJumlahKursi(int jumlahKursi) { this.jumlahKursi = jumlahKursi; }

    @Override
    public double hitungBiaya(int hari) {
        // Bus: tarif per hari + biaya sopir 300.000/hari
        return (getTarifPerHari() + 300000) * hari;
    }

    @Override
    public double hitungAsuransi(int hari) {
        return 75000 * hari;
    }

    @Override
    public String getPaketAsuransi() {
        return "Asuransi Penumpang Premium";
    }

    @Override
    public void tampilkanInfo() {
        System.out.println("=== BUS ===");
        super.tampilkanInfo();
        System.out.println("Kursi  : " + jumlahKursi + " kursi");
        System.out.println("Paket  : " + getPaketAsuransi());
    }
}
