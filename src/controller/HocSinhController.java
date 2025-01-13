package controller;  

import model.HocSinh;  
import view.JFHocSinh;  

import javax.swing.*;  
import javax.swing.table.DefaultTableModel;  
import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;  
import java.sql.ResultSet;  
import java.sql.SQLException;  
import java.sql.Date;  
import java.awt.event.ActionEvent;  
import java.awt.event.ActionListener;  
import java.util.ArrayList;  
import java.util.List;  

public class HocSinhController {  
    private JFHocSinh frame;  
    private List<HocSinh> hocSinhList; // Danh sách học sinh để quản lý  

    private String url = "jdbc:sqlserver://localhost:1433;databaseName=schoolmanage3;encrypt=true;trustServerCertificate=true";  
    private String username = "sa";  
    private String password = "123456789";  

    public HocSinhController(JFHocSinh frame) {  
        this.frame = frame;  
        this.hocSinhList = new ArrayList<>(); // Khởi tạo danh sách học sinh  
        initController();  
        loadHocSinhList(); // Tải danh sách học sinh từ cơ sở dữ liệu khi khởi động  
    }  

    private void initController() {  
        // Action listener cho nút thêm học sinh  
        frame.getBtnThem().addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  
                addHocSinh();  
            }  
        });  

        // Action listener cho nút xóa học sinh  
        frame.getBtnXoa().addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  
                deleteHocSinh();  
            }  
        });  

        // Action listener cho nút hiển thị danh sách học sinh  
        frame.getBtnDanhSach().addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  
                displayHocSinhList();  
            }  
        });  
    }  

    private void addHocSinh() {  
        String maHocSinh = frame.getTxtMaHocSinh().getText();  
        String hoTen = frame.getTxtHoTen().getText();  
        String gioiTinh = (String) frame.getComboBoxGioiTinh().getSelectedItem();  
        Date ngaySinh = Date.valueOf(frame.getTxtNgaySinh().getText());  
        String diaChi = frame.getTxtDiaChi().getText();  
        String email = frame.getTxtEmail().getText();  
        String maLop = ""; // Bạn có thể thêm input này từ người dùng nếu cần  
        String nienKhoa = frame.getTxtNienKhoa().getText();  

        HocSinh hocSinh = new HocSinh(maHocSinh, hoTen, gioiTinh, ngaySinh, diaChi, email, maLop, nienKhoa);  
        hocSinhList.add(hocSinh); // Thêm vào danh sách học sinh  

        try (Connection connection = DriverManager.getConnection(url, username, password);  
             PreparedStatement pstmt = connection.prepareStatement("INSERT INTO HOCSINH (MaHocSinh, HoTen, GioiTinh, NgaySinh, DiaChi, Email, NienKhoa) VALUES (?, ?, ?, ?, ?, ?, ?)")) {  
            pstmt.setString(1, maHocSinh);  
            pstmt.setString(2, hoTen);  
            pstmt.setString(3, gioiTinh);  
            pstmt.setDate(4, ngaySinh);  
            pstmt.setString(5, diaChi);  
            pstmt.setString(6, email);  
            pstmt.setString(7, nienKhoa);  
            pstmt.executeUpdate();  
            JOptionPane.showMessageDialog(frame, "Thêm học sinh thành công!");  
        } catch (SQLException e) {  
            e.printStackTrace();  
            JOptionPane.showMessageDialog(frame, "Lỗi thêm học sinh vào cơ sở dữ liệu.");  
        }  

        clearInputFields();  
    }  

    private void deleteHocSinh() {  
        String maHocSinh = frame.getTxtMaHocSinh().getText();  
        hocSinhList.removeIf(hs -> hs.getMaHocSinh().equals(maHocSinh)); // Xóa học sinh theo mã số  

        try (Connection connection = DriverManager.getConnection(url, username, password);  
             PreparedStatement pstmt = connection.prepareStatement("DELETE FROM HOCSINH WHERE MaHocSinh = ?")) {  
            pstmt.setString(1, maHocSinh);  
            pstmt.executeUpdate();  
            JOptionPane.showMessageDialog(frame, "Xóa học sinh thành công!");  
        } catch (SQLException e) {  
            e.printStackTrace();  
            JOptionPane.showMessageDialog(frame, "Lỗi xóa học sinh khỏi cơ sở dữ liệu.");  
        }  

        clearInputFields();  
    }  

    private void displayHocSinhList() {  
        DefaultTableModel model = new DefaultTableModel(new String[]{"Mã Học Sinh", "Họ Tên", "Giới Tính", "Ngày Sinh", "Địa Chỉ", "Email", "Niên Khóa"}, 0);  
        for (HocSinh hs : hocSinhList) {  
            model.addRow(new Object[]{hs.getMaHocSinh(), hs.getHoTen(), hs.getGioiTinh(), hs.getNgaySinh(), hs.getDiaChi(), hs.getEmail(), hs.getNienKhoa()});  
        }  
        frame.getTable().setModel(model);  
    }  

    private void clearInputFields() {  
        frame.getTxtMaHocSinh().setText("");  
        frame.getTxtHoTen().setText("");  
        frame.getTxtNgaySinh().setText("");  
        frame.getTxtDiaChi().setText("");  
        frame.getTxtEmail().setText("");  
        frame.getComboBoxGioiTinh().setSelectedIndex(0);  
        frame.getTxtNienKhoa().setText(""); // Clear Niên Khóa input  
    }  

    private void loadHocSinhList() {  
        try (Connection connection = DriverManager.getConnection(url, username, password);  
             PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM HOCSINH");  
             ResultSet rs = pstmt.executeQuery()) {  
            while (rs.next()) {  
                HocSinh hocSinh = new HocSinh(  
                    rs.getString("MaHocSinh"),  
                    rs.getString("HoTen"),  
                    rs.getString("GioiTinh"),  
                    rs.getDate("NgaySinh"),  
                    rs.getString("DiaChi"),  
                    rs.getString("Email"),  
                    "", // Thêm căn cứ nếu cần  
                    rs.getString("NienKhoa")  
                );  
                hocSinhList.add(hocSinh);  
            }  
        } catch (SQLException e) {  
            e.printStackTrace();  
            JOptionPane.showMessageDialog(frame, "Lỗi tải danh sách học sinh từ cơ sở dữ liệu.");  
        }  
    }  
}