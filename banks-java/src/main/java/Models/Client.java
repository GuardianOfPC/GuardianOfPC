package Models;

import Models.Enums.BankPolicyChangeTypes;

public class Client
{
    private Client(String firstName, String lastName, String homeAddress, int passportNumber)
    {
        FirstName = firstName;
        LastName = lastName;
        HomeAddress = homeAddress;
        PassportNumber = passportNumber;
    }

    public String getFirstName() {return FirstName;}
    public String getLastName() {return LastName;}
    public String getHomeAddress() {return HomeAddress;}
    public int getPassportNumber() {return PassportNumber;}

    private String FirstName;
    private String LastName;
    private String HomeAddress;
    private int PassportNumber;

    public void SubscribeToBankPolicyChanges(Bank bank)
    {
        bank.AddClientToSubscribedClients(this);
    }

    public static String BankPolicyChangeNotification(double value, BankPolicyChangeTypes type)
    {
        return "${type} changed by {value}";
    }

    public ClientBuilder ToBuild()
    {
        ClientBuilder builder = new ClientBuilder();
        builder.WithFirstName(FirstName)
                .WithLastName(LastName)
                .WithHomeAddress(HomeAddress)
                .WithPassportNumber(PassportNumber);
        return builder;
    }

    public static class ClientBuilder
    {
        private String _firstName;
        private String _lastName;
        private String _homeAddress;
        private int _passportNumber;

        public ClientBuilder WithFirstName(String firstName)
        {
            _firstName = firstName;
            return this;
        }

        public ClientBuilder WithLastName(String lastName)
        {
            _lastName = lastName;
            return this;
        }

        public ClientBuilder WithHomeAddress(String homeAddress)
        {
            _homeAddress = homeAddress;
            return this;
        }

        public ClientBuilder WithPassportNumber(int num)
        {
            _passportNumber = num;
            return this;
        }

        public Client Build()
        {
            return new Client(_firstName, _lastName, _homeAddress, _passportNumber);
        }
    }
}
