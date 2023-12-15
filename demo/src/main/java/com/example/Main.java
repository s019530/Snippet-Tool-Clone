package com.example;

import javax.swing.JFrame;
import javax.swing.UIManager;

public class Main {
    public static Window frame;
    public static void main(String[] args) {
        
        try { 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame = new Window();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setSize(1000,1000);
        frame.setAlwaysOnTop(true);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}