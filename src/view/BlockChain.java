package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTable;

import controller.NewBlockChainController;

import javax.swing.JComboBox;
import javax.swing.JButton;

public class BlockChain {

	private JFrame frame;
	private JTable table;
	private NewBlockChainController Controller;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BlockChain window = new BlockChain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public BlockChain(NewBlockChainController Controller) {
		this.Controller = Controller;
		initialize();
	}

	/**
	 * Create the application.
	 */
	public BlockChain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 763, 408);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		table = new JTable();
		table.setBounds(47, 42, 690, 316);
		frame.getContentPane().add(table);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(47, 11, 180, 20);
		frame.getContentPane().add(comboBox);
		for(int i=0;i<Controller.getBlockchain().getWallet().size();i++) {
			comboBox.addItem(Controller.getBlockchain().getWallet().get(i).getID());
		}
		
		JButton btnBusqueda = new JButton("Busqueda");
		btnBusqueda.setBounds(247, 10, 89, 23);
		frame.getContentPane().add(btnBusqueda);
		
		JButton btnReset = new JButton("Reset");
		btnReset.setBounds(648, 10, 89, 23);
		frame.getContentPane().add(btnReset);
	}
	
	public JFrame getFrame() {
		return frame;
	}
}
