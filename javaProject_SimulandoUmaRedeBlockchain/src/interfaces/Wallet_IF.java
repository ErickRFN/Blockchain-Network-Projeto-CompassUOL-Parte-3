package interfaces;

import java.security.PublicKey;
import java.util.ArrayList;

import model.Transaction;

public interface Wallet_IF {

	void generateKeyPair();
	String generateAddress();
	void addTransaction(Transaction transaction);
	void setNickname(String nickname);
	PublicKey getPublicKey();
	String getNickname();
	String getAddress();
	Double getBalance();
	ArrayList<Transaction> getTransactions();
	void resetWallet();
	void displayTransactionAndBalance();
	
}
