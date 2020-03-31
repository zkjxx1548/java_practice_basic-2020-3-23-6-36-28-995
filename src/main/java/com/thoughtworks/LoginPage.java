package com.thoughtworks;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Stream;

public class LoginPage {
    public void mainPage() {
        System.out.println("1. 注册\n"
                + "2. 登录\n"
                + "3. 退出\n"
                +"请输入你的选择(1~3)：");
    }

    public void successRegisterPage(User user) {
        System.out.println(user.getName() + "，恭喜你注册成功！");
        System.out.print("1. 注册\n");
        System.out.print("2. 登录\n");
        System.out.print("3. 退出\n");
    }

    public User choose_1() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入注册信息(格式：用户名,手机号,邮箱,密码)：");
        boolean register = false;
        User user = null;
        while (!register) {
            String info = scanner.nextLine();
            String[] infoSingles = info.split(",");
            if (infoSingles.length != 4 && infoSingles.length != 0) {
                formatMistake("注册信息");
                continue;
            }
            if (infoSingles[0].length() < 2 || infoSingles[0].length() > 10) {
                registerFailReturn("用户名");
                continue;
            }

            if (infoSingles[1].length() != 11
                    || infoSingles[1].charAt(0) != '1'
                    || !isAllDigit(infoSingles[1])
            ) {
                registerFailReturn("手机号");
                continue;
            }
            String[] a = infoSingles[2].split("@");
            if (a.length != 2) {
                registerFailReturn("邮箱");
                continue;
            }

            String password = infoSingles[3];
            if (password.length() < 8
                    || password.length() > 16
                    || isAllDigit(password)
                    || isAllNotDigit(password)) {
                registerFailReturn("密码");
                continue;
            }
            register = true;
            user = new User(infoSingles[0], infoSingles[1], infoSingles[2], infoSingles[3], 0, false);
        }
        return user;
    }

    public void choose_2(UserRepository userRepository) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入用户名和密码(格式：用户名,密码)：");
        //boolean login = false;
        boolean lock = false;
        int loginCount;
        while (!lock) {
            String info = scanner.nextLine();
            String[] infoSingles = info.split(",");
            User user;
            if (infoSingles.length != 2) {
                formatMistake("用户名和密码");
                continue;
            }

            user = userRepository.queryByName(infoSingles[0]);
            if (user == null) {
                loginFailReturn();
                continue;
            }

            lock = user.isLoginLock();
            loginCount = user.getLoginCount();

            if (lock) {
                lockReturn();
                mainPage();
                break;
            }

            if (!Objects.equals(infoSingles[1], user.getPassword())) {
                userRepository.updateLoginCount(user.getName(), ++loginCount);
                if (loginCount == 3) {
                    lockReturn();
                    userRepository.updateLoginLock(user.getName(), true);
                    mainPage();
                    break;
                }
                loginFailReturn();
                continue;
            }

            //login = true;
            loginSuccessReturn(user);
            break;
        }
    }

    public static boolean isAllDigit(String str) {
        Stream<String> stream = Arrays.stream(str.split(""));
        return stream.filter(s -> Character.isDigit(s.charAt(0)))
                .count() == str.length();
    }

    public static boolean isAllNotDigit(String str) {
        Stream<String> stream = Arrays.stream(str.split(""));
        return stream.noneMatch(s -> Character.isDigit(s.charAt(0)));
    }

    public void registerFailReturn(String type) {
        System.out.println(type + "不合法\n" +
                "请输入合法的注册信息：");
    }

    public void formatMistake(String type) {
        System.out.println("格式错误\n" +
                "请按正确格式输入" + type + "：");
    }

    public void loginFailReturn() {
        System.out.println("密码或用户名错误\n" +
                "请重新输入用户名和密码：");
    }

    public void loginSuccessReturn(User user) {
        System.out.println(user.getName() + "，欢迎回来！\n"
                + "您的手机号是"
                + user.getTelephone()
                + "，邮箱是"
                + user.getEmail());
    }

    public void lockReturn() {
        System.out.println("您已3次输错密码，账号被锁定");
    }
}
