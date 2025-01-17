package controller;  

import javax.swing.*;  
import javax.swing.table.DefaultTableModel;  
import java.sql.Connection;  
import java.sql.PreparedStatement;  
import java.sql.ResultSet;  
import java.sql.SQLException;  
import schoolmanagement.jdbcSqlServer;  

public class QuanLyDiemController {  
    private JComboBox<String> comboBoxHocSinh;  
    private JComboBox<String> comboBoxKhoi;  
    private JComboBox<String> comboBoxLop;  
    private JComboBox<String> comboBoxMon;  
    private JComboBox<String> comboBoxHocKy;  
    private JComboBox<String> comboBoxLoaiKT;  
    private JComboBox<String> comboBoxGiaoVien;  
    private JTable table;  
    private DefaultTableModel tableModel;  

    public QuanLyDiemController(  
            JComboBox<String> comboBoxHocSinh, JComboBox<String> comboBoxKhoi, JComboBox<String> comboBoxLop,  
            JComboBox<String> comboBoxMon, JComboBox<String> comboBoxHocKy, JComboBox<String> comboBoxLoaiKT,  
            JComboBox<String> comboBoxGiaoVien, JTable table, DefaultTableModel tableModel) {  
        this.comboBoxHocSinh = comboBoxHocSinh;  
        this.comboBoxKhoi = comboBoxKhoi;  
        this.comboBoxLop = comboBoxLop;  
        this.comboBoxMon = comboBoxMon;  
        this.comboBoxHocKy = comboBoxHocKy;  
        this.comboBoxLoaiKT = comboBoxLoaiKT;  
        this.comboBoxGiaoVien = comboBoxGiaoVien;  
        this.table = table;  
        this.tableModel = tableModel;  
    }  

