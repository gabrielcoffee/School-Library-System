package frames;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import main.Main;

public class MainFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private final JLabel label;
	
	private final JButton emprestimo;
	private final JButton devolucao;
	private final JButton registro;
	private final JButton quitButton;
	
	public static Font font = new Font("Arial", Font.PLAIN, 20);
	public static Font boldFont = new Font("Arial", Font.BOLD,22);
	
	public MainFrame() {
		super("Biblioteca Pompeu: Menu Principal");
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(20, 40, 20, 40);
		c.fill = 1;
		
		
		label = new JLabel("Biblioteca");
		label.setFont(new Font("Arial", Font.BOLD, 84));

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridheight = 2;
		c.anchor = GridBagConstraints.CENTER;
		add(label, c);
		
		
		/* BUTTONS */
		
		emprestimo = new JButton("Empréstimo");
		emprestimo.setFont(font);
		
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 2;
		c.ipady = 40;
		
		add(emprestimo, c);
		
		devolucao = new JButton("Devolução");
		devolucao.setFont(font);
		
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 1;
		c.gridheight = 2;
		
		add(devolucao, c);
		
		
		registro = new JButton("Registro de livros");
		registro.setFont(font);
		
		c.gridx = 2;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 4;
		c.ipady = 40;
		
		add(registro, c);
		
		quitButton = new JButton("Salvar e sair");
		quitButton.setFont(font);
		
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 3;
		c.gridheight = 1;
		c.ipady = 10;
		
		add(quitButton, c);

		/* END OF BUTTONS */
		
		// add button handler
		ButtonHandler handler = new ButtonHandler();
		emprestimo.addActionListener(handler);
		devolucao.addActionListener(handler);
		registro.addActionListener(handler);
		quitButton.addActionListener(handler);
		
	}	
	
	
	private class ButtonHandler implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == emprestimo) {
				// emprestimo button
				Main.openEmprestimoFrame();
			
			} 
			else if (event.getSource() == devolucao) {
				// devolucao button
				Main.openDevolucaoFrame();
			
			} 
			else if (event.getSource() == registro) {
				// registro button
				Main.openRegistroFrame();
			
			} 
			else if (event.getSource() == quitButton) {
				
				// save the changes on the lists
				Main.avaliableBooksSave();
				Main.takenBooksSave();
				
				// close the program
				System.exit(0);
				
			}
			// removes last window
			dispose();
		}
	}
}













