package controller;

import model.NewBlockChain;
import model.Block;
import model.Wallet;

public class NewBlockChainController {
	

	private NewBlockChain Blockchain;

	public NewBlockChainController (){
		Blockchain=new NewBlockChain();
	}	
	
	public NewBlockChain getBlockchain() {
		return Blockchain;
	}
	public void setBlockchain(NewBlockChain blockchain) {
		Blockchain = blockchain;
	}
	
	public void transfer(Wallet Sender, Wallet Reciver, Float value) {
		
		int index=Blockchain.blockchain.size()-1;
		
		Block block = new Block(Blockchain.blockchain.get(index).hash);
		block.addTransaction(Sender.sendFunds( Reciver.publicKey, value));
		Blockchain.addBlock(block);
		System.out.println("\nWalletA's balance is: " + Sender.getBalance());
		System.out.println("WalletB's balance is: " + Reciver.getBalance());
	}

	
}
