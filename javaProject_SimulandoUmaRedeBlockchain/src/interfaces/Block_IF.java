package interfaces;

import java.util.ArrayList;

import model.Transaction;

public interface Block_IF {
	
    String calculateHash();
    int getId();
    int getNonce();
    String getPreviousHash();
    String getHash();
    String getTimestamp();
    ArrayList<Transaction> getTransactions();
	void checkBlock(int difficulty);
	
}
