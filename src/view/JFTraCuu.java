package view;  

import javax.swing.*;  
import javax.swing.border.EmptyBorder;  
import javax.swing.table.DefaultTableModel;  
import java.awt.*;  
import java.sql.*;  
import java.awt.event.ActionEvent;  
import java.awt.event.ActionListener;  

public class JFTraCuu extends JFrame {  

    private static final long serialVersionUID = 1L;  
    private JPanel contentPane;  
    private JTextField txtMaHocSinh;  
    private JTable tableDiem;  
    private DefaultTableModel tableModelDiem;  
    private JTable tableThongTin;  
    private DefaultTableModel tableModelThongTin; 
    private JTextField txtMaLop;
    private JTable tableHocsinh;
    private DefaultTableModel tableModelHocsinh;

    // Cấu hình kết nối cơ sở dữ liệu  
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=schoolmanage3;encrypt=true;trustServerCertificate=true";  
    private static final String USERNAME = "sa";  
    private static final String PASSWORD = "123456789";  
    private JTextField textField;

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
        setBounds(100, 100, 800, 712);  
        contentPane = new JPanel();  
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));  
        setContentPane(contentPane);  
        contentPane.setLayout(null);  

        // Tiêu đề  
        JLabel lblNewLabel = new JLabel("Tra Cứu Điểm Học Sinh");  
        lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));  
        lblNewLabel.setBounds(230, 20, 300, 30);  
        contentPane.add(lblNewLabel);  

        // Trường nhập mã học sinh  
        JLabel lblMaHocSinh = new JLabel("Mã Học Sinh:");  
        lblMaHocSinh.setBounds(20, 70, 100, 25);  
        contentPane.add(lblMaHocSinh);  

        txtMaHocSinh = new JTextField();  
        txtMaHocSinh.setBounds(120, 70, 150, 25);  
        contentPane.add(txtMaHocSinh);  

        JButton btnTraCuu = new JButton("Tra Cứu");  
        btnTraCuu.setBounds(280, 70, 100, 25);  
        contentPane.add(btnTraCuu);  

        // Bảng hiển thị thông tin học sinh  
        String[] columnNamesThongTin = {"Thông Tin", "Giá Trị"};  
        tableModelThongTin = new DefaultTableModel(columnNamesThongTin, 0);  
        tableThongTin = new JTable(tableModelThongTin);  
        JScrollPane scrollPaneThongTin = new JScrollPane(tableThongTin);  
        scrollPaneThongTin.setBounds(10, 133, 740, 103);  
        contentPane.add(scrollPaneThongTin);  

        // Bảng hiển thị thông tin điểm  
        String[] columnNamesDiem = {"Môn Học", "Điểm 15 Phút", "Điểm 1 Tiết", "Điểm Cuối Kỳ", "Điểm Trung Bình"};  
        tableModelDiem = new DefaultTableModel(columnNamesDiem, 0);  
        tableDiem = new JTable(tableModelDiem);  
        JScrollPane scrollPaneDiem = new JScrollPane(tableDiem);  
        scrollPaneDiem.setBounds(10, 247, 740, 134);  
        contentPane.add(scrollPaneDiem);  

        // Nút thoát  
        JButton btnThoat = new JButton("Thoát");  
        btnThoat.setBounds(674, 637, 100, 25);  
        btnThoat.addActionListener(e -> System.exit(0));  
        contentPane.add(btnThoat);  

        // Action listener cho nút tra cứu  
        btnTraCuu.addActionListener(e -> {  
            String maHocSinh = txtMaHocSinh.getText();  
            if (maHocSinh.isEmpty()) {  
                JOptionPane.showMessageDialog(contentPane, "Vui lòng nhập mã học sinh!", "Thông báo", JOptionPane.WARNING_MESSAGE);  
                
                JLabel lblNewLabel_1 = new JLabel("Mã lớp");
                lblNewLabel_1.setBounds(26, 430, 46, 14);
                contentPane.add(lblNewLabel_1);
                
                textField = new JTextField();
                textField.setBounds(120, 427, 150, 20);
                contentPane.add(textField);
                textField.setColumns(10);
                
                JButton btnTraCuu_1 = new JButton("Tra Cứu");
                btnTraCuu_1.setBounds(280, 426, 100, 25);
                contentPane.add(btnTraCuu_1);
            } else {  
                layThongTinHocsinh(maHocSinh);  
                layThongTinDiem(maHocSinh);  
            }  
        });
     // Trong constructor, khởi tạo các thành phần giao diện:
        JLabel lblMaLop = new JLabel("Mã Lớp:");
        lblMaLop.setBounds(20, 430, 100, 25);
        contentPane.add(lblMaLop);

        txtMaLop = new JTextField();
        txtMaLop.setBounds(120, 430, 150, 25);
        contentPane.add(txtMaLop);

        JButton btnTraCuuMaLop = new JButton("Tra Cứu Lớp");
        btnTraCuuMaLop.setBounds(280, 430, 150, 25);
        contentPane.add(btnTraCuuMaLop);

        // Bảng danh sách học sinh
        String[] columnNamesHocsinh = {"Mã Học Sinh", "Họ Tên", "Ngày Sinh", "Giới Tính"};
        tableModelHocsinh = new DefaultTableModel(columnNamesHocsinh, 0);

        tableHocsinh = new JTable(tableModelHocsinh);
        JScrollPane scrollPaneHocsinh = new JScrollPane(tableHocsinh);
        scrollPaneHocsinh.setBounds(10, 470, 740, 150);
        contentPane.add(scrollPaneHocsinh);
        
        JScrollPane scrollPaneThongTin_1 = new JScrollPane((Component) null);
        scrollPaneHocsinh.setColumnHeaderView(scrollPaneThongTin_1);

        // Action listener cho nút tra cứu lớp
        btnTraCuuMaLop.addActionListener(e -> {
            String maLop = txtMaLop.getText();
            if (maLop.isEmpty()) {
                JOptionPane.showMessageDialog(contentPane, "Vui lòng nhập mã lớp!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                
                JLabel lblTraCuLp = new JLabel("Tra Cứu Lớp");
                lblTraCuLp.setFont(new Font("Times New Roman", Font.BOLD, 20));
                lblTraCuLp.setBounds(279, 392, 300, 30);
                contentPane.add(lblTraCuLp);
            } else {
                layThongTinHocsinhTheoMaLop(maLop);
            }
        });
    }  

    private void layThongTinHocsinh(String maHocSinh) {  
        // Xóa thông tin học sinh hiện tại  
        tableModelThongTin.setRowCount(0);  

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);  
             PreparedStatement stmt = conn.prepareStatement("SELECT HoTen, GioiTinh, NgaySinh, LOP.MaLop, TenLop " +  
                     "FROM Hocsinh LEFT JOIN LOP ON Hocsinh.MaLop = LOP.MaLop WHERE Hocsinh.MaHocSinh = ?")) {  
            stmt.setString(1, maHocSinh);  
            ResultSet rs = stmt.executeQuery();  

            if (rs.next()) {  
                String hoTen = rs.getString("HoTen");  
                String gioiTinh = rs.getString("GioiTinh");  
                Date ngaySinh = rs.getDate("NgaySinh"); // Lấy ngày sinh  
                String tenLop = rs.getString("TenLop");  
                String maLop = rs.getString("MaLop"); // Lấy mã lớp  

                // Thêm thông tin học sinh vào bảng  
                tableModelThongTin.addRow(new Object[]{"Tên", hoTen});  
                tableModelThongTin.addRow(new Object[]{"Giới Tính", gioiTinh});  
                tableModelThongTin.addRow(new Object[]{"Ngày Sinh", ngaySinh != null ? ngaySinh.toString() : "Không xác định"}); // Kiểm tra null cho ngày sinh  
                tableModelThongTin.addRow(new Object[]{"Tên Lớp", tenLop});  

                // Chỉ hiển thị mã lớp nếu có thông tin về lớp  
                if (maLop != null) {  
                    tableModelThongTin.addRow(new Object[]{"Mã Lớp", maLop});  
                }  
            } else {  
                JOptionPane.showMessageDialog(contentPane, "Không tìm thấy thông tin cho mã học sinh này!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);  
            }  
        } catch (SQLException e) {  
            e.printStackTrace();  
            JOptionPane.showMessageDialog(contentPane, "Lỗi kết nối cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);  
        }  
    } 

    private void layThongTinDiem(String maHocSinh) {  
        // Xóa hết dòng hiện tại trong bảng điểm  
        tableModelDiem.setRowCount(0);  

        // Kết nối đến cơ sở dữ liệu và lấy thông tin điểm  
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);  
             PreparedStatement stmt = conn.prepareStatement("SELECT MonHoc.TenMonHoc, " +  
                     "SUM(CASE WHEN ChiTietDiem.MaLoaiKiemTra = 1 THEN ChiTietDiem.DiemSo ELSE 0 END) AS Diem15Phut, " +  
                     "SUM(CASE WHEN ChiTietDiem.MaLoaiKiemTra = 2 THEN ChiTietDiem.DiemSo ELSE 0 END) AS Diem1Tiet, " +  
                     "SUM(CASE WHEN ChiTietDiem.MaLoaiKiemTra = 3 THEN ChiTietDiem.DiemSo ELSE 0 END) AS DiemCuoiKy " +  
                     "FROM ChiTietDiem " +  
                     "JOIN MonHoc ON ChiTietDiem.MaMonHoc = MonHoc.MaMonHoc " +  
                     "WHERE ChiTietDiem.MaHocSinh = ? " +  
                     "GROUP BY MonHoc.TenMonHoc")) {  
            stmt.setString(1, maHocSinh);  
            ResultSet rs = stmt.executeQuery();  

            double totalScore = 0;  
            int count = 0;  

            // Nếu có dữ liệu thì thêm vào bảng  
            while (rs.next()) {  
                String monHoc = rs.getString("TenMonHoc");  
                double diem15Phut = rs.getDouble("Diem15Phut");  
                double diem1Tiet = rs.getDouble("Diem1Tiet");  
                double diemCuoiKy = rs.getDouble("DiemCuoiKy");  

                // Tính điểm trung bình cho môn hiện tại  
                double totalForSubject = 0;  
                int validCount = 0;  

                if (diem15Phut > 0) {  
                    totalForSubject += diem15Phut;  
                    validCount++;  
                }  

                if (diem1Tiet > 0) {  
                    totalForSubject += diem1Tiet;  
                    validCount++;  
                }  

                if (diemCuoiKy > 0) {  
                    totalForSubject += diemCuoiKy;  
                    validCount++;  
                }  

                // Tính điểm trung bình cho môn học  
                double diemTrungBinhMonHoc = (validCount > 0) ? (totalForSubject / validCount) : 0;  

                // Thêm dòng vào bảng  
                tableModelDiem.addRow(new Object[]{monHoc, diem15Phut, diem1Tiet, diemCuoiKy, diemTrungBinhMonHoc});  

                // Tính tổng điểm cho các môn có dữ liệu  
                if (validCount > 0) {  
                    totalScore += diemTrungBinhMonHoc; // Cộng điểm trung bình của môn  
                    count++;  
                }  
            }  

            // Tính điểm trung bình tổng  
            String xepLoai = "";  
            if (count > 0) {  
                double diemTrungBinhTong = totalScore / count; // Tính điểm trung bình cho các môn có dữ liệu  

                // Xếp loại học sinh  
                if (diemTrungBinhTong >= 8) {  
                    xepLoai = "Giỏi";  
                } else if (diemTrungBinhTong >= 6.5) {  
                    xepLoai = "Khá";  
                } else {  
                    xepLoai = "Trung bình";  
                }  

                // Thêm dòng để hiển thị trung bình tổng và xếp loại  
                tableModelDiem.addRow(new Object[]{"Điểm Trung Bình", "", "", "", diemTrungBinhTong});  
                tableModelDiem.addRow(new Object[]{"Xếp Loại", "", "", "", xepLoai}); // Thêm dòng xếp loại  
            }  

        } catch (SQLException e) {  
            e.printStackTrace();  
            JOptionPane.showMessageDialog(contentPane, "Lỗi kết nối cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);  
        }  
    }
    private void layThongTinHocsinhTheoMaLop(String maLop) {
        // Xóa thông tin hiện tại
        tableModelHocsinh.setRowCount(0);

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT L.TenLop, H.MaHocSinh, H.HoTen, H.NgaySinh, H.GioiTinh " +
                     "FROM HOCSINH H " +
                     "JOIN LOP L ON H.MaLop = L.MaLop " +
                     "WHERE L.MaLop = ?")) {
            stmt.setString(1, maLop);
            ResultSet rs = stmt.executeQuery();

            String tenLop = null;
            int siSo = 0;

            while (rs.next()) {
                if (tenLop == null) {
                    tenLop = rs.getString("TenLop");
                }

                String maHocSinh = rs.getString("MaHocSinh");
                String hoTen = rs.getString("HoTen");
                Date ngaySinh = rs.getDate("NgaySinh");
                String gioiTinh = rs.getString("GioiTinh");

                // Thêm thông tin vào bảng
                tableModelHocsinh.addRow(new Object[]{
                        maHocSinh, hoTen, ngaySinh, gioiTinh
                });
                siSo++;
            }

            if (tenLop != null) {
                JOptionPane.showMessageDialog(contentPane,
                        "Lớp: " + tenLop + "\nSĩ số: " + siSo,
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(contentPane,
                        "Không tìm thấy lớp với mã: " + maLop,
                        "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(contentPane, "Lỗi kết nối cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }


}