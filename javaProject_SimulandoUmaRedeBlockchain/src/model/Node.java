package model;

import java.util.List;
import interfaces.*;

public class Node implements Node_IF {
	
	private Blockchain_IF blockchain;
	private List<Node_IF> neighbors;
	
	//construct
	public Node(Blockchain_IF blockchain) {
        this.blockchain = blockchain;
    }
	
	//methods
	@Override
	public void addNeighbor(Node_IF neighborAdd) {
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

}
