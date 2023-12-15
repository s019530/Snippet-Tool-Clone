package com.example;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.StrokeBorder;
import javax.swing.event.MouseInputAdapter;

import javafx.embed.swing.SwingFXUtils;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.nio.Buffer;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Desktop;

public class Window extends JFrame {    

    final Color deft = new Color(0,0,0,.3f);

    public Window()
    {

        this.setUndecorated(true);
        this.setBackground(deft);
        //changeOpacity();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));


        Draw dr = new Draw();
        this.add(dr);

        Handler idc = new Handler(dr);

        keyHandler keyidc = new keyHandler();

        this.addMouseMotionListener(idc);
        this.addMouseListener(idc);
        this.addKeyListener(keyidc);

    }

    public void changeOpacity()
    {
        this.setBackground(new Color(0,0,0,0f));
    }


}

class Draw extends JComponent{

    Rectangle newrect = new Rectangle(0,0,0,0);
    Rectangle oldrect = new Rectangle(0,0,0,0);
    Point ogpoint;
    final Color cr = new Color(1, 1, 1, .2f);


    @Override
    public void paintComponent(Graphics g)
    {

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(cr);
        g2.fill(newrect);
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2));
        g2.draw(newrect);


    }
    public void painting(Point pt)
    {
        newrect = new Rectangle(pt);
        newrect.add(ogpoint);
        repaint();
        oldrect = newrect;
    }
    public void firstPress(Point pt)
    {
        ogpoint = pt;
    }

    public void clear() throws InvocationTargetException, InterruptedException
    {
        
        oldrect = newrect;
        newrect = new Rectangle(0,0,0,0);
        //repaint();
        SwingUtilities.invokeLater(new Runnable() {
            public void run()
            {
                repaint();
                Main.frame.changeOpacity();
                CreateImage img = new CreateImage(oldrect);
                System.exit(0);
            }
        });
    }
}

class CreateImage{

    BufferedImage img;
    Rectangle rect;

    public CreateImage(Rectangle rect){
        this.rect = rect;
        GettingImage();
        SavingImage();
    }

    private void GettingImage()
    {
        try {
            img = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            img = img.getSubimage(rect.x, rect.y, rect.width, rect.height);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void SavingImage()
    {
        try {
            ImageIO.write(img, "png", new File("././screenshot.png"));
            Desktop desktop = Desktop.getDesktop();
            desktop.open(new File("././screenshot.png"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}


class Handler extends MouseInputAdapter{
    Draw dr;

    public Handler(Draw painting)
    {
        dr = painting;
    }

    @Override
    public void mousePressed(MouseEvent e) 
    {
        dr.firstPress(e.getPoint());
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {   
        dr.painting(e.getPoint());
    }
    @Override
    public void mouseReleased(MouseEvent e)
    {

        try {
            dr.clear();
        } catch (Exception b) {
            
        }
        //dr.screenshot();
    }


}
class keyHandler extends KeyAdapter{

    @Override
    public void keyPressed(KeyEvent e)
    {
        System.out.println(e.getKeyCode());
        if(e.getKeyCode() == 27){
            System.exit(0);
        }
    }
}

