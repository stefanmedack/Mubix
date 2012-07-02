package de.tub.mubix.main;

import java.util.LinkedList;

import peasy.PeasyCam;
import processing.core.PApplet;
import processing.core.PVector;
import SimpleOpenNI.SimpleOpenNI;
import SimpleOpenNI.XnPoint3D;
import SimpleOpenNI.XnVHandPointContext;
import SimpleOpenNI.XnVSelectableSlider2D;
import SimpleOpenNI.XnVSessionManager;
import SimpleOpenNI.XnVSwipeDetector;
import de.tub.mubix.speechControl.SpeechControl;

@SuppressWarnings("serial")
public class Mubix extends PApplet {
	PeasyCam cam;

	final int WINDOWX = 1024;
	final int WINDOWY = 640;

	// modes
	final boolean enableOpenNI = false;
	final boolean enableSpeech = false;
	final boolean enableMultimodal = true;
	boolean showDepth = false;

	private LinkedList<Integer> moveList = null;

	// int clock = 0;
	// int ith = 0;
	// int faceTurn = 0;
	// boolean switch1 = false;
	// String operation = new String();
	// String solution = new String();

	// PImage bkg_img;

	public SimpleOpenNI context;
	public XnVSwipeDetector swipeDetector;

	Cube theCube;

	XnVSessionManager sessionManager;
	XnVSelectableSlider2D trackPad;
	Thread speechThread;

	int gridX = 3;
	int gridY = 3;

	Trackpad trackPadViz;

	@Override
	public void setup() {

		size(WINDOWX, WINDOWY, P3D);

		/************************************************************
		 ********************* Cube Stuff
		 ************************************************************/

		cam = new PeasyCam(this, WINDOWX / 2, WINDOWY / 2, 0, 350);
		cam.setMinimumDistance(50);
		cam.setMaximumDistance(600);
		cam.setActive(false); // deactivate mouse rotation
		theCube = new Cube(this);

		moveList = new LinkedList<Integer>();

		// bkg_img = loadImage("cube_black_1024_640.jpg");
		// bkg_img.filter(OPAQUE);

		/************************************************************
		 ********************* OpenNI Stuff
		 ************************************************************/

		if (enableOpenNI) {
			/*
			 * Simple NI
			 */
			context = new SimpleOpenNI(this);
			context.enableDepth();
			context.enableUser(SimpleOpenNI.SKEL_PROFILE_ALL);
			context.setMirror(true);

			stroke(0, 0, 255);
			strokeWeight(3);

			// swipeDetector
			sessionManager = new XnVSessionManager("RaiseHand");
			sessionManager.SetTracker(context.getHandsGenerator());

			swipeDetector = new XnVSwipeDetector();
			swipeDetector.RegisterSwipeLeft(this);
			swipeDetector.RegisterSwipeRight(this);
			swipeDetector.RegisterSwipeUp(this);
			swipeDetector.RegisterSwipeDown(this);
			sessionManager.AddListener(swipeDetector);

		}
		// size(context.depthWidth(), context.depthHeight());

		/************************************************************
		 ********************* Speech Control Stuff
		 ************************************************************/

		if (enableSpeech) {
			SpeechControl speechCtrl = new SpeechControl();
			speechCtrl.setCube(this.theCube);
			Thread thread = new Thread(speechCtrl);
			thread.start();
		}

		/************************************************************
		 ********************* Multimodal
		 ************************************************************/

		if (enableMultimodal) {
			// Camera input
			context = new SimpleOpenNI(this,
					SimpleOpenNI.RUN_MODE_MULTI_THREADED);
			context.enableDepth();
			context.enableUser(SimpleOpenNI.SKEL_PROFILE_ALL);
			context.setMirror(true);

			// enable the hands + gesture
			context.enableGesture();
			context.enableHands();

			// setup NITE
			sessionManager = context.createSessionManager("Click,Wave",
					"RaiseHand");

			trackPad = new XnVSelectableSlider2D(gridX, gridY);
			sessionManager.AddListener(trackPad);

			trackPad.RegisterItemHover(this);
			trackPad.RegisterValueChange(this);
			trackPad.RegisterItemSelect(this);

			trackPad.RegisterPrimaryPointCreate(this);
			trackPad.RegisterPrimaryPointDestroy(this);

			// create gui viz
			trackPadViz = new Trackpad(this, new PVector(
					context.depthWidth() / 2, context.depthHeight() / 2, 0),
					gridX, gridY, 30, 30, 5);

			// size(context.depthWidth(), context.depthHeight());
			smooth();

			// info text
			println("-------------------------------");
			println("1. Wave till the tiles get green");
			println("2. The relative hand movement will select the tiles");
			println("-------------------------------");

			// Speech input
			SpeechControl speechCtrl = new SpeechControl();
			speechCtrl.setCube(this.theCube);
			speechCtrl.setTrackpad(this.trackPadViz);
			speechThread = new Thread(speechCtrl);
			speechThread.start();
		}

	}

