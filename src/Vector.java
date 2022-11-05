import java.util.List;
import java.util.concurrent.ThreadFactory;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

class ThreadFactoryAccount {
    public static void main(String[] args) {

        ThreadFactoryAccount threadFactoryAccount = new ThreadFactoryAccount();

        ConcurrentLinkedQueue<List<Account>> accountLinkedQueue = threadFactoryAccount.createConcurrentLinkedQueue();

        ArrayList<Runnable> array = new ArrayList<>(5);

        threadFactoryAccount.fillRunnableArrayList(accountLinkedQueue, array);

        threadFactoryAccount.createThread(array);
    }

    public void fillRunnableArrayList(ConcurrentLinkedQueue<List<Account>> linkedQueue, ArrayList<Runnable> array){
        for (Account accountList : linkedQueue.element()) {
            Runnable command = ()
                    -> System.out.println(accountList.toString());
            array.add(command);
        }
    }

    public void createThread(ArrayList<Runnable> array){
        CustomThreadFactory threadFactory
                = new CustomThreadFactory();
        for (Runnable command : array) {
            threadFactory.newThread(command).start();
        }
        System.out.println(
                "Total number of threads created using CustomThreadFactory = "
                        + threadFactory.getCount());
    }

    public ConcurrentLinkedQueue<List<Account>> createConcurrentLinkedQueue() {

        ConcurrentLinkedQueue<List<Account>> accountConcurrentLinkedQueue = new ConcurrentLinkedQueue<>();
        accountConcurrentLinkedQueue.add(fillAccountValues());
        return accountConcurrentLinkedQueue;
    }

    public List<Account> fillAccountValues(){
        Account accountOne = new Account();
        accountOne.setAccountNumber(1);
        accountOne.setCpf("111111111111");
        accountOne.setFullName("Wendel Siqueira Menegasso");

        Account accountTwo = new Account();
        accountTwo.setAccountNumber(2);
        accountTwo.setCpf("222222222222");
        accountTwo.setFullName("Wesley Siqueira Menegasso");

        List<Account> accountList = new ArrayList<>();
        accountList.add(accountOne);
        accountList.add(accountTwo);

        return accountList;

    }

}

// ThreadFactory class
class CustomThreadFactory implements ThreadFactory {

    private int count = 0;

    public int getCount() { return count; }

    @Override
    public Thread newThread(Runnable command)
    {
        count++;
        return new Thread(command);
    }
}
class Account{
    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    private int accountNumber;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    private String cpf;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    private String fullName;

    @Override
    public String toString(){
        return "Account number: " + getAccountNumber() +
                "\nCPF: " + getCpf() +
                "\nFull Name: " + getFullName();
    }

}

