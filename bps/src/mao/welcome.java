package mao;

import javax.swing.*;

public class welcome extends JLabel {
	String str;

	static Icon pic = new ImageIcon("imp/background.jpg"); // 要显示的图片

	public welcome(String c_str) {

		this.str = c_str;
		this.initialFrame();
	}

	public void initialFrame() {
		this.setIcon(pic);
		this.setHorizontalAlignment(JLabel.CENTER); // 设置水平位置
		this.setVerticalAlignment(JLabel.CENTER); // 设置垂直位置
	}
}