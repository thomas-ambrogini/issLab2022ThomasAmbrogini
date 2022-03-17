package it.unibo.radarSystem22.domain;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.unibo.radarSystem22.domain.interfaces.ISonar;
import it.unibo.radarSystem22.domain.utils.BasicUtils;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;

public class TestSonar {

	@Before
	public void up() {
		System.out.println("Up");
	}
	
	@After
	public void down() {
		System.out.println("Down");
	}
	
	@Test
	public void testSonar() {
		System.out.println("testSonar");
		DomainSystemConfig.simulation = true;
		DomainSystemConfig.testing    = false;
		DomainSystemConfig.sonarDelay = 100;
		
		int delta = 1;
		
		ISonar sonar = DeviceFactory.createSonar();
		assertTrue(!sonar.isActive());
		
		new SonarConsumerForTesting( sonar, delta).start();
		sonar.activate();
		assertTrue(sonar.isActive());
		
		while( sonar.isActive() ) {
			BasicUtils.delay(1000);
		}
		
	}
}
