package de.tub.mubix.main;

import processing.core.PApplet;
import processing.core.PVector;

public class Trackpad {
	private PApplet parent;
	int xRes;
	int yRes;
	int width;
	int height;

	boolean active;
	PVector center;
	PVector offset;

	int space;

	int focusX;
	int focusY;
	int selX;
	int selY;
	int dir;

	Trackpad(PApplet parent, PVector center, int xRes, int yRes, int width,
			int height, int space) {
		this.parent = parent;
		this.xRes = xRes;
		this.yRes = yRes;
		this.width = width;
		this.height = height;
		active = false;

		this.center = center.get();
		offset = new PVector();
		offset.set(-(float) (xRes * width + (xRes - 1) * space) * .5f,
				-(float) (yRes * height + (yRes - 1) * space) * .5f, 0.0f);
		offset.add(this.center);

		this.space = space;
	}

	public int getFocusX() {
		return focusX;
	}

	public void setFocusX(int focusX) {
		this.focusX = focusX;
	}

	public int getFocusY() {
		return focusY;
	}

	public void setFocusY(int focusY) {
		this.focusY = focusY;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void enable() {
		active = true;

		focusX = -1;
		focusY = -1;
		selX = -1;
		selY = -1;
	}

	void update(int indexX, int indexY) {
		focusX = indexX;
		focusY = (yRes - 1) - indexY;
	}

	void push(int indexX, int indexY, int dir) {
		selX = indexX;
		selY = (yRes - 1) - indexY;
		this.dir = dir;
	}

	public void disable() {
		active = false;
	}

	public void draw() {
		parent.pushStyle();
		parent.pushMatrix();

		parent.translate(offset.x, offset.y);

		for (int y = 0; y < yRes; y++) {
			for (int x = 0; x < xRes; x++) {
				if (active && (selX == x) && (selY == y)) { // selected object
					parent.fill(100, 100, 220, 190);
					parent.strokeWeight(3);
					parent.stroke(100, 200, 100, 220);
				} else if (active && (focusX == x) && (focusY == y)) { // focus
																		// object
					parent.fill(100, 255, 100, 220);
					parent.strokeWeight(3);
					parent.stroke(100, 200, 100, 220);
				} else if (active) { // normal
					parent.strokeWeight(3);
					parent.stroke(100, 200, 100, 190);
					parent.noFill();
				} else {
					parent.strokeWeight(2);
					parent.stroke(200, 100, 100, 60);
					parent.noFill();
				}
				parent.rect(x * (width + space), y * (width + space), width,
						height);
			}
		}
		parent.popMatrix();
		parent.popStyle();
	}
}