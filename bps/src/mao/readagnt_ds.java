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
	Workbook wb;  //声明一个工作薄（集合）
	int workbook_num; 
	Sheet st;     //声明一个工作薄（单体）
	
	/* relist用于存放xml中的内容 */
	ArrayList relist;
	
	/*文件头数据定义*/
	String head_fileflag; //记录标示
	String head_seqnum;   //顺序号
	String head_orgflag;  //机构过账标识
	String head_file;     //源文件名
	String head_orgidt;   //交易机构号
	String head_curcde;   //货币
	String head_numcount; //合计笔数
	String head_sumbal;   //合计金额
	String head_flagbal1; //金额符号一
	String head_channel;  //渠道号
	String head_tlr;      //交易柜员号
	String head_ter;      //交易终端号
	String head_visanum;  //单位签约号
	String head_dealnum;  //数据批次号
	String head_notedate; //记账日期
	String head_filetyp;  //文件处理类型
	String head_succount; //成功笔数
	String head_sucbal;   //成功金额
	String head_flagbal2; //金额符号二
	String head_failcount;//失败笔数
	String head_failbal;  //失败金额
	String head_flagbal3; //金额符号位
	String head_returnflag;//文件返回信息码
	String head_returninfor;//文件返回信息
	String head_filler;
	
	/* 文件体数据定义 */
	String body_fileflag; //记录标示
	String body_seqnum;  //顺序号
	String body_orgidt;  //交易机构号
	String body_cusidt;  //客户号
	String body_newoutaccount; //转出新账号
	String body_oldoutaccount; //转出就账号
	String body_outcard; //转出卡号
	String body_subacctyp_out; //子帐户类别号(转出)
	String body_filler1;
	String body_newinaccount;  //转入新账号
	String body_oldinaccount;  //转入旧账号
	String body_incard;        //转入卡号
	String body_subacctyp_in;  //子帐户类别号(转入)
	String body_ptyp;          //产品类别
	String body_subptyp;       //产品子类别
	String body_bal;           //金额
	String body_flagbal;       //金额符号位
	String body_promocode;     //提示码
	String body_pastyp;        //证件类型
	String body_passno;        //证件号码
	String body_checkout_type; //校验种类
	String body_checkout_direct; //校验方向
	String body_trancode;      //交易码
	String body_mainnum;       //主机参考号
	String body_maindate;      //主机记账日期
	String body_returnflag;    //返回信息码
	String body_returninfor;   //返回信息
	String body_ballance;      //帐户余额
	String body_ballance_flag; //帐户余额符号位
	String body_filler2;
	String body_cusname;       //账户名
	String body_store;         //备用
	String body_red;           //冗余域   
	
	/* 构造器 */
	readagnt_ds(String filepath)
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
			System.out.println("未找到代收电子模板文件，请检查是否已上传!!");
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
	
	/* 该方法用于获取合计金额 */
	public String get_sumbal()
	{
		String sumbal="";
		long sum=0;         /*注意：此处金额合计时设为long型，否则超出上限*/
		ArrayList tmplist=this.relist;
		
		//System.out.println("tmplist集合大小为："+tmplist.size());
		
		for(int i=0;i<tmplist.size();i++)
		{
			ArrayList single_list=(ArrayList)(tmplist.get(i));
			sum=sum+Integer.valueOf((String)single_list.get(5)).intValue();
			System.out.println(sum);
		}
		
		sumbal=String.valueOf(sum);
		
		return sumbal;
	}
	
	/* 该方法用于获得转入新账号 */
	public String get_in_account_new(String in_account)
	{
		String straccount="";
		
		if(in_account.length()==12||in_account.length()==16)
		{
			straccount=in_account;  //转出账号长度为12位，被认为是新账号
		}
		else
		{
			straccount="";
		}
		 
		return straccount;
	}
	
	/* 该方法用于获得转入旧账号 */
	public String get_in_account_old(String in_account)
	{
		String straccount="";
		
		if(in_account.length()==17||in_account.length()==21)
		{
			straccount=in_account;  //转出账号长度为17位或21位，被认为是旧账号
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
	  该方法用于产生数据头部分
	  参数说明：
	  c_head_file：源文件名
	  c_head_orgidt：交易机构号
	  c_head_tlr：交易柜员号
	  c_head_ter：交易终端号
	  c_head_visanum：单位签约号
	  c_head_dealnum：数据批次
	  c_head_notedate：记账日
	*/
	public String deal_with_head(String c_head_file,String c_head_orgidt,String c_head_tlr,String c_head_ter,String c_head_visanum,String c_head_dealnum,String c_head_notedate)
	{
		String restr="";
		
		this.head_fileflag="H";
		System.out.println("记录标志："+this.head_fileflag);
		
		this.head_seqnum="000000000001";
		System.out.println("数据头顺序号："+this.head_seqnum);
		
		this.head_orgflag="0";
		System.out.println("机构过账标识："+this.head_orgflag);
		
		this.head_file=format_parameter(c_head_file,50," ","right");
		System.out.println("源文件名称："+this.head_file);
		
		this.head_orgidt=format_parameter(c_head_orgidt,5,"0","left");
		System.out.println("交易机构号："+this.head_orgidt);
		
		this.head_curcde="CNY";
		System.out.println("货币号："+this.head_curcde);
		
		this.head_numcount=format_parameter(get_numcount(),12,"0","left");
		System.out.println("合计笔数："+this.head_numcount);
		
		this.head_sumbal=format_parameter(get_sumbal(),17,"0","left");
		System.out.println("合计金额："+this.head_sumbal);
		
		this.head_flagbal1="+";
		System.out.println("金额符号："+this.head_flagbal1);
		
		this.head_channel="T";
		System.out.println("渠道号："+this.head_channel);
		
		this.head_tlr=format_parameter(c_head_tlr,7,"0","left");
		System.out.println("交易柜员号："+this.head_tlr);
		
		this.head_ter=format_parameter(c_head_ter,3,"0","left");
		System.out.println("交易终端号："+this.head_ter);
		
		this.head_visanum=format_parameter(c_head_visanum,10," ","right");
		System.out.println("单位签约号："+this.head_visanum);
		
		this.head_dealnum=format_parameter(c_head_dealnum,3,"0","left");
		System.out.println("数据批次："+this.head_dealnum);
		
		//this.head_notedate=get_date();
		//this.head_notedate="20100210";
		this.head_notedate=c_head_notedate;
		System.out.println("记账日期："+this.head_notedate);
		
		this.head_filetyp="AGNT";
		System.out.println("文件处理类型："+this.head_filetyp);
		
		this.head_succount=return_blank(12);
		
		this.head_sucbal=return_blank(17);
		
		this.head_flagbal2="+";         //"金额符号位"，尚未确定
		
		this.head_failcount=return_blank(12);
		
		this.head_failbal=return_blank(17);
		
		this.head_flagbal3="+";         //"金额符号位"，尚未确定
		
		this.head_returnflag=return_blank(4);
		
		this.head_returninfor=return_blank(30);  //全角，空
		
		this.head_filler=return_blank(224);
		
		//拼装数据头
		restr=head_fileflag+head_seqnum+head_orgflag+head_file+head_orgidt+head_curcde+head_numcount+head_sumbal+head_flagbal1+head_channel+head_tlr+head_ter+head_visanum+head_dealnum+head_notedate+head_filetyp+head_succount+head_sucbal+head_flagbal2+head_failcount+head_failbal+head_flagbal3+head_returnflag+head_returninfor+head_filler;
		
		return restr;
	}
	
	/* 
	  该方法用于产生数据体体信息
	  参数说明：
	  c_num：excel：行号
	  c_body_orgidt：交易机构号
	  c_body_trancode：交易码
	  c_body_inaccount：转入账号
	  c_body_promocode：BOC提示码
	*/
	public String deal_with_body(int c_num,String c_body_orgidt,String c_body_trancode,String c_body_inaccount,String c_body_promocode)
	{
		String restr="";
		
		int num=c_num;
		
		String tmp="";
		
		this.body_fileflag="D";
		System.out.println("数据体记录标志："+this.body_fileflag);
		
		this.body_seqnum=format_parameter(String.valueOf(num+1),12,"0","left");
		System.out.println("数据体顺序号："+this.body_seqnum);
		
		this.body_orgidt=format_parameter(c_body_orgidt,5,"0","left");
		System.out.println("交易机构号："+this.body_orgidt);
		
		this.body_cusidt=return_blank(10);
		System.out.println("客户号："+this.body_cusidt);
		
		this.body_newoutaccount=format_parameter(get_list_value(num,0),17,"0","left");
		System.out.println("转出新账号："+this.body_newoutaccount);
		
		/*
		tmp=get_list_value(num,1);
		tmp=tmp.trim();
		if(!tmp.equals(""))
		{
			this.body_oldoutaccount=format_parameter("52"+get_list_value(num,1),25," ","right");
		    System.out.println("转出旧账号："+this.body_oldoutaccount);
		}
		else
		{
			this.body_oldoutaccount=format_parameter(" ",25," ","right");
		    System.out.println("转出旧账号："+this.body_oldoutaccount);
		}
		*/
		
        this.body_oldoutaccount=format_parameter(get_list_value(num,1),25," ","right");
		System.out.println("转出旧账号："+this.body_oldoutaccount);
		
		this.body_outcard=format_parameter(get_list_value(num,2),19," ","right");
		System.out.println("转出卡号："+this.body_outcard);
		
		this.body_subacctyp_out="CNY0";
		System.out.println("帐户子类别（转出）："+this.body_subacctyp_out);
		
		this.body_filler1=return_blank(1);
		
		this.body_newinaccount=format_parameter(get_in_account_new(c_body_inaccount),17,"0","left");
		System.out.println("转入新账号："+this.body_newinaccount);
		
		this.body_oldinaccount=format_parameter(get_in_account_old(c_body_inaccount),25," ","right");
		System.out.println("转入旧账号："+this.body_oldinaccount);
		
		this.body_incard=format_parameter(" ",19," ","right");
		System.out.println("转入卡号："+this.body_incard);
		
		this.body_subacctyp_in="CNY0";
		System.out.println("帐户子类别（转出）："+this.body_subacctyp_in);
		
		this.body_ptyp=format_parameter(get_list_value(num,3),4," ","right");
		System.out.println("产品类别："+this.body_ptyp);
		
		this.body_subptyp=format_parameter(get_list_value(num,4),4," ","right");
		System.out.println("产品子类别："+this.body_subptyp);
		
		this.body_bal=format_parameter(get_list_value(num,5),17,"0","left");
		System.out.println("交易金额："+this.body_bal);
		
		this.body_flagbal="+";                      //"金额符号位"
		
		this.body_promocode=format_parameter(c_body_promocode,2," ","right"); //"提示码"，待定
		System.out.println("BOC提示码为："+this.body_promocode);
		
		this.body_pastyp=return_blank(2);                      //"证件类型"，选填部分
		
		this.body_passno=return_blank(30); //"证件号码"，选填部分
		
		this.body_checkout_type="0";                //"校验"
		
		this.body_checkout_direct=return_blank(1);
		
		this.body_trancode=format_parameter(c_body_trancode,6,"0","left");
		System.out.println("交易码："+this.body_trancode);
		
		this.body_mainnum=return_blank(9);             
	
        this.body_maindate=return_blank(8);             
        
        this.body_returnflag=return_blank(4);               
        
        this.body_returninfor=return_blank(30);  //全角，空
        
        this.body_ballance=return_blank(17);     //选填部分
        
        this.body_ballance_flag=return_blank(1);                //选填部分
        
        this.body_filler2=return_blank(2);                     //选填部分
        
        this.body_cusname=format_parameter(get_list_value(num,6),58," ","right");
        System.out.println("账户名："+this.body_cusname);
        
        this.body_store=return_blank(50);
        
        this.body_red=return_blank(48);
        
        restr=body_fileflag+body_seqnum+body_orgidt+body_cusidt+body_newoutaccount+body_oldoutaccount+body_outcard+body_subacctyp_out+body_filler1+body_newinaccount+body_oldinaccount+body_incard+body_subacctyp_in+body_ptyp+body_subptyp+body_bal+body_flagbal+body_promocode+body_pastyp+body_passno+body_checkout_type+body_checkout_direct+body_trancode+body_mainnum+body_maindate+body_returnflag+body_returninfor+body_ballance+body_ballance_flag+body_filler2+body_cusname+body_store+body_red;
        
        System.out.println("#########################################################################");
		
		return restr;
	}
}