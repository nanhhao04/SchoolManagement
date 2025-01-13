package model;  

public class LoaiKiemTra {  
    private int maLoaiKiemTra;  
    private String tenLoaiKiemTra;  
    private float trongSo;  

    public LoaiKiemTra(int maLoaiKiemTra, String tenLoaiKiemTra, float trongSo) {  
        this.maLoaiKiemTra = maLoaiKiemTra;  
        this.tenLoaiKiemTra = tenLoaiKiemTra;  
        this.trongSo = trongSo;  
    }

	public int getMaLoaiKiemTra() {
		return maLoaiKiemTra;
	}

	public void setMaLoaiKiemTra(int maLoaiKiemTra) {
		this.maLoaiKiemTra = maLoaiKiemTra;
	}

	public String getTenLoaiKiemTra() {
		return tenLoaiKiemTra;
	}

	public void setTenLoaiKiemTra(String tenLoaiKiemTra) {
		this.tenLoaiKiemTra = tenLoaiKiemTra;
	}

	public float getTrongSo() {
		return trongSo;
	}

	public void setTrongSo(float trongSo) {
		this.trongSo = trongSo;
	}  

    // Getters and Setters  
    // ...  
    
}