
import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class modu6{

    public static void main(String[] args) {
        T1(2);
        T2("I like to eat honey waffles.");
        T3("rgb(0,,0)");
        T4("https://edabit.com?a=1&b=2&a=2");
        T5("Why You Will Probably Pay More for Your Christmas Tree This Year");
        T6(4);
        T7("abcda");
        T8(12);
        T9("6 * 4 = 24");
        T10(11);

    }

    public static void T1(int n){
        System.out.println();
        System.out.println("Task 1");
        System.out.println("For n - " + n + "\nBell numbers: " + Z1(n));
    }

    public static int Z1(int n) {
        int[][] bellTriangle = new int[n+1][n+1];
        bellTriangle[0][0] = 1;

        for (int i=1; i<=n; i++) {
            bellTriangle[i][0] = bellTriangle[i-1][i-1]; //Треугольник Белла можно построить путём размещения числа 1 в первой позиции. Затем все самые левые числа берутся равными последнему числу предыдущей строки. Остальные позиции строки заполняются аналогично правилу заполнения треугольника Паскаля — число равно сумме значений слева в той же строке и слева из предшествующей строки.

            for (int j=1; j<=i; j++) {
                bellTriangle[i][j] = bellTriangle[i-1][j-1] + bellTriangle[i][j-1];
            }
        }

        return bellTriangle[n][0];
    }

    public static void T2(String str){
        System.out.println();
        System.out.println("Task 2");
        System.out.println("For sentence - " + str + "\nTranslated sentence - " + Z22(str));
    }

    public static String Z2(String word) {
        String result = word;

        if (String.valueOf(result.charAt(0)).toLowerCase().matches("[aeiouy]")) { //проверка если первые гласные
            result += "yay";
        } else {
            result = result.toLowerCase();
            String newWord = result.split("[aeiouy]")[0]; //разделяем слово по гласной букве (записываем сиволы до первой гласной буквы)
            result = result.replaceFirst(newWord,"") + newWord + "ay"; //переставляет
            result = String.valueOf(result.charAt(0)).toUpperCase() + result.substring(1);
        }

        return result;
    }

    public static String Z22(String str) {
        char lastSymb = str.charAt(str.length() - 1);
        StringBuffer strB = new StringBuffer(str);
        strB.insert(str.length()-1," ");
        str = new String(strB);
        String[] tokens = str.split(" ");
        str = Z2(tokens[0]) + " ";
        for (int i = 1; i < tokens.length - 1; i++) {
            str += Z2(tokens[i]).toLowerCase() + " ";
        }
        return str.trim() + lastSymb;
    }


    public static void T3(String str){
        System.out.println();
        System.out.println("Task 3");
        System.out.println("Is the string " + str + " an rgb format - " + Z3(str));
    }

    public static boolean Z3(String str) {
        if (!str.startsWith("rgb") && !str.startsWith("rgba")) { //проверка на слова rgb и rgba
            return false;
        }

        String[] numbers = str.split("\\(")[1].split(","); // подстрока из чисел
        numbers[numbers.length - 1] = numbers[numbers.length - 1].substring(0, numbers[numbers.length - 1].length() - 1); //убирает скобку последнюю

        if (str.startsWith("rgb") && !str.startsWith("rgba")) {
            if (str.contains(".")) { //в ргб не может быть не целых
                return false;
            }

            for (int i = 0; i < numbers.length; i ++) {
                if (numbers[i].trim().equals("")) { //если нет числа
                    return false;
                }

                int num = Integer.parseInt(numbers[i].trim());

                if (!(num >= 0 && num <= 255)) { //для проверки ргб
                    return false;
                }
            }
        } else {
            for (int i = 0; i < numbers.length - 1; i ++) { //первые 3 элемента
                if (numbers[i].trim().equals("")) {
                    return false;
                }

                int num = Integer.parseInt(numbers[i].trim());

                if (!(num >= 0 && num <= 255)) {
                    return false;
                }
            }

            if (numbers[3].trim().equals("")) return false; //проверка последнего на пустоту

            double num = Double.parseDouble(numbers[3].trim());

            return num >= 0 && num <= 1; //проверяем дробное значение
        }

        return true;
    }

    public static void T4(String str){
        System.out.println();
        System.out.println("Task 4");
        System.out.println("For URL" + str+ "\nResult - " + Z4(str));
    }

    public static String Z4(String url, String ...paramsToStrip) {
        String str = "";

        if (!url.contains("?"))
            return url;
        else {
            str = url.substring(url.indexOf("?") + 1);
            url = url.substring(0, url.indexOf("?") + 1);
        }

        char[] params = str.toCharArray();
        StringBuilder print = new StringBuilder();

        for (char param : params) { //цикл который рассматривает каждый символ
            if (Character.isLetter(param)) //буква?
                if (!(print.toString().contains(String.valueOf(param)))) { //проверяем не содержится ли символ в строке
                    if (paramsToStrip.length > 0) { //если есть 2е значение
                        for (String arg : paramsToStrip) {
                            if (!(arg.contains(String.valueOf(param))))
                                print.append(str, str.lastIndexOf(param), str.lastIndexOf(param) + 3).append("&"); //это условие отвечает за правую часть после ? постоянно вставляет & после каждого элемента и послднее вхождение символа в строку
                        }
                    }
                    else
                        print.append(str, str.lastIndexOf(param), str.lastIndexOf(param) + 3).append("&"); //если нет 2й строки
                }
        }

        return url + print.substring(0, print.length()-1);
    }

    public static void T5(String str){
        System.out.println();
        System.out.println("Task 5");
        System.out.println("For str" + str + "\nResult - " + Z5(str).toString());
    }


    public static ArrayList<String> Z5(String str){
        String[] tokens = str.toLowerCase().split(" "); //разбивает на слова
        ArrayList<String> hashtags = new ArrayList<>(); //результат

        while (hashtags.size() < 3) { //3 саммых длинных слова если их нет то
            double maxLength = Double.NEGATIVE_INFINITY;
            String word = "";
            int idx = 0;

            try {
                for (int i = 0; i < tokens.length; i++) {
                    if (tokens[i].length() > maxLength) {
                        maxLength = tokens[i].length(); //слово с максимальной длиной
                        word = tokens[i];
                        idx = i;
                    }
                }

                if (String.valueOf(word.charAt(word.length() - 1)).matches("[!?.,;:]")) { //если это слово содержит запятые
                    hashtags.add("#" + word.substring(0, word.length() - 1));
                } else {
                    hashtags.add("#" + word);
                }
                tokens[idx] = "";
            } catch (StringIndexOutOfBoundsException e) {
                return hashtags;
            }
        }

        return hashtags;
    }
    public static void T6(int n){
        System.out.println();
        System.out.println("Task 6");
        System.out.println("For str" + n + "\nResult - " + Z6(n));
    }

    public static int Z6 (int n){
        int[] arr = new int[n];
        arr[0]=1;
        arr[1]=2;
        int len=2, next=3;

        while (next < Integer.MAX_VALUE && len < n){
            int count = 0;

            for (int i = 0; i < len; i++){
                for (int j = len-1; j > i; j--){
                    if (arr[i] + arr[j] == next && arr[i] != arr[j]) //находим следующее число из суммы двух других
                        count++;
                    else if (count > 1) //но если таких сумм много то вызываем исключение
                        break;
                }

                if (count > 1)
                    break;
            }
            if (count == 1) {
                arr[len] = next; //если сумма единственна то записываем в массив
                len++;
            }
            next++;
        }
        return arr[n-1];
    }
    public static void T7(String str){
        System.out.println();
        System.out.println("Task 7");
        System.out.println("For str" + str + "\nResult - " + Z7(str));
    }

    public static String Z7(String str){
        String substr = "";
        char [] chars = str.toCharArray();
        StringBuilder builder = new StringBuilder();

        for (char c : chars) {
            if (!builder.toString().contains(String.valueOf(c))) //если не содержит записыает
                builder.append(c);
            else {
                if (builder.length() > substr.length()) {
                    substr = builder.toString(); //максимальная поддстрока
                }
                builder = new StringBuilder("" + c);
            }
        }

        str = builder.toString();

        if (str.length() > substr.length())
            substr = str;

        return substr;
    }
    public static void T8(int num) {
        System.out.println();
        System.out.println("Task 8");
        System.out.println("For str" + num + "\nResult - " + Z8(num));
    }

    public static String Z8 (int num){
        StringBuilder roman = new StringBuilder();

        if (num < 1 || num > 3999)
            return "Введите другое число. ";

        while (num >= 1000) {
            roman.append("M");
            num -= 1000;
        }

        while (num >= 900) {
            roman.append("CM");
            num -= 900;
        }

        while (num >=500) {
            roman.append("D");
            num -= 500;
        }

        while (num >= 400) {
            roman.append("CD");
            num -= 400;
        }

        while (num >= 100) {
            roman.append("C");
            num -= 100;
        }

        while (num >= 90) {
            roman.append("XC");
            num -= 90;
        }

        while (num >= 50) {
            roman.append("L");
            num -= 50;
        }

        while (num >= 40) {
            roman.append("XL");
            num -= 40;
        }

        while (num >= 10) {
            roman.append("X");
            num -= 10;
        }

        while (num >= 9) {
            roman.append("IX");
            num -= 9;
        }

        while (num >= 5) {
            roman.append("V");
            num -= 5;
        }

        while (num >= 4) {
            roman.append("IV");
            num -= 4;
        }

        while (num >= 1) {
            roman.append("I");
            num -= 1;
        }

        return roman.toString();
    }
    public static void T9(String str){
        System.out.println();
        System.out.println("Task 9");
        System.out.println("For str" + str + "\nResult - " + Z9(str));
    }

    public static boolean Z9(String formula){
        String[] tokens = formula.split(" ");
        int ans = 0;
        int expectedResult = 0;

        if (!Character.isDigit(tokens[0].charAt(0))) return false;
        else ans = Integer.parseInt(tokens[0]);

        int i = 1;

        while (!tokens[i].equals("=")) {
            if (tokens[i].equals("+")){
                ans += Integer.parseInt(tokens[i + 1]);
            }
            if (tokens[i].equals("-")){
                ans -= Integer.parseInt(tokens[i + 1]);
            }
            if (tokens[i].equals("*")){
                ans *= Integer.parseInt(tokens[i + 1]);
            }
            if (tokens[i].equals("/")){
                ans /= Integer.parseInt(tokens[i + 1]); //находим чему равна левая часть
            }

            i += 2;
        }

        i = formula.indexOf('='); //находим индекс =
        String check = formula.substring(i + 1); //значение после =

        if (check.contains("=")) return false;
        else expectedResult = Integer.parseInt(tokens[tokens.length - 1]); //записыает число из массива

        return ans == expectedResult;
    }
    public static void T10(int num) {
        System.out.println();
        System.out.println("Task 10");
        System.out.println("For str" + num + "\nResult - " + Z10(num));
    }

    public static boolean Z10(int num){
        boolean isDescendant = false;
        StringBuffer buffer1 =new StringBuffer(num);
        StringBuffer buffer2 =new StringBuffer(num);

        if (buffer1.length() % 2 != 0) //если число содержит нечетное кол-во символов
            return false;
        else {
            while (!isDescendant){
                if(buffer1 != buffer1.reverse()){
                    for(int i = 0; i < buffer1.length(); i += 2){
                        int a = Integer.parseInt(String.valueOf(buffer1.charAt(i)));
                        int b = Integer.parseInt(String.valueOf(buffer1.charAt(i + 1)));
                        buffer2.append(a + b); //записываем сумму 2ух чисел соседних
                    }
                }
                else
                    isDescendant = true;
            }
        }

        return isDescendant;
    }
}