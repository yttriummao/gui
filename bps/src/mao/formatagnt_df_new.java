package mao;

import java.io.*;
import javax.swing.*;
import java.lang.Thread;

public class formatagnt_df_new extends Thread
{
	String gui_orgidt;
	String gui_dealnum;
	String gui_tlr;
	String gui_visanum;
	String gui_ter;
	String gui_trancode;
	String gui_notedate;
	String gui_curcde;
	String gui_promocode;
	String gui_filepath;
	String gui_outaccount;
	//JProgressBar jbr;
	
	/*从GUI传递过来的参数
	  参数说明：
	  gui_orgidt：交易机构号
	  gui_dealnum：数据批次号
	  gui_tlr：交易柜员号
	  gui_visanum：签约单位号
	  gui_ter：终端号
	  gui_trancode：交易码
	  gui_notedate：记账日期
	  gui_curcde：交易货币
	  gui_promocode：收费选项
	  gui_filepath：模板文件路径
	  gui_outaccount：转出账号
	*/
	formatagnt_df_new(String c_orgidt,String c_dealnum,String c_tlr,String c_visanum,String c_ter,String c_trancode,String c_notedate,String c_curcde,String c_promocode,String c_filepath,String c_outaccount)
	{
		this.gui_orgidt=c_orgidt;
	    this.gui_dealnum=c_dealnum;
	    this.gui_tlr=c_tlr;
	    this.gui_visanum=c_visanum;
	    this.gui_ter=c_ter;
	    this.gui_trancode=c_trancode;
	    this.gui_notedate=c_notedate;
	    this.gui_curcde=c_curcde;
	    this.gui_promocode=c_promocode;
	    this.gui_filepath=c_filepath;
	    this.gui_outaccount=c_outaccount;
	    //this.jbr=c_jbr;
	}
	
	public void run()
	{
		String head;
		String body;
	    readagnt_df ra_df=new readagnt_df(gui_filepath);
	    
	    /* 检查模板填写的列数 */
	    try
	    {
	        if(ra_df.st.getColumns()!=7)
		    {
		        throw new MyException("批量代付电子模板填写列数不正确，正确应为7列，请检查!");
		    }
	    }
		catch(MyException me)
		{
			System.err.println(me.toString());
			//System.exit(0);
		}
		
		ra_df.put_into_arraylist();
		head=ra_df.deal_with_head("gui_filepath","gui_orgidt","gui_tlr","gui_ter","gui_visanum","gui_dealnum","gui_notedate");
		
		/* 输出头文件 */
	    try
	    {
	    	PrintStream ps_head=new PrintStream(new FileOutputStream("PE.AGNT.00024.0000000000.103.T0403.DAT",false));
	    	ps_head.println(head);
	    }
        catch(IOException ioe1)
        {
        	System.err.println(ioe1.toString());
        }
        
        /* 输出文件体 */
		for(int i=0;i<ra_df.relist.size();i++)
		{
			body=ra_df.deal_with_body(i,"gui_orgidt","gui_trancode","gui_outaccount","NF");
			try
			{
				PrintStream ps_body=new PrintStream(new FileOutputStream("PE.AGNT.00024.0000000000.103.T0403.DAT",true));
				ps_body.println(body);
			}
			catch(IOException ioe2)
			{
				System.err.println(ioe2.toString());
			}
			
			/*
			try
			{
				Thread.sleep(100);
			}
			catch(InterruptedException e)
			{
		    }*/
		//jbr.setIndeterminate(false);
		}
		
		//System.exit(0);
	}
}