package tugaspbo_hukum;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LayananHukumFrame extends JFrame {
    private JTextField txtIDKonsultasi, txtIDJadwal, txtIDPelanggan, txtNama, txtNIPKonsultan;
    private JButton btnAdd, btnUpdate, btnDelete, btnView;
    private JTable table;
    private DefaultTableModel tableModel;

    public LayananHukumFrame() {
        setTitle("Konsultasi Layanan Hukum");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel lblIDKonsultasi = new JLabel("ID Konsultasi:");
        lblIDKonsultasi.setBounds(10, 10, 100, 25);
        add(lblIDKonsultasi);

        txtIDKonsultasi = new JTextField();
        txtIDKonsultasi.setBounds(120, 10, 150, 25);
        add(txtIDKonsultasi);

        JLabel lblIDJadwal = new JLabel("ID Jadwal:");
        lblIDJadwal.setBounds(10, 40, 100, 25);
        add(lblIDJadwal);

        txtIDJadwal = new JTextField();
        txtIDJadwal.setBounds(120, 40, 150, 25);
        add(txtIDJadwal);

        JLabel lblIDPelanggan = new JLabel("ID Pelanggan:");
        lblIDPelanggan.setBounds(10, 70, 100, 25);
        add(lblIDPelanggan);

        txtIDPelanggan = new JTextField();
        txtIDPelanggan.setBounds(120, 70, 150, 25);
        add(txtIDPelanggan);

        JLabel lblNama = new JLabel("Nama:");
        lblNama.setBounds(10, 100, 100, 25);
        add(lblNama);

        txtNama = new JTextField();
        txtNama.setBounds(120, 100, 150, 25);
        add(txtNama);

        JLabel lblNIPKonsultan = new JLabel("NIP Konsultan:");
        lblNIPKonsultan.setBounds(10, 130, 100, 25);
        add(lblNIPKonsultan);

        txtNIPKonsultan = new JTextField();
        txtNIPKonsultan.setBounds(120, 130, 150, 25);
        add(txtNIPKonsultan);

        btnAdd = new JButton("Add");
        btnAdd.setBounds(10, 170, 80, 25);
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addKonsultasi();
            }
        });
        add(btnAdd);

        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(100, 170, 80, 25);
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateKonsultasi();
            }
        });
        add(btnUpdate);

        btnDelete = new JButton("Delete");
        btnDelete.setBounds(190, 170, 80, 25);
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteKonsultasi();
            }
        });
        add(btnDelete);

        btnView = new JButton("View");
        btnView.setBounds(280, 170, 80, 25);
        btnView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewKonsultasi();
            }
        });
        add(btnView);

        // Setup table for displaying data
        tableModel = new DefaultTableModel(new String[]{"ID Konsultasi", "ID Jadwal", "ID Pelanggan", "Nama", "NIP Konsultan"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 210, 560, 150);
        add(scrollPane);
    }

    private void addKonsultasi() {
        if (txtIDJadwal.getText().isEmpty() || txtIDPelanggan.getText().isEmpty() || txtNama.getText().isEmpty() || txtNIPKonsultan.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO konsultasi_layanan (ID_Jadwal, ID_Pelanggan, Nama, NIP_konsultan) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(txtIDJadwal.getText()));
            pstmt.setInt(2, Integer.parseInt(txtIDPelanggan.getText()));
            pstmt.setString(3, txtNama.getText());
            pstmt.setString(4, txtNIPKonsultan.getText());
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Konsultasi berhasil ditambahkan");
            viewKonsultasi(); // Refresh the table
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID Jadwal dan ID Pelanggan harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateKonsultasi() {
        if (txtIDKonsultasi.getText().isEmpty() || txtIDJadwal.getText().isEmpty() || txtIDPelanggan.getText().isEmpty() || txtNama.getText().isEmpty() || txtNIPKonsultan.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE konsultasi_layanan SET ID_Jadwal=?, ID_Pelanggan=?, Nama=?, NIP_konsultan=? WHERE ID_Konsultasi=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(txtIDJadwal.getText()));
            pstmt.setInt(2, Integer.parseInt(txtIDPelanggan.getText()));
            pstmt.setString(3, txtNama.getText());
            pstmt.setString(4, txtNIPKonsultan.getText());
            pstmt.setInt(5, Integer.parseInt(txtIDKonsultasi.getText()));
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Konsultasi berhasil diperbarui");
                viewKonsultasi(); // Refresh the table
            } else {
                JOptionPane.showMessageDialog(this, "Konsultasi tidak ditemukan", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID Jadwal, ID Pelanggan, dan ID Konsultasi harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteKonsultasi() {
        if (txtIDKonsultasi.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID Konsultasi harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM konsultasi_layanan WHERE ID_Konsultasi=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(txtIDKonsultasi.getText()));
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Konsultasi berhasil dihapus");
                viewKonsultasi(); // Refresh the table
            } else {
                JOptionPane.showMessageDialog(this, "Konsultasi tidak ditemukan", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this , "ID Konsultasi harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewKonsultasi() {
        tableModel.setRowCount(0); // Clear existing data
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM konsultasi_layanan";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int idKonsultasi = rs.getInt("ID_Konsultasi");
                int idJadwal = rs.getInt("ID_Jadwal");
                int idPelanggan = rs.getInt("ID_Pelanggan");
                String nama = rs.getString("Nama");
                String nipKonsultan = rs.getString("NIP_konsultan");
                tableModel.addRow(new Object[]{idKonsultasi, idJadwal, idPelanggan, nama, nipKonsultan});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LayananHukumFrame frame = new LayananHukumFrame();
            frame.setVisible(true);
        });
    }
}