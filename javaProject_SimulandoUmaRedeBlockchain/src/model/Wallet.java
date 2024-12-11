package model;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import interfaces.Wallet_IF;

public class Wallet implements Wallet_IF {
	
	//attributes
	private String nickname;
	private PublicKey publicKey;
	@SuppressWarnings("unused")
	private PrivateKey privateKey; // Por enquanto, sem uso
	private String address;
	
	private ArrayList<Transaction> transactions;
	private Double balance;
	
	//constructor
	public Wallet(String nickname) {
		this.nickname = nickname;
        generateKeyPair();	
        this.address = generateAddress();
        
        this.transactions = new ArrayList<>();
        this.balance = 0.0;
    }
	
	// methods
	@Override
	public void generateKeyPair() {
		/*
		 * Utilizo o algoritmo de criptografia de curva elíptica (ECC) para gerar
		 * a chave privada e com ela a pública. Preferi ele pois utiliza apenas 256 bits e
		 * o algoritmo RSA 3072 bits o que usaria mais memória.
		 */
		
		try {
			// Determina o uso da criptografia ECC da biblioteca Java Security
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC"); 
			
			// Determina o uso da curva elíptica de 256 bits e o inicializa
			ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256r1");
			keyPairGenerator.initialize(ecSpec, new SecureRandom());
			
			// Gera um par de chaves
			KeyPair keyPair = keyPairGenerator.generateKeyPair();
            
            // Obtem a chave pública e a chave privada
            this.publicKey = keyPair.getPublic();
            this.privateKey = keyPair.getPrivate();
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar par de chaves", e);
        }
	}

	@Override
	public String generateAddress() {
		// Prefixo
		String prefix = "DERFN";
		
		/*
		 * Na sequência, farei um algoritmo que retornará 7 dígitos aleatórios
		 * de 0 a 9 que somados são sempre = 21, isso dá 209.525 possibilidades
		 * diferentes de retorno.
		 */
		String numericPart = generateSevenDigitsSum21();
		
		/*
		 * Agora para completar 50 caracteres, vou gerar 38 caracteres hexadecimais
		 * a partir da chave pública
		 */
		String hexPart = generateHexFromPublicKey();
		
		// Une tudo e retorna o endereço da carteira
		return prefix + numericPart + hexPart;
	}
	
	@Override
	public void addTransaction(Transaction transaction) {
		
		this.transactions.add(transaction);
		
		if(transaction.getAddressSender().equals(this.address)) {
			updateBalance(-transaction.getAmount());
		}else {
			updateBalance(transaction.getAmount());
		}
		
	}
	
	// auxiliary methods
	private String generateSevenDigitsSum21() {
		// Criando método randomico
		Random random = new Random();
		
		// Lista de números
		List<Integer> digits = new ArrayList<>();
		
		// inicializar soma
		int sum = 0;
        
        // Gerar 7 dígitos
        while (digits.size() < 7) {
        	
        	int digit = random.nextInt(10); // numeros de 0 a 9
        	
        	if(digit + sum <= 21 && digits.size() < 6) {
        		
        		digits.add(digit);
                sum += digit;
                
                // completa de 0 em caso de já ter completado 21
                if(sum == 21) {
                	int numberMissingOfNumbers = Math.abs(digits.size() - 7);
                	
                	for(int i = numberMissingOfNumbers; i > 0; i--) {
                		digits.add(0);
                	}
                }
                
        	} else if(digits.size() == 6) {
        		
        		/*
        		 *  Verifica se tem um numero que possa completar,
        		 *  se não tem limpa tudo e recomeça
        		 */
        		if(sum + 9 < 21) {
	        		sum = 0;
	        		digits.clear();
        		}else { // Se tiver, entra no else e descobre esse número e o coloca
        			int numberMissing = 21 - sum;
        			digits.add(numberMissing);
                    sum += numberMissing;
        		}
        		
        	}
            
        }
        
        // Converte os dígitos para String e retorna
        StringBuilder numericPart = new StringBuilder();
        for (int digit : digits) {
            numericPart.append(digit);
        }
        return numericPart.toString();
	}
	
	private void updateBalance(Double attBalance) {
		this.balance += attBalance;
	}
	
	private String generateHexFromPublicKey() {
        try {
        	
        	// Gera a instãncia de SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            
            // Calcula o hash a partir da chave pública
            byte[] hash = digest.digest(this.publicKey.getEncoded());
            
            // Transforma o array de bytes em string diretamente
            String hexString = new BigInteger(1, hash).toString(16);
            
            // Retorna os primeiros 38 caracteres
            return hexString.substring(0, 38);
            
        } catch (Exception e) {
        	
            throw new RuntimeException("Erro ao gerar parte hexadecimal do endereço", e);
            
        }
	}
	
	public void resetWallet() {
		this.transactions.clear();
		this.balance = 0.0;
	}
	
	public void displayTransactionAndBalance() {
		System.out.println("Endereço da Wallet: ");
		System.out.println("# " + this.address.substring(0, 20));
		System.out.println("Nickname da Wallet: ");
		System.out.println("# " + this.nickname);
		
		
		if(this.transactions.size() == 0) {
			System.out.println("## Nenhuma transação registrada ##\n");
			System.out.println("Saldo da carteira: " + this.balance + "\n");
            return;
		}
		
		int i = 0;
		for(Transaction transaction : this.transactions) {
			
			if(transaction.getAddressSender().equals(this.address)) {
				System.out.println(i + " - " + "SENT");
			}else {
				System.out.println(i + " - " + "RECEIVED");
			}
			i++;
			
			System.out.printf("%s", transaction);
		}
		
		System.out.println("\nSaldo da carteira: " + this.balance + "\n");
	}
	
	// getters and setters
	@Override
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Override
	public String getNickname() {
		return this.nickname;
	}
	
	@Override
	public PublicKey getPublicKey() {
		return this.publicKey;
	}

	@Override
	public String getAddress() {
		return this.address;
	}

	@Override
	public Double getBalance() {
		return this.balance;
	}
	
	@Override
	public ArrayList<Transaction> getTransactions() {
		return this.transactions;
	}
	
	// toString
	@Override
	public String toString() {
	    return "Wallet {" +
	    	   "\n  Nickname: " + this.nickname +
	           "\n  Public Key: " + this.getPublicKey().getEncoded().toString() +
	           "\n  Private Key: #ACESSO RESTRITO#" +
	           "\n  Address: " + this.address +
	           "\n}";
	}

}
