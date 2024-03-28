package baitonghop.business.entity;

import baitonghop.business.config.CONSTANT;

import java.util.List;
import java.util.Scanner;

public class Department
{
    private int id;
    private String name;
    private int numberEmployee;
    private Boolean status = true;

    public Department()
    {
    }

    public Department(String name, Boolean status)
    {
        this.name = name;
        this.status = status;
    }

    public int getId()
    {
        return id;
    }

    public void setId(List<Department> departmentList)//Id tự tăng nên không cho set
    {
        if (departmentList.isEmpty())//Nếu list chưa có phần tử nào thì đây chính là phần tử đầu tiên
        {
            this.id = 1;
        } else
        {
            int maxId = departmentList.get(0).getId();
            for (Department d : departmentList)
            {
                if (d.getId() > maxId)
                    maxId = d.getId();//Tìm ra id lớn nhất hiện có trong list department
            }
            this.id = ++maxId;//Set this.id tăng thêm 1 so với maxId hiện tại
        }
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name, List<Department> departmentList)
    {
        updateName(name, departmentList);
    }

    public int getNumberEmployee()
    {
        return numberEmployee;
    }

    public void setNumberEmployee(List<Employee> employeeList)//Không cho tự set
    {
        if (employeeList.isEmpty())//Giá trị mặc định là 0 nên nếu list trống thì không cần đếm
            return;
        int count = 0;//Dùng biến đếm để cập nhật theo mỗi lần thêm hoặc xóa nhân viên
        for (Employee e : employeeList)
        {
            if (e.getDepartment() == this)
                count++;
        }
        this.numberEmployee = count;//Sau khi đếm xong mới gán lại
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
        System.out.printf("Mã phòng ban: %d - Tên phòng ban: %s - Số lượng nhân viên: %d - Trạng thái: %s\n",
                this.id, this.name, this.numberEmployee, this.status ? "Hoạt động" : "Không hoạt động");
        System.out.println("----------------------------------------------------------------------------------");
    }

    public void inputData(Scanner scanner, List<Department> departmentList, List<Employee> employeeList, boolean isAdding)
    {
        if (isAdding)//Nếu là hành động thêm mới thì mới cần cập nhật id
        {
            setId(departmentList);
        }
        if (isAdding)//Nếu là hành động thêm mới thì gọi hàm inputName
        {
            this.name = inputDepartmentName(scanner, departmentList);
        } else//Nếu là hành động update thì gọi hàm update name
        {
            System.out.println("Mời nhập tên mới");
            String newName = scanner.nextLine();
            updateName(newName, departmentList);
        }
        inputDepartmentStatus(scanner);
        setNumberEmployee(employeeList);
    }

    private String inputDepartmentName(Scanner scanner, List<Department> departmentList)
    {
        while (true)
        {
            System.out.println("Nhập tên phòng ban");
            String inputName = scanner.nextLine();
            if (inputName.isBlank())//Tên để trống thì bắt nhập lại
            {
                System.out.println(CONSTANT.NAME_NOT_NULL + ". " + CONSTANT.INPUT_AGAIN);
                continue;
            }
            if (checkNameNotExist(inputName, departmentList))//Không bị trùng thì return inputName
            {
                return inputName;
            } else
            {   //Trùng thì bắt nhập lại
                System.out.println(CONSTANT.NAME_NOT_NULL + ". " + CONSTANT.INPUT_AGAIN);
            }
        }
    }

    private void inputDepartmentStatus(Scanner scanner)
    {
        while (true)
        {
            System.out.println("Nhập trạng thái của phòng ban: true - Hoạt động, false - Không hoạt động");
            String inputStatus = scanner.nextLine();
            if (inputStatus.equals(CONSTANT.TRUE) || inputStatus.equals(CONSTANT.FALSE))
            {
                setStatus(Boolean.parseBoolean(inputStatus));
                break;
            } else System.out.println("Chỉ được nhập true hoặc false. " + CONSTANT.INPUT_AGAIN);
        }
    }

    @Override
    public String toString()
    {
        return "Mã phòng ban: " + this.id + " | Tên phòng ban: " + this.name;
    }

    private boolean checkNameNotExist(String nameToCheck, List<Department> departmentList)
    {
        if (departmentList.isEmpty())//List trống thì không cần kiểm tra trùng lặp
        {
            return true;
        }
        for (Department d : departmentList)//Kiểm tra trùng lặp
        {
            if (d.name.equals(nameToCheck))
            {
                return false;
            }
        }
        return true;//Chạy hết loop mà không bị return => không trùng
    }

    private void updateName(String nameToUpdate, List<Department> departmentList)
    {
        if (departmentList.isEmpty())//Nếu list đang trống thì không cần kiểm tra
        {
            this.name = nameToUpdate;
            return;
        }
        if (!checkNameNotExist(nameToUpdate, departmentList))//False = tên bị trùng
        {
            if (this.name.equals(nameToUpdate))//Kiểm tra xem có phải là đang trùng với chính nó không
            {//Nếu là trùng với chính nó thì cho phép update
                this.name = nameToUpdate;
            }//Nếu không => trùng với tên khác => không cho set name
            else System.out.println(CONSTANT.NAME_EXISTED);
        }

    }
}
