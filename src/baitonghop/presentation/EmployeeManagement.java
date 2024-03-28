package baitonghop.presentation;

import baitonghop.business.config.CONSTANT;
import baitonghop.business.implementation.EmployeeImplementation;

import java.util.Scanner;

public class EmployeeManagement
{
    private static final EmployeeImplementation employeeImplementation = new EmployeeImplementation();

    public void showManageMenu(Scanner scanner)
    {
        while (true)
        {
            System.out.println("====================MENU====================");
            System.out.println("1. Hiển thị tất cả nhân viên");
            System.out.println("2. Thêm mới nhân viên");
            System.out.println("3. Sửa thông tin nhân viên");
            System.out.println("4. Thay đổi trạng thái nhân viên");
            System.out.println("5. Danh sách nhân viên theo phòng ban");
            System.out.println("6. Sắp xếp nhân viên theo tên tăng dần");
            System.out.println("7. Quay lại");
            System.out.println("============================================");
            System.out.println("Vui lòng lựa chọn:");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice)
            {
                case 1:
                    employeeImplementation.displayAll(scanner);
                    break;
                case 2:
                    employeeImplementation.addEmployee(scanner);
                    break;
                case 3:
                    employeeImplementation.updateEntity(scanner);
                    break;
                case 4:
                    employeeImplementation.changeStatus(scanner);
                    break;
                case 5:
                    employeeImplementation.listByDepartment(scanner);
                    break;
                case 6:
                    employeeImplementation.sortAscendingByName();
                    break;
                case 7:
                    return;
                default:
                    System.out.println(CONSTANT.CHOICE_NOT_AVAI);
                    break;
            }
        }
    }
}
