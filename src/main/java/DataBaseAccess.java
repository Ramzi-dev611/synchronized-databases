package main.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

public class DataBaseAccess {
    protected String url;
    protected String username;
    protected String password;
    protected Connection connection;

    public DataBaseAccess(String database, String user, String pass) {
        this.url= "jdbc:mysql://localhost:3306/"+database;
        this.username = user;
        this.password = pass;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+database,
                    username, password);
        }catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    public void add (Sale sale){
        String sql = "insert into sales (sale_id, dateSale, region, product, quantity, cost, amt," +
                " tax, total, sent)" +
                " values " +
                "(? , ?, ?, ?, ?, ?, ?, ?, ?, 0)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,sale.getId());
            ps.setString(2,sale.getDate());
            ps.setString(3, sale.getRegion());
            ps.setString(4, sale.getProduct());
            ps.setInt(5, sale.getQuantity());
            ps.setDouble(6, sale.getCost());
            ps.setDouble(7, sale.getAmt());
            ps.setDouble(8, sale.getTax());
            ps.setDouble(9, sale.getTotal());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ArrayList <Sale> getAll() {
        ArrayList<Sale> response = new ArrayList<>();
        String sql = "Select * from sales";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs  = ps.executeQuery();
            while ( rs.next()){
                Sale s = new Sale(
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getDouble(6),
                        rs.getDouble(8)
                );
                s.setId(rs.getString(1));
                response.add(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    public ArrayList <Sale> getAllUnsent (){
        ArrayList<Sale> response = new ArrayList<>();
        String sql = "Select * from sales where sent = 0";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs  = ps.executeQuery();
            while ( rs.next()){
                Sale s = new Sale(
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getDouble(6),
                        rs.getDouble(8)
                );
                s.setId(rs.getString(1));
                response.add(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    public void updateSale(String id){
        String sql = "update sales " +
                "set sent = 1 " +
                "where sale_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,id);
            ps.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addHead (Sale sale){
        String sql = "insert into sales (sale_id, dateSale, region, product, quantity, cost, amt," +
                " tax, total)" +
                " values " +
                "(? , ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,sale.getId());
            ps.setString(2,sale.getDate());
            ps.setString(3, sale.getRegion());
            ps.setString(4, sale.getProduct());
            ps.setInt(5, sale.getQuantity());
            ps.setDouble(6, sale.getCost());
            ps.setDouble(7, sale.getAmt());
            ps.setDouble(8, sale.getTax());
            ps.setDouble(9, sale.getTotal());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
