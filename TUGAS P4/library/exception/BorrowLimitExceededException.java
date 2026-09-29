package library.exception;

/** Dilempar ketika anggota melebihi batas pinjaman (maks. 3 buku). */
public class BorrowLimitExceededException extends Exception {
    private final int batas;

    public BorrowLimitExceededException(String message, int batas) {
        super(message);
        this.batas = batas;
    }

    public int getBatas() { return batas; }
}
