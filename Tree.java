import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Tree {
    private static ArrayList<String> treeList;
    private static TreeMap <String, String> blobMap;
    private static String currentFileName;
    //private File file; 
    //private PrintWriter pw;

    public Tree() throws IOException
    {
        treeList = new ArrayList<String>();
        blobMap = new TreeMap <String, String> ();
        currentFileName = "init";
        File path = new File("objects");
        path.mkdirs();
        //this.file = file;
        //pw = new PrintWriter(new FileWriter("tree", true));
    }
    
    /*Add another entry into the tree:
Entries are added as new lines of Strings
    tree.add("tree : bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b")
    tree.add("blob : 81e0268c84067377a0a1fdfb5cc996c93f6dcf9f : file1.txt")
Entries are a newline String in format  typeOfFile : shaOfFile : optionalFileName
    typeOfFile: Can be either the String 'blob' or 'tree' 
    shaOfFile: Is a valid sha1 of a file in the objects folder
    optionalFileName: if it's a blob, please include the filename
    the two are separated by a " : "
Do NOT allow for duplicate 'trees' or duplicate 'filenames' in the file */

    public void addTree (String entry) throws Throwable
    {
        String typeOfFile = entry.substring(0, 4); 
        String shaOfFile = entry.substring(7, 47);
        if(treeList.contains(shaOfFile) || blobMap.containsKey(shaOfFile))
        {
            return;
        }
        if(typeOfFile.equals("tree"))
        {
            treeList.add(shaOfFile);
        }
        else
        {
            String optionalFileName = entry.substring(50);
            blobMap.put(shaOfFile, optionalFileName);
        }
        updateName();
    }
    /*Remove an entry from a tree by...
    Remove a BLOB entry from the tree based on a filename
        tree.remove("file1.txt")
    Remove a TREE entry based on SHA1
        tree.remove("bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b")
 */
    public void removeTree (String entry) throws Throwable
    {
        if(treeList.contains(entry))
        {
            treeList.remove(entry);
        }
        else if (blobMap.containsValue(entry))
        {
            Set<String> blobSet = blobMap.keySet();
            String target = "";
            for(String key: blobSet)
		    {
                if(blobMap.get(key).equals(entry))
                {
                    target = key;
                }
		    }
            blobMap.remove(target);
        }
        updateName();
    }

    private void updateName() throws Throwable
    {
        File doomedFile = new File ("objects", currentFileName);
        doomedFile.delete();
        String newFileName = Blob.encryptPassword(content());
        currentFileName = newFileName;
        printToFile();
        /*File oldFile = new File("objects", currentFileName);
        File newFile = new File("objects", newFileName);
        oldFile.renameTo(newFile);*/
        
    }

    private void printToFile() throws Throwable
    {
        String folder = "objects";
        String file = getCurrentFileName();
        File dir = new File(folder);
        File actualFile = new File(dir,file);
        PrintWriter pw = new PrintWriter(new FileWriter(actualFile, false));
        pw.print(content());
        pw.close();
        /* 
        //PrintWriter pw = new PrintWriter(new FileWriter(file, false));
        Set<String> blobSet = blobMap.keySet();
        for(String key: blobSet)
		{
            if(blobMap.lastKey().equals(key))
            {
                String fileName = blobMap.get(key);
                pw.print("blob : " + key + " : " + fileName);
            }
            else
            {
                String fileName = blobMap.get(key);
                pw.print("blob : " + key + " : " + fileName);
                pw.print("\n");
            }
        }
        if(!blobMap.isEmpty() && !treeList.isEmpty())
        {
            pw.print("\n");
        }
        if(treeList.size() > 0)
        {
            int last = treeList.size()-1;
            for (int i = 0; i < last; i++) 
            {
                pw.println("tree : " + treeList.get(i));
                //pw.print("\n");
            }
            pw.print("tree : " + treeList.get(last));
        }
        pw.close();*/
    }

    public static String content()
    {
        String content = "";
        Set<String> blobSet = blobMap.keySet();
        for(String key: blobSet)
		{
            if(blobMap.lastKey().equals(key))
            {
                String fileName = blobMap.get(key);
                content += "blob : " + key + " : " + fileName;
            }
            else
            {
                String fileName = blobMap.get(key);
                content += "blob : " + key + " : " + fileName + "\n";
            }
		}
        if(!blobMap.isEmpty() && !treeList.isEmpty())
        {
            content += "\n";
        }

        if(treeList.size() > 0)
        {
            int last = treeList.size()-1;
            for (int i = 0; i < last; i++) 
            {
                content += "tree : " + treeList.get(i) + "\n";
            }
            content += "tree : " + treeList.get(last);
        }
        return content;
    }

    public static String getCurrentFileName()
    {
        return currentFileName;
    }
}