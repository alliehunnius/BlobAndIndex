import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class Index {
    
    private HashMap <String, String> hm;
    private PrintWriter pw;
    
    public Index() throws IOException
    {
        hm = new HashMap <String, String> ();
        
    }

    public void initialize () throws IOException
    {
        
        File path = new File("objects");
        path.mkdirs();
        File file = new File ("index");
        if (!file.exists())
        {
            //File newFile = new File
            file.createNewFile();
        }
    }

    public void addBlobs (String fileName) throws Throwable
    {
        Blob blob = new Blob (fileName);
        String contentsOfInputFile = Blob.fileToString(fileName);
        String shaOfBlobContent = Blob.encryptPassword(contentsOfInputFile);
        hm.put(fileName, shaOfBlobContent);
        writeToIndex (fileName + " : " + shaOfBlobContent);

    }


    public static void writeToIndex (String inputContents) throws IOException
    {
        

      FileWriter fw = new FileWriter ("index", true);
      BufferedWriter bw = new BufferedWriter (fw);
      PrintWriter addLineOfContents = new PrintWriter (bw);


        addLineOfContents.println (inputContents);
        //do I want to close it everytime if I'm writing to the same file and it's a static method?
        //I changed it to be inputContents rather than fileName




           
        addLineOfContents.close();
        bw.close();
        
        
    }





    public void removeBlobFromIndexFile (String fileName) throws Throwable
    {

       
        if(hm.isEmpty())
        {
            throw new Exception ("There are no files in the index");
        }
        else{

             hm.remove(fileName);
        }

        this.removeFromIndexFile (fileName);
    }



    public void removeFromIndexFile (String fileName) throws Throwable
    {
        String fileNameContents = Blob.fileToString ("index");
        int indexOfDeletedFile = fileNameContents.indexOf(fileName);
        String beforeDeletedFile = fileNameContents.substring (0, indexOfDeletedFile);
        String deletedChunk = fileName + " : " + hm.get (fileName);
        int deletedChunkLength = deletedChunk.length();

        String afterDeletedFile = fileNameContents.substring (indexOfDeletedFile + deletedChunkLength);
        String newContents = beforeDeletedFile + afterDeletedFile;
        
        PrintWriter pw = new PrintWriter ("index");
        pw.print (newContents);
        pw.close();

    }











    public void addDirectory (String newDirectory) throws Throwable
    {
        //Adding Directories in the form of a Tree ???
        Tree tree = new Tree ();
        String write = tree.addDirectory (newDirectory);
        pw.println ("tree : " + write);
    }





    public void updateTrees ()
    {
        //Updating the format of Index trees to match the recent Tree


    }


 }


