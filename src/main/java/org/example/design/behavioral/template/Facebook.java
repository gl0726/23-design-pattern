package org.example.design.behavioral.template;

/**
 *  Facebook通讯子类，实现父类逻辑
 *
 * Author: GL
 * Date: 2021-11-22
 */
public class Facebook extends Network {

    public Facebook(String userName, String password) {
        super(userName, password);
    }

    public boolean logIn(String userName, String password) {
        System.out.println("\nChecking user's parameters");
        System.out.println("Name: " + this.getUserName());
        System.out.print("Password: ");
        for (int i = 0; i < this.getPassword().length(); i++) {
            System.out.print("*");
        }
        simulateNetworkLatency();
        System.out.println("\n\nLogIn success on Facebook");
        return true;
    }

    public boolean sendData(byte[] data) {
        System.out.println("Message: '" + new String(data) + "' was posted on Facebook");
        return true;
    }

    public void logOut() {
        System.out.println("User: '" + this.getUserName() + "' was logged out from Facebook");
    }

    // 模拟网络延迟
    private void simulateNetworkLatency() {
        try {
            int i = 0;
            System.out.println();
            while (i < 10) {
                System.out.print(".");
                Thread.sleep(500);
                i++;
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}