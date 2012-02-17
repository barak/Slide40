import java.io.*;
import java.util.*;

// Slide40 - Presentations with 8-bit style.
// Copyright (C) 2007 Timothy Jon Fraser <tfraser@alum.wpi.edu>
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.

/**
 *
 * Encapsulates a slide presentation.  Implements methods
 * to read a presentation from a file and convert it to
 * a form we can handle with our Alphabet.
 *
 * Also provides a default presentation that presents
 * usage and license information.
 *
 * @author Timothy Jon Fraser <i>tfraser@alum.wpi.edu</i>
 *
 */
class Presentation
{
    /** The default presentation: usage and license information */
    protected static String helloMessage[] = {
	"****************************************",
	"*                                      *",
	"*               SLIDE40                *",
	"*                                      *",
	"*                                      *",
	"*    PRESENTATIONS WITH 8-BIT STYLE    *",
	"*                                      *",
	"****************************************",
	"",
	"",
	" COPYRIGHT (C) 2007 TIMOTHY JON FRASER",
	"",
	"          TFRASER@ALUM.WPI.EDU",
	"",
	"              VERSION 1.0",
	"",
	"",
	"",
	"  FOR USAGE, LICENSE AND WARRANTY INFO",
	"",
	"    HIT SPACE TO ADVANCE THROUGH THE",
	"",
	"          FOLLOWING 7 SLIDES.",
	"",

	"****************************************",
	"*                                      *",
	"*             BASIC USAGE              *",
	"*                                      *",
	"****************************************",
	"",
	"",
	"  + USE FILE MENU TO LOAD A FILE, QUIT,",
	"",
	"    OR GO TO FULL SCREEN MODE.",
	"",
	"",
	"  + SPACE, PAGE DOWN, AND R/D ARROW",
	"",
	"    KEYS ADVANCE TO NEXT SLIDE.",
	"",
	"",
	"  + B, PAGE UP, AND L/U ARROW KEYS",
	"",
	"    RETURN TO PREVIOUS SLIDE.",
	"",
	"",
	"  + USE ESC TO EXIT FULL SCREEN MODE.",
	"",

	"****************************************",
	"*                                      *",
	"*          HOW TO MAKE SLIDES          *",
	"*                                      *",
	"****************************************",
	"",
	"",
	"",
	" + USE YOUR FAVORITE WORD PROCESSOR",
	"",
	"   OR TEXT EDITOR TO MAKE SLIDES.",
	"",
	"",
	"",
	" + SAVE THEM IN TEXT FORMAT.",
	"",
	"",
	"",
	" + SLIDE40 WRAPS LINES TO 40 COLUMNS",
	"",
	"   AND CONVERTS LETTERS TO UPPER CASE.",
	"",
	"",
	"",


	"****************************************",
	"*                                      *",
	"*      HOW TO MANAGE SLIDE BREAKS      *",
	"*                                      *",
	"****************************************",
	"",
	"",
	"",
	" + EACH SLIDE IS 24 LINES LONG.",
	"",
	"",
	"",
	" + INSERT BLANK LINES WHEN NEEDED",
	"",
	"   TO MAKE SLIDE BREAKS FALL WHERE",
	"",
	"   YOU WANT THEM.",
	"",
	"",
	"",
	" + TEST BY VIEWING SLIDES IN SLIDE40.",
	"",
	"   ADJUST UNTIL YOU HAVE IT RIGHT.",
	"",


	"****************************************",
	"*                                      *",
	"*   WHY EVERYONE SHOULD USE SLIDE40    *",
	"*                                      *",
	"****************************************",
	"",
	" + FOR GREAT JUSTICE!",
	"",
	"",
	" + SLIDES WITH FLASHY EYE CANDY CLOG ",
	"",
	"   THE NET.  IT'S NOT A BIG TRUCK---",
	"",
	"   IT'S A SERIES OF TUBES!",
	"",
	"",
	" + IF IT'S IMPORTANT ENOUGH TO FORCE",
	"",
	"   EVERYONE TO ATTEND A MEETING,",
	"",
	"   IT'S IMPORTANT ENOUGH TO SHOUT IN  ",
	"",
	"    A L L   C A P S ! ! 1 ! ONE ! !",
	"",

	"****************************************",
	"*                                      *",
	"*   LICENSE AND WARRANTY INFORMATION   *",
	"*                                      *",
	"****************************************",
	"",
	"",
	"",
	"THIS PROGRAM IS FREE SOFTWARE: YOU CAN",
	"",
	"REDISTRIBUTE IT AND/OR MODIFY IT UNDER",
	"",
	"THE TERMS OF THE GNU GENERAL PUBLIC",
	"",
	"LICENSE AS PUBLISHED BY THE FREE",
	"",
	"SOFTWARE FOUNDATION, EITHER VERSION 3",
	"",
	"OF THE LICENSE OR (AT YOUR OPTION) ANY",
	"",
	"LATER VERSION.",
	"",
	"",
	"               (CONTINUED ON NEXT SLIDE)",

	"****************************************",
	"*                                      *",
	"*   LICENSE AND WARRANTY INFORMATION   *",
	"*                                      *",
	"****************************************",
	"",
	"",
	"",
	"THIS PROGRAM IS DISTRIBUTED IN THE HOPE",
	"",
	"THAT IT WILL BE USEFUL, BUT WITHOUT ANY",
	"",
	"WARRANTY; WITHOUT EVEN THE IMPLIED",
	"",
	"WARRANTY OF MERCHANTABILITY OR FITNESS",
	"",
	"FOR A PARTICULAR PURPOSE.  SEE THE GNU",
	"",
	"GENERAL PUBLIC LICENSE FOR MORE DETAILS.",
	"",
	"",
	"",
	"",
	"               (CONTINUED ON NEXT SLIDE)",


	"****************************************",
	"*                                      *",
	"*   LICENSE AND WARRANTY INFORMATION   *",
	"*                                      *",
	"****************************************",
	"",
	"",
	"",
	"YOU SHOULD HAVE RECEIVED A COPY OF THE",
	"",
	"GNU GENERAL PUBLIC LICENSE ALONG WITH",
	"",
	"THIS PROGRAM.  IF NOT, SEE",
	"",
	"<HTTP://WWW.GNU.ORG/LICENSES/>.",
	"",
	"",
    };

