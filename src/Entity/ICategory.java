package Entity;

import java.util.List;
import java.util.Scanner;

public interface ICategory<T> {
    default void inputDataCategory(Scanner sc){};
    default void displayData(){};

}
