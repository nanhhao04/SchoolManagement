package model;  

public class Lop {  
    private String maLop;  
    private String tenLop;  
    private int siSo;  
    private String maKhoi;  
    private String maGiaoVien;  

    public Lop(String maLop, String tenLop, int siSo, String maKhoi, String maGiaoVien) {  
        this.maLop = maLop;  
        this.tenLop = tenLop;  
        this.siSo = siSo;  
        this.maKhoi = maKhoi;  
        this.maGiaoVien = maGiaoVien;  
    }

	

	public String getMaLop() {
		return maLop;
	}

	public void setMaLop(String maLop) {
		this.maLop = maLop;
	}

	public String getTenLop() {
		return tenLop;
	}

	public void setTenLop(String tenLop) {
		this.tenLop = tenLop;
	}

	public int getSiSo() {
		return siSo;
	}

	public void setSiSo(int siSo) {
		this.siSo = siSo;
	}

	public String getMaKhoi() {
		return maKhoi;
	}

	public void setMaKhoi(String maKhoi) {
		this.maKhoi = maKhoi;
	}

	public String getMaGiaoVien() {
		return maGiaoVien;
	}

	public void setMaGiaoVien(String maGiaoVien) {
		this.maGiaoVien = maGiaoVien;
	}  

    // Getters and Setters  
    
}
