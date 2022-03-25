package Interfaces;

import Models.Bank;
import Models.TransactionLog;

public interface IAccount
{
    Models.Client Client = null;
    Models.Client getClient();
    Models.Bank Bank = null;
    Bank getBank();
    double Money = 0;
    double getMoney();
    void setMoney(double value);
    void WithdrawMoney(double value) throws Exception;
    void RefillMoney(double value);
    TransactionLog TransferMoney(IAccount account, Bank bank, double value) throws Exception;
    void AddInterest();
    void ChargeCommission();
}
