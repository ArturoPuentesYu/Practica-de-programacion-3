package ifp.pr_daw_p3_arturo_puentes;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.System.*;
import javax.swing.*;

/**
 * Programa que realiza una gestión sencilla de ficheros de texto
 * @author Arturo Puentes Yu
 */
public class PR_DAW_P3_Arturo_Puentes {
    
    /**
     * @version 1.0
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        String[] opciones = {"Crear","Leer","Escribir","Salir"};
        int op;
        boolean bucle = true;
        while (bucle){
            op = JOptionPane.showOptionDialog(null, "Elije una opcion", "Gestor de archivos",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones,
            opciones[0]);
            if (op == 3 || op == JOptionPane.CLOSED_OPTION) {
                exit(0);
                bucle = false;
            }
            File file = abrirFichero();
            if (file == null) {
                op = 4;
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
                case 4 -> {
                    System.out.println("No se ha realizado alguna operación.");
                }
            }
        }
    }
    
    /**
     * Utiliza JFileChooser para crear un objeto tipo File
     * @return Retorna un objeto tipo File con el nombre y la ruta de un fichero
     */
    public static File abrirFichero() {
        JFileChooser jFC = new JFileChooser();
        String fichero;
        String ruta;
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
    
    /**
     *  Crea un fichero en la ruta que se ha seleccionado
     *  En caso de que ya exista enseña un mensaje
     * @param file objeto previamente creado en abrirFichero()
     */
    public static void crearFichero(File file){
        if (!file.exists()) {
            try {
            file.createNewFile();
            JOptionPane.showMessageDialog(null, "Se ha creado el fichero " + file.getName());
            }
            catch(IOException e){
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Este archivo ya existe");
        }
    }

    /**
     * Muestra el contenido del archivo y no permite editarlo
     * @param file objeto previamente creado en abrirFichero()
     * @throws IOException
     */
    public static void leerFichero(File file) throws IOException {
        if (file.exists()) {
            String text = convertirFicheroToString(file);
            abrirPanel(file.getName(),text,false);
        } 
        else {
            JOptionPane.showMessageDialog(null, "Este fichero no existe", "Error", JOptionPane.WARNING_MESSAGE);
        }        
    }

    /**
     *  Muestra el contenido del archivo y permite editarlo
     * @param file objeto previamente creado en abrirFichero()
     * @throws IOException
     */
    public static void escribirFichero(File file) throws IOException{
        if (file.exists()) {
            String text = convertirFicheroToString(file);
            String textoEditado = abrirPanel(file.getName(),text,true);
            
            try {
                    // Crea el escritor de ficheros
                    FileWriter wr = new FileWriter(file, false);
                    // Crea el buffered writer para escribir
                    BufferedWriter w = new BufferedWriter(wr);
                    // Escribe del textArea
                    w.write(textoEditado);
                    w.flush();
                    w.close();
            }
            catch (IOException evt) {
                    JOptionPane.showMessageDialog(null, evt.getMessage());
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Este fichero no existe", "Error", JOptionPane.WARNING_MESSAGE);
        }        
    }
    
    /**
     * Convierte el contenido de un fichero de texto a String
     * @param file objeto previamente creado en abrirFichero()
     * @return Restorna el contenido del fichero en forma de String
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String convertirFicheroToString(File file) throws FileNotFoundException, IOException{
        BufferedReader in =  new BufferedReader(new FileReader(file));
        String line = in.readLine();
        String text = line;
        line = in.readLine();
        while (line != null) {
            text = text + "\n" + line;
            line = in.readLine();
        }
        return text;
    }
    
     /**
     * Método que abre el JOptionPanel para leer o escribrir los ficheros
     * @param nombreFile Nombre del fichero para poner como título del panel
     * @param text  Contenido del fichero
     * @param editable boolean que determina si el panel es editable o no
     * @return devuelve el texto editado
     */
    public static String abrirPanel(String nombreFile,String text,boolean editable){
            JTextArea textArea = new JTextArea(text);
            textArea.setLineWrap(true);
            textArea.setEditable(editable);
            textArea.setPreferredSize(new Dimension(375,450));
            JScrollPane scroll = new JScrollPane(textArea);
            JOptionPane.showMessageDialog(null,scroll, nombreFile,JOptionPane.PLAIN_MESSAGE);
            return textArea.getText();
    }
}
