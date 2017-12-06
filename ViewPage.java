package bookingSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Font;
import java.sql.*;
import java.util.Vector;
import java.io.*;

public class ViewPage extends JFrame implements ActionListener{

    JTextField BIDText, BDateText, STimeText, ETimeText, VIDText, TDateText;

    JLabel LabelBID, LabelBD, LabelSTime, LabelETime, LabelVID, LabelTDate;

    JButton  DeleteBtn, EditBtn, BackBtn,PrintBtn ;
    JScrollPane TableScoll;

    JTable Table = new JTable();
    DefaultTableModel model;
    JFrame VFrame = new JFrame("University Venue Management System - View Booking Page");

    public ViewPage() {
        VFrame.setResizable(false);
        VFrame.setPreferredSize(new Dimension(900,500));
        VFrame.pack();
        VFrame.setVisible(true);
        VFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        VFrame.setLocationRelativeTo(null);

        //load table from database
        model = new DefaultTableModel();
        loadData();
        Table.setModel(model);

        // Change A JTable Background Color, Font Size, Font Color, Row Height
        Table.setBackground(Color.LIGHT_GRAY);
        Table.setForeground(Color.black);
        Font font = new Font("",1,22);
        Table.setFont(font);
        Table.setRowHeight(30);

        LabelBID = new JLabel("Booking ID : ");
        LabelBD = new JLabel("Date : ");
        LabelSTime = new JLabel("Start Time : ");
        LabelETime = new JLabel("End Time : ");
        LabelVID = new JLabel("Venue ID : ");
        LabelTDate = new JLabel("Transaction Date : ");

        BIDText = new JTextField();
        BDateText = new JTextField();
        STimeText = new JTextField();
        ETimeText= new JTextField();
        VIDText= new JTextField();
        TDateText= new JTextField();

        DeleteBtn = new JButton("Delete");
        EditBtn = new JButton("Edit");
        BackBtn = new JButton("Back");
        PrintBtn = new JButton ("Print");

        LabelBID.setBounds(20, 220, 120, 25);
        LabelBD.setBounds(20, 250, 120, 25);
        LabelSTime.setBounds(20, 280, 120, 25);
        LabelETime.setBounds(20, 310, 120, 25);
        LabelVID.setBounds(20, 340, 120, 25);
        LabelTDate.setBounds(20, 370, 120, 25);

        BIDText.setBounds(160, 220, 100, 25);
        BDateText.setBounds(160, 250, 100, 25);
        STimeText.setBounds(160, 280, 100, 25);
        ETimeText.setBounds(160, 310, 100, 25);
        VIDText.setBounds(160, 340, 100, 25);
        TDateText.setBounds(160, 370, 100, 25);

        DeleteBtn.setBounds(310, 220, 100, 25);
        EditBtn.setBounds(310,265,100,25);
        PrintBtn.setBounds(310, 310, 100, 25);
        BackBtn.setBounds(770, 430, 100, 25);

        // create JScrollPane
        TableScoll = new JScrollPane(Table);
        TableScoll.setBounds(0, 0, 880, 200);

        VFrame.setLayout(null);

        VFrame.add(TableScoll);

        VFrame.add(LabelBID);
        VFrame.add(LabelBD);
        VFrame.add(LabelSTime);
        VFrame.add(LabelETime);
        VFrame.add(LabelVID);
        VFrame.add(LabelTDate);

        VFrame.add(BIDText);
        VFrame.add(BDateText);
        VFrame.add(STimeText);
        VFrame.add(ETimeText);
        VFrame.add(VIDText);
        VFrame.add(TDateText);

        VFrame.add(DeleteBtn);
        VFrame.add(EditBtn);
        VFrame.add(BackBtn);
        VFrame.add(PrintBtn);

        PrintBtn.addActionListener(this);
        DeleteBtn.addActionListener(this);
        BackBtn.addActionListener(this);
        EditBtn.addActionListener(this);

        Table.addMouseListener(new MouseAdapter(){

            public void mouseClicked(MouseEvent e){

                // i = the index of the selected row
                int i = Table.getSelectedRow();

                BIDText.setText(model.getValueAt(i, 0).toString());
                BDateText.setText(model.getValueAt(i, 1).toString());
                STimeText.setText(model.getValueAt(i, 2).toString());
                ETimeText.setText(model.getValueAt(i, 3).toString());
                VIDText.setText(model.getValueAt(i, 4).toString());
                TDateText.setText(model.getValueAt(i, 5).toString());
            }
        });
    }

