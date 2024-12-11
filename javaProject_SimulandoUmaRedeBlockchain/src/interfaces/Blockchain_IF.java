package interfaces;

import java.util.ArrayList;

import model.Block;

public interface Blockchain_IF {

    Block getLatestBlock();
    void addBlock(Block newBlock);
    boolean isChainValid();
    void displayChain();
    Double getAmountCoinBase();
    int getDifficulty();
    ArrayList<Block> getChain();
    
}

