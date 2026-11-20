package lab.kasir;

import javax.swing.*;
import java.text.NumberFormat;
import java.util.Locale;

public class KasirGUI extends JFrame {
    private Pesanan pesanan = new Pesanan();

    private JTextField txtMenu, txtHarga, txtJumlah;
    private JTextArea areaStruk;
    private JLabel lblTotal;
    private JButton btnTambah, btnHitung;
    private JButton btnCetak;      // TAHAP 4
    private JLabel lblStatus;      // TAHAP 4

    public KasirGUI() {
        setTitle("Kasir Kantin UNS");
        setSize(420, 560);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel l1 = new JLabel("Menu");
        l1.setBounds(20, 20, 80, 25);
        add(l1);
        txtMenu = new JTextField();
        txtMenu.setBounds(110, 20, 270, 25);
        add(txtMenu);

        JLabel l2 = new JLabel("Harga");
        l2.setBounds(20, 55, 80, 25);
        add(l2);
        txtHarga = new JTextField();
        txtHarga.setBounds(110, 55, 270, 25);
        add(txtHarga);

        JLabel l3 = new JLabel("Jumlah");
        l3.setBounds(20, 90, 80, 25);
        add(l3);
        txtJumlah = new JTextField();
        txtJumlah.setBounds(110, 90, 270, 25);
        add(txtJumlah);

        btnTambah = new JButton("Tambah");
        btnTambah.setBounds(110, 125, 120, 30);
        add(btnTambah);

        areaStruk = new JTextArea();
        areaStruk.setEditable(false);
        JScrollPane sp = new JScrollPane(areaStruk);
        sp.setBounds(20, 170, 360, 180);
        add(sp);

        btnHitung = new JButton("Hitung Total");
        btnHitung.setBounds(20, 365, 140, 30);
        add(btnHitung);

        lblTotal = new JLabel("Total: Rp 0");
        lblTotal.setBounds(180, 365, 200, 30);
        add(lblTotal);

        // TAHAP 4
        btnCetak = new JButton("Cetak Struk");
        btnCetak.setBounds(20, 415, 140, 30);
        add(btnCetak);

        lblStatus = new JLabel("Status: -");
        lblStatus.setBounds(20, 460, 360, 25);
        add(lblStatus);

        // Tombol Tambah
        btnTambah.addActionListener(e -> {
            String menu = txtMenu.getText().trim();
            String hargaStr = txtHarga.getText().trim();
            String jumlahStr = txtJumlah.getText().trim();

            if (menu.isEmpty() || hargaStr.isEmpty() || jumlahStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
                return;
            }
            try {
                double harga = Double.parseDouble(hargaStr);
                int jumlah = Integer.parseInt(jumlahStr);

                pesanan.tambahItem(harga, jumlah);
                areaStruk.append(menu + "  " + jumlah + " x " + format(harga)
                        + " = " + format(harga * jumlah) + "\n");

                txtMenu.setText("");
                txtHarga.setText("");
                txtJumlah.setText("");
                txtMenu.requestFocus();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Harga dan jumlah harus berupa angka!");
            }
        });

        // Tombol Hitung Total
        btnHitung.addActionListener(e ->
                lblTotal.setText("Total: Rp " + format(pesanan.hitungTotal())));

        // TAHAP 4: Tombol Cetak Struk (menunggu di thread baru)
        btnCetak.addActionListener(e -> {
            lblStatus.setText("Status: Mencetak struk...");
            Thread t = new Thread(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                SwingUtilities.invokeLater(() ->
                        lblStatus.setText("Status: Struk selesai"));
            });
            t.start();
        });

        setVisible(true);
    }

    private String format(double nilai) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("id", "ID"));
        nf.setMaximumFractionDigits(0);
        return nf.format(nilai);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new KasirGUI());
    }
}
