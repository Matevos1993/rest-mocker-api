package restmocker.backend.domain;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import restmocker.backend.domain.dto.Todo;
import restmocker.backend.domain.dto.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;

@ApplicationScoped
public class UserGenerator {

  private final TodoRepository repository;
  private final Map<String, List<User>> userData = new HashMap<>();
  private final Map<String, LocalDateTime> lastGeneration = new HashMap<>();

  @Inject
  public UserGenerator(TodoRepository repository) {
    this.repository = repository;
  }

  @Scheduled(cron = "0 0 0 * * ?")
  public void clearMemory() {

    userData.clear();
    lastGeneration.clear();
  }

  public void generateUsers(String userId, int count) {

    Random userRandom = new Random(userId.hashCode());

    LocalDateTime now = LocalDateTime.now();
    LocalDateTime lastTime = lastGeneration.get(userId);

    if (lastTime != null && lastTime.toLocalDate().isEqual(LocalDate.now()) && count == userData.get(userId).size()) {
      return;
    }

    List<User> users = new ArrayList<>();

    if (count < 1) {
      count = 1;
    } else if (count > 100) {
      count = 100;
    }

    List<String> maleNames = new ArrayList<>(Arrays.asList(
        "Aaron", "Adam", "Adrian", "Aiden", "Alan", "Albert", "Alex", "Alexander", "Alfred", "Andrew",
        "Anthony", "Arthur", "Austin", "Barry", "Benjamin", "Blake", "Bradley", "Brandon", "Brian", "Bruce",
        "Caleb", "Cameron", "Carl", "Charles", "Christian", "Christopher", "Clark", "Colin", "Connor", "Craig",
        "Daniel", "David", "Dean", "Dennis", "Derek", "Dominic", "Donald", "Douglas", "Dylan", "Edward",
        "Elijah", "Elliot", "Eric", "Ethan", "Evan", "Felix", "Finn", "Francis", "Frank", "Frederick",
        "Gavin", "George", "Gordon", "Grant", "Gregory", "Harold", "Harry", "Henry", "Hugh", "Hunter",
        "Ian", "Isaac", "Jack", "Jacob", "Jake", "James", "Jason", "Jasper", "Jeffrey", "Jeremy",
        "Jerry", "Jesse", "Joel", "John", "Jonathan", "Joseph", "Joshua", "Julian", "Justin", "Keith",
        "Kenneth", "Kevin", "Kyle", "Landon", "Lawrence", "Leon", "Leo", "Leonard", "Liam", "Logan",
        "Louis", "Lucas", "Luke", "Malcolm", "Marcus", "Mark", "Martin", "Mason", "Matthew", "Michael",
        "Mitchell", "Nathan", "Neil", "Nicholas", "Noah", "Norman", "Oliver", "Oscar", "Owen", "Patrick",
        "Paul", "Peter", "Philip", "Preston", "Quentin", "Ralph", "Raymond", "Richard", "Rick", "Robert",
        "Roger", "Ronald", "Ross", "Russell", "Ryan", "Samuel", "Scott", "Sean", "Sebastian", "Seth",
        "Shawn", "Simon", "Spencer", "Stanley", "Stephen", "Steve", "Stewart", "Terrence", "Theodore", "Thomas",
        "Timothy", "Todd", "Tom", "Travis", "Trevor", "Tristan", "Troy", "Tyler", "Victor", "Vincent"
    ));

    List<String> femaleNames = new ArrayList<>(Arrays.asList(
        "Abigail", "Ada", "Adelaide", "Adele", "Alexa", "Alexandra", "Alice", "Alicia", "Alina", "Alison",
        "Allison", "Alyssa", "Amanda", "Amber", "Amelia", "Amy", "Ana", "Andrea", "Angela", "Angelica",
        "Anna", "Annabelle", "Anne", "Annie", "April", "Ariana", "Ashley", "Audrey", "Autumn", "Ava",
        "Barbara", "Beatrice", "Bella", "Beth", "Bianca", "Brenda", "Briana", "Brianna", "Brooke", "Caitlin",
        "Camila", "Candice", "Carla", "Carmen", "Caroline", "Carrie", "Cassandra", "Catherine", "Cecilia", "Charlotte",
        "Chelsea", "Chloe", "Christina", "Claire", "Clara", "Colleen", "Courtney", "Crystal", "Cynthia", "Daisy",
        "Dana", "Daniela", "Danielle", "Daphne", "Deborah", "Delilah", "Denise", "Diana", "Diane", "Donna",
        "Doris", "Dorothy", "Eden", "Edith", "Eleanor", "Elena", "Elisa", "Elisabeth", "Elise", "Eliza",
        "Elizabeth", "Ella", "Elle", "Ellen", "Emily", "Emma", "Erica", "Erin", "Esme", "Esther",
        "Eva", "Evelyn", "Faith", "Fiona", "Florence", "Frances", "Freya", "Gabriela", "Gabriella", "Georgia",
        "Gina", "Giselle", "Gloria", "Grace", "Greta", "Gwen", "Hailey", "Hannah", "Hazel", "Heather",
        "Heidi", "Helen", "Holly", "Hope", "Irene", "Iris", "Isabel", "Isabella", "Isla", "Ivy",
        "Jacqueline", "Jade", "Jamie", "Jane", "Jasmine", "Jean", "Jenna", "Jennifer", "Jessica", "Jillian",
        "Joan", "Joanna", "Jocelyn", "Jordan", "Joy", "Judith", "Julia", "Juliana", "Julie", "June",
        "Justine", "Kaitlyn", "Karen", "Katherine", "Kathleen", "Kayla", "Kelly", "Kim", "Kimberly", "Kristen"
    ));

    List<String> surnames = new ArrayList<>(Arrays.asList(
        "Anderson", "Allen", "Adams", "Armstrong", "Alexander", "Baker", "Barnes", "Bell", "Bennett", "Black",
        "Bradley", "Brooks", "Brown", "Burke", "Burns", "Campbell", "Carter", "Chambers", "Clark", "Collins",
        "Coleman", "Cook", "Cooper", "Cox", "Craig", "Cruz", "Daniels", "Davis", "Diaz", "Dixon",
        "Douglas", "Duncan", "Edwards", "Elliott", "Ellis", "Evans", "Fisher", "Fleming", "Ford", "Foster",
        "Fox", "Francis", "Freeman", "Garcia", "Gardner", "Gibson", "Gomez", "Gonzalez", "Gordon", "Graham",
        "Grant", "Gray", "Green", "Griffin", "Hall", "Hamilton", "Hansen", "Harris", "Harrison", "Hart",
        "Harvey", "Hawkins", "Hayes", "Henderson", "Henry", "Hernandez", "Hicks", "Hill", "Holland", "Holmes",
        "Howard", "Hughes", "Hunt", "Hunter", "Jackson", "Jacobs", "James", "Jenkins", "Johnson", "Johnston",
        "Jones", "Jordan", "Kelley", "Kelly", "Kennedy", "Kim", "King", "Knight", "Lambert", "Lane",
        "Larson", "Lawrence", "Lawson", "Lee", "Lewis", "Long", "Lopez", "Marshall", "Martin", "Martinez",
        "Mason", "Matthews", "McCarthy", "McCoy", "McDonald", "McGee", "McKenzie", "Medina", "Mendez", "Miller",
        "Mitchell", "Moore", "Morales", "Morgan", "Morris", "Murphy", "Murray", "Myers", "Nelson", "Nguyen",
        "Nichols", "Norman", "Oliver", "Ortiz", "Owens", "Palmer", "Parker", "Patel", "Perez", "Perkins",
        "Perry", "Peterson", "Phillips", "Pierce", "Porter", "Powell", "Price", "Ramirez", "Reed", "Reyes",
        "Reynolds", "Richardson", "Rivera", "Roberts", "Robertson", "Robinson", "Rodriguez", "Rogers", "Ross", "Russell"
    ));

    List<String> roles = new ArrayList<>(Arrays.asList(
        "Admin", "Moderator", "Editor", "Viewer", "Contributor", "Guest", "Member", "Super Admin", "Support Agent", "Developer", "Tester", "Manager"
    ));

    for (int i = 1; i <= count; i++) {

      boolean isMale = userRandom.nextBoolean();
      String firstName = isMale ?
          maleNames.get(userRandom.nextInt(maleNames.size())) :
          femaleNames.get(userRandom.nextInt(femaleNames.size()));

      String surname = surnames.get(userRandom.nextInt(surnames.size()));
      String gender = isMale ? "male" : "female";

      int year = 1970 + userRandom.nextInt(2009 - 1970 + 1);
      int month = 1 + userRandom.nextInt(12);
      int day = 1 + userRandom.nextInt(LocalDate.of(year, month, 1).lengthOfMonth());

      LocalDate birthday = LocalDate.of(year, month, day);

      users.add(new User(i,
          firstName,
          surname,
          gender,
          birthday,
          Period.between(birthday, LocalDate.now()).getYears(),
          String.format("%s.%s@example.com", firstName.toLowerCase(), surname.toLowerCase()),
          String.format("%s.%s", firstName.toLowerCase(), surname.toLowerCase()),
          String.format("%s%d", firstName.toLowerCase(), userRandom.nextInt(1000)),
          roles.get(userRandom.nextInt(roles.size())),
          new ArrayList<>(repository.getTodosByUserId(i))));
    }

    userData.put(userId, users);
    lastGeneration.put(userId, now);
  }

