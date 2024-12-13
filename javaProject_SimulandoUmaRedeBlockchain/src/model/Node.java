package model;

import java.util.ArrayList;
import interfaces.*;

public class Node implements Node_IF {
	
	private Blockchain_IF blockchain;
	private ArrayList<Node_IF> neighbors;
	private Wallet_IF walletNode;
	
	//construct
	public Node(Blockchain_IF blockchain, Wallet_IF walletNode) {
        this.blockchain = blockchain;
        this.walletNode = walletNode;
        this.neighbors = new ArrayList<Node_IF>();
    }
	
	//methods
	@Override
	public void mineBlockNode(ArrayList<Transaction_IF> transactions) {
		Block_IF newBlock = new Block(
				this.blockchain.getLatestBlock().getId()+1,
				transactions, 
				this.blockchain.getLatestBlock().getHash(),
				this.blockchain.getDifficulty(),
				this.walletNode);
		
		this.blockchain.addBlock(newBlock);
		propagateLastestBlock();
	}
	
	@Override
	public void addNeighbor(Node_IF neighborAdd) {
		if(this.neighbors == null) {
			neighbors.add(neighborAdd);
			return;
		}
		
		for (Node_IF neighbor : this.neighbors) {
			if(neighbor.equals(neighborAdd)) {
				System.out.println("-> Par para adicionar já está na lista de conexões desse nó");
				return;
			}
		}
		neighbors.add(neighborAdd);
	}

	@Override
	public void removeNeighbor(Node_IF neighborRemove) {
		int i = 0;
		for (Node_IF neighbor : this.neighbors) {
			if(neighbor.equals(neighborRemove)) {
				neighbors.remove(i);
				return;
			}
		}
		System.out.println("-> Par para remover não foi encontrado na lista de conexões desse nó");
	}

	@Override
	public void propagateLastestBlock() {
		Block_IF thisLatestBlock = this.blockchain.getLatestBlock();
		
		for (Node_IF neighbor : this.neighbors) {
            neighbor.receiveLastestBlock(thisLatestBlock, this);
        }
	}

	@Override
	public void receiveLastestBlock(Block_IF blockReceived, Node_IF neighborSender) {
		Block_IF thisLatestBlock = this.blockchain.getLatestBlock();
		
		if(blockReceived.getId() > thisLatestBlock.getId()) {
			
			if(blockReceived.getId() == thisLatestBlock.getId()+1) {		
				blockReceived.checkHash();
				
				if(!blockReceived.checkDifficulty(this.blockchain.getDifficulty())) {
					return;
				}
				
				if(!blockReceived.getPreviousHash().equals(thisLatestBlock.getHash())) {
					return;
				}
				
				this.blockchain.addBlock(blockReceived);
				
				propagateLastestBlock();
			} else {
				updateChain(neighborSender.sendChain());
			}
			
		}
	}

	@Override
	public Blockchain_IF sendChain() {
		return this.blockchain;
	}

	@Override
	public void updateChain(Blockchain_IF newChain) {
		
		if(newChain.isChainValid()) {
			this.blockchain = newChain;
            propagateLastestBlock();
		}
		
	}
	
	//getters and setters
	@Override
	public Wallet_IF getWalletNode() {
		return this.walletNode;
	}
	
	@Override
	public Blockchain_IF getBlockchain() {
		return this.blockchain;
	}

}
