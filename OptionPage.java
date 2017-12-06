package bookingSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class OptionPage extends JFrame implements ActionListener{
    JButton ViewBtn, AddBtn, LogoutBtn;
    JFrame OFrame = new JFrame("University Venue Management System - Main Page");

    public OptionPage() {

        OFrame.setResizable(false);
        OFrame.setPreferredSize(new Dimension(900,500));
        OFrame.pack();
        OFrame.setVisible(true);
        OFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        OFrame.setLocationRelativeTo(null);

        ViewBtn = new JButton("View Booking");
        AddBtn = new JButton("Make a new Booking");
        LogoutBtn = new JButton("Logout");

        ViewBtn.setBounds(370,100,200,55);
        AddBtn.setBounds(370 ,180,200,55);
        LogoutBtn.setBounds(770, 430, 100, 25);

        OFrame.setLayout(null);

        OFrame.add(ViewBtn);
        OFrame.add(AddBtn);
        OFrame.add(LogoutBtn);

        ViewBtn.addActionListener(this);
        AddBtn.addActionListener(this);
        LogoutBtn.addActionListener(this);
    }

    //create a event
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == ViewBtn){

            OFrame.dispose();
            new ViewPage();
        }
        else if(e.getSource() == AddBtn)
        {
            OFrame.dispose();
            new MakeBookingPage();
        }
        else if(e.getSource() == LogoutBtn)
        {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog (null, "Confirm Logout??","Logout",dialogButton);
            if(dialogResult == JOptionPane.YES_OPTION){
                JOptionPane.showMessageDialog(null,"Thank you for Using Our System!!!");
                OFrame.dispose();
            }
        }
    }
}
// Final Check 23 Sept 11.20 pm