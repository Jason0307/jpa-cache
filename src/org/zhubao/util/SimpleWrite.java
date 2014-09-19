package org.zhubao.util;



import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import javax.comm.CommPortIdentifier;
import javax.comm.SerialPort;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SimpleWrite extends JFrame implements Runnable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3409516298064386893L;
	private String commName;
	private JTextArea smsLst;
	private JPanel tools,tset;
	private JButton btnSend;
	private JTextField smstxt,smscenter,smstelno;
	private Thread readThread = null;
	private OutputStream outputStream =null;
	private InputStream inputStream = null;
	
	
	public SimpleWrite(String comname){
		super("sms");
		commName = comname;
		setBounds(100,100,400,300);
		init();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private void init(){
		smsLst = new JTextArea();
		tools = new JPanel();
		btnSend = new JButton("Send");
		btnSend.addActionListener(new BtnClick());
		smstxt = new JTextField("Ӣ˔׌х",20);
		
		tset = new JPanel();
		smscenter = new JTextField("13800290500",8);
		smstelno = new JTextField("13891928470",8);
		
		tset.add(new JLabel("׌хאфۅë"));
		tset.add(smscenter);
		tset.add(new JLabel("ה׽˖ܺۅë"));
		tset.add(smstelno);
		
		tools.add(smstxt);
		tools.add(btnSend);
		add(tset,BorderLayout.NORTH);
		add(smsLst,BorderLayout.CENTER);
		add(tools,BorderLayout.SOUTH);
		if (!initcomm()){
			System.out.println("ָ֨քԮࠚϞרԵʼۯá");
			System.exit(0);
		}
			
		readThread = new Thread(this);
		readThread.start();
	}
	
	private boolean initcomm(){
	     Enumeration portList;
	     CommPortIdentifier portId;
	     SerialPort serialPort;
	     
	     portList = CommPortIdentifier.getPortIdentifiers();

	        while (portList.hasMoreElements()) {
	            portId = (CommPortIdentifier) portList.nextElement();
	            System.out.println(portId.getName());
	            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
	                if (portId.getName().equals(commName)) {
	            		try {
							serialPort = (SerialPort)
							portId.open("mycomm", 2000);
		                    serialPort.setSerialPortParams(115200,
	                                SerialPort.DATABITS_8,
	                                SerialPort.STOPBITS_1,
	                                SerialPort.PARITY_NONE);	
		                    outputStream = serialPort.getOutputStream();
		                    inputStream = serialPort.getInputStream();
		                    return true;
						} catch (Exception e) {
							// TODO ؔ֯ʺԉ catch ࠩ
							e.printStackTrace();
							return false;
						}

	                }
	            }
	        }
	        return false;
	}
	     		

	public void run() {
	    try {
            Thread.sleep(20000);
            readSMS();
        } catch (InterruptedException e) {}
		
	}	

	class BtnClick implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			PDUdecoding pdu = new PDUdecoding();
			String str = pdu.smsDecodedsms(
					smscenter.getText(),
					smstelno.getText(),
					smstxt.getText());
			System.out.println(str);
			writeSMS(str);
			
		}
		
	}

	private void writeSMS(String str){
       String messageString =str;
			                       
       int x = (str.length()-18)/2;
       try {
		outputStream.write(("AT+CMGS="+x+"\r\n").getBytes());
	       Thread.sleep(500);
	       outputStream.write(messageString.getBytes());
	       outputStream.write(0x1A);
	       outputStream.flush();
       } catch (Exception e) {
    	   // TODO ؔ֯ʺԉ catch ࠩ
    	   e.printStackTrace();
       }								
	}

	
	private void readSMS(){

		byte [] buffer = new byte[1024];
		while(true){
			try {
				int x = inputStream.read(buffer);
				String str = new String (buffer,0,x);
				smsLst.append(str+":"+x);	
				if (str.indexOf("+CMTI:") !=-1){
					str = str.substring(str.length()-2);
					System.out.println(str);
					outputStream.write(("AT+CMGR="+str+"\r\n").getBytes());
				}
			} catch (IOException e) {
				// TODO ؔ֯ʺԉ catch ࠩ
				e.printStackTrace();
			}
		}
										
	}

	
    public static void main(String[] args) {
    	new SimpleWrite("COM2");
    }

 }
