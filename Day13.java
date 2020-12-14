/**
 * Advent Of Code 2020
 *  >>> www.adventofcode.com/2020
 * 
 * This file contains solutions to day 13.
 * 
 * @author Mislav.Gazdovic, mislav.gazdovic@gmail.com
 *
 */

package adventOfCode2020;

import java.util.*;

public class Day13 {

	private static class Bus {
		private int id;
		private int interval;
		private int offset;
		
		public Bus(int id, int offset) {
			this.id = id;
			this.interval = id;
			this.offset = offset;
		}
		
		public int getId() {
			return this.id;
		}
		
		public int getInterval() {
			return this.interval;
		}
		
		public int getOffset() {
			return this.offset;
		}
		
		public boolean departsAt(long time) {
			return time % interval == 0;
		}
		
	}
		
	private static class BusIntervalComparator implements Comparator<Bus> {
		@Override
		public int compare (Bus bus1, Bus bus2) {
			return Integer.compare(bus1.getInterval(), bus2.getInterval());
		}
	}
		
	public static long getFirstMatchingTimestamp(Set<Bus> busses) {
		Util.notEmptyOrThrow(busses);
		
		// Set -> PQ
		PriorityQueue<Bus> busPQ = new PriorityQueue<Bus>(busses.size(), new BusIntervalComparator());
		for (Bus bus : busses) {
			busPQ.add(bus);
		}
		
		// Bottom-Up by Interval
		long timestamp = 0;
		long step = 1;
		Bus current = busPQ.peek();
		while (!busPQ.isEmpty()) {
			timestamp += step;
			long targetDeparture = timestamp + current.getOffset();
			if (current.departsAt(targetDeparture)) {
				step *= current.getInterval(); 	// assumes all bus intervals are prime numbers!
				busPQ.poll();					
				current = busPQ.peek();
			}
		}
		
		return timestamp;
	}
	
	public static int getProductIdMin(Set<Bus> busses, int ETA) {
		Util.notEmptyOrThrow(busses);
		if (ETA <= 0) throw new IllegalArgumentException("ETA cannot be negative...");
		
		Map<Integer, Bus> waitMap = new HashMap<Integer, Bus>();
		
		int busInterval = 0;
		int expectedWait = 0;
		int expectedWaitMin = Integer.MAX_VALUE;
		for (Bus bus : busses) {
			busInterval = bus.getInterval();
			expectedWait = (((busInterval - ETA) % busInterval) + busInterval) % busInterval;
			expectedWaitMin = expectedWait < expectedWaitMin ? expectedWait : expectedWaitMin;
			waitMap.put(expectedWait, bus);
		}
			
		int minWaitBusId = waitMap.get(expectedWaitMin).getId();
		return expectedWaitMin * minWaitBusId;
	}
	
	private static Set<Bus> getInputBusses() {
		Set<Bus> busses = new HashSet<Bus>();
		
		for (String row : Util.getInputAsStringCollection("Inputs\\Day13.txt")) {
			if (!row.contains(",")) continue;
			int busID = 0;
			int beginIndex = 0;
			int endIndex = 0;
			String current = "";
			int offset = 0;
			while (endIndex < row.length()) {
				endIndex = row.indexOf(",", beginIndex);
				if (endIndex == -1) endIndex = row.length();
				current = row.substring(beginIndex, endIndex);
				if (!current.contentEquals("x")) {
					busID = Integer.valueOf(current);
					busses.add(new Bus(busID, offset));
				}
				beginIndex = endIndex + 1;
				offset++;
			}	
		}
				
		return busses;
	}
	
	private static int getInputETA() {
		for (String row : Util.getInputAsStringCollection("Inputs\\Day13.txt")) {
			if (!row.contains(",")) return Integer.valueOf(row);
		}
		
		return -1;
	}
	
	public static void main(String[] args) {
		Set<Bus> BUSSES = getInputBusses();
		final int ETA = getInputETA();
	
		System.out.printf("The product of bus ID and wait minutes is %d.\n", getProductIdMin(BUSSES, ETA));				// PART 1
		System.out.printf("The earliest matching departure timestamp is at %d.", getFirstMatchingTimestamp(BUSSES));	// PART 2
	}
	
}