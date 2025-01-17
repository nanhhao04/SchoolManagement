package controller;  

import view.JFHocSinh;  
import model.HocSinh;  // Giả định bạn có lớp mô hình HocSinh  
import java.sql.*;  
import javax.swing.*;  
import java.util.ArrayList;  
import schoolmanagement.jdbcSqlServer; // Đường dẫn đến lớp kết nối cơ sở dữ liệu của bạn  

public class HocSinhController {  
    private JFHocSinh view;  // Giao diện  
    private ArrayList<HocSinh> studentList;  // Danh sách học sinh  

    public HocSinhController(JFHocSinh view) {  
        this.view = view;  
        this.studentList = new ArrayList<>();  
    }  

    // Tải danh sách học sinh từ cơ sở dữ liệu  
    public void loadHocSinhList() {  
        try (Connection connection = jdbcSqlServer.getConnection()) {   
            String sqlSelect = "SELECT * FROM HOCSINH";  // Tùy chỉnh tên bảng  
            PreparedStatement statement = connection.prepareStatement(sqlSelect);  
            ResultSet resultSet = statement.executeQuery();  

            studentList.clear();  // Xóa danh sách hiện tại  
            while (resultSet.next()) {  
                HocSinh hocSinh = new HocSinh(  
                    resultSet.getString("MaHocSinh"),  
                    resultSet.getString("HoTen"),  
                    resultSet.getString("GioiTinh"),  
                    resultSet.getDate("NgaySinh"),  
                    resultSet.getString("DiaChi"),  
                    resultSet.getString("Email"),  
                    resultSet.getString("NienKhoa"),  
                    resultSet.getString("MaLop")  // Lấy mã lớp  
                );  
                studentList.add(hocSinh);  
            }  
            // Chuyển danh sách đến giao diện  
            updateStudentTable();  
        } catch (SQLException ex) {  
            JOptionPane.showMessageDialog(view, "Lỗi khi lấy danh sách học sinh: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);  
        }  
    }  

    // Phương thức để thêm học sinh  
    public void addHocSinh() {  
        // Lấy dữ liệu từ giao diện  
        String maHocSinh = view.getTxtMaHocSinh().getText().trim();  
        String hoTen = view.getTxtHoTen().getText().trim();  
        String gioiTinh = view.getComboBoxGioiTinh().getSelectedItem().toString();  
        String ngaySinhStr = view.getTxtNgaySinh().getText().trim();  
        String diaChi = view.getTxtDiaChi().getText().trim();  
        String email = view.getTxtEmail().getText().trim();  
        String nienKhoa = view.getTxtNienKhoa().getText().trim();  

        // Kiểm tra đầu vào  
        if (maHocSinh.isEmpty() || hoTen.isEmpty() || ngaySinhStr.isEmpty() || email.isEmpty() || diaChi.isEmpty() || nienKhoa.isEmpty()) {  
            JOptionPane.showMessageDialog(view, "Vui lòng điền đầy đủ thông tin.", "Thông báo", JOptionPane.WARNING_MESSAGE);  
            return;  
        }  

        try (Connection connection = jdbcSqlServer.getConnection()) {  
            String sqlInsert = "INSERT INTO HOCSINH (MaHocSinh, HoTen, GioiTinh, NgaySinh, DiaChi, Email, NienKhoa) VALUES (?, ?, ?, ?, ?, ?, ?)";  
            PreparedStatement statement = connection.prepareStatement(sqlInsert);  
            statement.setString(1, maHocSinh);  
            statement.setString(2, hoTen);  
            statement.setString(3, gioiTinh);  
            statement.setDate(4, java.sql.Date.valueOf(ngaySinhStr)); // Format yyyy-MM-dd  
            statement.setString(5, diaChi);  
            statement.setString(6, email);  
            statement.setString(7, nienKhoa);  
            statement.executeUpdate();  

            // Tải lại danh sách sau khi thêm  
            loadHocSinhList();  
            clearInputs();  
        } catch (SQLException ex) {  
            JOptionPane.showMessageDialog(view, "Lỗi khi thêm học sinh: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);  
        }  
    }  

    // Phương thức cập nhật học sinh  
    public void updateHocSinh() {  
        String maHocSinh = view.getTxtMaHocSinh().getText().trim();  
        String hoTen = view.getTxtHoTen().getText().trim();  
        String gioiTinh = view.getComboBoxGioiTinh().getSelectedItem().toString();  
        String ngaySinhStr = view.getTxtNgaySinh().getText().trim();  
        String diaChi = view.getTxtDiaChi().getText().trim();  
        String email = view.getTxtEmail().getText().trim();  
        String nienKhoa = view.getTxtNienKhoa().getText().trim();  

        // Kiểm tra đầu vào  
        if (maHocSinh.isEmpty() || hoTen.isEmpty() || ngaySinhStr.isEmpty()) {  
            JOptionPane.showMessageDialog(view, "Vui lòng điền đầy đủ thông tin.", "Thông báo", JOptionPane.WARNING_MESSAGE);  
            return;  
        }  

        try (Connection connection = jdbcSqlServer.getConnection()) {  
            String sqlUpdate = "UPDATE HOCSINH SET HoTen=?, GioiTinh=?, NgaySinh=?, DiaChi=?, Email=?, NienKhoa=? WHERE MaHocSinh=?";  
            PreparedStatement statement = connection.prepareStatement(sqlUpdate);  
            statement.setString(1, hoTen);  
            statement.setString(2, gioiTinh);  
            statement.setDate(3, java.sql.Date.valueOf(ngaySinhStr)); // Format yyyy-MM-dd  
            statement.setString(4, diaChi);  
            statement.setString(5, email);  
            statement.setString(6, nienKhoa);  
            statement.setString(7, maHocSinh);  
            statement.executeUpdate();  

            // Tải lại danh sách sau khi cập nhật  
            loadHocSinhList();  
            clearInputs();  
        } catch (SQLException ex) {  
            JOptionPane.showMessageDialog(view, "Lỗi khi cập nhật học sinh: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);  
        }  
    }  

    // Phương thức xóa học sinh  
    public void deleteHocSinh() {  
        String maHocSinh = view.getTxtMaHocSinh().getText().trim();  

        // Kiểm tra đầu vào  
        if (maHocSinh.isEmpty()) {  
            JOptionPane.showMessageDialog(view, "Vui lòng nhập mã học sinh cần xóa.", "Thông báo", JOptionPane.WARNING_MESSAGE);  
            return;  
        }  

        try (Connection connection = jdbcSqlServer.getConnection()) {  
            String sqlDelete = "DELETE FROM HOCSINH WHERE MaHocSinh=?";  
            PreparedStatement statement = connection.prepareStatement(sqlDelete);  
            statement.setString(1, maHocSinh);  
            statement.executeUpdate();  

            // Tải lại danh sách sau khi xóa  
            loadHocSinhList();
            clearInputs();  
        } catch (SQLException ex) {  
            JOptionPane.showMessageDialog(view, "Lỗi khi xóa học sinh: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);  
        }  
    }  

    // Cập nhật bảng học sinh trong giao diện  
    private void updateStudentTable() {  
        // Cập nhật bảng trong giao diện với danh sách học sinh  
        view.updateStudentTable(studentList);  
    }  

    // Xóa dữ liệu trong các trường nhập liệu  
    private void clearInputs() {  
        view.getTxtMaHocSinh().setText("");  
        view.getTxtHoTen().setText("");  
        view.getComboBoxGioiTinh().setSelectedIndex(0); // Giả định có một giá trị mặc định  
        view.getTxtNgaySinh().setText("");  
        view.getTxtDiaChi().setText("");  
        view.getTxtEmail().setText("");  
        view.getTxtNienKhoa().setText("");  
    }  
}