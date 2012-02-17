import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

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

/** SlideForty
 *
 * 40-column slide presentation program using low-res 8-bit all-caps font.
 * 
 * @author Timothy Jon Fraser <i>tfraser@alum.wpi.edu</i>
 * @version 0.1
 * 
 */

class SlideForty {
	
	/** the current presentation */
	protected Presentation presentation;
	/** terminal drawing canvas */
	protected Terminal terminal;
	/** menubar for window mode */
	protected MenuBar menuBar;
	/** top-level frame */
	protected Frame theFrame;
	
	/**
	 * 
	 * Provide a typical File pull-down menu.
	 *
	 */
	static class FileMenu extends Menu implements ActionListener {
		/** The SlideForty app */
		SlideForty theApp;     
		
		/**
		 * 
		 * Make the file open dialog filter out everything except
		 * text files.  Won't work on all platforms according to
		 * the Java docs, but apparently doesn't hurt to try.
		 *
		 */
		static class TextFilter implements FilenameFilter {
		    public boolean accept(File dir, String name) {
		        return (name.endsWith(".txt"));
		    }
		}
		
		/**
		 * Map file menu items to actions.
		 * 
		 * @param s instance of SlideForty that contains us
		 */
		public FileMenu(SlideForty s) {
			super("File"); 
			theApp = s; 
			MenuItem menuItem;
			add(menuItem = new MenuItem("Open..."));
			menuItem.addActionListener(this);
			add(menuItem = new MenuItem("Go to full screen mode"));
			menuItem.addActionListener(this);
			add(menuItem = new MenuItem("Exit")); 
			menuItem.addActionListener(this);
		}
		
		/**
		 * Map actions to the functions that handle them.
		 * 
		 * @param e action event provided by windowing system.
		 * 
		 */
		public void actionPerformed(ActionEvent e) { 
			String item = e.getActionCommand(); 
			if (item.equals("Exit")) {
				theApp.exit(); 
			} else if (item.equals("Open...")) {
				// Use a FileDialog to get a filename from the user.
				FileDialog fileDialog = new FileDialog(theApp.theFrame,
					"Open a slide presentation", FileDialog.LOAD);
				fileDialog.setFilenameFilter(new TextFilter());
				fileDialog.setVisible(true);
				String selectedFile = fileDialog.getDirectory() +
					fileDialog.getFile();
				
				// If the user did not select a file, we're done.
				if (fileDialog.getFile() == null) {
					return;
				}
								
				// Try to open the file and read a presentation from it.
				// If something goes wrong, pop up a dialog box.
				// If the open works, display the first slide.
				String err = theApp.presentation.openFile(selectedFile);
				if (err != null) {
					JOptionPane.showMessageDialog(theApp.theFrame,
						"Could not open " + err + ".");
					return;
				}
				theApp.goWindowMode();
			} else if (item.equals("Go to full screen mode")) {
				theApp.goFullScreenMode();
			}
		}
	}
	
	/**
	 * 
	 * Make the application react to keypresses.
	 *
	 */
	static class MyKeyListener extends KeyAdapter {
		/** The SlideForty app */
		protected SlideForty theApp;
		
		/**
		 * Make a KeyListener specialized for SlideForty
		 * @param s the instance of SlideForty that contains us
		 */
		public MyKeyListener(SlideForty s) {
			theApp = s;
		}
		
