import java.io.File;

public class manualTester {
    public static void main(String[] args) throws Throwable {


         File file = new File ("testAdd");
        Index index = new Index ();
        index.initialize();
        index.addBlobs ("testAdd");
        Commit commit = new Commit ("", "me", "testerCommit");
       
        
    }
}
 