package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import controller.GiaoVienController;
import java.awt.*;

public class JFGiaoVien extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtMaGiaoVien;
    private JTextField txtHoTen;
    private JTextField txtNgaySinh;
    private JTextField txtEmail;
    private JTextField txtMaMonHoc;
    private JComboBox<String> comboBoxGioiTinh;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnThem, btnXoa, btnCapNhat, btnDanhSach;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                JFGiaoVien frame = new JFGiaoVien();
                GiaoVienController controller = new GiaoVienController(frame);
                frame.setController(controller); // Liên kết Controller với View
                controller.loadGiaoVienList(); // Tải danh sách giáo viên ban đầu
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public JFGiaoVien() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1080, 650);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Tiêu đề
        JLabel lblTitle = new JLabel("Quản Lý Giáo Viên");
        lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 22));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(400, 10, 300, 30);
        contentPane.add(lblTitle);

        // Nhập thông tin giáo viên
        createLabel("Mã Giáo Viên:", 20, 50);
        txtMaGiaoVien = createTextField(150, 50);

        createLabel("Họ Tên:", 20, 90);
        txtHoTen = createTextField(150, 90);

        createLabel("Giới Tính:", 20, 130);
        comboBoxGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ", "Khác"});
        comboBoxGioiTinh.setBounds(150, 130, 200, 25);
        contentPane.add(comboBoxGioiTinh);

        createLabel("Ngày Sinh (yyyy-MM-dd):", 20, 170);
        txtNgaySinh = createTextField(150, 170);

        createLabel("Email:", 20, 210);
        txtEmail = createTextField(150, 210);

        createLabel("Mã Môn Học:", 20, 250);
        txtMaMonHoc = createTextField(150, 250);

        // Bảng hiển thị giáo viên
        String[] columnNames = {"Mã Giáo Viên", "Họ Tên", "Giới Tính", "Ngày Sinh", "Email", "Mã Môn Học"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(400, 50, 650, 400);
        contentPane.add(scrollPane);

        // Nút chức năng (Xếp theo hàng dọc)
        btnThem = createButton("Thêm Giáo Viên", 20, 320);
        btnCapNhat = createButton("Cập Nhật", 20, 360);
        btnXoa = createButton("Xóa Giáo Viên", 20, 400);
        btnDanhSach = createButton("Danh Sách Giáo Viên", 20, 440);

        JButton btnThoat = createButton("Thoát", 20, 480);
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

    // Liên kết controller với các nút trong giao diện
    public void setController(GiaoVienController controller) {
        btnThem.addActionListener(e -> controller.addGiaoVien());
        btnCapNhat.addActionListener(e -> controller.updateGiaoVien());
        btnXoa.addActionListener(e -> controller.deleteGiaoVien());
        btnDanhSach.addActionListener(e -> controller.displayGiaoVienList());

        // Thêm sự kiện khi click vào bảng để điền dữ liệu vào các trường nhập liệu
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    txtMaGiaoVien.setText(tableModel.getValueAt(selectedRow, 0).toString());
                    txtHoTen.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    comboBoxGioiTinh.setSelectedItem(tableModel.getValueAt(selectedRow, 2).toString());
                    txtNgaySinh.setText(tableModel.getValueAt(selectedRow, 3).toString());
                    txtEmail.setText(tableModel.getValueAt(selectedRow, 4).toString());
                    txtMaMonHoc.setText(tableModel.getValueAt(selectedRow, 5).toString());
                }
            }
        });
    }

    // Hiển thị danh sách giáo viên trong bảng
    public void displayTeacherList(Object[][] data) {
        tableModel.setRowCount(0); // Xóa dữ liệu cũ
        for (Object[] row : data) {
            tableModel.addRow(row);
        }
    }

    // Getters để lấy giá trị từ các trường nhập liệu
    public JTextField getTxtMaGiaoVien() {
        return txtMaGiaoVien;
    }

    public JTextField getTxtHoTen() {
        return txtHoTen;
    }

    public JTextField getTxtNgaySinh() {
        return txtNgaySinh;
    }

    public JTextField getTxtEmail() {
        return txtEmail;
    }

    public JTextField getTxtMaMonHoc() {
        return txtMaMonHoc;
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
