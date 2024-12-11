package model;

import java.util.ArrayList;

import interfaces.Block_IF;
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
	public Block(int id, ArrayList<Transaction> transactions, String previousHash, int difficulty) {
		// Guardando atributos já transmitidos
		this.id = id;
		this.transactions = transactions;
        this.previousHash = previousHash;
		
        // Solicita a hora local, formata ela no padrão BR e guarda no carimbo de tempo
        this.timestamp = generateTimestamp();
		
        // Gera o nonce, ou seja, realiza o processo de mineração do Proof of Work(PoW)
        mineBlock(difficulty);
	}
	
	//methods
	private String generateTimestamp() {
		LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter DTFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return DTFormat.format(currentDateTime);
	}
	
	private void mineBlock(int difficulty) {
	    // Define a quantidade de "0000" de prefixo com base na dificuldade
	    String target = new String(new char[difficulty]).replace('\0', '0');
	    
	    // Inicializa o nonce em 0 e tenta encontrar um hash válido
	    this.nonce = 0;
	    this.hash = calculateHash();
	    
	    // Loop até encontrar um hash que tenha a dificuldade
	    while (!this.hash.substring(0, difficulty).equals(target)) {
	        this.nonce++;
	        this.hash = calculateHash(); 
	    }
	    
	    System.out.println(TextColor.BLUE_BOLD + "-> Bloco minerado com sucesso! Nonce: " 
	    + this.nonce + ", Hash: " + this.hash + TextColor.RESET);
	}
	
	private void updateBlock(int difficulty) {
		mineBlock(difficulty);
	}
	
	public void checkBlock(int difficulty) {
		this.hash = calculateHash();
	}
	
	@Override
	public String calculateHash() {
		// Concatena todos os dados necessários para gerar o hash do bloco.
		String dataToHash = this.id + this.timestamp + this.transactions
				+ this.previousHash + this.nonce;
		
		// Envia o hash para a função applySHA256 e retorna o resultado.
		return HashUtil.applySHA256(dataToHash);
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
