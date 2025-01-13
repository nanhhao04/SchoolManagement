package controller;  

import schoolmanagement.jdbcSqlServer;  
import view.JFQuanLyDiem;  

import javax.swing.*;  
import javax.swing.table.DefaultTableModel;  
import java.sql.Connection;  
import java.sql.PreparedStatement;  
import java.sql.ResultSet;  
import java.sql.SQLException;  

public class ChiTietDiemController {  
    private JFQuanLyDiem view;  

    public ChiTietDiemController(JFQuanLyDiem view) {  
        this.view = view;  
        initialize();  
        loadHocSinh(); // Tải danh sách học sinh khi khởi động  
    }  

    private void initialize() {  
        view.getComboBoxHocSinh().addActionListener(e -> loadDiem(view.getComboBoxHocSinh().getSelectedItem().toString()));  
        view.getTable().addMouseListener(new java.awt.event.MouseAdapter() {  
            public void mouseClicked(java.awt.event.MouseEvent evt) {  
                fillDetailsFromTable();  
            }  
        });  

        // Đăng ký action listeners cho các nút  
        view.getBtnThemDiem().addActionListener(e -> addDiem());  
        view.getBtnXoaDiem().addActionListener(e -> deleteDiem());  
        view.getBtnHienToanBoDiem().addActionListener(e -> loadDiem(view.getComboBoxHocSinh().getSelectedItem().toString()));  
    }  

