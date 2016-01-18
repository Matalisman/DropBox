/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dropboxfolder;

import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JLabel;

/**
 *
 * @author Mateusz
 */
public class Statistics extends TimerTask{
    private AtomicInteger numberOfFiles;
    private JLabel label;
    Statistics(AtomicInteger numberOfFiles){
        this.numberOfFiles= numberOfFiles;
    }
    @Override
    public void run() {
        double number =(int) numberOfFiles.getAndSet(0);
        label.setText(Double.toString(number/10));
        label.revalidate();
    }
    void setLabel(JLabel label){
        this.label = label;
    }
    
}
