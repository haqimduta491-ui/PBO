package library.exception;

/** Dilempar ketika buku tidak ditemukan. */
public class BookNotFoundException extends Exception {
    public BookNotFoundException(String message) {
        super(message);
    }
}
