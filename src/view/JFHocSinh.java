package view;  

import javax.swing.*;  
import javax.swing.border.EmptyBorder;  
import javax.swing.table.DefaultTableModel;  
import model.HocSinh;  
import controller.HocSinhController;  

import java.awt.*;  
import java.util.ArrayList;  
import java.util.List;
public class JFHocSinh extends JFrame {  

    private static final long serialVersionUID = 1L;  
    private JPanel contentPane;  
    private JTextField txtMaHocSinh;  
    private JTextField txtHoTen;  
    private JTextField txtNgaySinh;  
    private JTextField txtDiaChi;  
    private JTextField txtEmail;  
    private JTextField txtNienKhoa; // New field for academic year  
    private JComboBox<String> comboBoxGioiTinh;  
    private JTable table;  
    private DefaultTableModel tableModel; // Thêm biến cho DefaultTableModel  
    private JButton btnThem;  
    private JButton btnXoa;  
    private JButton btnCapNhat;  
    private JButton btnDanhSach;  
    private List<HocSinh> studentList = new ArrayList<>();
     
    public static void main(String[] args) {  
        EventQueue.invokeLater(() -> {  
            try {  
                JFHocSinh frame = new JFHocSinh();  
                HocSinhController controller = new HocSinhController(frame);  
                frame.setVisible(true);  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        });  
    }  

    public JFHocSinh() {  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        setBounds(100, 100, 1083, 573);  
        contentPane = new JPanel();  
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));  
        setContentPane(contentPane);  
        contentPane.setLayout(null);  

