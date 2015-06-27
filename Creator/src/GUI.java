import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JSpinner;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;


public class GUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		setTitle("Creator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 766, 662);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNew = new JButton("New");
		btnNew.setBounds(10, 11, 89, 23);
		contentPane.add(btnNew);
		
		JButton btnOpen = new JButton("Open");
		btnOpen.setBounds(109, 11, 89, 23);
		contentPane.add(btnOpen);
		
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(208, 11, 89, 23);
		contentPane.add(btnSave);
		
		JLabel lblNewLabel = new JLabel("Name");
		lblNewLabel.setBounds(10, 48, 46, 14);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(58, 45, 332, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblAuthor = new JLabel("Author");
		lblAuthor.setBounds(10, 76, 46, 14);
		contentPane.add(lblAuthor);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(58, 73, 332, 20);
		contentPane.add(textField_1);
		
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		textArea.setBounds(58, 150, 551, 70);
		contentPane.add(textArea);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		textArea_1.setBounds(58, 231, 551, 70);
		contentPane.add(textArea_1);
		
		JTextArea textArea_2 = new JTextArea();
		textArea_2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		textArea_2.setBounds(58, 312, 551, 70);
		contentPane.add(textArea_2);
		
		JTextArea textArea_3 = new JTextArea();
		textArea_3.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		textArea_3.setBounds(58, 393, 551, 70);
		contentPane.add(textArea_3);
		
		JLabel lblContent = new JLabel("Content");
		lblContent.setBounds(10, 176, 46, 14);
		contentPane.add(lblContent);
		
		JLabel lblA = new JLabel("A");
		lblA.setBounds(34, 260, 12, 14);
		contentPane.add(lblA);
		
		JLabel lblB = new JLabel("B");
		lblB.setBounds(34, 340, 12, 14);
		contentPane.add(lblB);
		
		JLabel lblC = new JLabel("C");
		lblC.setBounds(34, 422, 12, 14);
		contentPane.add(lblC);
		
		JButton btnNewButton = new JButton("|<");
		btnNewButton.setBounds(58, 490, 89, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("<");
		btnNewButton_1.setBounds(157, 490, 89, 23);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton(">");
		btnNewButton_2.setBounds(326, 490, 89, 23);
		contentPane.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton(">|");
		btnNewButton_3.setBounds(425, 490, 89, 23);
		contentPane.add(btnNewButton_3);
		
		JSpinner spinner = new JSpinner();
		spinner.setBounds(256, 491, 29, 20);
		contentPane.add(spinner);
		
		JLabel lblNewLabel_2 = new JLabel("/ 30");
		lblNewLabel_2.setBounds(295, 494, 46, 14);
		contentPane.add(lblNewLabel_2);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setBounds(520, 490, 89, 23);
		contentPane.add(btnAdd);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Correct");
		buttonGroup.add(rdbtnNewRadioButton);
		rdbtnNewRadioButton.setBounds(615, 256, 109, 23);
		contentPane.add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Correct");
		buttonGroup.add(rdbtnNewRadioButton_1);
		rdbtnNewRadioButton_1.setBounds(615, 336, 109, 23);
		contentPane.add(rdbtnNewRadioButton_1);
		
		JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("Correct\r\n");
		buttonGroup.add(rdbtnNewRadioButton_2);
		rdbtnNewRadioButton_2.setBounds(615, 418, 109, 23);
		contentPane.add(rdbtnNewRadioButton_2);
	}
}
