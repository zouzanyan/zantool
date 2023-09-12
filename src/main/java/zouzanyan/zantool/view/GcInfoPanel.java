package zouzanyan.zantool.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import javax.swing.Timer;

public class GcInfoPanel extends JPanel {
	private JProgressBar progressBar;

	/**
	 * Create the panel.
	 */
	public GcInfoPanel() {
		setLayout(null);

		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setBounds(0, 0, 240, 30);
		add(progressBar);

		progressBar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.gc();
            }
        });
		setVisible(true);
		
		SwingWorker<Void, Void> swingWorker = new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {
				startMemoryMonitoring();
				return null;
			}
			
		};
		swingWorker.execute();
	}
	private void startMemoryMonitoring() {
        Timer timer = new Timer(1000, e -> {
            long totalMemory = Runtime.getRuntime().totalMemory();
            long freeMemory = Runtime.getRuntime().freeMemory();
            long usedMemory = totalMemory - freeMemory;

            int usedMemoryPercentage = (int) (usedMemory * 100 / totalMemory);
            progressBar.setValue(usedMemoryPercentage);

            String usedMemoryStr = formatMemorySize(usedMemory);
            String totalMemoryStr = formatMemorySize(totalMemory);

            progressBar.setString("Used: " + usedMemoryStr + " / Total: " + totalMemoryStr);
        });
        timer.start();
    }

    private String formatMemorySize(long bytes) {
        if (bytes < 1024) {
            return bytes + " B";
        } else if (bytes < 1024 * 1024) {
            double kb = bytes / 1024.0;
            return new DecimalFormat("#.##").format(kb) + " KB";
        } else {
            double mb = bytes / (1024.0 * 1024);
            return new DecimalFormat("#.##").format(mb) + " MB";
        }
    }

}
