import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class Main {

    public static boolean switching = true;
    public static Frame frame = new JFrame();
    public static File folder = new File("C:/Users/Logan Genualdo/AppData/Roaming/.minecraft/assets/THEFOLDER"); //PUT THEFOLDER PATH HERE
    public static ArrayList<File> images = new ArrayList<>();
    public static long delay = 1500;


    public static void main(String[] args) throws FileNotFoundException {

        if(!folder.exists()) { throw new FileNotFoundException("\n\nThis file does not exist!\nPlease use the correct folder path!\n"); }

        File[] folderList = folder.listFiles(); int totalImages = 0; HashMap<String, Integer> percents = new HashMap<>(); for(int i = 0; i< Objects.requireNonNull(folderList).length; i++) {if(!folderList[i].isFile()) { if(folderList[i].getName().equals("Comics")) {continue;}images.addAll(Arrays.asList(Objects.requireNonNull(folderList[i].listFiles()))); totalImages+= Objects.requireNonNull(folderList[i].listFiles()).length; String folderName = folderList[i].getName(); int folderSize = Objects.requireNonNull(folderList[i].listFiles()).length; int prev; try { prev = percents.get(folderName)+folderSize; } catch(Exception e) { prev = 0; } percents.put(folderName, prev+folderSize);} else { images.add(folderList[i]); totalImages++; int prev; try { prev = percents.get("misc")+1; } catch(Exception e) { prev = 0; } percents.put("misc", prev+1);}}
        int longestFolder = 0; for(String key : percents.keySet()) { if(key.length() > longestFolder) { longestFolder = key.length(); }}

        for(String key : percents.keySet()) { String percent = new DecimalFormat("#.##").format((double) percents.get(key) / totalImages * 100); StringBuilder printName = new StringBuilder(key); while(printName.length() < longestFolder) { printName.append(" "); } System.out.println(printName+" : "+percents.get(key)+" ("+percent+"%)");}
        StringBuilder printName = new StringBuilder("Total"); while(printName.length() < longestFolder) { printName.append(" "); }System.out.println(printName+" : "+totalImages+" (100%)");

        new RandomImage().start();
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

        frame.setSize((int) size.getWidth(), (int) size.getHeight());
        frame.setTitle("THE FOLDER!");
        frame.setUndecorated(true);
        new MouseChecker().activate();
        frame.setVisible(true);

    }
}

class RandomImage extends Thread {
    public static boolean paused = false;
    public void run() {
        while(Main.switching) {
            if(!paused) { switchImage(Main.delay); }
            try { Thread.sleep(Main.delay); } catch (InterruptedException e) { }

        }
    }

    public static void switchImage(long delay) {
        int random_int = (int) Math.floor(Math.random()*((Main.images.size() - 1) +1)+0);


        ImageIcon icon = new ImageIcon(Main.images.get(random_int).getAbsolutePath());
        int width = icon.getIconWidth();
        int height = icon.getIconHeight();
        double wW = Main.frame.getWidth();
        double wH = Main.frame.getHeight();

        double scale=1.0; double xScale=1.0; double yScale=1.0;
        if(width > wW) { xScale = wW/width; }
        if(height > wH) { yScale = wH/height; }

        if(xScale > yScale && yScale != 1.0 && xScale != 1.0) { scale = yScale; }
        else if(yScale > xScale && xScale != 1.0 && yScale != 1.0) { scale = xScale; }

        JLabel currImage = new JLabel(new ImageIcon(icon.getImage().getScaledInstance((int) (width*scale), (int) (height*scale), Image.SCALE_SMOOTH)));
        Main.frame.add(currImage);
        Main.frame.revalidate();
        Main.frame.repaint();
        try { Thread.sleep(delay); } catch (InterruptedException e) { }
        Main.frame.remove(currImage);
    }
}

class MouseChecker implements MouseListener {

    public void activate() { Main.frame.addMouseListener(this); }

    @Override
    public void mousePressed(MouseEvent e) {
            RandomImage.paused = !RandomImage.paused;
            if(RandomImage.paused) { Main.frame.setTitle("THE FOLDER! (Paused)");
            } else { Main.frame.setTitle("THE FOLDER!"); }

    }

    @Override public void mouseClicked(MouseEvent e) { }
    @Override public void mouseReleased(MouseEvent e) { }
    @Override public void mouseEntered(MouseEvent e) { }
    @Override public void mouseExited(MouseEvent e) { }
}
