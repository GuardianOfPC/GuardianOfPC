package Models.Accounts;

import Interfaces.IAccount;
import Models.Bank;
import Models.Client;
import Models.TransactionLog;

import java.util.Objects;

public class CreditAccount implements IAccount
{
    public CreditAccount(Models.Client client, Models.Bank bank, double limit)
    {
        Client = client;
        Bank = bank;
        CreditLimit = limit;
    }

    public Models.Client getClient()
    {
        return Client;
    }

    public Models.Bank getBank()
    {
        return Bank;
    }

    public double getMoney()
    {
        return Money;
    }

    public void setMoney(double money)
    {
        Money = money;
    }

    public double getCreditLimit()
    {
        return CreditLimit;
    }

    public Client Client;
    public Bank Bank;
    public double Money;
    public double CreditLimit;

    public void WithdrawMoney(double value) throws Exception
    {
        if (Client.getPassportNumber() == 0 || Objects.equals(Client.getHomeAddress(), ""))
        {
            if (value > Bank.getTransferLimit()) throw new Exception("Transfer limit exceeded");
        }

        if (Money <= 0 && Math.abs(Money - value) > CreditLimit) throw new Exception("Credit limit exceeded");

        Money -= value;
        TransactionLog log = new TransactionLog(this, null, Bank, null, value);
        Bank.getCentralBank().AddLog(log);
    }

    public void RefillMoney(double value)
    {
        Money += value;
        TransactionLog log = new TransactionLog(this, null, Bank, null, value);
        Bank.getCentralBank().AddLog(log);
    }

    public TransactionLog TransferMoney(IAccount account, Bank bank, double value) throws Exception
    {
        if (Client.getPassportNumber() == 0 || Objects.equals(Client.getHomeAddress(), ""))
        {
            if (value > Bank.getTransferLimit()) throw new Exception("Transfer limit exceeded");
        }

        if (Money <= 0 && Math.abs(Money - value) > CreditLimit) throw new Exception("Credit limit exceeded");
        Money -= value;
        Bank.getCentralBank().TransferMoneyAcrossBanks(account, bank, value);
        TransactionLog log = new TransactionLog(this, account, Bank, bank, value);
        Bank.getCentralBank().AddLog(log);
        return log;
    }

    public void AddInterest()
    {
        // Empty by design
    }

    public void ChargeCommission()
    {
        if (!(Money < 0)) return;
        double commissionFinal = (Bank.getCommissionRate() * 0.01) * Math.abs(Money);
        Money -= commissionFinal;
    }
}
