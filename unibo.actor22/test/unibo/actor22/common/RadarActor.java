package unibo.actor22.common;

import org.json.JSONObject;

import it.unibo.kactor.IApplMessage;
import it.unibo.kactor.MsgUtil;
import it.unibo.radarSystem22.domain.DeviceFactory;
import it.unibo.radarSystem22.domain.interfaces.IDistance;
import it.unibo.radarSystem22.domain.interfaces.IRadarDisplay;
import it.unibo.radarSystem22.domain.interfaces.ISonar;
import unibo.actor22.QakActor22;
import unibo.actor22comm.utils.ColorsOut;
import unibo.actor22comm.utils.CommUtils;

public class RadarActor extends QakActor22{
	private IRadarDisplay radar;
	
	public RadarActor(String name) {
		super(name);
		radar = DeviceFactory.createRadarGui();
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
			case ApplData.reqGetCurDistance :{
				int distance = radar.getCurDistance();
				IApplMessage reply = MsgUtil.buildReply(getName(), ApplData.reqGetCurDistance, ""+distance, msg.msgSender());
				ColorsOut.out( getName()  + " | reply= " + reply, ColorsOut.CYAN);
				sendReply(msg, reply );				
				break;
			}
 			default: ColorsOut.outerr(getName()  + " | unknown " + msgReq);
		}
	}
	
	protected void elabCmd(IApplMessage msg) {
		String msgId  = msg.msgId();
		String msgContent = msg.msgContent();
		msgContent = msgContent.replace("'", "");
 		switch( msgId ) {
 			case ApplData.cmdUpdate :{
 				//{ "distance" : 90 , "angle" : 90 }
 				try {
 					JSONObject jsonObj   = new JSONObject(msgContent);	
 					int curDistance = jsonObj.getInt("distance");
 					String distance = ""+curDistance;
 					String angle    = ""+jsonObj.getInt("angle");
 					radar.update( distance, angle );
 				}catch(Exception e) {
 					e.printStackTrace();
 				}
 			}
			default: ColorsOut.outerr(getName()  + " | unknown " + msgId);
		}
	}

}