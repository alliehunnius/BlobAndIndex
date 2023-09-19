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
        pw = new PrintWriter (new FileWriter("index", false));
        
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
        String content = Blob.fileToString(fileName);
        String s = Blob.encryptPassword(content);
        hm.put(fileName, s);
        
        for (HashMap.Entry <String, String> entry : hm.entrySet ())
        {
            String string = entry.getKey () + " : " + entry.getValue();
            pw.println(string);
        }
        pw.close();
    }

    public void removeBlob (String fileName) throws Throwable
    {
        File file = new File(fileName);
        if (file.exists()) 
        {
            String content = Blob.fileToString(fileName);
            String s = Blob.encryptPassword(content);
            File doomedFile = new File ("objects", s);
            doomedFile.delete();
        }

        hm.remove(fileName);
        if(hm.isEmpty())
        {
            pw = new PrintWriter (new FileWriter("index", false));
        }
        else
        {
            for (HashMap.Entry <String, String> entry : hm.entrySet ())
            {
                pw.println (entry.getKey () + " : " + entry.getValue ());
            }
        }
        pw.close();
    }
 }


