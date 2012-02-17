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
 * A collection of Glyphs; essentially a "font".
 * 
 * @author Timothy Jon Fraser <i>tfraser@alum.wpi.edu</i>
 *
 */

class Alphabet {

	/** minimum character covered by Glyph Alphabet */
	static char minGlyph = ' ';                 // We cover glyphs from here...
	/** maximum character covered by Glyph Alphabet */
	static char maxGlyph = '`';                 // ... to here in ASCII order.

	Glyph glyphs[] = {
			new Glyph("                                                "),
			new Glyph("        !     !     !     !     !           !   "),
			new Glyph("       X X   X X                                "), // "
			new Glyph("       # #   # #  #####  # #  #####  # #   # #  "),
			new Glyph("        $   $$$$$ $ $    $$$    $ $ $$$$$   $   "),
			new Glyph("             %  %    %    %    %    %  %        "),
			new Glyph("       &&   &     &  &   &    & &   &  &   && & "),
			new Glyph("        X     X     X                           "), // '
			new Glyph("         (    (    (     (     (      (      (  "),
			new Glyph("       )      )      )     )     )    )    )    "),
			new Glyph("        *   * * *  ***    *    ***  * * *   *   "),
			new Glyph("              +     +   +++++   +     +         "),
			new Glyph("                                ,     ,    ,    "),
			new Glyph("                        -----                   "),
			new Glyph("                                            .   "),
			new Glyph("         /     /    /     /     /    /     /    "),
			new Glyph("       000  0   0 0  00 0 0 0 00  0 0   0  000  "),
			new Glyph("        1    11     1     1     1     1    111  "),
			new Glyph("       222  2   2     2   22   2    2     22222 "),
			new Glyph("      33333     3    3    33      3 3   3  333  "),
			new Glyph("         4    44   4 4  4  4  44444    4     4  "),
			new Glyph("      55555 5     5555      5     5 5   5  555  "),
			new Glyph("        666  6    6     6666  6   6 6   6  666  "),
			new Glyph("      77777     7    7    7    7     7     7    "),
			new Glyph("       888  8   8 8   8  888  8   8 8   8  888  "),
			new Glyph("       999  9   9 9   9  9999     9    9  999   "),
			new Glyph("                    :           :               "),
			new Glyph("                    ;           :     ;    ;    "),
			new Glyph("         <    <    <    <      <      <      <  "),
			new Glyph("                  =====       =====             "),
			new Glyph("       >      >      >      >    >    >    >    "),
			new Glyph("       ???  ?   ?    ?    ?     ?           ?   "),
			new Glyph("       @@@  @   @ @ @ @ @ @@@ @ @@  @      @@@  "),
			new Glyph("        A    A A  A   A A   A AAAAA A   A A   A "),
			new Glyph("      BBBB  B   B B   B BBBB  B   B B   B BBBB  "),
			new Glyph("       CCC  C   C C     C     C     C   C  CCC  "),
			new Glyph("      DDDD  D   D D   D D   D D   D D   D DDDD  "),
			new Glyph("      EEEEE E     E     EEEE  E     E     EEEEE "),
			new Glyph("      FFFFF F     F     FFFF  F     F     F     "),
			new Glyph("       GGGG G     G     G     G  GG G   G GGGGG "),
			new Glyph("      H   H H   H H   H HHHHH H   H H   H H   H "),
			new Glyph("       III    I     I     I     I     I    III  "),
			new Glyph("       JJJJ    J     J     J  J  J  J  J   JJJ  "),
			new Glyph("      K   K K  K  K K   KK    K K   K  K  K   K "),
			new Glyph("      L     L     L     L     L     L     LLLLL "),
			new Glyph("      M   M MM MM MM MM M M M M M M M   M M   M "),
			new Glyph("      N   N N   N NN  N N N N N  NN N   N N   N "),
			new Glyph("       OOO  O   O O   O O   O O   O O   O  OOO  "),
			new Glyph("      PPPP  P   P P   P PPPP  P     P     P     "),
			new Glyph("       QQQ  Q   Q Q   Q Q   Q Q Q Q Q  Q   QQ Q "),
			new Glyph("      RRRR  R   R R   R RRRR  R R   R  R  R   R "),
			new Glyph("       SSS  S   S S      SSS      S S   S  SSS  "),
			new Glyph("      TTTTT   T     T     T     T     T     T   "),
			new Glyph("      U   U U   U U   U U   U U   U U   U  UUU  "),
			new Glyph("      V   V V   V V   V V   V V   V  V V    V   "),
			new Glyph("      W   W W   W W   W W W W W W W WW WW W   W "),
			new Glyph("      X   X X   X  X X   XXX   X X  X   X X   X "),
			new Glyph("      Y   Y Y   Y  Y Y    Y     Y     Y     Y   "),
			new Glyph("      ZZZZZ     Z    Z    Z    Z    Z     ZZZZZ "),
			new Glyph("      [[[[[ [ [   [ [   [ [   [ [   [ [   [[[[[ "),
			new Glyph("       X     X      X     X     X      X     X  "), // \
			new Glyph("      ]]]]]   ] ]   ] ]   ] ]   ] ]   ] ] ]]]]] "),
			new Glyph("        ^    ^ ^                                "),
			new Glyph("                                          _____ "),
			new Glyph("       `      `      `                          ")
	};

	
	/**
	 * 
	 * Return the Glyph for drawing character c.  If there is no Glyph
	 * for c in the alphabet, return the Glyph for SPACE.
	 *
	 * @param c character that you want the Glyph for
	 * @return the Glyph for character c
	 * 
	 */
	
	public Glyph charToGlyph(char c) {
		// Replace chars for which we have no glyph with space.
		if (c < minGlyph) c = ' ';
		if (c > maxGlyph) c = ' ';
		return glyphs[((int)(c - minGlyph))];
	}
}
