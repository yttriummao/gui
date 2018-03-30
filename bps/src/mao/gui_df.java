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

public class gui_df extends JPanel implements ActionListener
{
	/*--------------�������-------------------*/
	/*��ǩ*/
	JLabel jl=new JLabel("���������������DF��");
	JLabel jl_orgidt=new JLabel("���׻�����");
	JLabel jl_tlr=new JLabel("���׹�Ա��");
	JLabel jl_ter=new JLabel("�����ն˺�");
	JLabel jl_visanum=new JLabel("��λǩԼ��");
	JLabel jl_dealnum=new JLabel("�������κ�");
	JLabel jl_notedate=new JLabel("��������");
	JLabel jl_formatdate=new JLabel("����ʽ��yyyymmdd��");
	JLabel jl_trancode=new JLabel("������");
	JLabel jl_filepath=new JLabel("����ģ���ļ�·��");
	JLabel jl_curcde=new JLabel("���׻���");
	JLabel jl_promocode=new JLabel("�շ�ѡ��");
	JLabel jl_outaccount=new JLabel("ת���˺�");
	
	/*�ı���*/
	JTextField jtf_orgidt=new JTextField();
	JTextField jtf_tlr=new JTextField();
	JTextField jtf_ter=new JTextField("PE");
	JTextField jtf_visanum=new JTextField();
	JTextField jtf_dealnum=new JTextField();
	JTextField jtf_notedate=new JTextField();
	JTextField jtf_filepath=new JTextField();
	JTextField jtf_outaccount=new JTextField();
	
	/*��ť*/
	JButton jb_filepath=new JButton("���");
	JButton jb_crtfile=new JButton("�����ı�");
	JButton jb_clear=new JButton("����������");
	JButton jb_download=new JButton("�����ı�");
	
	/*������*/
	JComboBox jcb_trancode;
	JComboBox jcb_curcde;
	JComboBox jcb_promocode;
	
	/*�ļ�ѡ����*/
	JFileChooser jfc=new JFileChooser();
	JFileChooser jfc_out=new JFileChooser();
	
	/*������*/
	JProgressBar jbr;
	
	/*--------------�������-------------------*/
	String trancode_array[]={"�ͻ��˺�<--1045-->�ͻ��˺�","BGL�˻�<--21051-->�ͻ��˺�","�ͻ��˺�<--21031-->BGL�˻�","BGL�˺�<--20045-->BGL�˺�"};
	String curcde_array[]={"�����<--CNY-->"};
	String promocode_array[]={"���շ�<--NF-->"};
	String filepath;
	//File mfile;
	Map<String,String> trancode_map=new HashMap<String,String>();
	/*---���ú��������漰����---*/
	formatagnt_df guidf;
	//Thread guidf;
	String filename="";
	String gui_orgidt="";
	String gui_dealnum="";
	String gui_tlr="";
	String gui_visanum="";
	String gui_ter="";
	String gui_trancode="";
	String gui_notedate="";
	String gui_curcde="";
	String gui_promocode="";
	String gui_filepath="";
	String gui_outaccount="";
	String outfilename="";  //����ļ���
	
	/*--------------������---------------------*/
	gui_df()
	{
		/*���ñ���ɫ*/
		this.setBackground(Color.orange);
		jl.setBackground(Color.orange);
		jl_formatdate.setBackground(Color.orange);
		
		/*��ʼ��map����*/
		initialMap();
		
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
		jbr.setBackground(Color.black);
		jbr.setForeground(Color.red);
		
		/*�������ʼ��*/
		jcb_trancode=new JComboBox(trancode_array);
		jcb_curcde=new JComboBox(curcde_array);
		jcb_promocode=new JComboBox(promocode_array);
		
		initialpanel();
		addlistener();
	}
	
	/*--------------Mapʼ��--0-----------------*/
	void initialMap()
	{
		trancode_map.put("�ͻ��˺�<--1045-->�ͻ��˺�","1045");
		trancode_map.put("BGL�˻�<--21051-->�ͻ��˺�","21051");
		trancode_map.put("�ͻ��˺�<--21031-->BGL�˻�","21031");
		trancode_map.put("BGL�˺�<--20045-->BGL�˺�","20045");
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
        this.add(jl_trancode);
        this.add(jl_filepath);
        this.add(jl_curcde);
        this.add(jl_promocode);
        this.add(jl_outaccount);
        
        this.add(jtf_orgidt);     //-----�ı���
        this.add(jtf_tlr);
        this.add(jtf_ter);
        this.add(jtf_visanum);
        this.add(jtf_dealnum);
        this.add(jtf_notedate);
        this.add(jcb_trancode);
        this.add(jtf_filepath);
        this.add(jtf_outaccount);
        
        this.add(jb_filepath);   //-----��ť
        this.add(jb_crtfile);
        this.add(jb_clear);
        this.add(jb_download);
        
        this.add(jcb_trancode);          //-----������
        this.add(jcb_curcde);
        this.add(jcb_promocode);
        
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
        
        jl_trancode.setBounds(350,220,150,30);
        jcb_trancode.setBounds(420,220,200,30);
        
        /*-------��---------*/
        jl_curcde.setBounds(30,320,150,30);
        jcb_curcde.setBounds(100,320,150,30);
        
        jl_promocode.setBounds(350,320,150,30);
        jcb_promocode.setBounds(420,320,150,30);
        
        jl_outaccount.setBounds(30,370,150,30);
        jtf_outaccount.setBounds(100,370,150,30);
        	
        jl_filepath.setBounds(30,430,150,30);
        jtf_filepath.setBounds(140,430,300,30);
        jb_filepath.setBounds(450,430,70,30);
        
        jb_crtfile.setBounds(30,500,130,30);
        
        jb_download.setBounds(235,500,130,30);
        
        jb_clear.setBounds(435,500,130,30);
        
        jbr.setBounds(205,600,200,30);
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
			JOptionPane.showMessageDialog(this,"����ģ���ļ�·��","����С�ɰ�",JOptionPane.ERROR_MESSAGE);
			b=false;
			jtf_filepath.grabFocus();
		}
		
