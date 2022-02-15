package Tools;

import Interfaces.IAccount;
import Interfaces.IAccountFactory;
import Models.Accounts.DebitAccount;
import Models.Bank;
import Models.Client;

import java.util.ArrayList;

public class DebitAccountFactory implements IAccountFactory
{
    public IAccount OpenAccount(Client client, Bank bank, int expireDate, double depositOrCreditAmount){
        DebitAccount account = new DebitAccount(client, bank);
        ArrayList<IAccount> accounts = bank.getAccounts();
        accounts.add(account);
        bank.getCentralBank().getBankRepository().UpdateBankAccounts(bank, accounts);
        return account;
    }
}
