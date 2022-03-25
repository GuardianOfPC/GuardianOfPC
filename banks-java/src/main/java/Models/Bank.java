package Models;

import Interfaces.IAccount;
import Interfaces.IAccountFactory;
import Models.Accounts.CreditAccount;
import Models.Accounts.DebitAccount;
import Models.Accounts.DepositAccount;
import Models.Enums.BankPolicyChangeTypes;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class Bank
{
    public Bank(String name)
    {
        BankName = name;
        Clients = new ArrayList<>();
        Accounts = new ArrayList<>();
        SubscribedClients = new ArrayList<>();
    }

    public Models.CentralBank getCentralBank()
    {
        return CentralBank;
    }

    public ArrayList<Models.Client> getClients()
    {
        return Clients;
    }

    public ArrayList<IAccount> getAccounts()
    {
        return Accounts;
    }
    public void setAccounts(ArrayList<IAccount> list) { Accounts = list; }

    public Map<Double, Integer> getDepositInterests()
    {
        return DepositInterests;
    }

    public String getBankName()
    {
        return BankName;
    }

    public double getDebitInterestRate()
    {
        return DebitInterestRate;
    }

    public double getCommissionRate()
    {
        return CommissionRate;
    }

    public double getTransferLimit()
    {
        return TransferLimit;
    }

    CentralBank CentralBank;
    private ArrayList<Client> Clients;
    private ArrayList<Client> SubscribedClients;
    private ArrayList<IAccount> Accounts;
    private Map<Double, Integer> DepositInterests;
    private String BankName;
    private double DebitInterestRate;
    private double CommissionRate;
    private double TransferLimit;

    public void AddClientToSubscribedClients(Client client) { SubscribedClients.add(client); }

    public IAccount GetAccount(IAccount account){
        return Accounts.stream().filter(neededAccount -> neededAccount.equals(account)).findFirst().orElse(null);
    }

    public void RegisterClient(Client client){
        Clients.add(client);
    }

    public IAccount OpenAccount(Client client, IAccountFactory factory, int expirationDate, double amount) {
        return factory.OpenAccount(client, this, expirationDate, amount);
    }

    public void AddInterestToAccounts()
    {
        for (IAccount account : Accounts)
        {
            account.AddInterest();
        }
    }

    public void ChargeCommissionsFromAccounts()
    {
        for (IAccount account : Accounts)
        {
            account.ChargeCommission();
        }
    }

    public String ChangeCommissionRate(double value)
    {
        CommissionRate = value;
        for (Client client : SubscribedClients){
            for (IAccount account : Accounts){
                if (account.getClient().equals(client) & account instanceof CreditAccount){
                    return Client.BankPolicyChangeNotification(value, BankPolicyChangeTypes.CommissionRate);
                }
            }
        }
        return null;
    }

    public String ChangeDebitInterestRate(double value)
    {
        DebitInterestRate = value;
        for (Client client : SubscribedClients){
            for (IAccount account : Accounts){
                if (account.getClient().equals(client) & account instanceof DebitAccount){
                    return Client.BankPolicyChangeNotification(value, BankPolicyChangeTypes.InterestRate);
                }
            }
        }
        return null;
    }

    public String ChangeDepositInterestsRate(Map<Double, Integer> set)
    {
        DepositInterests = set;
        for (Map.Entry<Double, Integer> entry : set.entrySet()) {
            for (Client client : SubscribedClients) {
                for (IAccount account : Accounts) {
                    if (account.getClient().equals(client) & account instanceof DepositAccount) {
                        return Client.BankPolicyChangeNotification(entry.getKey(), BankPolicyChangeTypes.DepositInterestRateChanged);
                    }
                }
            }
        }
        return null;
    }

    public String ChangeTransferLimit(double value)
    {
        TransferLimit = value;
        for (Client client : SubscribedClients){
            for (IAccount account : Accounts){
                if (account.getClient().getPassportNumber() == 0 || Objects.equals(client.getHomeAddress(), "")){
                    return Client.BankPolicyChangeNotification(value, BankPolicyChangeTypes.TransferLimit);
                }
            }
        }
        return null;
    }
}
