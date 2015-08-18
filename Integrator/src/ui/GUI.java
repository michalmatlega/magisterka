package ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import model.QBase;
import model.SuperBase;

public class GUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private SuperBase superBase;
	private JFileChooser fc;
	private File currFile;
	
	

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
	
	public void addBase(){
		try
	      {
	    	 
			fc.setMultiSelectionEnabled(true);
			int returnVal = fc.showOpenDialog(GUI.this);
	    	 
	    	 File files[];
	    	 
	    	 if (returnVal == JFileChooser.APPROVE_OPTION) {
	                files = fc.getSelectedFiles();
	                
	                //This is where a real application would open the file.
	                //log.append("Opening: " + file.getName() + "." + newline);
	            } else {
	                //log.append("Open command cancelled by user." + newline);
	            	return;
	         }
	    	  
	    	 for(File f: files){
		    	 FileInputStream fileIn = new FileInputStream(f);
		         ObjectInputStream in = new ObjectInputStream(fileIn);
		         superBase.add((QBase) in.readObject());
		         in.close();
		         fileIn.close();
	    	 }
	         
	         renderTable();
		   
		     //this.saved();
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
	
	public void renderTable(){
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		
		for(QBase qb: superBase.getQbcoll()){
			
			model.addRow(new Object[]{qb.getName(), qb.getAuthor(), qb.getQuestions().size(), new Boolean(false)});
		}
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		superBase = new SuperBase();
		fc = new JFileChooser("C:\\Users\\Miszelek\\Desktop");
		
		setTitle("Integrator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1016, 664);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNew = new JMenuItem("New");
		mntmNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		mnFile.add(mntmNew);
		
		JMenuItem mntmOpenFile = new JMenuItem("Open File...");
		mntmOpenFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		mnFile.add(mntmOpenFile);
		
		JSeparator separator = new JSeparator();
		mnFile.add(separator);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		mnFile.add(mntmSave);
		
		JMenuItem mntmSaveAs = new JMenuItem("Save As...");
		mntmSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		mnFile.add(mntmSaveAs);
		
		JSeparator separator_1 = new JSeparator();
		mnFile.add(separator_1);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
		mnFile.add(mntmExit);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnAddBase = new JButton("Add Base");
		btnAddBase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//DefaultTableModel model = (DefaultTableModel) table.getModel();
				//model.addRow(new Object[]{"a", "b", "c", "d"});
				
				addBase();
				
				
			}
		});
		btnAddBase.setBounds(10, 11, 175, 23);
		contentPane.add(btnAddBase);
		
		JLabel lblNumberOfGroups = new JLabel("Amount of groups");
		lblNumberOfGroups.setBounds(10, 579, 94, 14);
		contentPane.add(lblNumberOfGroups);
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(1, 1, 4, 1));
		spinner.setBounds(102, 576, 29, 20);
		contentPane.add(spinner);
		
		JLabel lblDate = new JLabel("Date");
		lblDate.setBounds(768, 15, 46, 14);
		contentPane.add(lblDate);
		
		JLabel lblTuDodamyMoze = new JLabel("Tu dodamy moze jakis jCalendar");
		lblTuDodamyMoze.setBounds(809, 15, 165, 72);
		contentPane.add(lblTuDodamyMoze);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 45, 734, 523);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Title", "Author", "# of Questions", "Delete"
			}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			Class[] columnTypes = new Class[] {
				String.class, String.class, Integer.class, Boolean.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		
		JButton btnDeleteSelected = new JButton("Delete Selected");
		btnDeleteSelected.setBounds(195, 11, 175, 23);
		contentPane.add(btnDeleteSelected);
		
		JButton btnGenerate = new JButton("Generate");
		btnGenerate.setBounds(141, 575, 89, 23);
		contentPane.add(btnGenerate);
		
		/*final JCheckBox checkBox = new JCheckBox();
		table.getColumn("Delete").setCellRenderer(new DefaultTableCellRenderer() {
		    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
		    {
		      checkBox.setSelected(((Boolean)value).booleanValue()) ;
		      return checkBox;
		    }
		});*/
		
		
	}
}