    private void loadData() {

        try (Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/uvms","root","")) {
            PreparedStatement ps = connect.prepareStatement("SELECT bookingID, bookingDate,startTime, endTime, venueID, transactDate from uvms.booking WHERE userID = ?");
            ps.setString(1,LoginPage.userID);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();

            // Names of columns
            Vector<String> columnNames = new Vector<String>();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(metaData.getColumnName(i));
            }

            // Data of the table
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            while (rs.next()) {
                Vector<Object> vector = new Vector<Object>();
                for (int i = 1; i <= columnCount; i++) {
                    vector.add(rs.getObject(i));
                }
                data.add(vector);
            }

            model.setDataVector(data, columnNames);
        }
        catch (Exception e)
        {
        }
    }

    public void printConfirmation()
    {
        java.util.Date date = new java.util.Date();

        try {
            PrintWriter out = new PrintWriter(new FileWriter(LoginPage.getUserID() + " Confirmation.txt"));
            out.println("Booking Confirmation");
            out.println();
            int row = Table.getSelectedRow();
            if (row >= 0) {
                for (int column = 0; column < Table.getColumnCount(); column++) {
                    out.print(Table.getColumnName(column) + ": ");
                    out.println(Table.getValueAt(row, column));
                }
            }

            Venue v = new Venue();
            out.println();
            out.println();
            out.print("Venue Details: ");
            out.println(v.venueDetails(VIDText.getText())+ " people maximum");

            Users u = new Users();
            out.println();
            out.println();
            out.print("Booked by: ");
            out.println(u.userDetails(LoginPage.getUserID()));
            out.println("Printed on: " + date);

            out.close();
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"Unable to save confirmation. Try later.", "Save Error" ,JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actionPerformed(ActionEvent e) {
        try {
            Booking b = new Booking();
            int bookingID = Integer.parseInt(BIDText.getText());

            if (e.getSource() == DeleteBtn) {
                b.deleteBooking(bookingID);
                int i = Table.getSelectedRow();
                if (i >= 0) {
                    // remove a row from jtable
                    model.removeRow(i);
                } else {
                    System.out.println("Delete Error");
                }
            } else if (e.getSource() == EditBtn) {
                // i = the index of the selected row
                int i = Table.getSelectedRow();

                if (i >= 0) {
                    model.setValueAt(BIDText.getText(), i, 0); //cannot edit booking ID
                    model.setValueAt(BDateText.getText(), i, 1);
                    b.editBooking(bookingID, "bookingDate", BDateText.getText());
                    model.setValueAt(STimeText.getText(), i, 2);
                    b.editBooking(bookingID, "startTime", STimeText.getText());
                    model.setValueAt(ETimeText.getText(), i, 3);
                    b.editBooking(bookingID, "endTime", ETimeText.getText());
                    model.setValueAt(VIDText.getText(), i, 4);
                    b.editBooking(bookingID, "venueID", VIDText.getText());
                    model.setValueAt(TDateText.getText(), i, 5);
                    //cannot edit transaction date
                    loadData();
                } else {
                    System.out.println("Update Error");
                }

            } else if (e.getSource() == PrintBtn) {
                printConfirmation();
                JOptionPane.showMessageDialog(null, "Print Success!");
            }
            else if(e.getSource() == BackBtn)
            {
                VFrame.dispose();
                new OptionPage();
            }
        }
        catch(Exception err) {
            if (e.getSource() == BackBtn) {
                VFrame.dispose();
                new OptionPage();
            } else {
                JOptionPane.showMessageDialog(null, "Unable to perform changes now. Try again later.", "Update failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

//Final Check 23 Sept 11.23 pm
