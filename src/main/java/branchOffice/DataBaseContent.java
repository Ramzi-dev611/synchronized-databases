package main.java.branchOffice;

import main.java.DataBaseAccess;
import main.java.Sale;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DataBaseContent extends JFrame {

    protected final String [] fieldsNames = {"Sale ID", "Date of Sale", "Region ",
            "Product",
            "Quantity", "Cost" , "Amount" , "Tax" , "Total"};

    protected int maxWidth = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    protected int maxHeight = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    protected ArrayList<Sale> sales ;
    protected DataBaseAccess dba;
    protected JPanel header;
    protected JButton goHome; protected JPanel homeButtonPanel;
    protected JButton refresh; protected JPanel refreshButtonPanel;
    protected JLabel headerText; protected JPanel titlePanel;
    protected JPanel mainZone; protected JScrollPane scrollable;

    private void refreshButtonBuild (){
        refresh = new JButton(new ImageIcon("assets/refreshIcon.png"));
        refresh.setBackground(new Color(0x3f48cc));
        refresh.setFocusable(false);
        refresh.setBounds(20,15,90,90);
        refresh.addActionListener(e -> {
            this.dispose();
            new DataBaseContent(dba);
        });
        refreshButtonPanel = new JPanel();
        refreshButtonPanel.setLayout(null);
        refreshButtonPanel.setPreferredSize(new Dimension(130, 100));
        refreshButtonPanel.add(refresh);
        refreshButtonPanel.setBackground(new Color(0xdaedeb));
    }

    private void titleBuild () {
        headerText = new JLabel("Your Local DataBase");
        headerText.setForeground(new Color(0x0a1152));
        headerText.setFont(new Font("ARIAL", Font.ITALIC, 40));
        headerText.setAlignmentX(CENTER_ALIGNMENT);
        headerText.setBounds(325,35, 500, 50);

        titlePanel = new JPanel();
        titlePanel.setLayout(null);
        titlePanel.setBackground(new Color(0xdaedeb));
        titlePanel.setPreferredSize(new Dimension(0,100));
        titlePanel.add(headerText);
    }

    private void getBackHome(){
        goHome = new JButton();
        goHome.setIcon(new ImageIcon("assets/homeIcon.jpg"));
        goHome.setBackground(new Color(0xb5e61d));
        goHome.setFocusable(false);
        goHome.setBounds(20 , 10 , 100, 95);
        goHome.addActionListener( e -> {
            this.dispose();
            new BranchOffice();
        });
        homeButtonPanel = new JPanel();
        homeButtonPanel.setLayout(null);
        homeButtonPanel.setBackground(new Color(0xdaedeb));
        homeButtonPanel.setPreferredSize(new Dimension(140, 100));
        homeButtonPanel.add(goHome);
    }

    private void buildHeader () {
        header = new JPanel();
        header.setLayout(new BorderLayout());
        header.setBackground(new Color(0xdaedeb));
        getBackHome();
        titleBuild();
        refreshButtonBuild();
        header.add(homeButtonPanel, BorderLayout.WEST);
        header.add(titlePanel, BorderLayout.CENTER);
        header.add(refreshButtonPanel, BorderLayout.EAST);
        header.setBounds(0,0,maxWidth,120);


    }

    private void buildTableHeader(){
        JPanel tableHeader = new JPanel();
        tableHeader.setLayout(null);
        tableHeader.setBounds(0,0,maxWidth-20, 100);
        tableHeader.setBackground(new Color(0xcaa9eb));
        for (int i =0; i< 9; i++){
            JLabel label = new JLabel(fieldsNames[i]);
            label.setFont(new Font("Arial", Font.PLAIN, 20));
            label.setBounds(20, 0, 129, 100);
            JPanel container = new JPanel();
            container.setLayout(null);
            container.setBackground(new Color(0xcaa9eb));
            container.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            container.setBounds(149*i, 0, 149, 100);
            container.add(label);
            tableHeader.add(container);
        }
        mainZone.add(tableHeader);
    }

    private void buildSaleView(int i){
        ArrayList <String> sale = ((Sale) sales.toArray()[i]).toArray();
        JPanel row = new JPanel();
        row.setLayout(null);
        row.setBounds(0,100*(i+1),maxWidth-20, 100);
        for (int j = 0; j<9; j++){
            JLabel l = new JLabel((String)sale.toArray()[j]);
            l.setFont(new Font("Arial", Font.PLAIN, (j==0) ?12 : 16));
            l.setBounds((j ==0)? 5 : 20, 0, 129, 100);
            JPanel cont = new JPanel();
            cont.setLayout(null);
            cont.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            cont.setBounds(149*j, 0, 149, 100);
            cont.add(l);
            row.add(cont);
        }
        mainZone.add(row);
    }

    private void buildTable(){
        for (int i =0; i<= sales.size(); i++){
            if (i == 0){
                buildTableHeader();
            } else  {
                buildSaleView(i-1);
            }
        }
    }

    private void buildMainZone() {
        mainZone = new JPanel();
        mainZone.setBackground(Color.white);
        mainZone.setLayout(null);
        mainZone.setPreferredSize(new Dimension(maxWidth-20, 100*(sales.size()+1)));
        buildTable();
        scrollable = new JScrollPane(mainZone);
        scrollable.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollable.setBounds (10, 135, maxWidth-20, maxHeight-170);
    }

    public DataBaseContent (DataBaseAccess d){
        dba = d;
        sales = dba.getAll();
        this.setTitle("Local DataBase");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(new Color(0x0b0e52));
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setLayout(null);
        buildHeader();
        buildMainZone();
        this.add(header);
        this.add(scrollable);
        this.setVisible(true);
    }

}
