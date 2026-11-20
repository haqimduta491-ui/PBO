package lab.kasir;

public class Pesanan {
    private double subtotal;

    public void tambahItem(double harga, int jumlah) {
        subtotal += harga * jumlah;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double hitungTotal() {
        if (subtotal > 50000) {
            return subtotal - (subtotal * 0.10);
        }
        return subtotal;
    }

    public void reset() {
        subtotal = 0;
    }
}
