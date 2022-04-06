package unibo.actor22.common;
 
import it.unibo.kactor.IApplMessage;
import unibo.actor22.*;
import unibo.actor22comm.utils.ColorsOut;
import unibo.actor22comm.utils.CommUtils;

/*
 * Il controller conosce SOLO I NOMI dei dispositivi 
 * (non ha riferimenti ai dispositivi-attori)
 */
public class ControllerActor extends QakActor22{
	protected int numIter = 1;
	protected IApplMessage getStateRequest ;
	protected IApplMessage getDistance;
	protected IApplMessage updateRadar;
	protected boolean on = true;

	public ControllerActor(String name  ) {
		super(name);
		
		getStateRequest  = ApplData.buildRequest(name,"ask", ApplData.reqLedState, ApplData.ledName);
		getDistance		 = ApplData.buildRequest(name, "ask", ApplData.reqGetDistance, ApplData.sonarName);
		updateRadar = ApplData.buildRequest(name, "ask", ApplData.cmdUpdate, ApplData.sonarName);
 	}

	@Override
	protected void handleMsg(IApplMessage msg) {  
		if( msg.isReply() ) {
			elabAnswer(msg);
		}else { 
			elabCmd(msg) ;	
		}
 	}
	
	protected void elabCmd(IApplMessage msg) {
		String msgCmd = msg.msgContent();
		ColorsOut.outappl( getName()  + " | elabCmd=" + msgCmd, ColorsOut.GREEN);
		switch( msgCmd ) {
			case ApplData.cmdActivate  : {
				activateSonar();
	 			break;
			}
			case ApplData.cmdDeactivate : {
				deactivateSonar();
				break;
			}
			case ApplData.cmdGetDistance : {
				getSonarDistance();
				break;
			}
			case ApplData.cmdUpdate      : {
				updateRadar();
				break;
			}
			default:break;
		}		
	}
	
	protected void activateSonar() {
		forward( ApplData.activateSonar );
	}
	
	protected void deactivateSonar() {
		forward( ApplData.deactivateSonar );
	}
	
	protected void getSonarDistance() {
		request(getDistance);
	}
	
	protected void updateRadar() {
		request(updateRadar);
	}
	
	protected void wrongBehavior() {
  	    //WARNING: Inviare un treno di messaggi VA EVITATO
		//mantiene il controllo del Thread degli attori (qaksingle)		
		for( int i=1; i<=3; i++) {
			forward( ApplData.turnOffLed );
			CommUtils.delay(500);
			forward( ApplData.turnOnLed );
			CommUtils.delay(500);		
		}
		forward( ApplData.turnOffLed );
	}
	
    protected void doControllerWork() {
		CommUtils.aboutThreads(getName()  + " |  Before doControllerWork on=" + on );
		//wrongBehavior();
  		//ColorsOut.outappl( getName()  + " | numIter=" + numIter  , ColorsOut.GREEN);		
	    if( numIter++ < 5 ) {
	        if( numIter%2 == 1)  forward( ApplData.turnOnLed ); //accesione
	        else forward( ApplData.turnOffLed ); //spegnimento
	        request(getStateRequest);
	      }else {
	    	  forward( ApplData.turnOffLed );
			  //ColorsOut.outappl(getName() + " | emit " + ApplData.endWorkEvent, ColorsOut.MAGENTA);
	    	  //emit( ApplData.endWorkEvent );
	      }
		
	}
    
	protected void elabAnswer(IApplMessage msg) {
		String msgRep = msg.msgContent();
		String msgId  = msg.msgId();
		ColorsOut.outappl( getName()  + " | elabAnswer=" + msgRep, ColorsOut.GREEN);
		switch( msgId ) {
			case ApplData.cmdActivate  : {
				activateSonar();
	 			break;
			}
			case ApplData.cmdDeactivate : {
				deactivateSonar();
				break;
			}
			case ApplData.cmdGetDistance : {
				ColorsOut.outappl(msg.msgContent(), ColorsOut.RED);
			}
			case ApplData.cmdUpdate      : {
				String updateMessage= "{ \"distance\" : D , \"angle\" : A }".replace("D",msgRep).replace("A","90");
				IApplMessage reply = ApplData.buildDispatch(ApplData.controllerName, ApplData.cmdUpdate, updateMessage, ApplData.radarName);
				forward(reply);
			}
			default:break;
		}
	}

}
