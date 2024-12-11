package model;

import java.io.*;
import java.util.ArrayList;
import interfaces.Blockchain_IF;

public class Blockchain implements Blockchain_IF {
	
	//attributes
	private ArrayList<Block> chain;
	private int difficulty = 5;
	private Double amountCoinBase = 1000.00;

	//construct
	public Blockchain(Wallet creatorsWallet) {
        initializeBlockchain();
        createGenesisBlock(creatorsWallet);
    }
	
	public Blockchain(ArrayList<Block> backupChain) {
		this.chain = backupChain;
	}
	
	//methods
	private void initializeBlockchain() {
		this.chain = new ArrayList<Block>();
	}
	
    private void createGenesisBlock(Wallet creatorsWallet) {
    	// Criando a coinbase transaction destinada a mim no genesis block
    	Transaction coinBaseTransaction = new Transaction(creatorsWallet, this.amountCoinBase);
    	ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    	transactions.add(coinBaseTransaction);
    	
    	Block genesisBlock = new Block(0, transactions, "0", this.difficulty);
    	
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
    		
    		// Atualiza o hash do bloco atual e do anterior por segurança
    		currentBlock.checkBlock(this.difficulty);
    		previousBlock.checkBlock(this.difficulty);
    		
    		// Verifica se a hash do bloco anterior corresponde ao hash que está no bloco atual
    		if(!currentBlock.getPreviousHash().equals(previousBlock.getHash())){
    			return false;
    		}
    	}
    	
    	/*
    	 * Se nenhuma inconsistência for encrontrada, o laço FOR acabará e a blockchain
    	 * será considerada válida.
    	 */
		return true;
		
	}
    
	public Blockchain blockchainBackup() {
    	try {
            // Serializa a Blockchain para um stream de bytes em memória
            ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
            ObjectOutputStream outStream = new ObjectOutputStream(byteOutStream);
            outStream.writeObject(this);

            // Desserializa a Blockchain de volta para um novo objeto
            ByteArrayInputStream byteInStream = new ByteArrayInputStream(byteOutStream.toByteArray());
            ObjectInputStream inStream = new ObjectInputStream(byteInStream);

            return (Blockchain) inStream.readObject();  // Retorna a cópia 
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Erro ao clonar a blockchain", e);
        }    
    }

    //getters and setters
	@Override
	public Block getLatestBlock() {
		return chain.get(chain.size() - 1);
	}
	
	public ArrayList<Block> getChain() {
		return this.chain;
	}
	
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