        // Tiêu đề  
        JLabel lblNewLabel = new JLabel("Quản Lý Học Sinh");  
        lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));  
        lblNewLabel.setBounds(296, 20, 200, 30);  
        contentPane.add(lblNewLabel);  

        // Nhập thông tin học sinh  
        JLabel lblMaHocSinh = new JLabel("Mã Học Sinh:");  
        lblMaHocSinh.setBounds(20, 70, 100, 25);  
        contentPane.add(lblMaHocSinh);  

        txtMaHocSinh = new JTextField();  
        txtMaHocSinh.setBounds(120, 70, 150, 25);  
        contentPane.add(txtMaHocSinh);  

        JLabel lblHoTen = new JLabel("Họ Tên:");  
        lblHoTen.setBounds(20, 110, 100, 25);  
        contentPane.add(lblHoTen);  

        txtHoTen = new JTextField();  
        txtHoTen.setBounds(120, 110, 150, 25);  
        contentPane.add(txtHoTen);  

        JLabel lblGioiTinh = new JLabel("Giới Tính:");  
        lblGioiTinh.setBounds(20, 150, 100, 25);  
        contentPane.add(lblGioiTinh);  

        comboBoxGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ", "Khác"});  
        comboBoxGioiTinh.setBounds(120, 150, 150, 25);  
        contentPane.add(comboBoxGioiTinh);  

        JLabel lblNgaySinh = new JLabel("Ngày Sinh:");  
        lblNgaySinh.setBounds(20, 190, 100, 25);  
        contentPane.add(lblNgaySinh);  

        txtNgaySinh = new JTextField();  
        txtNgaySinh.setBounds(120, 190, 150, 25);  
        contentPane.add(txtNgaySinh);  

        JLabel lblDiaChi = new JLabel("Địa Chỉ:");  
        lblDiaChi.setBounds(20, 230, 100, 25);  
        contentPane.add(lblDiaChi);  

        txtDiaChi = new JTextField();  
        txtDiaChi.setBounds(120, 230, 150, 25);  
        contentPane.add(txtDiaChi);  

        JLabel lblEmail = new JLabel("Email:");  
        lblEmail.setBounds(20, 270, 100, 25);  
        contentPane.add(lblEmail);  

        txtEmail = new JTextField();  
        txtEmail.setBounds(120, 270, 150, 25);  
        contentPane.add(txtEmail);  

        // New field for academic year  
        JLabel lblNienKhoa = new JLabel("Niên Khóa:");  
        lblNienKhoa.setBounds(20, 310, 100, 25);  
        contentPane.add(lblNienKhoa);  

        txtNienKhoa = new JTextField();  
        txtNienKhoa.setBounds(120, 310, 150, 25);  
        contentPane.add(txtNienKhoa);  

        // Bảng danh sách học sinh  
        String[] columnNames = {"Mã Học Sinh", "Họ Tên", "Giới Tính", "Ngày Sinh", "Địa Chỉ", "Email", "Niên Khóa"}; // Added "Niên Khóa"  
        tableModel = new DefaultTableModel(columnNames, 0); // Khởi tạo mô hình cho JTable  
        table = new JTable(tableModel);  
        JScrollPane scrollPane = new JScrollPane(table);  
        scrollPane.setBounds(360, 61, 684, 337);  
        contentPane.add(scrollPane);  

        // Nút chức năng  
        btnThem = new JButton("Thêm Học Sinh");  
        btnThem.setBounds(20, 360, 120, 25);  
        contentPane.add(btnThem);  

        btnCapNhat = new JButton("Cập Nhật");  
        btnCapNhat.setBounds(150, 360, 120, 25);  
        contentPane.add(btnCapNhat);  

        btnXoa = new JButton("Xóa Học Sinh");  
        btnXoa.setBounds(20, 400, 120, 25);  
        contentPane.add(btnXoa);  

        btnDanhSach = new JButton("Danh Sách Học Sinh");  
        btnDanhSach.setBounds(150, 400, 200, 25);
        contentPane.add(btnDanhSach);  

        JButton btnThoat = new JButton("Thoát");  
        btnThoat.setBounds(585, 409, 100, 25);  
        btnThoat.addActionListener(e -> System.exit(0));  
        contentPane.add(btnThoat);  

        // Action for adding a student  
        btnThem.addActionListener(e -> {  
            String maHocSinh = txtMaHocSinh.getText();  
            String hoTen = txtHoTen.getText();  
            String gioiTinh = (String) comboBoxGioiTinh.getSelectedItem();  
            String ngaySinh = txtNgaySinh.getText();  
            String diaChi = txtDiaChi.getText();  
            String email = txtEmail.getText();  
            String nienKhoa = txtNienKhoa.getText(); // Get the academic year  

            // Validate input (you can improve validation based on your requirements)  
            if (maHocSinh.isEmpty() || hoTen.isEmpty() || ngaySinh.isEmpty() || diaChi.isEmpty() || email.isEmpty() || nienKhoa.isEmpty()) {  
                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Thông báo", JOptionPane.WARNING_MESSAGE);  
                return;  
            }  

            // Add the new student to the table  
            tableModel.addRow(new Object[]{maHocSinh, hoTen, gioiTinh, ngaySinh, diaChi, email, nienKhoa}); // Include nienKhoa in the table row  

            // Clear inputs after adding  
            txtMaHocSinh.setText("");  
            txtHoTen.setText("");  
            txtNgaySinh.setText("");  
            txtDiaChi.setText("");  
            txtEmail.setText("");  
            txtNienKhoa.setText(""); // Clear the academic year field  
            comboBoxGioiTinh.setSelectedIndex(0); // Reset gender selection  
        });  
        
        // Setup for other buttons like updating and deleting would go here  
        btnDanhSach.addActionListener(e -> {  
            // Clear existing rows from the table model  
            tableModel.setRowCount(0);  

            // Loop through the current list of students (you should maintain this list somewhere)  
            // For example, you could have a List<HocSinh> studentList if you're managing a list of students  
            for (HocSinh hocSinh : studentList) { // Assuming studentList is your list of student objects  
                tableModel.addRow(new Object[]{  
                    hocSinh.getMaHocSinh(),  
                    hocSinh.getHoTen(),  
                    hocSinh.getGioiTinh(),  
                    hocSinh.getNgaySinh(),  
                    hocSinh.getDiaChi(),  
                    hocSinh.getEmail(),  
                    hocSinh.getNienKhoa() // Include the Nien Khoa in the table row  
                });  
            }  
        });
    }  

    // Getter for UI components  
    public JTextField getTxtMaHocSinh() {  
        return txtMaHocSinh;  
    }  

    public JTextField getTxtHoTen() {  
        return txtHoTen;  
    }  

    public JTextField getTxtNgaySinh() {  
        return txtNgaySinh;  
    }  

    public JTextField getTxtDiaChi() {  
        return txtDiaChi;  
    }  

    public JTextField getTxtEmail() {  
        return txtEmail;  
    }  

    public JTextField getTxtNienKhoa() { // Getter for Nien Khoa  
        return txtNienKhoa;  
    }  

    public JComboBox<String> getComboBoxGioiTinh() {  
        return comboBoxGioiTinh;  
    }  

    public JTable getTable() {  
        return table;  
    }  

    public JButton getBtnThem() {  
        return btnThem;  
    }  

    public JButton getBtnXoa() {  
        return btnXoa;  
    }  

    public JButton getBtnCapNhat() {  
        return btnCapNhat;  
    }  

    public JButton getBtnDanhSach() {  
        return btnDanhSach;  
    }  
}