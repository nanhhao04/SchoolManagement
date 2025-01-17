package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import controller.TraCuuController;
import java.awt.*;

public class JFTraCuu extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtMaHocSinh;
    private JTextField txtMaLop;
    private JTable tableDiem;
    private JTable tableThongTin;
    private JTable tableHocsinh;
    private JLabel lblHocLuc; // Nhãn hiển thị học lực
    private DefaultTableModel tableModelDiem;
    private DefaultTableModel tableModelThongTin;
    private DefaultTableModel tableModelHocsinh;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                JFTraCuu frame = new JFTraCuu();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public JFTraCuu() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 750);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitle = new JLabel("Tra Cứu Điểm Học Sinh");
        lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lblTitle.setBounds(230, 20, 300, 30);
        contentPane.add(lblTitle);

        JLabel lblMaHocSinh = new JLabel("Mã Học Sinh:");
        lblMaHocSinh.setBounds(20, 70, 100, 25);
        contentPane.add(lblMaHocSinh);

        txtMaHocSinh = new JTextField();
        txtMaHocSinh.setBounds(120, 70, 150, 25);
        contentPane.add(txtMaHocSinh);

        JButton btnTraCuuHocSinh = new JButton("Tra Cứu");
        btnTraCuuHocSinh.setBounds(280, 70, 100, 25);
        contentPane.add(btnTraCuuHocSinh);

        String[] columnNamesThongTin = {"Thông Tin", "Giá Trị"};
        tableModelThongTin = new DefaultTableModel(columnNamesThongTin, 0);
        tableThongTin = new JTable(tableModelThongTin);
        JScrollPane scrollPaneThongTin = new JScrollPane(tableThongTin);
        scrollPaneThongTin.setBounds(10, 133, 740, 103);
        contentPane.add(scrollPaneThongTin);

        String[] columnNamesDiem = {"Môn Học", "Điểm 15 Phút", "Điểm 1 Tiết", "Điểm Cuối Kỳ", "Điểm Trung Bình"};
        tableModelDiem = new DefaultTableModel(columnNamesDiem, 0);
        tableDiem = new JTable(tableModelDiem);
        JScrollPane scrollPaneDiem = new JScrollPane(tableDiem);
        scrollPaneDiem.setBounds(10, 247, 740, 134);
        contentPane.add(scrollPaneDiem);

        JLabel lblHocLucTitle = new JLabel("Học Lực:");
        lblHocLucTitle.setBounds(20, 390, 100, 25);
        contentPane.add(lblHocLucTitle);

        lblHocLuc = new JLabel(""); // Nhãn hiển thị học lực
        lblHocLuc.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblHocLuc.setBounds(120, 390, 200, 25);
        contentPane.add(lblHocLuc);

        JLabel lblMaLop = new JLabel("Mã Lớp:");
        lblMaLop.setBounds(20, 430, 100, 25);
        contentPane.add(lblMaLop);

        txtMaLop = new JTextField();
        txtMaLop.setBounds(120, 430, 150, 25);
        contentPane.add(txtMaLop);

        JButton btnTraCuuLop = new JButton("Tra Cứu Lớp");
        btnTraCuuLop.setBounds(280, 430, 150, 25);
        contentPane.add(btnTraCuuLop);

        String[] columnNamesHocsinh = {"Mã Học Sinh", "Họ Tên", "Ngày Sinh", "Giới Tính"};
        tableModelHocsinh = new DefaultTableModel(columnNamesHocsinh, 0);
        tableHocsinh = new JTable(tableModelHocsinh);
        JScrollPane scrollPaneHocsinh = new JScrollPane(tableHocsinh);
        scrollPaneHocsinh.setBounds(10, 470, 740, 150);
        contentPane.add(scrollPaneHocsinh);

        JButton btnThoat = new JButton("Thoát");
        btnThoat.setBounds(674, 637, 100, 25);
        btnThoat.addActionListener(e -> System.exit(0));
        contentPane.add(btnThoat);

        // Khởi tạo controller
        TraCuuController traCuuController = new TraCuuController(tableModelThongTin, tableModelDiem, tableModelHocsinh);

        // Sự kiện tra cứu học sinh
        btnTraCuuHocSinh.addActionListener(e -> {
            String maHocSinh = txtMaHocSinh.getText();
            if (maHocSinh.isEmpty()) {
                JOptionPane.showMessageDialog(contentPane, "Vui lòng nhập mã học sinh!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            } else {
                traCuuController.layThongTinHocsinh(maHocSinh);
                traCuuController.layThongTinDiem(maHocSinh);

                // Tính học lực từ bảng điểm
                double tongDiemTB = 0.0;
                int soMonHoc = 0;

                for (int i = 0; i < tableModelDiem.getRowCount(); i++) {
                    Object diemTBObj = tableModelDiem.getValueAt(i, 4); // Cột "Điểm Trung Bình"
                    if (diemTBObj != null) {
                        tongDiemTB += (double) diemTBObj;
                        soMonHoc++;
                    }
                }

                if (soMonHoc > 0) {
                    double diemTBChung = tongDiemTB / soMonHoc;
                    lblHocLuc.setText(danhGiaHocLuc(diemTBChung));
                } else {
                    lblHocLuc.setText("Chưa đủ dữ liệu");
                }
            }
        });

        // Sự kiện tra cứu lớp
        btnTraCuuLop.addActionListener(e -> {
            String maLop = txtMaLop.getText();
            if (maLop.isEmpty()) {
                JOptionPane.showMessageDialog(contentPane, "Vui lòng nhập mã lớp!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            } else {
                traCuuController.layThongTinHocsinhTheoMaLop(maLop);
            }
        });
    }

    // Phương thức đánh giá học lực
    private String danhGiaHocLuc(double diemTB) {
        if (diemTB >= 8.0) return "Giỏi";
        if (diemTB >= 6.5) return "Khá";
        if (diemTB >= 5.0) return "Trung Bình";
        return "Yếu";
    }
}
