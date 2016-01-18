/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dropboxfolder;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuthNoRedirect;
import java.io.File;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mateusz
 */
public class ReadFileFromFolder  implements Runnable{
    private File folder, directFile;
    private Thread[] threads;
    private Send[] runner;
    private DbxClient client;
    private String path, account="nie udało się znaleźć konta", directPath;
    private AtomicInteger numberOfFiles = new AtomicInteger();
    ReadFileFromFolder(String path, DropboxKey dropboxKey, int numberOfThreads){
        this.path=path;
        threads = new Thread[numberOfThreads];
        runner = new Send[numberOfThreads];
        DbxAppInfo appInfo = new DbxAppInfo(dropboxKey.getAPP_KEY(), dropboxKey.getSECRET());
        DbxRequestConfig config = new DbxRequestConfig("JavaTutorial/1.0",
            Locale.getDefault().toString());
        DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(config, appInfo);
        client = new DbxClient(config, dropboxKey.getToken());
        numberOfFiles.set(0);
    }
    String getAccount(){
        try {
            account= "Linked account: " + client.getAccountInfo().displayName;
        } catch (DbxException ex) {
            Logger.getLogger(ReadFileFromFolder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return account;
    }
    Statistics getStatistics(){
        return new Statistics(numberOfFiles);
    }
    
    @Override
    public void run() {
        for(int i=0; i<runner.length; i++){
            runner[i]= new Send();
        }
        for(int i=0; i<threads.length; i++){
            threads[i] = new Thread(runner[i]);
        }
        folder = new File(path);
        while(true){
        for (File fileEntry : folder.listFiles()) {
            if(!fileEntry.isDirectory()){
                directPath="";
                for(int i=0;i<threads.length;i++){
                    if(!threads[i].isAlive()){
                        runner[i].addFile("",fileEntry, client, numberOfFiles);
                        threads[i].run();
                        break;
                    }
                }
            }
            else if (fileEntry.isDirectory()){
                directFile =fileEntry;
                this.ForFolders();
            }
        }
        }
    }
    public void ForFolders(){
        try {
            String help1 = folder.getAbsolutePath().replaceAll("\\\\", "/");
            String help2 = directFile.getAbsolutePath().replaceAll("\\\\", "/");
            directPath = help2.replaceAll(help1, "");
            client.createFolder(directPath);
            for(File file : directFile.listFiles()){
                if(!file.isDirectory()){
                    for(int i=0;i<threads.length;i++){
                        if(!threads[i].isAlive()){
                            help2 = file.getParentFile().getAbsolutePath().replaceAll("\\\\", "/");
                            directPath = help2.replaceAll(help1, "");
                            runner[i].addFile(directPath,file, client, numberOfFiles);
                            threads[i].run();
                            break;
                        }
                    }
                }
                else if(file.isDirectory()){
                    directFile=file;
                    this.ForFolders();
                }
            }
            if(directFile.length()==0) directFile.delete();
        } catch (DbxException ex) {
            Logger.getLogger(ReadFileFromFolder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}