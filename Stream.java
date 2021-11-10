import java.util.Arrays;
import java.util.Collections;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Stream {
	public static void main(String[] args) {
		System.out.println("Hello world");
		Employee dev1 = new Employee("John", "Doe", "Developer", 110);
		dev1.setSkills("C#", "ASP.NET", "React", "AngularJS");
		Employee dev2 = new Employee("Peter", "Doe", "Developer", 120);
		dev2.setSkills("Java", "MongoDB", "Dropwizard", "Chef");
		Employee dev3 = new Employee("John", "Smith", "Developer", 115);
		dev3.setSkills("Java", "JSP", "GlassFish", "MySql");
		Employee dev4 = new Employee("Brad", "Johston", "Developer", 100);
		dev4.setSkills("C#", "MSSQL", "Entity Framework");
		Employee dev5 = new Employee("Philip", "Branson", "Developer", 140);
		dev5.setSkills("JavaScript", "React", "AngularJS", "NodeJS");
		Employee dev6 = new Employee("Nathaniel", "Barth", "Developer", 99);
		dev6.setSkills("Java", "Dropwizard");
		Employee qa1 = new Employee("Ronald", "Wynn", "QA", 100);
		qa1.setSkills("Selenium", "C#", "Java");
		Employee qa2 = new Employee("Erich", "Kohn", "QA", 105);
		qa2.setSkills("Selenium", "JavaScript", "Protractor");
		Employee devOps1 = new Employee("Harold", "Jess", "DEVOPS", 116);
		devOps1.setSkills("CentOS", "bash", "c", "puppet", "chef", "Ansible");
		Employee devOps2 = new Employee("Karl", "Madsen", "DEVOPS", 123);
		devOps2.setSkills("Ubuntu", "bash", "Python", "chef");

		List<Employee> empList = Arrays.asList(dev1, dev2, dev3, dev4, dev5, dev6,
					qa1, qa2, devOps1, devOps2);
		//*.FlatMap
		System.out.println(gatherEmployeeSkills(empList));
		//*.ForEach
		empList.forEach(e->System.out.println(e.getSalary()));
		//*.Collectors.joining
		System.out.println(printEmployeeSkills(empList,"DEVOPS"));
		//*.groupingBy
		System.out.println(employeesCountPerPosition(empList));
		//*.
		System.out.println(employeesWithDuplicateFirstName(empList));
		//*.filter
		System.out.println(filter(empList));
		//* summarizingInt
		System.out.println(summarizingInt(empList));
		//*.joining
		System.out.println(joining(empList));
		//*.toMap
		System.out.println(toMap(empList));
		//*.collecterOf
		System.out.println(collecterOf(empList));
		
		
		
		
	}
	//IntSummaryStatistics{count=10, sum=1128, min=99, average=112.800000, max=140}
	public static IntSummaryStatistics summarizingInt(List<Employee> employees) {
		IntSummaryStatistics salSummary =
				employees
			        .stream()
			        .collect(Collectors.summarizingInt(p -> p.getSalary()));

			System.out.println(salSummary);
			return salSummary;
	}
	
	
	
	public static List<Employee> filter(List<Employee> employees) {
		List<Employee> filtered =
				employees
			        .stream()
			        .filter(p -> p.getFirstName().startsWith("P"))
			        .collect(Collectors.toList());
		return filtered;
	}
	

		
	public static List<Employee> employeesWithDuplicateFirstName(
			List<Employee> employees) {
		return employees.stream()
			.collect(Collectors.groupingBy(Employee::getFirstName,
							Collectors.toList()))
			.entrySet().stream()
			.filter(entry -> entry.getValue().size() > 1)
			.flatMap(entry -> entry.getValue().stream())
			.collect(Collectors.toList());
	}
	
	public static Map<String, Long> employeesCountPerPosition(
			List<Employee> employees) {
	return employees.stream()
		.collect(Collectors.groupingBy(Employee::getPosition,
						Collectors.counting()));
}
	
	public static List<String> gatherEmployeeSkills(
			List<Employee> employees) {
		
		
		return employees == null ? Collections.emptyList()
			: employees.stream()
				.filter(employee 
					-> employees.contains(employee))
				.flatMap(employee -> employee.getSkills().stream())
				.distinct()
				.collect(Collectors.toList());
	}
	
	public static String printEmployeeSkills(
			List<Employee> employees,String position) {
		List<String> skills = gatherEmployeeSkills(employees);
		return skills.stream()
			.collect(Collectors.joining("; ",
				"Our " + position + "s have: ", " skills"));
	}
	
	public static String joining(
			List<Employee> employees) {
		String phrase = employees
			    .stream()
			    .filter(p -> p.getSalary() >= 50)
			    .map(p -> p.getFirstName())
			    .collect(Collectors.joining(" and ", "In Germany ", " are getting high salary"));
		return phrase;
	}
	
	public static Map toMap(
			List<Employee> employees) {
		Map<Integer, String> map = employees
			    .stream()
			    .collect(Collectors.toMap(
			        p -> p.getSalary(),
			        p -> p.getFirstName(),
			        (name1, name2) -> name1 + ";" + name2));
		return map;
	}
	
	public static String collecterOf(
			List<Employee> employees) {
		Collector<Employee, StringJoiner, String> personNameCollector =
			    Collector.of(
			        () -> new StringJoiner(" | "),          // supplier
			        (j, p) -> j.add(p.getFirstName().toUpperCase()),  // accumulator
			        (j1, j2) -> j1.merge(j2),               // combiner
			        StringJoiner::toString);                // finisher

			String names = employees
			    .stream()
			    .collect(personNameCollector);
		return names;
	}
	
}
