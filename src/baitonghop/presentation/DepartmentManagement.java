package baitonghop.presentation;

import baitonghop.business.config.CONSTANT;
import baitonghop.business.implementation.DepartmentImplementation;

import java.util.Scanner;

public class DepartmentManagement
{
    private static final DepartmentImplementation departmentImplementation = new DepartmentImplementation();

    public void showManageMenu(Scanner scanner)
    {
        while (true)
        {
            System.out.println("====================MENU====================");
            System.out.println("1. Hiển thị tất cả department");
            System.out.println("2. Thêm mới department");
            System.out.println("3. Sửa thông tin department");
            System.out.println("4. Thay đổi trạng thái department");
            System.out.println("5. Tìm kiếm theo tên");
            System.out.println("6. Quay lại");
            System.out.println("============================================");
            System.out.println("Vui lòng lựa chọn:");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice)
            {
                case 1:
                    departmentImplementation.displayAll(scanner);
                    break;
                case 2:
                    departmentImplementation.addDepartment(scanner);
                    break;
                case 3:
                    departmentImplementation.updateEntity(scanner);
                    break;
                case 4:
                    departmentImplementation.changeStatus(scanner);
                    break;
                case 5:
                    departmentImplementation.searchByName(scanner);
                    break;
                case 6:
                    return;
                default:
                    System.out.println(CONSTANT.CHOICE_NOT_AVAI);
                    break;
            }
        }
    }
}
