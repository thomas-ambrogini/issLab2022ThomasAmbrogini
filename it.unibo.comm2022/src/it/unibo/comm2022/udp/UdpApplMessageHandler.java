package it.unibo.comm2022.udp;

import it.unibo.comm2022.interfaces.IApplMsgHandler;
import it.unibo.comm2022.interfaces.Interaction2021;
import it.unibo.comm2022.utils.ColorsOut;

public class UdpApplMessageHandler extends Thread {
	private IApplMsgHandler handler ;
	private Interaction2021 conn;

	public UdpApplMessageHandler(  IApplMsgHandler handler, Interaction2021 conn ) {
		System.out.println("Message handler creato");
		this.handler = handler;
		this.conn    = conn;
 		this.start();
	}
 	
	@Override 
	public void run() {
		String name = handler.getName();
		
		try {
			ColorsOut.out( "UdpApplMessageHandler | STARTS with handler=" + name + " conn=" + conn, ColorsOut.BLUE );
			
			while( true ) {
				//ColorsOut.out(name + " | waits for message  ...");
			    String msg = conn.receiveMsg();
			    ColorsOut.out(name + "  | UdpApplMessageHandler received:" + msg + " handler="+handler, ColorsOut.GREEN );
			    
			    if( msg == null ) {
			    	conn.close();
			    	break;
			    }else {
			    	handler.elaborate( msg, conn ); 
			    }
			}
			ColorsOut.out(name + " | BYE"   );
			
		}catch( Exception e) {
			ColorsOut.outerr( name + "  | ERROR:" + e.getMessage()  );
		}	
	}
}
