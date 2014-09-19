package org.zhubao.util;

/**
 * 短信格式说明
 * 
			0891683108200905F011000D91683198918274F0 000800 0C 9A6C 4E0A 5230 0021 
			08 SMSC 地址信息的长度共8 个八位字节(包括91)
			91 SMSC 地址格式(TON/NPI) 用国际格式号码(在前面加‘+’)
			68 31 08 10 00 05 F0 SMSC 地址8613800100500，补‘F’凑成偶数个 
			11 基本参数(TP-MTI/VFP) 要求发送回复
			00 消息基准值(TP-MR) 
			0D 目标地址数字个数共13 个十进制数
			91 目标地址格式(TON/NPI)
			A1：国内格式
			91：国际格式
			81：未知，+86 可带可不带。
			683119109991F2 目标地址(TP-DA) 8613910199192，补‘F’凑成偶数个:
			00 协议标识(TP-PID) 是普通GSM 类型，点到点方式
			08用户信息编码方式(TP-DCS)，00：表示7-bit 编码， 08：表示UCS2 编码，04：表示8-bit 编码。
			00 有效期(TP-VP) 5 分钟
			0C 用户信息长度(TP-UDL) 实际长度12个字节
			60 A8 59 7D FF 01 9A 6C 4E 0A 52 30用户信息(TP-UD) “您好！马上到”

 * */
public class PDUdecoding
{
   /**
   * 函数功能：短信内容编码
   * 函数名称：smsPDUEncoded(String srvContent)
   * 参    数：srvContent 要进行转换的短信内容,string类型
   * 返 回 值：编码后的短信内容，string类型
   * 函数说明：
   *    把字符串转成4位16进制编码的串
   */
   private String smsPDUEncoded(String srvContent)
    {
     StringBuffer sb = new StringBuffer();
     int length = srvContent.length();
     for(int i=0;i<length;i++){
      String s = srvContent.substring(i, i+1);
      //是字符，前面两位是00 中文需要四位16进制来表示
      if(s.getBytes().length==1){
       sb.append("00");
      }
      sb.append(Integer.toHexString(srvContent.charAt(i)));
     }
     String s = sb.toString();
     sb = new StringBuffer();
     //最后接上内容编码长度的1/2 用16进制的2位来表示
     if(Integer.toHexString(srvContent.length()*2).length()<2){
      sb.append("0");
     }
     sb.append(Integer.toHexString(srvContent.length()*2));
     sb.append(s);
     return sb.toString();
    }

   /**
   * 函数功能：短信中心号编码
   * 函数名称：smsDecodedCenterNumber(String srvCenterNumber)
   * 参    数：srvCenterNumber 要进行转换的短信中心号,string类型
   * 返 回 值：编码后的短信中心号，string类型
   * 函数说明：
   *     1，将奇数位和偶数位交换。
   *     2，短信中心号奇偶数交换后，看看长度是否为偶数，如果不是，最后添加F
   *     3，加上短信中心号类型，91为国际化
   *     4，计算编码后的短信中心号长度，并格化成二位的十六进制
   */
   private String smsDecodedCenterNumber(String srvCenterNumber)
   {
    if (!(srvCenterNumber.substring(0,2).equals("86")))
    {
     srvCenterNumber = "86"+srvCenterNumber;     //检查当前接收手机号是否按标准格式书写，不是，就补上“86”
    }
    StringBuffer s = new StringBuffer();
    int nLength = srvCenterNumber.length();
    if(Integer.toHexString((nLength+3)/2).toString().length()<2){
     s.append("0");
    }
    s.append(Integer.toHexString((nLength+3)/2));
    s.append("91");
    for(int i = 1 ; i < nLength;i += 2)                       //奇偶互换
    {
     s.append(srvCenterNumber.charAt(i));
     s.append(srvCenterNumber.charAt(i-1));
    }
    if(!(nLength % 2 == 0))                           //是否为偶数，不是就加上F，并对最后一位与加上的F位互换
    {
     s.append('F');
     s.append(srvCenterNumber.charAt(nLength-1));
    }
    return s.toString();
   }

   /**
   * 函数功能：接收短信手机号编码
   * 函数名称：smsDecodedNumber(String srvNumber)
   * 参    数：srvCenterNumber 要进行转换的短信中心号,string类型
   * 返 回 值：编码后的接收短信手机号，string类型
   * 函数说明：
   *     1，检查当前接收手机号是否按标准格式书写，不是，就补上“86”
   *     1，将奇数位和偶数位交换。
   *     2，短信中心号奇偶数交换后，看看长度是否为偶数，如果不是，最后添加F
   *          11000D91和000800   在国内，根据PDU编码原则，我们写死在此   
   * 
   */
   private String smsDecodedNumber(String srvNumber)
   {
    StringBuffer s = new StringBuffer();
    s.append("11000D91");
    if (!(srvNumber.substring(0,2) == "86"))
    {
     s.append("68");     //检查当前接收手机号是否按标准格式书写，不是，就补上“86”
    }
    int nLength = srvNumber.length();
    for(int i = 1 ; i < nLength ; i += 2)                 //将奇数位和偶数位交换
    {
     s.append(srvNumber.charAt(i));
     s.append(srvNumber.charAt(i-1));
    }
    if(!(nLength % 2 == 0))                              //是否为偶数，不是就加上F，并对最后一位与加上的F位互换
    {
     s.append('F');
     s.append(srvNumber.charAt(nLength-1));
    }
    s.append("000800");
    return s.toString();
   }

   /**
   * 函数功能：整个短信的编码
   * 函数名称：smsDecodedsms(String strCenterNumber, String strNumber, String strSMScontent)
   * 参    数：strCenterNumber 要进行转换的短信中心号,string类型
   *     strNumber       接收手机号码，string类型
   *     strSMScontent   短信内容，String类型
   * 返 回 值：完整的短信编码，可以在AT指令中执行，string类型 最后两位是要发送的字符长度
   * 函数说明：
   */
   public  String smsDecodedsms(String strCenterNumber, String strNumber, String strSMScontent)
   {
    StringBuffer sb = new StringBuffer();
    sb.append(smsDecodedCenterNumber(strCenterNumber));
    sb.append(smsDecodedNumber(strNumber));
    sb.append(smsPDUEncoded(strSMScontent));
    sb.append((smsDecodedNumber(strNumber).length()));
    return sb.toString();
   }
   
   public static void main(String[] args){
	   PDUdecoding pdu = new PDUdecoding();
	   
	   String s =pdu.smsDecodedsms("18621926390", "18621926390", "我们的测试短信"); 
	   
	   System.out.println(s);
	   
   }
}

