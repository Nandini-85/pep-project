package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService (AccountDAO accountDAO){
        this.accountDAO=accountDAO;
    }

    public AccountService(){
        this.accountDAO = new AccountDAO();
    }
    

    public Account registerAccount(Account account){
        if(account.getUsername() == null || account.getUsername().trim().isEmpty()){
            return null;
        }
        if(account.getPassword() == null || account.getPassword().length()<4){
            return null;
        }

        Account existingAccount = accountDAO.getAccountByUsername(account.getUsername());
        if(existingAccount != null){
            return null;
        }

        return accountDAO.insertAccount(account);
    }

    public Account login(String username,String password){
        Account account = accountDAO.getAccountByUsername(username);

        if(account != null && account.getPassword().equals(password)){
            return account;
        }
        return null;
    }

    public boolean accountExists(int accountId){
        return accountDAO.getAccountById(accountId)!= null;
    }
}
