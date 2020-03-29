package moreredoc.application;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MoreRedocGuiStarter {
	private static JTextField textField;
	private static JTextField textField_1;
	private static JTextField textField_2;
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		 UIManager.setLookAndFeel(
		            UIManager.getSystemLookAndFeelClassName());
		
		// Creating the Frame
		JFrame frame = new JFrame("MoreRedoc");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 400);
		
		JPanel panel = new JPanel();
		
		Border border = BorderFactory.createDashedBorder(Color.BLACK);
		Border margin = new EmptyBorder(10, 10, 10, 10);
		panel.setBorder(new CompoundBorder(border, margin));
		
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblLabel = new JLabel("Keywords:");
		GridBagConstraints gbc_lblLabel = new GridBagConstraints();
		gbc_lblLabel.anchor = GridBagConstraints.WEST;
		gbc_lblLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblLabel.gridx = 0;
		gbc_lblLabel.gridy = 0;
		panel.add(lblLabel, gbc_lblLabel);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		panel.add(textField, gbc_textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Choose");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.WEST;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 2;
		gbc_btnNewButton.gridy = 0;
		panel.add(btnNewButton, gbc_btnNewButton);
		
		JLabel lblLabel_1 = new JLabel("Text:");
		lblLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblLabel_1 = new GridBagConstraints();
		gbc_lblLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblLabel_1.gridx = 0;
		gbc_lblLabel_1.gridy = 1;
		panel.add(lblLabel_1, gbc_lblLabel_1);
		
		textField_1 = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 1;
		panel.add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("Choose");
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.anchor = GridBagConstraints.WEST;
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_1.gridx = 2;
		gbc_btnNewButton_1.gridy = 1;
		panel.add(btnNewButton_1, gbc_btnNewButton_1);
		
		JLabel lblLabel_2 = new JLabel("Output folder:");
		GridBagConstraints gbc_lblLabel_2 = new GridBagConstraints();
		gbc_lblLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblLabel_2.gridx = 0;
		gbc_lblLabel_2.gridy = 2;
		panel.add(lblLabel_2, gbc_lblLabel_2);
		
		textField_2 = new JTextField();
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(0, 0, 5, 5);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 1;
		gbc_textField_2.gridy = 2;
		panel.add(textField_2, gbc_textField_2);
		textField_2.setColumns(10);
		
		JButton btnNewButton_2 = new JButton("Choose");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.anchor = GridBagConstraints.WEST;
		gbc_btnNewButton_2.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_2.gridx = 2;
		gbc_btnNewButton_2.gridy = 2;
		panel.add(btnNewButton_2, gbc_btnNewButton_2);
		
		JLabel lblSettings = new JLabel("Settings:");
		GridBagConstraints gbc_lblSettings = new GridBagConstraints();
		gbc_lblSettings.anchor = GridBagConstraints.WEST;
		gbc_lblSettings.insets = new Insets(0, 0, 5, 5);
		gbc_lblSettings.gridx = 0;
		gbc_lblSettings.gridy = 4;
		panel.add(lblSettings, gbc_lblSettings);
		
		JCheckBox chckbxNewCheckBox_3 = new JCheckBox("New check box");
		GridBagConstraints gbc_chckbxNewCheckBox_3 = new GridBagConstraints();
		gbc_chckbxNewCheckBox_3.anchor = GridBagConstraints.WEST;
		gbc_chckbxNewCheckBox_3.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxNewCheckBox_3.gridx = 1;
		gbc_chckbxNewCheckBox_3.gridy = 4;
		panel.add(chckbxNewCheckBox_3, gbc_chckbxNewCheckBox_3);
		
		JCheckBox chckbxNewCheckBox_4 = new JCheckBox("New check box");
		GridBagConstraints gbc_chckbxNewCheckBox_4 = new GridBagConstraints();
		gbc_chckbxNewCheckBox_4.anchor = GridBagConstraints.WEST;
		gbc_chckbxNewCheckBox_4.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxNewCheckBox_4.gridx = 1;
		gbc_chckbxNewCheckBox_4.gridy = 5;
		panel.add(chckbxNewCheckBox_4, gbc_chckbxNewCheckBox_4);
		
		JLabel lblOutput = new JLabel("Output:");
		GridBagConstraints gbc_lblOutput = new GridBagConstraints();
		gbc_lblOutput.anchor = GridBagConstraints.WEST;
		gbc_lblOutput.insets = new Insets(0, 0, 5, 5);
		gbc_lblOutput.gridx = 0;
		gbc_lblOutput.gridy = 7;
		panel.add(lblOutput, gbc_lblOutput);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("Raw XMI");
		GridBagConstraints gbc_chckbxNewCheckBox = new GridBagConstraints();
		gbc_chckbxNewCheckBox.anchor = GridBagConstraints.WEST;
		gbc_chckbxNewCheckBox.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxNewCheckBox.gridx = 1;
		gbc_chckbxNewCheckBox.gridy = 7;
		panel.add(chckbxNewCheckBox, gbc_chckbxNewCheckBox);
		
		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("XMI (ArgoUML)");
		GridBagConstraints gbc_chckbxNewCheckBox_1 = new GridBagConstraints();
		gbc_chckbxNewCheckBox_1.anchor = GridBagConstraints.WEST;
		gbc_chckbxNewCheckBox_1.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxNewCheckBox_1.gridx = 1;
		gbc_chckbxNewCheckBox_1.gridy = 8;
		panel.add(chckbxNewCheckBox_1, gbc_chckbxNewCheckBox_1);
		
		JCheckBox chckbxNewCheckBox_2 = new JCheckBox("XMI (StarUML)");
		GridBagConstraints gbc_chckbxNewCheckBox_2 = new GridBagConstraints();
		gbc_chckbxNewCheckBox_2.anchor = GridBagConstraints.WEST;
		gbc_chckbxNewCheckBox_2.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxNewCheckBox_2.gridx = 1;
		gbc_chckbxNewCheckBox_2.gridy = 9;
		panel.add(chckbxNewCheckBox_2, gbc_chckbxNewCheckBox_2);
		
		JButton btnGenerateModels = new JButton("Run");
		btnGenerateModels.setVerticalAlignment(SwingConstants.BOTTOM);
		btnGenerateModels.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_btnGenerateModels = new GridBagConstraints();
		gbc_btnGenerateModels.insets = new Insets(0, 0, 5, 0);
		gbc_btnGenerateModels.gridx = 2;
		gbc_btnGenerateModels.gridy = 11;
		panel.add(btnGenerateModels, gbc_btnGenerateModels);

		
		
		frame.setVisible(true);
	}
}
