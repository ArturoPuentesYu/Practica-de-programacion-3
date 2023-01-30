/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package ifp.pr_daw_p3_arturo_puentes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.System.*;
import javax.swing.*;

/**
 *
 * @author ajpyu
 */
public class PR_DAW_P3_Arturo_Puentes {
    
    static String[] opciones = {"Crear","Leer","Escribir","Borrar","Copiar","Salir"};
    static String ruta = null;
    static String fichero = null;
    static JFileChooser jFC = new JFileChooser();
    
    public static void main(String[] args) {
        int op;
        boolean bucle = true;
        while (bucle){
            op = JOptionPane.showOptionDialog(null, "Elije una opcion", "Gestor de archivos",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones,
            opciones[0]);
            if (op == 5) {
                exit(0);
                bucle = false;
            }
            File file = abrirFichero();
            if (file == null) {
                op = 6;
            }
            switch (op){
                case 0 -> {
                    crearFichero(file);
                }
                case 1 -> {
                    leerFichero(file);
                }
                case 2 -> {
                    escribirFichero(file);
                }
                case 3 -> {
                }
                case 4 -> {
                }
                case 6 -> {
                    JOptionPane.showMessageDialog(null, "No se ha seleccionado un fichero","Mensaje de error", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }  
    
    private static File abrirFichero() {
        
        int retorno = jFC.showOpenDialog(jFC);
        File file = null;
        if (retorno == JFileChooser.APPROVE_OPTION) {
            
            ruta = jFC.getSelectedFile().getParent();
            fichero = jFC.getName(new File(jFC.getSelectedFile().getAbsolutePath()));
            System.out.println(ruta);            
            System.out.println(fichero);
            
            file = new File(ruta,fichero);
        }
        else if (retorno == JFileChooser.CANCEL_OPTION) {
            System.out.println("Has cancelado");
        }
        return file;
    }
    
    private static void crearFichero(File file){
        jFC.setDialogTitle("Crear fichero");
        
        //Parte que comprueba si existe y lo creo o de lo contrario notifica que ya existe
        if (!file.exists()) {
            try {
            file.createNewFile();
            JOptionPane.showMessageDialog(null, "Se ha creado el fichero " + jFC.getName(new File(jFC.getSelectedFile().getAbsolutePath())));
            }
            catch(IOException e){
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Este archivo ya existe");
        }
        //
    }

    private static void leerFichero(File file) {

        jFC.setDialogTitle("Leer fichero");
        //Parte que lee el fichero y lo muestra en un JOptionPane
        try {
        BufferedReader in;
        in = new BufferedReader(new FileReader(file));
        String line = in.readLine();
        String text = "";
        while (line != null) {
            text = text + "\n" + line;
            line = in.readLine();
        }
        JOptionPane.showMessageDialog(null, text , jFC.getName(new File(jFC.getSelectedFile().getAbsolutePath())), JOptionPane.PLAIN_MESSAGE);
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        //
        }

    }

    private static void escribirFichero(File file) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


}
