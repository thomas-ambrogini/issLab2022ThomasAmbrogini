package unibo.actor22.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

import unibo.actor22.distrib.RadarSystemDistribPc;
import unibo.actor22.distrib.RadarSystemDistribRasp;
import unibo.actor22.distrib.observable.RadarSystemDistribObservablePc;
import unibo.actor22.distrib.observable.RadarSystemDistribObservableRasp;
import unibo.actor22.local.RadarSystemLocal;
import unibo.actor22.local.UsingLedAndControllerLocal;
import unibo.actor22comm.interfaces.IApplication;
import unibo.actor22comm.utils.ColorsOut;


public class MainSelector {
	public HashMap<String,IApplication> programs = new HashMap<String,IApplication>();
	
	protected void outMenu() {
		for (String i : programs.keySet()) { //
			  System.out.println( ""+i + "    " + programs.get(i).getName() );
		}
 	}
	
	public void doChoice() {
		try {
			programs.put("1", new UsingLedAndControllerLocal() );
			programs.put("2", new RadarSystemLocal() );
			programs.put("3", new RadarSystemDistribPc() );
			programs.put("4", new RadarSystemDistribRasp() );
			programs.put("5", new RadarSystemDistribObservablePc() );
			programs.put("6", new RadarSystemDistribObservableRasp() );
  			String i = "";
			outMenu();
			ColorsOut.outappl(">>>   ", ColorsOut.ANSI_PURPLE);
 			BufferedReader inputr = new BufferedReader(new InputStreamReader(System.in));
			i =  inputr.readLine();
 			programs.get( i ).doJob("DomainSystemConfig.json", "CommSystemConfig.json");
 		} catch ( Exception e) {
			 ColorsOut.outerr("ERROR:" + e.getMessage() );
		}
	}
	
	public static void main( String[] args) throws Exception {
		ColorsOut.outappl("---------------------------------------------------", ColorsOut.BLUE);
		ColorsOut.outappl("MainSelector: this application uses Config Files", ColorsOut.BLUE);
		ColorsOut.outappl("---------------------------------------------------", ColorsOut.BLUE);
		new MainSelector().doChoice();
	}
}
