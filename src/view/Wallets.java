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

import javax.swing.JOptionPane;

import controller.NewBlockChainController;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class Wallets {

	private JFrame frame;
	private JTextField textField;
	private NewBlockChainController Controller;
	private JComboBox<String> comboBox;
	private JComboBox<String> comboBox_1;
	private JLabel lblValorDeLa;
	private JLabel lblValororigen;
	private JLabel lblCarteraDeOrigen;
	private JLabel lblCarteraDeDestino;
	private JLabel lblValorDeLa_1;
	private JLabel lblValordestino;
	
	public Wallets(NewBlockChainController Controller) {
		this.Controller = Controller;
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
		
		JButton btnCrearNuevaCartera = new JButton("Crear Nueva Cartera");
		btnCrearNuevaCartera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Controller.getBlockchain().addWallet();
				
				int tamaño= Controller.getBlockchain().getWallet().size()-1;
				
				comboBox.addItem(Controller.getBlockchain().getWallet().get(tamaño).getID());
				comboBox_1.addItem(Controller.getBlockchain().getWallet().get(tamaño).getID());
				
			}
		});
		btnCrearNuevaCartera.setBounds(10, 227, 190, 23);
		frame.getContentPane().add(btnCrearNuevaCartera);
		
		JButton btnBuscarTransacciones = new JButton("Buscar Transacciones");
		btnBuscarTransacciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    BlockChain BlockChainView=new BlockChain(Controller);
			    BlockChainView.getFrame().setVisible(true);
			    frame.setVisible(false);
			}
		});
		
		btnBuscarTransacciones.setBounds(210, 227, 198, 23);
		frame.getContentPane().add(btnBuscarTransacciones);
		
		JButton btnTransfer = new JButton("Transfer");
		btnTransfer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!textField.getText().isEmpty()) {
					if(comboBox.getSelectedIndex()!=comboBox_1.getSelectedIndex()) {
					Controller.transfer(Controller.getWallet(comboBox.getSelectedIndex()), Controller.getWallet(comboBox_1.getSelectedIndex()), Float.parseFloat(textField.getText()));
					lblValororigen.setText(""+Controller.getWallet(comboBox.getSelectedIndex()).getBalance());
					lblValordestino.setText(""+Controller.getWallet(comboBox_1.getSelectedIndex()).getBalance());
					}else {
						JOptionPane.showMessageDialog(frame, "No se puede transferir a la misma cartera");
					}
				}else {
					JOptionPane.showMessageDialog(frame, "Por favor incluya un valor");
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
		lblValorDeLa = new JLabel("Dinero en la cartera");
		lblValorDeLa.setBounds(10, 128, 135, 14);
		frame.getContentPane().add(lblValorDeLa);
		
		lblValororigen = new JLabel("ValorOrigen");
		lblValororigen.setBounds(148, 128, 46, 14);
		frame.getContentPane().add(lblValororigen);
		
		lblCarteraDeOrigen = new JLabel("Cartera de origen");
		lblCarteraDeOrigen.setBounds(10, 68, 135, 14);
		frame.getContentPane().add(lblCarteraDeOrigen);
		
		lblCarteraDeDestino = new JLabel("Cartera de destino");
		lblCarteraDeDestino.setBounds(400, 68, 106, 14);
		frame.getContentPane().add(lblCarteraDeDestino);
		
		lblValorDeLa_1 = new JLabel("Dinero en la cartera");
		lblValorDeLa_1.setBounds(410, 128, 114, 14);
		frame.getContentPane().add(lblValorDeLa_1);
		
		lblValordestino = new JLabel("ValorDestino");
		lblValordestino.setBounds(568, 128, 46, 14);
		frame.getContentPane().add(lblValordestino);
		
	    PlainDocument doc = (PlainDocument) textField.getDocument();
	    doc.setDocumentFilter(new MyIntFilter());
	    
		lblValororigen.setText(""+Controller.getWallet(comboBox.getSelectedIndex()).getBalance());
		lblValordestino.setText(""+Controller.getWallet(comboBox_1.getSelectedIndex()).getBalance());
	    
	    ItemListener itemListener = new ItemListener() {
	        public void itemStateChanged(ItemEvent itemEvent) {
	        	lblValororigen.setText(""+Controller.getWallet(comboBox.getSelectedIndex()).getBalance());
	        }
	      };
	    comboBox.addItemListener(itemListener);
	      
		ItemListener itemListener2 = new ItemListener() {
		    public void itemStateChanged(ItemEvent itemEvent) {
		        lblValordestino.setText(""+Controller.getWallet(comboBox_1.getSelectedIndex()).getBalance());
		    }
		 };
		comboBox_1.addItemListener(itemListener2);
	    
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
		    	  JOptionPane.showMessageDialog(frame, "No se permite insertar valores no numericos");
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

		      if(sb.toString().length() == 0)
		      {
		    	  super.replace(fb, offset, length, "", null); 
		      } else {
		    		  if (test(sb.toString())) {
		    			  super.remove(fb, offset, length); 
		    		  } else {
		    			  // warn the user and don't allow the insert 
		    		  } 
		      }
		      
		   }//End of throw
		}
	
	
	
	
	
	
	
	
}
