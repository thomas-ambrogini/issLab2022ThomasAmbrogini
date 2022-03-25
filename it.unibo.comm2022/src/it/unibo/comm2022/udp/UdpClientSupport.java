package it.unibo.comm2022.udp;


import it.unibo.comm2022.interfaces.Interaction2021;
import it.unibo.comm2022.utils.ColorsOut;

public class UdpClientSupport {

public static Interaction2021 connect(String host, int port, int nattempts ) throws Exception {	 
		
		for( int i=1; i<=nattempts; i++ ) {
			try {
 				Interaction2021 conn  =  new UdpConnection( host, port );
				return conn;
			}catch(Exception e) {
				ColorsOut.out("UdpClient | Another attempt to connect with host:" + host + " port=" + port);
				Thread.sleep(500);
			}
		}//for
		throw new Exception("UdpClient | Unable to connect to host:" + host);
 	}
}
