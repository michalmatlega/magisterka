package ui;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
//import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import model.OutputDocument;
import model.QBase;
import model.Question;

import com.itextpdf.text.DocumentException;





public class GUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField_name;
	private JTextField textField_author;
	private final JRadioButton rdbtn_corrA = new JRadioButton("Correct");
	private final JRadioButton rdbtn_corrB = new JRadioButton("Correct");
	private final JRadioButton rdbtn_corrC = new JRadioButton("Correct");
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	private final JLabel lblqsize = new JLabel("/ 1");
	
	private JFileChooser fc = new JFileChooser("C:\\Users\\Miszelek\\Desktop");
	
	
	private QBase qBase;
	private int currQ;
	
	private JTextArea textArea_content = new JTextArea();
	private JTextArea textArea_varA = new JTextArea();
	private JTextArea textArea_varB = new JTextArea();
	private JTextArea textArea_varC = new JTextArea();
	
	private ImagePanel imagePanel;
	private ImagePanel imagePanelA;
	private ImagePanel imagePanelB;
	private ImagePanel imagePanelC;
	
	private File currFile;
	private boolean saved = false;
	private boolean newBase = true;
	
	private JLabel label_current;
	private JSpinner spinner;

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
		Question q = qBase.getQuestions().get(this.currQ);
		textArea_content.setLineWrap(true);
		textArea_content.setText(q.getContent());
		textArea_varA.setLineWrap(true);
		textArea_varA.setText(q.getVarA().getContent());
		textArea_varB.setLineWrap(true);
		textArea_varB.setText(q.getVarB().getContent());
		textArea_varC.setLineWrap(true);
		textArea_varC.setText(q.getVarC().getContent());
		//this.currQ = _currQ;
		//dodajemy dla spinnera zawsze 1, zeby nie zaczynac od 0 pytania na HUDzie
		//spinner.setValue(this.currQ+1);
		label_current.setText(Integer.toString(this.currQ+1));
		
		this.imagePanel.setImage(q.getImg());
		this.imagePanel.resizeImage();
		
		this.imagePanelA.setImage(q.getVarA().getImg());
		this.imagePanelA.resizeImage();
		
		this.imagePanelB.setImage(q.getVarB().getImg());
		this.imagePanelB.resizeImage();
		
		this.imagePanelC.setImage(q.getVarC().getImg());
		this.imagePanelC.resizeImage();
		
		if(q.getVarA().isCorrect()){
			rdbtn_corrA.setSelected(true);
		}
		
		if(q.getVarB().isCorrect()){
			rdbtn_corrB.setSelected(true);
		}
		
		if(q.getVarC().isCorrect()){
			rdbtn_corrC.setSelected(true);
		}
		
	}
	
	public void notSaved(){
		this.saved = false;
		if(this.newBase == false){
			this.setTitle(currFile.getName() + "* - Creator");
		}else{
			this.setTitle("NewBase* - Creator");
		}
	}
	
	public void saved(){
		this.saved = true;
		this.newBase = false;
		this.setTitle(currFile.getName() + " - Creator");
	}
	
	public void renderAuthorName(){
		textField_name.setText(qBase.getName());
		textField_author.setText(qBase.getAuthor());
	}
	
	public void nextQuestion(){
		if(this.currQ == qBase.getQuestions().size()-1){
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
		this.currQ = qBase.getQuestions().size()-1;
		renderQuestion();
	}
	
	public void jumpTo(int val){
		val--;
		if(val >= qBase.getQuestions().size()){
			return;
		}
		
		this.currQ = val;
		renderQuestion();
	}
	
	public void addQuestion(){
		qBase.addQuestion();
		this.currQ = qBase.getQuestions().size()-1;
		setQBaseSize();
		renderQuestion();
		this.notSaved();
	}
	
	public void removeQuestion(){
		if(qBase.getQuestions().size()-1 > 0){		//jesli mamy wiecej niz 1 pytanie
			if(this.currQ == qBase.getQuestions().size()-1){	//jesli jestesmy na ostatnim pytaniu
				this.currQ--;
				qBase.removeQuestion(this.currQ+1);	
				setQBaseSize();
				renderQuestion();
				return;
			}
			
			qBase.removeQuestion(this.currQ);	
			setQBaseSize();
			renderQuestion();
			this.notSaved();
		}else{
			return;			//moze dodamy tutaj kiedys czyszczenie pytania
		}
	}
	
	public void newBase(){
		this.qBase = new QBase("QuestionBase","Anonymous");
		this.currQ = 0;
		this.currFile = null;
		this.newBase = true;
		notSaved();
		renderAuthorName();
		setQBaseSize();
		renderQuestion();
	}
	
	public void setQBaseSize(){
		String str = "/ " + Integer.toString(qBase.getQuestions().size());
		this.lblqsize.setText(str);
		
	}
	
	public String getFormat(String imageName)
	{
		String extension = "";

		int i = imageName.lastIndexOf('.');
		if (i > 0) {
		    extension = imageName.substring(i+1);
		}
		
		switch(extension)
	    {
	        case "png": return "PNG";
	        case "gif": return "GIF";
	        case "tiff": return "TIFF";
	        case "jpg": return "JPG";
	        case "jpeg": return "JPEG";
	    }

	    return "UNKNOWN";
	}
	
	public void setImg(int choose) throws IOException{		//choose: 0 - content, 1 - varA, 2 - varB, 3 - varC
		int returnVal = fc.showSaveDialog(GUI.this);
		
		 File file;
		 
		 if (returnVal == JFileChooser.APPROVE_OPTION) {
               file = fc.getSelectedFile();
               //This is where a real application would open the file.
               //log.append("Opening: " + file.getName() + "." + newline);
           } else {
               //log.append("Open command cancelled by user." + newline);
           	return;
        }
		 
		 //BufferedImage img = null;
		 BufferedImage originalImage = ImageIO.read(file);
		 ByteArrayOutputStream baos = new ByteArrayOutputStream();
		 String format = getFormat(file.getName());
		 ImageIO.write( originalImage, format, baos );
		 baos.flush();
		 byte[] imageInByte = baos.toByteArray();
		 baos.close();
		 
		 switch(choose){
			 case 0:
			 	qBase.getQuestions().get(currQ).setImgInByte(imageInByte);
			 	qBase.getQuestions().get(currQ).setImg(originalImage);
			 	break;
			 
			 case 1:
				qBase.getQuestions().get(currQ).getVarA().setImgInByte(imageInByte);
				qBase.getQuestions().get(currQ).getVarA().setImg(originalImage);
				break;
			
			 case 2:
				qBase.getQuestions().get(currQ).getVarB().setImgInByte(imageInByte);
				qBase.getQuestions().get(currQ).getVarB().setImg(originalImage);
				break;
					
			 case 3:
				qBase.getQuestions().get(currQ).getVarC().setImgInByte(imageInByte);
				qBase.getQuestions().get(currQ).getVarC().setImg(originalImage);
		 }
		 
		 this.saved = false;
		 
		 renderQuestion();
		 
		 
		 
			
			//int type = img.getType() == 0? BufferedImage.TYPE_INT_ARGB : img.getType();
			
	}
	
	public void deleteImg(int choose){		//choose: 0 - content, 1 - varA, 2 - varB, 3 - varC
		switch(choose){
			case 0:
				qBase.getQuestions().get(currQ).deleteImg();
			case 1:
				qBase.getQuestions().get(currQ).getVarA().deleteImg();
			case 2:
				qBase.getQuestions().get(currQ).getVarB().deleteImg();
			case 3:
				qBase.getQuestions().get(currQ).getVarC().deleteImg();
		}
		this.saved = false;
		this.renderQuestion();
	}
	
	public void saveAs(){				//zapisz jako
		try
	      {
			int returnVal = fc.showSaveDialog(GUI.this);
			
			 File file;
			 
			 if (returnVal == JFileChooser.APPROVE_OPTION) {
	                file = fc.getSelectedFile();
	                this.currFile = file;
	                //This is where a real application would open the file.
	                //log.append("Opening: " + file.getName() + "." + newline);
	            } else {
	                //log.append("Open command cancelled by user." + newline);
	            	return;
	         }
			
			 FileOutputStream fileOut = new FileOutputStream(file);
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(this.qBase);
	         out.close();
	         fileOut.close();
	         this.saved();
	         //System.out.printf("Serialized data is saved in /tmp/employee.ser");
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
	}
	
	
	
	public void save(){					//zapis pliku
		try
	      {
			 if(this.currFile == null){				//jesli jeszcze plik nie powstal
				 saveAs();
				 return;
			 }
			
			 FileOutputStream fileOut = new FileOutputStream(this.currFile);
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(this.qBase);
	         out.close();
	         fileOut.close();
	         this.saved();
	         //System.out.printf("Serialized data is saved in /tmp/employee.ser");
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
	}
	
	public void open(){				//ladowanie pliku
	      try
	      {
	    	 int returnVal = fc.showOpenDialog(GUI.this);
	    	 
	    	 File file;
	    	 
	    	 if (returnVal == JFileChooser.APPROVE_OPTION) {
	                file = fc.getSelectedFile();
	                this.currFile = file;
	                //This is where a real application would open the file.
	                //log.append("Opening: " + file.getName() + "." + newline);
	            } else {
	                //log.append("Open command cancelled by user." + newline);
	            	return;
	         }
	    	  
	    	 FileInputStream fileIn = new FileInputStream(file);
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         this.qBase = (QBase) in.readObject();
	         in.close();
	         fileIn.close();
	         this.qBase.byteArrayToImg();
	         setQBaseSize();
		     currQ = 0;
		     renderQuestion();
		     renderAuthorName();
		     this.saved();
	      }catch(IOException i)
	      {
	         i.printStackTrace();
	         return;
	      }catch(ClassNotFoundException c)
	      {
	         System.out.println("Niepoprawny format");
	         c.printStackTrace();
	         return;
	      }
	      
	      
	      
	}
	
	
	

	/**
	 * Create the frame.
	 */
	public GUI() {
		setTitle("NewBase - Creator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 978, 662);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNew = new JMenuItem("New");
		mntmNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		mnFile.add(mntmNew);
		mntmNew.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				newBase();
			}
			
		});
		
		JMenuItem mntmOpenFile = new JMenuItem("Open File...");
		mntmOpenFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		mnFile.add(mntmOpenFile);
		mntmOpenFile.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				open();
			}
			
		});
		
		JSeparator separator = new JSeparator();
		mnFile.add(separator);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		mnFile.add(mntmSave);
		
		mntmSave.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				save();
			}
			
		});
		
		JMenuItem mntmSaveAs = new JMenuItem("Save As...");
		mntmSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		mnFile.add(mntmSaveAs);
		
		mntmSaveAs.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				saveAs();
			}
			
		});
		
		JSeparator separator_1 = new JSeparator();
		mnFile.add(separator_1);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
		mnFile.add(mntmExit);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Name");
		lblNewLabel.setBounds(10, 48, 46, 14);
		contentPane.add(lblNewLabel);
		
		textField_name = new JTextField();
		textField_name.setBounds(58, 45, 332, 20);
		contentPane.add(textField_name);
		textField_name.setColumns(10);
		
		textField_name.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				
				
				
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				qBase.setName(textField_name.getText());
				//System.out.println("Added character to" + currQ);
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				qBase.setName(textField_name.getText());
				//System.out.println("Removed character from" + currQ);
				
			}
			
		});
		
		JLabel lblAuthor = new JLabel("Author");
		lblAuthor.setBounds(10, 76, 46, 14);
		contentPane.add(lblAuthor);
		
		textField_author = new JTextField();
		textField_author.setColumns(10);
		textField_author.setBounds(58, 73, 332, 20);
		contentPane.add(textField_author);
		
		textField_author.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				
				
				
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				qBase.setAuthor(textField_author.getText());
				//System.out.println("Added character to" + currQ);
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				qBase.setAuthor(textField_author.getText());
				//System.out.println("Removed character from" + currQ);
				
			}
			
		});
		
		//JTextArea textArea_content = new JTextArea();
		textArea_content.setFont(new Font("Times New Roman", java.awt.Font.PLAIN, 20));
		textArea_content.setBounds(58, 150, 551, 70);
		textArea_content.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				
				
				
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				qBase.getQuestions().get(currQ).setContent(textArea_content.getText());
				notSaved();
				//System.out.println("Added character to" + currQ);
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				qBase.getQuestions().get(currQ).setContent(textArea_content.getText());
				notSaved();
				//System.out.println("Removed character from" + currQ);
				
			}
			
		});
		contentPane.add(textArea_content);
		
		//JTextArea textArea_varA = new JTextArea();
		textArea_varA.setFont(new Font("Times New Roman", java.awt.Font.PLAIN, 20));
		textArea_varA.setBounds(58, 231, 551, 70);
		textArea_varA.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				
				
				
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				qBase.getQuestions().get(currQ).getVarA().setContent(textArea_varA.getText());
				notSaved();
				//System.out.println("Added character to" + currQ);
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				qBase.getQuestions().get(currQ).getVarA().setContent(textArea_varA.getText());
				notSaved();
				//System.out.println("Removed character from" + currQ);
				
			}
			
		});
		contentPane.add(textArea_varA);
		
		//JTextArea textArea_varB = new JTextArea();
		textArea_varB.setFont(new Font("Times New Roman", java.awt.Font.PLAIN, 20));
		textArea_varB.setBounds(58, 312, 551, 70);
		textArea_varB.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				
				
				
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				qBase.getQuestions().get(currQ).getVarB().setContent(textArea_varB.getText());
				notSaved();
				//System.out.println("Added character to" + currQ);
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				qBase.getQuestions().get(currQ).getVarB().setContent(textArea_varB.getText());
				notSaved();
				//System.out.println("Removed character from" + currQ);
				
			}
			
		});
		contentPane.add(textArea_varB);
		
		//JTextArea textArea_varC = new JTextArea();
		textArea_varC.setFont(new java.awt.Font("Times New Roman", java.awt.Font.PLAIN, 20));
		textArea_varC.setBounds(58, 393, 551, 70);
		textArea_varC.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				
				
				
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				qBase.getQuestions().get(currQ).getVarC().setContent(textArea_varC.getText());
				notSaved();
				//System.out.println("Added character to" + currQ);
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				qBase.getQuestions().get(currQ).getVarC().setContent(textArea_varC.getText());
				notSaved();
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
		
		lblqsize.setBounds(295, 494, 46, 14);
		contentPane.add(lblqsize);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addQuestion();		
			}
		});
		btnAdd.setBounds(520, 490, 89, 23);
		contentPane.add(btnAdd);
		
		
		buttonGroup.add(rdbtn_corrA);
		rdbtn_corrA.setBounds(615, 256, 61, 23);
		rdbtn_corrA.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				qBase.getQuestions().get(currQ).setCorrectA();
			}
			
		});
		contentPane.add(rdbtn_corrA);
		
		
		buttonGroup.add(rdbtn_corrB);
		rdbtn_corrB.setBounds(615, 336, 61, 23);
		rdbtn_corrB.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				qBase.getQuestions().get(currQ).setCorrectB();
			}
			
		});
		contentPane.add(rdbtn_corrB);
		
		
		buttonGroup.add(rdbtn_corrC);
		rdbtn_corrC.setBounds(615, 418, 61, 23);
		rdbtn_corrC.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				qBase.getQuestions().get(currQ).setCorrectC();
			}
			
		});
		contentPane.add(rdbtn_corrC);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeQuestion();
				
			}
		});
		btnRemove.setBounds(619, 490, 89, 23);
		contentPane.add(btnRemove);
		
		JButton btnImage = new JButton("AddImage");
		btnImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					setImg(0);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnImage.setBounds(694, 156, 89, 23);
		contentPane.add(btnImage);
		
		JButton btnNewButton = new JButton("AddImage");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					setImg(1);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(694, 237, 89, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("AddImage");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					setImg(2);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnNewButton_1.setBounds(694, 318, 89, 23);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("AddImage");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					setImg(3);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_2.setBounds(694, 399, 89, 23);
		contentPane.add(btnNewButton_2);
		
		imagePanel = new ImagePanel(null);
		imagePanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		imagePanel.setBounds(802, 150, 150, 70);
		contentPane.add(imagePanel);
		
		imagePanelA = new ImagePanel(null);
		imagePanelA.setBorder(new LineBorder(new Color(0, 0, 0)));
		imagePanelA.setBounds(802, 231, 150, 70);
		contentPane.add(imagePanelA);
		
		imagePanelB = new ImagePanel(null);
		imagePanelB.setBorder(new LineBorder(new Color(0, 0, 0)));
		imagePanelB.setBounds(802, 312, 150, 70);
		contentPane.add(imagePanelB);
		
		imagePanelC = new ImagePanel(null);
		imagePanelC.setBorder(new LineBorder(new Color(0, 0, 0)));
		imagePanelC.setBounds(802, 393, 150, 70);
		contentPane.add(imagePanelC);
		
		JButton btnDelimage = new JButton("DeleteImg\r\n");
		btnDelimage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deleteImg(0);
			}
		});
		btnDelimage.setBounds(694, 186, 89, 23);
		contentPane.add(btnDelimage);
		
		JButton btnNewButton_3 = new JButton("DeleteImg");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteImg(1);
			}
		});
		btnNewButton_3.setBounds(694, 267, 89, 23);
		contentPane.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("DeleteImg");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteImg(2);
			}
		});
		btnNewButton_4.setBounds(694, 348, 89, 23);
		contentPane.add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton("DeleteImg");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteImg(3);
			}
		});
		btnNewButton_5.setBounds(694, 429, 89, 23);
		contentPane.add(btnNewButton_5);
		
		label_current = new JLabel("1");
		label_current.setBounds(270, 494, 26, 14);
		contentPane.add(label_current);
		
		JButton btnJumpTo = new JButton("Jump To");
		btnJumpTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jumpTo((Integer)spinner.getValue());
			}
		});
		btnJumpTo.setBounds(157, 524, 89, 23);
		contentPane.add(btnJumpTo);
		
		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spinner.setBounds(256, 525, 40, 20);
		contentPane.add(spinner);
		
		JButton btnGenerate = new JButton("Generate");
		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
					
						try {
							OutputDocument.createDocument(qBase);
						} catch (DocumentException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
			
			}
		});
		btnGenerate.setBounds(326, 524, 89, 23);
		contentPane.add(btnGenerate);
		
		this.newBase();
	}
	
	public void generatePDF() throws DocumentException, IOException{
		
	}
}
