package Models.Accounts;

import Interfaces.IAccount;
import Models.Bank;
import Models.Client;
import Models.TransactionLog;

import java.util.ArrayList;
import java.util.Objects;

public class DebitAccount implements IAccount
{
    public DebitAccount(Models.Client client, Bank bank)
    {
        Client = client;
        Bank = bank;
        InterestsAmounts = new ArrayList<>();
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

    public ArrayList<Double> getInterestsAmounts(){
        return InterestsAmounts;
    }

    public Client Client;
    public Bank Bank;
    public double Money;
    private ArrayList<Double> InterestsAmounts;

    public void WithdrawMoney(double value) throws Exception
    {
        if (Client.getPassportNumber() == 0 || Objects.equals(Client.getHomeAddress(), ""))
        {
            if (value > Bank.getTransferLimit()) throw new Exception("Transfer limit exceeded");
        }

        if (Money - value < 0) throw new Exception("Couldn't withdraw money - will be broke");
        Money -= value;
        TransactionLog log = new TransactionLog(this, null, Bank, null, value);
        Bank.getCentralBank().AddLog(log);
        InterestsAmounts.add((Bank.getDebitInterestRate() / 365 * 0.01) * Money);
    }

    public void RefillMoney(double value)
    {
        Money += value;
        TransactionLog log = new TransactionLog(this, null, Bank, null, value);
        Bank.getCentralBank().AddLog(log);
        InterestsAmounts.add(((Bank.getDebitInterestRate() / 365) * 0.01) * Money);
    }

    public TransactionLog TransferMoney(IAccount account, Bank bank, double value) throws Exception
    {
        if (Money - value < 0) throw new Exception("Couldn't withdraw money - will be broke");
        if (Client.getPassportNumber() == 0 || Objects.equals(Client.getHomeAddress(), ""))
        {
            if (value > Bank.getTransferLimit()) throw new Exception("Transfer limit exceeded");
        }

        Money -= value;
        Bank.getCentralBank().TransferMoneyAcrossBanks(account, bank, value);
        TransactionLog log = new TransactionLog(this, account, Bank, bank, value);
        Bank.getCentralBank().AddLog(log);
        InterestsAmounts.add((Bank.getDebitInterestRate() / 365 * 0.01) * Money);
        return log;
    }

    public void AddInterest()
    {
        int regularDays = 30 - InterestsAmounts.size();
        double notRegularInterest = InterestsAmounts.stream().mapToDouble(Double::doubleValue).sum();
        double finalInterest = ((Bank.getDebitInterestRate() / 365 * 0.01) * Money * regularDays) + notRegularInterest;
        Money += finalInterest;
    }

    public void ChargeCommission()
    {
        // Empty by design
    }
}
