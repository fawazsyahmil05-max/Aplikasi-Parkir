import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class FormParkir extends JFrame {

    JTextField txtPlat, txtJenis, txtJam;
    JTable table;
    DefaultTableModel model;

    public FormParkir() {
        setTitle("CRUD Parkir");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel Input
        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Plat Nomor"));
        txtPlat = new JTextField();
        panel.add(txtPlat);

        panel.add(new JLabel("Jenis Kendaraan"));
        txtJenis = new JTextField();
        panel.add(txtJenis);

        panel.add(new JLabel("Jam Masuk"));
        txtJam = new JTextField();
        panel.add(txtJam);

        JButton btnSimpan = new JButton("Simpan");
        JButton btnHapus = new JButton("Hapus");
        panel.add(btnSimpan);
        panel.add(btnHapus);

        add(panel, BorderLayout.NORTH);

        // Table
        model = new DefaultTableModel(new String[]{"ID", "Plat", "Jenis", "Jam"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        tampilData();

        // EVENT SIMPAN
        btnSimpan.addActionListener(e -> simpan());

        // EVENT HAPUS
        btnHapus.addActionListener(e -> hapus());
    }

    void tampilData() {
        model.setRowCount(0);
        try {
            Connection c = Koneksi.getConnection();
            if (c == null) return;

            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT * FROM parkir");
            while (r.next()) {
                model.addRow(new Object[]{
                        r.getInt("id"),
                        r.getString("plat"),
                        r.getString("jenis"),
                        r.getString("jam_masuk")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    void simpan() {
        try {
            Connection c = Koneksi.getConnection();
            if (c == null) return;

            String sql = "INSERT INTO parkir (plat, jenis, jam_masuk) VALUES (?, ?, ?)";
            PreparedStatement p = c.prepareStatement(sql);
            p.setString(1, txtPlat.getText());
            p.setString(2, txtJenis.getText());
            p.setString(3, txtJam.getText());
            p.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data tersimpan");
            tampilData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    void hapus() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data dulu!");
            return;
        }

        int id = (int) model.getValueAt(row, 0);

        try {
            Connection c = Koneksi.getConnection();
            if (c == null) return;

            PreparedStatement p = c.prepareStatement(
                    "DELETE FROM parkir WHERE id=?"
            );
            p.setInt(1, id);
            p.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data dihapus");
            tampilData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
}
