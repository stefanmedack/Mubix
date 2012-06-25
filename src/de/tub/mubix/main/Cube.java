package de.tub.mubix.main;

import processing.core.PApplet;
import processing.core.PVector;

public class Cube {
	PApplet parent;

	final int LENGTH = 60; // size of cube
	final int DEG = 3; // rotation speed (degrees per frame)
	
	private final int frontTwist = 1;
	private final int backTwist = -1;

	public int clock = 0;
	public int faceTurn = 0;
	
	public boolean mixCube = false;
	public boolean reverseRotation = false;
	
	int numOfSiteRotation = 0;
	int rand1 = 0;
	int rand2 = 0;
	int rand3 = 0;
	int rand4 = 0;

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
			rand4 = myRandom(0, 2);
		}
		
		if(numOfSiteRotation == 0 && clock < (90 / DEG) && mixCube == true){
			X_AxisRotationLeftTwist();
			clock++;
		}
		
		if(numOfSiteRotation == 1 && clock < (90 / DEG) && mixCube == true){
			X_AxisRotationTopTwist();
			clock++;
		}
		
		if(numOfSiteRotation == 2 && clock < (90 / DEG) && mixCube == true){
			X_AxisRotationRightTwist();
			clock++;
		}
		
		if(numOfSiteRotation == 3 && clock < (90 / DEG) && mixCube == true){
			Y_AxisRotationLeftTwist();
			clock++;
		}
		
		if(numOfSiteRotation == 4 && clock < (90 / DEG) && mixCube == true){
			Y_AxisRotationTopTwist();
			clock++;
		}
		
		if(numOfSiteRotation == 5 && clock < (90 / DEG) && mixCube == true){
			Y_AxisRotationRightTwist();
			clock++;
		}
		
		if(numOfSiteRotation == 6 && clock < (90 / DEG) && mixCube == true){
			Z_AxisRotationLeftTwist();
			clock++;
		}
		
		if(numOfSiteRotation == 7 && clock < (90 / DEG) && mixCube == true){
			Z_AxisRotationTopTwist();
			clock++;
		}
		
		if(numOfSiteRotation == 8 && clock < (90 / DEG) && mixCube == true){
			Z_AxisRotationRightTwist();
			clock++;
		}
				
		if(clock == (90 / DEG)){
			updateTwistSettings();
			if(numOfSiteRotation == 9){
				resetTwistSettings();
			}
		}
	}
	
	private void updateTwistSettings(){
		numOfSiteRotation++;
		reverseRotation = false;
		mixCube = false;
		rand1 = 0;
		rand2 = 0;
		rand3 = 0;
	}
	
	private void resetTwistSettings(){
		numOfSiteRotation = 0;
	}
	
	public void X_AxisRotationLeftTwist(){
		if(rand4 == 0){
			leftTwist(rand1);
		}else{
			leftTwist(-rand1);
		}
	}
	
	public void X_AxisRotationTopTwist(){
		if(rand4 == 0){
			leftTwist(-rand2);
			rightTwist(-rand2);
			rotateCube(1, rand2);
		}else{
			leftTwist(rand2);
			rightTwist(rand2);
			rotateCube(1, -rand2);
		}
	}
	
	public void X_AxisRotationRightTwist(){
		if(rand4 == 0){
			rightTwist(rand3);
		}else{
			rightTwist(-rand3);
		}
	}
	
	public void Y_AxisRotationLeftTwist(){
		if(rand4 == 0){
			upTwist(-rand1);
		}else{
			upTwist(rand1);
		}
	}
	
	public void Y_AxisRotationTopTwist(){
		if(rand4 == 0){
			upTwist(rand2);
			bottomTwist(rand2);
			rotateCube(2, -rand2);
		}else{
			upTwist(-rand2);
			bottomTwist(-rand2);
			rotateCube(2, rand2);
		}

	}
	
	public void Y_AxisRotationRightTwist(){
		if(rand4 == 0){
			bottomTwist(-rand3);
		}else{
			bottomTwist(rand3);
		}
	}
	
	public void Z_AxisRotationLeftTwist(){
		if(rand4 == 0){
			backTwist(-rand1);
		}else{
			backTwist(rand1);
		}
	}
	
	public void Z_AxisRotationTopTwist(){
		if(rand4 == 0){
			backTwist(-rand2);
			frontTwist(-rand2);
		}else{
			backTwist(rand2);
			frontTwist(rand2);
		}
	}
	
	public void Z_AxisRotationRightTwist(){
		if(rand4 == 0){
			frontTwist(rand3);
		}else{
			frontTwist(-rand3);
		}
	}

	public void singleTwist() {
		if (clock < (90 / DEG)) {	
			switch (faceTurn) {
			case 11:
				if(reverseRotation == false){
					leftTwist(frontTwist);
				}else{
					leftTwist(backTwist);
				}
				clock++;
				break;
			case 12:
				leftTwist(backTwist);
				clock++;
				break;
			case 21:
				if(reverseRotation == false){
					leftTwist(backTwist);
					rightTwist(backTwist);
					rotateCube(1, frontTwist);
				}else{
					leftTwist(frontTwist);
					rightTwist(frontTwist);
					rotateCube(1, backTwist);
				}
				clock++;
				break;
			case 22:
				leftTwist(frontTwist);
				rightTwist(frontTwist);
				rotateCube(1, backTwist);
				clock++;
				break;
			case 31:
				if(reverseRotation == false){
					rightTwist(frontTwist);
				}else{
					rightTwist(backTwist);
				}
				clock++;
				break;
			case 32:
				rightTwist(backTwist);
				clock++;
				break;
			case 41:
				if(reverseRotation == false){
					upTwist(backTwist);
				}else{
					upTwist(frontTwist);
				}
				clock++;
				break;
			case 42:
				upTwist(frontTwist);
				clock++;
				break;
			case 51:
				if(reverseRotation == false){
					upTwist(frontTwist);
					bottomTwist(frontTwist);
					rotateCube(2, backTwist);
				}else{
					upTwist(backTwist);
					bottomTwist(backTwist);
					rotateCube(2, frontTwist);
				}
				clock++;
				break;
			case 52:
				upTwist(backTwist);
				bottomTwist(backTwist);
				rotateCube(2, frontTwist);
				clock++;
				break;
			case 61:
				if(reverseRotation == false){
					bottomTwist(backTwist);
				}else{
					bottomTwist(frontTwist);
				}
				clock++;
				break;
			case 62:
				bottomTwist(frontTwist);
				clock++;
				break;
			case 71:
				if(reverseRotation == false){
					backTwist(frontTwist);
				}else{
					backTwist(backTwist);
				}
				clock++;
				break;
			case 72:
				backTwist(backTwist);
				clock++;
				break;
			case 81:
				if(reverseRotation == false){
					backTwist(backTwist);
					frontTwist(backTwist);
				}else{
					backTwist(frontTwist);
					frontTwist(frontTwist);
				}
				clock++;
				break;
			case 82:
				backTwist(frontTwist);
				frontTwist(frontTwist);
				clock++;
				break;
			case 91:
				if(reverseRotation == false){
					frontTwist(frontTwist);
				}else{
					frontTwist(backTwist);
				}
				clock++;
				break;
			case 92:
				frontTwist(backTwist);
				clock++;
				break;
			case 101:
				rotateCube(1, frontTwist);
				clock++;
				break;
			case 102:
				rotateCube(1, backTwist);
				clock++;
				break;
			case 103:
				rotateCube(2, frontTwist);
				clock++;
				break;
			case 104:
				rotateCube(2, backTwist);
				clock++;
				break;
			}
		} else {
			clock = 0;
			faceTurn = 0;
		}
	}
}