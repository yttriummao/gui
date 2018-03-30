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
	/*树组件定义*/
	
	   /*根节点*/
	   DefaultMutableTreeNode root=new DefaultMutableTreeNode(new mynode("操作选项","0"));
	
	   /*批量代付*/
	   DefaultMutableTreeNode bps_df=new DefaultMutableTreeNode(new mynode("批量代付","1"));
	   DefaultMutableTreeNode crt_df=new DefaultMutableTreeNode(new mynode("生成上传文本df","11"));
	   DefaultMutableTreeNode par_df=new DefaultMutableTreeNode(new mynode("解析返回文本df","12"));
	
	   /*批量代收*/
	   DefaultMutableTreeNode bps_ds=new DefaultMutableTreeNode(new mynode("批量代收","2"));
	   DefaultMutableTreeNode crt_ds=new DefaultMutableTreeNode(new mynode("生成上传文本ds","21"));
	   DefaultMutableTreeNode par_ds=new DefaultMutableTreeNode(new mynode("解析返回文本ds","22"));
       
       /*批量开客户*/
	   DefaultMutableTreeNode bps_fcus=new DefaultMutableTreeNode(new mynode("批量开客户","3"));
	   DefaultMutableTreeNode crt_fcus=new DefaultMutableTreeNode(new mynode("生成上传文本fcus","31"));
	   DefaultMutableTreeNode par_fcus=new DefaultMutableTreeNode(new mynode("解析返回文本fcus","32"));
	   
	   /*批量开帐户*/
	   DefaultMutableTreeNode bps_fact=new DefaultMutableTreeNode(new mynode("批量开帐户","4"));
	   DefaultMutableTreeNode crt_fact=new DefaultMutableTreeNode(new mynode("生成上传文本fact","41"));
	   DefaultMutableTreeNode par_fact=new DefaultMutableTreeNode(new mynode("解析返回文本fact","42"));
	   
	   /*批量开卡*/
	   DefaultMutableTreeNode bps_bodc=new DefaultMutableTreeNode(new mynode("批量开卡","5"));
	   DefaultMutableTreeNode crt_bodc=new DefaultMutableTreeNode(new mynode("生成上传文本bodc","51"));
	   DefaultMutableTreeNode par_bodc=new DefaultMutableTreeNode(new mynode("解析返回文本bodc","52"));
	   
	   /*报表打印选项*/
	   //DefaultMutableTreeNode print=new DefaultMutableTreeNode(new mynode("报表打印","13"));
	   
	   /*树*/
	   JTree tree=new JTree(root);
	   
	/*其他组件定义*/
	JPanel jpl=new JPanel(); //创建面板
	JScrollPane jsp=new JScrollPane(tree); //创建滚动窗口
	JSplitPane jslp=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,jsp,jpl);  //创建分割窗格
	
	/*"关闭事件"内部类定义*/
	WindowCloser wc;
	
	/*卡片布局定义*/
	CardLayout cl;
	
	/*模板变量定义*/
	welcome wel=new welcome("BPS预处理系统");
	gui_df gdf=new gui_df(); //代付
	gui_ds gds=new gui_ds(); //代收
	gui_fcus gfcus=new gui_fcus(); //代开客户
	
	/*我的节点*/
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
	
	/*构造器*/
	bps()
	{
		initialtree();
		addtreelistener();
		initialjframe();
		initialjpanel();
	}
	
	/*树初始化*/
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
		
		tree.setToggleClickCount(1);  //设置树节点展开的点击次数
	}
	
	/*Windows窗口关闭事件类*/
	public class WindowCloser extends WindowAdapter
    {
	    public void windowClosing(WindowEvent ew)
	    {
	    	System.out.println("关闭窗口");
		    JOptionPane.showMessageDialog(jpl,"谢谢使用，再见  ^_^","纯情小可爱",JOptionPane.INFORMATION_MESSAGE);
	    }
    }
	
	/*添加监听器*/
	void addtreelistener()
	{
		tree.addTreeSelectionListener(this);  //添加"树"监听
		wc=new WindowCloser();
		this.addWindowListener(wc);           //添加"窗口关闭"监听
	}
	
	/*树节点点击事件处理*/
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
				 System.out.println("生成上传文本df");
				 break;
		    case 12:
		    	 System.out.println("解析返回文本df");
		    	 break;
		    case 21:
		    	 cl.show(jpl,"gui_ds");
		    	 System.out.println("生成上传文本ds");
		    	 break;
		    case 22:
		    	 System.out.println("解析返回文本ds");
		    	 break;
		    case 31:
		    	 cl.show(jpl,"gui_fcus");
		    	 System.out.println("生成上传文本fcus");
		    	 break;
		    case 32:
		    	 System.out.println("解析返回文本fcus");
		    	 break;
		    case 41:
		    	 System.out.println("生产上传文本fact");
		    	 break;
		    case 42:
		    	 System.out.println("解析返回文本fact");
		    	 break;
		    case 51:
		    	 System.out.println("生成上传文本bodc");
		    	 break;
		    case 52:
		    	 System.out.println("解析返回文本bodc");
		    	 break;	 
		}
	}
	
	/*JFrame初始化*/
	void initialjframe()
	{
		this.add(jslp);
		jslp.setDividerLocation(170);//设置分隔条的位置
		this.setSize(1000,700);  //设置窗体大小
		//this.setExtendedState(MAXIMIZED_BOTH); //设置窗体初始大小
		this.setResizable(true);  //设置窗体是否可以手动调节
	}
	
	/*JPanel初始化*/
	void initialjpanel()
	{
		jpl.setLayout(new CardLayout());
		jpl.add(wel,"welcome"); //增加"欢迎"模块
		jpl.add(gdf,"gui_df");  //增加"批量代付"模块
		jpl.add(gds,"gui_ds");  //增加"批量代收"模块
		jpl.add(gfcus,"gui_fcus");  //增加"批量代开客户"模块
		cl=(CardLayout)jpl.getLayout();
		cl.show(jpl,"welcome"); //默认显示"欢迎"模块
	}
	
	
	public static void main(String args[])
	{
		bps b=new bps();
		b.setTitle("中国银行bps预处理系统");
		b.setVisible(true);
		b.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //用户在此窗体上发起 "close" 时默认执行的操作
	}
	
}