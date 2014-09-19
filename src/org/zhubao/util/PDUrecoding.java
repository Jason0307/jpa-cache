package org.zhubao.util;
/*
 * 
 * 接收到的短信格式说明
 * 
 *			0891683108200905F0  240D91683198918274F0  0008 80700122456423 10 62114EEC76848BDD91CC9762524D9762
 *			08 SMSC 地址信息的长度共8 个八位字节(包括91)
 *		    91 SMSC 地址格式(TON/NPI) 用国际格式号码(在前面加‘+’)
 *			68 31 08 10 00 05 F0 SMSC 地址8613800100500，补‘F’凑成偶数个 
 *			24 基本参数(TP-MTI/VFP) 收到的短信来源
 *			0D 目标地址数字个数共13 个十进制数
 *			91 目标地址格式(TON/NPI)
 *				A1：国内格式
 *				91：国际格式
 *				81：未知，+86 可带可不带。
 *			3198918274F0 短信来源地址(TP-DA) 8613891928470，补‘F’凑成偶数个:
 *			00 协议标识(TP-PID) 是普通GSM 类型，点到点方式
 *			08用户信息编码方式(TP-DCS)，00：表示7-bit 编码， 08：表示UCS2 编码，04：表示8-bit 编码。
 *			80700122456423 短信发送时间：08年07月10日22时54分46秒23
 *			10 信息长度(TP-UDL) 实际长度16个字节
 *			6211 4EEC 7684 8BDD 91CC 9762 524D 9762用户信息(TP-UD) “我们的话里面前面”
 *
 * */


public class PDUrecoding {
	private String smsCenter;	//短信中心号码
	private String smsTelno;	//发送手机号码
	private String sendtime;	//发送时间
	private String smstxt;		//发送内容
	
	/*
	 * 构造方法
	 * */
	public PDUrecoding(String sms){
		String s = sms.substring(0,2);
		int nLength = Integer.parseInt(s, 16)*2;
		s = sms.substring(2,2+nLength);
		smsCenter = getCenter(s);
		nLength = 4+ nLength;
		s= sms.substring(nLength,2+nLength);
		int tellength = Integer.parseInt(s, 16);
		s =sms.substring(nLength,5+nLength+tellength);
		smsTelno = getCenter(s);
		
		//System.out.println(smsTelno);
	}

	 /*
	  * 取出短信中心号码
	  * */
	 private String getCenter(String str){
	  StringBuffer  sb=new  StringBuffer(); 
	  sb.append(change(str));
	  String src = sb.substring(4, sb.length());
	  if(src.charAt(src.length()-1)=='F')
		   src = src.substring(0, src.length()-1);
	    
	  //System.out.println("短信中心号码是："+src); 
	  return src; 
	 }
	
	/*奇偶交换的程序*/
	private String change(String srv)
	{
		int nLength = srv.length();
		StringBuffer s = new StringBuffer();
		for(int i = 1 ; i < nLength;i += 2)   //奇偶互换
		{
			s.append(srv.charAt(i));
			s.append(srv.charAt(i-1));
		}
		return s.toString();
	}

	 /*
	  * 对收到的pdu字符串解码，翻译成字符形式
	  * */ 
	 private static String toStringChang(String str){
	  String sr = str.substring(0, 2);
	  System.out.print("字符内容长度：");
	  System.out.println(Integer.valueOf(sr, 16)/2);
	  str = str.substring(2,str.length());
	  StringBuffer  sb=new  StringBuffer(); 
	   for(int  i=0;i+4<=str.length();i=i+4)  
	         {  
	                 int  j=Integer.parseInt(str.substring(i,i+4),16);  
	                 sb.append((char)j);  
	         }  
	         System.out.println("字符内容是："+sb); 
	         
	         return new String(sb);
	 }
	 
	/*把短信的内容显示出去*/
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("短信内容是："+smstxt+"\n");
		sb.append("由"+smsTelno+"在"+sendtime+"发送\n");
		sb.append("短信中心号码："+smsCenter+"\n");
		
		return sb.toString();
	}




	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//String s ="0891683108200905F0240D91683198918274F00008807001224564231062114EEC76848BDD91CC9762524D9762";
		//System.out.println(new PDUrecoding(s));
		toStringChang("1062114EEC76848BDD91CC9762524D9762");
	}

}
