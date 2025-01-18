package controller;

import model.Lop;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LopController {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=schoolmanage3;encrypt=true;trustServerCertificate=true";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "123456789";

    public void addLop(JTextField txtMaLop, JTextField txtTenLop, JTextField txtMaGiaoVien, JTextField txtSiSo, JComboBox<String> comboBoxKhoi, JTable classTable) {
        String maLop = txtMaLop.getText();
        String tenLop = txtTenLop.getText();
        String maGiaoVien = txtMaGiaoVien.getText();
        String siSoStr = txtSiSo.getText();
        
        String selectedKhoi = (String) comboBoxKhoi.getSelectedItem();
        if (selectedKhoi == null || selectedKhoi.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn khối!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int siSo;
        try {
            siSo = Integer.parseInt(siSoStr);
            if (siSo < 0) {
                JOptionPane.showMessageDialog(null, "Sĩ số phải là số không âm.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập sĩ số hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String maKhoi = selectedKhoi.split(" - ")[0];

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            // Kiểm tra mã lớp tồn tại
            try (PreparedStatement pstmt = connection.prepareStatement("SELECT COUNT(*) FROM LOP WHERE MaLop = ?")) {
                pstmt.setString(1, maLop);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        JOptionPane.showMessageDialog(null, "Mã lớp đã tồn tại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }

            // Kiểm tra mã giáo viên tồn tại
            try (PreparedStatement pstmt = connection.prepareStatement("SELECT COUNT(*) FROM GIAOVIEN WHERE MaGiaoVien = ?")) {
                pstmt.setString(1, maGiaoVien);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) == 0) {
                        JOptionPane.showMessageDialog(null, "Mã giáo viên không tồn tại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }

            // Thêm lớp mới
            try (PreparedStatement pstmt = connection.prepareStatement("INSERT INTO LOP (MaLop, TenLop, SiSo, MaKhoi, MaGiaoVien) VALUES (?, ?, ?, ?, ?)")) {
                pstmt.setString(1, maLop);
                pstmt.setString(2, tenLop);
                pstmt.setInt(3, siSo);
                pstmt.setString(4, maKhoi);
                pstmt.setString(5, maGiaoVien);
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Thêm lớp thành công!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi thêm lớp vào cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        // Cập nhật bảng sau khi thêm
        displayClassesByKhoi(maKhoi, classTable);
    }



    public void updateLop(JTable classTable, JTextField txtMaLop, JTextField txtTenLop, JTextField txtMaGiaoVien, JTextField txtSiSo, JComboBox<String> comboBoxKhoi) {
        int selectedRow = classTable.getSelectedRow();
        if (selectedRow >= 0) {
            String maLop = txtMaLop.getText();
            String tenLop = txtTenLop.getText();
            String maGiaoVien = txtMaGiaoVien.getText();
            String siSoStr = txtSiSo.getText();
            String selectedKhoi = (String) comboBoxKhoi.getSelectedItem();

            if (selectedKhoi == null || selectedKhoi.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn khối!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int siSo;
            try {
                siSo = Integer.parseInt(siSoStr);
                if (siSo < 0) {
                    JOptionPane.showMessageDialog(null, "Sĩ số phải là số không âm.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập sĩ số hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String maKhoi = selectedKhoi.split(" - ")[0];

            try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                 PreparedStatement pstmt = connection.prepareStatement("UPDATE LOP SET TenLop = ?, SiSo = ?, MaKhoi = ?, MaGiaoVien = ? WHERE MaLop = ?")
            ) {
                pstmt.setString(1, tenLop);
                pstmt.setInt(2, siSo);
                pstmt.setString(3, maKhoi);
                pstmt.setString(4, maGiaoVien);
                pstmt.setString(5, maLop);
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Cập nhật lớp thành công!");
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi cập nhật lớp trong cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn lớp để cập nhật.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    public void deleteLop(JTable classTable) {
        int selectedRow = classTable.getSelectedRow();
        if (selectedRow >= 0) {
            String maLop = classTable.getValueAt(selectedRow, 0).toString();

            try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                 PreparedStatement pstmt = connection.prepareStatement("DELETE FROM LOP WHERE MaLop = ?")
            ) {
                pstmt.setString(1, maLop);
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Xóa lớp thành công!");
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi xóa lớp trong cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn lớp để xóa.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public List<Lop> getClassesByKhoi(String maKhoi) {
        List<Lop> lopList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM LOP WHERE MaKhoi = ?")
        ) {
            pstmt.setString(1, maKhoi);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Lop lop = new Lop(
                            rs.getString("MaLop"),
                            rs.getString("TenLop"),
                            rs.getInt("SiSo"),
                            rs.getString("MaKhoi"),
                            rs.getString("MaGiaoVien")
                    );
                    lopList.add(lop);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lopList;
    }

    public void displayClassesByKhoi(String maKhoi, JTable classTable) {
        List<Lop> lopList = getClassesByKhoi(maKhoi);
        DefaultTableModel model = new DefaultTableModel(new String[]{"Mã Lớp", "Tên Lớp", "Sĩ Số", "Mã Khối", "Mã Giáo Viên"}, 0);

        for (Lop lop : lopList) {
            model.addRow(new Object[]{
                lop.getMaLop(),
                lop.getTenLop(),
                lop.getSiSo(),
                lop.getMaKhoi(),
                lop.getMaGiaoVien()
            });
        }

        classTable.setModel(model);
        if (lopList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Không có lớp nào trong khối " + maKhoi, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
