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
	Workbook wb;  //声明一个工作薄（集合）
	int workbook_num; 
	Sheet st;     //声明一个工作薄（单体）
	
	/* relist用于存放xml中的内容 */
	ArrayList relist;
	
	/* 声明数据头部分 */
	String head_seqnum; //顺序号
	String head_file;   //源文件名
	String head_orgidt; //交易机构号
	String head_curcde; //币种
	String head_numcount; //合计笔数
	String head_sumbal; //合计金额
	String head_channel; //渠道号
	String head_tlr;    //交易柜员号
	String head_ter;    //交易终端
	String head_visanum; //单位签约号
	String head_dealnum; //数据批次号
	String head_notedate; //记账日期
	String head_filetyp; //文件处理类型
	String head_succount; //成功笔数
	String head_sucbal; //成功金额
	String head_failcount;  //失败笔数
	String head_failbal; //失败金额
	String head_returnflag; //文件返回信息码
	String head_returninfor; //文件返回信息
	String head_filler;
	
	/* 声明数据体部分 */
	String fcus_seqnum;  //顺序号
	String fcus_custyp;  //客户类型
	String fcus_subcustyp;  //客户子类型
	String fcus_pastyp;  //证件类型
	String fcus_passno;  //证件号码
	String fcus_pasdate; //证件到期日
	String fcus_lan;     //通讯语言
	String fcus_lname;   //姓
	String fcus_fname;   //名
	String fcus_address1; //家庭地址栏1
	String fcus_address2; //家庭地址栏2
	String fcus_address3; //家庭地址栏3
	String fcus_address4; //家庭地址栏4
	String fcus_posnum;   //邮政编码
	String fcus_nation;   //国籍
	String fcus_sex;      //性别
	String fcus_resident; //居民类型
	String fcus_box;      //持有保管箱
	String fcus_tax;      //利息税优惠标识
	String fcus_tel;      //电话
	String fucs_pay;      //个人月收入     !!!!!!!!!
	String fcus_job;      //职业代码       !!!!!!!!!
	String fcus_kyc;      //kyc风险等级（3.5版本新加)    
	String fcus_ps;       //外围系统备注
	String fcus_cusidt;   //客户号         !!!!!!!!!
	String fcus_suc_or_fail;  //交易成功或失败标志  !!!!!
	String fcus_log;      //交易日志号或交易失败代码 !!!!!
	String fcus_failexp;  //交易失败说明
	String fcus_filler;
	
	readfcus(String filepath)
	{
		try
		{
			this.is=new FileInputStream(filepath);
		    this.wb=Workbook.getWorkbook(is);
		    this.workbook_num=wb.getNumberOfSheets(); //工作薄的个数
		    this.st=this.wb.getSheet("Sheet1"); //这里默认只读取第一个工作薄的值
		    System.out.println("当前工作表名称为："+this.st.getName());
		    System.out.println("总行数："+this.st.getRows());
		    System.out.println("总列数："+this.st.getColumns());
		}
		catch(Exception e)
		{
			//System.out.println(e.toString());
			System.out.println("未找到批量开客户电子模板文件，请检查是否已上传!!");
			System.exit(0);
		}
	}
	
	/* 该方法用于将excel中的内容放入该类的成员变量relist中 */
	public void put_into_arraylist()
	{
		this.relist=new ArrayList();
		Cell cell=null;  //Cell对象用来存放xls中的单元格
		int row=this.st.getRows();
		int columns=this.st.getColumns();
		for(int i=0;i<row;i++)
		{
			ArrayList sub_relist=new ArrayList();
			for(int j=0;j<columns;j++)
			{
				cell=this.st.getCell(j,i);  //Sheet.getCell(列,行)：获得该行列对应的单元格
				sub_relist.add(cell.getContents()); //将excel中的一行内容放入sub_relist
				//System.out.print(cell.getContents()+"\t");
			}
			//System.out.println("\n");
			relist.add(sub_relist);
		}
		//this.wb.close();
	}
	
	/* 该方法将parameter参数封装成指定格式的字符串，并返回 */
	/*
	   参数parameter：需要格式化的参数
	   参数length：格式化的长度
	   参数fill：空白处需要填充的字符
	   参数position：‘right’指明向右填充，‘left’指明向左填充
	*/
	public String format_parameter(String parameter,int length,String fill,String position)
	{
		String formatstr=parameter;
		//int loop_num=length-formatstr.length(); //获得需要填充的个数
		int loop_num=0;
		int parameter_length=0;
		char parameter_tochar[]=parameter.toCharArray();
		
		for(int i=0;i<parameter_tochar.length;i++)
		{
			int ascii=parameter_tochar[i];
			if(ascii>=160)       //ascii大于160被认为是“中文”，算两个长度
			{
				parameter_length=parameter_length+2;
			}
			else                                 //非中文的只算一个长度
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
	
    /* 该方法用于返回指定数量的"空格"，参数c_num指定空格个数 */
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
	
	/* 该方法用于获得relist中的个数，即笔数，以String类型返回 */
	public String get_numcount()
	{
		int num=this.relist.size();
		
		return String.valueOf(num);
	}
	
	/* 该方法用于获得relist中指定子集的值 */
	public String get_list_value(int id,int sub_id)
	{
		String str="";
		ArrayList tmp_sub_relist=(ArrayList)relist.get(id);
		str=(String)tmp_sub_relist.get(sub_id);
		
		return str;
	}
	
	/* 
	  该方法用于产生数据头部分
	  参数说明：
	  c_head_file：源文件名
	  c_head_orgidt：交易机构号
	  c_head_tlr：交易柜员号
	  c_head_ter：交易终端号
	  c_head_visanum：单位签约号
	  c_head_dealnum：数据批次号
	  c_head_notedate：记账日期
	*/
	public String deal_with_head(String c_head_file,String c_head_orgidt,String c_head_tlr,String c_head_ter,String c_head_visanum,String c_head_dealnum,String c_head_notedate)
	{
		String restr="";
		
		System.out.println("########################数据头###################");
		
		this.head_seqnum="000000000000";
		System.out.println("顺序号："+this.head_seqnum);
		
		this.head_file=format_parameter(c_head_file,17," ","right");
		System.out.println("源文件名："+this.head_file);
		
		this.head_orgidt=format_parameter(c_head_orgidt,5,"0","left");
		System.out.println("交易机构号："+this.head_orgidt);
		
		this.head_curcde=return_blank(3);
		System.out.println("货币号："+this.head_curcde);
		
		this.head_numcount=format_parameter(get_numcount(),12,"0","left");
		System.out.println("合计笔数："+this.head_numcount);
		
		this.head_sumbal=return_blank(17);
		System.out.println("合计金额："+this.head_sumbal);
		
		this.head_channel="T";
		System.out.println("交易渠道号："+this.head_channel);
		
		this.head_tlr=format_parameter(c_head_tlr,7,"0","left");
		System.out.println("柜员号："+this.head_tlr);
		
		this.head_ter=format_parameter(c_head_ter,3,"0","left");
		System.out.println("终端号："+this.head_ter);
		
		this.head_visanum=format_parameter(c_head_visanum,10,"0","left");
		System.out.println("单位签约号："+this.head_visanum);
		
		this.head_dealnum=format_parameter(c_head_dealnum,3,"0","left");
		System.out.println("数据批次号："+this.head_dealnum);
		
		this.head_notedate=c_head_notedate;
		System.out.println("记账日期："+this.head_notedate);
		
		this.head_filetyp="FUCS";
		System.out.println("文件处理类型："+this.head_filetyp);
		
		this.head_succount=return_blank(12);
		System.out.println("成功笔数："+this.head_succount);
		
		this.head_sucbal=return_blank(17);
		System.out.println("成功金额："+this.head_sucbal);
		
		this.head_failcount=return_blank(12);
		System.out.println("失败笔数："+this.head_failcount);
		
		this.head_failbal=return_blank(17);
		System.out.println("失败金额："+this.head_failbal);
		
		this.head_returnflag=return_blank(4);
		System.out.println("文件返回信息码："+this.head_returnflag);
		
		this.head_returninfor=return_blank(30);
		System.out.println("文件返回信息："+this.head_returninfor);
		
		this.head_filler=return_blank(356);
		System.out.println("头文件filler："+this.head_filler);
		
		restr=head_seqnum+head_file+head_orgidt+head_curcde+head_numcount+head_sumbal+head_channel+head_tlr+head_ter+head_visanum+head_dealnum+head_notedate+head_filetyp+head_succount+head_sucbal+head_failcount+head_failbal+head_returnflag+head_returninfor+head_filler;
		
		//System.out.println("&&&&&&&&&&&头文件长度为&&&&&&&&&&："+restr.length());
		
		return restr;
	}
	
	/* 该方法用于产生数据体部分 */
	public String deal_with_body(int c_num)
	{
		String restr="";
		
		int num=c_num;
		
		System.out.println("########################数据体###################");
		
		this.fcus_seqnum=format_parameter(String.valueOf(num+1),12,"0","left");
		System.out.println("顺序号："+this.fcus_seqnum);
		
		this.fcus_custyp=format_parameter(get_list_value(num,0),2,"0","left");
		System.out.println("客户类型："+this.fcus_custyp);
		
		this.fcus_subcustyp=format_parameter(get_list_value(num,1),3,"0","left");
		System.out.println("客户子类型："+this.fcus_subcustyp);
		
		this.fcus_pastyp=format_parameter(get_list_value(num,2),2,"0","left");
		System.out.println("证件类型："+this.fcus_pastyp);
		
		this.fcus_passno=format_parameter(get_list_value(num,3),32," ","right");
		System.out.println("证件号："+this.fcus_passno);
		
		this.fcus_pasdate=format_parameter(get_list_value(num,4),8," ","right");;
		System.out.println("证件到期日："+this.fcus_pasdate);
		
		this.fcus_lan="01";
		System.out.println("通信语言："+this.fcus_lan);
		
		this.fcus_lname=format_parameter(get_list_value(num,5),38," ","right");
		System.out.println("姓："+this.fcus_lname);
		
		this.fcus_fname=format_parameter(get_list_value(num,6),38," ","right");
		System.out.println("名："+this.fcus_fname);
		
		this.fcus_address1=format_parameter(get_list_value(num,7),118," ","right");
		System.out.println("家庭地址栏1："+this.fcus_address1);
		
		this.fcus_address2=format_parameter(get_list_value(num,8),18," ","right");
		System.out.println("家庭地址栏2："+this.fcus_address2);
		
		this.fcus_address3=format_parameter(get_list_value(num,9),18," ","right");
		System.out.println("家庭地址栏3："+this.fcus_address3);
		
		this.fcus_address4=format_parameter(get_list_value(num,10),18," ","right");
		System.out.println("家庭地址栏4："+this.fcus_address4);
		
		this.fcus_posnum=format_parameter(get_list_value(num,11),8,"0","left");
		System.out.println("邮编："+this.fcus_posnum);
		
		this.fcus_nation=format_parameter(get_list_value(num,12),2," ","right");
		System.out.println("国籍："+this.fcus_nation);
		
		this.fcus_sex=format_parameter(get_list_value(num,13),1,"0","left");
		System.out.println("性别："+this.fcus_sex);
		
		this.fcus_resident=format_parameter(get_list_value(num,14),1,"0","left");
		System.out.println("居民："+this.fcus_resident);
		
		this.fcus_box=format_parameter(get_list_value(num,15),1,"0","left");
		System.out.println("持有保管箱："+this.fcus_box);
		
		this.fcus_tax=format_parameter(get_list_value(num,16),1,"0","left");
		System.out.println("利息税优惠标识："+this.fcus_tax);
		
		this.fcus_tel=format_parameter(get_list_value(num,17),16,"0","left");
		System.out.println("电话："+this.fcus_tel);
		
		this.fucs_pay=format_parameter(get_list_value(num,18),17,"0","left");
		System.out.println("月收入："+this.fucs_pay);
		
		this.fcus_job=format_parameter(get_list_value(num,19),2," ","right");
		System.out.println("职业代码："+this.fcus_job);
		
		this.fcus_kyc=format_parameter(get_list_value(num,21),1," ","right");
		System.out.println("kyc风险等级："+this.fcus_kyc);
		
		this.fcus_ps=return_blank(50);  //全角,空
		System.out.println("外围系统备注："+this.fcus_ps);
		
		this.fcus_cusidt=return_blank(10);
		System.out.println("客户号："+this.fcus_cusidt);
		
		this.fcus_suc_or_fail=return_blank(1);
		System.out.println("交易成功或失败标志："+this.fcus_suc_or_fail);
		
		this.fcus_log=return_blank(9);
		System.out.println("交易日志号或交易失败代码："+this.fcus_log);
		
		this.fcus_failexp=return_blank(65);  //全角，空
		System.out.println("交易失败说明："+this.fcus_failexp);
		
		this.fcus_filler=return_blank(44);
		System.out.println("文件体filler："+this.fcus_filler);
		
		restr=fcus_seqnum+fcus_custyp+fcus_subcustyp+fcus_pastyp+fcus_passno+fcus_pasdate+fcus_lan+fcus_lname+fcus_fname+fcus_address1+fcus_address2+fcus_address3+fcus_address4+fcus_posnum+fcus_nation+fcus_sex+fcus_resident+fcus_box+fcus_tax+fcus_tel+fucs_pay+fcus_job+fcus_kyc+fcus_ps+fcus_cusidt+fcus_suc_or_fail+fcus_log+fcus_failexp+fcus_filler;
		
		//System.out.println("#########数据体长度为#########："+restr.length());
		
		return restr;
	}
}