package main;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import frames.DevolucaoFrame;
import frames.EditarRegistroFrame;
import frames.EmprestimoFrame;
import frames.MainFrame;
import frames.RegistrarLivroFrame;
import frames.RegistroFrame;

public class Main {

	public static List<Book>  avaliableBooks   = new ArrayList<Book>();
	public static List<Book>  takenBooks       = new ArrayList<Book>();
	
	public static void main(String[] args) {
		
		// loads the saved books
		loadAvaliableBooks();
		loadTakenBooks();
		
		// starts program with main Frame
		openMainFrame();
		
	}
	
	public static void addBook(String bookName, int amount) {
		Book book = new Book(bookName, amount);
		avaliableBooks.add(book);
	}
	
	public static void removeAvaliableBook(String bookName) {
		Book book = getAvaliableBook(bookName);
		avaliableBooks.remove(book);
	}
	
	public static void removeTakenBook(String bookName, String studentName) {
		Book book = getTakenBook(bookName, studentName);
		takenBooks.remove(book);
	}

	/*
	public static void editBook(String bookName, int amount, String studentName, String schoolYear, int borrowDay) {
		Book book = getAvaliableBook(bookName);
		book.setBookName(bookName);
		book.setAmount(amount);
		
		book = getTakenBook(bookName, studentName);
		book.setBookName(bookName);
		book.setAmount(amount);
		book.setStudent(studentName);
		book.setSchoolYear(schoolYear);
		book.setBorrowDay(borrowDay);
	}
	*/
	
	public static void takeBook(String bookName, String studentName, String schoolYear) {
		
		Book book = null; 

		Book b = getAvaliableBook(bookName);
		
		// initialize book with the same properties
		// and decrease the amount of the book in avaliable list
		if (b != null) {
			book = new Book(b.getBookName(), b.getAmount());
			b.setAmount(b.getAmount() -1);
		}
		
		// decreasing the amount of books of this type book
		book.setAmount(book.getAmount() - 1);
		
		// setting the book's taken properties
		book.setStudent(studentName);
		book.setSchoolYear(schoolYear);	
		book.setBorrowDay(LocalDate.now().getDayOfYear());

		// adds the book to the taken books list
		takenBooks.add(book);
		
		// remove from avaliable list if it had just one book left
		if (b.getAmount() == 0) {
			avaliableBooks.remove(b);
		}
	}

	public static void returnBook(String bookName, String studentName) {
		
		Book book = null;
		book = getAvaliableBook(bookName);
		
		if (book != null) {
			// if the book is already on avaliable list	increase it's amount
			book.setAmount(book.getAmount() + 1);
			/***/
			book = getTakenBook(bookName, studentName);
		} 
		else 
		{ 
			// if its not on that list, reset and increase amount of
			// the taken one and add to the avaliable list
			book = getTakenBook(bookName, studentName);
			book.reset();
			book.setAmount(book.getAmount() + 1);
			avaliableBooks.add(book);
		} 
		
		// either way, remove it from taken books list
		takenBooks.remove(book);
		
	}

	public static Book getAvaliableBook(String bookName) {
		
		// loop through all the avaliable books and checks which has the
		// correct book's name, and returns this Book.
		
		Book book = null;
		for (int i = 0; i < avaliableBooks.size(); i++) {
			String name = avaliableBooks.get(i).getBookName();
			if (name == bookName) {	
				book = avaliableBooks.get(i);
			}
		}
		return book;
	}
	
	public static Book getTakenBook(String bookName, String studentName) {
		
		// loop through all the taken books and checks which has the
		// correct book and student's name, and returns this Book.
		
		Book book = null;
		
		for (int i = 0; i < takenBooks.size(); i++) {
			String book_name = takenBooks.get(i).getBookName();
			String student = takenBooks.get(i).getStudentName();
			if (book_name == bookName && student == studentName) {	
				book = takenBooks.get(i);
			}
		}
		return book;
		
	}

	public static String[] getArrayAlphabetically(String[] array) {
		// organize and array alphabetically
		
		String temp;
		
		for (int i = 0; i < array.length; i++) {
			for (int j = i + 1; j < array.length; j++) {
				if (array[i].compareTo(array[j]) > 0) {
					temp = array[i];
					array[i] = array[j];
					array[j] = temp;
				}
			}
		}
		return array;
	}
	
