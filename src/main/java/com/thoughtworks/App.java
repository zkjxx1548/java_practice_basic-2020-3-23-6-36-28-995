package com.thoughtworks;

import java.util.Scanner;

public class App {

  public static void main(String[] args) {
    LoginPage loginPage = new LoginPage();
    loginPage.mainPage();
    Scanner scanner = new Scanner(System.in);

    User user = null;
    UserRepository userRepository = new UserRepository();
    while (scanner.hasNext()) {
      int choose = scanner.nextInt();
      switch (choose) {
        case 1 :
          user = loginPage.choose_1();
          userRepository.save(user);
          loginPage.successRegisterPage(user);
          break;
        case 2 :
          loginPage.choose_2(userRepository);
          break;
      }
    }

  }


}
