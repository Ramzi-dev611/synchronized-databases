package main.java.branchOffice;

import main.java.DataBaseAccess;
import main.java.Sale;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BranchOffice  extends JFrame {

    protected JPanel formZone;
    protected final String [] fieldsNames = {"Sale ID", "Date of Sale", "Region (characters) ", 
            "Product (caracters)",
            "Quantity (integer)", "Cost (double)", "Tax (double)"};
    protected JLabel errorMessage;
    protected JLabel successMessage;
    protected ArrayList<Component> inputs;
    protected  JButton saveButton;
    protected JPanel actionsZone;
    protected JButton sendMessage;
    protected JButton checkDatabase;
    protected DataBaseAccess dba;
    protected BranchMessaging messageService;

    public void buildInputField(int i){
        JLabel label = new JLabel(fieldsNames[i]);
        label.setFont(new Font("Helvetica", Font.PLAIN, 16));
        label.setForeground(Color.white);
        label.setBounds(110, 70+77*i, 200, 30 );
        formZone.add(label);
        if (i!=1){
            JTextField field = new JTextField();
            field.setFont(new Font("Helvetica", Font.PLAIN, 20));
            field.setBounds(80, 100 + 77 * i, 400, 40);
            field.setHorizontalAlignment(JTextField.CENTER);
            if (i == 6 || i ==5){
                field.getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        reaction();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        reaction();
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        reaction();
                    }
                    private boolean verifyNumeric (String str) {
                        try{
                            Double.parseDouble(str);
                            return true;
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    }

                    private void reaction () {
                        if (!(field.getText().equals("") || verifyNumeric(field.getText()))){
                            field.setBorder(BorderFactory.createLineBorder(Color.red));
                            errorMessage.setVisible(true);
                        } else {
                            field.setBorder(BorderFactory.createLineBorder(Color.white));
                            errorMessage.setVisible(false);
                        }
                    }

                });
            }
            else if (i == 4) {
                field.getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        reaction();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        reaction();
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        reaction();
                    }
                    private boolean verifyNumeric (String str) {
                        try{
                            Integer.parseInt(str);
                            return true;
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    }

                    private void reaction () {
                        if (!(field.getText().equals("") || verifyNumeric(field.getText()))){
                            field.setBorder(BorderFactory.createLineBorder(Color.red));
                            errorMessage.setVisible(true);
                        } else {
                            field.setBorder(BorderFactory.createLineBorder(Color.white));
                            errorMessage.setVisible(false);
                        }
                    }
                });
            }
            else {
                field.getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        react ();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        react();
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        react ();
                    }

                    public void react (){
                        field.setBorder(BorderFactory.createLineBorder(Color.white));
                        errorMessage.setVisible(false);
                    }
                });
            }
            formZone.add(field);
            inputs.add(field);
        }
        else {
            UtilDateModel model = new UtilDateModel();
            model.setValue(new Date());
            JDatePanelImpl datePanel = new JDatePanelImpl(model, new Properties());
            JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new JFormattedTextField.AbstractFormatter() {

                private String datePattern = "yyyy-MM-dd";
                private SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);

                @Override
                public Object stringToValue(String text) throws ParseException {
                    return dateFormat.parseObject(text);
                }

                @Override
                public String valueToString(Object value) throws ParseException {
                    if (value != null) {
                        Calendar cal = (Calendar) value;
                        return dateFormat.format(cal.getTime());
                    }

                    return "";
                }
            });
            datePicker.setBounds(80,177,400,40);
            datePicker.setButtonFocusable(false);
            inputs.add(datePicker);
            formZone.add(datePicker);
        }
    }

    private void buildSaveButton(){
        saveButton = new JButton("Save");
        saveButton.setBackground(new Color(0x15823b));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusable(false);
        saveButton.setBorder(BorderFactory.createLineBorder(new Color(0x15823b), 2, true));
        saveButton.setFont(new Font("Comic Sans", Font.PLAIN, 22));
        saveButton.setBounds(180, 630,190,40);
    }

    public void handleSaveEvent (ActionEvent e) {
        ArrayList<String> data = new ArrayList<String>(6);
        for (int i =0; i<6; i++){
            if ( i ==0) {
                int year = ((JDatePicker)inputs.toArray()[0]).getModel().getYear();
                int month = ((JDatePicker)inputs.toArray()[0]).getModel().getMonth() +1;
                int day = ((JDatePicker)inputs.toArray()[0]).getModel().getDay();
                data.add(year +"-" + month + "-"+ day);
            } else {
                String s = ((JTextField) inputs.toArray()[i]).getText();
                if (s.equals("") || errorMessage.isVisible()) {
                    ((JTextField) inputs.toArray()[i]).setBorder(BorderFactory.createLineBorder(Color.red));
                    errorMessage.setVisible(true);
                    return;
                } else {
                    data.add(s);
                    errorMessage.setVisible(false);
                    ((JTextField) inputs.toArray()[i]).setBorder(BorderFactory.createLineBorder(Color.white));
                }
            }
        }
        Sale s = new Sale ((String)data.toArray()[0], (String)data.toArray()[1], (String)data.toArray()[2],
                Integer.parseInt((String)data.toArray()[3]),
                Double.parseDouble((String)data.toArray()[4]),
                Double.parseDouble((String)data.toArray()[5]));
        dba.add(s);
        for (int i =1; i<6; i++){
            ((JTextField)inputs.toArray()[i]).setText("");
        }
        successMessage.setVisible(true);
    }

    public void buildForm(){
        inputs = new ArrayList<>(6);
        formZone = new JPanel();
        formZone.setBackground(new Color(0x0b0e52));
        formZone.setLayout(null);
        {
            errorMessage = new JLabel("Error: data inserted not well formated");
            errorMessage.setBackground(new Color(0xe37184));
            errorMessage.setOpaque(true);
            errorMessage.setForeground(new Color(0xb00000));
            errorMessage.setFont(new Font("Helvetica", Font.PLAIN, 14));
            errorMessage.setHorizontalAlignment(SwingConstants.CENTER);
            errorMessage.setVerticalAlignment(SwingConstants.CENTER);
            errorMessage.setVisible(false);
            errorMessage.setBounds(145, 80, 270, 50);
        } //error Message Building
        {
            successMessage = new JLabel("Success: Sale saved into local database");
            successMessage.setBackground(new Color(0xbeff9e));
            successMessage.setOpaque(true);
            successMessage.setForeground(new Color(0x136900));
            successMessage.setFont(new Font("Helvetica", Font.PLAIN, 14));
            successMessage.setHorizontalAlignment(SwingConstants.CENTER);
            successMessage.setVerticalAlignment(SwingConstants.CENTER);
            successMessage.setVisible(false);
            successMessage.setBounds(145, 80, 270, 50);
        } // success Message Building
        for (int i=1 ; i<7; i++){
            buildInputField(i);
        }
        JLabel lab = new JLabel("first field");
        lab.setFont(new Font("Helvetica", Font.PLAIN, 14));
        saveButton = new JButton("Save");
        buildSaveButton();
        saveButton.addActionListener(e -> {
            handleSaveEvent(e);
        });
        formZone.add(saveButton);
        formZone.add(errorMessage);
        formZone.add(successMessage);
    }

    public void dataBaseButtonCreator(){
        checkDatabase = new JButton("Local Database");
        checkDatabase.setBackground(new Color(0xFFFFFF));
        checkDatabase.setForeground(Color.BLACK);
        checkDatabase.setFocusable(false);
        checkDatabase.setFont(new Font("Book Antiqua", Font.PLAIN, 24));
        checkDatabase.setBounds(230, 300,285,60);
        checkDatabase.addActionListener( e -> { handleDataBaseCheckerEvent(e); });
        actionsZone.add(checkDatabase);
    }

    public void messageSenderButtonCreator(){
        sendMessage = new JButton("Send To Head Office");
        sendMessage.setBackground(new Color(0x8558a6));
        sendMessage.setForeground(Color.white);
        sendMessage.setFocusable(false);
        sendMessage.setFont(new Font("Book Antiqua", Font.PLAIN, 20));
        sendMessage.setBounds(230, 400,285,60);
        sendMessage.addActionListener(e -> { handleMessageSendingEvent(e); });
        actionsZone.add(sendMessage);
    }

    public void handleDataBaseCheckerEvent(ActionEvent e){
        this.dispose();
        new DataBaseContent(dba);
    }

    public void handleMessageSendingEvent(ActionEvent e){
        ArrayList <Sale> toBeSent = dba.getAllUnsent();
        for (Sale s : toBeSent){
            dba.updateSale(s.getId());
            messageService.send(s.toJSON());
        }
    }

    public void buildActionZone () {
        actionsZone = new JPanel();
        actionsZone.setBackground(new Color(0x0b0e52));
        actionsZone.setLayout(null);
        dataBaseButtonCreator();
        messageSenderButtonCreator();
    }

    public BranchOffice (){
        dba = new DataBaseAccess("branch_office_db", "root", "");
        messageService = new BranchMessaging();

        this.setTitle("Branch Office");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(1,2,10,0));
        this.getContentPane().setBackground(Color.CYAN);
        buildForm();
        buildActionZone();

        this.add(formZone);
        this.add(actionsZone);
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setVisible(true);
    }

    public static void main (String [] args){
        new BranchOffice();
    }
}
