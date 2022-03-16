package it.unibo.radarSystem22.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.unibo.radarSystem22.domain.interfaces.ISonar;
import it.unibo.radarSystem22.domain.mock.SonarMock;
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
		
		ISonar sonar = DeviceFactory.createSonar();
		assertTrue(!sonar.isActive());
		
		sonar.activate();
		assertTrue(sonar.isActive());
		
		assertEquals(sonar.getDistance().getVal(), 90);
		
		BasicUtils.delay(DomainSystemConfig.sonarDelay * 3);
		assertEquals(sonar.getDistance().getVal(), 87);
		
		sonar.deactivate();
		assertTrue(!sonar.isActive());
		
	}
}
