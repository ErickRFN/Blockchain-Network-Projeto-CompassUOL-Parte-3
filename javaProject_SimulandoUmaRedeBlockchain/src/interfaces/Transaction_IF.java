package interfaces;

public interface Transaction_IF {

    String getAddressSender();
    String getAddressReceiver();
    double getAmount();
    public Double getFee();
    public Wallet_IF getWallerSender();
    public Wallet_IF getWallerReceiver();
    
}
