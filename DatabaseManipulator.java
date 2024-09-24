import java.util.*;
import java.text.*;

public class DatabaseManipulator {

  /////////////////////////////////////////////////////////////
  // SELECT EXAMPLE
  /////////////////////////////////////////////////////////////
  public void selectExample()
  {
	  AccountDao dao = new AccountDao();
	  
	  ArrayList<Object> list = dao.selectAll();
	  
	  for (int i=0; i<list.size(); i++)
	  {
		  System.out.println((Account) list.get(i));
	  }
  }

 /////////////////////////////////////////////////////////////
 // INSERT EXAMPLE
 /////////////////////////////////////////////////////////////
 public void insertExample()
 {
  SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");

  try {
    Date dob = dateFormatter.parse("04/05/1975");
	  Account account = new Account("Becky", "Dugan", dob,
								  "123 Main Street", "Wayland", "MA", "01778", 
								  "7194455202", "bdugan@stonehill.edu", "bdugan01778",false);

    AccountDao dao = new AccountDao();
	  dao.insert(account);
  } catch (ParseException e) {
    System.out.println("Invalid date format! Please enter the date in mm/dd/yyyy format.");
  }

 }

 /////////////////////////////////////////////////////////////
 // UPDATE EXAMPLE
 /////////////////////////////////////////////////////////////
 public void updateExample()
 {
	 AccountDao dao = new AccountDao();
	 Account account = dao.selectByEmail("bdugan@stonehill.edu");
	 account.password = "bdugan";
	 dao.update(account);
 }

/////////////////////////////////////////////////////////////
// DELETE EXAMPLE
/////////////////////////////////////////////////////////////
 public void deleteExample()
 {
	 AccountDao dao = new AccountDao();
	 Account account = dao.selectByEmail("bdugan@stonehill.edu");
	 dao.delete(account.email,account.password);
 }

}