    private void loadHocSinh() {  
        try (Connection connection = jdbcSqlServer.getConnection()) {  
            String sql = "SELECT HoTen FROM HOCSINH"; // Chỉ truy vấn dữ liệu từ bảng HOCSINH  
            PreparedStatement statement = connection.prepareStatement(sql);  
            ResultSet resultSet = statement.executeQuery();  
            while (resultSet.next()) {  
                String hoTen = resultSet.getString("HoTen");  
                view.getComboBoxHocSinh().addItem(hoTen); // Thêm tên học sinh vào ComboBox  
            }  
        } catch (SQLException e) {  
            JOptionPane.showMessageDialog(view.getContentPane(), "Lỗi khi tải danh sách học sinh: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);  
        }  
    }  

    private void loadDiem(String tenHocSinh) {  
        DefaultTableModel tableModel = view.getTableModel();  
        tableModel.setRowCount(0); // Xóa các hàng hiện tại  

        try (Connection connection = jdbcSqlServer.getConnection()) {  
            String sql = "SELECT khoi, lop, mon, hocKy, loaiKT, DiemSo FROM DIEMMON WHERE MaHocSinh = (SELECT MaHocSinh FROM HOCSINH WHERE HoTen = ?)";  
            PreparedStatement statement = connection.prepareStatement(sql);  
            statement.setString(1, tenHocSinh);  
            ResultSet resultSet = statement.executeQuery();  

            while (resultSet.next()) {  
                tableModel.addRow(new Object[]{  
                        tenHocSinh,  
                        resultSet.getString("khoi"),  
                        resultSet.getString("lop"),  
                        resultSet.getString("mon"),  
                        resultSet.getString("hocKy"),  
                        resultSet.getString("loaiKT"),  
                        resultSet.getDouble("DiemSo")  
                });  
            }  
        } catch (SQLException e) {  
            JOptionPane.showMessageDialog(view.getContentPane(), "Lỗi khi tải điểm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);  
        }  
    }  

    private void addDiem() {  
        String tenHocSinh = view.getComboBoxHocSinh().getSelectedItem().toString().split(" \\(")[0]; // Lấy tên học sinh  
        String khoi = view.getComboBoxKhoi().getSelectedItem().toString();  
        String lop = view.getComboBoxLop().getSelectedItem().toString();  
        String mon = view.getComboBoxMon().getSelectedItem().toString();  
        String hocKy = view.getComboBoxHocKy().getSelectedItem().toString();  
        String loaiKT = view.getComboBoxLoaiKT().getSelectedItem().toString();  
        String diem = view.getTxtDiem().getText().trim();  

        if (diem.isEmpty()) {  
            JOptionPane.showMessageDialog(view.getContentPane(), "Vui lòng nhập điểm!", "Lỗi", JOptionPane.ERROR_MESSAGE);  
            return;  
        }  

        try (Connection connection = jdbcSqlServer.getConnection()) {  
            String sql = "INSERT INTO DIEMMON (MaMonHoc, MaHocKy, MaHocSinh, MaLoaiKiemTra, DiemSo) VALUES (?, ?, (SELECT MaHocSinh FROM HOCSINH WHERE HoTen = ?), ?, ?)";  
            PreparedStatement statement = connection.prepareStatement(sql);  
            statement.setString(1, mon);  
            statement.setString(2, hocKy);  
            statement.setString(3, tenHocSinh);  
            statement.setString(4, loaiKT);  
            statement.setDouble(5, Double.parseDouble(diem));  

            int rowsAffected = statement.executeUpdate();  
            if (rowsAffected > 0) {  
                // Cập nhật bảng điểm trong UI  
                view.getTableModel().addRow(new Object[]{tenHocSinh, khoi, lop, mon, hocKy, loaiKT, diem});  
                view.getTxtDiem().setText(""); // Xóa trường nhập điểm  
                JOptionPane.showMessageDialog(view.getContentPane(), "Thêm điểm thành công!");  
            } else {  
                JOptionPane.showMessageDialog(view.getContentPane(), "Không thể thêm điểm.", "Lỗi", JOptionPane.ERROR_MESSAGE);  
            }  
        } catch (SQLException e) {  
            JOptionPane.showMessageDialog(view.getContentPane(), "Lỗi khi thêm điểm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);  
        } catch (NumberFormatException e) {  
            JOptionPane.showMessageDialog(view.getContentPane(), "Điểm phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);  
        }  
    }  

    private void deleteDiem() {  
        int selectedRow = view.getTable().getSelectedRow();  
        if (selectedRow != -1) {  
            String tenHocSinh = view.getComboBoxHocSinh().getSelectedItem().toString().split(" \\(")[0]; // Lấy tên học sinh  
            String mon = view.getTableModel().getValueAt(selectedRow, 3).toString();  
            String hocKy = view.getTableModel().getValueAt(selectedRow, 4).toString();  
            String loaiKT = view.getTableModel().getValueAt(selectedRow, 5).toString();  
            double diem = Double.parseDouble(view.getTableModel().getValueAt(selectedRow, 6).toString());  

            try (Connection connection = jdbcSqlServer.getConnection()) {  
                String sql = "DELETE FROM DIEMMON WHERE MaMonHoc = ? AND MaHocKy = ? AND MaHocSinh = (SELECT MaHocSinh FROM HOCSINH WHERE HoTen = ?) AND MaLoaiKiemTra = ? AND DiemSo = ?";  
                PreparedStatement statement = connection.prepareStatement(sql);  
                statement.setString(1, mon);  
                statement.setString(2, hocKy);  
                statement.setString(3, tenHocSinh); // Assuming this is the actual ID used  
                statement.setString(4, loaiKT);  
                statement.setDouble(5, diem);  
                statement.executeUpdate();  

                // Cập nhật bảng điểm trong UI  
                view.getTableModel().removeRow(selectedRow);  
                JOptionPane.showMessageDialog(view.getContentPane(), "Xóa điểm thành công!");  
            } catch (SQLException e) {  
                JOptionPane.showMessageDialog(view.getContentPane(), "Lỗi khi xóa điểm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);  
            }  
        } else {  
            JOptionPane.showMessageDialog(view.getContentPane(), "Vui lòng chọn một hàng để xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);  
        }  
    }  

    private void fillDetailsFromTable() {  
        int selectedRow = view.getTable().getSelectedRow();  
        if (selectedRow != -1) {  
            // Lấy thông tin từ hàng được chọn và điền vào các trường nhập liệu  
            view.getComboBoxHocSinh().setSelectedItem(view.getTableModel().getValueAt(selectedRow, 0));  
            view.getComboBoxKhoi().setSelectedItem(view.getTableModel().getValueAt(selectedRow, 1));  
            view.getComboBoxLop().setSelectedItem(view.getTableModel().getValueAt(selectedRow, 2));  
            view.getComboBoxMon().setSelectedItem(view.getTableModel().getValueAt(selectedRow, 3));  
            view.getComboBoxHocKy().setSelectedItem(view.getTableModel().getValueAt(selectedRow, 4));  
            view.getComboBoxLoaiKT().setSelectedItem(view.getTableModel().getValueAt(selectedRow, 5));  
            view.getTxtDiem().setText(view.getTableModel().getValueAt(selectedRow, 6).toString());  
        }  
    }  
}