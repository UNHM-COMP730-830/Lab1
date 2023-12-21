import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class DbApp3 {

	private JFrame frame;
	private JTextField txtDbName;
	private JTextField txtCsvFile;
	private JComboBox<String> comboBoxList;
	private JTextField txtTpid;
	private JTextField txtLastName;
	private JTextField txtFirstName;
	private JTextField txtAge;
	private JTextField txtCountry;
	private JTextField txtOrganization;
	private JTextField txtRanking;
	private JTextField txtPoints;
	private JButton btnClear;
	private JButton btnFind;
	private JButton btnInsert;
	private JButton btnUpdate;
	private JButton btnDelete;
	private JLabel lblImage;
	private int imgNum = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DbApp3 window = new DbApp3();
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
	public DbApp3() {
		initialize();
	}
	
	public String[] getTextFieldValues() {
		String[] str = new String[SqliteDb.TPF_COUNT];
		str[SqliteDb.TPF_TPID] = txtTpid.getText();
		str[SqliteDb.TPF_LASTNAME] = txtLastName.getText(); 
		str[SqliteDb.TPF_FIRSTNAME] = txtFirstName.getText();
		str[SqliteDb.TPF_AGE] = txtAge.getText();
		str[SqliteDb.TPF_COUNTRY] = txtCountry.getText();
		str[SqliteDb.TPF_ORGANIZATION] = txtOrganization.getText();
		str[SqliteDb.TPF_RANKING] = txtRanking.getText();
		str[SqliteDb.TPF_POINTS] = txtPoints.getText(); 
		return str;
	}
	
	public void setTextFieldValues(String[] flist) {
		txtTpid.setText(flist[SqliteDb.TPF_TPID]);
		txtLastName.setText(flist[SqliteDb.TPF_LASTNAME]);
		txtFirstName.setText(flist[SqliteDb.TPF_FIRSTNAME]);
		txtAge.setText(flist[SqliteDb.TPF_AGE]);
		txtCountry.setText(flist[SqliteDb.TPF_COUNTRY]);
		txtOrganization.setText(flist[SqliteDb.TPF_ORGANIZATION]);
		txtRanking.setText(flist[SqliteDb.TPF_RANKING]);
		txtPoints.setText(flist[SqliteDb.TPF_POINTS]);
	}
	
	public void updateComboBoxList() {
		comboBoxList.removeAllItems();
		String[] list = SqliteDb.getList();
		for (String s : list) comboBoxList.addItem(s);
	}

	public class ImageThread extends Thread {
		public void run() {
			while (true) {
				try {
					ImageIcon img = null;
					sleep(10000); // 10,000 ms
					switch (imgNum) {
					case 0: img = new ImageIcon("../Lab1_Files/tennis_player_2_100x139.jpg"); break;
					case 1: img = new ImageIcon("../Lab1_Files/tennis_player_3_100x137.jpg"); break;
					case 2: img = new ImageIcon("../Lab1_Files/tennis_player_1_100x138.jpg"); break;
					}
					lblImage.setIcon(img);
					imgNum += 1;
					if (imgNum > 2) imgNum = 0;
				}
				catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 600, 408);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblDbName = new JLabel("SQLite DB Name");
		lblDbName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDbName.setBounds(129, 11, 110, 24);
		frame.getContentPane().add(lblDbName);
		
		JLabel lblCsvFile = new JLabel("CSV Data File");
		lblCsvFile.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCsvFile.setBounds(129, 42, 110, 24);
		frame.getContentPane().add(lblCsvFile);
		
		txtDbName = new JTextField();
		txtDbName.setText("sqlite1.db");
		txtDbName.setFont(new Font("Consolas", Font.PLAIN, 14));
		txtDbName.setBounds(243, 15, 300, 24);
		frame.getContentPane().add(txtDbName);
		txtDbName.setColumns(10);
		
		txtCsvFile = new JTextField();
		txtCsvFile.setText("../Lab1_Files/tennis_players.csv");
		txtCsvFile.setFont(new Font("Consolas", Font.PLAIN, 14));
		txtCsvFile.setBounds(243, 46, 300, 24);
		frame.getContentPane().add(txtCsvFile);
		txtCsvFile.setColumns(10);
		
		JButton btnInit = new JButton("Init");
		btnInit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SqliteDb.setDb(txtDbName.getText());
				SqliteDb.initialize(txtCsvFile.getText());
				updateComboBoxList();
			}
		});
		btnInit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnInit.setBounds(33, 42, 70, 24);
		frame.getContentPane().add(btnInit);
		
		JButton btnLoad = new JButton("Load");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SqliteDb.setDb(txtDbName.getText());
				updateComboBoxList();
			}
		});
		btnLoad.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnLoad.setBounds(33, 11, 70, 24);
		frame.getContentPane().add(btnLoad);
		
		comboBoxList = new JComboBox<String>();
		comboBoxList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selected = (String) comboBoxList.getSelectedItem();
				if (selected == null) return;
				String[] item = selected.split("[:]", 0);
				ArrayList<String[]> vlist = SqliteDb.find(SqliteDb.TPF_TPID, item[0]);
				setTextFieldValues(vlist.get(0));
			}
		});
		comboBoxList.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBoxList.setBounds(33, 80, 178, 24);
		frame.getContentPane().add(comboBoxList);
		
		JLabel lblTpid = new JLabel("Tpid");
		lblTpid.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTpid.setBounds(33, 115, 90, 24);
		frame.getContentPane().add(lblTpid);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLastName.setBounds(33, 145, 90, 24);
		frame.getContentPane().add(lblLastName);
		
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFirstName.setBounds(33, 175, 90, 24);
		frame.getContentPane().add(lblFirstName);
		
		JLabel lblAge = new JLabel("Age");
		lblAge.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAge.setBounds(33, 205, 90, 24);
		frame.getContentPane().add(lblAge);
		
		JLabel lblCountry = new JLabel("Country");
		lblCountry.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCountry.setBounds(33, 235, 90, 24);
		frame.getContentPane().add(lblCountry);
		
		JLabel lblOrganization = new JLabel("Organization");
		lblOrganization.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblOrganization.setBounds(33, 265, 90, 24);
		frame.getContentPane().add(lblOrganization);
		
		JLabel lblRanking = new JLabel("Ranking");
		lblRanking.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRanking.setBounds(33, 295, 90, 24);
		frame.getContentPane().add(lblRanking);
		
		JLabel lblPoints = new JLabel("Points");
		lblPoints.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPoints.setBounds(33, 325, 90, 24);
		frame.getContentPane().add(lblPoints);
		
		txtTpid = new JTextField();
		txtTpid.setFont(new Font("Consolas", Font.PLAIN, 14));
		txtTpid.setColumns(10);
		txtTpid.setBounds(123, 115, 200, 24);
		frame.getContentPane().add(txtTpid);
		
		txtLastName = new JTextField();
		txtLastName.setFont(new Font("Consolas", Font.PLAIN, 14));
		txtLastName.setColumns(10);
		txtLastName.setBounds(123, 145, 200, 24);
		frame.getContentPane().add(txtLastName);
		
		txtFirstName = new JTextField();
		txtFirstName.setFont(new Font("Consolas", Font.PLAIN, 14));
		txtFirstName.setColumns(10);
		txtFirstName.setBounds(123, 175, 200, 24);
		frame.getContentPane().add(txtFirstName);
		
		txtAge = new JTextField();
		txtAge.setFont(new Font("Consolas", Font.PLAIN, 14));
		txtAge.setColumns(10);
		txtAge.setBounds(123, 205, 200, 24);
		frame.getContentPane().add(txtAge);
		
		txtCountry = new JTextField();
		txtCountry.setFont(new Font("Consolas", Font.PLAIN, 14));
		txtCountry.setColumns(10);
		txtCountry.setBounds(123, 235, 200, 24);
		frame.getContentPane().add(txtCountry);
		
		txtOrganization = new JTextField();
		txtOrganization.setFont(new Font("Consolas", Font.PLAIN, 14));
		txtOrganization.setColumns(10);
		txtOrganization.setBounds(123, 265, 200, 24);
		frame.getContentPane().add(txtOrganization);
		
		txtRanking = new JTextField();
		txtRanking.setFont(new Font("Consolas", Font.PLAIN, 14));
		txtRanking.setColumns(10);
		txtRanking.setBounds(123, 295, 200, 24);
		frame.getContentPane().add(txtRanking);
		
		txtPoints = new JTextField();
		txtPoints.setFont(new Font("Consolas", Font.PLAIN, 14));
		txtPoints.setColumns(10);
		txtPoints.setBounds(123, 325, 200, 24);
		frame.getContentPane().add(txtPoints);
		
		btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] flist = {"", "", "", "", "", "", "", ""};
				setTextFieldValues(flist);
			}
		});
		btnClear.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnClear.setBounds(344, 115, 80, 24);
		frame.getContentPane().add(btnClear);
		
		btnFind = new JButton("Find");
		btnFind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] flist = getTextFieldValues();	
