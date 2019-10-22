package components;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;

/*
 * FileChooserDemo.java uses these files:
 *   images/Open16.gif
 *   images/Save16.gif
 */
public class FileRenameUI extends JPanel implements ActionListener {
	static private final String newline = "\n";
	JButton folderButton, fileButton, renameButton;
	JRadioButton copyToggle, renameToggle;
	JTextArea log;
	JFileChooser dirChooser;
	JFileChooser fileChooser;
	String folderPath, filePath;
	boolean copy = true;

	public FileRenameUI() {
		super(new BorderLayout());

		// Create the log first, because the action listeners
		// need to refer to it.
		log = new JTextArea(5, 20);
		log.setMargin(new Insets(5, 5, 5, 5));
		log.setEditable(false);
		JScrollPane logScrollPane = new JScrollPane(log);

		// Create a file chooser
		dirChooser = new JFileChooser();
		fileChooser = new JFileChooser();

		// Uncomment one of the following lines to try a different
		// file selection mode. The first allows just directories
		// to be selected (and, at least in the Java look and feel,
		// shown). The second allows both files and directories
		// to be selected. If you leave these lines commented out,
		// then the default mode (FILES_ONLY) will be used.
		//
		dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		// fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		// Create the open button.
		folderButton = new JButton("Picture Directory Path: ", createImageIcon("images/Open16.gif"));
		folderButton.addActionListener(this);

		// Create the save button.
		fileButton = new JButton("Name File Path:", createImageIcon("images/Save16.gif"));
		fileButton.addActionListener(this);

		// Create the rename button.
		renameButton = new JButton("Rename");
		renameButton.addActionListener(this);

		// Radio Buttons for renaming options
		copy = true;
		copyToggle = new JRadioButton("Copy");
		copyToggle.setSelected(true);
		renameToggle = new JRadioButton("Rename");
		ButtonGroup copyOptions = new ButtonGroup();
		copyOptions.add(copyToggle);
		copyOptions.add(renameToggle);
		copyToggle.addActionListener(this);
		renameToggle.addActionListener(this);

		// For layout purposes, put the buttons in a separate panel
		JPanel buttonPanel = new JPanel(); // use FlowLayout
		buttonPanel.add(folderButton);
		buttonPanel.add(fileButton);
		buttonPanel.add(renameButton);

		JPanel optionsPanel = new JPanel();
		optionsPanel.add(copyToggle);
		optionsPanel.add(renameToggle);

		JPanel pathPanel = new JPanel();
		pathPanel.setLayout(new BoxLayout(pathPanel, BoxLayout.PAGE_AXIS));

		// Add the buttons and the log to this panel.
		add(buttonPanel, BorderLayout.PAGE_START);
		add(optionsPanel, BorderLayout.LINE_START);
		add(logScrollPane, BorderLayout.AFTER_LAST_LINE);
	}

	public void actionPerformed(ActionEvent e) {

		// Handle directory button action.
		if (e.getSource() == folderButton) {
			int returnVal = dirChooser.showOpenDialog(FileRenameUI.this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = dirChooser.getSelectedFile();
				folderPath = file.getAbsolutePath();
				System.out.println(file.getAbsolutePath());
				// This is where a real application would open the file.
				log.append("Directory located! " + file.getAbsolutePath() + "." + newline);
				log.append("Please make sure there are no hidden files in the directory" + newline);
			} else {
				log.append("Directory location cancelled by user." + newline);
			}
			log.setCaretPosition(log.getDocument().getLength());

			// Handle Naming file button action.
		} else if (e.getSource() == fileButton) {
			int returnVal = fileChooser.showSaveDialog(FileRenameUI.this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				filePath = file.getAbsolutePath();
				System.out.println(file.getAbsolutePath());
				// This is where a real application would save the file.
				log.append("File found! " + file.getAbsolutePath() + "." + newline);
			} else {
				log.append("File location cancelled by user." + newline);
			}
			log.setCaretPosition(log.getDocument().getLength());
		}

		else if (e.getSource() == renameButton) {
			File nameCSV = new File(filePath);
			File photoDir = new File(folderPath);
			File[] fileList = photoDir.listFiles();
			Scanner input;
			try {
				input = new Scanner(nameCSV);
				ArrayList<String> names = NameFormatter.Renamer(input);

				log.append("Renaming...");
				// Renamer.renameAll(names, fileList);

				Arrays.sort(fileList);
				int skipFile = 0;
				String copyText = "Renaming: ";
				if(copy) {
					copyText = "Copying: ";
					
				}
				for (int i = 0; i < fileList.length && i < names.size() + skipFile - 1; i++) {
					if (fileList[i].isHidden()) {
						skipFile++;
						log.append("Skipped file" + fileList[i].getName());
					} else {
						log.append(copyText + fileList[i].getName() + newline);
						Renamer.rename(names.get(i - skipFile + 1), fileList[i], false);
					}

				}
				log.append("Rename complete" + newline);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} else if (e.getSource() == copyToggle) {
			copy = true;
		} else if (e.getSource() == renameToggle) {
			copy = false;
		}
	}

	/** Returns an ImageIcon, or null if the path was invalid. */
	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = FileRenameUI.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be invoked
	 * from the event dispatch thread.
	 */
	private static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("File Rename Utility");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Add content to the window.
		frame.add(new FileRenameUI());

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		// Schedule a job for the event dispatch thread:
		// creating and showing this application's GUI.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Turn off metal's use of bold fonts
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				createAndShowGUI();
			}
		});
	}
}
