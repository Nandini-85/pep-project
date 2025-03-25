package Service;

import DAO.MessageDAO;
import Model.Message;
import java.util.List; 
public class MessageService {

    private MessageDAO messageDAO;
    private AccountService accountService;

    public MessageService(MessageDAO messageDAO,AccountService accountService) {
        this.messageDAO=messageDAO;
        this.accountService = accountService;

    }

    public MessageService(){
        this.messageDAO = new MessageDAO();
        this.accountService = new AccountService();
    }

    public Message createMessage(Message message){
        if(message.getMessage_text() == null || message.getMessage_text().trim().isEmpty()){
            return null;
        }

        if(message.getMessage_text().length() > 255){
            return null;
        }

        if(!accountService.accountExists(message.getPosted_by())){
            return null;
        }

        if(message.getTime_posted_epoch() == 0){
            message.setTime_posted_epoch(System.currentTimeMillis());
        }

        return messageDAO.insertMessage(message);

    }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int messageId){
        return messageDAO.getMessageById(messageId);
    }

    public Message deleteMessage(int messageId){
        return messageDAO.deleteMessage(messageId);
    }

    public Message updateMessage(int messageId,String messageText){
        if(messageText == null || messageText.trim().isEmpty()){
            return null;
        }

        if(messageText.length()>255){
            return null;
        }

        Message existingMessage = messageDAO.getMessageById(messageId);
        if(existingMessage == null){
            return null;
        }
        return messageDAO.updateMessage(messageId, messageText);
    }

    public List<Message> getMessagesByAccount(int accountId){
        return messageDAO.getMessagesByAccount(accountId);
    }
    
}
