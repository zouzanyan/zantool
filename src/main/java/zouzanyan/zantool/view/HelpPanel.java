package zouzanyan.zantool.view;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JTextArea;

public class HelpPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public HelpPanel() {
		setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(10, 10, 430, 280);
		add(textArea);

	}
}
