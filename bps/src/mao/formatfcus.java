package mao;

import java.io.*;

public class formatfcus
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
	  gui_notedate����������
	  gui_filepath��ģ���ļ�·��
	  gui_filename���ļ���
	  gui_outfilename������ļ���
	*/
	
	public formatfcus(String gui_orgidt,String gui_dealnum,String gui_tlr,String gui_visanum,String gui_ter,String gui_notedate,String gui_filepath,String gui_filename,String gui_outfilename)
	{
		String head;
		String body;
		readfcus rf=new readfcus(gui_filepath);
		rf.put_into_arraylist();
		
		System.out.println("����ļ�����"+gui_outfilename);
		
		head=rf.deal_with_head(gui_filename,gui_orgidt,gui_tlr,gui_ter,gui_visanum,gui_dealnum,gui_notedate);
		
		/* ���ͷ�ļ� */
	    try
	    {
	    	ps_head=new PrintStream(new FileOutputStream(gui_outfilename,false));
	    	ps_head.println(head);
	    	ps_head.close();
            System.out.println("�ļ�ͷ������ѹر�");
	    }
        catch(IOException ioe1)
        {
        	System.err.println(ioe1.toString());
        }
        
        
        /* ����ļ��� */
		for(int i=0;i<rf.relist.size();i++)
		{
			body=rf.deal_with_body(i);
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