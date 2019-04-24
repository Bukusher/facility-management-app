package sample;

public class Administrator extends Account {

    public Administrator(String name, String surname, String email, String password) {
        super(name, surname, email, password, AccountType.ADMINISTRATOR);
    }
}
