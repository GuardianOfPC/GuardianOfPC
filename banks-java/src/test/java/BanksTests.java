import Models.Bank;
import Models.CentralBank;
import Models.Client;
import Models.TransactionLog;
import Tools.BankRepository;
import Tools.CreditAccountFactory;
import Tools.DebitAccountFactory;
import Tools.DepositAccountFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class BanksTests
{
    private CentralBank _centralBank;

    @Test
    public void WithdrawFromDebitAccount_MoneyAddedAndExceptionAsserted()
    {
        _centralBank = new CentralBank(new BankRepository());
        Client client = new Client.ClientBuilder()
                .WithFirstName("Alexey")
                .WithLastName("Odinochenko")
                .WithHomeAddress("SPB")
                .WithPassportNumber(228)
                .Build();
        Bank bank = new Bank("Sber");
        _centralBank.RegisterBank(bank);
        bank.RegisterClient(client);
        var account = bank.OpenAccount(client, new DebitAccountFactory(), 0, 0);
        account.RefillMoney(1000);
        Assertions.assertEquals(1000,account.getMoney());
        Exception thrown = Assertions.assertThrows(Exception.class, () -> account.WithdrawMoney(9000));
        Assertions.assertEquals("Couldn't withdraw money - will be broke", thrown.getMessage());
    }

    @Test
    public void WithdrawFromSuspiciousDebitAccount_SuspiciousExceptionAsserted()
    {
        _centralBank = new CentralBank(new BankRepository());
        Client client = new Client.ClientBuilder()
                .WithFirstName("Alexey")
                .WithLastName("Odinochenko")
                .Build();
        Bank bank = new Bank("Sber");
        _centralBank.RegisterBank(bank);
        bank.RegisterClient(client);
        bank.ChangeTransferLimit(900);
        var account = bank.OpenAccount(client, new DebitAccountFactory(), 0, 0);
        account.RefillMoney(1000);
        Exception thrown = Assertions.assertThrows(Exception.class, () -> account.WithdrawMoney(901));
        Assertions.assertEquals("Transfer limit exceeded", thrown.getMessage());
    }

    @Test
    public void TransferBetweenAccountsAcrossBanksAndCancelTransaction_MoneySentAndReturnedAfterCancel() throws Exception
    {
        _centralBank = new CentralBank(new BankRepository());
        Client client1 = new Client.ClientBuilder()
                .WithFirstName("Alexey")
                .WithLastName("Odinochenko")
                .WithHomeAddress("SPB")
                .WithPassportNumber(228)
                .Build();
        Client client2 = new Client.ClientBuilder()
                .WithFirstName("John")
                .WithLastName("Doe")
                .WithHomeAddress("LA")
                .WithPassportNumber(81)
                .Build();
        Bank bank1 = new Bank("Sber");
        Bank bank2 = new Bank("American Bank");
        _centralBank.RegisterBank(bank1);
        _centralBank.RegisterBank(bank2);
        bank1.RegisterClient(client1);
        bank2.RegisterClient(client2);

        var account1 = bank1.OpenAccount(client1, new DebitAccountFactory(), 0, 0);
        account1.RefillMoney(1000);

        var account2 = bank2.OpenAccount(client2, new DebitAccountFactory(), 0, 0);
        account2.RefillMoney(1000);

        TransactionLog log = account1.TransferMoney(account2, bank2, 500);
        Assertions.assertEquals(500,account1.getMoney());
        Assertions.assertEquals(1500,account2.getMoney());
        _centralBank.CancelTransaction(log);
        Assertions.assertEquals(1000,account1.getMoney());
        Assertions.assertEquals(1000,account2.getMoney());
    }

    @Test
    public void DebitAccountInterestCalculation_CorrectAmountWithInterest()
    {
        _centralBank = new CentralBank(new BankRepository());
        Client client = new Client.ClientBuilder()
                .WithFirstName("Alexey")
                .WithLastName("Odinochenko")
                .WithHomeAddress("SPB")
                .WithPassportNumber(228)
                .Build();
        Bank bank = new Bank("Sber");
        _centralBank.RegisterBank(bank);
        bank.RegisterClient(client);
        var account = bank.OpenAccount(client, new DebitAccountFactory(), 0, 0);
        bank.ChangeDebitInterestRate(3.65);
        account.RefillMoney(100000);
        Assertions.assertEquals(100000,account.getMoney());
        _centralBank.ForwardTime(30);
        Assertions.assertEquals(100300, account.getMoney());
    }

    @Test
    public void DepositAccountInterestCalculation_CorrectAmountWithInterest()
    {
        _centralBank = new CentralBank(new BankRepository());
        Client client = new Client.ClientBuilder()
                .WithFirstName("Alexey")
                .WithLastName("Odinochenko")
                .WithHomeAddress("SPB")
                .WithPassportNumber(228)
                .Build();
        Bank bank = new Bank("Sber");
        Map<Double, Integer> depositInt = new HashMap<>()
        {{
            put(2.0, 50000);
            put(3.65, 100000);
            put(5.0, 200000);
        }};
        _centralBank.RegisterBank(bank);
        bank.RegisterClient(client);
        bank.ChangeDepositInterestsRate(depositInt);
        var account = bank.OpenAccount(client, new DepositAccountFactory(), 20, 100000);
        _centralBank.ForwardTime(30);
        Assertions.assertEquals(100300, account.getMoney());
    }

    @Test
    public void CreditAccountCommissionCalculation_CorrectAmountWithCommission() throws Exception
    {
        _centralBank = new CentralBank(new BankRepository());
        Client client = new Client.ClientBuilder()
                .WithFirstName("Alexey")
                .WithLastName("Odinochenko")
                .WithHomeAddress("SPB")
                .WithPassportNumber(228)
                .Build();
        Bank bank = new Bank("Sber");
        _centralBank.RegisterBank(bank);
        bank.RegisterClient(client);
        bank.ChangeCommissionRate(3);
        var account = bank.OpenAccount(client, new CreditAccountFactory(), 0, 200);
        account.RefillMoney(100);
        Assertions.assertEquals(100,account.getMoney());
        account.WithdrawMoney(200);
        Assertions.assertEquals(-100,account.getMoney());
        _centralBank.ForwardTime(30);
        Assertions.assertEquals(-103,account.getMoney());
    }

    @Test
    public void CreditAccountCreditLimitTest_ExceptionCaught()
    {
        _centralBank = new CentralBank(new BankRepository());
        Client client = new Client.ClientBuilder()
                .WithFirstName("Alexey")
                .WithLastName("Odinochenko")
                .WithHomeAddress("SPB")
                .WithPassportNumber(228)
                .Build();
        Bank bank = new Bank("Sber");
        _centralBank.RegisterBank(bank);
        bank.RegisterClient(client);
        var account = bank.OpenAccount(client, new CreditAccountFactory(), 0, 200);
        Exception thrown = Assertions.assertThrows(Exception.class, () -> account.WithdrawMoney(201));
        Assertions.assertEquals("Credit limit exceeded", thrown.getMessage());
    }
}
