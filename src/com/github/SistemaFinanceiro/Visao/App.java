package edu.com.ifpb.pooProjectSistemaFinanceiro.Visao;

import edu.com.ifpb.pooProjectSistemaFinanceiro.controle.usuarioController;
import edu.com.ifpb.pooProjectSistemaFinanceiro.modelo.ModeloDeUsuario;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Scanner;

public class App {
    
    public static void main(String args[]){
        usuarioController novoUser = new usuarioController();
        int escolha = 0;
        Scanner scanner = new Scanner(System.in);
        while(escolha != 6){
            System.out.println("Digite 1 para cadastra um novo usuário.");
            System.out.println("Digite 2 para fazer Login.");
            System.out.println("Digite 3 para atualizar um usuário.");
            System.out.println("Digite 4 para remover um usuário.");
            System.out.println("Digite 5 para sair.");
            escolha = scanner.nextInt();

            switch(escolha){
                //Case 1: Cadastrando um novo Usuário
                case 1:{
                    ModeloDeUsuario novo = new ModeloDeUsuario();
                    
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

                    if (novoUser.adicionar(novo) == false){
                       System.out.println("Usuário já está cadastrado! "); 
                    }else System.out.println("Usuário cadastrado com Sucesso!");
                   
                    break;
                     
                }

                //Case 2 Fazendo o Login do Usuário
                case 2: {
                    if (novoUser.getContas() == null) {
                            System.out.println("Não existe nem um usuário cadastrado no sistema!");
                            break;
                    }
                    System.out.println("Digite seu email: ");
                    String email = scanner.next();
                    System.out.println("Digite sua senha: ");
                    String password = scanner.next();
                    if (novoUser.userLogin(email, password) == true) {
                       System.out.println("Login feito com Sucesso!");
                    }else if (novoUser.userLogin(email, password) == false) System.out.println("Usuário não cadastrado!") ;
                    break;
                }

                case 3:{
                    if (novoUser.getContas().isEmpty()) {
                            System.out.println("Não existe nem um usuário cadastrado no sistema!");
                            break;
                    }
                    System.out.println("Digite o email do usuário: ");
                    String email = scanner.next();
                    System.out.println("Digite a senha do usuário: ");
                    String password = scanner.next();
                    if (novoUser.userLogin(email, password) == false) {
                        System.out.println("O usuário não está cadastrado!");
                        break;
                    }else {

                        ModeloDeUsuario novo = new ModeloDeUsuario();
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

                        novoUser.atualizar(novo);
                        System.out.println("Dados atualizados com sucesso! ");
                    }
                    break;
                }

                case 4:{
                    if (novoUser.getContas().isEmpty()) {
                        System.out.println("Não existe nem um usuário cadastrado no sistema!");
                        break;
                    }
                    System.out.println("Digite seu E-mail: ");
                    String email = scanner.next();
                    System.out.println("Digite sua Senha: ");
                    String password = scanner.next();
                    if(novoUser.removerConta(email, password) == true){
                        System.out.println("O usuário removido com Sucesso! ");
                    }else System.out.println("O usuário não foi encontrado! ");
                    break; 
                }
                case 5:{
                   System.exit(0);
                }
            }
        }
    }
}