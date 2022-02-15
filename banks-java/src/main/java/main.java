import Models.Bank;
import Models.CentralBank;
import Models.Client;
import Tools.BankRepository;
import Tools.CreditAccountFactory;
import Tools.DebitAccountFactory;
import Tools.DepositAccountFactory;

import java.util.Map;
import java.util.Scanner;

public class main
{
    private static void Main() throws Exception
    {
        CentralBank centralBank = new CentralBank(new BankRepository());
        Scanner sc = new Scanner(System.in);

        System.out.println("\nWelcome to BankApp\n");
        System.out.println("Please choose an option:");
        while (true)
        {
            System.out.println("1. Create bank");
            System.out.println("2. Create client and open an account");
            System.out.println("3. Exit");
            System.out.println("Your choice: ");
            String choice = sc.nextLine();
            switch (choice)
            {
                case "1":
                    clearScreen();
                    System.out.println("Enter bank name: ");
                    String name = sc.nextLine();

                    Bank bank = new Bank(name);
                    centralBank.RegisterBank(bank);

                    System.out.println("Enter debit interest rate: ");
                    double debitIntRate = Double.parseDouble(sc.nextLine());
                    bank.ChangeDebitInterestRate(debitIntRate);

                    System.out.println("Enter commission rate: ");
                    double commissionRate = Double.parseDouble(sc.nextLine());
                    bank.ChangeCommissionRate(commissionRate);

                    System.out.println("Enter transfer limit: ");
                    double transferLimit = Double.parseDouble(sc.nextLine());
                    bank.ChangeTransferLimit(transferLimit);

                    System.out.println("Initializing deposit interests configurator\n");
                    System.out.println("Enter ranges count: ");
                    int count = Integer.parseInt(sc.nextLine());
                    Map<Double, Integer> depositInterests = null;
                    for (int i = 0; i < count; i++)
                    {
                        System.out.println("Enter interest rate: ");
                        double rate = Double.parseDouble(sc.nextLine());
                        System.out.println("Enter limit for this rate: ");
                        int limit = Integer.parseInt(sc.nextLine());
                        depositInterests.put(rate, limit);
                    }

                    bank.ChangeDepositInterestsRate(depositInterests);
                    break;
                case "2":
                    clearScreen();
                    System.out.println("Enter first name: ");
                    String firstName = sc.nextLine();

                    System.out.println("Enter last name: ");
                    String lastName = sc.nextLine();

                    System.out.println("Enter client home address: ");
                    String homeAddress = sc.nextLine();

                    System.out.println("Enter client passport number");
                    int passportNum = Integer.parseInt(sc.nextLine());

                    Client client = new Client.ClientBuilder()
                            .WithFirstName(firstName)
                            .WithLastName(lastName)
                            .WithHomeAddress(homeAddress)
                            .WithPassportNumber(passportNum)
                            .Build();
                    System.out.println("Would you like to open an account?");
                    System.out.println("1. Yes");
                    System.out.println("2. No");
                    String choice2 = sc.nextLine();
                    switch (choice2)
                    {
                        case "1":
                            clearScreen();
                            System.out.println("Enter bank name: ");
                            Bank ourBank = centralBank.getBankRepository().GetBankByName(sc.nextLine());
                            System.out.println("Available account types:\n");
                            System.out.println("1. Debit");
                            System.out.println("2. Deposit");
                            System.out.println("3. Credit");
                            System.out.print("Enter account type: ");
                            String typeChoice = sc.nextLine();
                            switch (typeChoice)
                            {
                                case "1":
                                    ourBank.OpenAccount(client, new DebitAccountFactory(), 0, 0);
                                break;
                                case "2":
                                    System.out.println("Enter expiration date (days): ");
                                    int days = Integer.parseInt(sc.nextLine());
                                    System.out.println("Enter deposit amount: ");
                                    double amount = Double.parseDouble(sc.nextLine());
                                    ourBank.OpenAccount(client, new DepositAccountFactory(), days, amount);
                                    break;
                                case "3":
                                    System.out.println("Enter credit limit: ");
                                    double limit = Double.parseDouble(sc.nextLine());
                                    ourBank.OpenAccount(client, new CreditAccountFactory(),0, limit);
                                    break;
                                default:
                                    throw new Exception("Invalid argument");
                            }

                            break;
                        case "2":
                            clearScreen();
                            System.out.println("Okay");
                            System.exit(0);
                            break;
                    }

                    break;
                case "3":
                    clearScreen();
                    System.out.println("Exit");
                    System.exit(0);
                    break;
                default:
                    throw new Exception("Invalid argument");
            }
        }
    }
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
