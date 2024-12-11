package interfaces;

import model.Block;

public interface Blockchain_IF {

    Block getLatestBlock();
    void addBlock(Block newBlock);
    boolean isChainValid();
    void displayChain();
    Double getAmountCoinBase();
    
}

