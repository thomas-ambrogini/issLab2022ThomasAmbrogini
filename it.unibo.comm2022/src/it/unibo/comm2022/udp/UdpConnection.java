package it.unibo.comm2022.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import it.unibo.comm2022.interfaces.Interaction2021;
import it.unibo.comm2022.utils.ColorsOut;

public class UdpConnection implements Interaction2021 {
	private DatagramSocket socket;
	private String host;
	private int port;
	 
	public UdpConnection( String host, int port) {
		this.host 	= host;
		this.port 	= port;
		
		try {
			socket		= new DatagramSocket();
			
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public UdpConnection( DatagramSocket socket, String host, int port ) {
		this.socket = socket;
		this.host	= host;
		this.port	= port;
	}
	
	@Override
	public void forward(String msg) throws Exception {
		ColorsOut.out( "UdpConnection | sendALine  " + msg + " on " + socket, ColorsOut.ANSI_YELLOW );	 
		
		try {
			byte[] buf = msg.getBytes();
			DatagramPacket packet = new DatagramPacket( buf, buf.length, InetAddress.getByName(host), port );
			socket.send(packet);
		 
		} catch (IOException e) {
			throw e;
		}
		
	}

	@Override
	public String request(String msg) throws Exception {
		forward(  msg );
		String answer = receiveMsg();
		return answer;
	}

	@Override
	public void reply(String msg) throws Exception {
		forward(msg);
	}

	@Override
	public String receiveMsg() throws Exception {
		
		try {
			byte [] msg = new byte[2048];
			DatagramPacket packetReceive = new DatagramPacket( msg, msg.length );
			socket.receive(packetReceive); 
			return new String( packetReceive.getData(), 0, packetReceive.getLength() );
 			
		} catch ( Exception e   ) {
			ColorsOut.outerr( "UdpConnection | receiveMsg ERROR  " + e.getMessage() );	
	 		return null;
		}	
	}

	@Override
	public void close() throws Exception {
		
		try {
			socket.close();
			ColorsOut.out( "UdpConnection | CLOSED  " );
		} catch (Exception e) {
			ColorsOut.outerr( "UdpConnection | close ERROR " + e.getMessage());	
		}
		
	}

}
