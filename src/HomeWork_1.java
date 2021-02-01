public class HomeWork_1 {
    /*
     GU_Java_1017 (23.01.2021)
     First homework
     */


    /* __________№1__________*/
    public static void main(String[] args) {

        /* __________№2__________*/
        byte a = 125;
        short b = 1125;
        int c = 1111254;
        long d = 122245812210L;

        float e = 1.2f;
        double f = 1.255456;

        boolean g = 10 > 100;

        char i = 'a';

        /* __________№3__________*/
//        System.out.println(Calc(1.5f, 2.5f, 3.0f, 4.23f));

        /* __________№4__________*/
//        System.out.println(ToSum(11,2));

        /* __________№5__________*/
//        CheckNumber(-1);

        /* __________№6__________*/
//        System.out.println(Check(1));

        /* __________№7__________*/
//        SayHellow("Dima");

        /* __________№8__________*/
        LeapYear(1766);
    }

    public static float Calc(float a, float b, float c, float d) {
        return a * (b + (c / d));

    }

    public static boolean ToSum(int a, int b) {
        return ((a + b) <= 20 && (a + b) >= 10);
    }


    public static void CheckNumber(int number) {
        if (number >= 0) {
            System.out.println("Число положительное");
        } else {
            System.out.println("Число отрицательное");
        }

    }

    public static boolean Check(int number) {
        return number < 0;
    }

    public static void SayHellow(String name) {
        System.out.println("Привет, " + name);
    }

    public static void LeapYear(int year) {
        if ((year % 4 == 0) || (year % 400 == 0 && year %100 !=0)) {
            System.out.println(year + " - год високосный");
        } else {
            System.out.println(year + " - год не високосный");
        }
    }
}