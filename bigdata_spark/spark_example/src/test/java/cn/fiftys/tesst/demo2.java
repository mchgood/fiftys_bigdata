package cn.fiftys.tesst;

import java.util.Scanner;

public class demo2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //1,拿到用户输入的字符串~
        System.out.println("请输入字符串");
        String userStr = scanner.next();
        //2,使用stringBuilder类中的reverse方法反转字符串
        StringBuilder sb = new StringBuilder(userStr);
        StringBuilder reverse = sb.reverse();
        //3,将stringBuilder类型转化为String类型,在与用户输入的字符串相匹配
        String userId2 = reverse.toString();
        if (userStr.equals(userId2)){
            System.out.println("您输入的字符串反转是相同的哦~");
        }else {
            System.out.println("您输入的字符串反转过后不相同哦~");
        }
    }


}