	@Override
	public void draw() {
		background(70, 130, 180);// For dynamically updating the background
		if (enableOpenNI) {

			context.update();
			context.update(sessionManager);

			// draw depthImageMap
			image(context.depthImage(), 0, 0);

			// draw the skeleton if it's available
			if (context.isTrackingSkeleton(1))
				drawSkeleton(1);
		}

		if (enableMultimodal) {
			// update the cam
			context.update();

			// update nite
			context.update(sessionManager);

			if (showDepth) {
				// draw depthImageMap
				image(context.depthImage(), 0, 0, width / 2, height / 2);
			}
			trackPadViz.draw();
		}

		stroke(0, 0, 0);
		translate(WINDOWX / 2, WINDOWY / 2);
		rotateX(-0.4f);
		rotateY(0.4f);
		theCube.display();

		theCube.mixingCube();

		theCube.singleTwist();

		// image(bkg_img,-width/2, -height/2);
		// println(frameRate);
	}

	// -----------------------------------------------------------------
	// draw skeleton
	// -----------------------------------------------------------------

	// draw the skeleton with the selected joints
	public void drawSkeleton(int userId) {
		// to get the 3d joint data
		/*
		 * PVector jointPos = new PVector();
		 * context.getJointPositionSkeleton(userId
		 * ,SimpleOpenNI.SKEL_NECK,jointPos); println(jointPos);
		 */

		stroke(0, 0, 255);
		strokeWeight(10);

		context.drawLimb(userId, SimpleOpenNI.SKEL_HEAD, SimpleOpenNI.SKEL_NECK);

		context.drawLimb(userId, SimpleOpenNI.SKEL_NECK,
				SimpleOpenNI.SKEL_LEFT_SHOULDER);
		context.drawLimb(userId, SimpleOpenNI.SKEL_LEFT_SHOULDER,
				SimpleOpenNI.SKEL_LEFT_ELBOW);
		context.drawLimb(userId, SimpleOpenNI.SKEL_LEFT_ELBOW,
				SimpleOpenNI.SKEL_LEFT_HAND);

		context.drawLimb(userId, SimpleOpenNI.SKEL_NECK,
				SimpleOpenNI.SKEL_RIGHT_SHOULDER);
		context.drawLimb(userId, SimpleOpenNI.SKEL_RIGHT_SHOULDER,
				SimpleOpenNI.SKEL_RIGHT_ELBOW);
		context.drawLimb(userId, SimpleOpenNI.SKEL_RIGHT_ELBOW,
				SimpleOpenNI.SKEL_RIGHT_HAND);

		context.drawLimb(userId, SimpleOpenNI.SKEL_LEFT_SHOULDER,
				SimpleOpenNI.SKEL_TORSO);
		context.drawLimb(userId, SimpleOpenNI.SKEL_RIGHT_SHOULDER,
				SimpleOpenNI.SKEL_TORSO);

		context.drawLimb(userId, SimpleOpenNI.SKEL_TORSO,
				SimpleOpenNI.SKEL_LEFT_HIP);
		context.drawLimb(userId, SimpleOpenNI.SKEL_LEFT_HIP,
				SimpleOpenNI.SKEL_LEFT_KNEE);
		context.drawLimb(userId, SimpleOpenNI.SKEL_LEFT_KNEE,
				SimpleOpenNI.SKEL_LEFT_FOOT);

		context.drawLimb(userId, SimpleOpenNI.SKEL_TORSO,
				SimpleOpenNI.SKEL_RIGHT_HIP);
		context.drawLimb(userId, SimpleOpenNI.SKEL_RIGHT_HIP,
				SimpleOpenNI.SKEL_RIGHT_KNEE);
		context.drawLimb(userId, SimpleOpenNI.SKEL_RIGHT_KNEE,
				SimpleOpenNI.SKEL_RIGHT_FOOT);
	}

	// -----------------------------------------------------------------
	// SimpleOpenNI events
	// -----------------------------------------------------------------

	public void onNewUser(int userId) {
		println("onNewUser - userId: " + userId);
		context.requestCalibrationSkeleton(userId, true);
	}

	public void onLostUser(int userId) {
		println("onLostUser - userId: " + userId);
	}

	public void onStartCalibration(int userId) {
		println("onStartCalibration - userId: " + userId);
	}

	public void onEndCalibration(int userId, boolean successfull) {
		println("onEndCalibration - userId: " + userId + ", successfull: "
				+ successfull);

		if (successfull) {
			println("  User calibrated !!!");
			context.startTrackingSkeleton(userId);
		} else {
			println("  Failed to calibrate user !!!");
		}
	}

	void onSwipeLeft(float velocity, float angle) {
		println("SWIPELEFT " + velocity + " " + angle);
	}

	void onSwipeRight(float velocity, float angle) {
		println("SWIPERIGHT " + velocity + " " + angle);

	}

