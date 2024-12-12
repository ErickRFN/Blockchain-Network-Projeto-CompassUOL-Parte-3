package interfaces;

import java.security.PublicKey;
import java.util.ArrayList;

public interface Wallet_IF {

	void generateKeyPair();
	String generateAddress();
	void addTransaction(Transaction_IF transaction);
	void setNickname(String nickname);
	PublicKey getPublicKey();
	String getNickname();
	String getAddress();
	Double getBalance();
	ArrayList<Transaction_IF> getTransactions();
	void resetWallet();
	void displayTransactionAndBalance();
	
}
