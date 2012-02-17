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
 * The Glyph class represents the shape to draw for a single character
 * encoded as a String.
 *
 * @author Timothy Jon Fraser <i>tfraser@alum.wpi.edu</i>
 *
 */

class Glyph {

	/** Glyphs are 6 pixels wide. */
	static int glyphWidth = 6;
	/** Glyphs are 8 pixels wide. */
	static int glyphHeight = 8;
	/** Bit pattern for Glyph encoded in a string. */
	String pattern;             
	
	/**
	 * 
	 * Create a Glyph based on the pattern in <code>p</code>.  The pattern
	 * should be a string of <code>glyphWidth</code> by <code>glyphHeight</code>
	 * characters.  Each character represents one pixel.  The first character
	 * in the string is the leftmost pixel of the top row.  The following
	 * characters are the remainder of the top row followed by all the lower
	 * rows.  A space character represents an off pixel.  Any other character
	 * is an on pixel.
	 * 
	 * @param p string describing on/off pixel pattern
	 */
	Glyph(String p) {
		pattern = p;
	}

	/**
	 * 
	 * Return true or flase depending on whether the pixel at <code>x</code>,
	 * <code>y</code> in the Glyph is on or off.
	 * 
	 * @param x x coordinate of pixel
	 * @param y y coordinate of pixel
	 * @return true if pixel <code>x</code>,<code>y</code> should be on,
	 *         else false.
	 */
	public boolean pixel(int x, int y) {
		assert(x < glyphWidth);
		assert(y < glyphHeight);
		return (' ' != pattern.charAt(x + y * glyphWidth));
	}
}
	
