package model;  

public class ChiTietDiem {  
    private int maChiTietDiem;  
    private String maHocSinh;  
    private String maMonHoc;  
    private int maLoaiKiemTra;  
    private String maGiaoVien;  
    private float diemSo;  

    public ChiTietDiem(int maChiTietDiem, String maHocSinh, String maMonHoc, int maLoaiKiemTra, String maGiaoVien, float diemSo) {  
        this.maChiTietDiem = maChiTietDiem;  
        this.maHocSinh = maHocSinh;  
        this.maMonHoc = maMonHoc;  
        this.maLoaiKiemTra = maLoaiKiemTra;  
        this.maGiaoVien = maGiaoVien;  
        this.diemSo = diemSo;  
    }

	public int getMaChiTietDiem() {
		return maChiTietDiem;
	}

	public void setMaChiTietDiem(int maChiTietDiem) {
		this.maChiTietDiem = maChiTietDiem;
	}

	public String getMaHocSinh() {
		return maHocSinh;
	}

	public void setMaHocSinh(String maHocSinh) {
		this.maHocSinh = maHocSinh;
	}

	public String getMaMonHoc() {
		return maMonHoc;
	}

	public void setMaMonHoc(String maMonHoc) {
		this.maMonHoc = maMonHoc;
	}

	public int getMaLoaiKiemTra() {
		return maLoaiKiemTra;
	}

	public void setMaLoaiKiemTra(int maLoaiKiemTra) {
		this.maLoaiKiemTra = maLoaiKiemTra;
	}

	public String getMaGiaoVien() {
		return maGiaoVien;
	}

	public void setMaGiaoVien(String maGiaoVien) {
		this.maGiaoVien = maGiaoVien;
	}

	public float getDiemSo() {
		return diemSo;
	}

	public void setDiemSo(float diemSo) {
		this.diemSo = diemSo;
	}  

    // Getters and Setters  
    // ...  
    
}