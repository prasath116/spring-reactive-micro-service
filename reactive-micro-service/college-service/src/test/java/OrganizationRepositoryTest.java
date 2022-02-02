/*
 * import java.util.List;
 * 
 * import org.junit.jupiter.api.Test; import org.springframework.util.Assert;
 * 
 * import com.prs.services.college.model.College; import
 * com.prs.services.college.repository.CollegeRepository;
 * 
 * public class CollegeRepositoryTest {
 * 
 * private static CollegeRepository repository = new
 * CollegeRepository();
 * 
 * @Test public void testAddCollege() { College college = new
 * College("Test", "Test Street"); college =
 * repository.add(college); Assert.notNull(college,
 * "College is null."); Assert.isTrue(college.getId() == 1L,
 * "College bad id."); }
 * 
 * @Test public void testFindAll() { List<College> colleges =
 * repository.findAll(); Assert.isTrue(colleges.size() == 1,
 * "Colleges size is wrong."); Assert.isTrue(colleges.get(0).getId()
 * == 1L, "College bad id."); }
 * 
 * @Test public void testFindById() { College college =
 * repository.findById(1L); Assert.notNull(college,
 * "College not found."); Assert.isTrue(college.getId() == 1L,
 * "College bad id."); }
 * 
 * }
 */