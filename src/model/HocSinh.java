package model;  

import java.util.Date;  

public class HocSinh {  
    private String maHocSinh;  
    private String hoTen;  
    private String gioiTinh;  
    private Date ngaySinh;  
    private String diaChi;  
    private String email;  
    private String nienKhoa;  
    private String maLop;  

    public HocSinh(String maHocSinh, String hoTen, String gioiTinh, Date ngaySinh, String diaChi, String email, String nienKhoa, String maLop) {  
        this.maHocSinh = maHocSinh;  
        this.hoTen = hoTen;  
        this.gioiTinh = gioiTinh;  
        this.ngaySinh = ngaySinh;  
        this.diaChi = diaChi;  
        this.email = email;  
        this.nienKhoa = nienKhoa;  
        this.maLop = maLop;  
    }

	public String getMaHocSinh() {
		return maHocSinh;
	}

	public void setMaHocSinh(String maHocSinh) {
		this.maHocSinh = maHocSinh;
	}

	public String getHoTen() {
		return hoTen;
	}

	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}

	public String getGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(String gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	public Date getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNienKhoa() {
		return nienKhoa;
	}

	public void setNienKhoa(String nienKhoa) {
		this.nienKhoa = nienKhoa;
	}

	public String getMaLop() {
		return maLop;
	}

	public void setMaLop(String maLop) {
		this.maLop = maLop;
	}  

    // Getters and Setters  
    // ...  
    
}