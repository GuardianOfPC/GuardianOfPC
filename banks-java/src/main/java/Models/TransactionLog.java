package Models;

import Interfaces.IAccount;

public class TransactionLog
{
    public TransactionLog(IAccount accountFrom, IAccount accountTo, Bank bankFrom, Bank bankTo, double amount)
    {
        AccountFrom = accountFrom;
        AccountTo = accountTo;
        BankFrom = bankFrom;
        BankTo = bankTo;
        Amount = amount;
    }

    public IAccount getAccountFrom()
    {
        return AccountFrom;
    }

    public IAccount getAccountTo()
    {
        return AccountTo;
    }

    public Bank getBankFrom()
    {
        return BankFrom;
    }

    public Bank getBankTo()
    {
        return BankTo;
    }

    public double getAmount()
    {
        return Amount;
    }

    public void setAccountFrom(IAccount accountFrom)
    {
        AccountFrom = accountFrom;
    }

    public IAccount AccountFrom;
    public IAccount AccountTo;
    public Bank BankFrom;
    public Bank BankTo;
    public double Amount;
}
