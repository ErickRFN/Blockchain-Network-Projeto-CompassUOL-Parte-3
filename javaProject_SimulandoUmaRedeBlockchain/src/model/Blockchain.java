package model;

import java.util.ArrayList;
import interfaces.Blockchain_IF;
import interfaces.Wallet_IF;

public class Blockchain implements Blockchain_IF {
	
	//attributes
	private ArrayList<Block> chain;
	private int difficulty = 5;
	private Double amountCoinBase = 1000.00;

	//construct
	public Blockchain(Wallet_IF creatorsWallet) {
        initializeBlockchain();
        createGenesisBlock(creatorsWallet);
    }
	
	//methods
	private void initializeBlockchain() {
		this.chain = new ArrayList<Block>();
	}
	
    private void createGenesisBlock(Wallet_IF creatorsWallet) {
    	Transaction coinBaseTransaction = new Transaction(creatorsWallet, this.amountCoinBase, 0);
    	ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    	transactions.add(coinBaseTransaction);
    	
    	Block genesisBlock = new Block(0, transactions, "0", this.difficulty, creatorsWallet);
    	
        this.chain.add(genesisBlock);
    }
    
    @Override
	public void addBlock(Block newBlock) {
		this.chain.add(newBlock);
	}
    
    @Override
	public boolean isChainValid() {
    	
    	int indexCurrentBlock; 
    	for(indexCurrentBlock = this.chain.size() - 1; indexCurrentBlock > 0; indexCurrentBlock--) {
    		Block currentBlock = this.chain.get(indexCurrentBlock);
    		Block previousBlock = this.chain.get(indexCurrentBlock - 1);
    		
    		currentBlock.checkBlock(this.difficulty);
    		previousBlock.checkBlock(this.difficulty);
    		
    		if(!currentBlock.getPreviousHash().equals(previousBlock.getHash())){
    			return false;
    		}
    	}
    	
		return true;
		
	}

    //getters and setters
	@Override
	public Block getLatestBlock() {
		return chain.get(chain.size() - 1);
	}
	
	@Override
	public ArrayList<Block> getChain() {
		return this.chain;
	}
	
	@Override
	public int getDifficulty() {
		return this.difficulty;
	}
	
	@Override
	public Double getAmountCoinBase() {
		return this.amountCoinBase;
	}

	//toString
	@Override
	public void displayChain() {
		for (Block block : chain) {
            System.out.println("Block ID: " + block.getId());
            System.out.println("Timestamp: " + block.getTimestamp());
            System.out.println("Transactions: \n" + block.getTransactions());
            System.out.println("Previous Hash: " + block.getPreviousHash());
            System.out.println("Hash: " + block.getHash());
            System.out.println("Nonce: " + block.getNonce());
            System.out.println("-------------------------------");
        }
	}

}
