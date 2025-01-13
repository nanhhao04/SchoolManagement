package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import model.Lop;
import model.Khoi;
import controller.LopController;
import controller.KhoiController;

import java.awt.*;
import java.util.List;

public class JFQuanLyChung extends JFrame {
    private JPanel contentPane;
    private JTextField txtMaLop, txtTenLop, txtMaGiaoVien;
    private JComboBox<String> comboBoxKhoi;
    private JTable classTable;
    private DefaultTableModel classTableModel;

    private JButton btnHienThiLop, btnThemLop, btnCapNhatLop, btnXoaLop, btnQuayLai;

    private LopController lopController;
    private KhoiController khoiController;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                JFQuanLyChung frame = new JFQuanLyChung();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public JFQuanLyChung() {
        setTitle("Quản Lý Lớp và Khối");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        lopController = new LopController();
        khoiController = new KhoiController();

        JLabel lblTitle = new JLabel("Quản Lý Lớp và Khối");
        lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 24));
        lblTitle.setBounds(320, 20, 300, 30);
        contentPane.add(lblTitle);

        JLabel lblKhoi = new JLabel("Khối:");
        lblKhoi.setBounds(20, 70, 100, 25);
        contentPane.add(lblKhoi);

        comboBoxKhoi = new JComboBox<>();
        comboBoxKhoi.setBounds(130, 70, 150, 25);
        contentPane.add(comboBoxKhoi);

        JLabel lblMaLop = new JLabel("Mã Lớp:");
        lblMaLop.setBounds(20, 110, 100, 25);
        contentPane.add(lblMaLop);

        txtMaLop = new JTextField();
        txtMaLop.setBounds(130, 110, 150, 25);
        contentPane.add(txtMaLop);

        JLabel lblTenLop = new JLabel("Tên Lớp:");
        lblTenLop.setBounds(20, 150, 100, 25);
        contentPane.add(lblTenLop);

        txtTenLop = new JTextField();
        txtTenLop.setBounds(130, 150, 150, 25);
        contentPane.add(txtTenLop);

        JLabel lblMaGiaoVien = new JLabel("Mã Giáo Viên:");
        lblMaGiaoVien.setBounds(20, 190, 100, 25);
        contentPane.add(lblMaGiaoVien);

        txtMaGiaoVien = new JTextField();
        txtMaGiaoVien.setBounds(130, 190, 150, 25);
        contentPane.add(txtMaGiaoVien);

        btnHienThiLop = new JButton("Hiển Thị Lớp");
        btnHienThiLop.setBounds(300, 70, 220, 25);
        contentPane.add(btnHienThiLop);

        btnThemLop = new JButton("Thêm Lớp");
        btnThemLop.setBounds(300, 150, 130, 25);
        contentPane.add(btnThemLop);

        String[] columnNames = {"Mã Lớp", "Tên Lớp", "Sĩ Số", "Mã Khối", "Mã Giáo Viên"};
        classTableModel = new DefaultTableModel(columnNames, 0);
        classTable = new JTable(classTableModel);
        JScrollPane scrollPane = new JScrollPane(classTable);
        scrollPane.setBounds(20, 240, 840, 200);
        contentPane.add(scrollPane);

        btnCapNhatLop = new JButton("Cập Nhật");
        btnCapNhatLop.setBounds(450, 150, 130, 25);
        contentPane.add(btnCapNhatLop);

        btnXoaLop = new JButton("Xóa Lớp");
        btnXoaLop.setBounds(600, 150, 130, 25);
        contentPane.add(btnXoaLop);

        btnQuayLai = new JButton("Quay Lại");
        btnQuayLai.setBounds(750, 150, 130, 25);
        contentPane.add(btnQuayLai);

        loadKhoi();

        btnHienThiLop.addActionListener(e -> showClassesForSelectedKhoi());
        btnThemLop.addActionListener(e -> lopController.addLop(txtMaLop, txtTenLop, txtMaGiaoVien, comboBoxKhoi));
        btnCapNhatLop.addActionListener(e -> lopController.updateLop(classTable, txtMaLop, txtTenLop, txtMaGiaoVien, comboBoxKhoi));
        btnXoaLop.addActionListener(e -> lopController.deleteLop(classTable));
        btnQuayLai.addActionListener(e -> dispose());
    }

    private void loadKhoi() {
        List<Khoi> khoiList = khoiController.getAllKhoi();
        for (Khoi khoi : khoiList) {
            comboBoxKhoi.addItem(khoi.getMaKhoi() + " - " + khoi.getTenKhoi());
        }
    }

    private void showClassesForSelectedKhoi() {
        String selectedKhoi = (String) comboBoxKhoi.getSelectedItem();
        if (selectedKhoi != null) {
            String maKhoi = selectedKhoi.split(" - ")[0]; // Lấy mã khối
            List<Lop> classList = lopController.getClassesByKhoi(maKhoi);
            classTableModel.setRowCount(0);
            if (classList.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Không có lớp nào trong khối này!");
                return;
            }
            for (Lop lop : classList) {
                classTableModel.addRow(new Object[]{lop.getMaLop(), lop.getTenLop(), lop.getSiSo(), lop.getMaKhoi(), lop.getMaGiaoVien()});
            }
        }
    }
}
