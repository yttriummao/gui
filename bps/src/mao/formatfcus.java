package mao;

import java.io.*;

public class formatfcus
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
	  gui_notedate：记账日期
	  gui_filepath：模板文件路径
	  gui_filename：文件名
	  gui_outfilename：输出文件名
	*/
	
	public formatfcus(String gui_orgidt,String gui_dealnum,String gui_tlr,String gui_visanum,String gui_ter,String gui_notedate,String gui_filepath,String gui_filename,String gui_outfilename)
	{
		String head;
		String body;
		readfcus rf=new readfcus(gui_filepath);
		rf.put_into_arraylist();
		
		System.out.println("输出文件名："+gui_outfilename);
		
		head=rf.deal_with_head(gui_filename,gui_orgidt,gui_tlr,gui_ter,gui_visanum,gui_dealnum,gui_notedate);
		
		/* 输出头文件 */
	    try
	    {
	    	ps_head=new PrintStream(new FileOutputStream(gui_outfilename,false));
	    	ps_head.println(head);
	    	ps_head.close();
            System.out.println("文件头输出流已关闭");
	    }
        catch(IOException ioe1)
        {
        	System.err.println(ioe1.toString());
        }
        
        
        /* 输出文件体 */
		for(int i=0;i<rf.relist.size();i++)
		{
			body=rf.deal_with_body(i);
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