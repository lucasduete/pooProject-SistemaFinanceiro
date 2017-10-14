package com.github.SistemaFinanceiro.view;

import com.github.SistemaFinanceiro.controllers.MovimentacaoController;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import org.omg.CORBA.INTERNAL;

import com.github.SistemaFinanceiro.controllers.UsuarioController;
import com.github.SistemaFinanceiro.model.MovimentacaoFinanceira;
import com.github.SistemaFinanceiro.model.Usuario;

public class App {

    private static UsuarioController userController;
    private static MovimentacaoController movimenController;
    private static Scanner scanner;
    
  //Variavel que controla o usuario logado
    private static Usuario user = null;

    public static void main(String args[]) {
        userController = new UsuarioController();
        movimenController = new MovimentacaoController();
        int escolha = 0;
        scanner = new Scanner(System.in);
        
        //Enquanto n�o conhecer o usuario
        while(user == null) {
        	System.out.println("Digite 1 para fazer login.");

            //Se n�o houver contas cadastradas ent�o pode-se cadastrar-se para realizar o login
            if (userController.getContas().isEmpty() == true) {
                System.out.println("Digite 2 para se cadastro.");
            }

            escolha = scanner.nextInt();
            switch (escolha){
                case 1:
                	int count = 0;
                    while (login() == false) {
                        System.out.println("Dados Inv�lidos.");
                        //login();
                        if(++count == 5) {
                        	System.out.printf("\nN�mero de Tentativas Execidada, recomece o processo.\n\n");
                        	break;
                        }
                        	
                    }
                    break;
                case 2:
                    //Se for n�o houver contas libera o cadastro para previnir que o usuario tente 
                    //burlar a seguran�a da aplica��o
                    if (userController.getContas().isEmpty() == true) {
                        cadastro();
                    }
                    login();
                    break;
                default:
                    System.out.println("Op��o Inv�lida, Finalizando Aplica��o");
                    System.exit(0);
            }       
        }
        
        //Repete infinitamente, a saida do programa é feita pelo bloco default do switch
        while (true) {
            System.out.println("Digite 1 para remover seu perfil de usu�rio.");
            System.out.println("Digite 2 para atualizar seu usu�rio.");
            System.out.println("Digite 3 para adicionar uma Movimen��o.");
            System.out.println("Digite 4 para atualizar uma Movimen��o.");
            System.out.println("Digite 5 para remover uma Movimen��o.");
            System.out.println("Digite qualquer outro n�mero para sair.");

            escolha = scanner.nextInt();

            switch (escolha) {
                case 1:
                    removerPerfil();
                break;
                case 2:
                    System.out.println("Digite seu e-mail: ");
                    String myEmail = scanner.next();

                    System.out.println("Digite sua senha: ");
                    String myPassword = scanner.next();

                    //Valida se usuario realmente sabe sua senha e email
                    if (userController.userLogin(myEmail, myPassword) == false) {
                        System.out.println("Dados Inv�lidos!");
                        break;
                    }
                    //Atualiza dados
                    atualizarPerfil(myEmail, myPassword);
                break;
                case 3:
                    adicionarMovimentacao();
                break;
                case 4:
                    atualizarMovimentacao();
                break;
                case 5:         
                    removerMovimentacao();
                break;
                default:
                    scanner.close();
                    System.exit(0);
            }
        }
    }

    private static boolean login() {
        System.out.println();
        System.out.println("Digite seu email e sua senha para fazer o login!");
        System.out.println();
        System.out.println("Digite seu email: ");
        String email = scanner.next();

        System.out.println("Digite sua senha: ");
        String password = scanner.next();

        if (userController.userLogin(email, password) == true) {
            System.out.println("Login feito com Sucesso!");
            
            //Define qual usuario fez login
            user = userController.localizar(email, password);
            		
            return true;
        } else {
            System.out.println("Usu�rio n�o cadastrado!");
        }
        return false;
    }

    private static void cadastro() {
        Usuario novo = new Usuario();

        System.out.println("Digite o seu E-mail: ");
        novo.setEmail(scanner.next());

        System.out.println("Digite uma Senha: ");
        novo.setPassword(scanner.next());

        System.out.println("Digite seu Nome: ");
        novo.setNome(scanner.next());

        System.out.println("Digite 'F' ou 'M' para seu Sexo: ");
        novo.setSexo(scanner.next().charAt(0));

        System.out.println("Digite sua Data de Nascimento: ");
        String dataNascimento = scanner.next();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate nascimento = LocalDate.parse(dataNascimento, formatter);
        novo.setDataNasc(nascimento);

        if (userController.adicionar(novo) == false) {
            System.out.println("Usu�rio n�o est� cadastrado! ");
        } else {
            System.out.println("Usu�rio cadastrado com Sucesso!");
        }
    }
    
