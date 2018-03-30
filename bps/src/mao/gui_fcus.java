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
	/*--------------定义组件-------------------*/
	/*标签*/
	JLabel jl=new JLabel("批量开客户输入界面FCUS：");
	JLabel jl_orgidt=new JLabel("交易机构号");
	JLabel jl_tlr=new JLabel("交易柜员号");
	JLabel jl_ter=new JLabel("交易终端号");
	JLabel jl_visanum=new JLabel("单位签约号");
	JLabel jl_dealnum=new JLabel("数据批次号");
	JLabel jl_notedate=new JLabel("记账日期");
	JLabel jl_formatdate=new JLabel("（格式：yyyymmdd）");
	JLabel jl_filepath=new JLabel("开客户模板文件路径");
	
	/*文本框*/
	JTextField jtf_orgidt=new JTextField();
	JTextField jtf_tlr=new JTextField();
	JTextField jtf_ter=new JTextField("PE");
	JTextField jtf_visanum=new JTextField();
	JTextField jtf_dealnum=new JTextField();
	JTextField jtf_notedate=new JTextField();
	JTextField jtf_filepath=new JTextField();
	
	/*按钮*/
	JButton jb_filepath=new JButton("浏览");
	JButton jb_crtfile=new JButton("生成文本");
	JButton jb_clear=new JButton("重置输入项");
	JButton jb_download=new JButton("下载文本");
		
	/*文件选择器*/
	JFileChooser jfc=new JFileChooser();
	JFileChooser jfc_out=new JFileChooser();
	
	/*进度条*/
	JProgressBar jbr;
	
	/*--------------定义变量-------------------*/
	String filepath;

	/*---调用函数处理涉及变量---*/
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
	String outfilename="";  //输出文件名
	
	/*--------------构造器---------------------*/
	gui_fcus()
	{
		/*设置背景色*/
		this.setBackground(Color.pink);
		jl.setBackground(Color.pink);
		jl_formatdate.setBackground(Color.pink);
		
		/*标签颜色设置*/
		jl.setOpaque(true);
		jl.setForeground(Color.red);
		jl_formatdate.setOpaque(true);
		jl_formatdate.setForeground(Color.blue);
		//jl_formatdate.setBackground(Color.red);
		
		/*进度条初始化*/
		jbr=new JProgressBar();
		//jbr.setIndeterminate(false);
		jbr.setStringPainted(true);
		jbr.setString("进度条");
		jbr.setBackground(Color.lightGray);
		jbr.setForeground(Color.yellow);
		
		initialpanel();
		addlistener();
	}
	
	/*--------------面板初始化-----------------*/
	void initialpanel()
	{
		//使"下载文本"不可用
		jb_download.setEnabled(false);
		 
		//添加组件
        this.add(jl);             //-----标签
        this.add(jl_orgidt);
        this.add(jl_tlr);
        this.add(jl_ter);
        this.add(jl_visanum);
        this.add(jl_dealnum);
        this.add(jl_notedate);
        this.add(jl_formatdate);
        this.add(jl_filepath);
        
        this.add(jtf_orgidt);     //-----文本框
        this.add(jtf_tlr);
        this.add(jtf_ter);
        this.add(jtf_visanum);
        this.add(jtf_dealnum);
        this.add(jtf_notedate);
        this.add(jtf_filepath);
        
        this.add(jb_filepath);   //-----按钮
        this.add(jb_crtfile);
        this.add(jb_clear);
        this.add(jb_download);
        
        this.add(jbr);            //------进度条
        
        //布局设置
        this.setLayout(null);
        jl.setBounds(5,70,300,30);
        
        /*-------左--------*/
        jl_orgidt.setBounds(30,120,150,30);
        jtf_orgidt.setBounds(100,120,150,30);
        
        jl_tlr.setBounds(30,170,150,30);
        jtf_tlr.setBounds(100,170,150,30);
        
        jl_ter.setBounds(30,220,150,30);
        jtf_ter.setBounds(100,220,150,30);
        
        jl_notedate.setBounds(30,270,150,30);
        jtf_notedate.setBounds(100,270,150,30);
        jl_formatdate.setBounds(270,270,150,30);
        
        /*-------右--------*/
        jl_dealnum.setBounds(350,120,150,30);
        jtf_dealnum.setBounds(420,120,150,30);
        
        jl_visanum.setBounds(350,170,150,30);
        jtf_visanum.setBounds(420,170,150,30);
        
        //jl_trancode.setBounds(350,220,150,30);
        //jcb_trancode.setBounds(420,220,200,30);
        
        /*-------下---------*/
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
	
	/*--------------检查输入项------------------*/
	public boolean MyCheck()
	{
		boolean b=true;
		
		if(jtf_orgidt.getText().trim().equals(""))
		{
			JOptionPane.showMessageDialog(this,"交易机构号不能为空","纯情小可爱",JOptionPane.ERROR_MESSAGE);
			b=false;
			jtf_orgidt.grabFocus();
		}
		
		if(jtf_orgidt.getText().trim().length()!=5) //校验长度
		{
			JOptionPane.showMessageDialog(this,"交易机构号长度应为5位！","纯情小可爱",JOptionPane.ERROR_MESSAGE);
			b=false;
			jtf_orgidt.grabFocus();
		}
		
		if(jtf_tlr.getText().trim().equals(""))
		{
			JOptionPane.showMessageDialog(this,"交易柜员号不能为空","纯情小可爱",JOptionPane.ERROR_MESSAGE);
			b=false;
			jtf_tlr.grabFocus();
		}
		
		if(jtf_tlr.getText().trim().length()!=7) //校验长度
		{
			JOptionPane.showMessageDialog(this,"交易柜员号长度应为7位！","纯情小可爱",JOptionPane.ERROR_MESSAGE);
			b=false;
			jtf_tlr.grabFocus();
		}
		
		if(jtf_ter.getText().trim().equals(""))
		{
			JOptionPane.showMessageDialog(this,"终端号不能为空","纯情小可爱",JOptionPane.ERROR_MESSAGE);
			b=false;
			jtf_ter.grabFocus();
		}
		
		if(jtf_visanum.getText().trim().equals(""))
		{
			JOptionPane.showMessageDialog(this,"单位签约号不能为空","纯情小可爱",JOptionPane.ERROR_MESSAGE);
			b=false;
			jtf_visanum.grabFocus();
		}
		
		if(jtf_dealnum.getText().trim().equals(""))
		{
			JOptionPane.showMessageDialog(this,"数据批次号不能为空","纯情小可爱",JOptionPane.ERROR_MESSAGE);
			b=false;
			jtf_dealnum.grabFocus();
		}
		
		if(jtf_dealnum.getText().trim().length()!=3) //校验长度
		{
			JOptionPane.showMessageDialog(this,"数据批次号应为3位","纯情小可爱",JOptionPane.ERROR_MESSAGE);
			b=false;
			jtf_dealnum.grabFocus();
		}
		
		if(jtf_notedate.getText().trim().equals(""))
		{
			JOptionPane.showMessageDialog(this,"记账日期不能为空","纯情小可爱",JOptionPane.ERROR_MESSAGE);
			b=false;
			jtf_notedate.grabFocus();
		}
		
		if(jtf_notedate.getText().trim().length()!=8) //校验长度
		{
			JOptionPane.showMessageDialog(this,"记账日期长度应为8位","纯情小可爱",JOptionPane.ERROR_MESSAGE);
			b=false;
			jtf_notedate.grabFocus();
		}
		
		if(jtf_filepath.getText().trim().equals(""))
		{
			JOptionPane.showMessageDialog(this,"开客户模板文件路径不能为空","纯情小可爱",JOptionPane.ERROR_MESSAGE);
			b=false;
			jtf_filepath.grabFocus();
		}
		
		return b;
	}
	
	/*--------------添加监听器------------------*/
	public void addlistener()
	{
		jb_filepath.addActionListener(this);
		jb_crtfile.addActionListener(this);
		jb_clear.addActionListener(this);
		jb_download.addActionListener(this);
	}
	
	/*------------通过线程生产文本并绘制进度条------------*/
	public class run_JProgressBar extends Thread
	{
		public void run()
		{
			jbr.setStringPainted(true);
			jbr.setString("文本正在产生中,请稍等...");
			jbr.setIndeterminate(true); //进度条启
			
            guifcus=new formatfcus(gui_orgidt,gui_dealnum,gui_tlr,gui_visanum,gui_ter,gui_notedate,gui_filepath,filename,outfilename);
            
            jbr.setIndeterminate(false); //进度条停
            //jb_crtfile.setEnabled(true); //将"生产文本"按钮设置为可用
	        //jb_clear.setEnabled(true);   //将"重置"按钮设置为可用
	        jb_download.setEnabled(true); //将"下载"按钮设置为可用
	        jbr.setString("文本已生成,请点击\"下载文本\"按钮");
	        //jtf_filepath.setEnabled(true);
		}
	}
	
	/*--------------事件响应-------------------*/
	public void actionPerformed(ActionEvent e)
	{
		boolean flag=false;
		
		/*响应代付模板文件路径按钮*/
		if(e.getSource()==jb_filepath)
		{
			jfc.showOpenDialog(this);
			if(jfc.getSelectedFile()!=null)
			{
				filepath=jfc.getSelectedFile().toString();  //文件路径
				filename=jfc.getName(jfc.getSelectedFile());//文件名
				jtf_filepath.setText(filepath);
				jtf_filepath.setEnabled(false);
				System.out.println("选择的文件路径为："+filepath);
				System.out.println("文件名为："+filename);
			}
		}
		
		/*响应生成文本按钮*/
		if(e.getSource()==jb_crtfile)
		{		
			if(this.MyCheck())
			{
			    gui_orgidt=jtf_orgidt.getText().trim();
			    System.out.println("gui机构号为："+gui_orgidt);
			    
	            gui_dealnum=jtf_dealnum.getText().trim();
	            System.out.println("gui数据批次号为："+gui_dealnum);
	            
	            gui_tlr=jtf_tlr.getText().trim();
	            System.out.println("gui柜员号为："+gui_tlr);
	            
	            gui_visanum=jtf_visanum.getText().trim();
	            System.out.println("gui单位签约号为："+gui_visanum);
	            
	            gui_ter=jtf_ter.getText().trim();
	            System.out.println("gui终端号为："+gui_ter);
                
                gui_notedate=jtf_notedate.getText().trim();
                System.out.println("gui记账日期为："+gui_notedate);
                
	            gui_filepath=jtf_filepath.getText().trim();
	            System.out.println("gui模板路径为："+gui_filepath);
	            
	            jb_crtfile.setEnabled(false); //将"生产文本"按钮设置为不可用
	            jb_clear.setEnabled(false);   //将"重置"按钮设置为不可用
	            jb_download.setEnabled(false); //将"下载"按钮设置为不可用
	            
	            outfilename="PE.FCUS."+gui_orgidt+"."+gui_visanum+"."+gui_dealnum+".T"+gui_notedate.substring(4)+".DAT";//输出文件名
	            
	            run_JProgressBar rj=new run_JProgressBar(); //开始生产文件并运行进度条
	            //SwingUtilities.invokeLater(rj);//将对象排到事件派发线程的队列中
	            rj.start();
			}
			else
			{ 
				JOptionPane.showMessageDialog(this,"请填补未输入项目或更正错误项目","纯情小可爱",JOptionPane.WARNING_MESSAGE);
			}
        }
		
	    /*响应重置输入项按钮*/
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
	       System.out.println("重置输入项后选择的文件路径为："+filepath);
       }
		
	   /*响应下载文本按钮*/
 	   if(e.getSource()==jb_download)
	   {
	   	   File outfile=new File(outfilename);
           jfc_out.setSelectedFile(outfile);
           int returnValue=jfc_out.showSaveDialog(this);
           if (returnValue==JFileChooser.APPROVE_OPTION)
           {
           	   String writePath=jfc_out.getCurrentDirectory().getAbsolutePath()+"\\"+outfilename;
           	   System.out.println("writePath："+writePath);
               outfile.renameTo(new File(writePath));
               JOptionPane.showMessageDialog(this,"文本下载成功 ^_^","纯情小可爱",JOptionPane.INFORMATION_MESSAGE);
               jb_download.setEnabled(false);
               jbr.setString("进度条");
               jb_crtfile.setEnabled(true); //将"生产文本"按钮设置为可用
	           jb_clear.setEnabled(true);   //将"重置"按钮设置为可用
	           jtf_filepath.setEnabled(true);
           }
	   }
	}
	
	/*--------------测试使用-------------------*/
	public static void main(String args[])
	{
		gui_fcus l=new gui_fcus();
		JFrame jf=new JFrame("BPS预处理系统--批量开客户FCUS");
		jf.add(l);
		jf.setVisible(true);
		jf.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
}

