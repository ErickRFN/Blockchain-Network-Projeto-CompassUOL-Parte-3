package main;

import java.util.ArrayList;
import model.*;
import util.*;
import interfaces.*;

public class Execution {
	
	public static void exec() throws InterruptedException {
		System.out.println(TextColor.BLUE + "############################################" + TextColor.RESET);
		System.out.println(TextColor.GREEN_BOLD + "PROJETO: Construindo uma " + TextColor.YELLOW_BOLD +
				"Blockchain " + TextColor.GREEN_BOLD + "com Java" + TextColor.RESET);
        System.out.println(TextColor.BLUE + "############################################" + TextColor.RESET);
        
        Thread.sleep(1000);
        
        System.out.println("\n-> Nesse projeto, demonstrarei uma \nblockchain de transações utilizando"
        		+ " a \nlinguagem java.");
        
        Thread.sleep(1500);
        
        System.out.println("\n-> Para iniciar, irei gerar as carteiras que \n"
        		+ "onde tem os par de chaves (pública e privada), \num "
        		+ "endereço da carteira e um cálculo para mostrar o\n"
        		+ "saldo da mesma.");
        
        ExecUtils.threePoints();
        ArrayList<Wallet_IF> wallets = new ArrayList<Wallet_IF>();
        ExecUtils.generateWallets(wallets);
        System.out.println("\n" + TextColor.GREEN_BOLD + "WALLETS GERADAS COM SUCESSO" + TextColor.RESET);
        
        Thread.sleep(1500);
        
        System.out.println("\n-> Essas carteiras serão usadas durante a \ndemonstração do projeto");
        System.out.println("\n## Informações de cada carteira ##\n");
        
        Thread.sleep(1000);
        
        ExecUtils.displayWallets(wallets);
        
        Thread.sleep(1500);
        
        System.out.println("-> Importante lembrar que o endereço de cada carteira \n"
        		+ "são construídos a partir de 3 regras, e essas \n"
        		+ "regras os validam. São elas:\n");
        Thread.sleep(500);
        System.out.println("1°) O endereção iniciam com o prefixo DERFN, que é a sigla\n"
        		+ "para Descentralizado Erick Rafael Ferreira Nunes");
        Thread.sleep(500);
        System.out.println("2°) Os 7 primeiros dígitos após o prefixo somados são \n"
        		+ "iguais a 21");
        Thread.sleep(500);
        System.out.println("3°) Para completar os 50 caracteres, 38 caracteres hexadecimais \n"
        		+ "gerados a partir da chave pública, preenchem o \nfinal do endereço");
        Thread.sleep(1000);
        
        System.out.println("\n-> Se um ou dois endereços em uma transação não \n"
        		+ "for validado,  a transação é declarada como INVALID TRASACTION");
        
        Thread.sleep(1000);
        System.out.println("\n# EXEMPLOS DE ENDEREÇOS INVÁLIDOS #");
        Thread.sleep(1000);
        
        ExecUtils.examplesOfInvalidAddresses();
        
        System.out.println("-> Agora nos exemplos irei mostrar os endereços \n"
        		+ "reduzidos a 20 caracteres, apenas para melhorar a\n"
        		+ "visualização, porém internamente eles ainda tem \n"
        		+ "o mesmo tamanho de 50 caracteres");
        
        System.out.println("\n-> Passado os testes com as carteiras e endereços, \nirei gerar uma nova blockchain e adicionar a primeira transação"
        		+ "\ncoinbase no bloco Gênesis direcionada \npara mim mesmo.");

        ExecUtils.threePoints();
        Blockchain_IF blockchain = new Blockchain(wallets.get(0));
        System.out.println("\n" + TextColor.GREEN_BOLD + "BLOCKCHAIN GERADA COM SUCESSO" + TextColor.RESET);
        Thread.sleep(1500);
        
        System.out.println("\n-> Lembrando que cada bloco é minerado \n(encontrado o nonce correspondente \na dificuldade atualmente utilizada)");
        System.out.println("-> Dificuldade da mineração usada: " + blockchain.getDifficulty());
        
        Thread.sleep(1000);
        
        System.out.println("\n-> Abaixo irei mostrar a blockchain atual.");
        
        ExecUtils.displayBlockchain(blockchain);
        
        System.out.println("-> Agora, irei adicionar mais 2 blocos "
        		+ "\na blockchain, cada um com duas transações, \nalém da "
        		+ "coinbase sempre para mim.");
        
        ExecUtils.addTransactionToBlockAndBlockchain(blockchain, wallets);
        
        ExecUtils.threePoints();
        System.out.println("\n" + TextColor.GREEN_BOLD + "BLOCOS CRIADOS E ADICIONADOS COM SUCESSO" + TextColor.RESET);
        Thread.sleep(1500);
        
        System.out.println("\n-> A nova blockchain");
        
        ExecUtils.displayBlockchain(blockchain);
        
        System.out.println("-> Validando a blockchain");
        
        Thread.sleep(1500);
        ExecUtils.threePoints();
        ExecUtils.validateBlockchain(blockchain);
        Thread.sleep(1500);
        
        System.out.println("\n-> Agora, vou exibir o histórico de transações\n"
        		+ "das wallets e os seus saldos\n");
        
        ExecUtils.displayTransactionAndBalanceWallet(wallets);
        
        System.out.println("-> Por fim, digamos que dois Hackers "
        		+ "\ninvadiram e adulteraram as transações \ndo bloco com ID = 1");
        
        Thread.sleep(2000);
        ExecUtils.threePoints();
        ExecUtils.adulterateBlockchain(blockchain, wallets);
        System.out.println("\n" + TextColor.RED_BOLD + "HACKERS ADULTERARAM O BLOCO ID 1" + TextColor.RESET);
        Thread.sleep(2000);
        
        System.out.println("\n-> Você pode verifificar abaixo que "
        		+ "\na blockchain está adulterada. Porém "
        		+ "\nverifique que o Hash anterior do bloco ID 2 "
        		+ "\nnão é o mesmo que o Hash de seu anterior, "
        		+ "\no bloco adulterado com ID 1.");
        
        Thread.sleep(2000);
        ExecUtils.displayBlockchain(blockchain);
        Thread.sleep(2000);
        
        System.out.println("-> Logo, irei executar a validação da "
        		+ "\nblockchain que deve confirmar a invalidade "
        		+ "\nda mesma.");
	
        Thread.sleep(1500);
        ExecUtils.threePoints();
        ExecUtils.validateBlockchain(blockchain);
        
        /*
         * Tive que fazer assim por que java utiliza passagem por referência
         * em quase todas as estrutura e não tem um método clone para um onjeto,
         * para que eu conseguisse clonar eu teria q mexer bastante nas classes,
         * já que as 3 estão interligadas, então não seria interessante nem prático,
         * e, por fim, recolocar as transações antigas não faz com que o hash seja
         * o mesmo, já que o timestamp vai ser outro, melhor refazer a blockchain.
         */
        System.out.println("\n-> Após esse teste, irei "
        		+ "\nrefazer a blockchain");
        
        Thread.sleep(1500);
        ExecUtils.threePoints();
        ExecUtils.resetWallets(wallets);
        blockchain = new Blockchain(wallets.get(0));
        ExecUtils.addTransactionToBlockAndBlockchain(blockchain, wallets);
        System.out.println("\n" + TextColor.GREEN_BOLD + "BLOCKCHAIN REFEITA COM SUCESSO" + TextColor.RESET);
        Thread.sleep(1500);
        
        System.out.println("\n-> Agora, uma última exibição");
        
        Thread.sleep(2000);
        ExecUtils.displayBlockchain(blockchain);
        
        System.out.println("-> Por último, uma última validação");
        
        Thread.sleep(1500);
        ExecUtils.threePoints();
        ExecUtils.validateBlockchain(blockchain);
	}

}
