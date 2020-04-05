package main;

import java.time.LocalDate;

public class Book {

	// variables for book name and year
	private String bookName;
	private int amount;
	// variables for unavaliable books
	private String studentName = null;
	private String schoolYear = null;
	private int borrowDay; 
	
	public Book(String bookName, int amount) {
		this.bookName = bookName;
		this.amount = amount;
	}
	
	public Book(String bookName, int amount, String studentName,
				String schoolYear, int borrowDay) {
		
		this.bookName = bookName;
		this.amount   = amount;
		
		this.studentName = studentName;
		this.schoolYear  = schoolYear;
		this.borrowDay   = borrowDay;
	}
	
	// getter for book name
	public String getBookName() {
		return this.bookName;
	} // setter for book name
	public void setBookName(String newBookName) {
		this.bookName = newBookName;
	}
	
	// getter and setter for amount of books
	public int getAmount() {
		return this.amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	// getter and setter for student name
	public String getStudentName() {
		return this.studentName;
	}
	public void setStudent(String student) {
		this.studentName = student;
	}
	
	// getter and setter for school year of the student
	public String getSchoolYear() {
		return this.schoolYear;
	}
	public void setSchoolYear(String schoolYear) {
		this.schoolYear = schoolYear;
	}
	
	// getter and setter for the borrow day
	public int getBorrowDay() {
		return this.borrowDay;
	}
	public void setBorrowDay(int borrowDay) {
		this.borrowDay = borrowDay;
	}
	
	public int getTotalDays() {
		return (LocalDate.now().getDayOfYear() - this.getBorrowDay());
	}
	
	public String toString() {
		return this.bookName;
	}
	
	public void reset() {
		this.studentName = null;
		this.schoolYear  = null;
		this.borrowDay   = 0;
	}
	
}
