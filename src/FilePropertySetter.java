import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class FilePropertySetter {
    public final static String fileSeparator = System.getProperty("file.separator");
    public final static String lineSeparator = System.getProperty("line.separator");
    public final static String userDir = System.getProperty("user.dir");
    
    public static void main(String[] args) throws Exception {
        
        try {
            JFileChooser chooser = new JFileChooser(userDir); 
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            
            int returnVal = chooser.showOpenDialog(null); 
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String folderString = chooser.getSelectedFile().getPath(); 
                
                File folderPath = new File(folderString); 
                
                String[] s = folderPath.list(); 
                
                for (String string : s) {
                    File fileReference = new File(folderPath, string); 
                    
                    if (fileReference.isFile()) {
                        String fileName = fileReference.getPath(); 
                        System.out.println("\n***now processing file: " + fileName);
                        
                        Path file = Paths.get(fileName);
                        BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
                        FileTime first = attr.lastModifiedTime();
                        
                        System.out.println("[original] getting modified time into created time:" + first);
                        
                        Files.setAttribute(file, "creationTime", first); 
                        
                        // read last modified time again
                        FileTime newCreatedTime = Files.readAttributes(file,
                        BasicFileAttributes.class).creationTime();
                        System.out.println("[updated] creationTime:" + newCreatedTime); 
                        
                    }
                }
            } else {
                int a = JOptionPane.showConfirmDialog(null, "ERR OCCURED, restart?", "Property Setter", JOptionPane.ERROR_MESSAGE); 
                if (a == JOptionPane.YES_OPTION) {
                    main(null); 
                } else {
                    return; 
                }                
            }
        } catch (Exception e) {
            int a = JOptionPane.showConfirmDialog(null, "Err occured with file chooser, restart?", "Property Setter", JOptionPane.ERROR_MESSAGE); 
            e.printStackTrace();
            
            if (a == JOptionPane.YES_OPTION) {
                main(null); 
            } else {
                return; 
            }    
        }
        
        
    }
}