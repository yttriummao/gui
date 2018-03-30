package mao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class readfcus
{
	InputStream is;
	Workbook wb;  //����һ�������������ϣ�
	int workbook_num; 
	Sheet st;     //����һ�������������壩
	
	/* relist���ڴ��xml�е����� */
	ArrayList relist;
	
	/* ��������ͷ���� */
	String head_seqnum; //˳���
	String head_file;   //Դ�ļ���
	String head_orgidt; //���׻�����
	String head_curcde; //����
	String head_numcount; //�ϼƱ���
	String head_sumbal; //�ϼƽ��
	String head_channel; //������
	String head_tlr;    //���׹�Ա��
	String head_ter;    //�����ն�
	String head_visanum; //��λǩԼ��
	String head_dealnum; //�������κ�
	String head_notedate; //��������
	String head_filetyp; //�ļ���������
	String head_succount; //�ɹ�����
	String head_sucbal; //�ɹ����
	String head_failcount;  //ʧ�ܱ���
	String head_failbal; //ʧ�ܽ��
	String head_returnflag; //�ļ�������Ϣ��
	String head_returninfor; //�ļ�������Ϣ
	String head_filler;
	
	/* ���������岿�� */
	String fcus_seqnum;  //˳���
	String fcus_custyp;  //�ͻ�����
	String fcus_subcustyp;  //�ͻ�������
	String fcus_pastyp;  //֤������
	String fcus_passno;  //֤������
	String fcus_pasdate; //֤��������
	String fcus_lan;     //ͨѶ����
	String fcus_lname;   //��
	String fcus_fname;   //��
	String fcus_address1; //��ͥ��ַ��1
	String fcus_address2; //��ͥ��ַ��2
	String fcus_address3; //��ͥ��ַ��3
	String fcus_address4; //��ͥ��ַ��4
	String fcus_posnum;   //��������
	String fcus_nation;   //����
	String fcus_sex;      //�Ա�
	String fcus_resident; //��������
	String fcus_box;      //���б�����
	String fcus_tax;      //��Ϣ˰�Żݱ�ʶ
	String fcus_tel;      //�绰
	String fucs_pay;      //����������     !!!!!!!!!
	String fcus_job;      //ְҵ����       !!!!!!!!!
	String fcus_kyc;      //kyc���յȼ���3.5�汾�¼�)    
	String fcus_ps;       //��Χϵͳ��ע
	String fcus_cusidt;   //�ͻ���         !!!!!!!!!
	String fcus_suc_or_fail;  //���׳ɹ���ʧ�ܱ�־  !!!!!
	String fcus_log;      //������־�Ż���ʧ�ܴ��� !!!!!
	String fcus_failexp;  //����ʧ��˵��
	String fcus_filler;
	
	readfcus(String filepath)
	{
		try
		{
			this.is=new FileInputStream(filepath);
		    this.wb=Workbook.getWorkbook(is);
		    this.workbook_num=wb.getNumberOfSheets(); //�������ĸ���
		    this.st=this.wb.getSheet("Sheet1"); //����Ĭ��ֻ��ȡ��һ����������ֵ
		    System.out.println("��ǰ����������Ϊ��"+this.st.getName());
		    System.out.println("��������"+this.st.getRows());
		    System.out.println("��������"+this.st.getColumns());
		}
		catch(Exception e)
		{
			//System.out.println(e.toString());
			System.out.println("δ�ҵ��������ͻ�����ģ���ļ��������Ƿ����ϴ�!!");
			System.exit(0);
		}
	}
	
	/* �÷������ڽ�excel�е����ݷ������ĳ�Ա����relist�� */
	public void put_into_arraylist()
	{
		this.relist=new ArrayList();
		Cell cell=null;  //Cell�����������xls�еĵ�Ԫ��
		int row=this.st.getRows();
		int columns=this.st.getColumns();
		for(int i=0;i<row;i++)
		{
			ArrayList sub_relist=new ArrayList();
			for(int j=0;j<columns;j++)
			{
				cell=this.st.getCell(j,i);  //Sheet.getCell(��,��)����ø����ж�Ӧ�ĵ�Ԫ��
				sub_relist.add(cell.getContents()); //��excel�е�һ�����ݷ���sub_relist
				//System.out.print(cell.getContents()+"\t");
			}
			//System.out.println("\n");
			relist.add(sub_relist);
		}
		//this.wb.close();
	}
	
	/* �÷�����parameter������װ��ָ����ʽ���ַ����������� */
	/*
	   ����parameter����Ҫ��ʽ���Ĳ���
	   ����length����ʽ���ĳ���
	   ����fill���հ״���Ҫ�����ַ�
	   ����position����right��ָ��������䣬��left��ָ���������
	*/
	public String format_parameter(String parameter,int length,String fill,String position)
	{
		String formatstr=parameter;
		//int loop_num=length-formatstr.length(); //�����Ҫ���ĸ���
		int loop_num=0;
		int parameter_length=0;
		char parameter_tochar[]=parameter.toCharArray();
		
		for(int i=0;i<parameter_tochar.length;i++)
		{
			int ascii=parameter_tochar[i];
			if(ascii>=160)       //ascii����160����Ϊ�ǡ����ġ�������������
			{
				parameter_length=parameter_length+2;
			}
			else                                 //�����ĵ�ֻ��һ������
			{
				parameter_length=parameter_length+1;
			}
		}
		
		loop_num=length-parameter_length;
		
		if(position.equals("right"))
		{
			for(int i=0;i<loop_num;i++)
		    {
			    formatstr=formatstr+fill;
		    }
		}
		
		if(position.equals("left"))
		{
		    for(int i=0;i<loop_num;i++)
		    {
			    formatstr=fill+formatstr;
		    }
		}

		return formatstr;
	}
	
    /* �÷������ڷ���ָ��������"�ո�"������c_numָ���ո���� */
	public String return_blank(int c_num)
	{
		int num=c_num;
		String strblank="";
		for(int i=0;i<num;i++)
		{
			strblank=strblank+" ";
		}
		return strblank;
	}
	
	/* �÷������ڻ��relist�еĸ���������������String���ͷ��� */
	public String get_numcount()
	{
		int num=this.relist.size();
		
		return String.valueOf(num);
	}
	
	/* �÷������ڻ��relist��ָ���Ӽ���ֵ */
	public String get_list_value(int id,int sub_id)
	{
		String str="";
		ArrayList tmp_sub_relist=(ArrayList)relist.get(id);
		str=(String)tmp_sub_relist.get(sub_id);
		
		return str;
	}
	
	/* 
	  �÷������ڲ�������ͷ����
	  ����˵����
	  c_head_file��Դ�ļ���
	  c_head_orgidt�����׻�����
	  c_head_tlr�����׹�Ա��
	  c_head_ter�������ն˺�
	  c_head_visanum����λǩԼ��
	  c_head_dealnum���������κ�
	  c_head_notedate����������
	*/
	public String deal_with_head(String c_head_file,String c_head_orgidt,String c_head_tlr,String c_head_ter,String c_head_visanum,String c_head_dealnum,String c_head_notedate)
	{
		String restr="";
		
		System.out.println("########################����ͷ###################");
		
		this.head_seqnum="000000000000";
		System.out.println("˳��ţ�"+this.head_seqnum);
		
		this.head_file=format_parameter(c_head_file,17," ","right");
		System.out.println("Դ�ļ�����"+this.head_file);
		
		this.head_orgidt=format_parameter(c_head_orgidt,5,"0","left");
		System.out.println("���׻����ţ�"+this.head_orgidt);
		
		this.head_curcde=return_blank(3);
		System.out.println("���Һţ�"+this.head_curcde);
		
		this.head_numcount=format_parameter(get_numcount(),12,"0","left");
		System.out.println("�ϼƱ�����"+this.head_numcount);
		
		this.head_sumbal=return_blank(17);
		System.out.println("�ϼƽ�"+this.head_sumbal);
		
		this.head_channel="T";
		System.out.println("���������ţ�"+this.head_channel);
		
		this.head_tlr=format_parameter(c_head_tlr,7,"0","left");
		System.out.println("��Ա�ţ�"+this.head_tlr);
		
		this.head_ter=format_parameter(c_head_ter,3,"0","left");
		System.out.println("�ն˺ţ�"+this.head_ter);
		
		this.head_visanum=format_parameter(c_head_visanum,10,"0","left");
		System.out.println("��λǩԼ�ţ�"+this.head_visanum);
		
		this.head_dealnum=format_parameter(c_head_dealnum,3,"0","left");
		System.out.println("�������κţ�"+this.head_dealnum);
		
		this.head_notedate=c_head_notedate;
		System.out.println("�������ڣ�"+this.head_notedate);
		
		this.head_filetyp="FUCS";
		System.out.println("�ļ��������ͣ�"+this.head_filetyp);
		
		this.head_succount=return_blank(12);
		System.out.println("�ɹ�������"+this.head_succount);
		
		this.head_sucbal=return_blank(17);
		System.out.println("�ɹ���"+this.head_sucbal);
		
		this.head_failcount=return_blank(12);
		System.out.println("ʧ�ܱ�����"+this.head_failcount);
		
		this.head_failbal=return_blank(17);
		System.out.println("ʧ�ܽ�"+this.head_failbal);
		
		this.head_returnflag=return_blank(4);
		System.out.println("�ļ�������Ϣ�룺"+this.head_returnflag);
		
		this.head_returninfor=return_blank(30);
		System.out.println("�ļ�������Ϣ��"+this.head_returninfor);
		
		this.head_filler=return_blank(356);
		System.out.println("ͷ�ļ�filler��"+this.head_filler);
		
		restr=head_seqnum+head_file+head_orgidt+head_curcde+head_numcount+head_sumbal+head_channel+head_tlr+head_ter+head_visanum+head_dealnum+head_notedate+head_filetyp+head_succount+head_sucbal+head_failcount+head_failbal+head_returnflag+head_returninfor+head_filler;
		
		//System.out.println("&&&&&&&&&&&ͷ�ļ�����Ϊ&&&&&&&&&&��"+restr.length());
		
		return restr;
	}
	
	/* �÷������ڲ��������岿�� */
	public String deal_with_body(int c_num)
	{
		String restr="";
		
		int num=c_num;
		
		System.out.println("########################������###################");
		
		this.fcus_seqnum=format_parameter(String.valueOf(num+1),12,"0","left");
		System.out.println("˳��ţ�"+this.fcus_seqnum);
		
		this.fcus_custyp=format_parameter(get_list_value(num,0),2,"0","left");
		System.out.println("�ͻ����ͣ�"+this.fcus_custyp);
		
		this.fcus_subcustyp=format_parameter(get_list_value(num,1),3,"0","left");
		System.out.println("�ͻ������ͣ�"+this.fcus_subcustyp);
		
		this.fcus_pastyp=format_parameter(get_list_value(num,2),2,"0","left");
		System.out.println("֤�����ͣ�"+this.fcus_pastyp);
		
		this.fcus_passno=format_parameter(get_list_value(num,3),32," ","right");
		System.out.println("֤���ţ�"+this.fcus_passno);
		
		this.fcus_pasdate=format_parameter(get_list_value(num,4),8," ","right");;
		System.out.println("֤�������գ�"+this.fcus_pasdate);
		
		this.fcus_lan="01";
		System.out.println("ͨ�����ԣ�"+this.fcus_lan);
		
		this.fcus_lname=format_parameter(get_list_value(num,5),38," ","right");
		System.out.println("�գ�"+this.fcus_lname);
		
		this.fcus_fname=format_parameter(get_list_value(num,6),38," ","right");
		System.out.println("����"+this.fcus_fname);
		
		this.fcus_address1=format_parameter(get_list_value(num,7),118," ","right");
		System.out.println("��ͥ��ַ��1��"+this.fcus_address1);
		
		this.fcus_address2=format_parameter(get_list_value(num,8),18," ","right");
		System.out.println("��ͥ��ַ��2��"+this.fcus_address2);
		
		this.fcus_address3=format_parameter(get_list_value(num,9),18," ","right");
		System.out.println("��ͥ��ַ��3��"+this.fcus_address3);
		
		this.fcus_address4=format_parameter(get_list_value(num,10),18," ","right");
		System.out.println("��ͥ��ַ��4��"+this.fcus_address4);
		
		this.fcus_posnum=format_parameter(get_list_value(num,11),8,"0","left");
		System.out.println("�ʱࣺ"+this.fcus_posnum);
		
		this.fcus_nation=format_parameter(get_list_value(num,12),2," ","right");
		System.out.println("������"+this.fcus_nation);
		
		this.fcus_sex=format_parameter(get_list_value(num,13),1,"0","left");
		System.out.println("�Ա�"+this.fcus_sex);
		
		this.fcus_resident=format_parameter(get_list_value(num,14),1,"0","left");
		System.out.println("����"+this.fcus_resident);
		
		this.fcus_box=format_parameter(get_list_value(num,15),1,"0","left");
		System.out.println("���б����䣺"+this.fcus_box);
		
		this.fcus_tax=format_parameter(get_list_value(num,16),1,"0","left");
		System.out.println("��Ϣ˰�Żݱ�ʶ��"+this.fcus_tax);
		
		this.fcus_tel=format_parameter(get_list_value(num,17),16,"0","left");
		System.out.println("�绰��"+this.fcus_tel);
		
		this.fucs_pay=format_parameter(get_list_value(num,18),17,"0","left");
		System.out.println("�����룺"+this.fucs_pay);
		
		this.fcus_job=format_parameter(get_list_value(num,19),2," ","right");
		System.out.println("ְҵ���룺"+this.fcus_job);
		
		this.fcus_kyc=format_parameter(get_list_value(num,21),1," ","right");
		System.out.println("kyc���յȼ���"+this.fcus_kyc);
		
		this.fcus_ps=return_blank(50);  //ȫ��,��
		System.out.println("��Χϵͳ��ע��"+this.fcus_ps);
		
		this.fcus_cusidt=return_blank(10);
		System.out.println("�ͻ��ţ�"+this.fcus_cusidt);
		
		this.fcus_suc_or_fail=return_blank(1);
		System.out.println("���׳ɹ���ʧ�ܱ�־��"+this.fcus_suc_or_fail);
		
		this.fcus_log=return_blank(9);
		System.out.println("������־�Ż���ʧ�ܴ��룺"+this.fcus_log);
		
		this.fcus_failexp=return_blank(65);  //ȫ�ǣ���
		System.out.println("����ʧ��˵����"+this.fcus_failexp);
		
		this.fcus_filler=return_blank(44);
		System.out.println("�ļ���filler��"+this.fcus_filler);
		
		restr=fcus_seqnum+fcus_custyp+fcus_subcustyp+fcus_pastyp+fcus_passno+fcus_pasdate+fcus_lan+fcus_lname+fcus_fname+fcus_address1+fcus_address2+fcus_address3+fcus_address4+fcus_posnum+fcus_nation+fcus_sex+fcus_resident+fcus_box+fcus_tax+fcus_tel+fucs_pay+fcus_job+fcus_kyc+fcus_ps+fcus_cusidt+fcus_suc_or_fail+fcus_log+fcus_failexp+fcus_filler;
		
		//System.out.println("#########�����峤��Ϊ#########��"+restr.length());
		
		return restr;
	}
}