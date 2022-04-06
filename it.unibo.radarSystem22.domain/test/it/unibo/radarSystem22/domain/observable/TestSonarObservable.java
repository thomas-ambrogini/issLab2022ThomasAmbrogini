package it.unibo.radarSystem22.domain.observable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.unibo.radarSystem22.domain.DeviceFactory;
import it.unibo.radarSystem22.domain.interfaces.IDistanceMeasured;
import it.unibo.radarSystem22.domain.interfaces.ISonarObservable;
import it.unibo.radarSystem22.domain.SonarObserver;
import it.unibo.radarSystem22.domain.utils.BasicUtils;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;

public class TestSonarObservable {
	private SonarObserver[] observers;
	private static int DLIMIT1 = 13;
	private static int DLIMIT2 = 24;
	
	@Before
	public void up() {
		System.out.println("Up");
	}
	
	@After
	public void down() {
		System.out.println("Down");
	}
	
	@Test
	public void testSonarObservable() {
		System.out.println("testSonarObservable");
		DomainSystemConfig.simulation = true;
		DomainSystemConfig.testing    = true;
		DomainSystemConfig.observable = true;
		DomainSystemConfig.tracing	  = true;
		DomainSystemConfig.sonarDelay = 100;
		
		DomainSystemConfig.testingDistance = DLIMIT1;
		
		createObservers( 5 );
		
		ISonarObservable sonar = DeviceFactory.createSonarObservable();
		assertTrue(!sonar.isActive());
		
		addObservers( sonar.getDistanceMeasured() );
		
		sonar.activate();
		assertTrue(sonar.isActive());
		
		BasicUtils.delay(150);
		
		checkObservers(DLIMIT1);
		
		
		DomainSystemConfig.testingDistance = DLIMIT2;
		
		BasicUtils.delay(200);
		
		checkObservers(DLIMIT2);
		
		sonar.deactivate();
		assertTrue(!sonar.isActive());
		
		
		
	}
	
	private void createObservers( int num ) {
		observers = new SonarObserver[num];
		
		for( int i = 0; i < num; i++ ) {
			observers[i] = new SonarObserver();
		}
	}
	
	private void addObservers( IDistanceMeasured distanceMeasured) {
		
		for( int i = 0; i < observers.length; i++ ) {
			distanceMeasured.addObserver(observers[i]);
		}
	}

	private void checkObservers( int value ) {
	
		for( int i = 0; i < observers.length; i++ ) {
			assertEquals( value, observers[i].getVal() );
		}
	}
	
}
