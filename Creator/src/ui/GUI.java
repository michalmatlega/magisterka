package ui;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import model.QBase;
import model.Question;


public class GUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private final JRadioButton rdbtn_corrA = new JRadioButton("Correct");
	private final JRadioButton rdbtn_corrB = new JRadioButton("Correct");
	private final JRadioButton rdbtn_corrC = new JRadioButton("Correct");
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	
	private QBase qbase = new QBase("anonymous","example");
	private int currQ = 0;
	
	JTextArea textArea_content = new JTextArea();
	JTextArea textArea_varA = new JTextArea();
	JTextArea textArea_varB = new JTextArea();
	JTextArea textArea_varC = new JTextArea();
	JSpinner spinner = new JSpinner();

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
	
	public void renderQuestion(){
		Question q = qbase.getQuestions().get(this.currQ);
		textArea_content.setText(q.getContent());
		textArea_varA.setText(q.getVarA().getContent());
		textArea_varB.setText(q.getVarB().getContent());
		textArea_varC.setText(q.getVarC().getContent());
		//this.currQ = _currQ;
		//dodajemy dla spinnera zawsze 1, zeby nie zaczynac od 0 pytania na HUDzie
		spinner.setValue(this.currQ+1);
		
		switch(q.getCorrect()){
			case 1:
				rdbtn_corrA.setSelected(true);
				break;
			case 2:
				rdbtn_corrB.setSelected(true);
				break;
			case 3:
				rdbtn_corrC.setSelected(true);
				
				
		}
		
	}
	
	public void nextQuestion(){
		if(this.currQ == qbase.getQuestions().size()-1){
			return;
		}else{
			this.currQ++;
			renderQuestion();
		}
	}
	
	public void previousQuestion(){
		if(this.currQ == 0){
			return;
		}else{
			this.currQ--;
			renderQuestion();
		}
	}
	
	public void firstQuestion(){
		this.currQ = 0;
		renderQuestion();
	}
	
	public void lastQuestion(){
		this.currQ = qbase.getQuestions().size()-1;
		renderQuestion();
	}
	
	public void addQuestion(){
		qbase.addQuestion();
		this.currQ = qbase.getQuestions().size()-1;
		renderQuestion();
	}
	
	public void removeQuestion(){
		if(qbase.getQuestions().size()-1 > 0){		//jesli mamy wiecej niz 1 pytanie
			if(this.currQ == qbase.getQuestions().size()-1){	//jesli jestesmy na ostatnim pytaniu
				this.currQ--;
				qbase.removeQuestion(this.currQ+1);	
				renderQuestion();
				return;
			}
			
			qbase.removeQuestion(this.currQ);	
			
			renderQuestion();
		}else{
			return;			//moze dodamy tutaj kiedys czyszczenie pytania
		}
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
		
		//JTextArea textArea_content = new JTextArea();
		textArea_content.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		textArea_content.setBounds(58, 150, 551, 70);
		textArea_content.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				
				
				
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				qbase.getQuestions().get(currQ).setContent(textArea_content.getText());
				//System.out.println("Added character to" + currQ);
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				qbase.getQuestions().get(currQ).setContent(textArea_content.getText());
				//System.out.println("Removed character from" + currQ);
				
			}
			
		});
		contentPane.add(textArea_content);
		
		//JTextArea textArea_varA = new JTextArea();
		textArea_varA.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		textArea_varA.setBounds(58, 231, 551, 70);
		textArea_varA.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				
				
				
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				qbase.getQuestions().get(currQ).getVarA().setContent(textArea_varA.getText());
				//System.out.println("Added character to" + currQ);
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				qbase.getQuestions().get(currQ).getVarA().setContent(textArea_varA.getText());
				//System.out.println("Removed character from" + currQ);
				
			}
			
		});
		contentPane.add(textArea_varA);
		
		//JTextArea textArea_varB = new JTextArea();
		textArea_varB.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		textArea_varB.setBounds(58, 312, 551, 70);
		textArea_varB.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				
				
				
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				qbase.getQuestions().get(currQ).getVarB().setContent(textArea_varB.getText());
				//System.out.println("Added character to" + currQ);
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				qbase.getQuestions().get(currQ).getVarB().setContent(textArea_varB.getText());
				//System.out.println("Removed character from" + currQ);
				
			}
			
		});
		contentPane.add(textArea_varB);
		
		//JTextArea textArea_varC = new JTextArea();
		textArea_varC.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		textArea_varC.setBounds(58, 393, 551, 70);
		textArea_varC.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				
				
				
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				qbase.getQuestions().get(currQ).getVarC().setContent(textArea_varC.getText());
				//System.out.println("Added character to" + currQ);
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				qbase.getQuestions().get(currQ).getVarC().setContent(textArea_varC.getText());
				//System.out.println("Removed character from" + currQ);
				
			}
			
		});
		contentPane.add(textArea_varC);
		
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
		
		JButton btn_first = new JButton("|<");
		btn_first.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				firstQuestion();
			}
		});
		btn_first.setBounds(58, 490, 89, 23);
		contentPane.add(btn_first);
		
		JButton btn_prev = new JButton("<");
		btn_prev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				previousQuestion();
			}
		});
		btn_prev.setBounds(157, 490, 89, 23);
		contentPane.add(btn_prev);
		
		JButton btn_next = new JButton(">");
		btn_next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nextQuestion();
			}
		});
		btn_next.setBounds(326, 490, 89, 23);
		contentPane.add(btn_next);
		
		JButton btn_last = new JButton(">|");
		btn_last.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lastQuestion();
			}
		});
		btn_last.setBounds(425, 490, 89, 23);
		contentPane.add(btn_last);
		
		//JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spinner.setBounds(249, 491, 38, 20);
		contentPane.add(spinner);
		
		final JLabel lblqsize = new JLabel("/ 1");
		lblqsize.setBounds(295, 494, 46, 14);
		contentPane.add(lblqsize);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addQuestion();
				String str = "/ " + Integer.toString(qbase.getQuestions().size());
				lblqsize.setText(str);
				
			}
		});
		btnAdd.setBounds(520, 490, 89, 23);
		contentPane.add(btnAdd);
		
		
		buttonGroup.add(rdbtn_corrA);
		rdbtn_corrA.setBounds(615, 256, 109, 23);
		rdbtn_corrA.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				qbase.getQuestions().get(currQ).setCorrect(1);
			}
			
		});
		contentPane.add(rdbtn_corrA);
		
		
		buttonGroup.add(rdbtn_corrB);
		rdbtn_corrB.setBounds(615, 336, 109, 23);
		rdbtn_corrB.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				qbase.getQuestions().get(currQ).setCorrect(2);
			}
			
		});
		contentPane.add(rdbtn_corrB);
		
		
		buttonGroup.add(rdbtn_corrC);
		rdbtn_corrC.setBounds(615, 418, 109, 23);
		rdbtn_corrC.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				qbase.getQuestions().get(currQ).setCorrect(3);
			}
			
		});
		contentPane.add(rdbtn_corrC);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			removeQuestion();
				String str = "/ " + Integer.toString(qbase.getQuestions().size());
				lblqsize.setText(str);
			}
		});
		btnRemove.setBounds(619, 490, 89, 23);
		contentPane.add(btnRemove);
		
		this.renderQuestion();
	}
}