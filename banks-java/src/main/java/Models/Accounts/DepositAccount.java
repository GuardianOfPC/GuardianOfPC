package Models.Accounts;

import Interfaces.IAccount;
import Models.Bank;
import Models.Client;
import Models.TransactionLog;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class DepositAccount implements IAccount
{
    public DepositAccount(Client client, Bank bank, int expirationDate, double depositAmount)
    {
        Client = client;
        Bank = bank;
        ExpirationDate = expirationDate;
        Money = depositAmount;
        InterestsAmounts = new ArrayList<>();

        for (Map.Entry<Double, Integer> entry : bank.getDepositInterests().entrySet()){
            if (depositAmount > entry.getValue()) continue;
            DepositInterest = entry.getKey();
        }
    }

    @Override
    public Models.Client getClient()
    {
        return Client;
    }

    @Override
    public Models.Bank getBank()
    {
        return Bank;
    }

    @Override
    public double getMoney()
    {
        return Money;
    }

    public void setMoney(double value){
        Money = value;
    }

    public int getExpirationDate()
    {
        return ExpirationDate;
    }

    public double getDepositInterest()
    {
        return DepositInterest;
    }

    public Client Client;
    public Bank Bank;
    public double Money;
    public int ExpirationDate;
    public double DepositInterest;

    public ArrayList<Double> getInterestsAmounts(){
        return InterestsAmounts;
    }
    private ArrayList<Double> InterestsAmounts;
    public void WithdrawMoney(double value) throws Exception
    {
        if (Bank.getCentralBank().getDaysFromCentralBankCreation() < ExpirationDate)
            throw new Exception("Cannot withdraw until expiration date");
        if (Client.getPassportNumber() == 0 || Objects.equals(Client.getHomeAddress(), ""))
        {
            if (value > Bank.getTransferLimit()) throw new Exception("Transfer limit exceeded");
        }

        Money -= value;
        TransactionLog log = new TransactionLog(this, null, Bank, null, value);
        Bank.getCentralBank().AddLog(log);
        InterestsAmounts.add((DepositInterest / 365 * 0.01) * Money);
    }

    public void RefillMoney(double value)
    {
        Money += value;
        TransactionLog log = new TransactionLog(this, null, Bank, null, value);
        Bank.getCentralBank().AddLog(log);
        InterestsAmounts.add((DepositInterest / 365 * 0.01) * Money);
    }

    public TransactionLog TransferMoney(IAccount account, Bank bank, double value) throws Exception
    {
        if (Bank.getCentralBank().getDaysFromCentralBankCreation() < ExpirationDate)
            throw new Exception("Cannot withdraw until expiration date");
        if (Client.getPassportNumber() == 0 || Objects.equals(Client.getHomeAddress(), ""))
        {
            if (value > Bank.getTransferLimit()) throw new Exception("Transfer limit exceeded");
        }

        Money -= value;
        Bank.getCentralBank().TransferMoneyAcrossBanks(account, bank, value);
        TransactionLog log = new TransactionLog(this, account, Bank, bank, value);
        Bank.getCentralBank().AddLog(log);
        InterestsAmounts.add((DepositInterest / 365 * 0.01) * Money);
        return log;
    }

    public void AddInterest()
    {
        int regularDays = 30 - InterestsAmounts.size();
        double notRegularInterest = InterestsAmounts.stream().mapToDouble(Double::doubleValue).sum();
        double finalInterest = ((DepositInterest / 365 * 0.01) * Money * regularDays) + notRegularInterest;
        Money += finalInterest;
    }

    public void ChargeCommission()
    {
        // Empty by design
    }
}
