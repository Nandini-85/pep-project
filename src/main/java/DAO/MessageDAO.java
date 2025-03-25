package DAO;
import Model.Message;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {

    public Message insertMessage(Message message){
        try(Connection conn= ConnectionUtil.getConnection()){
            String sql="insert into message (posted_by, message_text, time_posted_epoch)values(?,?,?)";

            PreparedStatement ps =conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1,message.getPosted_by());
            ps.setString(2,message.getMessage_text());
            ps.setLong(3,message.getTime_posted_epoch());


            int rowsAffected = ps.executeUpdate();

            if(rowsAffected == 1){
                ResultSet rs = ps.getGeneratedKeys();
                if(rs.next()){
                    int generatedId =rs.getInt(1);
                    message.setMessage_id(generatedId);
                    return message;

                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Message> getAllMessages(){
        List<Message> messages = new ArrayList<>();
        try(Connection conn= ConnectionUtil.getConnection()){
            String sql ="Select * from message";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();

            while(rs.next()){
                messages.add(new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                ));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return messages;
    }
   

     public Message getMessageById(int messageId){
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql ="Select * from message where message_id =?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,messageId);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
           return null;
     }

     public Message deleteMessage(int messageId){
        Message message = getMessageById(messageId);

        if(message != null){
            try(Connection conn= ConnectionUtil.getConnection()){
                String sql ="delete from message where message_id=?";

                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1,messageId);

                int rowsAffected=ps.executeUpdate();

                if(rowsAffected == 1){
                    return message;
                }
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
        return null;
     }
    

     public Message updateMessage(int messageId,String messageText){
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "update message set message_text = ? where message_id=?";

            PreparedStatement ps= conn.prepareStatement(sql);
            ps.setString(1,messageText);
            ps.setInt(2,messageId);

            int rowsAffected = ps.executeUpdate();

            if(rowsAffected ==1){
             return getMessageById(messageId);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return null;
     }


     public  List<Message> getMessagesByAccount(int accountId){
        List<Message> messages = new ArrayList<>();

        try(Connection conn = ConnectionUtil.getConnection()){
            String sql ="select * from message where posted_by=?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,accountId);
            ResultSet rs =ps.executeQuery();

            while(rs.next()){
                messages.add(new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                ));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return messages;
     }
    
}