    // Load danh sách học sinh  
    public void loadHocSinh() {  
        try (Connection connection = jdbcSqlServer.getConnection()) {  
            String sql = "SELECT * FROM HOCSINH";  
            PreparedStatement statement = connection.prepareStatement(sql);  
            ResultSet resultSet = statement.executeQuery();  

            comboBoxHocSinh.removeAllItems();  
            while (resultSet.next()) {  
                String hoTen = resultSet.getString("HoTen");  
                String maHocSinh = resultSet.getString("MaHocSinh");  
                comboBoxHocSinh.addItem(hoTen + " (" + maHocSinh + ")");  
            }  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
    }  

    // Load danh sách khối  
    public void loadKhoi() {  
        comboBoxKhoi.removeAllItems();  
        comboBoxKhoi.addItem("Khối 6");  
        comboBoxKhoi.addItem("Khối 7");  
        comboBoxKhoi.addItem("Khối 8");  
        comboBoxKhoi.addItem("Khối 9");  
    }  

    // Load danh sách lớp  
    public void loadLop() {  
        try (Connection connection = jdbcSqlServer.getConnection()) {  
            String sql = "SELECT MaLop, TenLop FROM LOP";  
            PreparedStatement statement = connection.prepareStatement(sql);  
            ResultSet resultSet = statement.executeQuery();  

            comboBoxLop.removeAllItems();  
            while (resultSet.next()) {  
                String maLop = resultSet.getString("MaLop");  
                String tenLop = resultSet.getString("TenLop");  
                comboBoxLop.addItem(maLop + " - " + tenLop);  
            }  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
    }  

    // Load danh sách môn học  
    public void loadMon() {  
        try (Connection connection = jdbcSqlServer.getConnection()) {  
            String sql = "SELECT MaMonHoc, TenMonHoc FROM MONHOC";  
            PreparedStatement statement = connection.prepareStatement(sql);  
            ResultSet resultSet = statement.executeQuery();  

            comboBoxMon.removeAllItems();  
            while (resultSet.next()) {  
                String maMonHoc = resultSet.getString("MaMonHoc");  
                String tenMonHoc = resultSet.getString("TenMonHoc");  
                comboBoxMon.addItem(maMonHoc + " - " + tenMonHoc);  
            }  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
    }  

    // Load danh sách loại kiểm tra  
    public void loadLoaiKiemTra() {  
        try (Connection connection = jdbcSqlServer.getConnection()) {  
            String sql = "SELECT MaLoaiKiemTra, TenLoaiKiemTra FROM LOAIKIEMTRA";  
            PreparedStatement statement = connection.prepareStatement(sql);  
            ResultSet resultSet = statement.executeQuery();  

            comboBoxLoaiKT.removeAllItems();  
            while (resultSet.next()) {  
                String maLoaiKT = resultSet.getString("MaLoaiKiemTra");  
                String tenLoaiKT = resultSet.getString("TenLoaiKiemTra");  
                comboBoxLoaiKT.addItem(maLoaiKT + " - " + tenLoaiKT);  
            }  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
    }  

    // Load danh sách giáo viên  
    public void loadGiaoVien() {  
        try (Connection connection = jdbcSqlServer.getConnection()) {  
            String sql = """  
                SELECT gv.MaGiaoVien, gv.HoTen, mh.TenMonHoc  
                FROM GIAOVIEN gv  
                JOIN MONHOC mh ON gv.MaMonHoc = mh.MaMonHoc  
            """;  
            PreparedStatement statement = connection.prepareStatement(sql);  
            ResultSet resultSet = statement.executeQuery();  

            comboBoxGiaoVien.removeAllItems();  
            while (resultSet.next()) {  
                String maGiaoVien = resultSet.getString("MaGiaoVien");  
                String hoTen = resultSet.getString("HoTen");  
                String tenMonHoc = resultSet.getString("TenMonHoc");  
                comboBoxGiaoVien.addItem(hoTen + " - " + tenMonHoc + " (" + maGiaoVien + ")");  
            }  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
    }  

    // Thêm điểm  
    public void addScore(String diem) {  
        String maHocSinh = comboBoxHocSinh.getSelectedItem().toString().split(" \\(")[1].replace(")", "");  
        String maMonHoc = comboBoxMon.getSelectedItem().toString().split(" - ")[0];  
        String maLoaiKT = comboBoxLoaiKT.getSelectedItem().toString().split(" - ")[0];  
        String maGiaoVien = comboBoxGiaoVien.getSelectedItem().toString().split(" \\(")[1].replace(")", "");  

        // Validate điểm nhập vào  
        if (diem.isEmpty()) {  
            JOptionPane.showMessageDialog(null, "Vui lòng nhập điểm!", "Thông báo", JOptionPane.WARNING_MESSAGE);  
            return;  
        }  

        try {  
            float diemSo = Float.parseFloat(diem);  
            if (diemSo < 0 || diemSo > 10) {  
                JOptionPane.showMessageDialog(null, "Điểm phải nằm trong khoảng 0 đến 10!", "Lỗi", JOptionPane.ERROR_MESSAGE);  
                return;  
            }  

            // Thêm điểm vào cơ sở dữ liệu  
            try (Connection connection = jdbcSqlServer.getConnection()) {  
                String sqlMaxId = "SELECT ISNULL(MAX(MaChiTietDiem), 0) + 1 AS NextId FROM CHITIETDIEM";  
                PreparedStatement stmtMaxId = connection.prepareStatement(sqlMaxId);  
                ResultSet rs = stmtMaxId.executeQuery();  
                int maChiTietDiem = rs.next() ? rs.getInt("NextId") : 1;  

                String sqlInsert = """  
                    INSERT INTO CHITIETDIEM (MaChiTietDiem, MaHocSinh, MaMonHoc, MaLoaiKiemTra, MaGiaoVien, DiemSo)  
                    VALUES (?, ?, ?, ?, ?, ?)  
                """;  
                PreparedStatement stmt = connection.prepareStatement(sqlInsert);  
                stmt.setInt(1, maChiTietDiem);  
                stmt.setString(2, maHocSinh);  
                stmt.setString(3, maMonHoc);  
                stmt.setString(4, maLoaiKT);  
                stmt.setString(5, maGiaoVien);  
                stmt.setFloat(6, diemSo);  

                stmt.executeUpdate();  
                JOptionPane.showMessageDialog(null, "Thêm điểm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);  
                loadScores();  
            }  
        } catch (NumberFormatException e) {  
            JOptionPane.showMessageDialog(null, "Điểm phải là một số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);  
        } catch (SQLException e) {  
            e.printStackTrace();  
            JOptionPane.showMessageDialog(null, "Lỗi khi thêm điểm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);  
        }  
    }  

    // Load bảng điểm  
    public void loadScores() {  
        try (Connection connection = jdbcSqlServer.getConnection()) {  
            String sql = "SELECT * FROM CHITIETDIEM";  
            PreparedStatement statement = connection.prepareStatement(sql);  
            ResultSet resultSet = statement.executeQuery();  

            tableModel.setRowCount(0); // Clear the current table data  
            while (resultSet.next()) {  
                Object[] row = {  
                    resultSet.getInt("MaChiTietDiem"),  
                    resultSet.getString("MaHocSinh"),  
                    resultSet.getString("MaMonHoc"),  
                    resultSet.getString("MaLoaiKiemTra"),  
                    resultSet.getString("MaGiaoVien"),  
                    resultSet.getFloat("DiemSo")  
                };  
                tableModel.addRow(row);  
            }  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
    }  

    // Xóa điểm  
    public void deleteScore(int maChiTietDiem) {  
        try (Connection connection = jdbcSqlServer.getConnection()) {  
            String sqlDelete = "DELETE FROM CHITIETDIEM WHERE MaChiTietDiem = ?";  
            PreparedStatement deleteStmt = connection.prepareStatement(sqlDelete);  
            deleteStmt.setInt(1, maChiTietDiem);  

            int rowsAffected = deleteStmt.executeUpdate();  
            if (rowsAffected > 0) {  
                JOptionPane.showMessageDialog(null, "Xóa điểm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);  
                loadScores();  
            } else {  
                JOptionPane.showMessageDialog(null, "Không tìm thấy điểm cần xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);  
            }  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
    }  
}