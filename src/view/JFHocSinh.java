package view;  

import javax.swing.*;  
import javax.swing.border.EmptyBorder;  
import javax.swing.table.DefaultTableModel;  
import controller.HocSinhController;  
import java.awt.*;  
import java.util.ArrayList; // Thêm import cho ArrayList  
import model.HocSinh; // Sửa import  

public class JFHocSinh extends JFrame {  

    private static final long serialVersionUID = 1L;  
    private JPanel contentPane;  
    private JTextField txtMaHocSinh;  
    private JTextField txtHoTen;  
    private JTextField txtNgaySinh;  
    private JTextField txtDiaChi;  
    private JTextField txtEmail;  
    private JTextField txtNienKhoa;  
    private JComboBox<String> comboBoxGioiTinh;  
    private JTable table;  
    private DefaultTableModel tableModel;  
    private JButton btnThem, btnXoa, btnCapNhat, btnDanhSach;  
    

    public static void main(String[] args) {  
        EventQueue.invokeLater(() -> {  
            try {  
                JFHocSinh frame = new JFHocSinh();  
                HocSinhController controller = new HocSinhController(frame);  
                frame.setController(controller);  
                controller.loadHocSinhList(); // Tải dữ liệu học sinh   
                frame.setVisible(true);  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        });  
    }  

    public JFHocSinh() {  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        setBounds(100, 100, 1080, 650);  
        contentPane = new JPanel();  
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));  
        setContentPane(contentPane);  
        contentPane.setLayout(null);  

        JLabel lblTitle = new JLabel("Quản Lý Học Sinh");  
        lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 22));  
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);  
        lblTitle.setBounds(400, 10, 300, 30);  
        contentPane.add(lblTitle);  

        // Nhập thông tin học sinh  
        createLabel("Mã Học Sinh:", 20, 50);  
        txtMaHocSinh = createTextField(150, 50);  

        createLabel("Họ Tên:", 20, 90);  
        txtHoTen = createTextField(150, 90);  

        createLabel("Giới Tính:", 20, 130);  
        comboBoxGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ", "Khác"});  
        comboBoxGioiTinh.setBounds(150, 130, 200, 25);  
        contentPane.add(comboBoxGioiTinh);  

        createLabel("Ngày Sinh (yyyy-MM-dd):", 20, 170);  
        txtNgaySinh = createTextField(150, 170);  

        createLabel("Địa Chỉ:", 20, 210);  
        txtDiaChi = createTextField(150, 210);  

        createLabel("Email:", 20, 250);  
        txtEmail = createTextField(150, 250);  

        createLabel("Niên Khóa:", 20, 290);  
        txtNienKhoa = createTextField(150, 290);  

        // Bảng hiển thị danh sách học sinh  
        String[] columnNames = {"Mã Học Sinh", "Họ Tên", "Giới Tính", "Ngày Sinh", "Địa Chỉ", "Email", "Niên Khóa"};  
        tableModel = new DefaultTableModel(columnNames, 0);  
        table = new JTable(tableModel);  
        JScrollPane scrollPane = new JScrollPane(table);  
        scrollPane.setBounds(400, 50, 650, 400);  
        contentPane.add(scrollPane);  

        // Nút chức năng  
        btnThem = createButton("Thêm Học Sinh", 20, 360);  
        btnCapNhat = createButton("Cập Nhật", 20, 400);  
        btnXoa = createButton("Xóa Học Sinh", 20, 440);  
        btnDanhSach = createButton("Danh Sách Học Sinh", 20, 480);  

        JButton btnThoat = createButton("Thoát", 20, 520);  
        btnThoat.addActionListener(e -> System.exit(0));  
    }  

    private JLabel createLabel(String text, int x, int y) {  
        JLabel label = new JLabel(text);  
        label.setBounds(x, y, 150, 25);  
        contentPane.add(label);  
        return label;  
    }  

    private JTextField createTextField(int x, int y) {  
        JTextField textField = new JTextField();  
        textField.setBounds(x, y, 200, 25);  
        contentPane.add(textField);  
        return textField;  
    }  

    private JButton createButton(String text, int x, int y) {  
        JButton button = new JButton(text);  
        button.setBounds(x, y, 200, 25);  
        contentPane.add(button);  
        return button;  
    }

    public void updateStudentTable(ArrayList<HocSinh> students) {  
    tableModel.setRowCount(0); // Xóa tất cả hàng trong bảng  
    for (HocSinh student : students) {  
        // Thêm từng đối tượng HocSinh vào bảng  
        Object[] row = new Object[]{  
            student.getMaHocSinh(),  
            student.getHoTen(),  
            student.getGioiTinh(),  
            student.getNgaySinh(),  
            student.getDiaChi(),  
            student.getEmail(),  
            student.getNienKhoa()  
        };  
        tableModel.addRow(row); // Thêm hàng mới vào bảng  
    }  
}  

    public void setController(HocSinhController controller) {  
        btnThem.addActionListener(e -> controller.addHocSinh());  
        btnCapNhat.addActionListener(e -> controller.updateHocSinh());  
        btnXoa.addActionListener(e -> controller.deleteHocSinh());  
        btnDanhSach.addActionListener(e -> controller.loadHocSinhList());  

        // Sự kiện khi click vào bảng  
        table.addMouseListener(new java.awt.event.MouseAdapter() {  
            public void mouseClicked(java.awt.event.MouseEvent evt) {  
                int selectedRow = table.getSelectedRow();  
                if (selectedRow >= 0) {  
                    txtMaHocSinh.setText(tableModel.getValueAt(selectedRow, 0).toString());  
                    txtHoTen.setText(tableModel.getValueAt(selectedRow, 1).toString());  
                    comboBoxGioiTinh.setSelectedItem(tableModel.getValueAt(selectedRow, 2).toString());  
                    txtNgaySinh.setText(tableModel.getValueAt(selectedRow, 3).toString());  
                    txtDiaChi.setText(tableModel.getValueAt(selectedRow, 4).toString());  
                    txtEmail.setText(tableModel.getValueAt(selectedRow, 5).toString());  
                    txtNienKhoa.setText(tableModel.getValueAt(selectedRow, 6).toString());  
                }  
            }  
        });  
    }  

    public void displayStudentList(Object[][] data) {  
        tableModel.setRowCount(0); // Xóa tất cả các hàng hiện có  
        for (Object[] row : data) {  
            tableModel.addRow(row); // Thêm hàng mới vào bảng  
        }  
    }  

    // Getters để lấy giá trị từ các trường nhập liệu  
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

    public JTextField getTxtNienKhoa() {  
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