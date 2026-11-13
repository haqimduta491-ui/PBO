package model;

/**
 * Kelas data yang merepresentasikan satu mahasiswa beserta nilainya.
 */
public class Mahasiswa {

    // Konstanta standar kelulusan (final + static = konstanta kelas)
    public static final double FINAL_PASSING_GRADE = 70.0;

    private final String nama;   // reference type
    private final String nim;    // reference type
    private final double nilai;  // primitive type

    /**
     * @throws NilaiInvalidException jika nilai < 0 atau > 100
     * @throws IllegalArgumentException jika NIM mengandung karakter bukan angka
     */
    public Mahasiswa(String nama, String nim, double nilai) throws NilaiInvalidException {
        // Perapian String
        String namaRapi = nama.trim().toUpperCase();
        String nimRapi = nim.trim();

        // Assertion: aktifkan dengan opsi JVM -ea
        assert !nimRapi.isEmpty() : "NIM tidak boleh kosong!";

        // Validasi NIM hanya angka menggunakan kelas Character
        for (char c : nimRapi.toCharArray()) {
            if (!Character.isDigit(c)) {
                throw new IllegalArgumentException(
                        "NIM hanya boleh berisi angka, ditemukan karakter '" + c + "'");
            }
        }

        // Validasi nilai dengan Custom Exception
        if (nilai < 0 || nilai > 100) {
            throw new NilaiInvalidException(
                    "Nilai harus antara 0 sampai 100, tetapi diberikan: " + nilai);
        }

        this.nama = namaRapi;
        this.nim = nimRapi;
        this.nilai = nilai;
    }

    public String getNama() { return nama; }

    public String getNim() { return nim; }

    public double getNilai() { return nilai; }

    /** Memetakan nilai ke grade huruf menggunakan if-else. */
    public String getGrade() {
        if (nilai >= 85) {
            return "A";
        } else if (nilai >= 70) {
            return "B";
        } else if (nilai >= 55) {
            return "C";
        } else {
            return "D";
        }
    }

    /** Status kelulusan (tipe primitif boolean). */
    public boolean isLulus() {
        return nilai >= FINAL_PASSING_GRADE;
    }

    public String getStatus() {
        return isLulus() ? "LULUS" : "TIDAK LULUS";
    }

    @Override
    public String toString() {
        return String.format("%-12s %-25s %7.2f  %-5s %s",
                nim, nama, nilai, getGrade(), getStatus());
    }
}
