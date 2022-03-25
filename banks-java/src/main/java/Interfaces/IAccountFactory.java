package Interfaces;

import Models.Bank;
import Models.Client;

public interface IAccountFactory
{
    IAccount OpenAccount(Client client, Bank bank, int expireDate, double depositOrCreditAmount);
}
