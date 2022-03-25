package Tools;

import Interfaces.IAccount;
import Interfaces.IBankRepository;
import Models.Bank;

import java.util.ArrayList;

public class BankRepository implements IBankRepository
{
    private ArrayList<Bank> _banks;

    public BankRepository(){_banks = new ArrayList<>();}

    public void AddBank(Bank bank){
        _banks.add(bank);
    }

    public Bank GetBank(Bank bank){
        return _banks.stream().filter(bank::equals).findAny().orElse(null);
    }

    public Bank GetBankByName(String name){
        return _banks.stream().filter(neededBank -> name.equals(neededBank.getBankName())).findAny().orElse(null);
    }

    public ArrayList<Bank> GetBanks(){
        return _banks;
    }

    public void UpdateBankAccounts(Bank bank, ArrayList<IAccount> accounts){
        Bank neededBank = GetBank(bank);
        _banks.remove(neededBank);
        neededBank.setAccounts(accounts);
        _banks.add(neededBank);
    }
}
