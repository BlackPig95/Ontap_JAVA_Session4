package baitonghop.business.design;

import java.util.List;
import java.util.Scanner;

public interface IManageable<T, E>
{
    void displayAll(Scanner scanner);

    void addEntity(Scanner scanner, List<T> tList, List<E> eList);

    void updateEntity(Scanner scanner);

    void changeStatus(Scanner scanner);

    int getIndexById(Scanner scanner);
}
