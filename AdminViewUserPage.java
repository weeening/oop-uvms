package bookingSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Font;
import java.sql.ResultSetMetaData;
import java.util.Vector;
import java.sql.*;

public class AdminViewUserPage extends JFrame implements ActionListener {
    JTextField UIDText, UPassText, UNameText, CNoText;
    JComboBox UStat;
    String[] uStrings = {"Lecturer", "Student"};

    JLabel LabelUID, LabelUPass, LabelUName, LabelCNo, LabelUStatus;

    JButton AddBtn, DeleteBtn, EditBtn, BackBtn;
    JScrollPane TableScoll;

    JTable Table = new JTable();
    DefaultTableModel model;
    JFrame VFrame = new JFrame("University Venue Management System - View User Page");

    public AdminViewUserPage() {
        VFrame.setResizable(false);
        VFrame.setPreferredSize(new Dimension(900, 500));
        VFrame.pack();
        VFrame.setVisible(true);
        VFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        VFrame.setLocationRelativeTo(null);

        //load the table into the jtable
        model = new DefaultTableModel();
        loadData();
        Table.setModel(model);

        // Change A JTable Background Color, Font Size, Font Color, Row Height
        Table.setBackground(Color.LIGHT_GRAY);
        Table.setForeground(Color.black);
        Font font = new Font("", 1, 22);
        Table.setFont(font);
        Table.setRowHeight(30);

        LabelUID = new JLabel("User ID : ");
        LabelUPass = new JLabel("User Pass : ");
        LabelUName = new JLabel("User Name : ");
        LabelCNo = new JLabel("Contact No : ");
        LabelUStatus = new JLabel("User Status : ");

        UIDText = new JTextField();
        UPassText = new JTextField();
        UNameText = new JTextField();
        CNoText = new JTextField();
        UStat = new JComboBox(uStrings);

        AddBtn = new JButton("Add");
        DeleteBtn = new JButton("Delete");
        EditBtn = new JButton("Edit");
        BackBtn = new JButton("Back");

        LabelUID.setBounds(20, 220, 100, 25);
        LabelUPass.setBounds(20, 250, 100, 25);
        LabelUName.setBounds(20, 280, 100, 25);
        LabelCNo.setBounds(20, 310, 100, 25);
        LabelUStatus.setBounds(20, 340, 100, 25);

        UIDText.setBounds(160, 220, 100, 25);
        UPassText.setBounds(160, 250, 100, 25);
        UNameText.setBounds(160, 280, 100, 25);
        CNoText.setBounds(160, 310, 100, 25);
        UStat.setBounds(160, 340, 100, 25);

        AddBtn.setBounds(310, 220, 100, 25);
        DeleteBtn.setBounds(310, 265, 100, 25);
        EditBtn.setBounds(310, 310, 100, 25);
        BackBtn.setBounds(770, 430, 100, 25);

        // create JScrollPane
        TableScoll = new JScrollPane(Table);
        TableScoll.setBounds(0, 0, 895, 200);

        VFrame.setLayout(null);

        VFrame.add(TableScoll);

        VFrame.add(LabelUID);
        VFrame.add(LabelUPass);
        VFrame.add(LabelUName);
        VFrame.add(LabelCNo);
        VFrame.add(LabelUStatus);

        VFrame.add(UIDText);
        VFrame.add(UPassText);
        VFrame.add(UNameText);
        VFrame.add(CNoText);
        VFrame.add(UStat);

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

                UIDText.setText(model.getValueAt(i, 0).toString());
                UPassText.setText(model.getValueAt(i, 1).toString());
                UNameText.setText(model.getValueAt(i, 2).toString());
                CNoText.setText(model.getValueAt(i, 3).toString());
                UStat.getSelectedItem();
            }
        });
    }

    private void loadData() {

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/uvms", "root", "");
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery("select * from users");
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
            Users u = new Users();
            if (e.getSource() == AddBtn)
            {
                //add row to the table in database
                u.addUsers(UIDText.getText(),
                        UPassText.getText(),
                        UNameText.getText(),
                        CNoText.getText(),
                        UStat.getSelectedItem().toString());

                // add row to the model
                model.addRow(new Object[]{
                        UIDText.getText(),
                        UPassText.getText(),
                        UNameText.getText(),
                        CNoText.getText(),
                        UStat.getSelectedItem().toString(),
                });

            } else if (e.getSource() == DeleteBtn) {
                //delete row from sql
                u.deleteUsers(UIDText.getText());

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
                    model.setValueAt(UIDText.getText(), i, 0);
                    u.editUsers(UIDText.getText(),"userID",UIDText.getText());
                    model.setValueAt(UPassText.getText(), i, 1);
                    u.editUsers(UIDText.getText(),"userPass",UPassText.getText());
                    model.setValueAt(UNameText.getText(), i, 2);
                    u.editUsers(UIDText.getText(),"userName",UNameText.getText());
                    model.setValueAt(CNoText.getText(), i, 3);
                    u.editUsers(UIDText.getText(),"contactNo",CNoText.getText());
                    model.setValueAt(UStat.getSelectedItem().toString(), i, 4);
                    u.editUsers(UIDText.getText(),"userStatus",UStat.getSelectedItem().toString());
                } else {
                    System.out.println("Update Error");
                }

            } else if (e.getSource() == BackBtn) {
                VFrame.dispose();
                new AdminOptionPage();
            }
        }
        catch(Exception err)
        {
            if (e.getSource() == BackBtn) {
                VFrame.dispose();
                new AdminOptionPage();
            } else {
                JOptionPane.showMessageDialog(null, "Unable to perform changes now. Try again later.", "Update failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
// Final check 23 Sept 11.13pm