	void onSwipeUp(float velocity, float angle) {
		println("SWIPEUP " + velocity + " " + angle);
	}

	void onSwipeDown(float velocity, float angle) {
		println("SWIPEDOWN " + velocity + " " + angle);
	}

	// -----------------------------------------------------------------
	// Cube Stuff
	// -----------------------------------------------------------------

	@Override
	public void keyPressed() {
		if (theCube.clock == 0) {
			if (key == '1') {
				theCube.faceTurn = 11;
				setMoveReminder(theCube.faceTurn);
			}
			if (key == '2') {
				theCube.faceTurn = 21;
				setMoveReminder(theCube.faceTurn);
			}
			if (key == '3') {
				theCube.faceTurn = 31;
				setMoveReminder(theCube.faceTurn);
			}
			if (key == '4') {
				theCube.faceTurn = 41;
				setMoveReminder(theCube.faceTurn);
			}
			if (key == '5') {
				theCube.faceTurn = 51;
				setMoveReminder(theCube.faceTurn);
			}
			if (key == '6') {
				theCube.faceTurn = 61;
				setMoveReminder(theCube.faceTurn);
			}
			if (key == '7') {
				theCube.faceTurn = 71;
				setMoveReminder(theCube.faceTurn);
			}
			if (key == '8') {
				theCube.faceTurn = 81;
				setMoveReminder(theCube.faceTurn);
			}
			if (key == '9') {
				theCube.faceTurn = 91;
				setMoveReminder(theCube.faceTurn);
			}
			if ((key == 'q') || (key == 'Q')) {
				theCube.faceTurn = 12;
				setMoveReminder(theCube.faceTurn);
			}
			if ((key == 'w') || (key == 'W')) {
				theCube.faceTurn = 22;
				setMoveReminder(theCube.faceTurn);
			}
			if ((key == 'e') || (key == 'E')) {
				theCube.faceTurn = 32;
				setMoveReminder(theCube.faceTurn);
			}
			if ((key == 'r') || (key == 'R')) {
				theCube.faceTurn = 42;
				setMoveReminder(theCube.faceTurn);
			}
			if ((key == 't') || (key == 'T')) {
				theCube.faceTurn = 52;
				setMoveReminder(theCube.faceTurn);
			}
			if ((key == 'z') || (key == 'Z')) {
				theCube.faceTurn = 62;
				setMoveReminder(theCube.faceTurn);
			}
			if ((key == 'u') || (key == 'U')) {
				theCube.faceTurn = 72;
				setMoveReminder(theCube.faceTurn);
			}
			if ((key == 'i') || (key == 'I')) {
				theCube.faceTurn = 82;
				setMoveReminder(theCube.faceTurn);
			}
			if ((key == 'o') || (key == 'O')) {
				theCube.faceTurn = 92;
				setMoveReminder(theCube.faceTurn);
			}
			if ((key == 'n') || (key == 'N') || (key == '.')) {
				theCube.mixCube = true;
			} else {
				theCube.mixCube = false;
			}
			if ((key == 'ö') || (key == 'Ö') || (key == ',')) {
				if (!moveList.isEmpty()) {
					theCube.reverseRotation = true;
					theCube.faceTurn = moveList.getLast();
					moveList.removeLast();
				}
			}
			if ((key == 'd') || (key == 'D')) {
				this.showDepth = !this.showDepth;
			}

			if (key == CODED) {
				if (keyCode == UP) {
					theCube.faceTurn = 101;
				}
				if (keyCode == DOWN) {
					theCube.faceTurn = 102;
				}
				if (keyCode == LEFT) {
					theCube.faceTurn = 103;
				}
				if (keyCode == RIGHT) {
					theCube.faceTurn = 104;
				}
			}
		}
	}

	public void setMoveReminder(int faceTurn) {
		moveList.add(faceTurn);
	}

	// mm mehtods

	void onItemHover(int nXIndex, int nYIndex) {
		println("onItemHover: nXIndex=" + nXIndex + " nYIndex=" + nYIndex);

		trackPadViz.update(nXIndex, nYIndex);
	}

	void onValueChange(float fXValue, float fYValue) {
		// println("onValueChange: fXValue=" + fXValue +" fYValue=" + fYValue);
	}

	void onItemSelect(int nXIndex, int nYIndex, int eDir) {
		// println("onItemSelect: nXIndex=" + nXIndex + " nYIndex=" + nYIndex +
		// " eDir=" + eDir);
		// trackPadViz.push(nXIndex,nYIndex,eDir);
	}

	void onPrimaryPointCreate(XnVHandPointContext pContext, XnPoint3D ptFocus) {
		println("onPrimaryPointCreate");

		trackPadViz.enable();
	}

	void onPrimaryPointDestroy(int nID) {
		println("onPrimaryPointDestroy");

		trackPadViz.disable();
	}

}
