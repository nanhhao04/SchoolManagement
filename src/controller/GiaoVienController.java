package controller;  

import model.GiaoVien;  
import view.JFGiaoVien;  
import javax.swing.*;  
import java.sql.Connection;  
import java.sql.PreparedStatement;  
import java.sql.ResultSet;  
import java.sql.SQLException;  
import java.text.ParseException;  
import java.text.SimpleDateFormat;  
import java.util.ArrayList;  
import java.util.Date;  
import java.util.List;  

import schoolmanagement.jdbcSqlServer;  

public class GiaoVienController {  
    private JFGiaoVien view;  
    private List<GiaoVien> teacherList = new ArrayList<>(); // Danh sách giáo viên  

    public GiaoVienController(JFGiaoVien view) {  
        this.view = view;  
        loadGiaoVienList(); // Tải danh sách giáo viên khi khởi tạo controller  
    }  

    // Phương thức thêm giáo viên  
    public void addGiaoVien() {  
        String maGiaoVien = view.getTxtMaGiaoVien().getText();  
        String hoTen = view.getTxtHoTen().getText();  
        String ngaySinhStr = view.getTxtNgaySinh().getText();  
        String gioiTinh = (String) view.getComboBoxGioiTinh().getSelectedItem();  
        String email = view.getTxtEmail().getText();  
        String maMonHoc = view.getTxtMaMonHoc().getText();  

        if (maGiaoVien.isEmpty() || hoTen.isEmpty() || ngaySinhStr.isEmpty()) {  
            JOptionPane.showMessageDialog(view, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);  
            return;  
        }  

        // Chuyển đổi chuỗi thành đối tượng Date  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Định dạng ngày cần thiết  
        Date ngaySinh;  
        try {  
            ngaySinh = sdf.parse(ngaySinhStr);  
        } catch (ParseException e) {  
            JOptionPane.showMessageDialog(view, "Ngày sinh không hợp lệ. Vui lòng sử dụng định dạng yyyy-MM-dd!", "Lỗi", JOptionPane.ERROR_MESSAGE);  
            return;  
        }  

        try (Connection connection = jdbcSqlServer.getConnection()) {  
            String sqlInsert = "INSERT INTO GIAOVIEN (MaGiaoVien, HoTen, NgaySinh, GioiTinh, Email, MaMonHoc) VALUES (?, ?, ?, ?, ?, ?)";  
            PreparedStatement statement = connection.prepareStatement(sqlInsert);  
            statement.setString(1, maGiaoVien);  
            statement.setString(2, hoTen);  
            statement.setDate(3, new java.sql.Date(ngaySinh.getTime())); // Chuyển đổi Date sang java.sql.Date  
            statement.setString(4, gioiTinh);  
            statement.setString(5, email);  
            statement.setString(6, maMonHoc);  
            statement.executeUpdate();  

            // Thêm giáo viên vừa tạo vào danh sách  
            teacherList.add(new GiaoVien(maGiaoVien, hoTen, ngaySinh, gioiTinh, email, maMonHoc));  
            clearInputs();  
            JOptionPane.showMessageDialog(view, "Thêm giáo viên thành công!");  
            displayGiaoVienList(); // Cập nhật danh sách giáo viên  
        } catch (SQLException ex) {  
            JOptionPane.showMessageDialog(view, "Lỗi khi thêm giáo viên: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);  
        }  
    }  

    // Phương thức cập nhật giáo viên  
    public void updateGiaoVien() {  
        String maGiaoVien = view.getTxtMaGiaoVien().getText();  
        String hoTen = view.getTxtHoTen().getText();  
        String ngaySinhStr = view.getTxtNgaySinh().getText();  
        String gioiTinh = (String) view.getComboBoxGioiTinh().getSelectedItem();  
        String email = view.getTxtEmail().getText();  
        String maMonHoc = view.getTxtMaMonHoc().getText();  

        if (maGiaoVien.isEmpty()) {  
            JOptionPane.showMessageDialog(view, "Vui lòng chọn giáo viên để cập nhật!", "Lỗi", JOptionPane.ERROR_MESSAGE);  
            return;  
        }  

        // Chuyển đổi chuỗi thành đối tượng Date  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Định dạng ngày cần thiết  
        Date ngaySinh;  
        try {  
            ngaySinh = sdf.parse(ngaySinhStr);  
        } catch (ParseException e) {  
            JOptionPane.showMessageDialog(view, "Ngày sinh không hợp lệ. Vui lòng sử dụng định dạng yyyy-MM-dd!", "Lỗi", JOptionPane.ERROR_MESSAGE);  
            return;  
        }  

        try (Connection connection = jdbcSqlServer.getConnection()) {  
            String sqlUpdate = "UPDATE GIAOVIEN SET HoTen = ?, NgaySinh = ?, GioiTinh = ?, Email = ?, MaMonHoc = ? WHERE MaGiaoVien = ?";  
            PreparedStatement statement = connection.prepareStatement(sqlUpdate);  
            statement.setString(1, hoTen);  
            statement.setDate(2, new java.sql.Date(ngaySinh.getTime())); // Chuyển đổi Date sang java.sql.Date  
            statement.setString(3, gioiTinh);  
            statement.setString(4, email);  
            statement.setString(5, maMonHoc);  
            statement.setString(6, maGiaoVien);  
            statement.executeUpdate();  

            // Cập nhật danh sách giáo viên  
            for (GiaoVien teacher : teacherList) {  
                if (teacher.getMaGiaoVien().equals(maGiaoVien)) {  
                    teacher.setHoTen(hoTen);  
                    teacher.setNgaySinh(ngaySinh); // Cập nhật ngày sinh  
                    teacher.setGioiTinh(gioiTinh);  
                    teacher.setEmail(email);  
                    teacher.setMaMonHoc(maMonHoc);  
                    break;  
                }  
            }  

            clearInputs();  
            JOptionPane.showMessageDialog(view, "Cập nhật giáo viên thành công!");  
            displayGiaoVienList(); // Cập nhật danh sách giáo viên  
        } catch (SQLException ex) {  
            JOptionPane.showMessageDialog(view, "Lỗi khi cập nhật giáo viên: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);  
        }  
    }  

    // Phương thức xóa giáo viên  
    public void deleteGiaoVien() {  
        String maGiaoVien = view.getTxtMaGiaoVien().getText();  

        if (maGiaoVien.isEmpty()) {  
            JOptionPane.showMessageDialog(view, "Vui lòng chọn giáo viên để xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);  
            return;  
        }  

        try (Connection connection = jdbcSqlServer.getConnection()) {  
            String sqlDelete = "DELETE FROM GIAOVIEN WHERE MaGiaoVien = ?";  
            PreparedStatement statement = connection.prepareStatement(sqlDelete);  
            statement.setString(1, maGiaoVien);  
            statement.executeUpdate();  

            // Xóa giáo viên khỏi danh sách  
            teacherList.removeIf(teacher -> teacher.getMaGiaoVien().equals(maGiaoVien));  

            clearInputs();  
            JOptionPane.showMessageDialog(view, "Xóa giáo viên thành công!");  
            displayGiaoVienList(); // Cập nhật danh sách giáo viên  
        } catch (SQLException ex) {  
            JOptionPane.showMessageDialog(view, "Lỗi khi xóa giáo viên: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);  
        }  
    }  

    // Phương thức hiển thị danh sách giáo viên  
    public void displayGiaoVienList() {  
        try (Connection connection = jdbcSqlServer.getConnection()) {  
            String sqlSelect = "SELECT * FROM GIAOVIEN";  
            PreparedStatement statement = connection.prepareStatement(sqlSelect);  
            ResultSet resultSet = statement.executeQuery();  

            List<Object[]> dataList = new ArrayList<>();  
            // Lấy dữ liệu từ ResultSet  
            while (resultSet.next()) {  
                dataList.add(new Object[]{  
                    resultSet.getString("MaGiaoVien"),  
                    resultSet.getString("HoTen"),  
                    resultSet.getString("GioiTinh"),  
                    resultSet.getDate("NgaySinh"),  
                    resultSet.getString("Email"),  
                    resultSet.getString("MaMonHoc")  
                });  
            }  

            // Chuyển danh sách thành mảng  
            Object[][] trimmedData = new Object[dataList.size()][6];  
            for (int j = 0; j < dataList.size(); j++) {  
                trimmedData[j] = dataList.get(j);  
            }  

            // Gọi phương thức hiển thị danh sách giáo viên  
            view.displayTeacherList(trimmedData);  
        } catch (SQLException ex) {  
            JOptionPane.showMessageDialog(view, "Lỗi khi lấy danh sách giáo viên: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);  
        }  
    }  

    // Phương thức tải danh sách giáo viên từ cơ sở dữ liệu  
    public void loadGiaoVienList() {  
        try (Connection connection = jdbcSqlServer.getConnection()) {  
            String sqlSelect = "SELECT * FROM GIAOVIEN";  
            PreparedStatement statement = connection.prepareStatement(sqlSelect);  
            ResultSet resultSet = statement.executeQuery();  

            teacherList.clear(); // Xóa danh sách hiện tại  
            while (resultSet.next()) {  
                GiaoVien giaoVien = new GiaoVien(  
                    resultSet.getString("MaGiaoVien"),  
                    resultSet.getString("HoTen"),  
                    resultSet.getDate("NgaySinh"), // Lấy ngày sinh từ ResultSet  
                    resultSet.getString("GioiTinh"),  
                    resultSet.getString("Email"),  
                    resultSet.getString("MaMonHoc")  
                );  
                teacherList.add(giaoVien);  
            }  
        } catch (SQLException ex) {  
            JOptionPane.showMessageDialog(view, "Lỗi khi lấy danh sách giáo viên: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);  
        }  
    }  

    // Phương thức làm sạch các trường nhập liệu  
    private void clearInputs() {  
        view.getTxtMaGiaoVien().setText("");  
        view.getTxtHoTen().setText("");  
        view.getTxtNgaySinh().setText("");  
        view.getTxtEmail().setText("");  
        view.getTxtMaMonHoc().setText("");  
        view.getComboBoxGioiTinh().setSelectedIndex(0);  
    }  
}