package Models;

import Interfaces.IAccount;
import Interfaces.IBankRepository;

import java.util.ArrayList;

public class CentralBank
{
    public CentralBank(IBankRepository bankRepository)
    {
        BankRepository = bankRepository;
        TransactionLogs = new ArrayList<>();
    }

    private IBankRepository BankRepository;
    public IBankRepository getBankRepository(){
        return BankRepository;
    }
    private int DaysFromCentralBankCreation;
    public int getDaysFromCentralBankCreation(){
        return DaysFromCentralBankCreation;
    }
    private ArrayList<TransactionLog> TransactionLogs;
    public void AddLog(TransactionLog log){
        TransactionLogs.add(log);
    }
    public void RemoveLog(TransactionLog log){
        TransactionLogs.remove(log);
    }

    public void ForwardTime(int value)
    {
        DaysFromCentralBankCreation += value;
        if ((DaysFromCentralBankCreation % 30) == 0)
        {
            for (Bank bank : BankRepository.GetBanks()){
                bank.AddInterestToAccounts();
                bank.ChargeCommissionsFromAccounts();
            }
        }
    }

    public void CancelTransaction(TransactionLog log) throws Exception
    {
        Bank bankFrom = BankRepository.GetBank(log.BankFrom);
        Bank bankTo = BankRepository.GetBank(log.BankTo);
        TransactionLog neededLog = TransactionLogs.stream().filter(log::equals).findFirst().orElse(null);
        IAccount accountFrom = bankFrom.GetAccount(log.AccountFrom);
        IAccount accountTo = bankTo.GetAccount(log.AccountTo);
        accountFrom.RefillMoney(log.Amount);
        accountTo.WithdrawMoney(log.Amount);
        RemoveLog(neededLog);
    }

    public void TransferMoneyAcrossBanks(IAccount accountTo, Bank bankTo, double value)
    {
        ArrayList<IAccount> accounts = bankTo.getAccounts();
        IAccount neededAccount = accounts.stream().filter(accountTo::equals).findAny().orElse(null);
        accounts.remove(neededAccount);
        neededAccount.setMoney(neededAccount.getMoney() + value);
        accounts.add(neededAccount);
        BankRepository.UpdateBankAccounts(bankTo, accounts);
    }

    public Bank RegisterBank(Bank bank)
    {
        BankRepository.AddBank(bank);
        bank.CentralBank = this;
        return bank;
    }
}
