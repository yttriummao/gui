package mao;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class readagnt_ds
{
	InputStream is;
	Workbook wb;  //����һ�������������ϣ�
	int workbook_num; 
	Sheet st;     //����һ�������������壩
	
	/* relist���ڴ��xml�е����� */
	ArrayList relist;
	
	/*�ļ�ͷ���ݶ���*/
	String head_fileflag; //��¼��ʾ
	String head_seqnum;   //˳���
	String head_orgflag;  //�������˱�ʶ
	String head_file;     //Դ�ļ���
	String head_orgidt;   //���׻�����
	String head_curcde;   //����
	String head_numcount; //�ϼƱ���
	String head_sumbal;   //�ϼƽ��
	String head_flagbal1; //������һ
	String head_channel;  //������
	String head_tlr;      //���׹�Ա��
	String head_ter;      //�����ն˺�
	String head_visanum;  //��λǩԼ��
	String head_dealnum;  //�������κ�
	String head_notedate; //��������
	String head_filetyp;  //�ļ���������
	String head_succount; //�ɹ�����
	String head_sucbal;   //�ɹ����
	String head_flagbal2; //�����Ŷ�
	String head_failcount;//ʧ�ܱ���
	String head_failbal;  //ʧ�ܽ��
	String head_flagbal3; //������λ
	String head_returnflag;//�ļ�������Ϣ��
	String head_returninfor;//�ļ�������Ϣ
	String head_filler;
	
	/* �ļ������ݶ��� */
	String body_fileflag; //��¼��ʾ
	String body_seqnum;  //˳���
	String body_orgidt;  //���׻�����
	String body_cusidt;  //�ͻ���
	String body_newoutaccount; //ת�����˺�
	String body_oldoutaccount; //ת�����˺�
	String body_outcard; //ת������
	String body_subacctyp_out; //���ʻ�����(ת��)
	String body_filler1;
	String body_newinaccount;  //ת�����˺�
	String body_oldinaccount;  //ת����˺�
	String body_incard;        //ת�뿨��
	String body_subacctyp_in;  //���ʻ�����(ת��)
	String body_ptyp;          //��Ʒ���
	String body_subptyp;       //��Ʒ�����
	String body_bal;           //���
	String body_flagbal;       //������λ
	String body_promocode;     //��ʾ��
	String body_pastyp;        //֤������
	String body_passno;        //֤������
	String body_checkout_type; //У������
	String body_checkout_direct; //У�鷽��
	String body_trancode;      //������
	String body_mainnum;       //�����ο���
	String body_maindate;      //������������
	String body_returnflag;    //������Ϣ��
	String body_returninfor;   //������Ϣ
	String body_ballance;      //�ʻ����
	String body_ballance_flag; //�ʻ�������λ
	String body_filler2;
	String body_cusname;       //�˻���
	String body_store;         //����
	String body_red;           //������   
	
	/* ������ */
	readagnt_ds(String filepath)
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
			System.out.println("δ�ҵ����յ���ģ���ļ��������Ƿ����ϴ�!!");
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
	
	/* �÷������ڻ�ȡ�ϼƽ�� */
	public String get_sumbal()
	{
		String sumbal="";
		long sum=0;         /*ע�⣺�˴����ϼ�ʱ��Ϊlong�ͣ����򳬳�����*/
		ArrayList tmplist=this.relist;
		
		//System.out.println("tmplist���ϴ�СΪ��"+tmplist.size());
		
		for(int i=0;i<tmplist.size();i++)
		{
			ArrayList single_list=(ArrayList)(tmplist.get(i));
			sum=sum+Integer.valueOf((String)single_list.get(5)).intValue();
			System.out.println(sum);
		}
		
		sumbal=String.valueOf(sum);
		
		return sumbal;
	}
	
	/* �÷������ڻ��ת�����˺� */
	public String get_in_account_new(String in_account)
	{
		String straccount="";
		
		if(in_account.length()==12||in_account.length()==16)
		{
			straccount=in_account;  //ת���˺ų���Ϊ12λ������Ϊ�����˺�
		}
		else
		{
			straccount="";
		}
		 
		return straccount;
	}
	
	/* �÷������ڻ��ת����˺� */
	public String get_in_account_old(String in_account)
	{
		String straccount="";
		
		if(in_account.length()==17||in_account.length()==21)
		{
			straccount=in_account;  //ת���˺ų���Ϊ17λ��21λ������Ϊ�Ǿ��˺�
		}
		else
		{
			straccount="";
		}
		 
		return straccount;
	}
	
	public String format_bal(String bal)
	{
		String strbal=bal.substring(0,16);
		return strbal;
	}

	
	/* 
	  �÷������ڲ�������ͷ����
	  ����˵����
	  c_head_file��Դ�ļ���
	  c_head_orgidt�����׻�����
	  c_head_tlr�����׹�Ա��
	  c_head_ter�������ն˺�
	  c_head_visanum����λǩԼ��
	  c_head_dealnum����������
	  c_head_notedate��������
	*/
	public String deal_with_head(String c_head_file,String c_head_orgidt,String c_head_tlr,String c_head_ter,String c_head_visanum,String c_head_dealnum,String c_head_notedate)
	{
		String restr="";
		
		this.head_fileflag="H";
		System.out.println("��¼��־��"+this.head_fileflag);
		
		this.head_seqnum="000000000001";
		System.out.println("����ͷ˳��ţ�"+this.head_seqnum);
		
		this.head_orgflag="0";
		System.out.println("�������˱�ʶ��"+this.head_orgflag);
		
		this.head_file=format_parameter(c_head_file,50," ","right");
		System.out.println("Դ�ļ����ƣ�"+this.head_file);
		
		this.head_orgidt=format_parameter(c_head_orgidt,5,"0","left");
		System.out.println("���׻����ţ�"+this.head_orgidt);
		
		this.head_curcde="CNY";
		System.out.println("���Һţ�"+this.head_curcde);
		
		this.head_numcount=format_parameter(get_numcount(),12,"0","left");
		System.out.println("�ϼƱ�����"+this.head_numcount);
		
		this.head_sumbal=format_parameter(get_sumbal(),17,"0","left");
		System.out.println("�ϼƽ�"+this.head_sumbal);
		
		this.head_flagbal1="+";
		System.out.println("�����ţ�"+this.head_flagbal1);
		
		this.head_channel="T";
		System.out.println("�����ţ�"+this.head_channel);
		
		this.head_tlr=format_parameter(c_head_tlr,7,"0","left");
		System.out.println("���׹�Ա�ţ�"+this.head_tlr);
		
		this.head_ter=format_parameter(c_head_ter,3,"0","left");
		System.out.println("�����ն˺ţ�"+this.head_ter);
		
		this.head_visanum=format_parameter(c_head_visanum,10," ","right");
		System.out.println("��λǩԼ�ţ�"+this.head_visanum);
		
		this.head_dealnum=format_parameter(c_head_dealnum,3,"0","left");
		System.out.println("�������Σ�"+this.head_dealnum);
		
		//this.head_notedate=get_date();
		//this.head_notedate="20100210";
		this.head_notedate=c_head_notedate;
		System.out.println("�������ڣ�"+this.head_notedate);
		
		this.head_filetyp="AGNT";
		System.out.println("�ļ��������ͣ�"+this.head_filetyp);
		
		this.head_succount=return_blank(12);
		
		this.head_sucbal=return_blank(17);
		
		this.head_flagbal2="+";         //"������λ"����δȷ��
		
		this.head_failcount=return_blank(12);
		
		this.head_failbal=return_blank(17);
		
		this.head_flagbal3="+";         //"������λ"����δȷ��
		
		this.head_returnflag=return_blank(4);
		
		this.head_returninfor=return_blank(30);  //ȫ�ǣ���
		
		this.head_filler=return_blank(224);
		
		//ƴװ����ͷ
		restr=head_fileflag+head_seqnum+head_orgflag+head_file+head_orgidt+head_curcde+head_numcount+head_sumbal+head_flagbal1+head_channel+head_tlr+head_ter+head_visanum+head_dealnum+head_notedate+head_filetyp+head_succount+head_sucbal+head_flagbal2+head_failcount+head_failbal+head_flagbal3+head_returnflag+head_returninfor+head_filler;
		
		return restr;
	}
	
	/* 
	  �÷������ڲ�������������Ϣ
	  ����˵����
	  c_num��excel���к�
	  c_body_orgidt�����׻�����
	  c_body_trancode��������
	  c_body_inaccount��ת���˺�
	  c_body_promocode��BOC��ʾ��
	*/
	public String deal_with_body(int c_num,String c_body_orgidt,String c_body_trancode,String c_body_inaccount,String c_body_promocode)
	{
		String restr="";
		
		int num=c_num;
		
		String tmp="";
		
		this.body_fileflag="D";
		System.out.println("�������¼��־��"+this.body_fileflag);
		
		this.body_seqnum=format_parameter(String.valueOf(num+1),12,"0","left");
		System.out.println("������˳��ţ�"+this.body_seqnum);
		
		this.body_orgidt=format_parameter(c_body_orgidt,5,"0","left");
		System.out.println("���׻����ţ�"+this.body_orgidt);
		
		this.body_cusidt=return_blank(10);
		System.out.println("�ͻ��ţ�"+this.body_cusidt);
		
		this.body_newoutaccount=format_parameter(get_list_value(num,0),17,"0","left");
		System.out.println("ת�����˺ţ�"+this.body_newoutaccount);
		
		/*
		tmp=get_list_value(num,1);
		tmp=tmp.trim();
		if(!tmp.equals(""))
		{
			this.body_oldoutaccount=format_parameter("52"+get_list_value(num,1),25," ","right");
		    System.out.println("ת�����˺ţ�"+this.body_oldoutaccount);
		}
		else
		{
			this.body_oldoutaccount=format_parameter(" ",25," ","right");
		    System.out.println("ת�����˺ţ�"+this.body_oldoutaccount);
		}
		*/
		
        this.body_oldoutaccount=format_parameter(get_list_value(num,1),25," ","right");
		System.out.println("ת�����˺ţ�"+this.body_oldoutaccount);
		
		this.body_outcard=format_parameter(get_list_value(num,2),19," ","right");
		System.out.println("ת�����ţ�"+this.body_outcard);
		
		this.body_subacctyp_out="CNY0";
		System.out.println("�ʻ������ת������"+this.body_subacctyp_out);
		
		this.body_filler1=return_blank(1);
		
		this.body_newinaccount=format_parameter(get_in_account_new(c_body_inaccount),17,"0","left");
		System.out.println("ת�����˺ţ�"+this.body_newinaccount);
		
		this.body_oldinaccount=format_parameter(get_in_account_old(c_body_inaccount),25," ","right");
		System.out.println("ת����˺ţ�"+this.body_oldinaccount);
		
		this.body_incard=format_parameter(" ",19," ","right");
		System.out.println("ת�뿨�ţ�"+this.body_incard);
		
		this.body_subacctyp_in="CNY0";
		System.out.println("�ʻ������ת������"+this.body_subacctyp_in);
		
		this.body_ptyp=format_parameter(get_list_value(num,3),4," ","right");
		System.out.println("��Ʒ���"+this.body_ptyp);
		
		this.body_subptyp=format_parameter(get_list_value(num,4),4," ","right");
		System.out.println("��Ʒ�����"+this.body_subptyp);
		
		this.body_bal=format_parameter(get_list_value(num,5),17,"0","left");
		System.out.println("���׽�"+this.body_bal);
		
		this.body_flagbal="+";                      //"������λ"
		
		this.body_promocode=format_parameter(c_body_promocode,2," ","right"); //"��ʾ��"������
		System.out.println("BOC��ʾ��Ϊ��"+this.body_promocode);
		
		this.body_pastyp=return_blank(2);                      //"֤������"��ѡ���
		
		this.body_passno=return_blank(30); //"֤������"��ѡ���
		
		this.body_checkout_type="0";                //"У��"
		
		this.body_checkout_direct=return_blank(1);
		
		this.body_trancode=format_parameter(c_body_trancode,6,"0","left");
		System.out.println("�����룺"+this.body_trancode);
		
		this.body_mainnum=return_blank(9);             
	
        this.body_maindate=return_blank(8);             
        
        this.body_returnflag=return_blank(4);               
        
        this.body_returninfor=return_blank(30);  //ȫ�ǣ���
        
        this.body_ballance=return_blank(17);     //ѡ���
        
        this.body_ballance_flag=return_blank(1);                //ѡ���
        
        this.body_filler2=return_blank(2);                     //ѡ���
        
        this.body_cusname=format_parameter(get_list_value(num,6),58," ","right");
        System.out.println("�˻�����"+this.body_cusname);
        
        this.body_store=return_blank(50);
        
        this.body_red=return_blank(48);
        
        restr=body_fileflag+body_seqnum+body_orgidt+body_cusidt+body_newoutaccount+body_oldoutaccount+body_outcard+body_subacctyp_out+body_filler1+body_newinaccount+body_oldinaccount+body_incard+body_subacctyp_in+body_ptyp+body_subptyp+body_bal+body_flagbal+body_promocode+body_pastyp+body_passno+body_checkout_type+body_checkout_direct+body_trancode+body_mainnum+body_maindate+body_returnflag+body_returninfor+body_ballance+body_ballance_flag+body_filler2+body_cusname+body_store+body_red;
        
        System.out.println("#########################################################################");
		
		return restr;
	}
}