package Tools;

import Interfaces.IAccount;
import Interfaces.IAccountFactory;
import Models.Accounts.DepositAccount;
import Models.Bank;
import Models.Client;

import java.util.ArrayList;

public class DepositAccountFactory implements IAccountFactory
{
    public IAccount OpenAccount(Client client, Bank bank, int expireDate, double depositOrCreditAmount){
        DepositAccount account = new DepositAccount(client, bank, expireDate, depositOrCreditAmount);
        ArrayList<IAccount> accounts = bank.getAccounts();
        accounts.add(account);
        bank.getCentralBank().getBankRepository().UpdateBankAccounts(bank, accounts);
        return account;
    }
}
