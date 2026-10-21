package iuh.fit.jdbc_23639641_phamthihongdung.repository;

import iuh.fit.jdbc_23639641_phamthihongdung.dto.DepartmentStatDto;
import iuh.fit.jdbc_23639641_phamthihongdung.model.Department;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Integer> {

    @Override
    List<Department> findAll();

    // Department chưa có Employee nào
    @Query("SELECT d.* FROM departments d LEFT JOIN employees e ON e.department_id = d.id WHERE e.id IS NULL")
    List<Department> findDepartmentsWithNoEmployees();

    // Thống kê: alias khớp tên thuộc tính DTO (snake_case)
    @Query("""
            SELECT d.name AS department_name,
                   COUNT(e.id) AS employee_count,
                   COALESCE(AVG(e.salary), 0) AS avg_salary,
                   COALESCE(SUM(e.salary), 0) AS total_salary
            FROM departments d
            LEFT JOIN employees e ON e.department_id = d.id
            GROUP BY d.id, d.name
            ORDER BY d.id
            """)
    List<DepartmentStatDto> getDepartmentStats();
}