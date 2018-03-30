package mao;

import java.io.*;
import javax.swing.*;
import java.lang.Thread;

public class formatagnt_df extends Thread
{
	PrintStream ps_body;
	PrintStream ps_head;
	
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
	  gui_filename：文件名
	  gui_outfilename：输出文件名
	*/
	public formatagnt_df(String gui_orgidt,String gui_dealnum,String gui_tlr,String gui_visanum,String gui_ter,String gui_trancode,String gui_notedate,String gui_curcde,String gui_promocode,String gui_filepath,String gui_outaccount,String gui_filename,String gui_outfilename)
	{
		String head;
		String body;
	    readagnt_df ra_df=new readagnt_df(gui_filepath);
	    
	    System.out.println("输出文件名："+gui_outfilename);
	    
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
		head=ra_df.deal_with_head(gui_filename,gui_orgidt,gui_tlr,gui_ter,gui_visanum,gui_dealnum,gui_notedate);
		
		/* 输出头文件 */
	    try
	    {
	    	ps_head=new PrintStream(new FileOutputStream(gui_outfilename,false));
	    	ps_head.println(head);
	    	ps_head.close();   //关闭流必须在try里面关闭，才能使后续的renameTo成功。。。。
            System.out.println("文件头输出流已关闭");
	    }
        catch(IOException ioe1)
        {
        	System.err.println(ioe1.toString());
        }

        
        /* 输出文件体 */
		for(int i=0;i<ra_df.relist.size();i++)
		{
			body=ra_df.deal_with_body(i,gui_orgidt,gui_trancode,gui_outaccount,"NF");
			try
			{
				ps_body=new PrintStream(new FileOutputStream(gui_outfilename,true));
				ps_body.println(body);
			    ps_body.close();
		        System.out.println("文件体输出流已关闭");
			}
			catch(IOException ioe2)
			{
				System.err.println(ioe2.toString());
			}
		}
	}
}