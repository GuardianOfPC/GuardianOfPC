package Tools;

import Interfaces.IAccount;
import Interfaces.IAccountFactory;
import Models.Accounts.CreditAccount;
import Models.Bank;
import Models.Client;

import java.util.ArrayList;

public class CreditAccountFactory implements IAccountFactory
{
    public IAccount OpenAccount(Client client, Bank bank, int expireDate, double depositOrCreditAmount){
        CreditAccount account = new CreditAccount(client, bank, depositOrCreditAmount);
        ArrayList<IAccount> accounts = bank.getAccounts();
        accounts.add(account);
        bank.getCentralBank().getBankRepository().UpdateBankAccounts(bank, accounts);
        return account;
    }
}
