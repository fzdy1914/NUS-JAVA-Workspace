package diy;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class LoginFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	
	public LoginFrame() {
		
		setSize(160,150);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		Container cp=getContentPane();
		getContentPane().setLayout(null);

		JLabel lb1 = new JLabel("用户名:");
		lb1.setBounds(10, 10, 50, 20);
		getContentPane().add(lb1);
		JLabel lb2 = new JLabel("密码:");
		lb2.setBounds(10, 40, 50, 20);
		getContentPane().add(lb2);
		JTextField jt=new JTextField("");
		jt.setBounds(60, 10, 80, 20);
		JPasswordField jp=new JPasswordField("");
		jp.setBounds(60, 40, 80, 20);
		cp.add(jt);
		cp.add(jp);
		ActionListener action = new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(jt.getText().equals("mr") && new String(jp.getPassword()).equals("mrsoft")) {
					JOptionPane.showMessageDialog(null, "登陆成功!");
				} else if (jt.getText().equals("")){
					JOptionPane.showMessageDialog(null, "请输入用户名!");
					jp.setText("");
					jt.requestFocus();
				} else if (new String(jp.getPassword()).equals("")) {
					JOptionPane.showMessageDialog(null, "请输入密码!");
					jp.requestFocus();
				} else {
					JOptionPane.showMessageDialog(null, "用户名或密码错误!");
					jt.setText("");
					jp.setText("");
					jt.requestFocus();
				}
			}
		};
		jt.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				jp.requestFocus();
			}
		});
		
		jp.addActionListener(action);	
		JButton jb1 = new JButton("提交");
		jb1.setBounds(10, 70, 60, 20);
		jb1.addActionListener(action);
		JButton jb2 = new JButton("重置");
		jb2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				jt.setText("");
				jp.setText("");
				jt.requestFocus();
			}
		});
		jb2.setBounds(80, 70, 60, 20);
		cp.add(jb1);
		cp.add(jb2);
		jt.requestFocus();
		setVisible(true);
	}
	public static void main(String[] args) {
		new LoginFrame();
	}

}


