package model;  

import java.util.Date;  

public class GiaoVien {  
    private String maGiaoVien;  
    private String hoTen;  
    private Date ngaySinh;  
    private String gioiTinh;  
    private String email;  
    private String maMonHoc;  

    public GiaoVien(String maGiaoVien, String hoTen, Date ngaySinh, String gioiTinh, String email, String maMonHoc) {  
        this.maGiaoVien = maGiaoVien;  
        this.hoTen = hoTen;  
        this.ngaySinh = ngaySinh;  
        this.gioiTinh = gioiTinh;  
        this.email = email;  
        this.maMonHoc = maMonHoc;  
    }

	public String getMaGiaoVien() {
		return maGiaoVien;
	}

	public void setMaGiaoVien(String maGiaoVien) {
		this.maGiaoVien = maGiaoVien;
	}

	public String getHoTen() {
		return hoTen;
	}

	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}

	public Date getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public String getGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(String gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMaMonHoc() {
		return maMonHoc;
	}

	public void setMaMonHoc(String maMonHoc) {
		this.maMonHoc = maMonHoc;
	}  

    // Getters and Setters  
    // ...  
    
}