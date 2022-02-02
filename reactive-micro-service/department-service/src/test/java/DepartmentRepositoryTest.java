/*import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.util.Assert;

import com.prs.services.department.model.Department;
import com.prs.services.department.repository.DepartmentRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DepartmentRepositoryTest {

    private static DepartmentRepository repository = new DepartmentRepository();

    @Test
    public void testAddDepartment() {
        Department department = new Department(1L, "Test");
        Mono<Department> mono = repository.add(department);
        mono.subscribe(d-> {
        	Assert.notNull(d, "Department is null.");
        	Assert.isTrue(d.getId() == 1L, "Department bad id.");
        });
    }

    @Test
    public void testFindAll() {
        Flux<Department> departments = repository.findAll();
        departments.collectList().subscribe(list -> {
        	Assert.isTrue(list.size() == 1, "Departments size is wrong.");
        	Assert.isTrue(list.get(0).getId() == 1L, "Department bad id.");
        });
    }

    @Test
    public void testFindByCollege() {
        Flux<Department> departments = repository.findByCollege(1L);
        departments.collectList().subscribe(list -> {
        	Assert.isTrue(list.size() == 1, "Departments size is wrong.");
        	Assert.isTrue(list.get(0).getId() == 1L, "Department bad id.");
        });
    }

    @Test
    public void testFindById() {
        Mono<Department> mono = repository.findById(1L);
        mono.subscribe(department-> {
        	Assert.notNull(department, "Department not found.");
        	Assert.isTrue(department.getId() == 1L, "Department bad id.");
        });
    }

}
*/