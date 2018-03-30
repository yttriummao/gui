package mao;

import java.io.*;
import javax.swing.*;
import java.lang.Thread;

public class formatagnt_df extends Thread
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
	  gui_outaccount��ת���˺�
	  gui_filename���ļ���
	  gui_outfilename������ļ���
	*/
	public formatagnt_df(String gui_orgidt,String gui_dealnum,String gui_tlr,String gui_visanum,String gui_ter,String gui_trancode,String gui_notedate,String gui_curcde,String gui_promocode,String gui_filepath,String gui_outaccount,String gui_filename,String gui_outfilename)
	{
		String head;
		String body;
	    readagnt_df ra_df=new readagnt_df(gui_filepath);
	    
	    System.out.println("����ļ�����"+gui_outfilename);
	    
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
		head=ra_df.deal_with_head(gui_filename,gui_orgidt,gui_tlr,gui_ter,gui_visanum,gui_dealnum,gui_notedate);
		
		/* ���ͷ�ļ� */
	    try
	    {
	    	ps_head=new PrintStream(new FileOutputStream(gui_outfilename,false));
	    	ps_head.println(head);
	    	ps_head.close();   //�ر���������try����رգ�����ʹ������renameTo�ɹ���������
            System.out.println("�ļ�ͷ������ѹر�");
	    }
        catch(IOException ioe1)
        {
        	System.err.println(ioe1.toString());
        }

        
        /* ����ļ��� */
		for(int i=0;i<ra_df.relist.size();i++)
		{
			body=ra_df.deal_with_body(i,gui_orgidt,gui_trancode,gui_outaccount,"NF");
			try
			{
				ps_body=new PrintStream(new FileOutputStream(gui_outfilename,true));
				ps_body.println(body);
			    ps_body.close();
		        System.out.println("�ļ���������ѹر�");
			}
			catch(IOException ioe2)
			{
				System.err.println(ioe2.toString());
			}
		}
	}
}