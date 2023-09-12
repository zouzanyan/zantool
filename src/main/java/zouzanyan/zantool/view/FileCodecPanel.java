package zouzanyan.zantool.view;

import javax.swing.JPanel;

import org.bouncycastle.util.encoders.Hex;

import zouzanyan.zantool.service.ProgressCallback;
import zouzanyan.zantool.util.CipherUtil;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Arrays;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.JLabel;
import javax.swing.JFormattedTextField;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import java.awt.Color;

public class FileCodecPanel extends JPanel {
	private JTextField textField;
	private JTextField textField_2;
	private JPasswordField passwordField;

	/**
	 * Create the panel.
	 */
	public FileCodecPanel() {
		initialize();
	}

	private void initialize() {

		setLayout(null);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setBounds(10, 455, 753, 21);
		add(progressBar);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setForeground(Color.RED);
		lblNewLabel_1.setBounds(10, 430, 332, 15);
		add(lblNewLabel_1);

		JLabel lblNewLabel = new JLabel("文件路径:");
		lblNewLabel.setBounds(10, 40, 66, 15);
		add(lblNewLabel);

		textField = new JTextField();
		textField.setBounds(70, 37, 476, 21);
		add(textField);
		textField.setColumns(10);

		JButton btnNewButton = new JButton("选择文件");
		// 强迫症关闭按钮上面的虚线框
		btnNewButton.setFocusable(false);
		btnNewButton.setBounds(563, 36, 93, 23);

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
//                fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int result = fileChooser.showOpenDialog(FileCodecPanel.this);
				if (result == JFileChooser.APPROVE_OPTION) {
					String filePath = fileChooser.getSelectedFile().getAbsolutePath();
					textField.setText(filePath);
					textField_2.setText(fileChooser.getSelectedFile().getParent());
				}
			}
		});
		this.add(btnNewButton);

		JLabel label = new JLabel("密钥:");
		label.setBounds(10, 96, 54, 15);
		add(label);
		passwordField = new JPasswordField();
		passwordField.setBounds(70, 93, 476, 21);
		add(passwordField);

		JLabel label_1 = new JLabel("输出目录:");
		label_1.setBounds(10, 154, 54, 15);
		add(label_1);
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(70, 151, 476, 21);
		add(textField_2);
		JButton btnNewButton_2 = new JButton("选择目录");
		btnNewButton_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int result = fileChooser.showOpenDialog(FileCodecPanel.this);
				if (result == JFileChooser.APPROVE_OPTION) {
					String selectedDirectory = fileChooser.getSelectedFile().getAbsolutePath();
					textField_2.setText(selectedDirectory);
				}

			}
		});
		btnNewButton_2.setFocusable(false);
		btnNewButton_2.setBounds(563, 150, 93, 23);
		add(btnNewButton_2);

		JButton btnNewButton_1 = new JButton("加密");
		JButton btnNewButton_3 = new JButton("解密");

		btnNewButton_1.setBounds(71, 208, 93, 23);
		btnNewButton_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				btnNewButton_1.setEnabled(false);
				btnNewButton_3.setEnabled(false);
				lblNewLabel_1.setText("加密运行中...");
				SwingWorker<Void, Void> swingWorker = new SwingWorker<Void, Void>() {
					@Override
					protected Void doInBackground() throws Exception {
						File file = new File(textField.getText());
						long totalBytes = file.length();
						// 把大数映射到100以内
						double divide = (double) totalBytes / 100;
						progressBar.setMaximum(100);

						try {
							CipherUtil.encryptFile(textField.getText(), textField_2.getText(),
									new String(passwordField.getPassword()), new ProgressCallback() {

										@Override
										public void progressUpdate(long progress) {
											progressBar.setValue((int) Math.round(progress / divide));
										}
									});

						} catch (Exception e2) {
							lblNewLabel_1.setText("加密失败，密钥应该为128,192或256位");
							e2.printStackTrace();
							return null;
						}
						lblNewLabel_1.setText("加密完成");
						return null;
					}

					@Override
					protected void done() {
						btnNewButton_1.setEnabled(true);
						btnNewButton_3.setEnabled(true);
					}
				};
				swingWorker.execute();
			}
		});
		add(btnNewButton_1);

		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnNewButton_3.setEnabled(false);
				btnNewButton_1.setEnabled(false);
				lblNewLabel_1.setText("解密运行中...");

				SwingWorker<Void, Void> swingWorker = new SwingWorker<Void, Void>() {
					@Override
					protected Void doInBackground() throws Exception {
						File file = new File(textField.getText());
						long totalBytes = file.length();
						// 把大数映射到100以内
						double divide = (double) totalBytes / 100;

						progressBar.setMaximum(100);

						try {
							CipherUtil.decryptFile(textField.getText(), textField_2.getText(),
									new String(passwordField.getPassword()), new ProgressCallback() {
										@Override
										public void progressUpdate(long progress) {
											// 四舍五入
											progressBar.setValue((int) Math.round(progress / divide));
										}
									});
						} catch (Exception e) {
							lblNewLabel_1.setText("解密失败，密钥错误");
							e.printStackTrace();
							return null;
						}
						lblNewLabel_1.setText("解密完成");
						return null;
					}

					@Override
					protected void done() {
						btnNewButton_1.setEnabled(true);
						btnNewButton_3.setEnabled(true);
					}
				};
				swingWorker.execute();
			}
		});
		btnNewButton_3.setBounds(214, 208, 93, 23);
		add(btnNewButton_3);

		JButton button = new JButton("打开目录");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop desktop = Desktop.getDesktop();
					File file = new File(textField_2.getText());
					desktop.open(file);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		});
		button.setBounds(670, 420, 93, 23);
		add(button);

	}
}
