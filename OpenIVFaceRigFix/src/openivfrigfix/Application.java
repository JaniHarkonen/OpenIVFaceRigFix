package openivfrigfix;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Application {
    
    public Application() {}
    
    public void start() {
        this.showMenu();
        
        List<String> files = this.acceptFiles();
        
        if( files.size() <= 0 )
        {
            this.prompt("No files provided, shutting down...");
            System.exit(0);
        }
        
        this.prompt("Starting...");
        
        for( String path : files )
        this.fixFile(path);
        
        this.newLine();
        this.prompt("Successfully fixed " + files.size() + " files!");
    }
    
    public void showMenu() {
        this.prompt("=== OpenIVFaceRigFix ===");
        this.prompt("This program fixes OpenIV animation files by removing faulty content blocks");
        this.prompt("starting with 'Type21 Float'.");
        this.prompt("You can quit at anytime by spamming CTRL + C.");
        this.newLine();
        this.newLine();
    }
    
    public List<String> acceptFiles() {
        this.prompt("Enter a filepath and press ENTER.");
        this.prompt("Once you've entered all the files press ENTER again to continue:");
        
        List<String> files = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        String input;
        while( !(input = scanner.nextLine()).equals("") )
        {
            input = input.replace("\"", "");
            
            if( !Files.exists(Paths.get(input)) )
            this.prompt("ERROR: Given file doesn't exist! The file was not added.");
            else
            files.add(input);
        }
        
        scanner.close();
        return files;
    }
    
    public void fixFile(String path) {
        try
        {
            String outputPath = path + "FIXED";
            
            if( Files.exists(Paths.get(outputPath)) )
            {
                this.newLine();
                this.prompt("ERROR: Trying to write into file '" + outputPath + "', which already exists!");
                System.exit(0);
            }
            
            BufferedReader br = new BufferedReader(new FileReader(path));
            BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath));
            String line;
            
            int startBlockLevel = 0;
            int currentBlockLevel = 0;
            boolean isInsideType21 = false;
            
            String identifier = "Type21 Float";
            int identifierSize = identifier.length();
            
            while( (line = br.readLine()) != null )
            {
                String trimLine = line.trim();
                
                if( isInsideType21 )
                {
                    if( trimLine.equals("{") )
                    currentBlockLevel++;
                    else if( trimLine.equals("}") )
                    {
                        currentBlockLevel--;
                        
                        if( currentBlockLevel == startBlockLevel )
                        isInsideType21 = false;
                    }
                }
                else
                {
                    if(
                        trimLine.length() >= identifier.length() && 
                        trimLine.substring(0, identifierSize).equals(identifier) )
                    {
                        startBlockLevel = currentBlockLevel;
                        isInsideType21 = true;
                    }
                    else
                    bw.write(line + "\n");
                }
            }
            
            br.close();
            bw.close();
            
            this.prompt("Fixed '" + path + "'...");
        }
        catch( Exception e )
        {
            System.out.println(e);
            System.exit(0);
        }
    }
    
    public void prompt(String message) {
        System.out.println(message);
    }
    
    public void newLine() {
        this.prompt("");
    }

    
    public static void main(String[] args) {
        Application app = new Application();
        app.start();
    }
}
