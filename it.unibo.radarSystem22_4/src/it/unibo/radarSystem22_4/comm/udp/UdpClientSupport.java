package it.unibo.radarSystem22_4.comm.udp;

import it.unibo.radarSystem22_4.comm.interfaces.Interaction2021;
import it.unibo.radarSystem22_4.comm.utils.ColorsOut;

public class UdpClientSupport {

public static Interaction2021 connect(String host, int port ) throws Exception {	 
		
		try {
			Interaction2021 conn  =  new UdpConnection( host, port );
			return conn;
			
		}catch(Exception e) {
			ColorsOut.out("UdpClient | Another attempt to connect with host:" + host + " port=" + port);
			Thread.sleep(500);
		}
		
		throw new Exception("UdpClient | Unable to connect to host:" + host);
 	}
}
