package bookingSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Font;
import java.sql.*;
import java.util.Vector;

public class AdminViewVenuePage  extends JFrame implements ActionListener{

    JTextField VIDText, BuildText, RoomText, CapacityText;

    JLabel LabelVID, LabelBuild, LabelRoom, LabelCapacity;

    JButton  AddBtn, DeleteBtn, EditBtn, BackBtn ;
    JScrollPane TableScoll;

    JTable Table = new JTable();
    DefaultTableModel model;
    JFrame VFrame = new JFrame("University Venue Management System - View Venue Page");

    public AdminViewVenuePage(){

        VFrame.setResizable(false);
        VFrame.setPreferredSize(new Dimension(900,500));
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
        Font font = new Font("",1,22);
        Table.setFont(font);
        Table.setRowHeight(30);

        LabelVID = new JLabel("Venue ID : ");
        LabelBuild = new JLabel("Building : ");
        LabelRoom = new JLabel("Room : ");
        LabelCapacity = new JLabel("Capacity : ");

        VIDText= new JTextField();
        BuildText = new JTextField();
        RoomText = new JTextField();
        CapacityText = new JTextField();

        AddBtn = new JButton("Add");
        DeleteBtn = new JButton("Delete");
        EditBtn = new JButton("Edit");
        BackBtn = new JButton("Back");

        LabelVID.setBounds(20, 220, 100, 25);
        LabelBuild.setBounds(20, 250, 100, 25);
        LabelRoom.setBounds(20, 280, 100, 25);
        LabelCapacity.setBounds(20, 310, 100, 25);

        VIDText.setBounds(160, 220, 180, 25);
        BuildText.setBounds(160, 250, 180, 25);
        RoomText.setBounds(160, 280, 180, 25);
        CapacityText.setBounds(160, 310, 180, 25);

        AddBtn.setBounds(390,220,100,25);
        DeleteBtn.setBounds(390, 265, 100, 25);
        EditBtn.setBounds(390,310,100,25);
        BackBtn.setBounds(770, 430, 100, 25);

        // create JScrollPane
        TableScoll = new JScrollPane(Table);
        TableScoll.setBounds(0, 0, 895, 200);

        VFrame.setLayout(null);

        VFrame.add(TableScoll);

        VFrame.add(LabelVID);
        VFrame.add(LabelBuild);
        VFrame.add(LabelRoom);
        VFrame.add(LabelCapacity);

        VFrame.add(VIDText);
        VFrame.add(BuildText);
        VFrame.add(RoomText);
        VFrame.add(CapacityText);

        VFrame.add(AddBtn);
        VFrame.add(DeleteBtn);
        VFrame.add(EditBtn);
        VFrame.add(BackBtn);

        AddBtn.addActionListener(this);
        DeleteBtn.addActionListener(this);
        BackBtn.addActionListener(this);
        EditBtn.addActionListener(this);

        Table.addMouseListener(new MouseAdapter(){

            public void mouseClicked(MouseEvent e){

                // i = the index of the selected row
                int i = Table.getSelectedRow();

                VIDText.setText(model.getValueAt(i, 0).toString());
                BuildText.setText(model.getValueAt(i, 1).toString());
                RoomText.setText(model.getValueAt(i, 2).toString());
                CapacityText.setText(model.getValueAt(i, 3).toString());
            }
        });
    }

    private void loadData() {

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/uvms","root","");
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery("select * from venue");
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

    public void actionPerformed(ActionEvent e) {
        try {
            Venue v = new Venue();
            int cap = Integer.parseInt(CapacityText.getText());

            if (e.getSource() == AddBtn) {
                //add to sql database
                v.addVenue(VIDText.getText(),
                        BuildText.getText(),
                        RoomText.getText(),
                        cap);

                // add row to the model
                model.addRow(new Object[]{
                        VIDText.getText(),
                        BuildText.getText(),
                        RoomText.getText(),
                        CapacityText.getText(),
                });

            } else if (e.getSource() == DeleteBtn) {
                //delete from sql
                v.deleteVenue(VIDText.getText());

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
                    model.setValueAt(VIDText.getText(), i, 0);
                    v.editVenue(VIDText.getText(),"venueID",VIDText.getText());
                    model.setValueAt(BuildText.getText(), i, 1);
                    v.editVenue(VIDText.getText(),"building",BuildText.getText());
                    model.setValueAt(RoomText.getText(), i, 2);
                    v.editVenue(VIDText.getText(),"room",RoomText.getText());
                    model.setValueAt(CapacityText.getText(), i, 3);
                    v.editVenue(VIDText.getText(),"capacity",cap);

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

//Final Check 23 Sept 11.13 pm
