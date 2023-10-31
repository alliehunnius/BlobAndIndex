import java.io.File;

public class manualTester {
    public static void main(String[] args) throws Throwable {


        //  File file = new File ("testAdd");
        // Index index = new Index ();
        // index.initialize();
        // index.addBlobs ("testAdd");
        // Commit commit = new Commit ("", "me", "testerCommit");
       
        
        Index testDay3 = new Index();
        testDay3.initialize();
        testDay3.addBlobs("testAdd");
        testDay3.addBlobs("secondAdd");
        testDay3.removeBlobFromIndexFile ("testAdd");
        testDay3.addBlobs ("thirdAdd");

        
    }
}
 