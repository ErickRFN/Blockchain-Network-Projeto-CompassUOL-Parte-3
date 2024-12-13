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
        System.out.println();
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
        		+ "for validado,  a transação é declarada como INVALID TRANSACTION");
        
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
        System.out.println("\n");
        @SuppressWarnings("unchecked")
		ArrayList<Wallet_IF> walletsClone1 = (ArrayList<Wallet_IF>) wallets.clone();
        Blockchain_IF blockchain = new Blockchain(walletsClone1.get(0), walletsClone1);
        System.out.println("\n" + TextColor.GREEN_BOLD + "BLOCKCHAIN GERADA COM SUCESSO" + TextColor.RESET);
        Thread.sleep(1500);
        
        System.out.println("\n-> Lembrando que cada bloco é minerado \n(encontrado o nonce correspondente \na dificuldade atualmente utilizada)");
        System.out.println("-> Dificuldade da mineração usada: " + blockchain.getDifficulty());
        
        Thread.sleep(1000);
        
        System.out.println("\n-> Abaixo irei mostrar a blockchain atual.");
        
        ExecUtils.displayBlockchain(blockchain);
        
        System.out.println("-> Agora, irei adicionar mais 2 blocos "
        		+ "\na blockchain, cada um com duas transações, \nalém da "
        		+ "coinbase sempre para mim.\n");
        
        ExecUtils.addTransactionToBlockAndBlockchain(blockchain, walletsClone1);
        
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
        System.out.println("\n-> Todas as transações, menos as coinbase, pagam uma taxa de 5% ao minerador,\n"
        		+ "essas taxas são transformada em uma transação chamada FEES \nem cada bloco.\n");
        
        ExecUtils.displayTransactionAndBalanceWallet(walletsClone1);
        
        System.out.println("-> Por fim, digamos que dois Hackers "
        		+ "\ninvadiram e adulteraram as transações \ndo bloco com ID = 1");
        
        Thread.sleep(2000);
        ExecUtils.threePoints();
        ExecUtils.adulterateBlockchain(blockchain, walletsClone1);
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
        		+ "\nrefazer a blockchain e a validar");
        
        Thread.sleep(1500);
        ExecUtils.threePoints();
        wallets.clear();
        ExecUtils.generateWallets(wallets);
        @SuppressWarnings("unchecked")
		ArrayList<Wallet_IF> walletsClone2 = (ArrayList<Wallet_IF>) wallets.clone();
        blockchain = new Blockchain(walletsClone2.get(0), walletsClone2);
        ExecUtils.addTransactionToBlockAndBlockchain(blockchain, walletsClone2);
        System.out.println("\n" + TextColor.GREEN_BOLD + "BLOCKCHAIN REFEITA COM SUCESSO" + TextColor.RESET);
        Thread.sleep(1500);
        ExecUtils.threePoints();
        ExecUtils.validateBlockchain(blockchain);
        
        System.out.println("\n-> Por fim, gostaria de demonstrar com funcionaria uma rede\n"
        		+ "(NETWORK) com vários nós dessa blockchain.");
        Thread.sleep(1000);
        System.out.println("\n-> Para iniciar, irei criar um nó primário que vai receber\n"
        		+ "a blockchain atual junto com as wallets disponíveis.");
        Thread.sleep(1500);
        ExecUtils.threePoints();
        Node_IF primaryNode = new Node(blockchain, blockchain.getWallets().get(0));
        ArrayList<Node_IF> listNodes = new ArrayList<Node_IF>();
        listNodes.add(primaryNode);
        System.out.println("\n" + TextColor.GREEN_BOLD + "NÓ PRIMÁRIO CRIADO, NICKNAME: " + primaryNode.getWalletNode().getNickname() + TextColor.RESET);
        
        System.out.println("\n-> Agora, vou criar mais 2 nós, todos com blockchain recem iniciadas:");
        Thread.sleep(1500);
        ExecUtils.threePoints();
        ArrayList<Wallet_IF> wallets2 = new ArrayList<Wallet_IF>();
        ExecUtils.generateWallets(wallets2);
        ExecUtils.createNode(wallets2, listNodes);
        ArrayList<Wallet_IF> wallets3 = new ArrayList<Wallet_IF>();
        ExecUtils.generateWallets(wallets3);
        ExecUtils.createNode(wallets3, listNodes);
        Thread.sleep(1500);
        System.out.println("\n" + TextColor.GREEN_BOLD + "2 NÓS COM BLOCKCHAIN NOVAS CRIADOS" + TextColor.RESET);
        
        System.out.println("\n-> Os 3 nós não estão conectados no momento, cada \num está por si,"
        		+ " e o 1° nó tem a maior blockchain. Irei conectar \no 2° nó a ele:");
        Thread.sleep(1500);
        ExecUtils.threePoints();
        ExecUtils.pairNodes(listNodes.get(0), listNodes.get(1));
        System.out.println("\n" + TextColor.GREEN_BOLD + "NÓS 1 E 2 CONECTADOS" + TextColor.RESET);
        
        System.out.println("\n-> Mesmo os conectando, \neles ainda não estão sicronizados, "
        		+ "como pode ver a seguir:\n");
        System.out.println("# Blockchain do node 1:");
        ExecUtils.displayBlockchain(listNodes.get(0).getBlockchain());
        System.out.println("# Blockchain do node 2:");
        ExecUtils.displayBlockchain(listNodes.get(1).getBlockchain());
	
        Thread.sleep(1500);
        System.out.println("\n-> Isso acontece por que mesmo após conectar não foi\n"
        		+ "minerado nenhum bloco pós conexão\n");
        Thread.sleep(1500);
        System.out.println("\n-> Irei adicionar um novo bloco no nó 1, e a blockchain \n"
        		+ "do nó 2 irá mudar pois o bloco novo irá acionar uma propagação\n");
        Thread.sleep(1500);
        System.out.println("\n-> Seguindo a regra de maior cadeia, o nó 2 irá perceber\n"
        		+ " que a cadeia do nó 2 está 3 blocos a frente, e então ele irá \n"
        		+ "adotar a Blockchain do nó 1 para si");
        ExecUtils.threePoints();
        ExecUtils.createNewBlockNode(listNodes.get(0));
        System.out.println("\n" + TextColor.GREEN_BOLD + "NOVO BLOCO NO NÓ 1" + TextColor.RESET);

        Thread.sleep(1500);
        System.out.println("\n-> Agora as duas blockchain estão em consenso,\n"
        		+ "e qualquer novo bloco em algum nó, ocorrerá a transmição do mesmo.\n");
        Thread.sleep(1500);
        System.out.println("# Blockchain do node 1:");
        ExecUtils.displayBlockchain(listNodes.get(0).getBlockchain());
        Thread.sleep(1500);
        System.out.println("# Blockchain do node 2:");
        ExecUtils.displayBlockchain(listNodes.get(1).getBlockchain());
        
        Thread.sleep(1500);
        System.out.println("\n-> Agora vou conectar o nó 3 aos demais nós (1 e 2)\n");
        ExecUtils.threePoints();
        ExecUtils.pairNodes(listNodes.get(2), listNodes.get(0));
        ExecUtils.pairNodes(listNodes.get(2), listNodes.get(1));
        System.out.println("\n" + TextColor.GREEN_BOLD + "O NÓ 3 ESTÁ CONECTADO AOS OUTROS 2 NÓS" + TextColor.RESET);
        
        Thread.sleep(1500);
        System.out.println("\n-> Por fim, para testar se o fork está funcionando corretamente.\n"
        		+ " Irei adicionar um bloco ao nó 3 e ele irá propagar para\n"
        		+ " os outros 2 nós, porém nenhum irá aceitar a mudança,\n"
        		+ " pois os nós 1 e 2 tem blockchain maiores e irão 'ignorar'.");
        ExecUtils.threePoints();
        ExecUtils.createNewBlockNode(listNodes.get(2));
        System.out.println("\n" + TextColor.GREEN_BOLD + "BLOCO ADICIONADO AO NÓ 3 E PROPAGADO AOS DEMAIS" + TextColor.RESET);
        
        Thread.sleep(1500);
        System.out.println("\n-> Agora finalizando, exibindo as blockchains do 4 nós\n"
        		+ " para confirmar que: nó 1 = 2 e nó 3 sozinho (nós 1 e 2 ignoraram o \n"
        		+ "nó 3)");
        Thread.sleep(1500);
        System.out.println("# Blockchain do node 1:");
        ExecUtils.displayBlockchain(listNodes.get(0).getBlockchain());
        Thread.sleep(1500);
        System.out.println("# Blockchain do node 2:");
        ExecUtils.displayBlockchain(listNodes.get(1).getBlockchain());
        Thread.sleep(1500);
        System.out.println("# Blockchain do node 3:");
        ExecUtils.displayBlockchain(listNodes.get(2).getBlockchain());
        Thread.sleep(1500);
        
        Thread.sleep(1500);
        System.out.println("\n-> Exibindo por último as wallets de cada blockchain,\n"
        		+ " onde os nós 1 e 2 vão concordar mas o 3 não quanto ao saldo de\n"
        		+ " cada carteira");
        Thread.sleep(1500);
        System.out.println("# Wallets do node 1:");
        ExecUtils.displayTransactionAndBalanceWallet(listNodes.get(0).getBlockchain().getWallets());
        Thread.sleep(1500);
        System.out.println("# Wallets do node 2:");
        ExecUtils.displayTransactionAndBalanceWallet(listNodes.get(1).getBlockchain().getWallets());
        Thread.sleep(1500);
        System.out.println("# Wallets do node 3:");
        ExecUtils.displayTransactionAndBalanceWallet(listNodes.get(2).getBlockchain().getWallets());
        Thread.sleep(1500);
	}

}
