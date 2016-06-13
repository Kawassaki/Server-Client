/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketfinalservidor;

import java.io.IOException;
import java.lang.NullPointerException;
/**
 *
 * @author alunos
 */
public class SocketFinalServidor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
          new Servidor(12345).executa();
    }
    
}