		/**
		 * Map keypresses to the functions that react to them.
		 */
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();
			if ((keyCode == KeyEvent.VK_UP)||
				(keyCode == KeyEvent.VK_LEFT)||
				(keyCode == KeyEvent.VK_KP_LEFT)||
				(keyCode == KeyEvent.VK_PAGE_UP)||
				(keyCode == KeyEvent.VK_B)) {
				theApp.terminal.back();
			} else if ((keyCode == KeyEvent.VK_DOWN)||
				(keyCode == KeyEvent.VK_RIGHT)||
				(keyCode == KeyEvent.VK_KP_RIGHT)||
				(keyCode == KeyEvent.VK_PAGE_DOWN)||
				(keyCode == KeyEvent.VK_SPACE)) {
				theApp.terminal.forward();
			} else if ((keyCode == KeyEvent.VK_ESCAPE)) {
				theApp.goWindowMode();
			}
		} 
	}
	
	/**
	 * I'm not smart enough to figure out how to specify
	 * a minimum window size in Java, so instead I'm
	 * using this hack: this class responds to resize
	 * events and if the size drops below the minimum
	 * required to draw the terminal, it resizes the
	 * window to that minimum size.
	 */
	static class MyComponentAdapter extends ComponentAdapter {
		
		/** Our SlideForty instance */
		protected SlideForty theApp;
		
		/**
		 * Make a ComponentAdapter specialized for SlideForty.
		 * 
		 * @param s - SlideForty instance that contains component
		 *            whose events we're responding to.
		 */
		public MyComponentAdapter(SlideForty s) {
			theApp = s;
		}
		
		/**
		 * React to resize events.  We know the dimensions of the minimum-
		 * sized terminal.  But it's difficult to determine the minimum
		 * frame size needed to contain this minimum-sized terminal because
		 * Java includes the size of the frame border decorations and the
		 * menu bar when we ask it how big the frame is.  Because we don't
		 * know the sizes of these decorations, we can't calculate how much
		 * space is left for the terminal.  So, we basically punt and
		 * multiply the terminal size by an arbitrary fraction to get a 
		 * good guess at the minimal frame size.
		 */
		public void componentResized(ComponentEvent e) {
			boolean needSetSize = false;    // set iff we need to correct size
			int minWidth = (int)((double)Terminal.twidth * 1.25);
			int minHeight = (int)((double)Terminal.theight * 1.5);
			Dimension dim = theApp.theFrame.getSize();
			if (dim.width < minWidth) {
				dim.width = minWidth;
				needSetSize = true;
			}
			if (dim.height < minHeight) {
				dim.height = minHeight;
				needSetSize = true;
			}
			if (needSetSize) {
				theApp.theFrame.setSize(dim);
			}
		}
	}
	
	/**
	 * Switches the display to window mode.
	 *
	 */
	protected void goWindowMode() {
		if (theFrame != null) {
			theFrame.setVisible(false);
			theFrame.dispose();
		}
		
		theFrame = new Frame("Slide40");
		theFrame.setMenuBar(menuBar);
		theFrame.setUndecorated(false);		
		terminal = new Terminal(presentation);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension sdim = toolkit.getScreenSize();
		theFrame.setSize(sdim.width / 2, sdim.height / 2);
		theFrame.add(terminal, BorderLayout.CENTER);
		menuBar = new MenuBar();
		FileMenu fileMenu = new FileMenu(this);
		menuBar.add(fileMenu);
		theFrame.setMenuBar(menuBar);
			
		theFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				exit();
			}
		});
		
		theFrame.addComponentListener(new MyComponentAdapter(this));
		
		theFrame.addKeyListener(new MyKeyListener(this));
		theFrame.setVisible(true);
	}
	
	/**
	 * Switches the display to full screen mode.  Full screen mode is
	 * actually an undecorated window the size of the screen.  It's
	 * not Java's real full screen mode.
	 *
	 */
	protected void goFullScreenMode() {
		if (theFrame != null) {
			theFrame.setVisible(false);
			theFrame.dispose();
		}
		
		theFrame = new Frame("Slide40");
		theFrame.setUndecorated(true);
		terminal = new Terminal(presentation);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension sdim = toolkit.getScreenSize();
		theFrame.setSize(sdim.width, sdim.height);
		theFrame.add(terminal, BorderLayout.CENTER);
		theFrame.addKeyListener(new MyKeyListener(this));
		theFrame.setVisible(true);
	}
	
	/**
	 * Creates a new SlideForty application displaying the built-in help
	 * presentation.
	 *
	 */
	public SlideForty() {
		presentation = new Presentation();
		goWindowMode();
	}

	/**
	 * Exit the SlideForty program.
	 *
	 */
	protected void exit() {
		theFrame.setVisible(false);
		theFrame.dispose();
		System.exit(0);		
	}
	
	/**
	 * The main function for the entire SlideForty application.
	 * 
	 * @param args command line arguments (unused)
	 */
	public static void main(String args[]) {
		SlideForty app = new SlideForty();
	}
}
