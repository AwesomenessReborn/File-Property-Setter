import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class FilePropertySetter {
    public final static String fileSeparator = System.getProperty("file.separator");
    public final static String lineSeparator = System.getProperty("line.separator");
    public final static String userDir = System.getProperty("user.dir");
    
    public static void main(String[] args) throws Exception {
        // program to change copy all "modified dates" into the "created date". 
        
        
        //  changeable field to match folder path. 
        File folderPath = new File("C:\\Users\\rams3\\Desktop\\File Property Setter\\data\\test"); 
        // NOTE all files within folder will be changed to designated time change. 
        
        String[] s = folderPath.list(); 
        
        for (String string : s) {
            File fileReference = new File(folderPath, string); 
            
            if (fileReference.isFile()) {
                // Code to perform the task: copy the modified date into the created date 
                
                
                String fileName = fileReference.getPath(); 
                System.out.println("\n now processing file: " + fileName);
                
                Path file = Paths.get(fileName);
                BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
                FileTime first = attr.lastModifiedTime();
                
                // print path of which file is being changed. 


                // print original last modified time
                System.out.println("[original] getting modified time into created time:" + first);
                
                // NOTE TODO this is the new modified date for now, will change later change created date. 
                // LocalDate newLocalDate = LocalDate.of(1998, 9, 30);
                // // convert LocalDate to instant, need time zone
                // Instant instant = newLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant();

                // convert instant to filetime
                // update last modified time
                Files.setAttribute(file, "creationTime", first); 
                
                // read last modified time again
                FileTime newCreatedTime = Files.readAttributes(file,
                BasicFileAttributes.class).creationTime();
                System.out.println("[updated] creationTime:" + newCreatedTime); 

            }
        }
    }
}