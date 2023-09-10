package zouzanyan.zantool.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JDialog;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainWindow {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
			        // 设置外观为操作系统默认
			        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu menu = new JMenu("菜单");
		menuBar.add(menu);
		
		JMenu menu_1 = new JMenu("关于");
		menuBar.add(menu_1);
		
		JMenuItem menuItem = new JMenuItem("作者");
		menuItem.setSelected(true);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new AuthorDetailDialog();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
				dialog.setLocationRelativeTo(frame);
			}
		});
		
		JMenuItem menuItem_1 = new JMenuItem("帮助");
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		menu_1.add(menuItem_1);
		menu_1.add(menuItem);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFocusable(false);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel = new FileCodecPanel();
		tabbedPane.addTab("AES-ECB 加解密", null, panel, null);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_1, null);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_2, null);
	}
}
