package view;  

import java.awt.EventQueue;  
import javax.swing.JFrame;  
import javax.swing.JPanel;  
import javax.swing.border.EmptyBorder;  
import javax.swing.JLabel;  
import java.awt.Font;  
import javax.swing.JButton;  
import java.awt.event.ActionListener;  
import java.awt.event.ActionEvent;  

public class JFGiaoDienAdmin extends JFrame {  

    private static final long serialVersionUID = 1L;  
    private JPanel contentPane;  

    /**  
     * Launch the application.  
     */  
    public static void main(String[] args) {  
        EventQueue.invokeLater(new Runnable() {  
            public void run() {  
                try {  
                    JFGiaoDienAdmin frame = new JFGiaoDienAdmin();  
                    frame.setVisible(true);  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }  
        });  
    }  

    /**  
     * Create the frame.  
     */  
    public JFGiaoDienAdmin() {  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        setBounds(100, 100, 299, 322);  
        contentPane = new JPanel();  
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));  

        setContentPane(contentPane);  
        contentPane.setLayout(null);  
        
        JButton btnQuanLyHocSinh = new JButton("Quản Lý Học Sinh");  
        btnQuanLyHocSinh.setBounds(73, 51, 152, 23);  
        btnQuanLyHocSinh.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  
                openStudentManagement();  
            }  
        });  
        contentPane.add(btnQuanLyHocSinh);  
        
        JLabel lblTitle = new JLabel("Chương trình quản trị viên");  
        lblTitle.setBounds(58, 11, 167, 17);  
        lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 14));  
        contentPane.add(lblTitle);  
        
        JButton btnQuanLyGiaoVien = new JButton("Quản Lý Giáo Viên");  
        btnQuanLyGiaoVien.setBounds(73, 85, 152, 23);  
        btnQuanLyGiaoVien.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  
                openTeacherManagement();  
            }  
        });  
        contentPane.add(btnQuanLyGiaoVien);  
        
        JButton btnQuanLyChung = new JButton("Quản Lý Chung");  
        btnQuanLyChung.setBounds(73, 119, 152, 23);  
        btnQuanLyChung.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  
                openGeneralManagement();  
            }  
        });  
        contentPane.add(btnQuanLyChung);  
        
        JButton btnTraCuu = new JButton("Tra Cứu");  
        btnTraCuu.setBounds(73, 153, 152, 23);  
        btnTraCuu.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  
                openSearch();  
            }  
        });  
        contentPane.add(btnTraCuu);  
        
        JButton btnQuayLai = new JButton("Quay Lại");  
        btnQuayLai.setBounds(73, 187, 152, 23);  
        btnQuayLai.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  
                goBack();  
            }  
        });  
        contentPane.add(btnQuayLai);  
    }  

    // Mở các phần quản lý tương ứng  
    private void openStudentManagement() {  
        JFHocSinh studentFrame = new JFHocSinh();  
        studentFrame.setVisible(true);  
        this.dispose(); // Đóng cửa sổ hiện tại  
    }  

    private void openTeacherManagement() {  
        JFGiaoVien teacherFrame = new JFGiaoVien();  
        teacherFrame.setVisible(true);  
        this.dispose();  
    }  

    private void openGeneralManagement() {  
        JFQuanLyChung generalFrame = new JFQuanLyChung();  
        generalFrame.setVisible(true);  
        this.dispose();  
    }  

    private void openSearch() {  
        JFTraCuu searchFrame = new JFTraCuu();  
        searchFrame.setVisible(true);  
        this.dispose();  
    }  

    private void goBack() {  
        // Logic để quay lại màn hình trước đó (nếu cần)  
        // Ví dụ: mở JFrame chính  
        JFrame mainFrame = new JFMainView();  
        mainFrame.setVisible(true);  
        this.dispose();  
    }  
}