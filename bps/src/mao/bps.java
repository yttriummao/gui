package mao;

import javax.swing.*;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.CardLayout;
import java.awt.event.*;
import java.awt.*;
import java.awt.Color;

public class bps extends JFrame implements TreeSelectionListener
{
	/*���������*/
	
	   /*���ڵ�*/
	   DefaultMutableTreeNode root=new DefaultMutableTreeNode(new mynode("����ѡ��","0"));
	
	   /*��������*/
	   DefaultMutableTreeNode bps_df=new DefaultMutableTreeNode(new mynode("��������","1"));
	   DefaultMutableTreeNode crt_df=new DefaultMutableTreeNode(new mynode("�����ϴ��ı�df","11"));
	   DefaultMutableTreeNode par_df=new DefaultMutableTreeNode(new mynode("���������ı�df","12"));
	
	   /*��������*/
	   DefaultMutableTreeNode bps_ds=new DefaultMutableTreeNode(new mynode("��������","2"));
	   DefaultMutableTreeNode crt_ds=new DefaultMutableTreeNode(new mynode("�����ϴ��ı�ds","21"));
	   DefaultMutableTreeNode par_ds=new DefaultMutableTreeNode(new mynode("���������ı�ds","22"));
       
       /*�������ͻ�*/
	   DefaultMutableTreeNode bps_fcus=new DefaultMutableTreeNode(new mynode("�������ͻ�","3"));
	   DefaultMutableTreeNode crt_fcus=new DefaultMutableTreeNode(new mynode("�����ϴ��ı�fcus","31"));
	   DefaultMutableTreeNode par_fcus=new DefaultMutableTreeNode(new mynode("���������ı�fcus","32"));
	   
	   /*�������ʻ�*/
	   DefaultMutableTreeNode bps_fact=new DefaultMutableTreeNode(new mynode("�������ʻ�","4"));
	   DefaultMutableTreeNode crt_fact=new DefaultMutableTreeNode(new mynode("�����ϴ��ı�fact","41"));
	   DefaultMutableTreeNode par_fact=new DefaultMutableTreeNode(new mynode("���������ı�fact","42"));
	   
	   /*��������*/
	   DefaultMutableTreeNode bps_bodc=new DefaultMutableTreeNode(new mynode("��������","5"));
	   DefaultMutableTreeNode crt_bodc=new DefaultMutableTreeNode(new mynode("�����ϴ��ı�bodc","51"));
	   DefaultMutableTreeNode par_bodc=new DefaultMutableTreeNode(new mynode("���������ı�bodc","52"));
	   
	   /*�����ӡѡ��*/
	   //DefaultMutableTreeNode print=new DefaultMutableTreeNode(new mynode("�����ӡ","13"));
	   
	   /*��*/
	   JTree tree=new JTree(root);
	   
