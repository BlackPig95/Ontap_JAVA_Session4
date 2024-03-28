package baitonghop.presentation;

import baitonghop.business.config.CONSTANT;

import java.util.Scanner;

public class ManageMenu
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        DepartmentManagement departmentManagement = new DepartmentManagement();
        EmployeeManagement employeeManagement = new EmployeeManagement();
        while (true)
        {
            System.out.println("====================MENU====================");
            System.out.println("1. Quản lý department");
            System.out.println("2. Quản lý employee");
            System.out.println("3. Thoát");
            System.out.println("============================================");
            System.out.println("Vui lòng lựa chọn:");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice)
            {
                case 1:
                    departmentManagement.showManageMenu(scanner);
                    break;
                case 2:
                    employeeManagement.showManageMenu(scanner);
                    break;
                case 3:
                    return;
                default:
                    System.out.println(CONSTANT.CHOICE_NOT_AVAI);
                    break;
            }
        }
    }
}
