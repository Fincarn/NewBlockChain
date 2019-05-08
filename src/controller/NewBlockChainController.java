package controller;

import model.NewBlockChain;

import java.security.PublicKey;

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
	
	public Wallet getWallet(int i) {
		
		return	Blockchain.getWallet().get(i);
	}
	
	public void transfer(Wallet Sender, Wallet Reciver, Float value) {
		
		int index=Blockchain.getBlock().size()-1;
		
		Block block = new Block(Blockchain.getBlock().get(index).hash);
		block.addTransaction(Sender.sendFunds( Reciver.getPublicKey(), value));
		Blockchain.addBlock(block);
		System.out.println("\nWalletA's balance is: " + Sender.getBalance());
		System.out.println("WalletB's balance is: " + Reciver.getBalance());
	}
	
	public Wallet searchWallet(PublicKey publicKey) {
		Wallet wallet;
		for(int i=0;i<Blockchain.getWallet().size();i++) {

			if(publicKey.toString().equalsIgnoreCase(Blockchain.getWallet().get(i).getPublicKey().toString())) {
				wallet=Blockchain.getWallet().get(i);
				return wallet;
			}
		}
		System.out.println("No se encontro");
		return null;	
	}

	
}
