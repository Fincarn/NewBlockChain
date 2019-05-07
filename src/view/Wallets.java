package view;

import java.awt.EventQueue;
import java.security.PublicKey;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JTextField;

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
		System.out.println("antes de inicializar "+ Controller.getBlockchain().getWallet().size());
		initialize();
		
		System.out.println("despues de inicializar");
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
				System.out.println(Controller.getBlockchain().getWallet().size());
				int tamaño= Controller.getBlockchain().getWallet().size()-1;
				
				comboBox.addItem(Controller.getBlockchain().getWallet().get(tamaño).getID());
				comboBox_1.addItem(Controller.getBlockchain().getWallet().get(tamaño).getID());
				
			}
		});
		btnCreateNewWallet.setBounds(10, 227, 135, 23);
		frame.getContentPane().add(btnCreateNewWallet);
		
		JButton btnSearchTransactions = new JButton("Search Transactions");
		btnSearchTransactions.setBounds(155, 227, 135, 23);
		frame.getContentPane().add(btnSearchTransactions);
		
		JButton btnTransfer = new JButton("Transfer");
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
	}
	
	public JFrame getFrame() {
		return frame;
	}

}
