package zouzanyan.zantool.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class AuthorDetailDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		try {
//			AuthorDetailDialog dialog = new AuthorDetailDialog();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public AuthorDetailDialog() {
		setBounds(100, 100, 450, 300);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel();
			lblNewLabel.setBounds(193, 10, 71, 46);
			contentPanel.add(lblNewLabel);
			{
				JLabel lblZouzanyan = new JLabel("zouzanyan");
				lblZouzanyan.setHorizontalAlignment(SwingConstants.CENTER);
				lblZouzanyan.setFont(UIManager.getFont("Label.font"));
				lblZouzanyan.setBounds(182, 66, 71, 15);
				contentPanel.add(lblZouzanyan);
			}
			{
				JLabel lblGithubcomzouzanyan = new JLabel("github.com/zouzanyan");
				lblGithubcomzouzanyan.setBounds(160, 91, 134, 15);
				contentPanel.add(lblGithubcomzouzanyan);
			}
			{
				JLabel lblQq = new JLabel("qq1406823510");
				lblQq.setBounds(160, 116, 134, 15);
				contentPanel.add(lblQq);
			}

			SwingWorker<Void, Void> swingWorker = new SwingWorker<Void, Void>(){

				@Override
				protected Void doInBackground() throws Exception {
					try {
						// 加载网络图像
						URL imageUrl = new URL("https://avatars.githubusercontent.com/zouzanyan");
						ImageIcon imageIcon = new ImageIcon(imageUrl);
						Image image = imageIcon.getImage();

						// 缩放图像大小
						Image scaledImage = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);

						// 设置缩放后的图像到标签
						lblNewLabel.setIcon(new ImageIcon(scaledImage));
					} catch (Exception e) {
						e.printStackTrace();
					}
				
					return null;
				}
			};
			swingWorker.execute();
		}
		
		
		{
			JPanel jPanel = new JPanel();
			jPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(jPanel, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				jPanel.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						dispose();
					}
				});
				jPanel.add(cancelButton);
			}
		}
	}

}
