package util;

import java.security.MessageDigest;

public class HashUtil {

    public static String applySHA256(String input) {
        try {
        	
        	// Solicitando instancia do algoritimo sha256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            
            // Aplicando algoritmo SHA256 ao input e obtendo o hash resultante
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            
            // Cria o formato hexadecimal do hash (convertendo de bytes para hexadecimal)
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            
            // Retornando o hash resultante em hexadecimal
            return hexString.toString();
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	
}
