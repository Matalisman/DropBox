package dropboxfolder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Read {
    private ArrayList<DropboxKey> keyList = new ArrayList();
    Read(){
        ObjectInputStream Odczyt=null ;
        try {
            Odczyt = new ObjectInputStream(new FileInputStream("DropboxKey.dat"));
        }catch (FileNotFoundException e){
        } catch (IOException ex) {
        }
        try{
            try {
                keyList = (ArrayList<DropboxKey>) Odczyt.readObject();
            } catch (IOException | ClassNotFoundException ex) {
            }
        }
        catch(NullPointerException e){}
    }
    ArrayList<DropboxKey> getKeyList(){
        return keyList;
    }
}
