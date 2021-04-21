package main.java.headOffice;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class HeadMessaging {
    protected Connection connection;

    public HeadMessaging (){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try{
            connection = factory.newConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void receive () {
        try{
            Channel channel = connection.createChannel();
            channel.queueDeclare("HeadDataBase", false, false, false, null);
            DeliverCallback callback = (tag , delivery) -> {

            };
            channel.basicConsume("HeadDataBase", true, callback, tag -> {});
        }catch ( Exception e ){
            e.printStackTrace();
        }
    }
}
