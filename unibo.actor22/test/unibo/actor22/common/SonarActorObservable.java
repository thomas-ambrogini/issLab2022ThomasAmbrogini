package unibo.actor22.common;

import it.unibo.kactor.IApplMessage;
import it.unibo.kactor.MsgUtil;
import it.unibo.radarSystem22.domain.DeviceFactory;
import it.unibo.radarSystem22.domain.interfaces.IDistanceMeasured;
import it.unibo.radarSystem22.domain.interfaces.ISonarObservable;
import unibo.actor22.Qak22Context;
import unibo.actor22.QakActor22;
import unibo.actor22comm.utils.ColorsOut;
import unibo.actor22comm.utils.CommUtils;

public class SonarActorObservable extends QakActor22{
	private ISonarObservable sonar;
	private IDistanceMeasured distMeasured;
	
	public SonarActorObservable(String name) {
		super(name);
		sonar = DeviceFactory.createSonarObservable();
		distMeasured = sonar.getDistanceMeasured();
	}

	@Override
	protected void handleMsg(IApplMessage msg) {
		CommUtils.aboutThreads(getName()  + " |  Before doJob - ");
		ColorsOut.out( getName()  + " | doJob " + msg, ColorsOut.CYAN);
		
		if( msg.isRequest() ) 
			elabRequest(msg);
		else 
			elabCmd(msg);
	}
 
	protected void elabRequest(IApplMessage msg) {
		String msgReq = msg.msgContent();
		ColorsOut.out( getName()  + " | elabRequest " + msgReq, ColorsOut.CYAN);
		switch( msgReq ) {
			case ApplData.reqIsActive   :{
				boolean b = sonar.isActive();
				IApplMessage reply = MsgUtil.buildReply(getName(), ApplData.reqIsActive, ""+b, msg.msgSender());
				ColorsOut.out( getName()  + " | reply= " + reply, ColorsOut.CYAN);
 				sendReply(msg, reply );				
				break;
			}
 			default: ColorsOut.outerr(getName()  + " | unknown " + msgReq);
		}
	}
	
	protected void elabCmd(IApplMessage msg) {
		String msgCmd = msg.msgContent();
 		switch( msgCmd ) {
			case ApplData.cmdActivate   : sonar.activate();break;
			case ApplData.cmdDeactivate : sonar.deactivate();break;
			case ApplData.addObserver   : addObserver(msg.msgSender(), msg.msgId());break;
			default: ColorsOut.outerr(getName()  + " | unknown " + msgCmd);break;
		}
	}
	
	private void addObserver(String sender, String host) {
		host = host.replace("'", "");
		String hostname = host.split(":")[0];
		String port = host.split(":")[1];
		Qak22Context.setActorAsRemote( 
				sender, port, hostname, ApplData.protocol);
		
		distMeasured.addObserver(new SonarObserverDist(sender));
	}
}
