package baitonghop.business.implementation;

import baitonghop.business.config.CONSTANT;
import baitonghop.business.design.IManageable;
import baitonghop.business.entity.Department;
import baitonghop.business.entity.Employee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static baitonghop.business.implementation.DepartmentImplementation.departmentList;

public class EmployeeImplementation implements IManageable<Department, Employee>
{
    static final List<Employee> employeeList = new ArrayList<>();

    //Hàm hỗ trợ để gọi ở class bên ngoài mà không phải thêm tham số thừa thãi
    public void addEmployee(Scanner scanner)
    {
        addEntity(scanner, departmentList, employeeList);
    }

    @Override
    public void displayAll(Scanner scanner)
    {
        if (employeeList.isEmpty())
        {
            System.out.println("Hiện không có nhân viên nào để hiển thị");
            return;
        }
        pagination(scanner);
    }

    private void pagination(Scanner scanner)
    {
        int itemPerPage = 3;
        int numberOfPages = (int) Math.ceil((double) employeeList.size() / itemPerPage);
        displayPagination(0, numberOfPages, itemPerPage);//Hiển thị ban đầu
        while (true)
        {
            System.out.println("Nhập số trang bạn muốn xem, hoặc nhập 0 để thoát");
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice == 0)
                return;
            if (choice <= numberOfPages && choice >= 1)
                displayPagination(choice - 1, numberOfPages, itemPerPage);
            else
            {
                System.out.println(CONSTANT.CHOICE_NOT_AVAI);
            }
        }
    }

    private void displayPagination(int currentPage, int numberOfPages, int itemPerPage)
    {
        for (int i = currentPage * itemPerPage; i < currentPage * itemPerPage + itemPerPage; i++)
        {
            if (i == employeeList.size())//Tránh duyệt null
                break;
            System.out.println(employeeList.get(i));
        }
        System.out.printf("Trang hiện tại: %d/%d", currentPage + 1, numberOfPages);
    }

    @Override
    public void addEntity(Scanner scanner, List<Department> departmentList, List<Employee> employeeList)
    {
        System.out.println("Bạn muốn thêm bao nhiêu nhân viên?");
        int n = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < n; i++)
        {
            Employee newEmployee = new Employee();
            System.out.println("Nhập thông tin cho nhân viên thứ " + (i + 1));
            newEmployee.inputData(scanner, departmentList, employeeList, true);
            employeeList.add(newEmployee);
            System.out.println("Thêm nhân viên thành công");
        }
    }

    @Override
    public void updateEntity(Scanner scanner)
    {
        int updateIndex = getIndexById(scanner);
        if (updateIndex == -1)
        {
            System.out.println("Không tìm thấy nhân viên để cập nhật");
            return;
        }
        while (true)
        {
            System.out.println("Nhập lựa cho để chỉnh sửa thông tin");
            System.out.println("1. Sửa tên nhân viên");
            System.out.println("2. Sửa trạng thái nhân viên");
            System.out.println("3. Sửa địa chỉ nhân viên");
            System.out.println("4. Sửa số điện thoại của nhân viên");
            System.out.println("5. Sửa ngày tháng năm sinh");
            System.out.println("6. Sửa phòng ban");
            System.out.println("7. Cập nhật tất cả thông tin");
            System.out.println("0. Thoát");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice)
            {
                case 1:
                    System.out.println("Mời nhập tên mới");
                    employeeList.get(updateIndex).setFullName(scanner.nextLine());
                    System.out.println("Hoàn thành yêu cầu cập nhật tên");
                    break;
                case 2:
                    System.out.println("Trạng thái cũ: " +
                            (employeeList.get(updateIndex).getStatus() ? "Hoạt động" : "Không hoạt động"));
                    //Set về trạng thái ngược lại với hiện tại
                    employeeList.get(updateIndex).setStatus(!employeeList.get(updateIndex).getStatus());
                    System.out.println("Thay đổi trạng thái thành công");
                    break;
                case 3:
                    System.out.println("Mời nhập địa chỉ mới");
                    employeeList.get(updateIndex).setAddress(scanner.nextLine());
                    System.out.println("Hoàn thành yêu cầu cập nhật địa chỉ");
                    break;
                case 4:
                    System.out.println("Mời nhập số điện thoại mới");
                    employeeList.get(updateIndex).setPhone(scanner.nextLine());
                    System.out.println("Hoàn thành yêu cầu cập nhật số điện thoại");
                    break;
                case 5:
                    System.out.println("Nhập ngày sinh của nhân viên theo định dạng dd/MM/yyyy");
                    employeeList.get(updateIndex).setDateOfBirth(scanner.nextLine());
                    System.out.println("Hoàn thành yêu cầu cập nhật ngày sinh");
                    break;
                case 6:
                    System.out.println("Danh sách phòng ban hiện có");
                    for (int i = 0; i < departmentList.size(); i++)
                    {
                        System.out.println(departmentList.get(i));
                    }
                    System.out.println("Nhập phòng ban mới theo mã số");
                    int newDept = Integer.parseInt(scanner.nextLine());
                    boolean departmentFound = false;
                    for (int i = 0; i < departmentList.size(); i++)
                    {
                        if (newDept == departmentList.get(updateIndex).getId())
                        {
                            departmentFound = true;
                            employeeList.get(updateIndex).setDepartment(departmentList.get(newDept));
                            System.out.println("Cập nhật phòng ban thành công");
                            break;//Nếu set thành công thì break luôn
                        }
                    }
                    if (!departmentFound)
                    {
                        System.out.println("Nhập sai thông tin phòng ban");
                    }
                    break;
                case 7:
                    System.out.println("Mời nhập thông tin mới");
                    employeeList.get(updateIndex).inputData(scanner, departmentList, employeeList, false);
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
            System.out.println("Không tìm thấy nhân viên để cập nhật");
            return;
        }
        System.out.println("Trạng thái cũ: " +
                (employeeList.get(statusIndex).getStatus() ? "Làm việc" : "Nghỉ việc"));
        //Set về trạng thái ngược lại với hiện tại
        employeeList.get(statusIndex).setStatus(!employeeList.get(statusIndex).getStatus());
        System.out.println("Cập nhật trạng thái thành công");
        System.out.println("Trạng thái mới: " +
                (employeeList.get(statusIndex).getStatus() ? "Làm việc" : "Nghỉ việc"));
    }

    @Override
    public int getIndexById(Scanner scanner)
    {
        if (employeeList.isEmpty())
        {
            System.out.println("Hiện không có nhân viên nào");
            return -1;
        }
        System.out.println("Danh sách thông tin các nhân viên hiện có:");
        for (int i = 0; i < employeeList.size(); i++)
        {   //Vòng lặp đầu tiên để liệt kê danh sách
            System.out.println(employeeList.get(i).toString());
        }
        System.out.println("Mời nhập mã số nhân viên cần tìm");
        int choice = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < employeeList.size(); i++)
        {   //Nếu tìm thấy thì trả về ngay index đó
            if (choice == employeeList.get(i).getId())
                return i;
        }
        return -1;//Chạy hết vòng lặp mà không return => Không tìm thấy
    }

    public void listByDepartment(Scanner scanner)
    {
        System.out.println("Danh sách các phòng ban hiện đang hoạt động");
        for (int i = 0; i < departmentList.size(); i++)
        {
            if (departmentList.get(i).getStatus())//Chỉ in ra phòng ban nào đang hoạt động
                System.out.println(departmentList.get(i));
        }
        System.out.println("Mời nhập mã phòng ban muốn kiểm tra nhân viên");
        int choice = Integer.parseInt(scanner.nextLine());
        System.out.println("Danh sách nhân viên thuộc phòng ban này:");
        for (int i = 0; i < employeeList.size(); i++)
        {
            if (employeeList.get(i).getDepartment().getId() == choice)
            {
                System.out.println(employeeList.get(i));
            }
        }
    }

    public void sortAscendingByName()
    {
        employeeList.sort(((e1, e2) -> e1.getFullName().compareTo(e2.getFullName())));
    }
}
