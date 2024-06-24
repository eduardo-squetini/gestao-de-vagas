package br.com.eduardosquetini.gestao_vagas.exceptions;

 // Construtor da classe 'UserNotFoundException'.
public class UserNotfoundException extends RuntimeException {
    
   public UserNotfoundException(){

   // Chama o construtor da classe pai ('RuntimeException') passando a mensagem "User not found".
    super("User not found");
   }
    

} 
