package tugaspbo_hukum;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class PelangganFrame extends JFrame {
    private JTextField txtIDPelanggan, txtNama, txtAlamat, txtEmail
, txtTelp, txtSearch;
    private JButton btnAdd, btnUpdate, btnDelete, btnView, btnSearch;

    public PelangganFrame() {
        setTitle("Pelanggan");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel lblIDPelanggan = new JLabel("ID Pelanggan:");
        lblIDPelanggan.setBounds(10, 10, 100, 25);
        add(lblIDPelanggan);

        txtIDPelanggan = new JTextField();
        txtIDPelanggan.setBounds(120, 10, 150, 25);
        add(txtIDPelanggan);

        JLabel lblNama = new JLabel("Nama:");
        lblNama.setBounds(10, 40, 100, 25);
        add(lblNama);

        txtNama = new JTextField();
        txtNama.setBounds(120, 40, 150, 25);
        add(txtNama);

        JLabel lblAlamat = new JLabel("Alamat:");
        lblAlamat.setBounds(10, 70, 100, 25);
        add(lblAlamat);

        txtAlamat = new JTextField();
        txtAlamat.setBounds(120, 70, 150, 25);
        add(txtAlamat);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(10, 100, 100, 25);
        add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(120, 100, 150, 25);
        add(txtEmail);

        JLabel lblTelp = new JLabel("Telepon:");
        lblTelp.setBounds(10, 130, 100, 25);
        add(lblTelp);

        txtTelp = new JTextField();
        txtTelp.setBounds(120, 130, 150, 25);
        add(txtTelp);

        btnAdd = new JButton("Add");
        btnAdd.setBounds(10, 170, 80, 25);
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPelanggan();
            }
        });
        add(btnAdd);

        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(100, 170, 80, 25);
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePelanggan();
            }
        });
        add(btnUpdate);

        btnDelete = new JButton("Delete");
        btnDelete.setBounds(190, 170, 80, 25);
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePelanggan();
            }
        });
        add(btnDelete);

        btnView = new JButton("View");
        btnView.setBounds(280, 170, 80, 25);
        btnView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewPelanggan();
            }
        });
        add(btnView);

        JLabel lblSearch = new JLabel("Search:");
        lblSearch.setBounds(10, 200, 100, 25);
        add(lblSearch);

        txtSearch = new JTextField();
        txtSearch.setBounds(120, 200, 150, 25);
        add(txtSearch);

        btnSearch = new JButton("Search");
        btnSearch.setBounds(280, 200, 80, 25);
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchPelanggan();
            }
        });
        add(btnSearch);
    }

    private void addPelanggan() {
        if (txtNama.getText().isEmpty() || txtAlamat.getText().isEmpty() || txtEmail.getText().isEmpty() || txtTelp.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO pelanggan (Nama, Alamat, Email, Telp) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, txtNama.getText());
            pstmt.setString(2, txtAlamat.getText());
            pstmt.setString(3, txtEmail.getText());
            pstmt.setString(4, txtTelp.getText());
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Pelanggan berhasil ditambahkan");
        } catch (SQLException e
) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void updatePelanggan() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE pelanggan SET Nama=?, Alamat=?, Email=?, Telp=? WHERE ID_Pelanggan=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, txtNama.getText());
            pstmt.setString(2, txtAlamat.getText());
            pstmt.setString(3, txtEmail.getText());
            pstmt.setString(4, txtTelp.getText());
            pstmt.setInt(5, Integer.parseInt(txtIDPelanggan.getText()));
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Pelanggan berhasil diperbarui");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void deletePelanggan() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM pelanggan WHERE ID_Pelanggan=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(txtIDPelanggan.getText()));
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Pelanggan berhasil dihapus");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void viewPelanggan() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM pelanggan";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            String[] columnNames = {"ID Pelanggan", "Nama", "Alamat", "Email", "Telepon"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
            
            while (rs.next()) {
                int idPelanggan = rs.getInt("ID_Pelanggan");
                String nama = rs.getString("Nama");
                String alamat = rs.getString("Alamat");
                String email = rs.getString("Email");
                String telp = rs.getString("Telp");
                model.addRow(new Object[]{idPelanggan, nama, alamat, email, telp});
            }
            
            JTable table = new JTable(model);
            JOptionPane.showMessageDialog(this, new JScrollPane(table), "Data Pelanggan", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void searchPelanggan() {
        String searchTerm = txtSearch.getText();
        if (searchTerm.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Masukkan nama pelanggan untuk mencari!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM pelanggan WHERE Nama LIKE ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + searchTerm + "%");
            ResultSet rs = pstmt.executeQuery();

            String[] columnNames = {"ID Pelanggan", "Nama", "Alamat", "Email", "Telepon"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
            
            while (rs.next()) {
                int idPelanggan = rs.getInt("ID_Pelanggan");
                String nama = rs.getString("Nama");
                String alamat = rs.getString("Alamat");
                String email = rs.getString("Email");
                String telp = rs.getString("Telp");
                model.addRow(new Object[]{idPelanggan, nama, alamat, email, telp});
            }
            
            JTable table = new JTable(model);
            JOptionPane.showMessageDialog(this, new JScrollPane(table), "Hasil Pencarian", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}