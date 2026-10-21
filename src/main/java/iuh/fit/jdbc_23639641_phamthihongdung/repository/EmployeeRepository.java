package iuh.fit.jdbc_23639641_phamthihongdung.repository;

import iuh.fit.jdbc_23639641_phamthihongdung.model.Employee;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

    @Override
    List<Employee> findAll(); // Xem danh sách[cite: 4]

    // 1. Tim theo name: findByNameContainingIgnoreCase(name)[cite: 4]
    List<Employee> findByNameContainingIgnoreCase(String name);

    // 2. Tim theo age: findByAgeBetween(a, b)[cite: 4]
    List<Employee> findByAgeBetween(int a, int b);

    // 3. Tim theo salary: findBySalaryGreaterThan(s)[cite: 4]
    List<Employee> findBySalaryGreaterThan(double salary);

    // 4. Danh sach Employee theo Position ID: findByPositionId(id)[cite: 4]
    List<Employee> findByPositionId(Integer positionId);

    // 5. Danh sach Employee theo Department ID va Position ID: findByDepartmentIdAndPositionId(d, p)[cite: 4]
    List<Employee> findByDepartmentIdAndPositionId(Integer departmentId, Integer positionId);

    // 6. Top N Employee luong cao nhat trong mot Department: findTop5ByDepartmentIdOrderBySalaryDesc(id)[cite: 4]
    @Query("SELECT * FROM employees WHERE department_id = :deptId ORDER BY salary DESC LIMIT :n")
    List<Employee> findTopNByDepartmentIdOrderBySalaryDesc(@Param("deptId") Integer deptId, @Param("n") int n);
}