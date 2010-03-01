package ss12.s2t;

//ankur
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class SickPad extends JFrame implements ActionListener {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	private final String TAG = "open ";
	private final String COMMAND = "command ";
	private final String END = "finish ";
	private final String CAPITAL = "capital ";
	private final String SPACE = "command space";
	private final String LINK = "link";

	private JTextArea console = new JTextArea(1, 30);
	// public JTextArea codeBox = new JTextArea(40, 50);
	// public JTextPane codeBox = new JTextPane();
	private JPanel pan = new JPanel();
	public ColorTextPane codeBox = new ColorTextPane();
	public JScrollPane scrollPane = new JScrollPane(codeBox);
	private JButton clickClickBoom = new JButton("Click Click Boom!");

	private boolean isLastInputText = true;
	private boolean isSelectMode = false;
	private int selectStart = 0;
	private int selectEnd = 0;

	// NEW DATA
	ArrayList<DevStruct> StringRecords = new ArrayList<DevStruct>();
	int cursorPosition = 0;

	private MenuBar menuBar = new MenuBar(); // first, create a MenuBar item
	private Menu file = new Menu(); // our File menu
	// what's going in File? let's see...
	private MenuItem openFile = new MenuItem(); // an open option
	private MenuItem saveFile = new MenuItem(); // a save option
	private MenuItem close = new MenuItem(); // and a close option!

	public SickPad() {
		this.setSize(800, 600); // set the initial size of the window
		this.setTitle("SickPad"); // set the title of the window
		setDefaultCloseOperation(EXIT_ON_CLOSE); // set the default close
		// operation (exit when it
		// gets closed)
		this.codeBox.setFont(new Font("Century Gothic", Font.BOLD, 12));
		scrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		clickClickBoom.addActionListener(this);

		codeBox.setAutoscrolls(true);
		console.setBackground(Color.GRAY);
		console.setForeground(Color.WHITE);

		pan.setLayout(new BorderLayout());
		pan.add(console, BorderLayout.CENTER);
		pan.add(clickClickBoom, BorderLayout.LINE_END);

		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().setBounds(0, 0, 800, 600);
		this.getContentPane().setBackground(Color.GRAY);
		this.getContentPane().add(scrollPane, BorderLayout.CENTER);
		this.getContentPane().add(pan, BorderLayout.PAGE_END);

		// add our menu bar into the GUI
		this.setMenuBar(this.menuBar);
		this.menuBar.add(this.file); // we'll configure this later

		// first off, the design of the menuBar itself. Pretty simple, all we
		// need to do
		// is add a couple of menus, which will be populated later on
		this.file.setLabel("File");

		// now it's time to work with the menu. I'm only going to add a basic
		// File menu
		// but you could add more!

		// now we can start working on the content of the menu~ this gets a
		// little repetitive,
		// so please bare with me!

		// time for the repetitive stuff. let's add the "Open" option
		this.openFile.setLabel("Open"); // set the label of the menu item
		this.openFile.addActionListener(this); // add an action listener (so we
		// know when it's been clicked
		this.openFile.setShortcut(new MenuShortcut(KeyEvent.VK_O, false)); // set
		// a
		// keyboard
		// shortcut
		this.file.add(this.openFile); // add it to the "File" menu

		// and the save...
		this.saveFile.setLabel("Save");
		this.saveFile.addActionListener(this);
		this.saveFile.setShortcut(new MenuShortcut(KeyEvent.VK_S, false));
		this.file.add(this.saveFile);

		// and finally, the close option
		this.close.setLabel("Close");
		// along with our "CTRL+F4" shortcut to close the window, we also have
		// the default closer, as stated at the beginning of this tutorial.
		// this means that we actually have TWO shortcuts to close:
		// 1) the default close operation (example, Alt+F4 on Windows)
		// 2) CTRL+F4, which we are about to define now: (this one will appear
		// in the label)
		this.close.setShortcut(new MenuShortcut(KeyEvent.VK_F4, false));
		this.close.addActionListener(this);
		this.file.add(this.close);
	}

	public void actionPerformed(ActionEvent e) {
		// if the source of the event was our "close" option
		if (e.getSource() == this.close)
			this.dispose(); // dispose all resources and close the application

		// if the source was the "open" option
		else if (e.getSource() == this.openFile) {
			JFileChooser open = new JFileChooser(); // open up a file chooser (a
			// dialog for the user to
			// browse files to open)
			int option = open.showOpenDialog(this); // get the option that the
			// user selected (approve or
			// cancel)
			// NOTE: because we are OPENing a file, we call showOpenDialog~
			// if the user clicked OK, we have "APPROVE_OPTION"
			// so we want to open the file
			if (option == JFileChooser.APPROVE_OPTION) {
				this.codeBox.setText(""); // clear the TextArea before applying
				// the file contents
				try {
					// create a scanner to read the file
					// (getSelectedFile().getPath() will get the path to the
					// file)
					Scanner scan = new Scanner(new FileReader(open
							.getSelectedFile().getPath()));
					while (scan.hasNext())
						// while there's still something to read
						this.codeBox.setText(this.codeBox.getText()
								+ scan.nextLine() + "\n");
				} catch (Exception ex) { // catch any exceptions, and...
					// ...write to the debug console
					System.out.println(ex.getMessage());
				}
			}
		}

		// and lastly, if the source of the event was the "save" option
		else if (e.getSource() == this.saveFile) {
			JFileChooser save = new JFileChooser(); // again, open a file
			// chooser
			int option = save.showSaveDialog(this); // similar to the open file,
			// only this time we call
			// showSaveDialog instead of showOpenDialog
			// if the user clicked OK (and not cancel)
			if (option == JFileChooser.APPROVE_OPTION) {
				try {
					// create a buffered writer to write to a file
					BufferedWriter out = new BufferedWriter(new FileWriter(save
							.getSelectedFile().getPath()));
					out.write(this.codeBox.getText()); // write the contents of
					// the TextArea to the
					// file
					out.close(); // close the file stream
				} catch (Exception ex) { // again, catch any exceptions and...
					// ...write to the debug console
					System.out.println(ex.getMessage());
				}
			}
		}

		else if (e.getSource() == this.clickClickBoom) {
			String input = this.console.getText();
			processRawText(input);

			displayText();
		}
	}

	public void displayText() {
		codeBox.setText("");
		codeBox.Reset();
		for (int i = 0; i < StringRecords.size(); i++) {
			codeBox
					.append(StringRecords.get(i).key,
							StringRecords.get(i).value);
		}
		if (cursorPosition > 1) {
			codeBox.setCaretPosition(cursorPosition);
		}

		if (this.isSelectMode == true) {
			if (this.selectStart < this.codeBox.getCaretPosition()) {
				this.codeBox.select(this.selectStart, this.codeBox
						.getCaretPosition());
			} else {
				this.codeBox.select(this.codeBox.getCaretPosition(),
						this.selectStart);
			}
		}

	}

	public void processRawText(String input) {

		this.transferFocus();

		String typeCheck = "";
		// check for tags
		if (input.length() > TAG.length()) {
			typeCheck = input.substring(0, TAG.length());
			if (typeCheck.equals(TAG)) // Check if it is a tag
			{
				// Processing Hyperlinks
				// eg: TAG link project possibility my link
				StringTokenizer st = new StringTokenizer(input);
				String strLink = st.nextToken();
				strLink = st.nextToken();
				if (strLink.equalsIgnoreCase("link")) {
					strLink = resolveHref(input);
					this.StringRecords.add(new DevStruct("LINK", strLink));
					return;
				}
				
				String text = input.substring(TAG.length(), input.length());
				if (text.equals("new")) {
					text = "html";
				} else if (text.equals("ordered")) {
					text = "ol";
				} else if (text.equals("item")) {
					text = "li";
				} else if (text.equals("paragraph")) {
					text = "p";
				} else if (text.equals("break")) {
					text = "br /";
				} else if (text.equals("row")) {
					text = "tr";
				} else if (text.equals("cell")) {
					text = "td";
				}
				this.StringRecords
						.add(new DevStruct("TAG", resolveTag(text, 0)));
				return;
			}
		}
		if (input.length() > END.length()) {
			typeCheck = input.substring(0, END.length());
			if (typeCheck.equals(END)) // Check if it is a tag
			{
				String text = input.substring(END.length(), input.length());
				if (text.equals("new")) {
					text = "html";
				} else if (text.equals("ordered")) {
					text = "ol";
				} else if (text.equals("item")) {
					text = "li";
				} else if (text.equals("paragraph")) {
					text = "p";
				} else if (text.equals("break")) {
					text = "br /";
					this.StringRecords.add(new DevStruct("END", resolveTag(
							text, 1)));
					return;
				} else if (text.equals("row")) {
					text = "tr";
				} else if (text.equals("cell")) {
					text = "td";
				}

				this.StringRecords.add(new DevStruct("END", resolveTag("/"
						+ text, 1)));
				return;
			}
		}

		if (input.length() > CAPITAL.length()) {
			typeCheck = input.substring(0, CAPITAL.length());

			if (typeCheck.equals(CAPITAL)) {
				this.StringRecords.add(new DevStruct("TXT", resolveText(input
						.substring(CAPITAL.length()).toUpperCase())));
				return;
			}
		}

		if (input.equals(SPACE)) {
			this.StringRecords.add(new DevStruct("TXT", resolveText(" ")));
			return;
		}

		// check for command
		if (input.length() > COMMAND.length()) {
			typeCheck = input.substring(0, COMMAND.length());

			if (typeCheck.equals(COMMAND)) // Check if it is a tag
			{
				resolveCommand(input.substring(COMMAND.length()));
				return;
			}
		}
		this.StringRecords.add(new DevStruct("TXT", resolveText(input)));
	}

	public void sound2Text(String input) {
		processRawText(input);
		displayText();
	}

	public void resolveCommand(String cmd) {
		if (cmd.equals("up")) {

		} else if (cmd.equals("down")) {

		} else if (cmd.equals("left")) {
			displayText();
			if (codeBox.getCaretPosition() > 1) {
				cursorPosition = codeBox.getCaretPosition() - 1;
				codeBox.setCaretPosition(cursorPosition);
			}
		} else if (cmd.equals("right")) {
			displayText();
			if (cursorPosition < codeBox.getDocument().getLength() - 1) {
				cursorPosition = codeBox.getCaretPosition() + 1;
			}
			codeBox.setCaretPosition(cursorPosition);
		} else if (cmd.equals("select")) {
			if (this.isSelectMode == false) {
				this.isSelectMode = true;
				this.selectStart = this.codeBox.getCaretPosition();
			} else
				this.isSelectMode = false;
		} else if (cmd.equals("clear")) {
			this.codeBox.setText("");
			this.StringRecords.clear();
		} else if (cmd.equals("back")) {
			if (StringRecords.size() > 0) {
				this.StringRecords.remove(StringRecords.size() - 1);
				cursorPosition = codeBox.getCaretPosition();
			}

		} else if (cmd.equals("delete")) {
			String text = this.codeBox.getText();
			int index = text.lastIndexOf('\r');
			if (index > -1) {
				text = text.substring(0, index);
			} else {
				text = "";
			}
			this.codeBox.setText(text);
		} else if (cmd.equals("open")) {
			JFileChooser open = new JFileChooser(); // open up a file chooser (a
			// dialog for the user to
			// browse files to open)
			int option = open.showOpenDialog(this); // get the option that the
			// user selected (approve or
			// cancel)
			// NOTE: because we are OPENing a file, we call showOpenDialog~
			// if the user clicked OK, we have "APPROVE_OPTION"
			// so we want to open the file
			if (option == JFileChooser.APPROVE_OPTION) {
				this.codeBox.setText(""); // clear the TextArea before applying
				// the file contents
				try {
					// create a scanner to read the file
					// (getSelectedFile().getPath() will get the path to the
					// file)
					Scanner scan = new Scanner(new FileReader(open
							.getSelectedFile().getPath()));
					while (scan.hasNext())
						// while there's still something to read
						this.codeBox.setText(this.codeBox.getText()
								+ scan.nextLine() + "\n");
				} catch (Exception ex) { // catch any exceptions, and...
					// ...write to the debug console
					System.out.println(ex.getMessage());
				}
			}

		} else if (cmd.equals("save")) {
			JFileChooser save = new JFileChooser(); // again, open a file
			// chooser
			int option = save.showSaveDialog(this); // similar to the open file,
			// only this time we call
			// showSaveDialog instead of showOpenDialog
			// if the user clicked OK (and not cancel)
			if (option == JFileChooser.APPROVE_OPTION) {
				try {
					// create a buffered writer to write to a file
					BufferedWriter out = new BufferedWriter(new FileWriter(save
							.getSelectedFile().getPath()));
					String outputText = "";
					for(int i=0;i<this.StringRecords.size();i++)
					{
					outputText+=StringRecords.get(i).value;	
					}
					out.write(outputText);
					//out.write(this.codeBox.getText()); // write the contents of
					// the TextArea to the
					// file
					out.close(); // close the file stream
				} catch (Exception ex) { // again, catch any exceptions and...
					// ...write to the debug console
					System.out.println(ex.getMessage());
				}
			}
		} else if (cmd.equals("exit")) {
			System.exit(1);
		} else if (cmd.equals("flip")) {
			codeBox.ChangeTheme1();
		}
	}

	public String resolveText(String text) {
		if (this.isLastInputText == false) {
			this.isLastInputText = true;
		} else {
			if (text.length() > 1) {
				text = text + " ";
			}
		}
		return text;
	}

	public String resolveTag(String text, int tagType) // 0 for start, 1 for end
	{
		this.isLastInputText = false;
		text = "<" + text + ">";
		return text;
	}

	private String resolveHref(String text) {
		StringTokenizer st = new StringTokenizer(text);
		st.nextToken();
		if (st.nextToken().equalsIgnoreCase("link")) {
			String link = st.nextToken();
			String strLink = "";
			String strTxt = "";
			if (link.equalsIgnoreCase("yahoo")) {
				strLink = "www.yahoo.com";
				strTxt = hrefHelper(st.nextToken());
			} else if (link.equalsIgnoreCase("google")) {
				strLink = "www.google.com";
				strTxt = hrefHelper(st.nextToken());
			} else if (link.equalsIgnoreCase("you")) {
				strLink = "www.youtube.com";
				st.nextToken();
				strTxt = hrefHelper(st.nextToken());
			} else if (link.equalsIgnoreCase("project")) {
				strLink = "www.projectpossibility.org";
				st.nextToken();
				strTxt = hrefHelper(st.nextToken());
			}
			text = "<a href=\"" + strLink + "\">" + strTxt + "</a>";
		}
		return text;
	}

	private String hrefHelper(String txt) {
		String strTxt = "";
		if (txt.equalsIgnoreCase("my")) {
			strTxt = "mylink";
		} else if (txt.equalsIgnoreCase("website")) {
			strTxt = "website";
		}
		return strTxt;
	}
}
