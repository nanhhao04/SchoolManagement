package view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.SystemColor;
import javax.swing.UIManager;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class JFMainView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPasswordField passwordField;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFMainView frame = new JFMainView();
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
	public JFMainView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 498, 346);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("CHƯƠNG TRÌNH QUẢN LÝ TRƯỜNG HỌC");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setBackground(SystemColor.activeCaption);
		lblNewLabel.setBounds(71, 38, 324, 14);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Đăng nhập");
		lblNewLabel_1.setBounds(179, 89, 66, 14);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Tài Khoản");
		lblNewLabel_2.setBounds(69, 126, 78, 14);
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Mật khẩu");
		lblNewLabel_3.setBounds(69, 151, 61, 14);
		contentPane.add(lblNewLabel_3);

		passwordField = new JPasswordField();
		passwordField.setBounds(163, 148, 117, 20);
		contentPane.add(passwordField);

		JCheckBox chckbxNewCheckBox = new JCheckBox("Nhớ mật khẩu");
		chckbxNewCheckBox.setBounds(163, 175, 117, 23);
		contentPane.add(chckbxNewCheckBox);

		JButton btnNewButton_1 = new JButton("Đăng nhập");

		btnNewButton_1.setBounds(163, 205, 117, 23);
		contentPane.add(btnNewButton_1);

		textField = new JTextField();
		textField.setBounds(163, 120, 117, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		// Action Listener cho nút đăng nhập
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = textField.getText();
				String password = new String(passwordField.getPassword());

				// Kiểm tra tài khoản và mật khẩu
				if (username.equals("admin") && password.equals("admin123")) {
					JOptionPane.showMessageDialog(contentPane, "Đăng nhập thành công! Bạn là Quản trị viên.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
					// Chuyển tới giao diện quản trị viên
					new JFGiaoDienAdmin().setVisible(true);  
		            dispose(); // Đóng form đăng nhập  
				} else if (username.equals("teacher") && password.equals("teacher123")) {  
				    JOptionPane.showMessageDialog(contentPane, "Đăng nhập thành công! Bạn là Giáo viên.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);  
				    // Chuyển tới giao diện giáo viên  
				    JFQuanLyDiem quanLyDiem = new JFQuanLyDiem(); // Tạo đối tượng của JFQuanLyDiem  
				    quanLyDiem.setVisible(true); // Hiện giao diện quản lý điểm  
				    dispose(); // Đóng cửa sổ hiện tại  
				}   else if (username.equals("") && password.equals("")) {
					JOptionPane.showMessageDialog(contentPane, "Đăng nhập thành công! Bạn là Học sinh.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
					// Chuyển tới giao diện học sinh
					JFTraCuu tracuu = new JFTraCuu();
					tracuu.setVisible(true); // Hiện giao diện quản lý điểm  
				    dispose();
					
				} else {
					// Hiển thị thông báo lỗi
					JOptionPane.showMessageDialog(contentPane, "Tài khoản hoặc mật khẩu không đúng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
}