		if(jtf_outaccount.getText().trim().equals(""))
		{
			JOptionPane.showMessageDialog(this,"ת���ʻ�����Ϊ��","����С�ɰ�",JOptionPane.ERROR_MESSAGE);
			b=false;
			jtf_outaccount.grabFocus();
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
			
			/*�����ı�*/
            guidf=new formatagnt_df(gui_orgidt,gui_dealnum,gui_tlr,gui_visanum,gui_ter,gui_trancode,gui_notedate,gui_curcde,gui_promocode,filepath,gui_outaccount,filename,outfilename);
            
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
	            
	            gui_trancode=(String)trancode_map.get((String)jcb_trancode.getSelectedItem());   //ͨ�������б���ѡ�����Map���ϵļ�ֵƥ��
                System.out.println("gui������Ϊ��"+gui_trancode);
                
                gui_notedate=jtf_notedate.getText().trim();
                System.out.println("gui��������Ϊ��"+gui_notedate);
                
	            gui_curcde="CNY";    //readagnt_df�����в�û�д���
	            System.out.println("gui������Ϊ��"+gui_curcde);
	            
                gui_promocode="NF";  //readagnt_df�����в�û�д���
                System.out.println("gui�շ�ѡ��Ϊ��"+gui_promocode);
                
	            gui_filepath=jtf_filepath.getText().trim();
	            System.out.println("guiģ��·��Ϊ��"+gui_filepath);
	            
	            gui_outaccount=jtf_outaccount.getText().trim();
	            System.out.println("guiװת�˺�Ϊ��"+gui_outaccount);
	            
	            jb_crtfile.setEnabled(false); //��"�����ı�"��ť����Ϊ������
	            jb_clear.setEnabled(false);   //��"����"��ť����Ϊ������
	            jb_download.setEnabled(false); //��"����"��ť����Ϊ������
	            
	            outfilename="PE.AGNT."+gui_orgidt+"."+gui_visanum+"."+gui_dealnum+".T"+gui_notedate.substring(4)+".DAT";//����ļ���
	            
	            run_JProgressBar rj=new run_JProgressBar(); //��ʼ�����ļ������н�����
	            //SwingUtilities.invokeLater(rj);//�������ŵ��¼��ɷ��̵߳Ķ�����
	            rj.start();
	            
	            /*
	            new Thread()
	            {
	            	public void run()
	            	{
	            	    try
	                    {
	            	       guidf=new formatagnt_df(gui_orgidt,gui_dealnum,gui_tlr,gui_visanum,gui_ter,gui_trancode,gui_notedate,gui_curcde,gui_promocode,filepath,gui_outaccount);
	            	       guidf.setPriority(Thread.MIN_PRIORITY);
	            	       guidf.run();
	                    }
	                    catch(Exception exc)
	                    {
	            	       //JOptionPane.showMessageDialog(this,"������������ģ����д��������ȷ","����С�ɰ�",JOptionPane.ERROR_MESSAGE);
	                    }	
	            	}
	            }.start();
	            */

	            /*
	            jbr.setIndeterminate(true);
	            try
	            {
	                guidf=new formatagnt_df(gui_orgidt,gui_dealnum,gui_tlr,gui_visanum,gui_ter,gui_trancode,gui_notedate,gui_curcde,gui_promocode,filepath,gui_outaccount);
	                guidf.setPriority(Thread.MIN_PRIORITY);
	            	guidf.start();
	            }
	            catch(Exception exc)
	            {
	                JOptionPane.showMessageDialog(this,"������������ģ����д��������ȷ","����С�ɰ�",JOptionPane.ERROR_MESSAGE);
	            }
	            while(guidf.isAlive())
	            {
	                //jbr.setIndeterminate(true);
	                //jbr.setValue(jbr_value+1);
	                //Thread stepper = new BarThread(jbr);
	                //stepper.start();
	                try
	                {
	                	Thread.sleep(50);
	                }
	                catch(InterruptedException ie)
	                {
	                }
	            	System.out.println("�ӽ��̻���ִ�У�");
	            }

	            jbr.setIndeterminate(false);
	            */
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
	       jtf_outaccount.setText("");
	        
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
               boolean flagsuc=outfile.renameTo(new File(writePath));
               if(flagsuc)
               {
               	   System.out.println("�ļ�"+outfilename+"�������ɹ�");
               }
               else
               {
               	   System.out.println("�ļ�"+outfilename+"������ʧ��!!!");
               }
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
		gui_df l=new gui_df();
		JFrame jf=new JFrame("BPSԤ����ϵͳ--��������df");
		jf.add(l);
		jf.setVisible(true);
		jf.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
}

