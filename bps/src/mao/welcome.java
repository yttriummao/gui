package mao;

import javax.swing.*;

public class welcome extends JLabel {
	String str;

	static Icon pic = new ImageIcon("imp/background.jpg"); // Ҫ��ʾ��ͼƬ

	public welcome(String c_str) {

		this.str = c_str;
		this.initialFrame();
	}

	public void initialFrame() {
		this.setIcon(pic);
		this.setHorizontalAlignment(JLabel.CENTER); // ����ˮƽλ��
		this.setVerticalAlignment(JLabel.CENTER); // ���ô�ֱλ��
	}
}