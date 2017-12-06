package bookingSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Vector;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.optionalusertools.PickerUtilities;

public class MakeBookingPage extends JFrame implements ActionListener{

    JLabel LabelDate;
    JLabel LabelSTime;
    JLabel LabelETime;
    DatePicker DatePicker;
    static TimePicker STimePicker;
    static TimePicker ETimePicker;

    JButton SearchBtn;
    JButton NextBtn;
    JButton CancelBtn;

    JTable Table = new JTable();
    JScrollPane VenueScoll;
    JLabel LabelVenue;
    JTextField VenueText;

    DefaultTableModel model;

    static String isUser;

    JFrame MFrame = new JFrame("University Venue Management System - Make New Booking Page");

    public MakeBookingPage() {

        MFrame.setResizable(false);
        MFrame.setPreferredSize(new Dimension(900,500));
        MFrame.pack();
        MFrame.setVisible(true);
        MFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MFrame.setLocationRelativeTo(null);

        //DateTimePicker
        LabelDate = new JLabel("Date : ");
        LabelSTime = new JLabel("Start Time : ");
        LabelETime = new JLabel("End Time : ");

        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.setFirstDayOfWeek(DayOfWeek.SUNDAY);
        dateSettings.setFormatForDatesCommonEra("yyyy-MM-dd");

        DatePicker = new DatePicker(dateSettings);
        LocalDate today = LocalDate.now();
        dateSettings.setDateRangeLimits(today.minusDays(0), today.plusDays(60));

        TimePickerSettings timeSettings = new TimePickerSettings();
        timeSettings.setFormatForDisplayTime(PickerUtilities.createFormatterFromPatternString(
                "HH:mm:ss", timeSettings.getLocale()));
        timeSettings.setFormatForMenuTimes(PickerUtilities.createFormatterFromPatternString(
                "HH:mm:ss", timeSettings.getLocale()));
        timeSettings.initialTime = LocalTime.of(00, 00, 00);
        timeSettings.generatePotentialMenuTimes(TimePickerSettings.TimeIncrement.OneHour,LocalTime.of(07,00,00),LocalTime.of(23,00,00));
        timeSettings.setAllowKeyboardEditing(false);

        STimePicker = new TimePicker(timeSettings);
        ETimePicker = new TimePicker(timeSettings);

        //Search button
        SearchBtn = new JButton("Show Venue");

        LabelVenue = new JLabel("   Write the Venue ID : ");
        VenueText = new JTextField(10);

        //load table from database
        //load the table into the jtable
        model = new DefaultTableModel();
        loadData();
        Table.setModel(model);
        Table.setBounds(50,40,400,300);
        VenueScoll = new JScrollPane(Table);

        //Button
        NextBtn = new JButton("Next");
        CancelBtn = new JButton("Cancel");

        LabelDate.setBounds(100,20,100,25);
        LabelSTime.setBounds(100,50,100,25);
        LabelETime.setBounds(100,80,100,25);

        DatePicker.setBounds(470,20,400,25);
        STimePicker.setBounds(470,50,400,25);
        ETimePicker.setBounds(470,80,400,25);

        SearchBtn.setBounds(720,125,150,25);

        LabelVenue.setBounds(90,150,200,25);
        VenueText.setBounds(215,150,150,25);
        VenueScoll.setBounds(100,200,770,200);

        NextBtn.setBounds(770,430,100,25);
        CancelBtn.setBounds(660,430,100,25);

        MFrame.setLayout(null);
        MFrame.add(LabelDate);
        MFrame.add(LabelSTime);
        MFrame.add(LabelETime);
        MFrame.add(DatePicker);
        MFrame.add(STimePicker);
        MFrame.add(ETimePicker);
        MFrame.add(SearchBtn);
        MFrame.add(NextBtn);
        MFrame.add(CancelBtn);
        MFrame.add(LabelVenue);
        MFrame.add(VenueText);
        MFrame.add(VenueScoll);

        SearchBtn.addActionListener(this);
        CancelBtn.addActionListener(this);
        NextBtn.addActionListener(this);

        Table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                // i = the index of the selected row
                int i = Table.getSelectedRow();

                VenueText.setText(model.getValueAt(i, 0).toString());
            }}
        );
    }

    private void loadData() {

        try (Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/uvms","root","")) {
            PreparedStatement ps = connect.prepareStatement("SELECT * FROM venue WHERE venue.venueID NOT IN (SELECT venue.venueID FROM venue,booking WHERE (venue.venueID = booking.venueID AND booking.bookingDate = ? AND booking.endTime > ? AND booking.startTime < booking.endTime AND booking.startTime < ? AND booking.endTime > booking.startTime ) ) ORDER BY venue.venueID ");
            ps.setString(1, DatePicker.getDate().toString());
            ps.setString(2, STimePicker.getTime().toString());
            ps.setString(3, ETimePicker.getTime().toString());
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

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()== SearchBtn){
            loadData();
        }
        else if(e.getSource()== CancelBtn){
            try {
                isUser = LoginPage.TextID.getText();
                if(isUser.equals(LoginPage.userID))
                {
                    MFrame.dispose();
                    new OptionPage();
                }

                else if(isUser.equals(LoginPage.adminID))
                {
                    MFrame.dispose();
                    new AdminOptionPage();
                }
            } catch (Exception err) {
                JOptionPane.showMessageDialog(null, "Unable to return to option page!", "Option error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if(e.getSource() == NextBtn){
            try {
                Booking b = new Booking();
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog(null, "Confirm to make booking?", "Confirmation", dialogButton);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    b.addBooking(DatePicker.getDate().toString(), STimePicker.getTime().toString(), ETimePicker.getTime().toString(), VenueText.getText(), LoginPage.getUserID());
                    JOptionPane.showMessageDialog(null, "Booking successfully made!");
                    MFrame.dispose();
                    new ViewPage();
                }
                else if (dialogResult == JOptionPane.NO_OPTION) {
                    MFrame.dispose();
                    new MakeBookingPage();
                }
            }catch(Exception err)
            {
                JOptionPane.showMessageDialog(null, "Unable to perform changes now. Try again later.", "Update failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

// Final Check 23 Sept 10.16 pm
