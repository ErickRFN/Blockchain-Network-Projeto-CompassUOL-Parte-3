package model;

import java.util.ArrayList;
import interfaces.*;

public class Blockchain implements Blockchain_IF {
	
	//attributes
	private ArrayList<Block_IF> chain;
	private int difficulty = 5;
	private Double amountCoinBase = 1000.00;
	private ArrayList<Wallet_IF> wallets;

	//construct
	@SuppressWarnings("unchecked")
	public Blockchain(Wallet_IF creatorsWallet, Object wallets) {
		this.wallets = (ArrayList<Wallet_IF>) wallets;
        initializeBlockchain();
        createGenesisBlock(creatorsWallet);
    }
	
	//methods
	private void initializeBlockchain() {
		this.chain = new ArrayList<Block_IF>();
		
	}
	
    private void createGenesisBlock(Wallet_IF creatorsWallet) {
    	Transaction coinBaseTransaction = new Transaction(creatorsWallet, this.amountCoinBase, 0);
    	ArrayList<Transaction_IF> transactions = new ArrayList<Transaction_IF>();
    	transactions.add(coinBaseTransaction);
    	
    	Block genesisBlock = new Block(0, transactions, "0", this.difficulty, creatorsWallet);
    	
        addBlock(genesisBlock);
    }
    
    @Override
	public void addBlock(Block_IF newBlock) {
		this.chain.add(newBlock);
		confirmBlockAndTransactions(newBlock);
	}
    
    @Override
	public boolean isChainValid() {
    	
    	int indexCurrentBlock; 
    	for(indexCurrentBlock = this.chain.size() - 1; indexCurrentBlock > 0; indexCurrentBlock--) {
    		Block_IF currentBlock = this.chain.get(indexCurrentBlock);
    		Block_IF previousBlock = this.chain.get(indexCurrentBlock - 1);
    		
    		currentBlock.checkHash();
    		previousBlock.checkHash();
    		
    		if(!currentBlock.checkDifficulty(this.difficulty) ||
    				!previousBlock.checkDifficulty(this.difficulty)) {
                return false;
            }
    		
    		if(!currentBlock.getPreviousHash().equals(previousBlock.getHash())){
    			return false;
    		}
    	}
    	
		return true;
		
	}
    
    //auxiliary methods
    private void confirmBlockAndTransactions(Block_IF newBlock) {
    	ArrayList<Transaction_IF> transactions = newBlock.getTransactions();
    	
    	for(Transaction_IF transaction : transactions) {
    		
    		if(transaction.getWallerSender() != null &&
    				transaction.getWallerReceiver() != null) {
    			
    			int posAndContainsWalletSenderInList =
    					this.wallets.indexOf(transaction.getWallerSender());
    			int posAndContainsWalletReceiverInList =
    					this.wallets.indexOf(transaction.getWallerReceiver());
    			
    			verifyWalletAndAddTransaction(posAndContainsWalletSenderInList, transaction.getWallerSender(), transaction);
    			verifyWalletAndAddTransaction(posAndContainsWalletReceiverInList, transaction.getWallerReceiver(), transaction);
    			
    		} else if (transaction.getAddressReceiver() != null) {
    			
                int posAndContainsWalletReceiverInList =
                        this.wallets.indexOf(transaction.getWallerReceiver());
                
                verifyWalletAndAddTransaction(posAndContainsWalletReceiverInList, transaction.getWallerReceiver(), transaction);
    		
    		} else {
    			System.out.println("!!! Transação inválida por endereço inválido, nenhum saldo foi movido !!!");
    		}
    		
    	}
    }
    
    private void verifyWalletAndAddTransaction(int position, Wallet_IF wallet, Transaction_IF transaction) {
    	if(position != -1){
			
			this.wallets.get(position).addTransaction(transaction);;
			
		}else {
			
			addWallet(wallet);
			this.wallets.get(this.wallets.size() - 1).addTransaction(transaction);
		
		}
    }
    
    public void addWallet(Wallet_IF wallet) {
    	this.wallets.add(wallet);
    }

    //getters and setters
	@Override
	public Block_IF getLatestBlock() {
		return chain.get(chain.size() - 1);
	}
	
	@Override
	public ArrayList<Block_IF> getChain() {
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
	
	@Override
	public Wallet_IF getWallets() {
		return this.getWallets();
	}

	//toString
	@Override
	public void displayChain() {
		for (Block_IF block : chain) {
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
