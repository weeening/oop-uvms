package bookingSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;

public class AdminViewBookingPage extends JFrame implements ActionListener {

    JTextField BIDText, BDateText, STimeText, ETimeText, VIDText, UIDText, TDateText;

    JLabel LabelBID, LabelBD, LabelSTime, LabelETime, LabelVID, LabelUID, LabelTDate;

    JButton AddBtn, DeleteBtn, EditBtn, BackBtn;
    JScrollPane TableScoll;

    JTable Table = new JTable();
    DefaultTableModel model;
    JFrame VFrame = new JFrame("University Venue Management System - View Booking Page");

    public AdminViewBookingPage() {
        VFrame.setResizable(false);
        VFrame.setPreferredSize(new Dimension(900, 500));
        VFrame.pack();
        VFrame.setVisible(true);
        VFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        VFrame.setLocationRelativeTo(null);

        //load data into table
        model = new DefaultTableModel();
        loadData();
        Table.setModel(model);

        // Change A JTable Background Color, Font Size, Font Color, Row Height
        Table.setBackground(Color.LIGHT_GRAY);
        Table.setForeground(Color.black);
        Font font = new Font("", 1, 22);
        Table.setFont(font);
        Table.setRowHeight(30);

        LabelBID = new JLabel("Booking ID : ");
        LabelBD = new JLabel("Date : ");
        LabelSTime = new JLabel("Start Time : ");
        LabelETime = new JLabel("End Time : ");
        LabelVID = new JLabel("Venue ID : ");
        LabelUID = new JLabel("User ID : ");
        LabelTDate = new JLabel("Transaction Date : ");

        BIDText = new JTextField();
        BDateText = new JTextField();
        STimeText = new JTextField();
        ETimeText = new JTextField();
        VIDText = new JTextField();
        UIDText = new JTextField();
        TDateText = new JTextField();

        AddBtn = new JButton("Add");
        DeleteBtn = new JButton("Delete");
        EditBtn = new JButton("Edit");
        BackBtn = new JButton("Back");

        LabelBID.setBounds(20, 220, 100, 25);
        LabelBD.setBounds(20, 250, 100, 25);
        LabelSTime.setBounds(20, 280, 100, 25);
        LabelETime.setBounds(20, 310, 100, 25);
        LabelVID.setBounds(20, 340, 100, 25);
        LabelUID.setBounds(20, 370, 100, 25);
        LabelTDate.setBounds(20, 400, 100, 25);

        BIDText.setBounds(160, 220, 100, 25);
        BDateText.setBounds(160, 250, 100, 25);
        STimeText.setBounds(160, 280, 100, 25);
        ETimeText.setBounds(160, 310, 100, 25);
        VIDText.setBounds(160, 340, 100, 25);
        UIDText.setBounds(160, 370, 100, 25);
        TDateText.setBounds(160, 400, 100, 25);

        AddBtn.setBounds(310, 220, 100, 25);
        DeleteBtn.setBounds(310, 265, 100, 25);
        EditBtn.setBounds(310, 310, 100, 25);
        BackBtn.setBounds(770, 430, 100, 25);

        // create JScrollPane
        TableScoll = new JScrollPane(Table);
        TableScoll.setBounds(0, 0, 895, 200);

        VFrame.setLayout(null);

        VFrame.add(TableScoll);

        VFrame.add(LabelBID);
        VFrame.add(LabelBD);
        VFrame.add(LabelSTime);
        VFrame.add(LabelETime);
        VFrame.add(LabelVID);
        VFrame.add(LabelUID);
        VFrame.add(LabelTDate);

        VFrame.add(BIDText);
        VFrame.add(BDateText);
        VFrame.add(STimeText);
        VFrame.add(ETimeText);
        VFrame.add(VIDText);
        VFrame.add(UIDText);
        VFrame.add(TDateText);

        VFrame.add(AddBtn);
        VFrame.add(DeleteBtn);
        VFrame.add(EditBtn);
        VFrame.add(BackBtn);

        AddBtn.addActionListener(this);
        DeleteBtn.addActionListener(this);
        BackBtn.addActionListener(this);
        EditBtn.addActionListener(this);

        Table.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {

                // i = the index of the selected row
                int i = Table.getSelectedRow();

                BIDText.setText(model.getValueAt(i, 0).toString());
                BDateText.setText(model.getValueAt(i, 1).toString());
                STimeText.setText(model.getValueAt(i, 2).toString());
                ETimeText.setText(model.getValueAt(i, 3).toString());
                VIDText.setText(model.getValueAt(i, 4).toString());
                UIDText.setText(model.getValueAt(i, 5).toString());
                TDateText.setText(model.getValueAt(i, 6).toString());
            }
        });
    }

    private void loadData() {

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/uvms", "root", "");
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery("select * from booking");
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
        } catch (Exception e) {
        }
    }

    public void actionPerformed(ActionEvent e) {
        try {
            Booking b = new Booking();
            int bookingID = Integer.parseInt(BIDText.getText());

            if (e.getSource() == AddBtn) {

                b.addBooking(
                        BDateText.getText(),
                        STimeText.getText(),
                        ETimeText.getText(),
                        VIDText.getText(),
                        UIDText.getText());

                // add row to the model
                model.addRow(new Object[]{
                        BIDText.getText(),
                        BDateText.getText(),
                        STimeText.getText(),
                        ETimeText.getText(),
                        VIDText.getText(),
                        UIDText.getText(),
                        TDateText.getText(),
                });

            } else if (e.getSource() == DeleteBtn) {
                b.deleteBooking(bookingID);

                int i = Table.getSelectedRow();
                if (i >= 0) {
                    // remove a row from jtable
                    model.removeRow(i);
                } else {
                    System.out.println("Delete Error");
                }
            }
            else if (e.getSource() == EditBtn) {
                // i = the index of the selected row
                int i = Table.getSelectedRow();

                if (i >= 0) {
                    model.setValueAt(BIDText.getText(), i, 0); //cannot edit booking id
                    model.setValueAt(BDateText.getText(), i, 1);
                    b.editBooking(bookingID, "bookingDate", BDateText.getText());
                    model.setValueAt(STimeText.getText(), i, 2);
                    b.editBooking(bookingID, "startTime", STimeText.getText());
                    model.setValueAt(ETimeText.getText(), i, 3);
                    b.editBooking(bookingID, "endTime", ETimeText.getText());
                    model.setValueAt(VIDText.getText(), i, 4);
                    b.editBooking(bookingID, "venueID", VIDText.getText());
                    model.setValueAt(UIDText.getText(),i,5);
                    b.editBooking(bookingID, "userID", UIDText.getText());
                    model.setValueAt(TDateText.getText(), i, 6); //cannot edit transact date
                } else {
                    System.out.println("Update Error");
                }

            }  else if (e.getSource() == BackBtn) {
                VFrame.dispose();
                new AdminOptionPage();
            }
        } catch (Exception err) {
            if (e.getSource() == BackBtn) {
                VFrame.dispose();
                new AdminOptionPage();
            }
            else{
                JOptionPane.showMessageDialog(null, "Unable to perform changes now. Try again later.", "Update failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

//Final Check 23 Sept 11.13 pm