package de.tub.mubix.main;

import processing.core.PApplet;
import processing.core.PVector;

public class Cube {
	PApplet parent;

	final int LENGTH = 60; // size of cube
	final int DEG = 3; // rotation speed (degrees per frame)

	public int clock = 0;
	public int faceTurn = 0;
	
	public boolean mixCube = false;
	
	int numOfSiteRotation = 0;
	int rand1 = 0;
	int rand2 = 0;
	int rand3 = 0;

	PVector[][][] squareMatrix;
	public int[][] colorTable = { { 255, 0, 0 }, { 255, 127, 0 },
			{ 0, 0, 255 }, { 0, 255, 0 }, { 255, 255, 0 }, { 255, 255, 255 } };

	/*
	 * for scrambling from identity state rather than reading from camera. If
	 * change into camera reading mode,this colorTable will be a list contains
	 * 54 triples of color for each square, and initialized by a methodwhich
	 * controls camera
	 */

	public Cube(PApplet parent) {// The points for each square on the face
		this.parent = parent;

		squareMatrix = new PVector[6][9][4];
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				squareMatrix[0][i * 3 + j][0] = new PVector(
						(i - 1.5f) * LENGTH, (j - 1.5f) * LENGTH, 1.5f * LENGTH);
				squareMatrix[0][i * 3 + j][1] = new PVector(
						(i - 1.5f) * LENGTH, (j - 1.5f + 1) * LENGTH,
						1.5f * LENGTH);
				squareMatrix[0][i * 3 + j][2] = new PVector((i - 1.5f + 1)
						* LENGTH, (j - 1.5f + 1) * LENGTH, 1.5f * LENGTH);
				squareMatrix[0][i * 3 + j][3] = new PVector((i - 1.5f + 1)
						* LENGTH, (j - 1.5f) * LENGTH, 1.5f * LENGTH);
			}
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				squareMatrix[1][i * 3 + j][0] = new PVector(
						(i - 1.5f) * LENGTH, (j - 1.5f) * LENGTH, -1.5f
								* LENGTH);
				squareMatrix[1][i * 3 + j][1] = new PVector(
						(i - 1.5f) * LENGTH, (j - 1.5f + 1) * LENGTH, -1.5f
								* LENGTH);
				squareMatrix[1][i * 3 + j][2] = new PVector((i - 1.5f + 1)
						* LENGTH, (j - 1.5f + 1) * LENGTH, -1.5f * LENGTH);
				squareMatrix[1][i * 3 + j][3] = new PVector((i - 1.5f + 1)
						* LENGTH, (j - 1.5f) * LENGTH, -1.5f * LENGTH);
			}
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				squareMatrix[2][i * 3 + j][0] = new PVector(
						(i - 1.5f) * LENGTH, 1.5f * LENGTH, (j - 1.5f) * LENGTH);
				squareMatrix[2][i * 3 + j][1] = new PVector(
						(i - 1.5f) * LENGTH, 1.5f * LENGTH, (j - 1.5f + 1)
								* LENGTH);
				squareMatrix[2][i * 3 + j][2] = new PVector((i - 1.5f + 1)
						* LENGTH, 1.5f * LENGTH, (j - 1.5f + 1) * LENGTH);
				squareMatrix[2][i * 3 + j][3] = new PVector((i - 1.5f + 1)
						* LENGTH, 1.5f * LENGTH, (j - 1.5f) * LENGTH);
			}
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				squareMatrix[3][i * 3 + j][0] = new PVector(
						(i - 1.5f) * LENGTH, -1.5f * LENGTH, (j - 1.5f)
								* LENGTH);
				squareMatrix[3][i * 3 + j][1] = new PVector(
						(i - 1.5f) * LENGTH, -1.5f * LENGTH, (j - 1.5f + 1)
								* LENGTH);
				squareMatrix[3][i * 3 + j][2] = new PVector((i - 1.5f + 1)
						* LENGTH, -1.5f * LENGTH, (j - 1.5f + 1) * LENGTH);
				squareMatrix[3][i * 3 + j][3] = new PVector((i - 1.5f + 1)
						* LENGTH, -1.5f * LENGTH, (j - 1.5f) * LENGTH);
			}
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				squareMatrix[4][i * 3 + j][0] = new PVector(1.5f * LENGTH,
						(i - 1.5f) * LENGTH, (j - 1.5f) * LENGTH);
				squareMatrix[4][i * 3 + j][1] = new PVector(1.5f * LENGTH,
						(i - 1.5f) * LENGTH, (j - 1.5f + 1) * LENGTH);
				squareMatrix[4][i * 3 + j][2] = new PVector(1.5f * LENGTH,
						(i - 1.5f + 1) * LENGTH, (j - 1.5f + 1) * LENGTH);
				squareMatrix[4][i * 3 + j][3] = new PVector(1.5f * LENGTH,
						(i - 1.5f + 1) * LENGTH, (j - 1.5f) * LENGTH);
			}
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				squareMatrix[5][i * 3 + j][0] = new PVector(-1.5f * LENGTH,
						(i - 1.5f) * LENGTH, (j - 1.5f) * LENGTH);
				squareMatrix[5][i * 3 + j][1] = new PVector(-1.5f * LENGTH,
						(i - 1.5f) * LENGTH, (j - 1.5f + 1) * LENGTH);
				squareMatrix[5][i * 3 + j][2] = new PVector(-1.5f * LENGTH,
						(i - 1.5f + 1) * LENGTH, (j - 1.5f + 1) * LENGTH);
				squareMatrix[5][i * 3 + j][3] = new PVector(-1.5f * LENGTH,
						(i - 1.5f + 1) * LENGTH, (j - 1.5f) * LENGTH);
			}
	}

	@SuppressWarnings("static-access")
	public void display() {
		for (int i = 0; i < 6; i++) {
			parent.fill(colorTable[i][0], colorTable[i][1], colorTable[i][2]);
			// If the surface colors are read from camera, the fill() should be
			// insert in the Shape module opacity property doesn't seem to work
			// on well with fill(), it'll be confusing when alpha applied.
			for (int j = 0; j < 9; j++) {
				parent.beginShape(parent.QUAD);
				parent.vertex(squareMatrix[i][j][0].x, squareMatrix[i][j][0].y,
						squareMatrix[i][j][0].z);
				parent.vertex(squareMatrix[i][j][1].x, squareMatrix[i][j][1].y,
						squareMatrix[i][j][1].z);
				parent.vertex(squareMatrix[i][j][2].x, squareMatrix[i][j][2].y,
						squareMatrix[i][j][2].z);
				parent.vertex(squareMatrix[i][j][3].x, squareMatrix[i][j][3].y,
						squareMatrix[i][j][3].z);
				parent.endShape();
			}
		}
	}

	@SuppressWarnings("static-access")
	public void rTransform(PVector p, int axis, int dir) {// Simple rotation
		// transform, dir can be
		// positive or negative
		float temp;
		if (axis == 1) {
			temp = p.y;
			p.y = p.y * parent.cos(parent.radians(dir * DEG)) - p.z
					* parent.sin(parent.radians(dir * DEG));
			p.z = temp * parent.sin(parent.radians(dir * DEG)) + p.z
					* parent.cos(parent.radians(dir * DEG));
		} else if (axis == 2) {
			temp = p.x;
			p.x = p.x * parent.cos(parent.radians(dir * DEG)) - p.z
					* parent.sin(parent.radians(dir * DEG));
			p.z = temp * parent.sin(parent.radians(dir * DEG)) + p.z
					* parent.cos(parent.radians(dir * DEG));
		} else {
			temp = p.x;
			p.x = p.x * parent.cos(parent.radians(dir * DEG)) - p.y
					* parent.sin(parent.radians(dir * DEG));
			p.y = temp * parent.sin(parent.radians(dir * DEG)) + p.y
					* parent.cos(parent.radians(dir * DEG));
		}
	}

	public void rightTwist(int rdir) {
		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 9; j++)
				if ((squareMatrix[i][j][0].x >= 1.5 * LENGTH - 1)
						|| (squareMatrix[i][j][1].x >= 1.5 * LENGTH - 1)
						|| (squareMatrix[i][j][2].x >= 1.5 * LENGTH - 1)
						|| (squareMatrix[i][j][3].x >= 1.5 * LENGTH - 1)) {
					rTransform(squareMatrix[i][j][0], 1, rdir);
					rTransform(squareMatrix[i][j][1], 1, rdir);
					rTransform(squareMatrix[i][j][2], 1, rdir);
					rTransform(squareMatrix[i][j][3], 1, rdir);
				}
	}

	public void leftTwist(int rdir) {
		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 9; j++)
				if ((squareMatrix[i][j][0].x <= -(1.5 * LENGTH - 1))
						|| (squareMatrix[i][j][1].x <= -(1.5 * LENGTH - 1))
						|| (squareMatrix[i][j][2].x <= -(1.5 * LENGTH - 1))
						|| (squareMatrix[i][j][3].x <= -(1.5 * LENGTH - 1))) {
					rTransform(squareMatrix[i][j][0], 1, rdir);
					rTransform(squareMatrix[i][j][1], 1, rdir);
					rTransform(squareMatrix[i][j][2], 1, rdir);
					rTransform(squareMatrix[i][j][3], 1, rdir);
				}
	}

	public void bottomTwist(int rdir) {
		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 9; j++)
				if ((squareMatrix[i][j][0].y >= 1.5 * LENGTH - 1)
						|| (squareMatrix[i][j][1].y >= 1.5 * LENGTH - 1)
						|| (squareMatrix[i][j][2].y >= 1.5 * LENGTH - 1)
						|| (squareMatrix[i][j][3].y >= 1.5 * LENGTH - 1)) {
					rTransform(squareMatrix[i][j][0], 2, rdir);
					rTransform(squareMatrix[i][j][1], 2, rdir);
					rTransform(squareMatrix[i][j][2], 2, rdir);
					rTransform(squareMatrix[i][j][3], 2, rdir);
				}
	}

	public void upTwist(int rdir) {
		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 9; j++)
				if ((squareMatrix[i][j][0].y <= -(1.5 * LENGTH - 1))
						|| (squareMatrix[i][j][1].y <= -(1.5 * LENGTH - 1))
						|| (squareMatrix[i][j][2].y <= -(1.5 * LENGTH - 1))
						|| (squareMatrix[i][j][3].y <= -(1.5 * LENGTH))) {
					rTransform(squareMatrix[i][j][0], 2, rdir);
					rTransform(squareMatrix[i][j][1], 2, rdir);
					rTransform(squareMatrix[i][j][2], 2, rdir);
					rTransform(squareMatrix[i][j][3], 2, rdir);
				}
	}

	public void frontTwist(int rdir) {
		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 9; j++)
				if ((squareMatrix[i][j][0].z >= 1.5 * LENGTH - 1)
						|| (squareMatrix[i][j][1].z >= 1.5 * LENGTH - 1)
						|| (squareMatrix[i][j][2].z >= 1.5 * LENGTH - 1)
						|| (squareMatrix[i][j][3].z >= 1.5 * LENGTH - 1)) {
					rTransform(squareMatrix[i][j][0], 3, rdir);
					rTransform(squareMatrix[i][j][1], 3, rdir);
					rTransform(squareMatrix[i][j][2], 3, rdir);
					rTransform(squareMatrix[i][j][3], 3, rdir);
				}
	}

	public void backTwist(int rdir) {
		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 9; j++)
				if ((squareMatrix[i][j][0].z <= -(1.5 * LENGTH - 1))
						|| (squareMatrix[i][j][1].z <= -(1.5 * LENGTH - 1))
						|| (squareMatrix[i][j][2].z <= -(1.5 * LENGTH - 1))
						|| (squareMatrix[i][j][3].z <= -(1.5 * LENGTH - 1))) {
					rTransform(squareMatrix[i][j][0], 3, rdir);
					rTransform(squareMatrix[i][j][1], 3, rdir);
					rTransform(squareMatrix[i][j][2], 3, rdir);
					rTransform(squareMatrix[i][j][3], 3, rdir);
				}
	}

	public void rotateCube(int axis, int rdir) {
		// switch (axis) {
		// case 1:
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 9; j++) {
				// if
				// ((squareMatrix[i][j][0].x>=1.5*LENGTH-1)||(squareMatrix[i][j][1].x>=1.5*LENGTH-1)||(squareMatrix[i][j][2].x>=1.5*LENGTH-1)||(squareMatrix[i][j][3].x>=1.5*LENGTH-1))
				// {
				rTransform(squareMatrix[i][j][0], axis, rdir);
				rTransform(squareMatrix[i][j][1], axis, rdir);
				rTransform(squareMatrix[i][j][2], axis, rdir);
				rTransform(squareMatrix[i][j][3], axis, rdir);
				// }
			}
		}
		// break;
		// }
	}
	
	public static int myRandom(double low, double high) {  
	    return (int) (Math.random() * (high - low) + low);  
	}
	
	public void mixingCube(){	
		if(rand1 == 0){
			rand1 = myRandom(1, 4);
			rand2 = myRandom(1, 4);
			rand3 = myRandom(1, 4);
		}
		
		if(numOfSiteRotation == 0 && clock < (90 / DEG)){
			X_AxisRotation(rand1, rand2, rand3);
		}
		
		if(numOfSiteRotation == 1 && clock < (90 / DEG)){
			Y_AxisRotation(rand1, rand2, rand3);
		}
		
		if(numOfSiteRotation == 2 && clock < (90 / DEG)){
			Z_AxisRotation(rand1, rand2, rand3);
		}
				
		if(clock == (90 / DEG)){
			numOfSiteRotation++;
			rand1 = 0;
			rand2 = 0;
			rand3 = 0;
			if(numOfSiteRotation == 3){
				mixCube = false;
				numOfSiteRotation = 0;
			}
		}
	}
	
	public void X_AxisRotation(int rand1, int rand2, int rand3){
		if (clock < (90 / DEG)) {
			if (mixCube == true){
				verticalLeftTwist(rand1);
					
				leftTwist(-rand2);
				rightTwist(-rand2);
				rotateCube(1, rand2);
					
				rightTwist(rand3);
			}
		}
	}
	
	public void Y_AxisRotation(int rand1, int rand2, int rand3){
		if (clock < (90 / DEG)) {
			if (mixCube == true){
				upTwist(-rand1);
					
				upTwist(rand2);
				bottomTwist(rand2);
				rotateCube(2, -rand2);
				
				bottomTwist(-rand3);
				
				clock++;
			}
		}
	}
	
	public void Z_AxisRotation(int rand1, int rand2, int rand3){
		if (clock < (90 / DEG)) {
			if (mixCube == true){
				backTwist(-rand1);
					
				backTwist(-rand2);
				frontTwist(-rand2);
				
				frontTwist(rand3);
				
				clock++;
			}
		}
	}
	
	public void verticalLeftTwist(int numOfTwists){
		leftTwist(numOfTwists);
		clock++;
	}

	public void singleTwist() {
		if (clock < (90 / DEG)) {
			switch (faceTurn) {
			case 11:
				verticalLeftTwist(1);
				break;
			case 12:
				leftTwist(-1);
				clock++;
				break;
			case 21:
				leftTwist(-1);
				rightTwist(-1);
				rotateCube(1, 1);
				clock++;
				break;
			case 22:
				leftTwist(1);
				rightTwist(1);
				rotateCube(1, -1);
				clock++;
				break;
			case 31:
				rightTwist(1);
				clock++;
				break;
			case 32:
				rightTwist(-1);
				clock++;
				break;
			case 41:
				upTwist(-1);
				clock++;
				break;
			case 42:
				upTwist(1);
				clock++;
				break;
			case 51:
				upTwist(1);
				bottomTwist(1);
				rotateCube(2, -1);
				clock++;
				break;
			case 52:
				upTwist(-1);
				bottomTwist(-1);
				rotateCube(2, 1);
				clock++;
				break;
			case 61:
				bottomTwist(-1);
				clock++;
				break;
			case 62:
				bottomTwist(1);
				clock++;
				break;
			case 71:
				backTwist(1);
				clock++;
				break;
			case 72:
				backTwist(-1);
				clock++;
				break;
			case 81:
				backTwist(-1);
				frontTwist(-1);
				clock++;
				break;
			case 82:
				backTwist(1);
				frontTwist(1);
				clock++;
				break;
			case 91:
				frontTwist(1);
				clock++;
				break;
			case 92:
				frontTwist(-1);
				clock++;
				break;
			case 101:
				rotateCube(1, 1);
				clock++;
				break;
			case 102:
				rotateCube(1, -1);
				clock++;
				break;
			case 103:
				rotateCube(2, 1);
				clock++;
				break;
			case 104:
				rotateCube(2, -1);
				clock++;
				break;
			}
		} else {
			clock = 0;
			faceTurn = 0;
		}
	}
}