package main.java.branchOffice;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class BranchMessaging {

    protected Connection connection;

    public BranchMessaging() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try{
            connection = factory.newConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void send (String message) {
        try {
            Channel channel = connection.createChannel();
            channel.queueDeclare("HeadDataBase", false,false,false,null);
            channel.basicPublish("", "HeadDataBase", null, message.getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
