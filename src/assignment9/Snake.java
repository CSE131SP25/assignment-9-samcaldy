package assignment9;

import java.util.LinkedList;

public class Snake {

	private static final double SEGMENT_SIZE = 0.02;
	private static final double MOVEMENT_SIZE = SEGMENT_SIZE * 1.5;
	private LinkedList<BodySegment> segments;
	private double deltaX;
	private double deltaY;
	
	public Snake() {
		segments = new LinkedList<>();
		// Start snake in the center
		double startX = 0.5;
		double startY = 0.5;
		segments.add(new BodySegment(startX, startY, SEGMENT_SIZE));
		deltaX = 0;
		deltaY = 0;
	}
	
	public void changeDirection(int direction) {
		if(direction == 1) { //up
			deltaY = MOVEMENT_SIZE;
			deltaX = 0;
		} else if (direction == 2) { //down
			deltaY = -MOVEMENT_SIZE;
			deltaX = 0;
		} else if (direction == 3) { //left
			deltaY = 0;
			deltaX = -MOVEMENT_SIZE;
		} else if (direction == 4) { //right
			deltaY = 0;
			deltaX = MOVEMENT_SIZE;
		}
	}
	
	/**
	 * Moves the snake by updating the position of each of the segments
	 * based on the current direction of travel
	 */
	public void move() {
		if (deltaX == 0 && deltaY == 0) return; // No movement yet

		// Get head position
		BodySegment head = segments.getFirst();
		double newX = head.getX() + deltaX;
		double newY = head.getY() + deltaY;

		// Move body from tail to head
		for (int i = segments.size() - 1; i > 0; i--) {
			BodySegment prev = segments.get(i - 1);
			segments.get(i).setX(prev.getX());
			segments.get(i).setY(prev.getY());
		}

		// Update head position
		head.setX(newX);
		head.setY(newY);
	}
	
	/**
	 * Draws the snake by drawing each segment
	 */
	public void draw() {
		for (BodySegment segment : segments) {
			segment.draw();
		}
	}
	
	/**
	 * The snake attempts to eat the given food, growing if it does so successfully
	 * @param f the food to be eaten
	 * @return true if the snake successfully ate the food
	 */
	public boolean eatFood(Food f) {
		BodySegment head = segments.getFirst();
		double dist = Math.hypot(head.getX() - f.getX(), head.getY() - f.getY());
		if (dist < SEGMENT_SIZE + Food.FOOD_SIZE) {
			// Add new segment at the tail (copy tail position initially)
			BodySegment tail = segments.getLast();
			segments.add(new BodySegment(tail.getX(), tail.getY(), SEGMENT_SIZE));
			return true;
		}
		return false;
	}
	
	/**
	 * Returns true if the head of the snake is in bounds
	 * @return whether or not the head is in the bounds of the window
	 */
	public boolean isInbounds() {
		BodySegment head = segments.getFirst();
		double x = head.getX();
		double y = head.getY();
		// Ensure entire segment is inside bounds
		return (x >= SEGMENT_SIZE && x <= 1 - SEGMENT_SIZE &&
				y >= SEGMENT_SIZE && y <= 1 - SEGMENT_SIZE);
	}
}
