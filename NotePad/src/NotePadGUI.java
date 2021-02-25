import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

public class NotePadGUI implements ActionListener {

	JFrame frame = new JFrame();
	JTextArea tArea = new JTextArea(20, 20);
	Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	boolean saved=false;
	File file;

	public NotePadGUI() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setTitle("Notepad");

		JMenuBar mBar = new JMenuBar();
		JMenu menu1 = new JMenu("FILE");
		JMenu menu2 = new JMenu("EDIT");
		JMenu menu3 = new JMenu("HELP");
		JMenuItem item1 = new JMenuItem("Open");
		JMenuItem item2 = new JMenuItem("Close");
		JMenuItem item3 = new JMenuItem("Save As");
		JMenuItem item4 = new JMenuItem("Copy");
		JMenuItem item5 = new JMenuItem("Cut");
		JMenuItem item6 = new JMenuItem("Paste");
		JMenuItem item7 = new JMenuItem("Select All");
		JMenuItem item8 = new JMenuItem("About");
		JMenuItem itemSave = new JMenuItem("Save");
		KeyStroke ctrlS = KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
		item3.setAccelerator(ctrlS);
		KeyStroke ctrlSp = KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
		itemSave.setAccelerator(ctrlSp);

		item1.setActionCommand("open");
		item1.addActionListener(this);
		item2.setActionCommand("close");
		item2.addActionListener(this);
		item3.setActionCommand("save as");
		item3.addActionListener(this);
		item4.setActionCommand("Copy");
		item4.addActionListener(this);
		item5.setActionCommand("cut");
		item5.addActionListener(this);
		item6.setActionCommand("paste");
		item6.addActionListener(this);
		item7.setActionCommand("select");
		item7.addActionListener(this);
		item8.setActionCommand("about");
		item8.addActionListener(this);
		itemSave.setActionCommand("save");
		itemSave.addActionListener(this);

		menu1.setFont(new Font("Ubuntu", Font.BOLD, 14));
		menu2.setFont(new Font("Ubuntu", Font.BOLD, 14));
		menu3.setFont(new Font("Ubuntu", Font.BOLD, 14));
		menu1.add(item1);
		menu1.add(item3);
		menu1.add(itemSave);
		menu1.add(item2);
		menu2.add(item7);
		menu2.add(item5);
		menu2.add(item4);
		menu2.add(item6);
		menu3.add(item8);
		mBar.add(menu1);
		mBar.add(menu2);
		mBar.add(menu3);

		tArea.setFont(new Font("consolas", Font.LAYOUT_LEFT_TO_RIGHT, 20));
		tArea.setBackground(new Color(255, 255, 255));
		JScrollPane scrollableTextArea = new JScrollPane(tArea);

		scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		frame.getContentPane().add(scrollableTextArea);
		frame.setJMenuBar(mBar);

		frame.setSize(dimension);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals("close"))
			frame.dispose();
		else if (e.getActionCommand().equals("open")) {
			JFileChooser chooser = new JFileChooser();
			int op = chooser.showOpenDialog(frame);
			File file = null;
			if (op == JFileChooser.APPROVE_OPTION) {
				file = chooser.getSelectedFile();
				tArea.setText("");
			}
			try {
				@SuppressWarnings("resource")
				BufferedReader in = new BufferedReader(new FileReader(file));
				String line = in.readLine();
				while (line != null) {
					tArea.append(line + "\n");
					line = in.readLine();
				}
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (Exception e1) {
			}
		}

		else if (e.getActionCommand().equals("save as")) {
			saveAs();
		}

		else if (e.getActionCommand().equals("cut"))
			tArea.cut();
		else if (e.getActionCommand().equals("copy"))
			tArea.copy();
		else if (e.getActionCommand().equals("paste"))
			tArea.paste();
		else if (e.getActionCommand().equals("select"))
			tArea.selectAll();
		else if (e.getActionCommand().equals("about")) {
			JDialog box = new JDialog(frame, "About");
			JLabel label = new JLabel(
					"<html>This is a simple Notepad.<br>This Notepad enables you to save, create new files and open saved files.<br>Made with ❤  by Vamsi");
			label.setFont(new Font("Times New Roman", Font.ITALIC, 16));
			box.add(label);

			box.setLayout(new FlowLayout());
			box.setSize(565, 120);
			box.setVisible(true);
			int x = (dimension.width - 565) / 2;
			int y = (dimension.height - 120) / 2;
			box.setLocation(x, y);
		}
		else if(e.getActionCommand().equals("save")) {
			if(saved!=true)
				saveAs();
			else {
				try {
					FileWriter fw1= new FileWriter(file, false);
					tArea.write(fw1);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
				
		}
		
	}
	
	public void saveAs() {
		JFileChooser chooser2 = new JFileChooser();
		int op = chooser2.showSaveDialog(frame);
		if (op == JFileChooser.APPROVE_OPTION) {
			try {
				file=chooser2.getSelectedFile().getAbsoluteFile();
				FileWriter fw = new FileWriter(file, true);
				tArea.write(fw);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			saved=true;
		}
	}
}