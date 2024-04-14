import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.text.DecimalFormat;

public class DepartmentDA {

    private HashMap<String, Employee> empMap;

    public HashMap<String, Employee> getEmpMap() {
        return empMap;
    }

    public DepartmentDA() throws FileNotFoundException {
        try (Scanner deptFile = new Scanner(new FileReader("dep.csv"))) {
            empMap = new HashMap<>();

            deptFile.nextLine(); 

            while (deptFile.hasNext()) {
                String[] newRow = new String[2];
                newRow = deptFile.nextLine().split(",");

                Department department = new Department();
                department.setDepCode(newRow[0].trim());
                department.setDepName(newRow[1].trim());

                readDepEmp(department); 

                Double salary = 0.00;
                for (Map.Entry<String, Employee> entryMap : department.getEmployeeMap().entrySet()) {
                    salary += entryMap.getValue().getSalary();
                }
                department.setDepTotalSalary(salary);

                printDepartment(department);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
   
   public void readDepEmp(Department department){
        try{
            Scanner depEmpFile = new Scanner(new FileReader("deptemp.csv"));
            depEmpFile.nextLine();
            Integer key =0; 
            HashMap<String, Employee> employeeMap= new HashMap<>();
            while(depEmpFile.hasNext()){
                String depEmpline = new String();
                depEmpline = depEmpFile.nextLine();
                String[] depEmpArr = new String[3];
                depEmpArr = depEmpline.split(",");
                EmployeeDA employee = new EmployeeDA(depEmpArr[1].trim());
                if(department.getDepCode().equals(depEmpArr[0].trim())){
                    employee.getEmployee().setSalary(Double.parseDouble(depEmpArr[2]));
                    employeeMap.put(employee.getEmployee().getEmpNo()+key,employee.getEmployee());
                    key++;
                }
            }

            for(Map.Entry<String, Employee> entryMap : employeeMap.entrySet()){
                department.setDepTotalSalary(entryMap.getValue().getSalary());
            }
            department.setEmployeeMap(employeeMap);
        }catch(FileNotFoundException e){
            throw new RuntimeException(e);
        }

   }

   public void printDepartment(Department department){
        DecimalFormat df = new DecimalFormat("#,###.00");
        System.out.println("\n"+ "Department Code: "+ department.getDepCode());
        System.out.println("Department Name: "+ department.getDepName());
        System.out.println("Department total Salary: "+ df.format(department.getDepTotalSalary()));
        System.out.println("------------------Details----------------------");
        System.out.println("EmpNo \t\tEmployeeName \t\tSalary");
        
        for (Map.Entry<String, Employee> entryMap : department.getEmployeeMap().entrySet()) {
            System.out.print(entryMap.getValue().getEmpNo() + "\t");
            System.out.print("\t" + entryMap.getValue().getLastName() + "," + entryMap.getValue().getFirstName());
            System.out.println("\t\t"+ df.format(entryMap.getValue().getSalary()));
        }
        System.out.println();
    }     
} 
