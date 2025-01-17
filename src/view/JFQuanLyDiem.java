package view;  

import controller.QuanLyDiemController;  

import javax.swing.*;  
import javax.swing.border.EmptyBorder;  
import javax.swing.table.DefaultTableModel;  
import java.awt.*;  

public class JFQuanLyDiem extends JFrame {  
    private JPanel contentPane;  
    private JTable table;  
    private DefaultTableModel tableModel;  
    private JComboBox<String> comboBoxKhoi, comboBoxLop, comboBoxMon, comboBoxHocKy, comboBoxLoaiKT, comboBoxHocSinh, comboBoxGiaoVien;  
    private JTextField txtDiem;  
    private JButton btnThemDiem, btnXoaDiem, btnHienToanBoDiem, btnThoat;  

    private QuanLyDiemController controller;  

    public JFQuanLyDiem() {  
        setTitle("Quản Lý Điểm");  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        setBounds(100, 100, 750, 600);  

        // Initialize UI  
        initUI();  

        // Initialize Controller  
        controller = new QuanLyDiemController(  
                comboBoxHocSinh, comboBoxKhoi, comboBoxLop, comboBoxMon,  
                comboBoxHocKy, comboBoxLoaiKT, comboBoxGiaoVien, table, tableModel  
        );  

        // Load initial data  
        loadData();  

        // Add event listeners  
        addEventListeners();  
    }  

    private void initUI() {  
        contentPane = new JPanel();  
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));  
        setContentPane(contentPane);  
        contentPane.setLayout(null);  

        JLabel lblTitle = new JLabel("Quản Lý Điểm");  
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));  
        lblTitle.setBounds(300, 10, 200, 30);  
        contentPane.add(lblTitle);  

        // Input fields  
        createInputFields();  

        // Table display  
        tableModel = new DefaultTableModel(new String[]{"Mã HS", "Môn", "Loại KT", "Giáo Viên", "Điểm"}, 0);  
        table = new JTable(tableModel);  
        JScrollPane scrollPane = new JScrollPane(table);  
        scrollPane.setBounds(30, 266, 680, 214);  
        contentPane.add(scrollPane);  

        // Action buttons  
        btnThemDiem = new JButton("Thêm Điểm");  
        btnThemDiem.setBounds(50, 500, 120, 30);  
        contentPane.add(btnThemDiem);  

        btnXoaDiem = new JButton("Xóa Điểm");  
        btnXoaDiem.setBounds(200, 500, 120, 30);  
        contentPane.add(btnXoaDiem);  

        btnHienToanBoDiem = new JButton("Hiện Tất Cả");  
        btnHienToanBoDiem.setBounds(350, 500, 120, 30);  
        contentPane.add(btnHienToanBoDiem);  

        btnThoat = new JButton("Thoát");  
        btnThoat.setBounds(500, 500, 120, 30);  
        contentPane.add(btnThoat);  
    }  

    private void createInputFields() {  
        JLabel lblHocSinh = new JLabel("Học Sinh:");  
        lblHocSinh.setBounds(30, 60, 80, 25);  
        contentPane.add(lblHocSinh);  

        comboBoxHocSinh = new JComboBox<>();  
        comboBoxHocSinh.setBounds(120, 60, 200, 25);  
        contentPane.add(comboBoxHocSinh);  

        JLabel lblKhoi = new JLabel("Khối:");  
        lblKhoi.setBounds(350, 60, 80, 25);  
        contentPane.add(lblKhoi);  

        comboBoxKhoi = new JComboBox<>();  
        comboBoxKhoi.setBounds(430, 60, 200, 25);  
        contentPane.add(comboBoxKhoi);  

        JLabel lblLop = new JLabel("Lớp:");  
        lblLop.setBounds(30, 100, 80, 25);  
        contentPane.add(lblLop);  

        comboBoxLop = new JComboBox<>();  
        comboBoxLop.setBounds(120, 100, 200, 25);  
        contentPane.add(comboBoxLop);  

        JLabel lblMon = new JLabel("Môn:");  
        lblMon.setBounds(350, 100, 80, 25);  
        contentPane.add(lblMon);  

        comboBoxMon = new JComboBox<>();  
        comboBoxMon.setBounds(430, 100, 200, 25);  
        contentPane.add(comboBoxMon);  

        JLabel lblHocKy = new JLabel("Học Kỳ:");  
        lblHocKy.setBounds(30, 140, 80, 25);  
        contentPane.add(lblHocKy);  

        comboBoxHocKy = new JComboBox<>(new String[]{"Học Kỳ 1", "Học Kỳ 2"});  
        comboBoxHocKy.setBounds(120, 140, 200, 25);  
        contentPane.add(comboBoxHocKy);  

        JLabel lblLoaiKT = new JLabel("Loại KT:");  
        lblLoaiKT.setBounds(350, 140, 80, 25);  
        contentPane.add(lblLoaiKT);  

        comboBoxLoaiKT = new JComboBox<>();  
        comboBoxLoaiKT.setBounds(430, 140, 200, 25);  
        contentPane.add(comboBoxLoaiKT);  

        JLabel lblDiem = new JLabel("Điểm:");  
        lblDiem.setBounds(30, 180, 80, 25);  
        contentPane.add(lblDiem);  

        txtDiem = new JTextField();  
        txtDiem.setBounds(120, 180, 200, 25);  
        contentPane.add(txtDiem);  

        JLabel lblGiaoVien = new JLabel("Giáo Viên:");  
        lblGiaoVien.setBounds(30, 220, 80, 25);  
        contentPane.add(lblGiaoVien);  

        comboBoxGiaoVien = new JComboBox<>();  
        comboBoxGiaoVien.setBounds(120, 220, 200, 25);  
        contentPane.add(comboBoxGiaoVien);  
    }  

    private void loadData() {  
        controller.loadHocSinh();  
        controller.loadKhoi();  
        controller.loadLop();  
        controller.loadMon();  
        controller.loadLoaiKiemTra();  
        controller.loadGiaoVien(); // Change this if you don't need to pass comboBoxGiaoVien  
    }  

    private void addEventListeners() {  
        btnThemDiem.addActionListener(e -> {  
            try {  
                String diemText = txtDiem.getText();  
                controller.addScore(diemText); // Pass the score as a String  
                JOptionPane.showMessageDialog(this, "Thêm điểm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);  
                controller.loadScores(); // Call loadScores without parameters to refresh the table  
            } catch (NumberFormatException ex) {  
                JOptionPane.showMessageDialog(this, "Điểm phải là số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);  
            }  
        });  

        btnHienToanBoDiem.addActionListener(e -> controller.loadScores()); // Call loadScores without parameters  

        btnXoaDiem.addActionListener(e -> {  
            int selectedRow = table.getSelectedRow();  
            if (selectedRow != -1) {  
                int maChiTietDiem = (int) tableModel.getValueAt(selectedRow, 0); // Get int ID from table  
                controller.deleteScore(maChiTietDiem); // Call delete with ID  
                JOptionPane.showMessageDialog(this, "Xóa điểm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);  
                controller.loadScores(); // Refresh the scores after deletion  
            } else {  
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);  
            }  
        });  

        btnThoat.addActionListener(e -> dispose());  
    }  

    public static void main(String[] args) {  
        EventQueue.invokeLater(() -> {  
            try {  
                JFQuanLyDiem frame = new JFQuanLyDiem();  
                frame.setVisible(true);  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        });  
    }  
}