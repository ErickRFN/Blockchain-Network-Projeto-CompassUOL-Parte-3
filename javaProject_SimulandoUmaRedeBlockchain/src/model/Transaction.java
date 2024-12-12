package model;

import interfaces.Transaction_IF;
import interfaces.Wallet_IF;
import util.TextColor;

public class Transaction implements Transaction_IF {
	
	//atributes
	private String addressSender;
    private String addressReceiver;
    private Double amount;
    private Double fee;

    //constructor
    public Transaction(Wallet_IF sender, Wallet_IF receiver, double amount) {
    	
    	String senderAddress = sender.getAddress();
        String receiverAddress = receiver.getAddress();
        
        if (isValidAddress(senderAddress) && isValidAddress(receiverAddress)) {
            this.addressSender = senderAddress;
            this.addressReceiver = receiverAddress;
            this.amount = amount;
            this.fee = calculateFee(amount);
            
            System.out.println(TextColor.CYAN_BOLD + "# ENDEREÇOS DE TRANSAÇÕES VALIDADOS: \n" 
            + senderAddress.substring(0, 20) + " -> " + receiverAddress.substring(0, 20) + " = " + amount);
            
            sender.addTransaction(this);
            receiver.addTransaction(this);
            
            System.out.println("- SALDOS ATUALIZADOS: " 
            + "\nSender: " + sender.getBalance() + "\nReceiver: " + receiver.getBalance() + "\n" + TextColor.RESET);
            
        } else {
        	this.addressSender = "INVALID TRASACTION";
            this.addressReceiver = "INVALID TRASACTION";
            this.amount = 0.0;
            this.fee = 0.0;
        }
        
    }
    
    // Construtor de transação coinbase ou de taxas
    public Transaction(Wallet_IF miner, double amount, int type) {
  
    	String minerAddress = miner.getAddress();
    	
    	if (isValidAddress(minerAddress)) {
    		if (type == 0) { // 0- COINBASE & 1- FEES
    			this.addressSender = "COINBASE";
    		}else {
    			this.addressSender = "FEES";
    		}
            
            this.addressReceiver = minerAddress;
            this.amount = amount;
            this.fee = 0.0;
            
            System.out.println(TextColor.YELLOW_BOLD + "# ENDEREÇOS DE MINERADOR VALIDADO: \n" 
            + this.addressSender + " -> " + minerAddress.substring(0, 20) + " = " + amount);
            
            miner.addTransaction(this);
            
            System.out.println("- SALDO ATUALIZADO: " 
            + "\nMiner: " + miner.getBalance() + "\n" + TextColor.RESET);
            
        } else {
        	this.addressSender = "INVALID TRASACTION";
            this.addressReceiver = "INVALID TRASACTION";
            this.amount = 0.0;
            this.fee = 0.0;
        }
    	
    }
    
    public static boolean isValidAddress(String address){
    	
        if (!address.startsWith("DERFN")) {
        	return false;
        }
        	
        String numericPart = address.substring(5, 12);  
        String hexPart = address.substring(12);
            
        if (!numericPart.matches("\\d{7}") || !isSum21(numericPart)) {
        	return false;
        }

        return hexPart.length() == 38 && hexPart.matches("[0-9a-fA-F]+");
        
    }
    
    // auxiliary methods
    private static boolean isSum21(String numericPart) {
    	int sum = 0;
    		
        for (int i = 0; i < numericPart.length(); i++) {
            sum += Integer.parseInt(numericPart.substring(i, i + 1));
        }
        
        return sum == 21;
    }
    
    private static double calculateFee(double amount) {
    	return amount * 0.01; // 1% fee
    }
    
    //getters and setters
	@Override
	public String getAddressSender() {
		return addressSender;
	}

	@Override
	public String getAddressReceiver() {
		return addressReceiver;
	}

	@Override
	public double getAmount() {
		return amount;
	}
	
	@Override
	public Double getFee() {
        return fee;
    }
	
	//to string method
	@Override
    public String toString() {
        return addressSender + " -> " + addressReceiver + ": " + amount + "\n";
    }
	
}
