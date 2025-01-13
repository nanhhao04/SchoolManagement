package view;  

import schoolmanagement.jdbcSqlServer; // Import lớp kết nối SQL  
import model.ChiTietDiem;  

import javax.swing.*;  
import javax.swing.border.EmptyBorder;  
import javax.swing.table.DefaultTableModel;  
import java.awt.*;  
import java.awt.event.ActionEvent;  
import java.awt.event.ActionListener;  
import java.sql.*;  

public class JFQuanLyDiem extends JFrame {  
    private static final long serialVersionUID = 1L;  
    private JPanel contentPane;  
    private JTable table;  
    private DefaultTableModel tableModel;  
    private JComboBox<String> comboBoxKhoi, comboBoxLop, comboBoxMon, comboBoxHocKy, comboBoxLoaiKT, comboBoxHocSinh;  
    private JTextField txtDiem;  
    private JButton btnThemDiem;  
    private JButton btnXoaDiem;  
    private JButton btnHienToanBoDiem;  
    private JButton btnThoat;  

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

    public JFQuanLyDiem() {  
        setTitle("Quản Lý Điểm");  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        setBounds(100, 100, 715, 600);  
        contentPane = new JPanel();  
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));  
        setContentPane(contentPane);  
        contentPane.setLayout(null);  

        JLabel lblTitle = new JLabel("Bảng điểm môn học");  
        lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 20));  
        lblTitle.setBounds(200, 20, 200, 30);  
        contentPane.add(lblTitle);  

     // ComboBox chọn học sinh  
        JLabel lblHocSinh = new JLabel("Học Sinh");  
        lblHocSinh.setBounds(30, 70, 100, 25);  
        contentPane.add(lblHocSinh);  

        comboBoxHocSinh = new JComboBox<>();  
        comboBoxHocSinh.setBounds(100, 70, 120, 25);  
        loadHocSinh();  
        contentPane.add(comboBoxHocSinh);  

        // Khối  
        JLabel lblKhoi = new JLabel("Khối");  
        lblKhoi.setBounds(336, 70, 100, 25);  
        contentPane.add(lblKhoi);  

        comboBoxKhoi = new JComboBox<>(new String[]{"Khối 6", "Khối 7", "Khối 8", "Khối 9"});  
        comboBoxKhoi.setBounds(436, 70, 120, 25);  
        contentPane.add(comboBoxKhoi);  

        // Lớp  
        JLabel lblLop = new JLabel("Lớp");  
        lblLop.setBounds(30, 110, 100, 25);  
        contentPane.add(lblLop);  

        comboBoxLop = new JComboBox<>();  
        comboBoxLop.setBounds(100, 110, 120, 25);  
        loadLop(); // Gọi phương thức để tải dữ liệu lớp từ CSDL  
        contentPane.add(comboBoxLop);  

        // Môn  
        JLabel lblMon = new JLabel("Môn");  
        lblMon.setBounds(336, 110, 100, 25);  
        contentPane.add(lblMon);  

        comboBoxMon = new JComboBox<>();  
        comboBoxMon.setBounds(436, 110, 120, 25);  
        loadMon(); // Gọi phương thức để tải dữ liệu môn từ CSDL  
        contentPane.add(comboBoxMon);  

        // Học Kỳ  
        JLabel lblHocKy = new JLabel("Học Kỳ");  
        lblHocKy.setBounds(30, 150, 100, 25);  
        contentPane.add(lblHocKy);  

        comboBoxHocKy = new JComboBox<>(new String[]{"Học Kỳ 1", "Học Kỳ 2"});  
        comboBoxHocKy.setBounds(100, 150, 120, 25);  
        contentPane.add(comboBoxHocKy);  

        // Loại Kiểm Tra  
        JLabel lblLoaiKT = new JLabel("Loại kiểm tra");  
        lblLoaiKT.setBounds(336, 150, 100, 25);  
        contentPane.add(lblLoaiKT);  

        comboBoxLoaiKT = new JComboBox<>();  
        comboBoxLoaiKT.setBounds(436, 150, 120, 25);  
        loadLoaiKiemTra(); // Gọi phương thức để tải dữ liệu loại kiểm tra từ CSDL  
        contentPane.add(comboBoxLoaiKT);  

        // Điểm số  
        JLabel lblDiem = new JLabel("Điểm số");  
        lblDiem.setBounds(30, 190, 100, 25);  
        contentPane.add(lblDiem);  

        txtDiem = new JTextField();  
        txtDiem.setBounds(100, 190, 120, 25);  
        contentPane.add(txtDiem);  

        // Tạo mô hình cho JTable  
        tableModel = new DefaultTableModel(new String[]{"Học Sinh", "Khối", "Lớp", "Môn", "Học Kỳ", "Loại KT", "Điểm"}, 0);  
        table = new JTable(tableModel);  
        JScrollPane scrollPane = new JScrollPane(table);  
        scrollPane.setBounds(30, 230, 640, 150);  
        contentPane.add(scrollPane);  

        // Nút "Thêm điểm"  
        btnThemDiem = new JButton("Thêm điểm");  
        btnThemDiem.setBounds(50, 400, 100, 30);  
        btnThemDiem.addActionListener(new ActionListener() {  
            @Override  
            public void actionPerformed(ActionEvent e) {  
                addScore();  
            }  
        });  
        contentPane.add(btnThemDiem);  

        // Nút "Xóa điểm"  
        btnXoaDiem = new JButton("Xóa điểm");  
        btnXoaDiem.setBounds(200, 400, 100, 30);  
        btnXoaDiem.addActionListener(new ActionListener() {  
            @Override  
            public void actionPerformed(ActionEvent e) {  
                deleteScore();  
            }  
        });  
        contentPane.add(btnXoaDiem);  

        // Nút "Hiện toàn bộ điểm"  
        btnHienToanBoDiem = new JButton("Hiện toàn bộ điểm");  
        btnHienToanBoDiem.setBounds(350, 400, 150, 30);  
        btnHienToanBoDiem.addActionListener(new ActionListener() {  
            @Override  
            public void actionPerformed(ActionEvent e) {  
                loadScores();  
            }  
        });  
        contentPane.add(btnHienToanBoDiem);  

        // Nút "Thoát"  
        btnThoat = new JButton("Thoát");  
        btnThoat.setBounds(520, 500, 70, 30);  
        btnThoat.addActionListener(new ActionListener() {  
            @Override  
            public void actionPerformed(ActionEvent e) {  
                dispose();  
            }  
        });  
        contentPane.add(btnThoat);  
    }  

    // Phương thức để tải danh sách học sinh  
    private void loadHocSinh() {  
        try (Connection connection = jdbcSqlServer.getConnection()) {  
            String sql = "SELECT * FROM HOCSINH"; // Cập nhật bảng tương ứng  
            PreparedStatement statement = connection.prepareStatement(sql);  
            ResultSet resultSet = statement.executeQuery();  

            while (resultSet.next()) {  
                String hoTen = resultSet.getString("HoTen");  // Thay đổi ở đây  
                String maHocSinh = resultSet.getString("MaHocSinh");  
                comboBoxHocSinh.addItem(hoTen + " (" + maHocSinh + ")");  
            }  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
    }
    private void loadLoaiKiemTra() {  
        try (Connection connection = jdbcSqlServer.getConnection()) {  
            String sql = "SELECT * FROM LOAIKIEMTRA";  
            PreparedStatement statement = connection.prepareStatement(sql);  
            ResultSet resultSet = statement.executeQuery();  

            while (resultSet.next()) {  
                String maLoaiKT = resultSet.getString("MaLoaiKiemTra");  
                String tenLoaiKT = resultSet.getString("TenLoaiKiemTra");  
                comboBoxLoaiKT.addItem(maLoaiKT + " - " + tenLoaiKT); // Lưu ý tạo format cụ thể  
            }  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
    }
    private void loadMon() {  
        try (Connection connection = jdbcSqlServer.getConnection()) {  
            String sql = "SELECT MaMonHoc, TenMonHoc FROM MONHOC";  
            PreparedStatement statement = connection.prepareStatement(sql);  
            ResultSet resultSet = statement.executeQuery();  

            while (resultSet.next()) {  
                String maMonHoc = resultSet.getString("MaMonHoc");  
                String tenMonHoc = resultSet.getString("TenMonHoc");  
                comboBoxMon.addItem(maMonHoc + " - " + tenMonHoc); // Định dạng hiển thị  
            }  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
    }
    private void loadLop() {  
        try (Connection connection = jdbcSqlServer.getConnection()) {  
            String sql = "SELECT MaLop, TenLop FROM LOP";  
            PreparedStatement statement = connection.prepareStatement(sql);  
            ResultSet resultSet = statement.executeQuery();  

            while (resultSet.next()) {  
                String maLop = resultSet.getString("MaLop");  
                String tenLop = resultSet.getString("TenLop");  
                comboBoxLop.addItem(maLop + " - " + tenLop); // Định dạng hiển thị  
            }  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
    }

    // Phương thức để thêm điểm  
    private void addScore() {  
        String tenHocSinh = comboBoxHocSinh.getSelectedItem().toString().split(" \\(")[0];  
        String maHocSinh = "";  
        String maMonHoc = comboBoxMon.getSelectedItem().toString().split(" - ")[0];  
        String maLoaiKT = comboBoxLoaiKT.getSelectedItem().toString().split(" - ")[0];  
        String diem = txtDiem.getText();  

        if (!diem.isEmpty()) {  
            try (Connection connection = jdbcSqlServer.getConnection()) {  
                String sqlMaHS = "SELECT MaHocSinh FROM HOCSINH WHERE HoTen = ?";  
                PreparedStatement stmt = connection.prepareStatement(sqlMaHS);  
                stmt.setString(1, tenHocSinh);  
                ResultSet rs = stmt.executeQuery();  
                if (rs.next()) {  
                    maHocSinh = rs.getString("MaHocSinh");  
                }  

                // Lấy mã chi tiết điểm lớn nhất hiện tại  
                String sqlMaxID = "SELECT ISNULL(MAX(MaChiTietDiem), 0) AS MaxID FROM CHITIETDIEM";  
                PreparedStatement stmtMax = connection.prepareStatement(sqlMaxID);  
                ResultSet rsMax = stmtMax.executeQuery();  
                int maChiTietDiem = 0;  
                if (rsMax.next()) {  
                    maChiTietDiem = rsMax.getInt("MaxID") + 1; // Tạo mã mới  
                }  

                // Chèn dữ liệu vào bảng CHITIETDIEM  
                String sqlInsert = "INSERT INTO CHITIETDIEM (MaChiTietDiem, MaHocSinh, MaMonHoc, MaLoaiKiemTra, DiemSo) VALUES (?, ?, ?, ?, ?)";  
                PreparedStatement statement = connection.prepareStatement(sqlInsert);  
                statement.setInt(1, maChiTietDiem); // Sử dụng mã mới  
                statement.setString(2, maHocSinh);  
                statement.setString(3, maMonHoc);  
                statement.setString(4, maLoaiKT);  
                statement.setString(5, diem);  
                statement.executeUpdate();  

                // Cập nhật hiển thị  
                loadScores();  
                txtDiem.setText("");  
            } catch (SQLException ex) {  
                JOptionPane.showMessageDialog(contentPane, "Lỗi khi thêm điểm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);  
            }  
        } else {  
            JOptionPane.showMessageDialog(contentPane, "Vui lòng nhập điểm!", "Lỗi", JOptionPane.ERROR_MESSAGE);  
        }  
    } 

    // Phương thức để xóa điểm  
    private void deleteScore() {  
        String selectedStudent = (String) comboBoxHocSinh.getSelectedItem();  
        String tenHocSinh = selectedStudent != null ? selectedStudent.split(" \\(")[0] : "";  
        String maHocSinh = "";   
        String maMonHoc = comboBoxMon.getSelectedItem().toString().split(" - ")[0]; // Mã môn học cần xóa  

        try (Connection connection = jdbcSqlServer.getConnection()) {  
            // Lấy mã học sinh  
            String sqlMaHS = "SELECT MaHocSinh FROM HOCSINH WHERE HoTen = ?";  
            PreparedStatement stmt = connection.prepareStatement(sqlMaHS);  
            stmt.setString(1, tenHocSinh);  
            ResultSet rs = stmt.executeQuery();  
            if (rs.next()) {  
                maHocSinh = rs.getString("MaHocSinh");  
            }  

            // Câu lệnh xóa trong bảng CHITIETDIEM  
            String sqlDelete = "DELETE FROM CHITIETDIEM WHERE MaHocSinh = ? AND MaMonHoc = ?";  
            PreparedStatement deleteStmt = connection.prepareStatement(sqlDelete);  
            
            // Thực hiện câu lệnh xóa  
            deleteStmt.setString(1, maHocSinh);  
            deleteStmt.setString(2, maMonHoc);   
            deleteStmt.executeUpdate();  

            // Cập nhật bảng hiển thị  
            loadScores(); // Gọi phương thức để tải lại dữ liệu bảng  
        } catch (SQLException ex) {  
            JOptionPane.showMessageDialog(contentPane, "Lỗi khi xóa điểm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);  
        }  
    }  

    private void loadScores() {
        // Xóa dữ liệu hiện tại trong bảng
        tableModel.setRowCount(0);

        try (Connection connection = jdbcSqlServer.getConnection()) {
            // Truy vấn dữ liệu từ các bảng liên quan
            String sql = """
                SELECT hs.MaHocSinh, hs.HoTen AS TenHocSinh, l.TenLop, k.TenKhoi,
                       ct.MaMonHoc, ct.MaLoaiKiemTra, ct.DiemSo
                FROM CHITIETDIEM ct
                JOIN HOCSINH hs ON ct.MaHocSinh = hs.MaHocSinh
                JOIN LOP l ON hs.MaLop = l.MaLop
                JOIN KHOI k ON l.MaKhoi = k.MaKhoi
            """;

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // Lấy dữ liệu từ kết quả truy vấn
                String maHocSinh = resultSet.getString("MaHocSinh");
                String tenHocSinh = resultSet.getString("TenHocSinh");
                String tenLop = resultSet.getString("TenLop");
                String tenKhoi = resultSet.getString("TenKhoi");
                String maMonHoc = resultSet.getString("MaMonHoc");
                String maLoaiKiemTra = resultSet.getString("MaLoaiKiemTra");
                String diemSo = resultSet.getString("DiemSo");

                // Thêm dòng dữ liệu vào bảng
                tableModel.addRow(new Object[]{
                    tenHocSinh,tenKhoi, tenLop , maMonHoc,null, maLoaiKiemTra, diemSo
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(contentPane,
                "Lỗi khi hiển thị điểm: " + ex.getMessage(),
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

}