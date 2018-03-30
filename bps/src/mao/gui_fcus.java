package mao;

import javax.swing.*;
import java.awt.event.*;
import java.awt.Color;
import java.util.Map;
import java.util.HashMap;
import java.io.*;
import javax.swing.JProgressBar;
import java.lang.Thread;
import java.lang.reflect.*;

public class gui_fcus extends JPanel implements ActionListener
{
	/*--------------�������-------------------*/
	/*��ǩ*/
	JLabel jl=new JLabel("�������ͻ��������FCUS��");
	JLabel jl_orgidt=new JLabel("���׻�����");
	JLabel jl_tlr=new JLabel("���׹�Ա��");
	JLabel jl_ter=new JLabel("�����ն˺�");
	JLabel jl_visanum=new JLabel("��λǩԼ��");
	JLabel jl_dealnum=new JLabel("�������κ�");
	JLabel jl_notedate=new JLabel("��������");
	JLabel jl_formatdate=new JLabel("����ʽ��yyyymmdd��");
	JLabel jl_filepath=new JLabel("���ͻ�ģ���ļ�·��");
	
	/*�ı���*/
	JTextField jtf_orgidt=new JTextField();
	JTextField jtf_tlr=new JTextField();
	JTextField jtf_ter=new JTextField("PE");
	JTextField jtf_visanum=new JTextField();
	JTextField jtf_dealnum=new JTextField();
	JTextField jtf_notedate=new JTextField();
	JTextField jtf_filepath=new JTextField();
	
	/*��ť*/
	JButton jb_filepath=new JButton("���");
	JButton jb_crtfile=new JButton("�����ı�");
	JButton jb_clear=new JButton("����������");
	JButton jb_download=new JButton("�����ı�");
		
	/*�ļ�ѡ����*/
	JFileChooser jfc=new JFileChooser();
	JFileChooser jfc_out=new JFileChooser();
	
	/*������*/
	JProgressBar jbr;
	
	/*--------------�������-------------------*/
	String filepath;

	/*---���ú��������漰����---*/
	formatfcus guifcus;
	//Thread guidf;
	String filename="";
	String gui_orgidt="";
	String gui_dealnum="";
	String gui_tlr="";
	String gui_visanum="";
	String gui_ter="";
	String gui_notedate="";
	String gui_filepath="";
	String outfilename="";  //����ļ���
	
	/*--------------������---------------------*/
	gui_fcus()
	{
		/*���ñ���ɫ*/
		this.setBackground(Color.pink);
		jl.setBackground(Color.pink);
		jl_formatdate.setBackground(Color.pink);
		
		/*��ǩ��ɫ����*/
		jl.setOpaque(true);
		jl.setForeground(Color.red);
		jl_formatdate.setOpaque(true);
		jl_formatdate.setForeground(Color.blue);
		//jl_formatdate.setBackground(Color.red);
		
		/*��������ʼ��*/
		jbr=new JProgressBar();
		//jbr.setIndeterminate(false);
		jbr.setStringPainted(true);
		jbr.setString("������");
		jbr.setBackground(Color.lightGray);
		jbr.setForeground(Color.yellow);
		
		initialpanel();
		addlistener();
	}
	
	/*--------------����ʼ��-----------------*/
	void initialpanel()
	{
		//ʹ"�����ı�"������
		jb_download.setEnabled(false);
		 
		//������
        this.add(jl);             //-----��ǩ
        this.add(jl_orgidt);
        this.add(jl_tlr);
        this.add(jl_ter);
        this.add(jl_visanum);
        this.add(jl_dealnum);
        this.add(jl_notedate);
        this.add(jl_formatdate);
        this.add(jl_filepath);
        
        this.add(jtf_orgidt);     //-----�ı���
        this.add(jtf_tlr);
        this.add(jtf_ter);
        this.add(jtf_visanum);
        this.add(jtf_dealnum);
        this.add(jtf_notedate);
        this.add(jtf_filepath);
        
        this.add(jb_filepath);   //-----��ť
        this.add(jb_crtfile);
        this.add(jb_clear);
        this.add(jb_download);
        
        this.add(jbr);            //------������
        
        //��������
        this.setLayout(null);
        jl.setBounds(5,70,300,30);
        
        /*-------��--------*/
        jl_orgidt.setBounds(30,120,150,30);
        jtf_orgidt.setBounds(100,120,150,30);
        
        jl_tlr.setBounds(30,170,150,30);
        jtf_tlr.setBounds(100,170,150,30);
        
        jl_ter.setBounds(30,220,150,30);
        jtf_ter.setBounds(100,220,150,30);
        
        jl_notedate.setBounds(30,270,150,30);
        jtf_notedate.setBounds(100,270,150,30);
        jl_formatdate.setBounds(270,270,150,30);
        
        /*-------��--------*/
        jl_dealnum.setBounds(350,120,150,30);
        jtf_dealnum.setBounds(420,120,150,30);
        
        jl_visanum.setBounds(350,170,150,30);
        jtf_visanum.setBounds(420,170,150,30);
        
        //jl_trancode.setBounds(350,220,150,30);
        //jcb_trancode.setBounds(420,220,200,30);
        
        /*-------��---------*/
        //jl_curcde.setBounds(30,320,150,30);
        //jcb_curcde.setBounds(100,320,150,30);
        
        //jl_promocode.setBounds(350,320,150,30);
        //jcb_promocode.setBounds(420,320,150,30);
        
        //jl_outaccount.setBounds(30,370,150,30);
        //jtf_outaccount.setBounds(100,370,150,30);
        	
        jl_filepath.setBounds(30,330,150,30);
        jtf_filepath.setBounds(160,330,300,30);
        jb_filepath.setBounds(470,330,70,30);
        
        jb_crtfile.setBounds(30,400,130,30);
        
        jb_download.setBounds(235,400,130,30);
        
        jb_clear.setBounds(435,400,130,30);
        
        jbr.setBounds(205,480,200,30);
	}
	
