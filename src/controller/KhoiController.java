package controller;

import model.Khoi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KhoiController {
    private List<Khoi> khoiList;

    // Thông tin kết nối cơ sở dữ liệu
    private final String url = "jdbc:sqlserver://localhost:1433;databaseName=schoolmanage3;encrypt=true;trustServerCertificate=true";
    private final String username = "sa";
    private final String password = "123456789";

    public KhoiController() {
        this.khoiList = new ArrayList<>();
        loadKhoiList(); // Tải danh sách khối từ cơ sở dữ liệu khi khởi động
    }

    // Hàm thêm khối
    public void addKhoi(String maKhoi, String tenKhoi, String soLopToiDaStr, JTable table) {
        int soLopToiDa;
        try {
            soLopToiDa = Integer.parseInt(soLopToiDaStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập số lớp tối đa hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Khoi khoi = new Khoi(maKhoi, tenKhoi, soLopToiDa);
        khoiList.add(khoi);

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = connection.prepareStatement("INSERT INTO KHOI (MaKhoi, TenKhoi, SoLopToiDa) VALUES (?, ?, ?)")) {
            pstmt.setString(1, maKhoi);
            pstmt.setString(2, tenKhoi);
            pstmt.setInt(3, soLopToiDa);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Thêm khối thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi thêm khối vào cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        displayKhoiList(table); // Cập nhật danh sách khối trên bảng
    }

    // Hàm xóa khối
    public void deleteKhoi(String maKhoi, JTable table) {
        khoiList.removeIf(khoi -> khoi.getMaKhoi().equals(maKhoi));

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = connection.prepareStatement("DELETE FROM KHOI WHERE MaKhoi = ?")) {
            pstmt.setString(1, maKhoi);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Xóa khối thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi xóa khối khỏi cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        displayKhoiList(table); // Cập nhật danh sách khối trên bảng
    }

    // Hàm cập nhật khối
    public void updateKhoi(String maKhoi, String tenKhoi, String soLopToiDaStr, JTable table) {
        int soLopToiDa;
        try {
            soLopToiDa = Integer.parseInt(soLopToiDaStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập số lớp tối đa hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = connection.prepareStatement("UPDATE KHOI SET TenKhoi = ?, SoLopToiDa = ? WHERE MaKhoi = ?")) {
            pstmt.setString(1, tenKhoi);
            pstmt.setInt(2, soLopToiDa);
            pstmt.setString(3, maKhoi);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cập nhật khối thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi cập nhật khối trong cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        displayKhoiList(table); // Cập nhật danh sách khối trên bảng
    }

    // Hiển thị danh sách khối trong bảng
    public void displayKhoiList(JTable table) {
        DefaultTableModel model = new DefaultTableModel(new String[]{"Mã Khối", "Tên Khối", "Số Lớp Tối Đa"}, 0);
        for (Khoi khoi : khoiList) {
            model.addRow(new Object[]{khoi.getMaKhoi(), khoi.getTenKhoi(), khoi.getSoLopToiDa()});
        }
        table.setModel(model);
    }

    // Tải danh sách khối từ cơ sở dữ liệu
    private void loadKhoiList() {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM KHOI");
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Khoi khoi = new Khoi(
                        rs.getString("MaKhoi"),
                        rs.getString("TenKhoi"),
                        rs.getInt("SoLopToiDa")
                );
                khoiList.add(khoi);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi tải danh sách khối từ cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Getter cho danh sách khối
    public List<Khoi> getKhoiList() {
        return khoiList;
    }
    public List<Khoi> getAllKhoi() {
        return khoiList;
    }

}
