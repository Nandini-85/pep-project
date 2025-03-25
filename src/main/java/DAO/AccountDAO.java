package DAO;

import Model.Account;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AccountDAO {

    public Account insertAccount(Account account){
        try(Connection conn = ConnectionUtil.getConnection()){
             String sql ="INSERT into account (username,password) values(?,?)";

             PreparedStatement ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
             ps.setString(1,account.getUsername());
             ps.setString(2,account.getPassword());


             int rowsAffected =ps.executeUpdate();

             if(rowsAffected == 1){
                ResultSet rs =ps.getGeneratedKeys();
                if(rs.next()){
                    int generatedId = rs.getInt(1);
                    account.setAccount_id(generatedId);
                    return account;
                }
             }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public Account getAccountByUsername(String username){
        try(Connection conn= ConnectionUtil.getConnection()){
            String sql ="select * from account where username=?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,username);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password")
                );
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public Account getAccountById(int accountId){
            try(Connection conn =ConnectionUtil.getConnection()){
                String sql ="select * from account where account_id=?";

                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1,accountId);

                ResultSet rs = ps.executeQuery();

                if(rs.next()){
                    return new Account(
                        rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password")
                    );
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
            return null;
    }

      
    public List<Account> getAllAccounts(){
        List<Account> accounts = new ArrayList<>();

        try(Connection conn = ConnectionUtil.getConnection()){
            String sql ="select * from account";

            PreparedStatement ps =conn.prepareStatement(sql);
            ResultSet rs =ps.executeQuery();

            while (rs.next()){
                accounts.add(new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password")
                ));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return accounts;
    }
}
    
