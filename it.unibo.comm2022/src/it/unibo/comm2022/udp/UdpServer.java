package it.unibo.comm2022.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;

import it.unibo.comm2022.interfaces.IApplMsgHandler;
import it.unibo.comm2022.interfaces.Interaction2021;
import it.unibo.comm2022.utils.ColorsOut;

public class UdpServer extends Thread {
	private DatagramSocket serverSocket;
	private DatagramPacket packet;
	private Map<SocketAddress, Integer> map;
	protected IApplMsgHandler userDefHandler;
	protected String name;
	protected boolean stopped = true;

 	public UdpServer( String name, int port,  IApplMsgHandler userDefHandler   ) {
 		super(name);
		
 		try {
			ColorsOut.out(getName() + " | costructor port=" + port, ColorsOut.BLUE  );  
			this.name             = getName();
			this.userDefHandler   = userDefHandler;
			serverSocket		  = new DatagramSocket(port);
			
			byte[] receive 		  = new byte[2048];
			packet				  = new DatagramPacket( receive, receive.length );
			
			map = new HashMap<SocketAddress, Integer>();
			
		}catch (Exception e) { 
			 ColorsOut.outerr(getName() + " | costruct ERROR: " + e.getMessage());
		}
	}
	
	@Override
	public void run() {
	      
		try {
			ColorsOut.out(getName() + " | STARTING ... ", ColorsOut.BLUE  );
			
			while( ! stopped ) {
				serverSocket.receive(packet);
				SocketAddress senderAddress = packet.getSocketAddress();
				
				if(map.containsKey(senderAddress) == false) {
					System.out.println("la mappa non contiene l'address " + senderAddress);
					DatagramSocket socket = new DatagramSocket();
					sleep(100);
					map.put(senderAddress, socket.getLocalPort());
					Interaction2021 conn = new UdpConnection(socket, packet.getAddress().toString().replace("/", ""), packet.getPort());
					new UdpApplMessageHandler( userDefHandler, conn );
				}
	
				int port = map.get(senderAddress);
				packet.setPort(port);
				System.out.println("reindirizzo pacchetto a " + packet.getSocketAddress());
				serverSocket.send(packet);
				
				ColorsOut.out(getName() + " | accepted connection  ", ColorsOut.BLUE   );  
						 		
			}
			
		}catch (Exception e) {  //Scatta quando la deactive esegue: serversock.close();
			ColorsOut.out(getName() + " | probably socket closed: " + e.getMessage(), ColorsOut.GREEN);		 
		}
	}
	
	public void activate() {
		if( stopped ) {
			stopped = false;
			this.start();
		}
	}
 
	public void deactivate() {
		try {
			ColorsOut.out(getName()+" |  DEACTIVATE serversocket=" +  serverSocket);
			stopped = true;
			serverSocket.close();
			
		} catch (Exception e) {
			ColorsOut.outerr(getName() + " | deactivate ERROR: " + e.getMessage());	 
		}
	}
	
}