	/*--------------���������------------------*/
	public boolean MyCheck()
	{
		boolean b=true;
		
		if(jtf_orgidt.getText().trim().equals(""))
		{
			JOptionPane.showMessageDialog(this,"���׻����Ų���Ϊ��","����С�ɰ�",JOptionPane.ERROR_MESSAGE);
			b=false;
			jtf_orgidt.grabFocus();
		}
		
		if(jtf_orgidt.getText().trim().length()!=5) //У�鳤��
		{
			JOptionPane.showMessageDialog(this,"���׻����ų���ӦΪ5λ��","����С�ɰ�",JOptionPane.ERROR_MESSAGE);
			b=false;
			jtf_orgidt.grabFocus();
		}
		
		if(jtf_tlr.getText().trim().equals(""))
		{
			JOptionPane.showMessageDialog(this,"���׹�Ա�Ų���Ϊ��","����С�ɰ�",JOptionPane.ERROR_MESSAGE);
			b=false;
			jtf_tlr.grabFocus();
		}
		
		if(jtf_tlr.getText().trim().length()!=7) //У�鳤��
		{
			JOptionPane.showMessageDialog(this,"���׹�Ա�ų���ӦΪ7λ��","����С�ɰ�",JOptionPane.ERROR_MESSAGE);
			b=false;
			jtf_tlr.grabFocus();
		}
		
		if(jtf_ter.getText().trim().equals(""))
		{
			JOptionPane.showMessageDialog(this,"�ն˺Ų���Ϊ��","����С�ɰ�",JOptionPane.ERROR_MESSAGE);
			b=false;
			jtf_ter.grabFocus();
		}
		
		if(jtf_visanum.getText().trim().equals(""))
		{
			JOptionPane.showMessageDialog(this,"��λǩԼ�Ų���Ϊ��","����С�ɰ�",JOptionPane.ERROR_MESSAGE);
			b=false;
			jtf_visanum.grabFocus();
		}
		
		if(jtf_dealnum.getText().trim().equals(""))
		{
			JOptionPane.showMessageDialog(this,"�������κŲ���Ϊ��","����С�ɰ�",JOptionPane.ERROR_MESSAGE);
			b=false;
			jtf_dealnum.grabFocus();
		}
		
		if(jtf_dealnum.getText().trim().length()!=3) //У�鳤��
		{
			JOptionPane.showMessageDialog(this,"�������κ�ӦΪ3λ","����С�ɰ�",JOptionPane.ERROR_MESSAGE);
			b=false;
			jtf_dealnum.grabFocus();
		}
		
		if(jtf_notedate.getText().trim().equals(""))
		{
			JOptionPane.showMessageDialog(this,"�������ڲ���Ϊ��","����С�ɰ�",JOptionPane.ERROR_MESSAGE);
			b=false;
			jtf_notedate.grabFocus();
		}
		
		if(jtf_notedate.getText().trim().length()!=8) //У�鳤��
		{
			JOptionPane.showMessageDialog(this,"�������ڳ���ӦΪ8λ","����С�ɰ�",JOptionPane.ERROR_MESSAGE);
			b=false;
			jtf_notedate.grabFocus();
		}
		
		if(jtf_filepath.getText().trim().equals(""))
		{
			JOptionPane.showMessageDialog(this,"���ͻ�ģ���ļ�·������Ϊ��","����С�ɰ�",JOptionPane.ERROR_MESSAGE);
			b=false;
			jtf_filepath.grabFocus();
		}
		
		return b;
	}
	
	/*--------------��Ӽ�����------------------*/
	public void addlistener()
	{
		jb_filepath.addActionListener(this);
		jb_crtfile.addActionListener(this);
		jb_clear.addActionListener(this);
		jb_download.addActionListener(this);
	}
	
	/*------------ͨ���߳������ı������ƽ�����------------*/
	public class run_JProgressBar extends Thread
	{
		public void run()
		{
			jbr.setStringPainted(true);
			jbr.setString("�ı����ڲ�����,���Ե�...");
			jbr.setIndeterminate(true); //��������
			
            guifcus=new formatfcus(gui_orgidt,gui_dealnum,gui_tlr,gui_visanum,gui_ter,gui_notedate,gui_filepath,filename,outfilename);
            
            jbr.setIndeterminate(false); //������ͣ
            //jb_crtfile.setEnabled(true); //��"�����ı�"��ť����Ϊ����
	        //jb_clear.setEnabled(true);   //��"����"��ť����Ϊ����
	        jb_download.setEnabled(true); //��"����"��ť����Ϊ����
	        jbr.setString("�ı�������,����\"�����ı�\"��ť");
	        //jtf_filepath.setEnabled(true);
		}
	}
	
