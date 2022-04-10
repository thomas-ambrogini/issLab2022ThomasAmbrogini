package unibo.actor22.common;
 
import it.unibo.kactor.IApplMessage;
import it.unibo.radarSystem22.domain.Distance;
import it.unibo.radarSystem22.domain.interfaces.IDistance;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;
import unibo.actor22.*;
import unibo.actor22comm.utils.ColorsOut;
import unibo.actor22comm.utils.CommSystemConfig;
import unibo.actor22comm.utils.CommUtils;

/*
 * Il controller conosce SOLO I NOMI dei dispositivi 
 * (non ha riferimenti ai dispositivi-attori)
 */
public class ControllerActor extends QakActor22{
	protected int numIter = 1;
	protected boolean on = true;
	
	public ControllerActor(String name  ) {
		super(name);
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
		String msgId = msg.msgId();
		ColorsOut.outappl( getName()  + " | elabCmd=" + msgId, ColorsOut.GREEN);
		switch( msgId ) {
			case ApplData.cmdActivate :{
				forward(ApplData.activateSonar);
				doControllerWork();
	 			break;
			}
			case ApplData.distanceUpdate :{
				checkLed(msg.msgContent());
				break;
			}
			default:break;
		}		
	}
	
	protected void elabAnswer(IApplMessage msg) {
		ColorsOut.outappl( getName()  + " | elabAnswer numIter=" + numIter + " "+ msg, ColorsOut.MAGENTA);
 		CommUtils.delay(500);
 		String msgId      = msg.msgId();
 		String msgContent = msg.msgContent();
		ColorsOut.out( getName()  + " | elabAnswer " + msgId, ColorsOut.CYAN);
		switch( msgId ) {
			case ApplData.reqGetDistance   :{
				IDistance distance = new Distance(msgContent);
				if(distance.getVal() <= DomainSystemConfig.DLIMIT ) {
					forward( ApplData.turnOnLed );
				}else {
					forward( ApplData.turnOffLed );
				}
				String updateMessage= "{ \"distance\" : D , \"angle\" : A }".replace("D",distance.toString()).replace("A","60");
				IApplMessage updateRadar   = Qak22Util.buildDispatch(getName(), "update", updateMessage, ApplData.radarName);
				forward( updateRadar);
				doControllerWork();
				break;
			}
 			default: ColorsOut.outerr(getName()  + " | unknown " + msgId);
		}
	}
	
    protected void doControllerWork() {
		CommUtils.aboutThreads(getName()  + " |  Before doControllerWork on=" + on );
		IApplMessage getDistanceRequest   = Qak22Util.buildRequest(getName(), "ask", ApplData.reqGetDistance, ApplData.sonarName);
		IApplMessage addObserver		  = Qak22Util.buildDispatch(getName(),"'"+ CommSystemConfig.hostAddress + ":" + ApplData.ctxPortPc + "'", ApplData.addObserver, ApplData.sonarName);
		System.out.println(addObserver);
		//request(getDistanceRequest);
		forward(addObserver);
		
	}
    
    private void checkLed(String value) {
    	int val = Integer.parseInt(value);
    	if(val <= DomainSystemConfig.DLIMIT ) {
			forward( ApplData.turnOnLed );
		}else {
			forward( ApplData.turnOffLed );
		}
    }

}
