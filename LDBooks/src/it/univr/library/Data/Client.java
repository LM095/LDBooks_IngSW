package it.univr.library.Data;

public class Client extends User
{
    private String phoneNumber;

    public Client(String name, String surname, String email, String password, String phoneNumber)
    {
        super(name, surname, email, password);
        this.phoneNumber = phoneNumber;

        normalizeUser();
    }

    public Client(){}

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString()
    {
        return super.toString() + ", " + phoneNumber;
    }

    /*@Override
    public List<Address> getAddresses() {
        return null;
    }*/


    private void normalizeUser()
    {
        if(phoneNumber != null)
            phoneNumber = phoneNumber.trim();
    }
}
