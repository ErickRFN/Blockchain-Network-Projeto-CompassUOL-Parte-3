package interfaces;

import java.util.ArrayList;

public interface Blockchain_IF {

    Block_IF getLatestBlock();
    void addBlock(Block_IF newBlock);
    boolean isChainValid();
    void displayChain();
    Double getAmountCoinBase();
    int getDifficulty();
    ArrayList<Block_IF> getChain();
    
}

