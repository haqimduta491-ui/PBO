package lab.kasir;

public class MainKasir {
    public static void main(String[] args) {
        // ===== TAHAP 1: uji class Pesanan =====
        Pesanan p = new Pesanan();

        p.tambahItem(15000, 2); // Nasi Goreng
        p.tambahItem(5000, 3);  // Es Teh
        System.out.printf("Subtotal: %.0f | Total: %.0f%n",
                p.getSubtotal(), p.hitungTotal());

        p.tambahItem(25000, 1); // Ayam Bakar
        System.out.printf("Subtotal: %.0f | Total: %.0f%n",
                p.getSubtotal(), p.hitungTotal());

        // ===== TAHAP 2: dua kasir sebagai thread =====
        // Untuk tangkap layar Tahap 2, beri komentar pada bagian TAHAP 1 di atas.
        System.out.println();
        Kasir dina = new Kasir("Dina", 5);
        Kasir eko = new Kasir("Eko", 5);

        dina.start();
        eko.start();

        try {
            dina.join();
            eko.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Semua pelanggan selesai dilayani");
    }
}
