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
	/*--------------定义组件-------------------*/
	/*标签*/
	JLabel jl=new JLabel("批量代付输入界面DF：");
	JLabel jl_orgidt=new JLabel("交易机构号");
	JLabel jl_tlr=new JLabel("交易柜员号");
	JLabel jl_ter=new JLabel("交易终端号");
	JLabel jl_visanum=new JLabel("单位签约号");
	JLabel jl_dealnum=new JLabel("数据批次号");
	JLabel jl_notedate=new JLabel("记账日期");
	JLabel jl_formatdate=new JLabel("（格式：yyyymmdd）");
	JLabel jl_trancode=new JLabel("交易码");
	JLabel jl_filepath=new JLabel("代付模板文件路径");
	JLabel jl_curcde=new JLabel("交易货币");
	JLabel jl_promocode=new JLabel("收费选项");
	JLabel jl_outaccount=new JLabel("转出账号");
	
	/*文本框*/
	JTextField jtf_orgidt=new JTextField();
	JTextField jtf_tlr=new JTextField();
	JTextField jtf_ter=new JTextField("PE");
	JTextField jtf_visanum=new JTextField();
	JTextField jtf_dealnum=new JTextField();
	JTextField jtf_notedate=new JTextField();
	JTextField jtf_filepath=new JTextField();
	JTextField jtf_outaccount=new JTextField();
	
	/*按钮*/
	JButton jb_filepath=new JButton("浏览");
	JButton jb_crtfile=new JButton("生成文本");
	JButton jb_clear=new JButton("重置输入项");
	JButton jb_download=new JButton("下载文本");
	
	/*下拉框*/
	JComboBox jcb_trancode;
	JComboBox jcb_curcde;
	JComboBox jcb_promocode;
	
	/*文件选择器*/
	JFileChooser jfc=new JFileChooser();
	JFileChooser jfc_out=new JFileChooser();
	
	/*进度条*/
	JProgressBar jbr;
	
	/*--------------定义变量-------------------*/
	String trancode_array[]={"客户账号<--1045-->客户账号","BGL账户<--21051-->客户账号","客户账号<--21031-->BGL账户","BGL账号<--20045-->BGL账号"};
	String curcde_array[]={"人民币<--CNY-->"};
	String promocode_array[]={"不收费<--NF-->"};
	String filepath;
	//File mfile;
	Map<String,String> trancode_map=new HashMap<String,String>();
	/*---调用函数处理涉及变量---*/
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
	String outfilename="";  //输出文件名
	
	/*--------------构造器---------------------*/
	gui_df()
	{
		/*设置背景色*/
		this.setBackground(Color.orange);
		jl.setBackground(Color.orange);
		jl_formatdate.setBackground(Color.orange);
		
		/*初始化map集合*/
		initialMap();
		
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
		jbr.setBackground(Color.black);
		jbr.setForeground(Color.red);
		
		/*下拉框初始化*/
		jcb_trancode=new JComboBox(trancode_array);
		jcb_curcde=new JComboBox(curcde_array);
		jcb_promocode=new JComboBox(promocode_array);
		
		initialpanel();
		addlistener();
	}
	
	/*--------------Map始化--0-----------------*/
	void initialMap()
	{
		trancode_map.put("客户账号<--1045-->客户账号","1045");
		trancode_map.put("BGL账户<--21051-->客户账号","21051");
		trancode_map.put("客户账号<--21031-->BGL账户","21031");
		trancode_map.put("BGL账号<--20045-->BGL账号","20045");
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
        this.add(jl_trancode);
        this.add(jl_filepath);
        this.add(jl_curcde);
        this.add(jl_promocode);
        this.add(jl_outaccount);
        
        this.add(jtf_orgidt);     //-----文本框
        this.add(jtf_tlr);
        this.add(jtf_ter);
        this.add(jtf_visanum);
        this.add(jtf_dealnum);
        this.add(jtf_notedate);
        this.add(jcb_trancode);
        this.add(jtf_filepath);
        this.add(jtf_outaccount);
        
        this.add(jb_filepath);   //-----按钮
        this.add(jb_crtfile);
        this.add(jb_clear);
        this.add(jb_download);
        
        this.add(jcb_trancode);          //-----下拉框
        this.add(jcb_curcde);
        this.add(jcb_promocode);
        
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
        
        jl_trancode.setBounds(350,220,150,30);
        jcb_trancode.setBounds(420,220,200,30);
        
        /*-------下---------*/
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
			JOptionPane.showMessageDialog(this,"代付模板文件路径","纯情小可爱",JOptionPane.ERROR_MESSAGE);
			b=false;
			jtf_filepath.grabFocus();
		}
		
		if(jtf_outaccount.getText().trim().equals(""))
		{
			JOptionPane.showMessageDialog(this,"转出帐户不能为空","纯情小可爱",JOptionPane.ERROR_MESSAGE);
			b=false;
			jtf_outaccount.grabFocus();
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
			
			/*产生文本*/
            guidf=new formatagnt_df(gui_orgidt,gui_dealnum,gui_tlr,gui_visanum,gui_ter,gui_trancode,gui_notedate,gui_curcde,gui_promocode,filepath,gui_outaccount,filename,outfilename);
            
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
	            
	            gui_trancode=(String)trancode_map.get((String)jcb_trancode.getSelectedItem());   //通过下拉列表所选项进行Map集合的键值匹配
                System.out.println("gui交易码为："+gui_trancode);
                
                gui_notedate=jtf_notedate.getText().trim();
                System.out.println("gui记账日期为："+gui_notedate);
                
	            gui_curcde="CNY";    //readagnt_df函数中并没有带入
	            System.out.println("gui货币码为："+gui_curcde);
	            
                gui_promocode="NF";  //readagnt_df函数中并没有带入
                System.out.println("gui收费选项为："+gui_promocode);
                
	            gui_filepath=jtf_filepath.getText().trim();
	            System.out.println("gui模板路径为："+gui_filepath);
	            
	            gui_outaccount=jtf_outaccount.getText().trim();
	            System.out.println("gui装转账号为："+gui_outaccount);
	            
	            jb_crtfile.setEnabled(false); //将"生产文本"按钮设置为不可用
	            jb_clear.setEnabled(false);   //将"重置"按钮设置为不可用
	            jb_download.setEnabled(false); //将"下载"按钮设置为不可用
	            
	            outfilename="PE.AGNT."+gui_orgidt+"."+gui_visanum+"."+gui_dealnum+".T"+gui_notedate.substring(4)+".DAT";//输出文件名
	            
	            run_JProgressBar rj=new run_JProgressBar(); //开始生产文件并运行进度条
	            //SwingUtilities.invokeLater(rj);//将对象排到事件派发线程的队列中
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
	            	       //JOptionPane.showMessageDialog(this,"批量代付电子模板填写列数不正确","纯情小可爱",JOptionPane.ERROR_MESSAGE);
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
	                JOptionPane.showMessageDialog(this,"批量代付电子模板填写列数不正确","纯情小可爱",JOptionPane.ERROR_MESSAGE);
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
	            	System.out.println("子进程还在执行：");
	            }

	            jbr.setIndeterminate(false);
	            */
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
	       jtf_outaccount.setText("");
	        
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
               boolean flagsuc=outfile.renameTo(new File(writePath));
               if(flagsuc)
               {
               	   System.out.println("文件"+outfilename+"重命名成功");
               }
               else
               {
               	   System.out.println("文件"+outfilename+"重命名失败!!!");
               }
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
		gui_df l=new gui_df();
		JFrame jf=new JFrame("BPS预处理系统--批量代付df");
		jf.add(l);
		jf.setVisible(true);
		jf.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
}

