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
	
	/*��GUI���ݹ����Ĳ���
	  ����˵����
	  gui_orgidt�����׻�����
	  gui_dealnum���������κ�
	  gui_tlr�����׹�Ա��
	  gui_visanum��ǩԼ��λ��
	  gui_ter���ն˺�
	  gui_trancode��������
	  gui_notedate����������
	  gui_curcde�����׻���
	  gui_promocode���շ�ѡ��
	  gui_filepath��ģ���ļ�·��
	  gui_outaccount��ת���˺�
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
	    
	    /* ���ģ����д������ */
	    try
	    {
	        if(ra_df.st.getColumns()!=7)
		    {
		        throw new MyException("������������ģ����д��������ȷ����ȷӦΪ7�У�����!");
		    }
	    }
		catch(MyException me)
		{
			System.err.println(me.toString());
			//System.exit(0);
		}
		
		ra_df.put_into_arraylist();
		head=ra_df.deal_with_head("gui_filepath","gui_orgidt","gui_tlr","gui_ter","gui_visanum","gui_dealnum","gui_notedate");
		
		/* ���ͷ�ļ� */
	    try
	    {
	    	PrintStream ps_head=new PrintStream(new FileOutputStream("PE.AGNT.00024.0000000000.103.T0403.DAT",false));
	    	ps_head.println(head);
	    }
        catch(IOException ioe1)
        {
        	System.err.println(ioe1.toString());
        }
        
        /* ����ļ��� */
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