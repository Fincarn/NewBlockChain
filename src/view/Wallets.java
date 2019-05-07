package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

import controller.NewBlockChainController;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Wallets {

	private JFrame frame;
	private JTextField textField;
	private NewBlockChainController Controller;
	private JComboBox<String> comboBox;
	private JComboBox<String> comboBox_1;
	
	public Wallets(NewBlockChainController Controller) {
		this.Controller = Controller;
		System.out.println("Hay Bloques: "+ Controller.getBlockchain().blockchain.size());
		System.out.println(" Bloques 1: "+ Controller.getBlockchain().blockchain.get(0).transactions.get(0).value);
		System.out.println(" Bloques 2: "+ Controller.getBlockchain().blockchain.get(1).transactions.get(0).value);
		System.out.println(" Bloques 3: "+ Controller.getBlockchain().blockchain.get(2));
		System.out.println(" Bloques 4: "+ Controller.getBlockchain().blockchain.get(3).transactions.get(0).value);
		initialize();
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Wallets window = new Wallets();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Wallets() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 761, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnCreateNewWallet = new JButton("Create New Wallet");
		btnCreateNewWallet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Controller.getBlockchain().addWallet();
				
				int tamaño= Controller.getBlockchain().getWallet().size()-1;
				
				comboBox.addItem(Controller.getBlockchain().getWallet().get(tamaño).getID());
				comboBox_1.addItem(Controller.getBlockchain().getWallet().get(tamaño).getID());
				
			}
		});
		btnCreateNewWallet.setBounds(10, 227, 135, 23);
		frame.getContentPane().add(btnCreateNewWallet);
		
		JButton btnSearchTransactions = new JButton("Search Transactions");
		btnSearchTransactions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    BlockChain BlockChainView=new BlockChain(Controller);
			    BlockChainView.getFrame().setVisible(true);
			}
		});
		
		btnSearchTransactions.setBounds(155, 227, 135, 23);
		frame.getContentPane().add(btnSearchTransactions);
		
		JButton btnTransfer = new JButton("Transfer");
		btnTransfer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!textField.getText().isEmpty()) {
					System.out.println("El valor dentro del texto es: "+ Float.parseFloat(textField.getText()));
					
					
					Controller.transfer(Controller.getBlockchain().getWallet().get(0), Controller.getBlockchain().getWallet().get(1), Float.parseFloat(textField.getText()));
				}
			}
		});
		btnTransfer.setBounds(289, 124, 89, 23);
		frame.getContentPane().add(btnTransfer);
		
	    comboBox = new JComboBox<String>();
		comboBox.setBounds(10, 93, 250, 20);
		frame.getContentPane().add(comboBox);
		for(int i=0;i<Controller.getBlockchain().getWallet().size();i++) {
			comboBox.addItem(Controller.getBlockchain().getWallet().get(i).getID());
		}
		
	    comboBox_1 = new JComboBox<String>();
		comboBox_1.setBounds(400, 93, 250, 20);
		frame.getContentPane().add(comboBox_1);
		for(int i=0;i<Controller.getBlockchain().getWallet().size();i++) {
			comboBox_1.addItem(Controller.getBlockchain().getWallet().get(i).getID());
		}
		
		textField = new JTextField();
		textField.setBounds(289, 93, 86, 20);
		frame.getContentPane().add(textField);
		
		textField.setColumns(10);
		
	      PlainDocument doc = (PlainDocument) textField.getDocument();
	      doc.setDocumentFilter(new MyIntFilter());
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	class MyIntFilter extends DocumentFilter {
		   @Override
		   public void insertString(FilterBypass fb, int offset, String string,
		         AttributeSet attr) throws BadLocationException {

		      Document doc = fb.getDocument();
		      StringBuilder sb = new StringBuilder();
		      sb.append(doc.getText(0, doc.getLength()));
		      sb.insert(offset, string);

		      if (test(sb.toString())) {
		         super.insertString(fb, offset, string, attr);
		      } else {
		         // warn the user and don't allow the insert
		      }
		   }

		   private boolean test(String text) {
		      try {
		         Integer.parseInt(text);
		         return true;
		      } catch (NumberFormatException e) {
		         return false;
		      }
		   }

		   @Override
		   public void replace(FilterBypass fb, int offset, int length, String text,
		         AttributeSet attrs) throws BadLocationException {

		      Document doc = fb.getDocument();
		      StringBuilder sb = new StringBuilder();
		      sb.append(doc.getText(0, doc.getLength()));
		      sb.replace(offset, offset + length, text);

		      if (test(sb.toString())) {
		         super.replace(fb, offset, length, text, attrs);
		      } else {
		         // warn the user and don't allow the insert
		      }

		   }

		   @Override
		   public void remove(FilterBypass fb, int offset, int length)
		         throws BadLocationException {
		      Document doc = fb.getDocument();
		      StringBuilder sb = new StringBuilder();
		      sb.append(doc.getText(0, doc.getLength()));
		      sb.delete(offset, offset + length);

		      if (test(sb.toString())) {
		         super.remove(fb, offset, length);
		      } else {
		         // warn the user and don't allow the insert
		      }

		   }
		}

}
