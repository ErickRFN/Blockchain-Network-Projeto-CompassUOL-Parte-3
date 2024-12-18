package interfaces;

import java.util.ArrayList;

public interface Node_IF {
	
	public void addNeighbor(Node_IF neighbor);
	public void removeNeighbor(Node_IF neighbor);
	public void propagateLastestBlock();
	public void receiveLastestBlock(Block_IF blockReceived, Node_IF neighborSender);
	public Blockchain_IF sendChain();
	public void updateChain(Blockchain_IF newChain);
	public Wallet_IF getWalletNode();
	public void mineBlockNode(ArrayList<Transaction_IF> transactions);
	public Blockchain_IF getBlockchain();
	
}
