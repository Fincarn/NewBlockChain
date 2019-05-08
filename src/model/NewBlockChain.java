package model;

import java.util.ArrayList;
import java.util.HashMap;

public class NewBlockChain {

	private  ArrayList<Block> blockchain = new ArrayList<Block>();
	private  ArrayList<Wallet> wallet=new ArrayList<Wallet>();
	public  int difficulty = 3;
	public  Transaction genesisTransaction;
	public  static HashMap<String,TransactionOutput> UTXOs = new HashMap<String,TransactionOutput>();
	public  static float minimumTransaction = 0.1f;

	public  Boolean isChainValid() {
		Block currentBlock; 
		Block previousBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');
		HashMap<String,TransactionOutput> tempUTXOs = new HashMap<String,TransactionOutput>(); //a temporary working list of unspent transactions at a given block state.
		tempUTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0));
		
		System.out.println(" El tamaño de la cadena es:"+blockchain.size());
		
		//loop through blockchain to check hashes:
		for(int i=1; i < blockchain.size(); i++) {
			
			System.out.println("------- Cycle: "+ i);
			
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i-1);

			System.out.println(" Block: "+ (i-1) +" info: "+previousBlock.getTransactions().size());
			System.out.println(" Block: "+ i +" info: "+currentBlock.getTransactions().size());
								
			//compare registered hash and calculated hash:
			if(!currentBlock.hash.equals(currentBlock.calculateHash()) ){
				System.out.println("#Current Hashes not equal");
				return false;
			}
			//compare previous hash and registered previous hash
			if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
				System.out.println("#Previous Hashes not equal");
				return false;
			}
			//check if hash is solved
			if(!currentBlock.hash.substring( 0, difficulty).equals(hashTarget)) {
				System.out.println("#This block hasn't been mined");
				return false;
			}
			
			//loop thru blockchains transactions:
			TransactionOutput tempOutput;
			for(int t=0; t <currentBlock.getTransactions().size(); t++) {
				Transaction currentTransaction = currentBlock.getTransactions().get(t);
				
				if(!currentTransaction.verifiySignature()) {
					System.out.println("#Signature on Transaction(" + t + ") is Invalid");
					return false; 
				}
				if(currentTransaction.getInputsValue() != currentTransaction.getOutputsValue()) {
					System.out.println("#Inputs are note equal to outputs on Transaction(" + t + ")");
					return false; 
				}
				
				for(TransactionInput input: currentTransaction.inputs) {	
					tempOutput = tempUTXOs.get(input.transactionOutputId);
					
					if(tempOutput == null) {
						System.out.println("#Referenced input on Transaction(" + t + ") is Missing");
						return false;
					}
					
					if(input.UTXO.value != tempOutput.value) {
						System.out.println("#Referenced input Transaction(" + t + ") value is Invalid");
						return false;
					}
					
					tempUTXOs.remove(input.transactionOutputId);
				}
				
				for(TransactionOutput output: currentTransaction.outputs) {
					tempUTXOs.put(output.id, output);
				}
				
				if( currentTransaction.outputs.get(0).reciepient != currentTransaction.getReciepient()) {
					System.out.println("#Transaction(" + t + ") output reciepient is not who it should be");
					return false;
				}
				if( currentTransaction.outputs.get(1).reciepient != currentTransaction.getSender()) {
					System.out.println("#Transaction(" + t + ") output 'change' is not sender.");
					return false;
				}
				
			}
			
		}
		System.out.println("Blockchain is valid");
		return true;
	}
	
	public  void addBlock(Block newBlock) {
		newBlock.mineBlock(difficulty);
		blockchain.add(newBlock);
	}
	
	public  void addWallet(Wallet NewWallet) {
		NewWallet.setID("Wallet "+wallet.size());
		getWallet().add(NewWallet);
	}
	
	public  void addWallet() {
		Wallet NewWallet=new Wallet();
		NewWallet.setID("Wallet "+wallet.size());
		wallet.add(NewWallet);
	}

	public ArrayList<Wallet> getWallet() {
		return wallet;
	}
	
	public ArrayList<Block> getBlock() {
		return blockchain;
	}

	public void setBlock(ArrayList<Block> blockchain) {
		this.blockchain = blockchain;
	}
	
}
