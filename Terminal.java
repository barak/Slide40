import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

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
 * Terminal is a canvas that we can draw Glyphs on.  It has some special
 * convenience functions to make it easier to simulate an old 40x24
 * terminal display that displays presentations one page at a time.
 *
 * <p> A note on scaling: regardless of the size of the canvas, the terminal
 * itself will appear as a rectangle centered in the canvas.  The dimensions
 * of the terminal rectangle will always be an integral multiple of the
 * terminal's basic <code>twidth</code>X<code>theight</code> resolution.
 * So when you stretch the canvas, the terminal will not grow smoothly,
 * but will instead grow in discrete bursts whenever the canvas becomes
 * big enough for the terminal to go to 2x, 3x, 4x, ... zoom.
 *
 * @author Timothy Jon Fraser <i>tfraser@alum.wpi.edu</i>
 *
 */
class Terminal extends Canvas
{

    /** width in text columns */
    static int cwidth = 40;
    /** height in text rows */
    static int cheight = 24;
    /** width in terminal pixels */
    static int twidth = cwidth * Glyph.glyphWidth;
    /** height in terminal pixels */
    static int theight = cheight * Glyph.glyphHeight;

    /** dimension of the canvas in Java pixels */
    Dimension jdim;
    /** twidth,heigh * scale = biggest term that fits */
    int scale;
    /** shift in Java pels required to center terminal */
    int hshift;
    /** shift in Java pels required to center terminal */
    int vshift;
    /** width of black border around edge of screen */
    int blackBorder;
    /** Glyph alphabet */
    Alphabet alphabet;
    /** the slides for the presentation */
    Presentation presentation;

    /**
     *  Set scale to the maximum integral multiple that permits
     *  a terminal with the proper twidth : theight aspect ratio.
     *
     */
    protected void resize ()
    {
	jdim = getSize ();
	int hscale = jdim.width / twidth;
	int vscale = jdim.height / theight;
	if (hscale < vscale)
	    {
		scale = hscale;
	    }
	else
	    {
		scale = vscale;
	    }
	hshift = (jdim.width - (twidth * scale)) / 2;
	vshift = (jdim.height - (theight * scale)) / 2;
	blackBorder = Glyph.glyphWidth * scale / 2;
    }

    /**
     *
     *  Draw a pixel at <code>tx</code>, <code>ty</code> on the
     *  terminal.
     *
     * @param g current graphics context supplied by paint routine
     * @param tx x coordinate of pixel in terms of <code>twidth</code>
     *        terminal grid
     * @param ty y coordinate of pixel in terms of <code>theight</code>
     *        terminal grid
     *
     */
    protected void drawPixel (Graphics g, int tx, int ty)
    {
	int jx = tx * scale + hshift;
	int jy = ty * scale + vshift;

	g.setColor (Color.lightGray);
	g.fillRect (jx, jy, scale, scale);
	g.setColor (Color.white);
	g.fillOval (jx, jy, scale, scale);
    }

    /**
     *
     * Draw a Glyph at <code>cx</code>, <code>cy</code>.
     *
     * @param g current graphics context from paint method
     * @param glyph Glyph to draw
     * @param cx x coordinate of Glyph in <code>cwidth</code>
     *        columns
     * @param cy y coordinate of Glyph in <code>cheight</code>
     *        lines
     */
    protected void drawChar (Graphics g, Glyph glyph, int cx, int cy)
    {

	assert (cx >= 0);
	assert (cy >= 0);
	assert (cx < cwidth);
	assert (cy < cheight);

	int tx = cx * Glyph.glyphWidth;	// tx of glyph's upper left corner
	int ty = cy * Glyph.glyphHeight;	// ty of glyph's upper left corner

	for (int i = 0; i < Glyph.glyphWidth; i++)
	    {
		for (int j = 0; j < Glyph.glyphHeight; j++)
		    {
			if (glyph.pixel (i, j))
			    {
				drawPixel (g, tx + i, ty + j);
			    }
		    }
	    }
    }

    /**
     *
     * Repaint the entire terminal display to show the contents of
     * the current slide of the current presentation.
     *
     * @param g current raphics context
     *
     */
    public void paint (Graphics g)
    {
	resize ();
	g.setColor (Color.darkGray);
	g.fillRect (0, 0, jdim.width, jdim.height);
	g.setColor (Color.black);
	g.fillRect ((hshift - blackBorder), (vshift - blackBorder),
		    (twidth * scale + 2 * blackBorder),
		    (theight * scale + 2 * blackBorder));
	for (int row = 0; row < presentation.rowsOnCurrentSlide (); row++)
	    {
		String line = presentation.getLine (row);
		for (int column = 0; column < line.length (); column++)
		    {
			assert (line.length () <= cwidth);
			char glyph = line.charAt (column);
			drawChar (g, alphabet.charToGlyph (glyph), column, row);
		    }
	    }
    }

    /**
     * Move to next slide in the current presentation.
     *
     */
    public void forward ()
    {
	if (presentation.nextSlide ())
	    {
		repaint ();
	    }
    }

    /**
     * Move to previous slide in the current presentation.
     *
     */
    public void back ()
    {
	if (presentation.previousSlide ())
	    {
		repaint ();
	    }
    }

    /**
     *
     * Create new terminal to display presentation <code>p</code>
     *
     * @param p current presentation
     */
    public Terminal (Presentation p)
    {
	alphabet = new Alphabet ();
	presentation = p;
	resize ();
    }

}
