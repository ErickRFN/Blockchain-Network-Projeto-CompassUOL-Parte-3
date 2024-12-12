package model;

import java.util.ArrayList;

import interfaces.Block_IF;
import interfaces.Wallet_IF;
import util.HashUtil;
import util.TextColor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Block implements Block_IF {
	
	//attributes
	private int id;
	private String timestamp;
	private ArrayList<Transaction> transactions;
	private String previousHash;
	private String hash;
	private int nonce;
	
	//constructor
	public Block(int id, ArrayList<Transaction> transactions, String previousHash, int difficulty, Wallet_IF miner) {
		this.id = id;
		
		transactions.add(transFees(transactions, miner));
		
		this.transactions = transactions;
        this.previousHash = previousHash;
		
        this.timestamp = generateTimestamp();
		
        mineBlock(difficulty);
	}
	
	//methods
	@Override
	public void checkBlock(int difficulty) {
		this.hash = calculateHash();
	}
	
	@Override
	public String calculateHash() {
		String dataToHash = this.id + this.timestamp + this.transactions
				+ this.previousHash + this.nonce;
		
		return HashUtil.applySHA256(dataToHash);
	}
	
	//auxiliary methods
	private String generateTimestamp() {
		LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter DTFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return DTFormat.format(currentDateTime);
	}
	
	private void mineBlock(int difficulty) {
	    String target = new String(new char[difficulty]).replace('\0', '0');
	    
	    this.nonce = 0;
	    this.hash = calculateHash();
	    
	    while (!this.hash.substring(0, difficulty).equals(target)) {
	        this.nonce++;
	        this.hash = calculateHash(); 
	    }
	    
	    System.out.println(TextColor.BLUE_BOLD + "-> Bloco minerado com sucesso! Nonce: " 
	    + this.nonce + ", Hash: " + this.hash + TextColor.RESET + "\n");
	}
	
	private void updateBlock(int difficulty) {
		mineBlock(difficulty);
	}
	
	private Transaction transFees(ArrayList<Transaction> transactions, Wallet_IF miner) {
		double totalFees = 0;
        
        for (Transaction transaction : transactions) {
        	totalFees += transaction.getFee();
        }
        
        return new Transaction(miner, totalFees, 1);
	}
	
	//getters and setters
	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public String getPreviousHash() {
		return this.previousHash;
	}

	@Override
	public String getHash() {
		return this.hash;
	}

	@Override
	public String getTimestamp() {
		return this.timestamp;
	}

	@Override
	public ArrayList<Transaction> getTransactions() {
		return this.transactions;
	}

	@Override
	public int getNonce() {
		return this.nonce;
	}
	
	public void setTransactions(ArrayList<Transaction> transactions, int difficulty) {
		this.transactions = transactions;
		updateBlock(difficulty);
	}

}
