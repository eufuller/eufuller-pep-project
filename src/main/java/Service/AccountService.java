package Service;


import Model.Account;
import DAO.AccountDAO;

//import java.util.List;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }


    /**Use AccountDAO to persist a new account registration
     * CONDITIONS: 
     * username is not blank, 
     * Password is at least 4 characters long,
     * Account with that username does not already exist.
     */
    public Account addAccount(Account account){
        if(!(account.getUsername().isBlank()) && (account.getPassword().length() >= 4) && (accountDAO.getAccountByUsername(account.getUsername())) == null){
           Account newAccount = accountDAO.insertNewAccount(account);
            return newAccount;
        }else{
            return null;
        }
        
    }
    public Account verifyAccount(Account account){
        Account existingAccount = accountDAO.getAccountByUsername(account.getUsername());
        if(existingAccount == null){
            return null;
        }else if(account.getPassword().equals(existingAccount.getPassword())){
            return existingAccount;
        }else{
            return null;
        }
        
    }
}
