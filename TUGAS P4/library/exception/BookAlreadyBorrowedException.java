package library.exception;

/** Dilempar ketika buku yang diminta sedang dipinjam. */
public class BookAlreadyBorrowedException extends Exception {
    public BookAlreadyBorrowedException(String message) {
        super(message);
    }
}