  public List<User> getUsers(String userId) {
    return userData.getOrDefault(userId, new ArrayList<>());
  }

  public User getUserById(int id, String userId) {

    return userData.getOrDefault(userId, new ArrayList<>()).stream().filter(u -> u.getId() == id).findFirst().orElse(null);
  }

  public User updateUser(String userId, int id, User u) {

    User user = getUserById(id, userId);
    user.setFirstName(u.getFirstName() == null ? user.getFirstName() : u.getFirstName());
    user.setLastName(u.getLastName() == null ? user.getLastName() : u.getLastName());
    user.setGender(u.getGender() == null ? user.getGender() : u.getGender());
    user.setDateOfBirth(u.getDateOfBirth() == null ? user.getDateOfBirth() : u.getDateOfBirth());
    user.setAge(Period.between(user.getDateOfBirth(), LocalDate.now()).getYears());
    user.setEmail(u.getEmail() == null ? user.getEmail() : u.getEmail());
    user.setPassword(u.getPassword() == null ? user.getPassword() : u.getPassword());
    user.setRole(u.getRole() == null ? user.getRole() : u.getRole());

    return user;
  }

  public User updateTodo(String userId, int id, int todoId, Todo t) {

    User user = getUserById(id, userId);

    Optional<Todo> todoOpt = user.getTodos().stream()
        .filter(u -> u.getId() == todoId)
        .findFirst();

    if (todoOpt.isEmpty()) throw new NotFoundException("Todo with id " + todoId + " not found");

    Todo todo = todoOpt.get();
    todo.setCompleted(t.getCompleted() == null ? todo.getCompleted() : t.getCompleted());
    todo.setTodo(t.getTodo() == null ? todo.getTodo() : t.getTodo());
    todo.setUpdatedAt(new Date());
    return user;
  }

  public User addTodo(String userId, int id, Todo todo) {

    User user = getUserById(id, userId);

    int maxId = Math.toIntExact(getUsers(userId).stream()
        .flatMap(u -> u.getTodos().stream())
        .mapToLong(Todo::getId)
        .max()
        .orElse(0));

    todo.setUserId(user.getId());
    todo.setCreatedAt(new Date());
    todo.setId((long) maxId + 1);
    todo.setCompleted(Boolean.TRUE.equals(todo.getCompleted()));

    user.getTodos().add(todo);

    return user;
  }

  public void deleteTodosById(String userId, int id, List<Long> todoIds) {

    User user = getUserById(id, userId);

    for (Long todoId : todoIds) {
      user.getTodos().removeIf(u -> u.getId() == todoId);
    }
  }

  public boolean isUserListValid(String userId) {
    return userId != null && getUsers(userId) != null && !getUsers(userId).isEmpty();
  }

  public boolean isUserIdValid(String userId, int id) {
    return isUserListValid(userId) && id <= getUsers(userId).size();
  }
}
