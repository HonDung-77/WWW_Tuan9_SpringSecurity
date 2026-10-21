package iuh.fit.jdbc_23639641_phamthihongdung.controller;

import iuh.fit.jdbc_23639641_phamthihongdung.model.Employee;
import iuh.fit.jdbc_23639641_phamthihongdung.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // Trang chủ danh sách: Trả về file templates/employees/list.html
    @GetMapping
    public String listEmployees(Model model) {
        model.addAttribute("employees", employeeService.findAll());
        model.addAttribute("departments", employeeService.findAllDepartments());
        model.addAttribute("positions", employeeService.findAllPositions());
        return "employees/list";
    }

    // Form thêm nhân viên mới
    @GetMapping("/new")
    public String showNewForm(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("departments", employeeService.findAllDepartments());
        model.addAttribute("positions", employeeService.findAllPositions());
        return "employees/form";
    }

    // Lưu nhân viên (Thêm mới / Cập nhật)
    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") Employee employee, RedirectAttributes redirect) {
        employeeService.save(employee);
        redirect.addFlashAttribute("message", "Lưu thông tin nhân viên thành công!");
        return "redirect:/employees";
    }

    // Form cập nhật nhân viên
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        Employee emp = employeeService.findById(id);
        if (emp == null) {
            return "redirect:/employees";
        }
        model.addAttribute("employee", emp);
        model.addAttribute("departments", employeeService.findAllDepartments());
        model.addAttribute("positions", employeeService.findAllPositions());
        return "employees/form";
    }

    // Xóa nhân viên (Chỉ ADMIN)
    @PostMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id, RedirectAttributes redirect) {
        try {
            employeeService.deleteById(id);
            redirect.addFlashAttribute("message", "Đã xóa thành công nhân viên ID " + id);
        } catch (Exception e) {
            redirect.addFlashAttribute("message", "Không thể xóa nhân viên ID " + id + " do ràng buộc dữ liệu!");
        }
        return "redirect:/employees";
    }

    // --- CÁC ROUTE TÌM KIẾM DỮ LIỆU ---

    @GetMapping("/search/id")
    public String searchById(@RequestParam("id") Integer id, Model model) {
        Employee emp = employeeService.findById(id);
        List<Employee> list = (emp != null) ? List.of(emp) : Collections.emptyList();
        model.addAttribute("employees", list);
        return "employees/list";
    }

    @GetMapping("/search/name")
    public String searchByName(@RequestParam("name") String name, Model model) {
        model.addAttribute("employees", employeeService.findByName(name));
        return "employees/list";
    }

    @GetMapping("/search/age")
    public String searchByAge(@RequestParam("a") int a, @RequestParam("b") int b, Model model) {
        model.addAttribute("employees", employeeService.findByAge(a, b));
        return "employees/list";
    }

    @GetMapping("/search/salary")
    public String searchBySalary(@RequestParam("salary") double salary, Model model) {
        model.addAttribute("employees", employeeService.findBySalary(salary));
        return "employees/list";
    }

    @GetMapping("/search/position")
    public String searchByPosition(@RequestParam("posId") Integer posId, Model model) {
        model.addAttribute("employees", employeeService.findByPosition(posId));
        return "employees/list";
    }

    @GetMapping("/search/dept-pos")
    public String searchByDeptAndPos(@RequestParam("deptId") Integer deptId, @RequestParam("posId") Integer posId, Model model) {
        model.addAttribute("employees", employeeService.findByDeptAndPos(deptId, posId));
        return "employees/list";
    }

    @GetMapping("/top-salary")
    public String topSalary(@RequestParam("deptId") Integer deptId, @RequestParam("n") int n, Model model) {
        model.addAttribute("employees", employeeService.findTopNByDept(deptId, n));
        return "employees/list";
    }

    // --- TRANG THỐNG KÊ BÁO CÁO ---

    @GetMapping("/reports")
    public String reports(Model model) {
        model.addAttribute("stats", employeeService.getDepartmentStats());
        model.addAttribute("emptyDepartments", employeeService.findDepartmentsWithNoEmployees());
        return "employees/reports";
    }
}