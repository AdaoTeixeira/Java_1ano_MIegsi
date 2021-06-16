package gui;

import backend.RepositorioAdministradores;
import backend.Sistema;
import bd.Serializacao;

/**
 *
 * @author Adão Teixeira, Cláudia Ribeiro, José Ribeiro
 *
 */
public class Arranque {
    
    public static void main(String[] args) throws RepositorioAdministradores.AdministradorJaExisteException{
        Sistema sistema;
        String ficheiroDados = String.format("%s\\utilizadores.data", System.getProperty("user.dir"));
        System.out.println(String.format("Ficheiro de dados: %s.", ficheiroDados));
        Serializacao serializacao = new Serializacao(ficheiroDados);
        
        if (! serializacao.getFicheiro().exists()) {
            //Cria uma instancia do sistema
            sistema = new Sistema();      
            //Inicializa o sistema
            sistema.inicializar();                  
        }else{
            sistema = serializacao.carregar();            
        }
        
        JanelaPrincipal principal = new JanelaPrincipal(sistema,serializacao);
        principal.setVisible(true);
        
        

    }
    
}
