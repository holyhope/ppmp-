package DefaultNamespace;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.rpc.ServiceException;

public class BankManager {
	
	private ConcurrentHashMap<Long,BankAccount> accounts;
	
	public BankManager()
	{
		accounts=new ConcurrentHashMap<>();
		init();
	} 
	
	private void init() 
	{
		ArrayList<String> lines = new ArrayList<>();
		lines.add("10000000 100 Dupont Pierre USD");
		lines.add("10000502 400 Durant Serge JPY");
		lines.add("16000503 200 Siette Karine EUR");
		
		for(String line: lines)
			{				
				String[] tab=line.split(" ");
				addAccount(Long.parseLong(tab[0]),Double.parseDouble(tab[1]),tab[2],tab[3],tab[4]);
			}	
	}
	
	private void addAccount(long id, double balance,String name, String firstname, String currency)
	{
		accounts.put(id,new BankAccount(id,balance,name,firstname,currency));
	}
	
	private void deleteAccount(long id)
	{
		accounts.remove(id);
	}
	
	public boolean depositMoney(long id, String name, String firstname, String currency, double amount) throws ServiceException, RemoteException
	{
		BankAccount current=accounts.get(id);
		if(current == null || !(current.isValid(id, name, firstname)))
		{
			return false;
		}
		
		if(currency != current.getCurrency())
		{
			Converter conv=new ConverterServiceLocator().getConverter();
			amount=conv.convert(amount, currency, current.getCurrency());
			currency=current.getCurrency();
		}
		
		current.addMoney(amount);
		
		return true;
	}
	
	
	public boolean removeMoney(long id, String name, String firstname, String currency, double amount) throws ServiceException, RemoteException
	{
		BankAccount current=accounts.get(id);
		if(current == null || !(current.isValid(id, name, firstname)))
		{
			return false;
		}
		
		if(currency!=current.getCurrency())
		{
			Converter conv=new ConverterServiceLocator().getConverter();
			amount=conv.convert(amount, currency, current.getCurrency());
			currency=current.getCurrency();
		}
		
		return current.removeMoney(amount);
	}
	
	public double printBalance(long id,String currency) throws RemoteException, ServiceException{
		BankAccount current=accounts.get(id);
		
		double balance = current.getBalance();
		
		if(currency!=current.getCurrency())
		{
		
			Converter conv=new ConverterServiceLocator().getConverter();
			balance = conv.convert(balance, currency, current.getCurrency());
		}
		return balance;	
	}
	
}
