package runMVC;

import java.security.Security;
import model.Block;
import model.Transaction;
import model.TransactionOutput;
import model.Wallet;
import view.Wallets;
import model.NewBlockChain;
import controller.NewBlockChainController;

public class runMVC {
	public static void main(String[] args) {	
		
		NewBlockChain BlockChain=new NewBlockChain();
		NewBlockChainController Controller= new NewBlockChainController();
		//add our blocks to the blockchain ArrayList:
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); //Setup Bouncey castle as a Security Provider
		
		
		//Create wallets:
		BlockChain.addWallet(new Wallet());
		BlockChain.addWallet(new Wallet());
		Wallet coinbase = new Wallet();
		
		//create genesis transaction, which sends 100 NoobCoin to walletA: 
		BlockChain.genesisTransaction = new Transaction(coinbase.publicKey, BlockChain.getWallet().get(0).publicKey, 100f, null);
		BlockChain.genesisTransaction.generateSignature(coinbase.privateKey);	 //manually sign the genesis transaction	
		BlockChain.genesisTransaction.transactionId = "0"; //manually set the transaction id
		BlockChain.genesisTransaction.outputs.add(new TransactionOutput(BlockChain.genesisTransaction.reciepient, BlockChain.genesisTransaction.value, BlockChain.genesisTransaction.transactionId)); //manually add the Transactions Output
		BlockChain.UTXOs.put(BlockChain.genesisTransaction.outputs.get(0).id, BlockChain.genesisTransaction.outputs.get(0)); //its important to store our first transaction in the UTXOs list.
		
		System.out.println("Creating and Mining Genesis block... ");
		Block genesis = new Block("0");
		genesis.addTransaction(BlockChain.genesisTransaction);
		BlockChain.addBlock(genesis);
		
		//testing
		Block block1 = new Block(genesis.hash);
		System.out.println("\nWalletA's balance is: " + BlockChain.getWallet().get(0).getBalance());
		System.out.println("WalletB's balance is: " + BlockChain.getWallet().get(1).getBalance());
		
		System.out.println("\nWalletA is Attempting to send funds (40) to WalletB...");
		block1.addTransaction(BlockChain.getWallet().get(0).sendFunds(BlockChain.getWallet().get(1).publicKey, 40f));
		BlockChain.addBlock(block1);
		System.out.println("\nWalletA's balance is: " + BlockChain.getWallet().get(0).getBalance());
		System.out.println("WalletB's balance is: " + BlockChain.getWallet().get(1).getBalance());
		
		Block block2 = new Block(block1.hash);
		System.out.println("\nWalletA Attempting to send more funds (1000) than it has...");
		block2.addTransaction(BlockChain.getWallet().get(0).sendFunds(BlockChain.getWallet().get(1).publicKey, 1000f));
		BlockChain.addBlock(block2);
		System.out.println("\nWalletA's balance is: " + BlockChain.getWallet().get(0).getBalance());
		System.out.println("WalletB's balance is: " + BlockChain.getWallet().get(1).getBalance());
		
		Block block3 = new Block(block2.hash);
		System.out.println("\nWalletB is Attempting to send funds (20) to WalletA...");
		block3.addTransaction(BlockChain.getWallet().get(1).sendFunds( BlockChain.getWallet().get(0).publicKey, 20));
		System.out.println("\nWalletA's balance is: " + BlockChain.getWallet().get(0).getBalance());
		System.out.println("WalletB's balance is: " + BlockChain.getWallet().get(1).getBalance());
		
		Controller.setBlockchain(BlockChain);
		
		Wallets WalletView=new Wallets(Controller);
		WalletView.getFrame().setVisible(true);
		
		BlockChain.isChainValid();
	}
	
}
