package hellojpa;

public class ValueMain {

    public static void main(String[] args) {

        /**
         * 1. 기본 타입: 공유 X
         */
        int a = 10;
        int b = a;
        b = 20;

        System.out.println("a = " + a); // 10
        System.out.println("b = " + b); // 20

        /**
         * 2. 래퍼 클래스: 공유 O
         */
        Integer c = new Integer(10);
        Integer d = c;

//        c.setValue(20);

        System.out.println("a = " + a); // 20
        System.out.println("b = " + b); // 20

    }
}
