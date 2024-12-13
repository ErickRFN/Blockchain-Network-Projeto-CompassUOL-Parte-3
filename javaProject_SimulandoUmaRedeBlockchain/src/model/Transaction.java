package model;

import interfaces.Transaction_IF;
import interfaces.Wallet_IF;
import util.TextColor;

public class Transaction implements Transaction_IF {
	
	//atributes
	private Wallet_IF walletSender;
    private Wallet_IF walletReceiver;
    private String addressSender;
    private String addressReceiver;
    private Double amount;
    private Double fee;

    //constructor
    public Transaction(Wallet_IF sender, Wallet_IF receiver, double amount) {
    	
    	this.addressSender = sender.getAddress();
        this.addressReceiver = receiver.getAddress();
        
        if (isValidAddress(this.addressSender) && isValidAddress(this.addressReceiver)) {
            this.walletSender = sender;
            this.walletReceiver = receiver;
            this.fee = calculateFee(amount);
            this.amount = amount-this.fee;
            
            System.out.println(TextColor.CYAN_BOLD + "# ENDEREÇOS DE TRANSAÇÕES VALIDADOS: \n" 
            + this.addressSender.substring(0, 20) + " -> " + this.addressReceiver.substring(0, 20) + " = " + amount 
            + " (taxas: " + this.fee + ")");
            System.out.println("TRANSAÇÃO NÃO CONFIRMADA: FALTA MINERAR O BLOCO\n" + TextColor.RESET);
            
        } else {
        	declareTransactionInvalid();
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
    		this.walletSender = null;
            
    		this.walletReceiver = miner;
            this.addressReceiver = minerAddress;
            this.amount = amount;
            this.fee = 0.0;
            
            System.out.println(TextColor.YELLOW_BOLD + "# ENDEREÇOS DE MINERADOR VALIDADO: \n" 
            + this.addressSender + " -> " + minerAddress.substring(0, 20) + " = " + amount);
            System.out.println("TRANSAÇÃO NÃO CONFIRMADA: FALTA MINERAR O BLOCO\n" + TextColor.RESET);
            
        } else {
        	declareTransactionInvalid();
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
    	return amount * 0.05; // 5% fee
    }
    
    private void declareTransactionInvalid() {
    	this.walletReceiver = null;
    	this.walletSender = null;
    	this.addressSender = "INVALID TRASACTION";
        this.addressReceiver = "INVALID TRASACTION";
        this.amount = 0.0;
        this.fee = 0.0;
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
        return this.addressSender + " -> " + this.addressReceiver + ": " + amount +
        		" (taxas: " + this.fee + ")\n";
    }

	@Override
	public Wallet_IF getWallerSender() {
		return this.walletSender;
	}

	@Override
	public Wallet_IF getWallerReceiver() {
		return this.walletReceiver;
	}
	
}
