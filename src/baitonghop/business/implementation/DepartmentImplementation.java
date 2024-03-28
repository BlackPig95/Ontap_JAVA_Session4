package baitonghop.business.implementation;

import baitonghop.business.config.CONSTANT;
import baitonghop.business.design.IManageable;
import baitonghop.business.entity.Department;
import baitonghop.business.entity.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static baitonghop.business.implementation.EmployeeImplementation.employeeList;

public class DepartmentImplementation implements IManageable<Department, Employee>
{
    static final List<Department> departmentList = new ArrayList<>();

    //Hàm hỗ trợ việc gọi bên ngoài class, để không phải khai báo các list cần thiết ở class khác
    public void addDepartment(Scanner scanner)
    {
        addEntity(scanner, departmentList, employeeList);
    }

    @Override
    public void displayAll(Scanner scanner)
    {
        if (departmentList.isEmpty())
        {
            System.out.println("Hiện không có phòng ban nào để hiển thị");
            return;
        }
        System.out.println("Danh sách thông tin các phòng ban hiện có:");
        for (Department d : departmentList)
        {
            d.displayData();
        }
    }

    @Override
    public void addEntity(Scanner scanner, List<Department> departmentList, List<Employee> employeeList)
    {
        System.out.println("Bạn muốn thêm bao nhiêu phòng ban?");
        int n = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < n; i++)
        {
            Department newDept = new Department();
            System.out.println("Nhập thông tin cho phòng ban thứ " + (i + 1));
            newDept.inputData(scanner, departmentList, employeeList, true);
            departmentList.add(newDept);
            System.out.println("Thêm phòng ban thành công");
        }
    }

    @Override
    public void updateEntity(Scanner scanner)
    {
        int updateIndex = getIndexById(scanner);
        if (updateIndex == -1)
        {
            System.out.println("Không tìm thấy phòng ban để cập nhật");
            return;
        }
        while (true)
        {
            System.out.println("Nhập lựa cho để chỉnh sửa thông tin");
            System.out.println("1. Sửa tên phòng ban");
            System.out.println("2. Sửa trạng thái phòng ban");
            System.out.println("3. Cập nhật tất cả thông tin");
            System.out.println("0. Thoát");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice)
            {
                case 1:
                    System.out.println("Mời nhập tên mới");
                    departmentList.get(updateIndex).setName(scanner.nextLine(), departmentList);
                    System.out.println("Hoàn thành yêu cầu cập nhật tên");
                    break;
                case 2:
                    System.out.println("Trạng thái cũ: " +
                            (departmentList.get(updateIndex).getStatus() ? "Hoạt động" : "Không hoạt động"));
                    //Set về trạng thái ngược lại với hiện tại
                    departmentList.get(updateIndex).setStatus(!departmentList.get(updateIndex).getStatus());
                    System.out.println("Thay đổi trạng thái thành công");
                    break;
                case 3:
                    System.out.println("Mời nhập thông tin mới");
                    departmentList.get(updateIndex).inputData(scanner, departmentList, employeeList, false);
                    System.out.println("Cập nhật thành công");
                    break;
                case 0:
                    return;
                default:
                    System.out.println(CONSTANT.CHOICE_NOT_AVAI);
                    break;
            }
        }
    }

    @Override
    public void changeStatus(Scanner scanner)
    {
        int statusIndex = getIndexById(scanner);
        if (statusIndex == -1)
        {
            System.out.println("Không tìm thấy phòng ban để cập nhật");
            return;
        }
        System.out.println("Trạng thái cũ: " +
                (departmentList.get(statusIndex).getStatus() ? "Hoạt động" : "Không hoạt động"));
        //Set về trạng thái ngược lại với hiện tại
        departmentList.get(statusIndex).setStatus(!departmentList.get(statusIndex).getStatus());
        System.out.println("Cập nhật trạng thái thành công");
        System.out.println("Trạng thái mới: " +
                (departmentList.get(statusIndex).getStatus() ? "Hoạt động" : "Không hoạt động"));
    }

    @Override
    public int getIndexById(Scanner scanner)
    {
        if (departmentList.isEmpty())
        {
            System.out.println("Hiện không có phòng ban nào");
            return -1;
        }
        System.out.println("Danh sách thông tin các phòng ban hiện có:");
        for (int i = 0; i < departmentList.size(); i++)
        {   //Vòng lặp đầu tiên để liệt kê danh sách
            System.out.println(departmentList.get(i).toString());
        }
        System.out.println("Mời nhập mã số phòng ban cần tìm");
        int choice = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < departmentList.size(); i++)
        {   //Nếu tìm thấy thì trả về ngay index đó
            if (choice == departmentList.get(i).getId())
                return i;
        }
        return -1;//Chạy hết vòng lặp mà không return => Không tìm thấy
    }

    public void searchByName(Scanner scanner)
    {
        System.out.println("Mời nhập tên phòng ban cần tim");
        String searchName = scanner.nextLine();
        boolean nameFound = false;
        System.out.println("Danh sách phòng ban có tên giống với mô tả:");
        for (Department d : departmentList)
        {
            if (d.getName().contains(searchName))
            {
                System.out.println(d);
                nameFound = true;
            }
        }
        if (!nameFound)
            System.out.println("Không có");
    }
}
