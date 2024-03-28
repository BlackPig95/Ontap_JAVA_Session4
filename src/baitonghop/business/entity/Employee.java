package baitonghop.business.entity;

import baitonghop.business.config.CONSTANT;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Employee
{
    private int id;
    private String fullName;
    private String address;
    private String phone;
    private LocalDate dateOfBirth;
    private Department department;
    private Boolean status = true;

    public Employee()
    {
    }

    public Employee(String fullName, String address, String phone, LocalDate dateOfBirth, Department department, Boolean status)
    {
        this.fullName = fullName;
        this.address = address;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.department = department;
        this.status = status;
    }

    public int getId()
    {
        return id;
    }

    public void setId(List<Employee> employeeList)//Id tự tăng nên không cho nhập
    {
        if (employeeList.isEmpty())//Nếu list chưa có phần tử nào thì đây chính là phần tử đầu tiên
        {
            this.id = 1;
        } else
        {
            int maxId = employeeList.get(0).getId();
            for (Employee e : employeeList)
            {
                if (e.getId() > maxId)
                    maxId = e.getId();//Tìm ra id lớn nhất hiện có trong list employee
            }
            this.id = ++maxId;//Set this.id tăng thêm 1 so với maxId hiện tại
        }
    }

    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        String regex = "^(0|\\+84)[9873]\\d{8,9}$";
        if (phone.matches(regex))
        {
            this.phone = phone;
        } else
        {
            System.out.println("Số điện thoại không hợp lệ.");
            System.out.println("Số điện thoại Việt Nam bắt đầu bằng 0 hoặc +84," +
                    " theo sau là một trong các số 9,8,7,3. Có 10 hoặc 11 chữ số tất cả");
        }
    }

    public LocalDate getDateOfBirth()
    {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth)
    {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try
        {
            this.dateOfBirth = LocalDate.parse(dateOfBirth, dateTimeFormatter);
        } catch (DateTimeParseException dt)
        {
            System.out.println(CONSTANT.INPUT_CORRECT_FORMAT);
        } catch (Exception e)
        {
            System.out.println("Đã xảy ra lỗi, vui lòng thử lại");
        }
    }

    public Department getDepartment()
    {
        return department;
    }

    public void setDepartment(Department department)
    {
        this.department = department;
    }

    public Boolean getStatus()
    {
        return status;
    }

    public void setStatus(Boolean status)
    {
        this.status = status;
    }

    public void displayData()
    {
        System.out.printf("Mã nhân viên: %d - Tên nhân viên: %s - Địa chỉ: %s\n", this.id, this.fullName, this.address);
        System.out.printf("Số điện thoại: %s - Ngày sinh: %s - Phòng ban: %s - Trạng thái: %s\n",
                this.phone, this.dateOfBirth.toString(),
                this.department == null ? "Không có" : this.department.getName(),
                this.status ? "Làm việc" : "Nghỉ việc");
        System.out.println("----------------------------------------------------------------------------------");
    }

    public void inputData(Scanner scanner, List<Department> departmentList, List<Employee> employeeList, boolean isAdding)
    {
        if (isAdding)//Nếu là hành động thêm mới thì mới cần cập nhật id
        {
            setId(employeeList);
        }
        inputEmployeeName(scanner);
        inputEmployeeAddress(scanner);
        inputEmployeePhone(scanner);
        inputEmployeeDob(scanner);
        inputEmployeeDepartment(scanner, departmentList);
        inputEmployeeStatus(scanner);
    }

    private void inputEmployeeName(Scanner scanner)
    {
        while (true)
        {
            System.out.println("Nhập tên nhân viên");
            String inputName = scanner.nextLine();
            if (inputName.isBlank())
            {
                System.out.println(CONSTANT.NAME_NOT_NULL + ". " + CONSTANT.INPUT_AGAIN);
                continue;//Tên bị trống thì bắt nhập lại luôn
            }
            this.fullName = inputName;//Khi không trống thì set tên và phá vòng lặp
            break;
        }
    }

    private void inputEmployeeAddress(Scanner scanner)
    {
        while (true)
        {
            System.out.println("Nhập địa chỉ của nhân viên");
            String inputAddress = scanner.nextLine();
            if (inputAddress.isBlank())
            {
                System.out.println(CONSTANT.NAME_NOT_NULL + ". " + CONSTANT.INPUT_AGAIN);
                continue;//Tên bị trống thì bắt nhập lại luôn
            }
            this.address = inputAddress;//Khi không trống thì set tên và phá vòng lặp
            break;
        }
    }

    private void inputEmployeePhone(Scanner scanner)
    {
        String regex = "^(0|\\+84)[9873]\\d{8,9}$";
        while (true)
        {
            System.out.println("Nhập số điện thoại của nhân viên này theo định dạng của Việt Nam: " +
                    "Bắt đầu bằng 0 hoặc +84, theo sau là một trong các số 9,8,7,3. Có 10 hoặc 11 chữ số tất cả");
            String inputPhone = scanner.nextLine();
            if (inputPhone.matches(regex))
            {
                this.phone = inputPhone;
                break;
            } else System.out.println(CONSTANT.INPUT_CORRECT_FORMAT);
        }
    }

    private void inputEmployeeDob(Scanner scanner)
    {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while (true)
        {
            System.out.println("Nhập ngày sinh của nhân viên theo định dạng dd/MM/yyyy");
            String inputDob = scanner.nextLine();
            try
            {
                this.dateOfBirth = LocalDate.parse(inputDob, dateTimeFormatter);
                return;//Parse lỗi thì không kịp gọi return, chạy vào catch
            } catch (DateTimeParseException dt)
            {
                System.out.println(CONSTANT.INPUT_CORRECT_FORMAT);
            } catch (Exception e)
            {
                System.out.println("Đã xảy ra lỗi, vui lòng thử lại");
            }
        }
    }

    private void inputEmployeeDepartment(Scanner scanner, List<Department> departmentList)
    {
        if (departmentList.isEmpty())
        {
            System.out.println("Hiện không có phòng ban nào để chọn");
            this.department = null;
            return;//return fist để giảm bớt số ngoặc nhọn lồng nhau cho dễ nhìn
        }
        //Trong trường hợp list không trống, kiểm tra xem có phòng ban nào đang hoạt động không
        boolean isWorking = false;
        for (Department d : departmentList)
        {
            if (d.getStatus())//Nếu có ít nhất 1 phòng ban đang hoạt động thì set flag về true
            {
                isWorking = true;
                break;//Chỉ cần 1 phòng ban đang hoạt động là đủ, nên không cần duyệt tiếp
            }
        }
        if (!isWorking)//Nếu mọi phòng ban đều không hoạt động, thì không thể chọn
        {
            System.out.println("Hiện không có phòng ban nào đang hoạt động");
            this.department = null;
            return;//return fist để giảm bớt số ngoặc nhọn lồng nhau cho dễ nhìn
        }
        //Khi có ít nhất 1 department trong list đang hoạt động thì mới cho lựa chọn
        while (true)//Loop để tránh nhập sai lựa chọn
        {
            System.out.println("Nhập 1 nếu muốn thêm phòng ban cho nhân viên, nhập 2 nếu muốn để trống");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice)
            {
                case 1:
                    System.out.println("Danh sách các phòng ban hiện đang hoạt động:");
                    for (int i = 0; i < departmentList.size(); i++)
                    {   //In ra số thứ tự bằng cách lấy index + 1
                        if (departmentList.get(i).getStatus())//Chỉ in ra những phòng ban đang hoạt động
                            System.out.println((i + 1) + ": " + departmentList.get(i).getName());
                    }
                    while (true)
                    {
                        System.out.println("Vui lòng chọn phòng ban bằng cách nhập số mong muốn");
                        int departmentChoice = Integer.parseInt(scanner.nextLine());
                        if (departmentChoice < 1 || departmentChoice > departmentList.size())
                        {   //Nếu nhỏ hơn số thứ tự hoặc lớn hơn size thì nằm ngoài index
                            System.out.println(CONSTANT.CHOICE_NOT_AVAI + ". " + CONSTANT.INPUT_AGAIN);
                        } else
                        {   //Trừ đi 1 để đúng với index
                            this.department = departmentList.get(departmentChoice - 1);
                            return;//Nhập đúng thì dừng hàm
                        }
                    }
                case 2:
                    this.department = null;
                    return;//Lựa chọn đúng thì dừng hàm
                default:
                    System.out.println(CONSTANT.CHOICE_NOT_AVAI + ". " + CONSTANT.INPUT_AGAIN);
                    break;//Nhập sai lựa chọn thì bắt nhập lại nên không phá vòng while
            }
        }
    }

    private void inputEmployeeStatus(Scanner scanner)
    {
        while (true)
        {
            System.out.println("Nhập trạng thái của nhân viên: true - Làm việc, false - Nghỉ việc");
            String inputStatus = scanner.nextLine();
            if (inputStatus.equals(CONSTANT.TRUE) || inputStatus.equals(CONSTANT.FALSE))
            {
                this.status = Boolean.parseBoolean(inputStatus);
                break;
            } else System.out.println("Chỉ được nhập true hoặc false. " + CONSTANT.INPUT_AGAIN);
        }
    }

    @Override
    public String toString()
    {
        return "Mã nhân viên: " + this.id + " | Tên nhân viên: " + this.fullName;
    }
}
