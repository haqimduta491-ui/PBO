package model;

/**
 * Custom exception (checked) yang dilempar ketika nilai berada di luar rentang 0 - 100.
 */
public class NilaiInvalidException extends Exception {

    public NilaiInvalidException(String pesan) {
        super(pesan);
    }
}
