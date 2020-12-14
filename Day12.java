/**
 * Advent Of Code 2020
 *  >>> www.adventofcode.com/2020
 * 
 * This file contains solutions to day 12.
 * 
 * @author Mislav.Gazdovic, mislav.gazdovic@gmail.com
 *
 */

package adventOfCode2020;

import java.util.*;

public class Day12 {
	
	static final short DIRECTION_NORTH = 90;
	static final short DIRECTION_EAST = 0;
	static final short DIRECTION_SOUTH = 270;
	static final short DIRECTION_WEST = 180;
			
	public static long getFinalManhattanDistSum(Position ship, Position waypoint, List<Instruction> instructions) {
		Position finalShipPosition = getFinalShipPosition(ship, waypoint, instructions);
		
		long manhattanX = Math.abs(finalShipPosition.getX());
		long manhattanY = Math.abs(finalShipPosition.getY());
		
		return manhattanX + manhattanY;
	}
	
	private static void move(Position ship, Position waypoint, List<Instruction> instructions) {
		Positioning positioning = new Positioning(ship, waypoint);
		for (Instruction instruction : instructions) {
			positioning.execute(instruction);
		}
	}
	
	private static Position getFinalShipPosition(Position ship, Position waypoint, List<Instruction> instructions) {
		Position movingShip = new Position(ship.getX(), ship.getY(), ship.getDirection());
		Position movingWaypoint = waypoint != null ? new Position(waypoint.getX(), waypoint.getY(), waypoint.getDirection()) : null;
		
		move(movingShip, movingWaypoint, instructions);
		
		return movingShip;
	}
	
	private static class Positioning {
		private Position ship;
		private Position waypoint;
			
		public Positioning(Position ship, Position waypoint) {
			if (ship == null) throw new IllegalArgumentException("Reference to ship object must not be null.");
			this.ship = ship;
			this.waypoint = waypoint;
		}
			
		public void execute(Instruction instruction) {
			
			char operation = instruction.getOperation();
			int value = instruction.getValue();
			
			long xShip = this.ship.getX();
			long yShip = this.ship.getY();
			int directionShip = this.ship.getDirection();
						
			if (this.waypoint == null) {
				switch(operation) {
					case 'N': 
						yShip += value;
						break;
					case 'S': 
						yShip -= value;
						break;
					case 'E': 
						xShip += value;
						break;
					case 'W': 
						xShip -= value;
						break;
					case 'L': 
						directionShip += value;
						break;
					case 'R': 
						directionShip -= value;
						break;
					case 'F': 
						if (directionShip == DIRECTION_NORTH) {
							yShip += value;
						} else if (directionShip == DIRECTION_EAST) {
							xShip += value;
						} else if (directionShip == DIRECTION_SOUTH) {
							yShip -= value;
						} else if (directionShip == DIRECTION_WEST) {
							xShip -= value;
						} else {
							// Invalid direction, ignored
						}
						break;
					default:
						// Invalid operation, ignored
				}
			}
			else { // waypoint != null
				long xWaypoint = this.waypoint.getX();
				long yWaypoint = this.waypoint.getY();
				int directionWaypoint = this.waypoint.getDirection();
				
				switch(operation) {
					case 'N': 
						yWaypoint += value;
						break;
					case 'S': 
						yWaypoint -= value;
						break;
					case 'E': 
						xWaypoint += value;
						break;
					case 'W': 
						xWaypoint -= value;
						break;
					case 'L': 
					case 'R':
						this.waypoint.rotateBy(operation == 'R' ? -value : value);
						xWaypoint = this.waypoint.getX();
						yWaypoint = this.waypoint.getY();
						directionWaypoint = this.waypoint.getDirection();
						directionShip = directionWaypoint;
						break;
					case 'F': 					
						xShip += xWaypoint * value;
						yShip += yWaypoint * value;
						break;
					default:
						// Invalid operation, ignored
				}
				this.waypoint.setX(xWaypoint);
				this.waypoint.setY(yWaypoint);
				this.waypoint.setDirection(directionWaypoint);
			}
			
			this.ship.setX(xShip);
			this.ship.setY(yShip);
			this.ship.setDirection(directionShip);
		}
		
	}
	
	private static class Position {
		private long x;
		private long y;
		private int direction;
		
		public Position(long x, long y, int direction) {
			this.x = x;
			this.y = y;
			this.setDirection(direction);
		}
		
		private int getAngleNorm(int angle) {
			int norm = angle;
			while (norm >= 360 || norm < 0) {
				norm += norm > 0 ? -360 : 360;
			}
			return norm;
		}
		
		public long getX() {
			return this.x;
		}
		
		public void setX(long value) {
			this.x = value;
		}
		
		public long getY() {
			return this.y;
		}
		
		public void setY(long value) {
			this.y = value;
		}
		
		public int getDirection() {
			return this.direction;
		}
		
		public void setDirection(int value) {
			this.direction = getAngleNorm(value);
		}
		
		public void rotateBy(int degrees) {
			int degreeNorm = getAngleNorm(degrees);
			
			if (degreeNorm == 0) {
				// no rotation
				return;
			}
			else {
				if (degreeNorm == 90) {
					// (x, y) e.g. (1,5) +90° --> (-5,1)
					long tmp = y;
					y = x;
					x = -tmp;
				} else if (degreeNorm == 180) {
					// (x, y) e.g. (1,5) +180° --> (-1,-5)
					x = -x;
					y = -y;
				} else if (degreeNorm == 270) {
					// (x, y) e.g. (1,5) +270° --> (5,-1)
					long tmp = y;
					y = -x;
					x = tmp;
				}
				else {
					throw new IllegalArgumentException("Unallowed rotation angle degree value...");
				}	
				this.setDirection(direction + degrees);
			}
		}
			
	}
	
	private static class Instruction {
		private char operation;
		private int value;
		
		public Instruction(String instruction) {
			this.operation = instruction.charAt(0);
			this.value = Integer.parseInt(instruction.substring(1));
		}
		
		public char getOperation() {
			return this.operation;
		}
		
		public int getValue() {
			return this.value;
		}
				
	}
	
	private static List<Instruction> getInput() {
		List<Instruction> instructionList = new ArrayList<Instruction>();
		for (String instruction : Util.getInputAsStringCollection("Inputs\\Day12.txt")) {
			instructionList.add(new Instruction(instruction));
		}
		
		return instructionList;
	}
	
	// Driver
	public static void main(String[] args) {
		List<Instruction> instructionList = getInput();
		
		final Position START_POSITION_SHIP = new Position(0, 0, DIRECTION_EAST);
		final Position START_POSITION_WAYPOINT = new Position(10, 1, DIRECTION_EAST);
		
		long dist = getFinalManhattanDistSum(START_POSITION_SHIP, null, instructionList); // without waypoint
		long distWithWaypoint = getFinalManhattanDistSum(START_POSITION_SHIP, START_POSITION_WAYPOINT, instructionList);
			
		System.out.printf("The sum of end manhattan distance X&Y is %d.\n", dist);							// PART 1
		System.out.printf("The sum of end manhattan distance X&Y with waypoint is %d.", distWithWaypoint);	// PART 2 
	}

}