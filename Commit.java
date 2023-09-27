import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class Commit
{
/*A prerequisite of a commit is that you need a Tree's SHA1 file location.  In order to get this you you must...
create a Tree object and save it to the objects folder
(Trees can be blank and have no entries for this part of the code)

A Commit also has a few other entries
String 'summary'
String 'author'
String 'date'

A commit constructor takes an optional String of the SHA1 of a parent Commit, and two Strings for author and summary

A commit contains a method to generate a SHA1 String
The inputs for the SHA1 are the SUBSET OF FILE CONTENTS file:
Summary, Date, Author, Tree, and (possibly null) pointer to parent Commit file
(so all the file contents except line 3)

Has a method getDate()
It gets the date as a String in whatever format you like

Has a method to create a Tree which is used in the constructor
Returns the SHA1 of the Tree*/
    String summary, author, date, parent, sha;
    File commit;
    public Commit (String summary, String author, String date) throws Throwable
    {
        //commit is stored in the objects folder
        //create tree in constructor
        Tree tree = new Tree();
        tree.addTree("");
        sha = tree.getCurrentFileName();
        this.author = author;
        this.date = date;
        this.summary = summary;
        parent = tree.getCurrentFileName();
        File path = new File ("objects");
        path.mkdirs();
        FileOutputStream stream = new FileOutputStream(new File(path , "Commit"));
        //File commit = new File("Commit");
        FileWriter writer = new FileWriter("Commit");
        writer.close();

    }

    public Commit (String summary, String author, String date, String parent) throws Throwable
    {
        Tree tree = new Tree();
        tree.addTree("");
        sha = tree.getCurrentFileName();
        this.summary = summary;
        this.author = author;
        this.date = date;
        this.parent = parent;
        File path = new File ("objects");
        path.mkdirs();
        FileOutputStream stream = new FileOutputStream(new File(path , "Commit"));
        //File commit = new File("Commit");
    }

    public void generateShaString() throws IOException
    {
        int line = 1;
        String contents = "";
        String sha1;
        BufferedReader reader = new BufferedReader(new FileReader("Commit"));
        while (reader.ready())
        {
            if (line == 3)
            {
                reader.readLine();
            }
            else
            {
                contents += reader.readLine();
            }
            line++;
        }
        reader.close();
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(contents.getBytes("UTF-8"));
            sha1 = byteToHex(crypt.digest());
        }
        catch(NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch(UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        //return sha1;
    }

    private static String byteToHex(final byte[] hash)
    {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    public String getDate()
    {
        return date;
    }
}