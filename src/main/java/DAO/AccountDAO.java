package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;

public class AccountDAO {


 public Account loginToAccount(Account account){
    Connection connection = ConnectionUtil.getConnection();

    try {
        //SQL Logic
        String sql = "INSERT INTO Account (username, password) VALUES (?, ?)" ;
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        //Prepared Statement Methods
        preparedStatement.setString(1, account.getUsername());
        preparedStatement.setString(2, account.getPassword());
        
        preparedStatement.executeUpdate();

        ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
        if(pkeyResultSet.next()){
            int generated_account_id = (int) pkeyResultSet.getLong(1);
            return new Account(generated_account_id, account.getUsername(), account.getPassword());
        }
    }catch(SQLException e){
        System.out.println(e.getMessage());
    }
    return null;
}



    public Account insertNewAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();

        try {
            //SQL Logic
            String sql = "INSERT INTO account (username, password) VALUES (?, ?)" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //Prepared Statement Methods
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            
            preparedStatement.executeUpdate();

            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generatedAccountId = pkeyResultSet.getInt(1);
                return new Account(generatedAccountId, account.getUsername(), account.getPassword());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account getAccountByUsername(String account_username){
            Connection connection = ConnectionUtil.getConnection();

            try {
                //SQL logic
                String sql = "SELECT * FROM account WHERE username = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
    
                //Prepared Statement Method
                preparedStatement.setString(1, account_username);
    
                ResultSet rs = preparedStatement.executeQuery();
                while(rs.next()){
                    return new Account(rs.getInt("account_id"),
                            rs.getString("username"),
                            rs.getString("password"));
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
            return null;
        }

    public Account getAccountById(int account_id){
        Connection connection = ConnectionUtil.getConnection();

        try {
            //SQL logic
            String sql = "SELECT * FROM account WHERE account_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //Prepared Statement Method
            preparedStatement.setInt(1, account_id);

            ResultSet rs = preparedStatement.executeQuery();
           while(rs.next()){
                return new Account(rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}


