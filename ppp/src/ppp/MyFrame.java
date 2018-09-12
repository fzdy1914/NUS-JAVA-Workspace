package ppp;


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

class MyJDialog extends JDialog { // 创建新类继承JDialog类

	private static final long serialVersionUID = 1L;

	public MyJDialog(MyFrame frame) {
		// 实例化一个JDialog类对象，指定对话框的父窗体、窗体标题和类型
		super(frame, "第一个JDialog窗体", true);
		Container container = getContentPane(); // 创建一个容器
		JLabel jl = new JLabel("这是一个对话框"); // 在窗体中设置标签
		jl.setHorizontalAlignment(SwingConstants.CENTER);
		container.add(jl);
		setBounds(120, 120, 100, 100); // 设置对话框窗体大小
		setSize(200,200);
		container.setBackground(Color.white);
	}
}

public class MyFrame extends JFrame { // 创建新类

	private static final long serialVersionUID = 1L;

	public static void main(String args[]) {
		new MyFrame(); // 实例化MyJDialog类对象
	}
	
	public MyFrame() {
		Container container = getContentPane(); // 创建一个容器
		container.setLayout(null);
		JLabel jl = new JLabel("这是一个JFrame窗体"); // 在窗体中设置标签
		// 将标签的文字置于标签中间位置
		jl.setHorizontalAlignment(SwingConstants.CENTER);
		container.add(jl);
		jl.setBounds(20,45,150,20);
		JButton bl = new JButton("弹出对话框"); // 定义一个按钮
		bl.setBounds(60, 85, 100, 20);
		bl.addActionListener(new ActionListener() { // 为按钮添加鼠标单击事件
					public void actionPerformed(ActionEvent e) {
						// 使MyJDialog窗体可见
						new MyJDialog(MyFrame.this).setVisible(true);
					}
				});
		container.add(bl); // 将按钮添加到容器中
		
		container.setBackground(Color.white);
		setSize(400, 500);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}
