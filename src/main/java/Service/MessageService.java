package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.List;

public class MessageService {
    //create messageDao object
    MessageDAO messageDAO;

    //No arg constructor
    public MessageService(){
        messageDAO = new MessageDAO();
    }

    //for testing
    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }








    // Create New Messages
    public Message addMessage(Message message){
        if(!(message.getMessage_text().isBlank()) && !(message.getMessage_text().length() >= 255)){
            return messageDAO.insertMessage(message);
        }
        return null;
    }



    //Get All Messages
    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }



    //Get a message by message_id
    public Message getMessageById(int message_id){
    return messageDAO.getMessageById(message_id);
    }



    //Delete a message by mesage_id
    public void deleteMessageById(int id){
        messageDAO.deleteById(id);
    }



    //Update message_text by message_id
    public Message updateMessage(int message_id, Message message){
        if (message.getMessage_text().isBlank() || message.getMessage_text().length() >= 255) {
            return null;
        }
        if(messageDAO.getMessageById(message_id) != null){
            messageDAO.updateMessage(message_id, message);
            return messageDAO.getMessageById(message_id);
        }
        return null;
    }



    //Get messages by Account/posted_by
    public List<Message> getMessagesByAccount(int posted_by){
        return messageDAO.getMessagesByAccount(posted_by);

    }

}

