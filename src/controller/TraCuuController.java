package controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import schoolmanagement.jdbcSqlServer;

public class TraCuuController {
    private DefaultTableModel tableModelThongTin;
    private DefaultTableModel tableModelDiem;
    private DefaultTableModel tableModelHocsinh;

    public TraCuuController(DefaultTableModel tableModelThongTin, DefaultTableModel tableModelDiem, DefaultTableModel tableModelHocsinh) {
        this.tableModelThongTin = tableModelThongTin;
        this.tableModelDiem = tableModelDiem;
        this.tableModelHocsinh = tableModelHocsinh;
    }

    // Lấy thông tin học sinh
    public void layThongTinHocsinh(String maHocSinh) {
        tableModelThongTin.setRowCount(0);

        try (Connection connection = jdbcSqlServer.getConnection();
             PreparedStatement stmt = connection.prepareStatement(
                     "SELECT HoTen, GioiTinh, NgaySinh, LOP.MaLop, TenLop " +
                             "FROM HOCSINH LEFT JOIN LOP ON HOCSINH.MaLop = LOP.MaLop WHERE HOCSINH.MaHocSinh = ?")) {
            stmt.setString(1, maHocSinh);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                tableModelThongTin.addRow(new Object[]{"Tên", rs.getString("HoTen")});
                tableModelThongTin.addRow(new Object[]{"Giới Tính", rs.getString("GioiTinh")});
                tableModelThongTin.addRow(new Object[]{"Ngày Sinh", rs.getDate("NgaySinh")});
                tableModelThongTin.addRow(new Object[]{"Tên Lớp", rs.getString("TenLop")});
                tableModelThongTin.addRow(new Object[]{"Mã Lớp", rs.getString("MaLop")});
            } else {
                JOptionPane.showMessageDialog(null, "Không tìm thấy thông tin học sinh!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Lấy thông tin điểm và tính toán
    public void layThongTinDiem(String maHocSinh) {
        tableModelDiem.setRowCount(0);

        try (Connection connection = jdbcSqlServer.getConnection();
             PreparedStatement stmt = connection.prepareStatement(
                     "SELECT MonHoc.TenMonHoc, " +
                             "SUM(CASE WHEN ChiTietDiem.MaLoaiKiemTra = 1 THEN ChiTietDiem.DiemSo ELSE 0 END) AS Diem15Phut, " +
                             "SUM(CASE WHEN ChiTietDiem.MaLoaiKiemTra = 2 THEN ChiTietDiem.DiemSo ELSE 0 END) AS Diem1Tiet, " +
                             "SUM(CASE WHEN ChiTietDiem.MaLoaiKiemTra = 3 THEN ChiTietDiem.DiemSo ELSE 0 END) AS DiemCuoiKy " +
                             "FROM ChiTietDiem " +
                             "JOIN MonHoc ON ChiTietDiem.MaMonHoc = MonHoc.MaMonHoc " +
                             "WHERE ChiTietDiem.MaHocSinh = ? " +
                             "GROUP BY MonHoc.TenMonHoc")) {
            stmt.setString(1, maHocSinh);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                double diem15Phut = rs.getDouble("Diem15Phut");
                double diem1Tiet = rs.getDouble("Diem1Tiet");
                double diemCuoiKy = rs.getDouble("DiemCuoiKy");

                // Tính điểm trung bình
                double diemTB = tinhDiemTrungBinh(diem15Phut, diem1Tiet, diemCuoiKy);

                // Thêm dữ liệu vào bảng
                tableModelDiem.addRow(new Object[]{
                        rs.getString("TenMonHoc"),
                        diem15Phut > 0 ? diem15Phut : null,
                        diem1Tiet > 0 ? diem1Tiet : null,
                        diemCuoiKy > 0 ? diemCuoiKy : null,
                        Math.round(diemTB * 100.0) / 100.0
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Tính điểm trung bình
    private double tinhDiemTrungBinh(Double diem15Phut, Double diem1Tiet, Double diemCuoiKy) {
        double tongDiem = 0.0;
        double tongHeSo = 0.0;

        if (diem15Phut != null && diem15Phut > 0) {
            tongDiem += diem15Phut * 0.2;
            tongHeSo += 0.2;
        }
        if (diem1Tiet != null && diem1Tiet > 0) {
            tongDiem += diem1Tiet * 0.3;
            tongHeSo += 0.3;
        }
        if (diemCuoiKy != null && diemCuoiKy > 0) {
            tongDiem += diemCuoiKy * 0.5;
            tongHeSo += 0.5;
        }

        return tongHeSo > 0 ? tongDiem / tongHeSo : 0.0;
    }

    // Lấy thông tin học sinh theo mã lớp
    public void layThongTinHocsinhTheoMaLop(String maLop) {
        tableModelHocsinh.setRowCount(0);

        try (Connection connection = jdbcSqlServer.getConnection();
             PreparedStatement stmt = connection.prepareStatement(
                     "SELECT MaHocSinh, HoTen, NgaySinh, GioiTinh " +
                             "FROM HOCSINH WHERE MaLop = ?")) {
            stmt.setString(1, maLop);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tableModelHocsinh.addRow(new Object[]{
                        rs.getString("MaHocSinh"),
                        rs.getString("HoTen"),
                        rs.getDate("NgaySinh"),
                        rs.getString("GioiTinh")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
