package Entity;

import java.util.Scanner;

public interface IProduct {
    float MIN_INTEREST_RATE = 0.2F;
    default void inputData(Scanner sc){};
    default void displayData(){};
    default void calPropit(){};

}
