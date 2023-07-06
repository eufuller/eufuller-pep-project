package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;



/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;
    
    public SocialMediaController(){
        accountService = new AccountService();
        messageService = new MessageService();

    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        /** account endpoints */
        app.post("/register", this::postRegistrationHandler);
        app.post("/login", this::postLoginHandler);

        // /**Message endpoints */
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIDHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getMessagesByAccountHandler);
        return app;
    }

 



// ACCOUNT HANDLERS
    private void postRegistrationHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);

        if(addedAccount!=null){
            ctx.json(mapper.writeValueAsString(addedAccount));
        }else{
            ctx.status(400);
        }
    }



    private void postLoginHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();

        Account account = mapper.readValue(ctx.body(), Account.class);
        Account verifyAccount = accountService.verifyAccount(account);

        if(verifyAccount!=null){
            ctx.json(mapper.writeValueAsString(verifyAccount));
        }else{
            ctx.status(401);
        }
    }
    // END OF ACCOUNT HANDLERS




    // MESSAGE HANDLERS

    //Create/Post New Messages
    private void postMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = null;

        if(accountService.getAccountById(message.getPosted_by()) != null){
           addedMessage = messageService.addMessage(message);
        }
        if(addedMessage==null){
            ctx.status(400);
        }else{
            ctx.json(addedMessage);
        }
    }



    //Getting All Messages
    public void getAllMessagesHandler(Context ctx){
        ctx.json(messageService.getAllMessages());
    }



    //Get Message By message_id
    private void getMessageByIDHandler(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id")); 

        if(messageService.getMessageById(messageId) == null){
            ctx.status(200);
        }else{
            ctx.json(messageService.getMessageById(messageId));
        }
    }



    //Delete Message by mesage_id
    private void deleteMessageHandler(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id")); 
        Message existMessage = messageService.getMessageById(messageId);

        if(existMessage != null){
            messageService.deleteMessageById(messageId);    
            ctx.json(existMessage);
            
        }else{
            ctx.json("");
        }   
        ctx.status(200);     
    }


    //Update message_text By message_id
    private void updateMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        Message message = mapper.readValue(ctx.body(), Message.class);
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message updatedMessage = messageService.updateMessage(message_id, message);
        
        if(updatedMessage == null){
            ctx.status(400);
        }else{
            ctx.json(mapper.writeValueAsString(updatedMessage));
        }
    }


   
    //Get Message By Posted_By/Account
     private void getMessagesByAccountHandler(Context ctx) {
        int accountId = Integer.parseInt(ctx.pathParam("account_id")); 
        
            ctx.json(messageService.getMessagesByAccount(accountId));
        }


    // END OF MESSAGE HANDLERS
}