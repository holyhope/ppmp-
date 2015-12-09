import java.math.BigDecimal;
import java.math.MathContext;
import java.rmi.RemoteException;

import org.tempuri.ConverterSoapProxy;

public class Converter {
	public double convert(double value, String currencyFrom,String currencyTo) throws RemoteException
	{
		return new ConverterSoapProxy().getConversionAmount(currencyFrom, currencyTo, new ConverterSoapProxy().getLastUpdateDate(), new BigDecimal(value,MathContext.DECIMAL64)).doubleValue();
	}
}
