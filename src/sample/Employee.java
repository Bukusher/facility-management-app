package sample;

public class Employee extends Account {

    public Employee(String name, String surname, String email, String password) {

        super(name, surname, email, password, AccountType.EMPLOYEE);
    }


}