	/*--------------�¼���Ӧ-------------------*/
	public void actionPerformed(ActionEvent e)
	{
		boolean flag=false;
		
		/*��Ӧ����ģ���ļ�·����ť*/
		if(e.getSource()==jb_filepath)
		{
			jfc.showOpenDialog(this);
			if(jfc.getSelectedFile()!=null)
			{
				filepath=jfc.getSelectedFile().toString();  //�ļ�·��
				filename=jfc.getName(jfc.getSelectedFile());//�ļ���
				jtf_filepath.setText(filepath);
				jtf_filepath.setEnabled(false);
				System.out.println("ѡ����ļ�·��Ϊ��"+filepath);
				System.out.println("�ļ���Ϊ��"+filename);
			}
		}
		
		/*��Ӧ�����ı���ť*/
		if(e.getSource()==jb_crtfile)
		{		
			if(this.MyCheck())
			{
			    gui_orgidt=jtf_orgidt.getText().trim();
			    System.out.println("gui������Ϊ��"+gui_orgidt);
			    
	            gui_dealnum=jtf_dealnum.getText().trim();
	            System.out.println("gui�������κ�Ϊ��"+gui_dealnum);
	            
	            gui_tlr=jtf_tlr.getText().trim();
	            System.out.println("gui��Ա��Ϊ��"+gui_tlr);
	            
	            gui_visanum=jtf_visanum.getText().trim();
	            System.out.println("gui��λǩԼ��Ϊ��"+gui_visanum);
	            
	            gui_ter=jtf_ter.getText().trim();
	            System.out.println("gui�ն˺�Ϊ��"+gui_ter);
                
                gui_notedate=jtf_notedate.getText().trim();
                System.out.println("gui��������Ϊ��"+gui_notedate);
                
	            gui_filepath=jtf_filepath.getText().trim();
	            System.out.println("guiģ��·��Ϊ��"+gui_filepath);
	            
	            jb_crtfile.setEnabled(false); //��"�����ı�"��ť����Ϊ������
	            jb_clear.setEnabled(false);   //��"����"��ť����Ϊ������
	            jb_download.setEnabled(false); //��"����"��ť����Ϊ������
	            
	            outfilename="PE.FCUS."+gui_orgidt+"."+gui_visanum+"."+gui_dealnum+".T"+gui_notedate.substring(4)+".DAT";//����ļ���
	            
	            run_JProgressBar rj=new run_JProgressBar(); //��ʼ�����ļ������н�����
	            //SwingUtilities.invokeLater(rj);//�������ŵ��¼��ɷ��̵߳Ķ�����
	            rj.start();
			}
			else
			{ 
				JOptionPane.showMessageDialog(this,"���δ������Ŀ�����������Ŀ","����С�ɰ�",JOptionPane.WARNING_MESSAGE);
			}
        }
		
	    /*��Ӧ���������ť*/
	    if(e.getSource()==jb_clear)
	    {
	       jtf_orgidt.setText("");
	       jtf_tlr.setText("");
	       jtf_ter.setText("");
	       jtf_visanum.setText("");
	       jtf_dealnum.setText("");
	       jtf_notedate.setText("");
	       jtf_filepath.setText("");
	        
	       jtf_filepath.setEnabled(true);
	        
	       filepath="";
	       System.out.println("�����������ѡ����ļ�·��Ϊ��"+filepath);
       }
		
	   /*��Ӧ�����ı���ť*/
 	   if(e.getSource()==jb_download)
	   {
	   	   File outfile=new File(outfilename);
           jfc_out.setSelectedFile(outfile);
           int returnValue=jfc_out.showSaveDialog(this);
           if (returnValue==JFileChooser.APPROVE_OPTION)
           {
           	   String writePath=jfc_out.getCurrentDirectory().getAbsolutePath()+"\\"+outfilename;
           	   System.out.println("writePath��"+writePath);
               outfile.renameTo(new File(writePath));
               JOptionPane.showMessageDialog(this,"�ı����سɹ� ^_^","����С�ɰ�",JOptionPane.INFORMATION_MESSAGE);
               jb_download.setEnabled(false);
               jbr.setString("������");
               jb_crtfile.setEnabled(true); //��"�����ı�"��ť����Ϊ����
	           jb_clear.setEnabled(true);   //��"����"��ť����Ϊ����
	           jtf_filepath.setEnabled(true);
           }
	   }
	}
	
	/*--------------����ʹ��-------------------*/
	public static void main(String args[])
	{
		gui_fcus l=new gui_fcus();
		JFrame jf=new JFrame("BPSԤ����ϵͳ--�������ͻ�FCUS");
		jf.add(l);
		jf.setVisible(true);
		jf.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
}

