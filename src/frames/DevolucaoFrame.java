package frames;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import main.Main;

public class DevolucaoFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private final JTable table;
	private final JScrollPane scroller;
	
	private final JButton returnBookButton;
	private final JButton quitButton;
	
	public DevolucaoFrame() {
		super("Biblioteca Pompeu: Devolução de livro.");
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 15, 5, 15);
		c.anchor = GridBagConstraints.WEST;
		
		// create the jtable
		table = createJTable();
		
		// adding JTable to JScrollPane
		scroller = new JScrollPane(table);
		scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		c.ipadx = 120;
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 2;
		add(scroller, c);
		
		// add the returnBookButton
		returnBookButton = new JButton("Devolução do livro");
		returnBookButton.setFont(MainFrame.font);
		c.insets = new Insets(100, 15, 20, 15);
		c.ipady = 100;
		c.ipadx = 53;
		c.gridx = 1;
		c.gridy = 0;
		c.gridheight = 1;
		add(returnBookButton, c);
		
		// add quit button
		quitButton = new JButton("Voltar ao menu");
		quitButton.setFont(MainFrame.font);
		c.ipadx = 66;
		c.ipady = 10;
		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(50, 15, 100, 15);
		add(quitButton, c);
		
		// add the button handler
		// and setting the buttons action listeners
		ButtonHandler handler = new ButtonHandler();
		returnBookButton.addActionListener(handler);
		quitButton.addActionListener(handler);
		
	}
	
	private class ButtonHandler implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			
			// if returnBookButton was pressed
			if (event.getSource() == returnBookButton) {
				// return book to library
				
				// gets the int of the selected row
				int selectedRow = table.getSelectedRow();
				
				if (selectedRow == -1) {
					
					// if user didn't select any row of the table
					JLabel message = new JLabel("Nenhum livro foi selecionado!");
					message.setFont(MainFrame.font);
					JOptionPane.showMessageDialog(null, message, "Erro", JOptionPane.ERROR_MESSAGE);
					
				} else {
					// if the user selected a row
					
					// gets the name of the book and student within the selected cell
					String bookName    = table.getValueAt(selectedRow, 0).toString();
					String studentName = table.getValueAt(selectedRow, 1).toString();
					
					// set array of options
					String[] options = { "Confirmar", "Cancelar" };
					
					// set the label to be displayed
					JLabel message = new JLabel("<html><b>Confirmação de devolução<br /><br />Livro: </b>" + 
									 bookName + ".<br /><br /><b>Aluno(a): </b>" + studentName + "<html>");
					
					// set the message of the warning font
					message.setFont(MainFrame.font);
					
					// set the icon to be displayed
					ImageIcon icon = new ImageIcon(this.getClass().getResource("/return_book.png"));
					
					// creates an option pane asking for confirmation
					int numConfirm = JOptionPane.showOptionDialog(null, message, "Aviso:", 
									 JOptionPane.DEFAULT_OPTION, JOptionPane.DEFAULT_OPTION,
									 icon, options, 0);
					if (numConfirm == 0) {
						// IF USER CONFIRMED THE ACTION
						
						// return the book and show a message with icon
						Main.returnBook(bookName, studentName);
						
						// create an "confirmed" icon
						ImageIcon confirmIcon = new ImageIcon(this.getClass().getResource("/confirm.png"));
						
						// create JLabel for the new message and set font
						JLabel newMessage = new JLabel("Livro retornado com sucesso!");
						newMessage.setFont(MainFrame.font);
						
						JOptionPane.showMessageDialog(null, newMessage, "Aviso",
								JOptionPane.INFORMATION_MESSAGE, confirmIcon);
						
						// close current frame
						dispose();
						
						// reload devolução frame
						Main.openDevolucaoFrame();
					}
				}

			} else if (event.getSource() == quitButton) {
				// cancel and return to main frame
				
				// reopen main frame
				Main.openMainFrame();
				
				// close current frame
				dispose();
			}
		}
	}

	public JTable createJTable() {
		// declare the table
		JTable table;
		
		// set the collums names
		String[] columnNames = { "Livros emprestados", "Aluno(a)", "Ano Escolar", "Dias" };
		
		// declares a multidimensional array with the table data
		Object data[][] = new Object[Main.takenBooks.size()][columnNames.length];

		
		// loop through all the taken books
		for (int i = 0; i < Main.takenBooks.size(); i++) {
			
			String bookName    = Main.takenBooks.get(i).getBookName();
			String studentName = Main.takenBooks.get(i).getStudentName();
			String schoolYear  = Main.takenBooks.get(i).getSchoolYear();
			int    diffDays	   = Main.takenBooks.get(i).getTotalDays();
			
			data[i][0] = bookName;
			data[i][1] = studentName;
			data[i][2] = schoolYear;
			data[i][3] = diffDays;
		}
		
		// initialize the table
		table = new JTable(data, columnNames) {
			
			private static final long serialVersionUID = 1L;

			// setting the table text NOT to be editable
			 public boolean isCellEditable(int row, int column) {
				 return false;
			 }
		}; 

		// sort the table to be sorted automatically and can be sorted by the user
		TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
		table.setRowSorter(sorter);
		List<RowSorter.SortKey> sortKeys = new ArrayList<>();
		 
		int columnIndexToSort = 0;
		sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.ASCENDING));
		 
		sorter.setSortKeys(sortKeys);
		sorter.sort();
		
		// set the width for each column
		TableColumnModel columnModel = table.getColumnModel();
		// set the days column to be centered
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		
		columnModel.getColumn(0).setPreferredWidth(200);
		columnModel.getColumn(1).setPreferredWidth(100);
		columnModel.getColumn(2).setPreferredWidth(1);
		columnModel.getColumn(3).setPreferredWidth(1);
		
		// set the height for each row
		table.setRowHeight(22);
		
		// disable column width resizement
		table.getTableHeader().setResizingAllowed(false);
		
		// set to enable just one selection
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		// set the table columns NOT to be editable
		table.getTableHeader().setReorderingAllowed(false);
		
		// set the table font
		table.setFont(MainFrame.font);
		
		return table;
	}
 }