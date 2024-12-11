package interfaces;

public interface Transaction_IF {

    String getAddressSender();
    String getAddressReceiver();
    double getAmount();

    @Override
    String toString();
	
}
