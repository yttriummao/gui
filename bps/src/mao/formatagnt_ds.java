package mao;

import java.io.*;
import javax.swing.*;
import java.lang.Thread;

public class formatagnt_ds extends Thread
{
	PrintStream ps_body;
	PrintStream ps_head;
	
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
	  gui_inaccount��ת���˺�
	  gui_filename���ļ���
	  gui_outfilename������ļ���
	*/
	public formatagnt_ds(String gui_orgidt,String gui_dealnum,String gui_tlr,String gui_visanum,String gui_ter,String gui_trancode,String gui_notedate,String gui_curcde,String gui_promocode,String gui_filepath,String gui_inaccount,String gui_filename,String gui_outfilename)
	{
		String head;
		String body;
		readagnt_ds ra_ds=new readagnt_ds(gui_filepath);
		
		System.out.println("����ļ�����"+gui_outfilename);
		
	    /* ���ģ����д������ */
	    try
	    {
	        if(ra_ds.st.getColumns()!=7)
		    {
		        throw new MyException("�������յ���ģ����д��������ȷ����ȷӦΪ7�У�����!");
		    }
	    }
		catch(MyException me)
		{
			System.err.println(me.toString());
			System.exit(0);
		}
		
		ra_ds.put_into_arraylist();
		head=ra_ds.deal_with_head(gui_filename,gui_orgidt,gui_tlr,gui_ter,gui_visanum,gui_dealnum,gui_notedate);
		
		/* ���ͷ�ļ� */
	    try
	    {
	    	ps_head=new PrintStream(new FileOutputStream(gui_outfilename,false));
	    	ps_head.println(head);
	    	ps_head.close();
	    }
        catch(IOException ioe1)
        {
        	System.err.println(ioe1.toString());
        }
        
        /* ����ļ��� */
		for(int i=0;i<ra_ds.relist.size();i++)
		{
			body=ra_ds.deal_with_body(i,gui_orgidt,gui_trancode,gui_inaccount,"NF");
			try
			{
				ps_body=new PrintStream(new FileOutputStream(gui_outfilename,true));
				ps_body.println(body);
				ps_body.close();
			}
			catch(IOException ioe2)
			{
				System.err.println(ioe2.toString());
			}	
		}
	}
}