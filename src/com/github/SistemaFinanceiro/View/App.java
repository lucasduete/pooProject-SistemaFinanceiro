package com.github.SistemaFinanceiro.View;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import com.github.SistemaFinanceiro.controllers.UsuarioController;
import com.github.SistemaFinanceiro.model.Usuario;

public class App {
	
	private static UsuarioController userController;
	private static Scanner scanner; 
    
    public static void main(String args[]) {
        userController = new UsuarioController();
        int escolha = 0;
        scanner = new Scanner(System.in);
        
       System.out.println("Digite 1 para fazer login.");
        	
        //Se não houver contas cadastradas então pode-se cadastrar-se para realizar o login
        if (userController.getContas().isEmpty() == true)
        	System.out.println("Digite 2 para se cadastro.");
            
         escolha = scanner.nextInt();
            
         switch (escolha) {
         	case 1 :
            	while(login() == false) {
            		System.out.println("Dados Inválidos.");
            		login();
            	}
            	break;
            case 2:
            	//Se for não houver contas libera o cadastro para previnir que o usuario tente 
            		//burlar a segurança da aplicação
            	if (userController.getContas().isEmpty() == true)
            		cadastro();
            	login();
            	break;
            default:
            	System.out.println("Opção Inválida, Finalizando Aplicação");
            	System.exit(0);
         }
        
         //Repete infinitamente, a saida do programa é feita pelo bloco default do switch
        while(true) {
            System.out.println("Digite 1 para cadastra um novo usuário.");
            System.out.println("Digite 2 para atualizar um usuário.");
            System.out.println("Digite 3 para remover um usuário.");
            System.out.println("Digite 4 para atualizar seu usuário.");
            System.out.println("Digite qualquer outro número para sair.");
            
            escolha = scanner.nextInt();

            switch (escolha) {
                case 1 :
                    cadastro();
                    break;
                case 2 :
                	//IMPLEMENTAR
                    break;
                case 3 :
                	System.out.println("Digite seu E-mail: ");
                    String email = scanner.next();
                    System.out.println("Digite sua Senha: ");
                    String password = scanner.next();
                    if(userController.removerConta(email, password) == true){
                        System.out.println("O usuário removido com Sucesso! ");
                    }else System.out.println("O usuário não foi encontrado! ");
                    break;
                case 4 :
                	System.out.println("Digite seu email: ");
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
                default:
                	scanner.close();
                	System.exit(0);                	
            }
        }
    }
        
    private static boolean login() {
    	System.out.println("Digite seu email: ");
        String email = scanner.next();
        
        System.out.println("Digite sua senha: ");
        String password = scanner.next();
        
        if (userController.userLogin(email, password) == true) {
           System.out.println("Login feito com Sucesso!");
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
           System.out.println("Usuário já está cadastrado! "); 
        } else {
        	System.out.println("Usuário cadastrado com Sucesso!");
        }
    }
    
    public static void atualizarPerfil(String myEmail, String myPassword) {
    	Usuario novo = new Usuario();
            
        int idUsuario = 
        		userController
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
  
}