	public static void avaliableBooksSave() {
		
		BufferedWriter writer = null;
		
		try {
			writer = new BufferedWriter(new FileWriter("livrosDisponiveis.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < avaliableBooks.size(); i++) {
			// SET THE BOOK PROPERTIES
			String bookName = avaliableBooks.get(i).getBookName();
			int	   amount   = avaliableBooks.get(i).getAmount();
			
			// CREATES A NEW LINE WITH THE BOOK PROPERTIES
			String currentBookLine = "";
			currentBookLine += bookName;
			currentBookLine += ",";
			currentBookLine += amount;
			
			try {
				writer.write(currentBookLine);
				writer.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			// writes the content of the buffer to the destination 
			// and makes the buffer empty for further data to store
			writer.flush();
			
			// closes the stream permanently
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void takenBooksSave() {
		
		BufferedWriter writer = null;
		
		try {
			writer = new BufferedWriter(new FileWriter("livrosEmprestados.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < takenBooks.size(); i++) {
			// SET THE BOOK PROPERTIES
			String bookName   = takenBooks.get(i).getBookName();
			int	   amount     = takenBooks.get(i).getAmount();
			String student	  = takenBooks.get(i).getStudentName();
			String schoolYear = takenBooks.get(i).getSchoolYear();
			int	   borrowDay  = takenBooks.get(i).getBorrowDay();
			
			// CREATES A NEW LINE WITH THE BOOK PROPERTIES
			String currentBookLine = "";
			currentBookLine += bookName;
			currentBookLine += ",";
			currentBookLine += amount;
			currentBookLine += ",";
			currentBookLine += student;
			currentBookLine += ",";
			currentBookLine += schoolYear;
			currentBookLine += ",";
			currentBookLine += borrowDay;
			
			try {
				writer.write(currentBookLine);
				writer.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			// writes the content of the buffer to the destination 
			// and makes the buffer empty for further data to store
			writer.flush();
			
			// closes the stream permanently
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void loadAvaliableBooks() {
		File file = new File("livrosDisponiveis.txt");
		if (file.exists()) {
			try {
				String currentLine = "";
				BufferedReader reader = new BufferedReader(new FileReader("livrosDisponiveis.txt"));
				try {
					while((currentLine = reader.readLine()) != null) {
						String[] keyWords = currentLine.split(",");
						
						String bookName   = keyWords[0];
						int	   amount     = Integer.parseInt(keyWords[1]);
						
						Book book = new Book(bookName, amount);
						
						avaliableBooks.add(book);
					}
				}catch (IOException e) {}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void loadTakenBooks() {
		File file = new File("livrosEmprestados.txt");
		if (file.exists()) {
			try {
				String currentLine = "";
				BufferedReader reader = new BufferedReader(new FileReader("livrosEmprestados.txt"));
				try {
					while((currentLine = reader.readLine()) != null) {
						String[] keyWords = currentLine.split(",");
						
						String bookName   = keyWords[0];
						int	   amount     = Integer.parseInt(keyWords[1]);
						String student	  = keyWords[2];
						String schoolYear = keyWords[3];
						int	   borrowDay  = Integer.parseInt(keyWords[4]);
						
						Book book = new Book(bookName, amount, student,
											 schoolYear, borrowDay);
						
						takenBooks.add(book);
					}
				}catch (IOException e) {}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void openMainFrame() {
		
		MainFrame mainFrame = new MainFrame();
		mainFrame.setMinimumSize(new Dimension(1000, 600));
		mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}
	
	public static void openEmprestimoFrame() {
		
		EmprestimoFrame frame = new EmprestimoFrame();
		frame.setMinimumSize(new Dimension(1000, 600));
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public static void openDevolucaoFrame() {
		
		DevolucaoFrame frame = new DevolucaoFrame();
		frame.setMinimumSize(new Dimension(1000, 600));
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public static void openRegistrarLivroFrame() {
		
		RegistrarLivroFrame frame = new RegistrarLivroFrame();
		frame.setMinimumSize(new Dimension(1000, 600));
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public static void openRegistroFrame() {
		
		RegistroFrame frame = new RegistroFrame();
		frame.setMinimumSize(new Dimension(1000, 600));
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public static void openEditarRegistroFrame() {
		
		EditarRegistroFrame frame = new EditarRegistroFrame();
		frame.setMinimumSize(new Dimension(1000, 600));
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
