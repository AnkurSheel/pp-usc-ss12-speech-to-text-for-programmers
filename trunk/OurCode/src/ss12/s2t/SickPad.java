package ss12.s2t;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import java.io.*;

public class SickPad extends JFrame implements ActionListener {
	private JTextArea console = new JTextArea(1, 30);
	public JTextArea codeBox = new JTextArea(40,50);
	private JButton clickClickBoom = new JButton("Click Click Boom!");
	
	private int tagCount=0;
	private String indentString="";
	private boolean isLastInputText = false;
	
	private MenuBar menuBar = new MenuBar(); // first, create a MenuBar item
	private Menu file = new Menu(); // our File menu
	// what's going in File? let's see...
	private MenuItem openFile = new MenuItem();  // an open option
	private MenuItem saveFile = new MenuItem(); // a save option
	private MenuItem close = new MenuItem(); // and a close option!
	
	public SickPad() {
		this.setSize(800, 600); // set the initial size of the window
		this.setTitle("SickPad"); // set the title of the window
		setDefaultCloseOperation(EXIT_ON_CLOSE); // set the default close operation (exit when it gets closed)
		this.codeBox.setFont(new Font("Century Gothic", Font.BOLD, 12)); // set a default font for the TextArea
		// this is why we didn't have to worry about the size of the TextArea!
		//this.getContentPane().setLayout(new BorderLayout()); // the BorderLayout bit makes it fill it automatically
		this.getContentPane().setLayout(new FlowLayout());
		this.getContentPane().add(console);		
		clickClickBoom.addActionListener(this);
		this.getContentPane().add(clickClickBoom);
		this.getContentPane().add(codeBox);

		// add our menu bar into the GUI
		this.setMenuBar(this.menuBar);
		this.menuBar.add(this.file); // we'll configure this later
		
		// first off, the design of the menuBar itself. Pretty simple, all we need to do
		// is add a couple of menus, which will be populated later on
		this.file.setLabel("File");
		
		// now it's time to work with the menu. I'm only going to add a basic File menu
		// but you could add more!
		
		// now we can start working on the content of the menu~ this gets a little repetitive,
		// so please bare with me!
		
		// time for the repetitive stuff. let's add the "Open" option
		this.openFile.setLabel("Open"); // set the label of the menu item
		this.openFile.addActionListener(this); // add an action listener (so we know when it's been clicked
		this.openFile.setShortcut(new MenuShortcut(KeyEvent.VK_O, false)); // set a keyboard shortcut
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
		// 2) CTRL+F4, which we are about to define now: (this one will appear in the label)
		this.close.setShortcut(new MenuShortcut(KeyEvent.VK_F4, false));
		this.close.addActionListener(this);
		this.file.add(this.close);
	}
	
	public void actionPerformed (ActionEvent e) {
		// if the source of the event was our "close" option
		if (e.getSource() == this.close)
			this.dispose(); // dispose all resources and close the application
		
		// if the source was the "open" option
		else if (e.getSource() == this.openFile) {
			JFileChooser open = new JFileChooser(); // open up a file chooser (a dialog for the user to browse files to open)
			int option = open.showOpenDialog(this); // get the option that the user selected (approve or cancel)
			// NOTE: because we are OPENing a file, we call showOpenDialog~
			// if the user clicked OK, we have "APPROVE_OPTION"
			// so we want to open the file
			if (option == JFileChooser.APPROVE_OPTION) {
				this.codeBox.setText(""); // clear the TextArea before applying the file contents
				try {
					// create a scanner to read the file (getSelectedFile().getPath() will get the path to the file)
					Scanner scan = new Scanner(new FileReader(open.getSelectedFile().getPath()));
					while (scan.hasNext()) // while there's still something to read
						this.codeBox.append(scan.nextLine() + "\n"); // append the line to the TextArea
				} catch (Exception ex) { // catch any exceptions, and...
					// ...write to the debug console
					System.out.println(ex.getMessage());
				}
			} 	
		}
		
		// and lastly, if the source of the event was the "save" option
		else if (e.getSource() == this.saveFile) {
			JFileChooser save = new JFileChooser(); // again, open a file chooser
			int option = save.showSaveDialog(this); // similar to the open file, only this time we call
			// showSaveDialog instead of showOpenDialog
			// if the user clicked OK (and not cancel)
			if (option == JFileChooser.APPROVE_OPTION) {
				try {
					// create a buffered writer to write to a file
					BufferedWriter out = new BufferedWriter(new FileWriter(save.getSelectedFile().getPath()));
					out.write(this.codeBox.getText()); // write the contents of the TextArea to the file
					out.close(); // close the file stream
				} catch (Exception ex) { // again, catch any exceptions and...
					// ...write to the debug console
					System.out.println(ex.getMessage());
				}
			}
		}
		
		else if(e.getSource() == this.clickClickBoom)
		{
			String input=this.console.getText();
			//assuming input is tag only
			String typeCheck="";
			typeCheck = input.substring(0,4);
			
			if(typeCheck.equals("tag:")) //Check if it is a tag
			{
				this.codeBox.append(resolveTag(input.substring(4, input.length()),0));
				return;
			}
			else if(typeCheck.equals("end:")) //Check if it is a tag
			{
				this.codeBox.append(resolveTag("/"+input.substring(4, input.length()),1));
				return;
			}

			this.codeBox.append(resolveText(input));
			
		}
	}
	
	public void sound2Text(String input)
	{
		//assuming input is tag only
		String typeCheck="";
		if(input.length()>4)
		{
		typeCheck = input.substring(0,4);
		
		if(typeCheck.equals("tag ")) //Check if it is a tag
		{
			this.codeBox.append(resolveTag(input.substring(4, input.length()),0));
			return;
		}
		else if(typeCheck.equals("end ")) //Check if it is a tag
		{
			this.codeBox.append(resolveTag("/"+input.substring(4, input.length()),1));
			return;
		}
		}
		this.console.setText(input);
		this.codeBox.append(resolveText(input));
		
	}
	
	public String resolveText(String text)
	{
		if(this.isLastInputText == false)
		{
			text = indentString+text;
			this.isLastInputText = true;
		}
		else
		{
			text = " "+text;			
		}
		return text;
	}

	public String resolveTag(String text, int tagType) //0 for start, 1 for end
	{
		this.isLastInputText = false;
		text = "<"+text+">";
		
		if(tagType==0)
			text = indentString+text;

		indentString = "\n";
		if(tagType==0) tagCount++;
		else tagCount--;
		
		for(int i=0;i<tagCount;i++)
			indentString += "\t";
		
		if(tagType==1)
			text = indentString+text;
		
		return text;
	}
}