    private static void removerPerfil() {
    	if (userController.removerConta(user.getEmail(), user.getPassword()) == true) {
    		System.out.println("O Usu�rio foi removido com sucesso.");
    	} else {
    		System.out.println("N�o Foi Poss�vel Remover o Usu�rio.");
    	}
    	//Deleta o usuario e reEstarta a aplica��o
    	System.out.printf("\n\n\nREINICIANDO APLICA��O.\n\n\n");
    	main(null);
    	System.exit(0);
    }

    public static void atualizarPerfil(String myEmail, String myPassword) {
        Usuario novo = new Usuario();

        int idUsuario
                = userController
                .localizar(myEmail, myPassword)
                .getId();

        System.out.println("Digite seu NOVO E-amil: ");
        novo.setEmail(scanner.next());

        System.out.println("Digite sua NOVA Senha: ");
        novo.setPassword(scanner.next());

        System.out.println("Digite seu NOVO Nome de Usu�rio: ");
        novo.setNome(scanner.next());

        System.out.println("Digite 'F' ou 'M' para seu Sexo: ");
        novo.setSexo(scanner.next().charAt(0));

        System.out.println("Digite sua Data de Nascimento: ");
        String dataNascimento = scanner.next();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate nascimento = LocalDate.parse(dataNascimento, formatter);
        novo.setDataNasc(nascimento);

        userController.atualizar(novo, idUsuario);
        System.out.println("Dados atualizados com sucesso! ");
    }

    public static Integer escolherMovimentacao() {
    	//Retorna null se nao houver nenhuma movimenta��o cadastrada
    	if(user.getMovimentacoes().isEmpty()) {
    		return null;
    	}
    	
    	//Lista todas as movimenta��es para que o usuario escolha uma
    	//count controla a id das enquetes, id � o valor que o usuario digitara
    	Integer cont = 0, id = 1;
    	
    	//Variavel que controla se usuario escolheu corretamente
    	boolean aux = false;
    	
    	//Repete ate id valida
    	do {
    		System.out.println("Escolha uma Movimentacao");
    		//Lista Movimentacoes
        	for(MovimentacaoFinanceira mf: user.getMovimentacoes()) {
        		System.out.println(cont++ + "{" + mf.toString() + "}");
        	}
        	
        	id = scanner.nextInt();
        	//Se digitar id > cont ou menor que zero ent�o � inv�lido
        	if(id > cont || id < 0)
        		System.out.println("Valor de ID inv�lido");
        	else
        		aux = true;
        	
    	} while (aux == false);
    	
    	return id;
    }
    
    private static void removerMovimentacao() {
    	Integer id = escolherMovimentacao();
    	
    	//Caso id seja null entao n�o ha movimenta��es ent�o da um return para sair do metodo
    	if(id == null) {
    		System.out.println("N�o h� Movimenta��es Cadastradas!!");
    		return;
    	}
    	//Remove Movimenta��o
    	movimenController.deletarMovimentacao(user,user.getMovimentacao(id));
        System.out.println("Movimenta��o removida com Sucesso!");
    }
    
    private static void adicionarMovimentacao() {
    	System.out.println("Digite uma descri��o para sua Movimenta��o: ");
        String descricao = scanner.next();

        System.out.println("Digite a data da Movimenta��o: ");
        String dataMovimentacao = scanner.next();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate movimentar = LocalDate.parse(dataMovimentacao, formatter);

        System.out.println("Digite o valor da Movimenta��o: ");
        double valorMovimentacao = scanner.nextDouble();

        System.out.println("Digite o tipo de Movimenta��o: ");
        String tipoMovimentacao = scanner.next();

        System.out.println("Digite a categoria da Movimenta��o: ");
        String categoria = scanner.next();
            
        user.setMovimentacao(movimenController.criarMovimentacao(descricao, movimentar, valorMovimentacao, tipoMovimentacao, categoria));
    }

    private static void atualizarMovimentacao() {
    	
    	Integer id = escolherMovimentacao();
    	
    	//Caso id seja null entao n�o ha movimenta��es ent�o da um return para sair do metodo
    	if(id == null) {
    		System.out.println("N�o h� Movimenta��es Cadastradas!!");
    		return;
    	}
    	
    	MovimentacaoFinanceira nova = new MovimentacaoFinanceira();
        
        System.out.println("Digite a NOVA descri��o para sua Movimenta��o: ");
        nova.setDescricao(scanner.next());

        System.out.println("Digite a NOVA data da Movimenta��o: ");
        String dataMovimentacao = scanner.next();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        nova.setData(LocalDate.parse(dataMovimentacao, formatter));

        System.out.println("Digite o NOVO valor da Movimenta��o: ");
        nova.setValor(scanner.nextDouble());

        System.out.println("Digite o NOVO tipo de Movimenta��o: ");
        nova.setTipo(scanner.next());

        System.out.println("Digite a NOVA categoria da Movimenta��o: ");
        nova.setCategoria(scanner.next());

        movimenController.atualizarMovimentacao(user,user.getMovimentacao(id), nova);
        System.out.println("Movimenta��o Atualizada com sucesso!");
    }
}
