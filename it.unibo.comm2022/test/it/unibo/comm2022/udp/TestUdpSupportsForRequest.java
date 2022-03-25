package it.unibo.comm2022.udp;

import org.junit.After;
import org.junit.Test;


public class TestUdpSupportsForRequest {
private UdpServer server;
public static final int testPort = 8113; 


	//@Before
	public void up() {
	}
	
	@After
	public void down() {
		//if( server != null ) server.deactivate();
	}	
	
	protected void startTheServer(String name) {
		server = new UdpServer(name, testPort, new NaiveApplHandler("naiveH") );
		server.activate();		
	}
	
	//@Test 
	public void testSingleClient() {
		startTheServer("oneClientServer");
 		//Create a connection
		new ClientDoingRequest().doWork("client1",10 );		
		System.out.println("tesSingleClient BYE");
	}
	
	@Test 
	public void testManyClients() {
		startTheServer("manyClientsServer");
		new ClientDoingRequest().doWork("client1",10);
		new ClientDoingRequest().doWork("client2",1);
		new ClientDoingRequest().doWork("client3",1);
		System.out.println("testManyClients BYE");
	}	
	
}