//				String str = "";
//				for (String s : flist) str += "<" + s + "> ";
//				JOptionPane.showMessageDialog(null, "+++ " + str);
				ArrayList<String[]> vlist = null;
				for (int i = 0; i < SqliteDb.TPF_COUNT; i++) {
					if (flist[i].length() == 0) continue;
					vlist = SqliteDb.find(i,  flist[i]);
					break;
				}
				if (vlist.size() == 0) {
					JOptionPane.showMessageDialog(null, "No Data Found");
					return;
				}
				Object[] options = {"Yes", "No"};
				for (int i = 0; i < vlist.size(); i++) {
					setTextFieldValues(vlist.get(i));
					if (vlist.size() > 1) {
						int x = JOptionPane.showOptionDialog(null,
									"Next Data?",
									"Choose Yes/No",
									JOptionPane.YES_NO_OPTION,
									JOptionPane.QUESTION_MESSAGE,
									null,
									options,
									options[0]);
						if (x == 1) break;
					}
				}	
			}
		});
		btnFind.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnFind.setBounds(220, 80, 80, 24);
		frame.getContentPane().add(btnFind);
		
		btnInsert = new JButton("Insert");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] flist = getTextFieldValues();
				SqliteDb.insert(flist);
				updateComboBoxList();
			}
		});
		btnInsert.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnInsert.setBounds(310, 80, 80, 24);
		frame.getContentPane().add(btnInsert);
		
		btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] flist = getTextFieldValues();
				SqliteDb.update(flist);
				updateComboBoxList();
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnUpdate.setBounds(400, 80, 80, 24);
		frame.getContentPane().add(btnUpdate);
		
		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SqliteDb.delete(txtTpid.getText());
				updateComboBoxList();
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnDelete.setBounds(490, 80, 80, 24);
		frame.getContentPane().add(btnDelete);
		
//		lblImage = new JLabel("Image");
//		lblImage.setFont(new Font("Tahoma", Font.PLAIN, 14));
//		lblImage.setBounds(400, 175, 100, 140);
//		frame.getContentPane().add(lblImage);


		lblImage = new JLabel("Image");
		lblImage.setBounds(419, 205, 100, 140);
		ImageIcon img = new ImageIcon("../Lab1_Files/tennis_player_3_100x137.jpg");
		lblImage.setIcon(img);
		frame.getContentPane().add(lblImage);
		imgNum = 0;
		
		ImageThread it = new ImageThread();
		it.start();
		
	}
}
