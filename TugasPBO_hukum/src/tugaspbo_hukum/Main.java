package tugaspbo_hukum;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Aplikasi Hukum");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);
            frame.setLayout(null);

            JButton btnLayananHukum = new JButton("Layanan Hukum");
            btnLayananHukum.setBounds(100 , 50, 200, 30);
            btnLayananHukum.addActionListener(e -> {
                LayananHukumFrame layananHukumFrame = new LayananHukumFrame();
                layananHukumFrame.setVisible(true);
            });
            frame.add(btnLayananHukum);

            JButton btnPelanggan = new JButton("Pelanggan");
            btnPelanggan.setBounds(100, 100, 200, 30);
            btnPelanggan.addActionListener(e -> {
                PelangganFrame pelangganFrame = new PelangganFrame();
                pelangganFrame.setVisible(true);
            });
            frame.add(btnPelanggan);

            frame.setVisible(true);
        });
    }
}