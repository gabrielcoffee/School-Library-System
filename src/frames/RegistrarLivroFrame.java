package frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import main.Main;

public class RegistrarLivroFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JLabel text;
	
	private final JTextField bookName;
	private final JSpinner bookAmount;
	private final JButton addBook, quitButton;

	public RegistrarLivroFrame() {
		super("Biblioteca Pompeu: Registrar livro.");
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();	
		
		// DEFAULT PROPERTIES
		c.insets = new Insets(0, 15, 0, 15);
		c.anchor = GridBagConstraints.WEST;
		
		/* ADDING COMPONENTS OF THE FRAME */
		
		text = new JLabel("Nome do livro:");
		text.setFont(MainFrame.boldFont);
		c.gridx = 0;
		c.gridy = 0;
		add(text, c);
		
		bookName = new JTextField();
		bookName.setPreferredSize(new Dimension(280, 30));
		bookName.setFont(MainFrame.font);
		c.ipadx = 40;
		c.ipady = 5;
		c.gridx = 0;
		c.gridy = 1;
		add(bookName, c);
		
		text = new JLabel("Quantidade:");
		text.setFont(MainFrame.boldFont);
		c.ipadx = 0;
		c.gridx = 0;
		c.gridy = 2;
		add(text, c);
		
		bookAmount = new JSpinner();
		bookAmount.setFont(MainFrame.font);
		 // Disable keyboard edits in the spinner
	    JFormattedTextField tf = ((JSpinner.DefaultEditor) bookAmount.getEditor()).getTextField();
	    tf.setEditable(false);
	    tf.setBackground(Color.white);
		c.ipadx = 20;
		c.gridx = 0;
		c.gridy = 3;
		bookAmount.setValue(1);
		add(bookAmount, c);
		
		c.insets = new Insets(20, 20, 20, 20);
		
		addBook = new JButton("Adicionar Livro");
		c.ipadx = 38;
		c.ipady = 60;
		c.gridx = 1;
		c.gridy = 1;
		addBook.setFont(MainFrame.font);
		add(addBook, c);
		
		quitButton = new JButton("Cancelar e voltar");
		c.ipadx = 20;
		c.ipady = 10;
		c.gridx = 1;
		c.gridy = 3;
		quitButton.setFont(MainFrame.font);
		add(quitButton, c);
		
		/* ADDING ACTION LISTENER */
		
		ButtonHandler handler = new ButtonHandler();
		addBook.addActionListener(handler);
		quitButton.addActionListener(handler);

	}
	
	private class ButtonHandler implements ActionListener {


		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == addBook) {
				// BOTAO DE ADICIONAR LIVRO

				// get the amount in the bookAmount spinner
				int amount = Integer.parseInt(bookAmount.getValue().toString());
				
				if (amount <= 0) {
					
					JLabel message = new JLabel("Quantidade inválida!");
					message.setFont(MainFrame.font);
					
					JOptionPane.showMessageDialog(null, message, "Erro", JOptionPane.ERROR_MESSAGE);
				}
				else if (bookName.getText().equals("")) {
					JLabel message = new JLabel("Nome do livro inválido!");
					message.setFont(MainFrame.font);

					JOptionPane.showMessageDialog(null, message, "Erro", JOptionPane.ERROR_MESSAGE);
				} else {
					// get book name
					String book = bookName.getText().trim();
					
					// set the message
					JLabel message = new JLabel("<html><b>Confirmar registro de livro? <br /><br />"
											  + "Livro: </b>" 	   + book   + "<br /><br /><b>"
											  + "Quantidade: </b>" + amount + "<html>");
					message.setFont(MainFrame.font);
					
					// set the icon
					Icon icon = new ImageIcon(getClass().getResource("/add_book.png"));
					
					// set the string array of options
					String[] options = {"Confirmar", "Cancelar"};
					
					// creates a option pane asking for confirmation
					int numConfirm = JOptionPane.showOptionDialog(null, message, "Aviso", JOptionPane.DEFAULT_OPTION,
							JOptionPane.DEFAULT_OPTION, icon, options, options[0]);
					
					if (numConfirm == 0) {
						// if user confirmed the action
						
						// add the book to the system
						Main.addBook(book, amount);
						
						// create icon for the JOptionPane
						ImageIcon confirm = new ImageIcon(this.getClass().getResource("/confirm.png"));
						
						// create JLabel for the new message and set font
						JLabel newMessage = new JLabel("Registro feito com sucesso!");
						newMessage.setFont(MainFrame.font);
						
						JOptionPane.showMessageDialog(null, newMessage, "Aviso", 
									JOptionPane.INFORMATION_MESSAGE, confirm);
						
						// close this frame
						dispose();
						
						// reopen this frame
						Main.openRegistrarLivroFrame();
					}
				}
			}
			else if (event.getSource() == quitButton) {
				
				// close this frame
				dispose();
				
				// reopen this frame
				Main.openRegistroFrame();
			}
			
		}
		
	}
	

}








