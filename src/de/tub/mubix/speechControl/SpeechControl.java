/*
 * Copyright 1999-2004 Carnegie Mellon University.
 * Portions Copyright 2004 Sun Microsystems, Inc.
 * Portions Copyright 2004 Mitsubishi Electric Research Laboratories.
 * All Rights Reserved.  Use is subject to license terms.
 *
 * See the file "license.terms" for information on usage and
 * redistribution of this file, and for a DISCLAIMER OF ALL
 * WARRANTIES.
 *
 */

package de.tub.mubix.speechControl;

import de.tub.mubix.main.Cube;
import de.tub.mubix.main.Trackpad;
import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;

public class SpeechControl implements Runnable {

	private Cube cube;
	private Trackpad trackpad;

	public Cube getCube() {
		return cube;
	}

	public void setCube(Cube cube) {
		this.cube = cube;
	}

	public Trackpad getTrackpad() {
		return trackpad;
	}

	public void setTrackpad(Trackpad trackpad) {
		this.trackpad = trackpad;
	}

	private void extractCommand(Result result) {

		String s = result.getBestFinalResultNoFiller();

		if (!trackpad.isActive()) {
			return;
		}

		if (s != "" && this.cube.clock == 0) {
			if(s.contains("undo")){
				this.cube.undoLastMove();
			}
			
			if (trackpad.getFocusX() == 0 && trackpad.getFocusY() == 0) {
				if (s.contains("up")) {
					this.cube.setFaceTurn(11);
				} else if (s.contains("down")) {
					this.cube.setFaceTurn(12);
				} else if (s.contains("right")) {
					this.cube.setFaceTurn(41);
				} else if (s.contains("left")) {
					this.cube.setFaceTurn(42);
				}
			}

			if (trackpad.getFocusX() == 0 && trackpad.getFocusY() == 1) {
				if (s.contains("up")) {
					this.cube.setFaceTurn(11);
				} else if (s.contains("down")) {
					this.cube.setFaceTurn(12);
				} else if (s.contains("right")) {
					this.cube.setFaceTurn(51);
				} else if (s.contains("left")) {
					this.cube.setFaceTurn(52);
				}
			}

			if (trackpad.getFocusX() == 0 && trackpad.getFocusY() == 2) {
				if (s.contains("up")) {
					this.cube.setFaceTurn(11);
				} else if (s.contains("down")) {
					this.cube.setFaceTurn(12);
				} else if (s.contains("right")) {
					this.cube.setFaceTurn(61);
				} else if (s.contains("left")) {
					this.cube.setFaceTurn(62);
				}
			}

			if (trackpad.getFocusX() == 1 && trackpad.getFocusY() == 0) {
				if (s.contains("up")) {
					this.cube.setFaceTurn(21);
				} else if (s.contains("down")) {
					this.cube.setFaceTurn(22);
				} else if (s.contains("right")) {
					this.cube.setFaceTurn(41);
				} else if (s.contains("left")) {
					this.cube.setFaceTurn(42);
				}
			}

			if (trackpad.getFocusX() == 1 && trackpad.getFocusY() == 1) {
				if (s.contains("up")) {
					this.cube.setFaceTurn(101);
				} else if (s.contains("down")) {
					this.cube.setFaceTurn(102);
				} else if (s.contains("right")) {
					this.cube.setFaceTurn(104);
				} else if (s.contains("left")) {
					this.cube.setFaceTurn(103);
				}
			}

			if (trackpad.getFocusX() == 1 && trackpad.getFocusY() == 2) {
				if (s.contains("up")) {
					this.cube.setFaceTurn(21);
				} else if (s.contains("down")) {
					this.cube.setFaceTurn(22);
				} else if (s.contains("right")) {
					this.cube.setFaceTurn(61);
				} else if (s.contains("left")) {
					this.cube.setFaceTurn(62);
				}
			}

			if (trackpad.getFocusX() == 2 && trackpad.getFocusY() == 0) {
				if (s.contains("up")) {
					this.cube.setFaceTurn(31);
				} else if (s.contains("down")) {
					this.cube.setFaceTurn(32);
				} else if (s.contains("right")) {
					this.cube.setFaceTurn(41);
				} else if (s.contains("left")) {
					this.cube.setFaceTurn(42);
				}
			}

			if (trackpad.getFocusX() == 2 && trackpad.getFocusY() == 1) {
				if (s.contains("up")) {
					this.cube.setFaceTurn(31);
				} else if (s.contains("down")) {
					this.cube.setFaceTurn(32);
				} else if (s.contains("right")) {
					this.cube.setFaceTurn(51);
				} else if (s.contains("left")) {
					this.cube.setFaceTurn(52);
				}
			}

			if (trackpad.getFocusX() == 2 && trackpad.getFocusY() == 2) {
				if (s.contains("up")) {
					this.cube.setFaceTurn(31);
				} else if (s.contains("down")) {
					this.cube.setFaceTurn(32);
				} else if (s.contains("right")) {
					this.cube.setFaceTurn(61);
				} else if (s.contains("left")) {
					this.cube.setFaceTurn(62);
				}
			}

			// int cube = s.indexOf("cube");
			// int up = s.indexOf("up");
			// int down = s.indexOf("down");
			// int right = s.indexOf("right");
			// int left = s.indexOf("left");
			// int top = s.indexOf("top");
			// int bottom = s.indexOf("bottom");
			// if (this.cube.clock == 0) {
			// if (cube >= 0) {
			// if (up > cube) {
			// this.cube.faceTurn = 101;
			// } else if (down > cube) {
			// this.cube.faceTurn = 102;
			// } else if (right > cube) {
			// this.cube.faceTurn = 103;
			// } else if (left > cube) {
			// this.cube.faceTurn = 104;
			// }
			// }
			// if (right >= 0) {
			// if (up > right) {
			// this.cube.faceTurn = 31;
			// } else if (down > right) {
			// this.cube.faceTurn = 32;
			// }
			// }
			// if (left >= 0) {
			// if (up > left) {
			// this.cube.faceTurn = 11;
			// } else if (down > left) {
			// this.cube.faceTurn = 12;
			// }
			// }
			// if (top >= 0) {
			// if (right > top) {
			// this.cube.faceTurn = 41;
			// } else if (left > top) {
			// this.cube.faceTurn = 42;
			// }
			// }
			// if (bottom >= 0) {
			// if (right > bottom) {
			// this.cube.faceTurn = 61;
			// } else if (left > bottom) {
			// this.cube.faceTurn = 62;
			// }
			// }
			// }
		}
	}

	@Override
	public void run() {
		ConfigurationManager cm = new ConfigurationManager(
				SpeechControl.class.getResource("speechControl.config.xml"));

		Recognizer recognizer = (Recognizer) cm.lookup("recognizer");
		recognizer.allocate();

		// start the microphone or exit if the programm if this is not possible
		Microphone microphone = (Microphone) cm.lookup("microphone");
		if (!microphone.startRecording()) {
			System.out.println("Cannot start microphone.");
			recognizer.deallocate();
			System.exit(1);
		}

		System.out.println("Say: ( up | down | left | right )");

		// loop the recognition until the programm exits.
		while (true) {
			System.out.println("Start speaking. Press Ctrl-C to quit.\n");

			Result result = recognizer.recognize();

			if (result != null) {
				String resultText = result.getBestFinalResultNoFiller();
				System.out.println("You said: " + resultText + '\n');
				extractCommand(result);
			} else {
				System.out.println("I can't hear what you said.\n");
			}
		}
	}
}
