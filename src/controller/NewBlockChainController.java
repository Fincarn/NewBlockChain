package controller;

import model.NewBlockChain;

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

	
}
