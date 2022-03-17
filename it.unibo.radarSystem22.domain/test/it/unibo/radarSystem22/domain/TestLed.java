package it.unibo.radarSystem22.domain;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.unibo.radarSystem22.domain.interfaces.ILed;
import it.unibo.radarSystem22.domain.mock.LedMock;
import it.unibo.radarSystem22.domain.utils.BasicUtils;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;

public class TestLed {

	@Before
	public void up() {
		System.out.println("Up");
	}
	
	@After
	public void down() {
		System.out.println("Down");
	}
	
	@Test
	public void testLedMockOn() {
		DomainSystemConfig.ledGui = true;
		System.out.println("testLedMockOn");
		
		ILed led = DeviceFactory.createLed();
		assertTrue(!led.getState());
		
		led.turnOn();
		assertTrue(led.getState());
		
		BasicUtils.delay(1000);	
		
		led.turnOff();
		assertTrue(!led.getState());
		
		BasicUtils.delay(1000);	
		
		
	}
	
	@Test
	public void testLedMockOff() {
		System.out.println("testLedMockOff");
		
		ILed led = new LedMock();
		led.turnOn();
		
		led.turnOff();
		assertTrue(!led.getState());
		
	}
}
