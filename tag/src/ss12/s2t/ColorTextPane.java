package ss12.s2t;

import javax.swing.JTextPane;
import java.awt.Color;

import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class ColorTextPane extends JTextPane {

	StyleContext sc;
	Style tagStyle;
	Style mainStyle;
	Style defaultStyle;
	Style linkStyle;
	int TagCount;
	int LineNo;
	int ThemeNo;
	
	public ColorTextPane() {
		super();
		// TODO Auto-generated constructor stub
		sc = new StyleContext();
		defaultStyle = sc.getStyle(StyleContext.DEFAULT_STYLE);

		mainStyle = sc.addStyle("mainStyle", null);
		StyleConstants.setForeground(mainStyle, Color.black);
		// StyleConstants.setFontSize(mainStyle, 16);
		// StyleConstants.setFontFamily(mainStyle, "serif");
		// StyleConstants.setBold(mainStyle, true);
		// StyleConstants.setLeftIndent(mainStyle, 8);
		// StyleConstants.setFirstLineIndent(mainStyle, 0);

		tagStyle = sc.addStyle("Heading2", defaultStyle);
		StyleConstants.setForeground(tagStyle, Color.blue);

		linkStyle = sc.addStyle("link", defaultStyle);
		StyleConstants.setForeground(linkStyle, Color.red);
		ThemeNo = 0;	
		TagCount = 0;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void append(String key, String Value) {
		if (key.equalsIgnoreCase("TAG") || key.equalsIgnoreCase("END")) {
			LineNo++;
			// StyleContext sc = StyleContext.getDefaultStyleContext();
			// AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY,
			// StyleConstants.Foreground, Color.BLUE);
			if (key.equalsIgnoreCase("END")) {
				TagCount--;
			}

			String indentString = "\r\n"+LineNo;
			for (int i = 0; i < TagCount; i++) {
				indentString += "\t";
			}
			Value = indentString + Value;
			//System.out.println(TagCount + " " + Value);
			int len = getDocument().getLength();
			setCaretPosition(len);
			setCharacterAttributes(tagStyle, false);
			replaceSelection(Value);
		} else if(key.equalsIgnoreCase("LINK"))
		{
			LineNo++;
			String indentString = "\r\n"+LineNo;
			for (int i = 0; i < TagCount; i++) {
				indentString += "\t";
			}
			Value = indentString + Value;
			//System.out.println(TagCount + " " + Value);
			int len = getDocument().getLength();
			setCaretPosition(len);
			setCharacterAttributes(linkStyle, false);
			replaceSelection(Value);
		}
		else{
			int len = getDocument().getLength();
			setCaretPosition(len);
			setCharacterAttributes(mainStyle, false);
			replaceSelection(Value);
		}
		if (key.equalsIgnoreCase("TAG") || key.equalsIgnoreCase("LINK")) {
			TagCount++;
		
		}

	}

	public void Reset() {
		TagCount = 0;
		LineNo = 0;
	}
	
	public void ChangeTheme1()
	{
		ThemeNo = (ThemeNo+1)%3;
		switch(ThemeNo)
		{
		case 0:
			setBackground(Color.white);
			StyleConstants.setForeground(mainStyle, Color.black);
			StyleConstants.setForeground(tagStyle, Color.blue);
			StyleConstants.setForeground(linkStyle, Color.red);
			break;
		case 1:
			setBackground(Color.blue);
			StyleConstants.setForeground(mainStyle, Color.white);
			StyleConstants.setForeground(tagStyle, Color.orange);
			StyleConstants.setForeground(linkStyle, Color.MAGENTA);
			break;
		case 2:
			setBackground(Color.black);
			StyleConstants.setForeground(mainStyle, Color.orange);
			StyleConstants.setForeground(tagStyle, Color.red);
			StyleConstants.setForeground(linkStyle, Color.white);
			break;
		}
			
		//StyleConstants.setBackground(heading2Style, Color.blue);
		
	}
}