	/*�����������*/
	JPanel jpl=new JPanel(); //�������
	JScrollPane jsp=new JScrollPane(tree); //������������
	JSplitPane jslp=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,jsp,jpl);  //�����ָ��
	
	/*"�ر��¼�"�ڲ��ඨ��*/
	WindowCloser wc;
	
	/*��Ƭ���ֶ���*/
	CardLayout cl;
	
	/*ģ���������*/
	welcome wel=new welcome("BPSԤ����ϵͳ");
	gui_df gdf=new gui_df(); //����
	gui_ds gds=new gui_ds(); //����
	gui_fcus gfcus=new gui_fcus(); //�����ͻ�
	
	/*�ҵĽڵ�*/
	public class mynode
	{
		String name;
		String id;
		
		mynode(String c_name,String c_id)
		{
			name=c_name;
			id=c_id;
		}
		
		public String toString()
		{
			return(name);
		}
		
		public String getid()
		{
			return(id);
		}
	}
	
	/*������*/
	bps()
	{
		initialtree();
		addtreelistener();
		initialjframe();
		initialjpanel();
	}
	
	/*����ʼ��*/
	void initialtree()
	{
		root.add(bps_df);
		root.add(bps_ds);
		root.add(bps_fcus);
		root.add(bps_fact);
		root.add(bps_bodc);
		
		bps_df.add(crt_df);
		bps_df.add(par_df);
		
	    bps_ds.add(crt_ds);
		bps_ds.add(par_ds);
		
		bps_fcus.add(crt_fcus);
		bps_fcus.add(par_fcus);

		bps_fact.add(crt_fact);
		bps_fact.add(par_fact);

		bps_bodc.add(crt_bodc);
		bps_bodc.add(par_bodc);
		
		tree.setToggleClickCount(1);  //�������ڵ�չ���ĵ������
	}
	
	/*Windows���ڹر��¼���*/
	public class WindowCloser extends WindowAdapter
    {
	    public void windowClosing(WindowEvent ew)
	    {
	    	System.out.println("�رմ���");
		    JOptionPane.showMessageDialog(jpl,"ллʹ�ã��ټ�  ^_^","����С�ɰ�",JOptionPane.INFORMATION_MESSAGE);
	    }
    }
	
	/*��Ӽ�����*/
	void addtreelistener()
	{
		tree.addTreeSelectionListener(this);  //���"��"����
		wc=new WindowCloser();
		this.addWindowListener(wc);           //���"���ڹر�"����
	}
	
	/*���ڵ����¼�����*/
	public void valueChanged(TreeSelectionEvent e)
	{
		int id;
		DefaultMutableTreeNode selectnode=(DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
		mynode find=(mynode)selectnode.getUserObject();
		id=Integer.valueOf(find.getid()).intValue();
		switch(id)
		{
			case 11:
				 cl.show(jpl,"gui_df");
				 System.out.println("�����ϴ��ı�df");
				 break;
		    case 12:
		    	 System.out.println("���������ı�df");
		    	 break;
		    case 21:
		    	 cl.show(jpl,"gui_ds");
		    	 System.out.println("�����ϴ��ı�ds");
		    	 break;
		    case 22:
		    	 System.out.println("���������ı�ds");
		    	 break;
		    case 31:
		    	 cl.show(jpl,"gui_fcus");
		    	 System.out.println("�����ϴ��ı�fcus");
		    	 break;
		    case 32:
		    	 System.out.println("���������ı�fcus");
		    	 break;
		    case 41:
		    	 System.out.println("�����ϴ��ı�fact");
		    	 break;
		    case 42:
		    	 System.out.println("���������ı�fact");
		    	 break;
		    case 51:
		    	 System.out.println("�����ϴ��ı�bodc");
		    	 break;
		    case 52:
		    	 System.out.println("���������ı�bodc");
		    	 break;	 
		}
	}
	
	/*JFrame��ʼ��*/
	void initialjframe()
	{
		this.add(jslp);
		jslp.setDividerLocation(170);//���÷ָ�����λ��
		this.setSize(1000,700);  //���ô����С
		//this.setExtendedState(MAXIMIZED_BOTH); //���ô����ʼ��С
		this.setResizable(true);  //���ô����Ƿ�����ֶ�����
	}
	
	/*JPanel��ʼ��*/
	void initialjpanel()
	{
		jpl.setLayout(new CardLayout());
		jpl.add(wel,"welcome"); //����"��ӭ"ģ��
		jpl.add(gdf,"gui_df");  //����"��������"ģ��
		jpl.add(gds,"gui_ds");  //����"��������"ģ��
		jpl.add(gfcus,"gui_fcus");  //����"���������ͻ�"ģ��
		cl=(CardLayout)jpl.getLayout();
		cl.show(jpl,"welcome"); //Ĭ����ʾ"��ӭ"ģ��
	}
	
	
	public static void main(String args[])
	{
		bps b=new bps();
		b.setTitle("�й�����bpsԤ����ϵͳ");
		b.setVisible(true);
		b.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //�û��ڴ˴����Ϸ��� "close" ʱĬ��ִ�еĲ���
	}
	
}