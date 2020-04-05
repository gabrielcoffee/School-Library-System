package frames;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import main.Main;

public class EmprestimoFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private final JTable table;
	private final JScrollPane scroller;
	
	private final JTextField studentName;

	private final JComboBox<String> schoolYear;
	private final String[] schoolYears = { "Primeiro Ano", "Segundo Ano", "Terceiro Ano" };
	
	private final JButton addBookButton;
	private final JButton quitButton;
	
	private JLabel label;

	public EmprestimoFrame() {

		super("Biblioteca Pompeu: Empréstimo de livro.");
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 15, 5, 15);
		
		// create the table method
		table = createJTable();

		// add JTable to JScrollPane
		scroller = new JScrollPane(table);
		scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 7;
		add(scroller, c);
		c.gridheight = 1;
		
		// add label for student name
		label = new JLabel("Nome do aluno:");
		label.setFont(MainFrame.boldFont);
		
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 1;
		c.gridy = 0;
		add(label, c);
		
		// add text field for student name
		studentName = new JTextField();
		studentName.setPreferredSize(new Dimension(280, 30));
		studentName.setFont(MainFrame.font);
		c.gridx = 1;
		c.gridy = 1;
		c.ipadx = 20;
		c.ipady = 10;
		add(studentName, c);
		
		
		// add label for student name
		label = new JLabel("Ano escolar:");
		label.setFont(MainFrame.boldFont);
		c.gridx = 1;
		c.gridy = 3;
		c.gridheight = 1;
		add(label, c);
		
		// add jcombobox for school year
		schoolYear = new JComboBox<String>(schoolYears);
		schoolYear.setFont(MainFrame.font);
		c.ipadx = 1;
		c.ipady = 1;
		c.gridx = 1;
		c.gridy = 4;
		c.insets = new Insets(5, 15, 50, 15);
		add(schoolYear, c);
		

		// add button to take book
		addBookButton = new JButton("Confirmar");
		addBookButton.setFont(MainFrame.font);
		c.insets = new Insets(50, 15, 5, 15);
		c.gridx = 1;
		c.gridy = 5;
		c.ipadx = 103;
		c.ipady = 30;
		add(addBookButton, c);
		
		// add button to cancel action
		quitButton = new JButton("Voltar ao menu");
		quitButton.setFont(MainFrame.font);
		c.insets = new Insets(20, 15, 0, 15);
		c.gridx = 1;
		c.gridy = 6;
		c.ipadx = 40;
		c.ipady = 8;
		add(quitButton, c);
		
		// add action listener
		ButtonHandler handler = new ButtonHandler();
		addBookButton.addActionListener(handler);
		quitButton.addActionListener(handler);
	}
	
	private class ButtonHandler implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == addBookButton) {
				// student is taking the book
				
				int selectedRow = table.getSelectedRow();
				
				if (selectedRow == -1) {
					// if user didn't select any row of the table
					JLabel message = new JLabel("Nenhum livro foi selecionado!");
					message.setFont(MainFrame.font);
					JOptionPane.showMessageDialog(null, message, "Erro", JOptionPane.ERROR_MESSAGE);
					
				} else if (studentName.getText().equals("")) {
					// if user didn't write the student name
					JLabel message = new JLabel("Nome de aluno inválido!");
					message.setFont(MainFrame.font);
					JOptionPane.showMessageDialog(null, message, "Erro", JOptionPane.ERROR_MESSAGE);
					
				}
				else 
				{
					// gets the name of the book, student and school year, in the selected cell
					String book = table.getValueAt(selectedRow, 0).toString();
					String student = studentName.getText();
					String year = getSchoolYear(schoolYear.getSelectedIndex());
					
					// set the string array of options
					String[] options = {"Confirmar", "Cancelar"};
					
					// set the label to be displayed
					JLabel message = new JLabel("<html><b>Confirmação de Empréstimo.</b> <br /><br /><b>Livro: </b>" + book 
												+ ".<br /><br />" + "<b>Aluno(a): </b>" + student + ".<br /><br />"
														+ "<b>Série: </b>" + year + ".</html>");	
					// set the font for the texts
					message.setFont(MainFrame.font);
					
					// set the icon to be displayed
					ImageIcon bookIcon = new ImageIcon(this.getClass().getResource("/book_take.png"));
					
					// creates a option pane asking for confirmation
					int numConfirm = JOptionPane.showOptionDialog(null, message, "Aviso", JOptionPane.DEFAULT_OPTION,
							JOptionPane.DEFAULT_OPTION, bookIcon, options, options[0]);
					
					if (numConfirm == 0) {
						// if user confirmed the action
						
						// the book is taken and shows message with an icon
						Main.takeBook(book, student, year);

						// create icon for the JOptionPane
						ImageIcon confirm = new ImageIcon(this.getClass().getResource("/confirm.png"));
						
						// create JLabel for the new message and set font
						JLabel newMessage = new JLabel("Empréstimo feito com sucesso!");
						newMessage.setFont(MainFrame.font);
						
						JOptionPane.showMessageDialog(null, newMessage, "Aviso", 
									JOptionPane.INFORMATION_MESSAGE, confirm);
						
						// close current frame
						dispose();
						
						// reload emprestimo frame
						Main.openEmprestimoFrame();
					}
				}
			} 
			else if(event.getSource() == quitButton) {
				
				// opens the main frame
				Main.openMainFrame();
				
				// close current frame
				dispose();
			}
		}
	}

	public String getSchoolYear(int index) {
		
		switch (index) {
		case 0:
			return schoolYears[0];
		case 1:
			return schoolYears[1];
		case 2: 
			return schoolYears[2];
		default:
			return schoolYears[0];
		}
	}
	
	
	private JTable createJTable() {
		
		JTable table;
		
		String[] columnNames = { "Livros Disponíveis", "Quantidade" };
		
		Object[][] data = new Object[Main.avaliableBooks.size()][columnNames.length];
		
		for (int i = 0; i < Main.avaliableBooks.size(); i++) {
			String bookName = Main.avaliableBooks.get(i).getBookName();
			int bookAmount  = Main.avaliableBooks.get(i).getAmount();
			
			data[i][0] = bookName;
			data[i][1] = bookAmount;
		}
		
		// initialize table	
		table = new JTable(data, columnNames) {
			private static final long serialVersionUID = 1L;

			// setting the table text no to be editable
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
		columnModel.getColumn(0).setPreferredWidth(320);
		columnModel.getColumn(1).setPreferredWidth(10);
		
		// set the second column to be centered
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
		
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