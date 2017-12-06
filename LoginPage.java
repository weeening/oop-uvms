package bookingSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginPage extends JFrame implements ActionListener,Run{

    JButton LoginBtn = new JButton("Login");

    JLabel Label = new JLabel("Welcome To University Venue Management System");
    JLabel LabelID = new JLabel("ID:");
    JLabel LabelPass = new JLabel("Password:");
    static JTextField  TextID = new JTextField(15);
    JTextField TextPass = new JPasswordField(15);
    JFrame LFrame = new JFrame("University Venue Management System - Login Page");
    static String userID;
    static String adminID;

    public LoginPage() {

        LFrame.setResizable(false);
        LFrame.setPreferredSize(new Dimension(900,500));
        LFrame.pack();
        LFrame.setVisible(true);
        LFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        LFrame.setLocationRelativeTo(null);

        Label.setBounds(325,50,300,25);
        LabelID.setBounds(365,120,100,25);
        TextID.setBounds(420 ,120,200,25);

        LabelPass.setBounds(320 ,180,100,25);
        TextPass.setBounds(420 ,180,200,25);

        LoginBtn.setBounds(420 ,240,100,25);

        LFrame.setLayout(null);

        LFrame.add(Label);
        LFrame.add(LabelID);
        LFrame.add(TextID);
        LFrame.add(LabelPass);
        LFrame.add(TextPass);
        LFrame.add(LoginBtn);

        LoginBtn.addActionListener(this);
    }

    public static String getUserID()
    {
     return TextID.getText();
    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == LoginBtn)
        {
            try {
                Users u = new Users();
                if(u.login(TextID.getText(), TextPass.getText()))
                {
                    JOptionPane.showMessageDialog(null,"Log in success!", "Login success",JOptionPane.INFORMATION_MESSAGE);
                    LFrame.dispose();
                    new OptionPage();
                    userID = getUserID();
                }

                else if(u.adminLogin(TextID.getText(), TextPass.getText()))
                {
                    JOptionPane.showMessageDialog(null,"Log in success!", "Login success", JOptionPane.INFORMATION_MESSAGE);
                    LFrame.dispose();
                    new AdminOptionPage();
                    adminID = getUserID();
                }

                else
                {
                    JOptionPane.showMessageDialog(null, "Invalid user ID or password!", "Login failed", JOptionPane.ERROR_MESSAGE);
                }
            }catch(Exception err)
            {
                JOptionPane.showMessageDialog(null, "Unable to log in now. Try again later.", "Login failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

//Final check 23 Sept 11.15 pm