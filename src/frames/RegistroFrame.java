package frames;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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

import main.Book;
import main.Main;

public class RegistroFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private final JButton registerBook;
	private final JButton editBooks;
	private final JButton quitButton;
	
	private final JTabbedPane tabbedPane;
	
	
	public RegistroFrame() {
		super("Biblioteca Pompeu: Registro de livros.");
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(75, 15, 15, 15);

		
		// ADD ALL THE BUTTONS
		
		registerBook = new JButton("Registrar livro");
		registerBook.setFont(MainFrame.font);
		c.ipadx = 25;
		c.ipady = 40;
		c.gridx = 0;
		c.gridy = 0;
		add(registerBook, c);
		
		c.insets = new Insets(30, 15, 60, 15);
		
		editBooks = new JButton("Editar Registro");
		editBooks.setFont(MainFrame.font);
		c.ipadx = 15;
		c.gridx = 0;
		c.gridy = 1;
		
		add(editBooks, c);
		
        quitButton = new JButton("Voltar ao menu");
        quitButton.setFont(MainFrame.font);
        c.insets = new Insets(15, 15, 15, 15);
        c.ipadx = 3;
        c.ipady = 15;
        c.gridx = 0;
		c.gridy = 2;
        add(quitButton, c);

        
        // CREATE TABLES INTO TABS

        JScrollPane avaliableTable   = this.createJTableInScroller("AVALIABLE");
        JScrollPane unavaliableTable = this.createJTableInScroller("UNAVALIABLE");
        JScrollPane takenTable 	     = this.createJTableInScroller("TAKEN");
        JScrollPane pendentTable     = this.createJTableInScroller("PENDENT");

        tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Dísponiveis", avaliableTable);
		tabbedPane.addTab("Indisponíveis", unavaliableTable);
		tabbedPane.addTab("Emprestados", takenTable);
		tabbedPane.addTab("Pendentes"  , pendentTable);
		c.ipadx = 150;
		c.gridx = 1;
		c.gridy = 0;
		c.gridheight = 3;
		add(tabbedPane, c);
		
		// ADD BUTTON HANDLER
		ButtonHandler handler = new ButtonHandler();
		registerBook.addActionListener(handler);
		editBooks.addActionListener(handler);
		quitButton.addActionListener(handler);
	
	}
	
	private class ButtonHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
		
			if (event.getSource() == registerBook) {
				
				Main.openRegistrarLivroFrame();
			
				dispose();
				
			} 
			else if (event.getSource() == editBooks)    {
				
				Main.openEditarRegistroFrame();
				
				dispose();
			}
			else if (event.getSource() == quitButton)   {
				
				Main.openMainFrame();
				
				dispose();
				
			}
		}
	}
	
	private JScrollPane createJTableInScroller(String type) {
		
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
		} else if (type == "UNAVALIABLE") {
			
			ArrayList<Book> unavaliableBooks = new ArrayList<Book>();
			

			for (int i = 0; i < Main.takenBooks.size(); i++) {

				String bookName = Main.takenBooks.get(i).getBookName();
				
				Book   book = Main.getAvaliableBook(bookName);
				
				// if book is not in avaliable list it is unavaliable
				if (book == null) {
					unavaliableBooks.add(Main.takenBooks.get(i));
				}
				
			}
			
			columnNames = new String[]{ "Livros INDISPONÍVEIS", "Aluno(a)", "Ano escolar", "Dias" };
			data = new Object[unavaliableBooks.size()][columnNames.length];
			
			for (int i = 0; i < unavaliableBooks.size(); i++) {
				
				String bookName    = unavaliableBooks.get(i).getBookName();
				String studentName = unavaliableBooks.get(i).getStudentName();
				String schoolYear  = unavaliableBooks.get(i).getSchoolYear();
				int    diffDays	   = unavaliableBooks.get(i).getTotalDays();
				
				data[i][0] = bookName;
				data[i][1] = studentName;
				data[i][2] = schoolYear;
				data[i][3] = diffDays;
			}

		} else if (type == "TAKEN") {
			
			columnNames = new String[]{ "Livros EMPRESTADOS", "Aluno(a)", "Ano escolar", "Dias"  };
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
		} else if (type == "PENDENT") {
			
			ArrayList<Book> pendentBooks = new ArrayList<Book>();
			
			for (int i = 0; i < Main.takenBooks.size(); i++) {
				
				int diffDays = Main.takenBooks.get(i).getTotalDays();
				
				if (diffDays >= 7) {
					Book book = Main.takenBooks.get(i);
					pendentBooks.add(book);
				}
			}
			
			
			columnNames = new String[]{ "Livros PENDENTES", "Aluno(a)", "Ano escolar", "Dias"  };
			data = new Object[pendentBooks.size()][columnNames.length];
			
			for (int i = 0; i < pendentBooks.size(); i++) {
				
				// CREATE THE DATA FOR THE PENDENT TABLE
				
				String bookName    = pendentBooks.get(i).getBookName();
				String studentName = pendentBooks.get(i).getStudentName();
				String schoolYear  = pendentBooks.get(i).getSchoolYear();
				int    diffDays    = pendentBooks.get(i).getTotalDays();
				
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
			table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
			
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
		
		// add JTable to JScrollPane
		JScrollPane scroller = new JScrollPane(table);
		scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		return scroller;
	}
}
