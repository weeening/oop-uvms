package bookingSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminOptionPage extends JFrame implements ActionListener {

    JButton ViewBookingBtn, ViewUserBtn, ViewVenueBtn, LogoutBtn;
    JFrame OFrame = new JFrame("University Venue Management System - Main Page");

    AdminOptionPage() {

        OFrame.setResizable(false);
        OFrame.setPreferredSize(new Dimension(900, 500));
        OFrame.pack();
        OFrame.setVisible(true);
        OFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        OFrame.setLocationRelativeTo(null);

        ViewBookingBtn = new JButton("View Booking Detail");
        ViewUserBtn = new JButton("View User Detail");
        ViewVenueBtn = new JButton("View Venue Detail");
        LogoutBtn = new JButton("Logout");

        OFrame.setLayout( null );
        ViewBookingBtn.setBounds(370,50,200,55);
        ViewUserBtn.setBounds(370 ,130,200,55);
        ViewVenueBtn.setBounds(370 ,210,200,55);
        LogoutBtn.setBounds(770, 430, 100, 25);

        OFrame.add(ViewBookingBtn);
        OFrame.add(ViewUserBtn);
        OFrame.add(ViewVenueBtn);
        OFrame.add(LogoutBtn);

        ViewBookingBtn.addActionListener(this);
        ViewUserBtn.addActionListener(this);
        ViewVenueBtn.addActionListener(this);
        LogoutBtn.addActionListener(this);
    }

    //create a event
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ViewBookingBtn) {
            OFrame.dispose();
            new AdminViewBookingPage();
        }
        else if (e.getSource() == ViewUserBtn) {
            OFrame.dispose();
            new AdminViewUserPage();
        }
        else if (e.getSource() == ViewVenueBtn) {
            OFrame.dispose();
            new AdminViewVenuePage();
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
// Final check 23 Sept 11.01pm

