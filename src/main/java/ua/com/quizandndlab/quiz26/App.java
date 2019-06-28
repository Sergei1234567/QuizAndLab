package ua.com.quizandndlab.quiz26;

import java.util.List;

public class App {
    public static void main(String[] args) {
        List<? extends Number> list0 = null;
        List<?> list1 = null;
        list1 = list0;
    }
}
