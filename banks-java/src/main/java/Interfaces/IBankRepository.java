package Interfaces;

import Models.Bank;

import java.util.ArrayList;

public interface IBankRepository
{
    void AddBank(Bank bank);
    Bank GetBank(Bank bank);
    ArrayList<Bank> GetBanks();
    Bank GetBankByName(String name);
    void UpdateBankAccounts(Bank bank, ArrayList<IAccount> accounts);
}
