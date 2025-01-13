package model;  

public class Khoi {  
    private String maKhoi;  
    private String tenKhoi;  
    private int soLopToiDa;  

    public Khoi(String maKhoi, String tenKhoi, int soLopToiDa) {  
        this.maKhoi = maKhoi;  
        this.tenKhoi = tenKhoi;  
        this.soLopToiDa = soLopToiDa;  
    }  

    public String getMaKhoi() {  
        return maKhoi;  
    }  

    public void setMaKhoi(String maKhoi) {  
        this.maKhoi = maKhoi;  
    }  

    public String getTenKhoi() {  
        return tenKhoi;  
    }  

    public void setTenKhoi(String tenKhoi) {  
        this.tenKhoi = tenKhoi;  
    }  

    public int getSoLopToiDa() {  
        return soLopToiDa;  
    }  

    public void setSoLopToiDa(int soLopToiDa) {  
        this.soLopToiDa = soLopToiDa;  
    }  
}