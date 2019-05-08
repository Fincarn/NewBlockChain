package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTable;

import controller.NewBlockChainController;

import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.security.PublicKey;
import java.awt.event.ActionEvent;

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
		frame.setBounds(100, 100, 954, 469);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(47, 64, 881, 355);
		frame.getContentPane().add(scrollPane);
	    String header[] = { "Number", "Previous Hash", "Hash", "Envio", "Recibo", "Valor" };
		JPanel tablePanel = new JPanel(new BorderLayout());
		DefaultTableModel model = new DefaultTableModel(header,0);
		table = new JTable(model);
		tablePanel.add(table, BorderLayout.CENTER);
		tablePanel.add(table.getTableHeader(), BorderLayout.NORTH);
		table.getTableHeader().setReorderingAllowed(false);;
		scrollPane.setViewportView(table);
		
		fillTable(model);
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(47, 11, 283, 20);
		frame.getContentPane().add(comboBox);
		
		for(int i=0;i<Controller.getBlockchain().getWallet().size();i++) {
			comboBox.addItem(Controller.getBlockchain().getWallet().get(i).getID());
		}
		
		JButton btnBusqueda = new JButton("Busqueda");
		btnBusqueda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setRowCount(0);
				fillTableSearch(model,comboBox.getSelectedIndex());
			}
		});
		btnBusqueda.setBounds(340, 10, 116, 23);
		frame.getContentPane().add(btnBusqueda);
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (model.getRowCount() > 0) {
				    for (int i = model.getRowCount() - 1; i > -1; i--) {
				    	model.removeRow(i);
				    }
				}
				fillTable(model);
			}
		});
		btnReset.setBounds(839, 10, 89, 23);
		frame.getContentPane().add(btnReset);
		
		JButton btnAdministracionCarteras = new JButton("Administracion Carteras");
		btnAdministracionCarteras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Wallets wallet=new Wallets(Controller);
				wallet.getFrame().setVisible(true);
				frame.setVisible(false);
			}
		});
		btnAdministracionCarteras.setBounds(626, 10, 203, 23);
		frame.getContentPane().add(btnAdministracionCarteras);
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	private void fillTableSearch(DefaultTableModel model, int index) {
		String  publicKey=Controller.getWallet(index).getPublicKey().toString();
		System.out.println(Controller.getBlockchain().getBlock());
		for(int i=0;i<Controller.getBlockchain().getBlock().size();i++) {
			String number= (""+i);
			String previousHash= Controller.getBlockchain().getBlock().get(i).previousHash;
			String hash= Controller.getBlockchain().getBlock().get(i).hash;
			String envio="";
			String recibo="";
			String valor="";
			Boolean pass=false;
			System.out.println("More "+Controller.getBlockchain().getBlock());
			
			try {
			PublicKey publicKeyEnvio=Controller.getBlockchain().getBlock().get(i).getTransactions().get(0).getSender();
			PublicKey publicKeyRecibo=Controller.getBlockchain().getBlock().get(i).getTransactions().get(0).getReciepient();
			
			
			System.out.println("--------------------");
			System.out.println(publicKey.toString());
			System.out.println(publicKeyEnvio.toString());
			
			if(publicKeyEnvio.toString().contentEquals(publicKey)||publicKeyRecibo.toString().contentEquals(publicKey)) {
				pass=true;
				if(i!=0) {
					envio= Controller.searchWallet(publicKeyEnvio).getID();
				}else {
					envio= ""+0;
				}
				recibo= Controller.searchWallet(publicKeyRecibo).getID();
				valor= (""+Controller.getBlockchain().getBlock().get(i).getTransactions().get(0).getValue());
			}	
			}catch(IndexOutOfBoundsException e) {
				envio="Transfer aborted";
				valor="Transfer aborted";
				recibo="Transfer aborted";
			}
			
			String[] data= {number, previousHash, hash, envio, recibo, valor};
			if(pass) {
			model.addRow(data);
			}
		}
		
	}
	
	private void fillTable(DefaultTableModel model) {
		for(int i=0;i<Controller.getBlockchain().getBlock().size();i++) {
			String number= (""+i);
			String previousHash= Controller.getBlockchain().getBlock().get(i).previousHash;
			String hash= Controller.getBlockchain().getBlock().get(i).hash;
			String envio;
			String recibo;
			String valor;
			
			try {
			PublicKey publicKeyEnvio=Controller.getBlockchain().getBlock().get(i).getTransactions().get(0).getSender();
			PublicKey publicKeyRecibo=Controller.getBlockchain().getBlock().get(i).getTransactions().get(0).getReciepient();
			
			if(i!=0) {
			envio= Controller.searchWallet(publicKeyEnvio).getID();
			}else {
			envio= ""+0;
			}
			recibo= Controller.searchWallet(publicKeyRecibo).getID();
			valor= (""+Controller.getBlockchain().getBlock().get(i).getTransactions().get(0).getValue());
			
			}catch(IndexOutOfBoundsException e) {
				 envio="Transfer aborted";
				 valor="Transfer aborted";
				 recibo="Transfer aborted";
			}
			
			String[] data= {number, previousHash, hash, envio, recibo, valor};
			model.addRow(data);
		}
		
	}
}