    /** The array of lines in the presentation. */
    protected String lines[];
    /** The number of lines in the presentation. */
    protected int numLines;
    /** The number of slides in the presentation */
    protected int numSlides;
    /** The number of the slide being displayed, 0 = first */
    protected int currentSlide;

    /**
     *
     * The last slide in a presentation may not have enough rows
     * to fill the terminal screen.  This method tells us how
     * many rows are in the current slide so we know when to
     * stop drawing rows.
     *
     * @return the number of lines in the current slide.
     */
    public int rowsOnCurrentSlide ()
    {
	if (currentSlide == (numSlides - 1))
	    {
		return numLines % Terminal.cheight;
	    }
	else
	    {
		return Terminal.cheight;
	    }
    }

    /**
     * Return the String representing the <code>lineInCurrentSlide</code>th
     * line of the current slide.
     *
     * @param lineInCurrentSlide line number
     * @return String containing line data
     *
     */
    public String getLine (int lineInCurrentSlide)
    {
	return lines[currentSlide * Terminal.cheight + lineInCurrentSlide];
    }

    /**
     * Makes the next slide the current slide.  If there is no next slide,
     * keep the current slide the same.
     *
     * @return true if we moved to the next slide, false if we were already
     *         at the end of the presentation
     *
     */
    public boolean nextSlide ()
    {
	if (currentSlide < (numSlides - 1))
	    {
		currentSlide++;
		return true;
	    }
	return false;
    }

    /**
     *
     * Makes the previous slide the current slide.  If there is no previous slide,
     * keep the current slide the same.
     *
     * @return true if we moved to the previous slide, false if we were already
     *         at the beginning of the presentation
     */
    public boolean previousSlide ()
    {
	if (currentSlide > 0)
	    {
		currentSlide--;
		return true;
	    }
	return false;
    }

    /**
     *
     * Opens the file specified by <code>fileName</code> and
     * makes the data in it the current presentation.
     *
     * Converts characters to upper case and wraps lines to fit
     * 40 columns.
     *
     * @param fileName the name of the file to open
     * @return nil if the file opened without error, otherwise a
     *         diagnostic error string.
     */
    public String openFile (String fileName)
    {

	ArrayList < String > lineList = new ArrayList < String > ();

	try
	    {

		FileReader fr = new FileReader (fileName);
		BufferedReader br = new BufferedReader (fr);
		int c;
		boolean done = false;

		// Read input file one line at a time.  Make lines end on
		// a newline character or after reading Terminal.cwidth
		// columns.  Save each line as a separate string and append
		// each completed string to lineList.
		while (!done)
		    {
			StringBuffer sb = new StringBuffer ();
			for (int i = 0; i < Terminal.cwidth; i++)
			    {
				c = br.read ();
				if (c == -1)
				    {		// EOF
					done = true;
					break;
				    }
				else if (c == '\n')
				    {
					break;	// end the line
				    }
				else if (c == '\r')
				    {
					// skip
				    }
				sb.append ((char) c);
			    }
			String s = sb.toString ();
			s = s.toUpperCase ();
			lineList.add (s);
		    }

	    } catch (IOException e)
	    {
		String errorString = e.getLocalizedMessage ();
		if (errorString == null)
		    {
			errorString = "Cause unknown.";
		    }
		return errorString;
	    }

	// We're read and parsed the file.  Make its data
	// our current presentation.
	lines = lineList.toArray (new String[lineList.size ()]);
	numLines = lineList.size ();
	numSlides =
	    (int) Math.ceil ((double) lineList.size () / (double) Terminal.cheight);
	currentSlide = 0;


	return null;

    }

    /**
     * Create a new Presentation showing the default help presentation.
     *
     */
    public Presentation ()
    {
	lines = helloMessage;
	numLines = 209;
	numSlides = 8;
	currentSlide = 0;
    }
}
