package it.unibo.radarSystem22.sprint1;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.json.JSONObject;
import org.json.JSONTokener;

import it.unibo.comm2022.ProtocolType;
import it.unibo.radarSystem22.domain.utils.ColorsOut;

public class RadarSystemConfig {
	public static boolean tracing			= false;
	public static boolean testing			= false;
	public static int DLIMIT				= 15;
	public static boolean RadarGuiRemote	= false;
	
	//Aggiunte dello SPRINT2
	public static String hostAddr         	= "localhost";		
	public static String raspAddr         	= "localhost";		
	public static int serverPort          	= 8080;
	
	//Aggiunte dello SPRINT2a
	public static int ledPort             	= 8010;
	public static int sonarPort           	= 8015;
	
	//Aggiunta SPRINT3
	public static ProtocolType protocolType = ProtocolType.tcp;
	
	public static void setTheConfiguration() {
		setTheConfiguration("../RadarSystemConfig.json");
	}
	
	public static void setTheConfiguration( String resourceName ) {
		FileInputStream fis = null;
		
		try {
			ColorsOut.out("%%% setTheConfiguration from file:" + resourceName);
			
			if( fis == null ) {
				fis = new FileInputStream( new File(resourceName));
			}
			
			JSONTokener tokener = new JSONTokener( new InputStreamReader( fis ) );
			JSONObject object	= new JSONObject( tokener );
			
			tracing 							= object.getBoolean( "tracing" );
			testing 							= object.getBoolean( "testing" );
			RadarGuiRemote						= object.getBoolean( "RadarGuiRemote" );
			DLIMIT								= object.getInt("DLIMIT");
			
			//Aggiunte dello SPRINT2
			serverPort							= object.getInt("serverPort");
			hostAddr							= object.getString("hostAddr");
			raspAddr							= object.getString("raspAddr");
			
			//Aggiunte SPRINT2a
			ledPort								= object.getInt("ledPort");
			sonarPort							= object.getInt("sonarPort");
			
			switch( object.getString("protocolType") ) {
				case "tcp"	: protocolType = ProtocolType.tcp; break;
				case "udp"	: protocolType = ProtocolType.udp; break;
				case "coap"	: protocolType = ProtocolType.coap; break;
				case "mqtt"	: protocolType = ProtocolType.mqtt; break;
			}
			
		}catch( Exception e) {
			ColorsOut.outerr("setTheConfiguration | ERROR " + e.getMessage());
		}
	}
	
}
