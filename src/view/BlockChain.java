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

		
		Filltable(model);
		
		model.removeRow(1);

		
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(47, 11, 283, 20);
		frame.getContentPane().add(comboBox);
		for(int i=0;i<Controller.getBlockchain().getWallet().size();i++) {
			comboBox.addItem(Controller.getBlockchain().getWallet().get(i).getID());
		}
		
		JButton btnBusqueda = new JButton("Busqueda");
		btnBusqueda.setBounds(340, 10, 89, 23);
		frame.getContentPane().add(btnBusqueda);
		
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (model.getRowCount() > 0) {
				    for (int i = model.getRowCount() - 1; i > -1; i--) {
				    	model.removeRow(i);
				    }
				}
				
				Filltable(model);
			}
		});
		btnReset.setBounds(839, 10, 89, 23);
		frame.getContentPane().add(btnReset);
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	private void Filltable(DefaultTableModel model) {
		for(int i=1;i<Controller.getBlockchain().getBlock().size();i++) {
			String number= (""+i);
			String previousHash= Controller.getBlockchain().getBlock().get(i).previousHash;
			String hash= Controller.getBlockchain().getBlock().get(i).hash;
			
			String envio= Controller.getBlockchain().getBlock().get(i).getTransactions().get(0).sender.toString();
			String recibo= Controller.getBlockchain().getBlock().get(i).getTransactions().get(0).reciepient.toString();
			String valor= (""+Controller.getBlockchain().getBlock().get(i).getTransactions().get(0).getValue());
			
			String[] data= {number, previousHash, hash, envio, recibo, valor};
			model.addRow(data);
		}
		
	}
	
	
	
}
