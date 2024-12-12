package util;

import java.util.ArrayList;
import interfaces.*;
import model.*;

public class ExecUtils {

	public static void generateWallets(ArrayList<Wallet_IF> wallets) {
		
		wallets.add(new Wallet("Erick"));
		wallets.add(new Wallet("Aryosmar"));
		wallets.add(new Wallet("Pedro"));
		wallets.add(new Wallet("Sângela"));
		wallets.add(new Wallet("Gabriel"));
		wallets.add(new Wallet("Hacker 1"));
		wallets.add(new Wallet("Hacker 2"));
		
	}
	
	public static void displayWallets(ArrayList<Wallet_IF> wallets) throws InterruptedException{
		int i = 0;
		for(Wallet_IF wallet : wallets){
			System.out.println(i + "-");
            System.out.println(wallet.toString());
            i++;
            Thread.sleep(500);
        }
		System.out.println();
	}
	
	public static void displayTransactionAndBalanceWallet(ArrayList<Wallet_IF> wallets) throws InterruptedException{
		int i = 0;
		for(Wallet_IF wallet : wallets){
			System.out.println(i + "-");
			wallet.displayTransactionAndBalance();
			i++;
            Thread.sleep(500);
        }
		System.out.println();
	}
	
	public static void resetWallets(ArrayList<Wallet_IF> wallets) {
		for(Wallet_IF wallet : wallets){
			wallet.resetWallet();
        }
	}
	
	public static void examplesOfInvalidAddresses() throws InterruptedException {
		
		Thread.sleep(1500);
		System.out.println("#1 - DXXXX3216306ff514ef6d2ba9bae2f5a97ad4f763f7e214cc0");
        System.out.println("Prefixo errado");
        System.out.println(TextColor.RED_BOLD + Transaction.isValidAddress("DXXXX3216306ff514ef6d2ba"
        		+ "9bae2f5a97ad4f763f7e214cc0") + TextColor.RESET + "\n");
        
        Thread.sleep(1500);
        System.out.println("#2 - DERFN3256906ff514ef6d2ba9bae2f5a97ad4f763f7e214cc0");
        System.out.println("7 Dígitos posteriores != 21");
        System.out.println(TextColor.RED_BOLD + Transaction.isValidAddress("DERFN3256906ff514ef6d2ba"
        		+ "9bae2f5a97ad4f763f7e214cc0") + TextColor.RESET + "\n");
        
        Thread.sleep(1500);
        System.out.println("#3 - DERFN3256906ff514ef6d2ba9bae2f5a97ad4mnghayh214cc0");
        System.out.println("No final tem presença de caracteres não hexadecimal");
        System.out.println(TextColor.RED_BOLD + Transaction.isValidAddress("DERFN3256906ff514ef6d2ba"
        		+ "9bae2f5a97ad4mnghayh214cc0") + TextColor.RESET + "\n");
        
        Thread.sleep(1500);
        
	}
	
	@SuppressWarnings("unchecked")
	public static void addTransactionToBlockAndBlockchain(Blockchain_IF blockchain,
			ArrayList<Wallet_IF> wallets) {
		// Criando e adicionando o primeiro bloco
        ArrayList<Transaction> transactions = new ArrayList<Transaction>();
        transactions.add(new Transaction(wallets.get(0), blockchain.getAmountCoinBase(), 0));
        transactions.add(new Transaction(wallets.get(0), wallets.get(1), 200.78));
        transactions.add(new Transaction(wallets.get(0), wallets.get(2), 250.02));
        
        Block block1 = new Block(blockchain.getLatestBlock().getId()+1,
        	(ArrayList<Transaction>) transactions.clone(),
        	blockchain.getLatestBlock().getHash(),
        	blockchain.getDifficulty(),
        	wallets.get(0)
        );
        blockchain.addBlock(block1);
        transactions.clear();
        
        // Criando e adicionando o segundo bloco
        transactions.add(new Transaction(wallets.get(0), blockchain.getAmountCoinBase(), 0));
        transactions.add(new Transaction(wallets.get(0), wallets.get(3), 307.87));
        transactions.add(new Transaction(wallets.get(2), wallets.get(4), 179.44));
        
        Block block2 = new Block(blockchain.getLatestBlock().getId()+1,
        	(ArrayList<Transaction>) transactions.clone(),
        	blockchain.getLatestBlock().getHash(),
        	blockchain.getDifficulty(),
        	wallets.get(0)
        );
        blockchain.addBlock(block2);
        transactions.clear();
	}
	
	public static void adulterateBlockchain(Blockchain_IF blockchain,
			ArrayList<Wallet_IF> wallets) {
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();
		wallets.get(0).getTransactions().remove(1);
		wallets.get(0).getTransactions().remove(1);
		wallets.get(0).getTransactions().remove(1);
		
        transactions.add(new Transaction(wallets.get(5), blockchain.getAmountCoinBase(), 0));
        transactions.add(new Transaction(wallets.get(0), wallets.get(5), 200.78));
        transactions.add(new Transaction(wallets.get(0), wallets.get(6), 250.02));
		
		blockchain.getChain().get(1).setTransactions(transactions, blockchain.getDifficulty());;
	}
	
	public static void validateBlockchain(Blockchain_IF blockchain) {
		if(blockchain.isChainValid()) {
			System.out.println("\n" + TextColor.GREEN_BOLD + "BLOCKCHAIN É VÁLIDA" + TextColor.RESET);
        }else {
        	System.out.println("\n" + TextColor.RED_BOLD + "BLOCKCHAIN É INVÁLIDA" + TextColor.RESET);
        }
	}
	
	public static void displayBlockchain(Blockchain_IF blockchain) throws InterruptedException {
		Thread.sleep(1500);
        System.out.println();
        blockchain.displayChain();
        System.out.println();
        Thread.sleep(2000);
	}
	
	public static void threePoints() throws InterruptedException {
		Thread.sleep(600);
        System.out.print("\n.");
        Thread.sleep(600);
        System.out.print(".");
        Thread.sleep(600);
        System.out.print(".");
        Thread.sleep(600);
	}	

}
