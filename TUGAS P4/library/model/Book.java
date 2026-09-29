package library.model;

/**
 * Model data buku.
 */
public class Book {
    private static int counter = 0;          // primitive: generator ID otomatis

    private final int id;                    // primitive
    private String judul;                    // reference
    private String penulis;
    private int tahunTerbit;
    private String kategori;
    private boolean tersedia;                // statusKetersediaan
    private int jumlahDipinjam;              // statistik untuk analisis

    public Book(String judul, String penulis, int tahunTerbit, String kategori) {
        this.id = ++counter;
        this.judul = judul;
        this.penulis = penulis;
        this.tahunTerbit = tahunTerbit;
        this.kategori = kategori;
        this.tersedia = true;
        this.jumlahDipinjam = 0;
    }

    public int getId() { return id; }
    public String getJudul() { return judul; }
    public String getPenulis() { return penulis; }
    public int getTahunTerbit() { return tahunTerbit; }
    public String getKategori() { return kategori; }
    public boolean isTersedia() { return tersedia; }
    public int getJumlahDipinjam() { return jumlahDipinjam; }

    public void setTersedia(boolean tersedia) { this.tersedia = tersedia; }
    public void tambahJumlahDipinjam() { this.jumlahDipinjam++; }

    @Override
    public String toString() {
        return String.format("[%d] %-24s | %-22s | %d | %-10s | %s",
                id, judul, penulis, tahunTerbit, kategori,
                tersedia ? "Tersedia" : "Dipinjam");
    }
}
