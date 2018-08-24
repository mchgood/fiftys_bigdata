package cn.fiftys.tesst;

import java.util.Scanner;

public class demo {
    public static void main(String[] args) {
        //定义账号
        String accout = "WOIHFIE";
        String password = "wqfwfg";
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < 3; i++) {
            //请输入账号
            System.out.println("请输出账号..");
            String userId = sc.next();
            //密码
            System.out.println("请输出密码");
            String password1 = sc.next();
            if (userId.equals(accout) && password1.equals(password)) {
                System.out.println("登陆成功..");
                break;
            } else {
                if ((2 - i) == 0) {
                    System.out.println("账号密码被锁定...");
                } else {
                    System.out.println("您还有" + (2 - i) + "次机会");
                }
            }
        }
    }
}
