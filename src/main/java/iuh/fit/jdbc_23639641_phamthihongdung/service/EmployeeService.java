package iuh.fit.jdbc_23639641_phamthihongdung.service;

import iuh.fit.jdbc_23639641_phamthihongdung.dto.DepartmentStatDto;
import iuh.fit.jdbc_23639641_phamthihongdung.model.Department;
import iuh.fit.jdbc_23639641_phamthihongdung.model.Employee;
import iuh.fit.jdbc_23639641_phamthihongdung.model.Position;
import iuh.fit.jdbc_23639641_phamthihongdung.repository.DepartmentRepository;
import iuh.fit.jdbc_23639641_phamthihongdung.repository.EmployeeRepository;
import iuh.fit.jdbc_23639641_phamthihongdung.repository.PositionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final PositionRepository positionRepository;

    public EmployeeService(EmployeeRepository employeeRepository,
                           DepartmentRepository departmentRepository,
                           PositionRepository positionRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.positionRepository = positionRepository;
    }

    // ===== CRUD =====
    public List<Employee> findAll() { return employeeRepository.findAll(); }

    public Employee findById(Integer id) { return employeeRepository.findById(id).orElse(null); }

    public Employee save(Employee employee) { return employeeRepository.save(employee); }

    public void deleteById(Integer id) { employeeRepository.deleteById(id); }

    // ===== Tìm kiếm =====
    public List<Employee> findByName(String name) {
        return employeeRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Employee> findByAge(int a, int b) {
        return employeeRepository.findByAgeBetween(a, b);
    }

    public List<Employee> findBySalary(double salary) {
        return employeeRepository.findBySalaryGreaterThan(salary);
    }

    public List<Employee> findByPosition(Integer posId) {
        return employeeRepository.findByPositionId(posId);
    }

    public List<Employee> findByDeptAndPos(Integer deptId, Integer posId) {
        return employeeRepository.findByDepartmentIdAndPositionId(deptId, posId);
    }

    public List<Employee> findTopNByDept(Integer deptId, int n) {
        return employeeRepository.findTopNByDepartmentIdOrderBySalaryDesc(deptId, n);
    }

    // ===== Danh mục =====
    public List<Department> findAllDepartments() { return departmentRepository.findAll(); }

    public List<Position> findAllPositions() { return positionRepository.findAll(); }

    // ===== Quan hệ 1-n / Thống kê =====
    public List<Department> findDepartmentsWithNoEmployees() {
        return departmentRepository.findDepartmentsWithNoEmployees();
    }

    public List<DepartmentStatDto> getDepartmentStats() {
        return departmentRepository.getDepartmentStats();
    }
}