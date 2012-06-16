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
import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;


public class SpeechControl implements Runnable{
	
	private Cube cube;
	
    public Cube getCube() {
		return cube;
	}

	public void setCube(Cube cube) {
		this.cube = cube;
	}

	private void extractCommand(Result result) {
		
		String s = result.getBestFinalResultNoFiller();
		
		if (s != "") {
			int cube = s.indexOf("cube");
			int up = s.indexOf("up");
			int down = s.indexOf("down");
			int right = s.indexOf("right");
			int left = s.indexOf("left");
			int top = s.indexOf("top");
			int bottom = s.indexOf("bottom");
			if (this.cube.clock == 0) {
				if (cube >= 0) {
					if (up > cube) {
						this.cube.faceTurn = 101;
					} else if (down > cube) {
						this.cube.faceTurn = 102;
					} else if (right > cube) {
						this.cube.faceTurn = 103;
					} else if (left > cube) {
						this.cube.faceTurn = 104;
					}
				}
				if (right >= 0) {
					if (up > right) {
						this.cube.faceTurn = 31;
					} else if (down > right) {
						this.cube.faceTurn = 32;
					}
				}
				if (left >= 0) {
					if (up > left) {
						this.cube.faceTurn = 11;
					} else if (down > left) {
						this.cube.faceTurn = 12;
					}
				}
				if (top >= 0) {
					if (right > top) {
						this.cube.faceTurn = 41;
					} else if (left > top) {
						this.cube.faceTurn = 42;
					}
				}
				if (bottom >= 0) {
					if (right > bottom) {
						this.cube.faceTurn = 61;
					} else if (left > bottom) {
						this.cube.faceTurn = 62;
					}
				}
			}
		}
	}
		

	@Override
	public void run() {
	        ConfigurationManager cm = new ConfigurationManager(SpeechControl.class.getResource("speechControl.config.xml"));

	        Recognizer recognizer = (Recognizer) cm.lookup("recognizer");
	        recognizer.allocate();

	        // start the microphone or exit if the programm if this is not possible
	        Microphone microphone = (Microphone) cm.lookup("microphone");
	        if (!microphone.startRecording()) {
	            System.out.println("Cannot start microphone.");
	            recognizer.deallocate();
	            System.exit(1);
	        }

	        System.out.println("Say: (cube | left | right | top | bottom) ( up | down | left | right )");

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
