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
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import main.Main;

public class EditarRegistroFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;

	private final JTabbedPane tabbedPane;
	
	private final JTable avaliableTable, takenTable;
	
	private final JButton removeBook, quitButton;
	
	
	public EditarRegistroFrame() {
		
		super("Biblioteca Pompeu: Editar Registro");
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(120, 15, 15, 15);
		
		/* ADD ALL THE BUTTONS */
		
		removeBook = new JButton("Remover Livro");
		removeBook.setFont(MainFrame.font);
		c.ipadx = 20;
		c.ipady = 30;
		c.gridx = 0;
		c.gridy = 1;
		
		add(removeBook, c);
		
        quitButton = new JButton("Voltar");
        quitButton.setFont(MainFrame.font);
        c.insets = new Insets(15, 15, 15, 15);
        c.ipadx = 60;
        c.ipady = 10;
        c.gridx = 0;
		c.gridy = 2;
        add(quitButton, c);
		
		/* ADD JTABLES TO SCROLL PANES AND THEN TO TABBED PANES */
        avaliableTable = this.createJTable("AVALIABLE");
        takenTable 	  = this.createJTable("TAKEN");
        
		// add JTable to JScrollPane
		JScrollPane scrollerAvaliable = new JScrollPane(avaliableTable);
		scrollerAvaliable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollerAvaliable.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		JScrollPane scrollerTaken = new JScrollPane(takenTable);
		scrollerTaken.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollerTaken.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
        tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Dísponiveis", scrollerAvaliable);
		tabbedPane.addTab("Emprestados", scrollerTaken);
		c.ipadx = 150;
		c.gridx = 1;
		c.gridy = 0;
		c.gridheight = 3;
		add(tabbedPane, c);
		
		// ADD BUTTON HANDLER
		ButtonHandler handler = new ButtonHandler();
		removeBook.addActionListener(handler);
		quitButton.addActionListener(handler);
		
	}
	
	private class ButtonHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
		
			if (event.getSource() == removeBook)    {
				
				// USER IS DELETING A AVALIABLE BOOK
				if (tabbedPane.getSelectedIndex() == 0) {
					
					int selectedRow = avaliableTable.getSelectedRow();
					
					if (selectedRow == -1) {
						// if user didn't select any row of the table
						JLabel message = new JLabel("Nenhum livro foi selecionado!");
						message.setFont(MainFrame.font);
						JOptionPane.showMessageDialog(null, message, "Erro", JOptionPane.ERROR_MESSAGE);
						
					} else {
						// gets the name of the book
						String book = avaliableTable.getValueAt(selectedRow, 0).toString();
						
						// set the string array of options
						String[] options = {"Confirmar", "Cancelar"};
						
						// set the label to be displayed
						JLabel message = new JLabel("<html><b>Remover livro da biblioteca.</b> <br /><br /><b>Livro: </b>" + book + "</html>");	
						// set the font for the texts
						message.setFont(MainFrame.font);
						
						// set the icon to be displayed
						ImageIcon bookIcon = new ImageIcon(this.getClass().getResource("/remove_book.png"));
						
						// creates a option pane asking for confirmation
						int numConfirm = JOptionPane.showOptionDialog(null, message, "Aviso", JOptionPane.DEFAULT_OPTION,
								JOptionPane.DEFAULT_OPTION, bookIcon, options, options[0]);
						
						if (numConfirm == 0) {
							// if user confirmed the action
							
							Main.removeAvaliableBook(book);

							// create icon for the JOptionPane
							ImageIcon confirm = new ImageIcon(this.getClass().getResource("/confirm.png"));
							
							// create JLabel for the new message and set font
							JLabel newMessage = new JLabel("Livro removido com sucesso!");
							newMessage.setFont(MainFrame.font);
							
							JOptionPane.showMessageDialog(null, newMessage, "Aviso", 
										JOptionPane.INFORMATION_MESSAGE, confirm);
							
							// close current frame
							dispose();
							
							// reload emprestimo frame
							Main.openEditarRegistroFrame();
						}
					}
				} else if (tabbedPane.getSelectedIndex() == 1) {
					
					int selectedRow = takenTable.getSelectedRow();
					
					if (selectedRow == -1) {
						// if user didn't select any row of the table
						JLabel message = new JLabel("Nenhum livro foi selecionado!");
						message.setFont(MainFrame.font);
						JOptionPane.showMessageDialog(null, message, "Erro", JOptionPane.ERROR_MESSAGE);
						
					} else {
						// gets the name of the book
						String book    = takenTable.getValueAt(selectedRow, 0).toString();
						String student = takenTable.getValueAt(selectedRow, 1).toString();
						
						// set the string array of options
						String[] options = {"Confirmar", "Cancelar"};
						
						// set the label to be displayed
						JLabel message = new JLabel("<html><b>Remoção de Livro.</b> <br /><br /><b>Livro: </b>" + book
													+ "<br /><br /><b>Aluno(a):</b>" + student + "</html>");	
						// set the font for the texts
						message.setFont(MainFrame.font);
						
						// set the icon to be displayed
						ImageIcon bookIcon = new ImageIcon(this.getClass().getResource("/remove_book.png"));
						
						// creates a option pane asking for confirmation
						int numConfirm = JOptionPane.showOptionDialog(null, message, "Aviso", JOptionPane.DEFAULT_OPTION,
								JOptionPane.DEFAULT_OPTION, bookIcon, options, options[0]);
						
						if (numConfirm == 0) {
							// if user confirmed the action
							
							Main.removeTakenBook(book, student);

							// create icon for the JOptionPane
							ImageIcon confirm = new ImageIcon(this.getClass().getResource("/confirm.png"));
							
							// create JLabel for the new message and set font
							JLabel newMessage = new JLabel("Livro removido com sucesso!");
							newMessage.setFont(MainFrame.font);
							
							JOptionPane.showMessageDialog(null, newMessage, "Aviso", 
										JOptionPane.INFORMATION_MESSAGE, confirm);
							
							// close current frame
							dispose();
							
							// reload emprestimo frame
							Main.openEditarRegistroFrame();
						}
					}
				}
			}
			else if (event.getSource() == quitButton)   {
				
				Main.openRegistroFrame();
				
				dispose();
				
			}
		}
	}
	
	private JTable createJTable(String type) {
		
		JTable table;
		
		String[] columnNames = null;
		Object[][] data = null;
		
		if(type == "AVALIABLE") {
			
			columnNames = new String[]{ "Livros DISPONÍVEIS", "Quantidade" };
			data = new Object[Main.avaliableBooks.size()][columnNames.length];
			for (int i = 0; i < Main.avaliableBooks.size(); i++) {
				String bookName = Main.avaliableBooks.get(i).getBookName();
				int bookAmount  = Main.avaliableBooks.get(i).getAmount();
				
				data[i][0] = bookName;
				data[i][1] = bookAmount;
			}
		} else if (type == "TAKEN") {
			
			columnNames = new String[]{ "Livros EMPRESTADOS", "Aluno(a)", "Ano escolar", "Dias" };
			data = new Object[Main.takenBooks.size()][columnNames.length];
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
		if (type == "AVALIABLE") {
			columnModel.getColumn(0).setPreferredWidth(300);
			columnModel.getColumn(1).setPreferredWidth(1);
		} else {
			
			// set the days column to be centered
			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			centerRenderer.setHorizontalAlignment( JLabel.CENTER );
			table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
			
			columnModel.getColumn(0).setPreferredWidth(200);
			columnModel.getColumn(1).setPreferredWidth(100);
			columnModel.getColumn(2).setPreferredWidth(1);
			columnModel.getColumn(3).setPreferredWidth(1);
		}

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
