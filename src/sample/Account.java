package sample;

public abstract class Account {

    private String name;
    private String surname;
    private String email;
    private String password;
    private AccountType accountType;

    public Account(String name, String surname, String email, String password, AccountType accountType) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.accountType = accountType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccountType() {
        return accountType.name();
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}