package com.github.SistemaFinanceiro.View;

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
        
        //Enquanto não conhecer o usuario
        while(user == null) {
        	System.out.println("Digite 1 para fazer login.");

            //Se não houver contas cadastradas então pode-se cadastrar-se para realizar o login
            if (userController.getContas().isEmpty() == true) {
                System.out.println("Digite 2 para se cadastro.");
            }

            escolha = scanner.nextInt();
            switch (escolha){
                case 1:
                	int count = 0;
                    while (login() == false) {
                        System.out.println("Dados Inválidos.");
                        //login();
                        if(++count == 5) {
                        	System.out.printf("\nNúmero de Tentativas Execidada, recomece o processo.\n\n");
                        	break;
                        }
                        	
                    }
                    break;
                case 2:
                    //Se for não houver contas libera o cadastro para previnir que o usuario tente 
                    //burlar a segurança da aplicação
                    if (userController.getContas().isEmpty() == true) {
                        cadastro();
                    }
                    login();
                    break;
                default:
                    System.out.println("Opção Inválida, Finalizando Aplicação");
                    System.exit(0);
            }       
        }
        
        //Repete infinitamente, a saida do programa Ã© feita pelo bloco default do switch
        while (true) {
            System.out.println("Digite 1 para remover seu perfil de usuário.");
            System.out.println("Digite 2 para atualizar seu usuário.");
            System.out.println("Digite 3 para adicionar uma Movimenção.");
            System.out.println("Digite 4 para atualizar uma Movimenção.");
            System.out.println("Digite 5 para remover uma Movimenção.");
            System.out.println("Digite qualquer outro número para sair.");

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
                        System.out.println("Dados Inválidos!");
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
            System.out.println("Usuário não cadastrado!");
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
            System.out.println("Usuário não está cadastrado! ");
        } else {
            System.out.println("Usuário cadastrado com Sucesso!");
        }
    }
    
    private static void removerPerfil() {
    	if (userController.removerConta(user.getEmail(), user.getPassword()) == true) {
    		System.out.println("O Usuário foi removido com sucesso.");
    	} else {
    		System.out.println("Não Foi Possível Remover o Usuário.");
    	}
    	//Deleta o usuario e reEstarta a aplicação
    	System.out.printf("\n\n\nREINICIANDO APLICAÇÃO.\n\n\n");
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

        System.out.println("Digite seu NOVO Nome de Usuário: ");
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
    	//Retorna null se nao houver nenhuma movimentação cadastrada
    	if(user.getMovimentacoes().isEmpty()) {
    		return null;
    	}
    	
    	//Lista todas as movimentações para que o usuario escolha uma
    	//count controla a id das enquetes, id é o valor que o usuario digitara
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
        	//Se digitar id > cont ou menor que zero então é inválido
        	if(id > cont || id < 0)
        		System.out.println("Valor de ID inválido");
        	else
        		aux = true;
        	
    	} while (aux == false);
    	
    	return id;
    }
    
    private static void removerMovimentacao() {
    	Integer id = escolherMovimentacao();
    	
    	//Caso id seja null entao não ha movimentações então da um return para sair do metodo
    	if(id == null) {
    		System.out.println("Não há Movimentações Cadastradas!!");
    		return;
    	}
    	//Remove Movimentação
    	movimenController.deletarMovimentacao(user,user.getMovimentacao(id));
        System.out.println("Movimentação removida com Sucesso!");
    }
    
    private static void adicionarMovimentacao() {
    	System.out.println("Digite uma descrição para sua Movimentação: ");
        String descricao = scanner.next();

        System.out.println("Digite a data da Movimentação: ");
        String dataMovimentacao = scanner.next();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate movimentar = LocalDate.parse(dataMovimentacao, formatter);

        System.out.println("Digite o valor da Movimentação: ");
        double valorMovimentacao = scanner.nextDouble();

        System.out.println("Digite o tipo de Movimentação: ");
        String tipoMovimentacao = scanner.next();

        System.out.println("Digite a categoria da Movimentação: ");
        String categoria = scanner.next();
            
        user.setMovimentacao(movimenController.criarMovimentacao(descricao, movimentar, valorMovimentacao, tipoMovimentacao, categoria));
    }

    private static void atualizarMovimentacao() {
    	
    	Integer id = escolherMovimentacao();
    	
    	//Caso id seja null entao não ha movimentações então da um return para sair do metodo
    	if(id == null) {
    		System.out.println("Não há Movimentações Cadastradas!!");
    		return;
    	}
    	
    	MovimentacaoFinanceira nova = new MovimentacaoFinanceira();
        
        System.out.println("Digite a NOVA descrição para sua Movimentação: ");
        nova.setDescricao(scanner.next());

        System.out.println("Digite a NOVA data da Movimentação: ");
        String dataMovimentacao = scanner.next();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        nova.setData(LocalDate.parse(dataMovimentacao, formatter));

        System.out.println("Digite o NOVO valor da Movimentação: ");
        nova.setValor(scanner.nextDouble());

        System.out.println("Digite o NOVO tipo de Movimentação: ");
        nova.setTipo(scanner.next());

        System.out.println("Digite a NOVA categoria da Movimentação: ");
        nova.setCategoria(scanner.next());

        movimenController.atualizarMovimentacao(user,user.getMovimentacao(id), nova);
        System.out.println("Movimentação Atualizada com sucesso!");
    }
}
