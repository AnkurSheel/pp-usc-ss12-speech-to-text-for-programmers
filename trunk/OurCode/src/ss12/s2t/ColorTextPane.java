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
	Style heading2Style;
	Style mainStyle;
	Style defaultStyle;
	int TagCount;
	int LineNo;
	
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

		heading2Style = sc.addStyle("Heading2", defaultStyle);
		StyleConstants.setForeground(heading2Style, Color.blue);
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
			System.out.println(TagCount + " " + Value);
			int len = getDocument().getLength();
			setCaretPosition(len);
			setCharacterAttributes(heading2Style, false);
			replaceSelection(Value);
		} else {
			int len = getDocument().getLength();
			setCaretPosition(len);
			setCharacterAttributes(mainStyle, false);
			replaceSelection(Value);
		}
		if (key.equalsIgnoreCase("TAG")) {
			TagCount++;
		
		}

	}

	public void Reset() {
		TagCount = 0;
		LineNo = 0;
	}
	// StyleContext sc = StyleContext.getDefaultStyleContext();
	// AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY,
	// StyleConstants.Foreground, c);
	//
	// int len = getDocument().getLength();
	// setCaretPosition(len);
	// setCharacterAttributes(aset, false);
	// replaceSelection(s);
}
