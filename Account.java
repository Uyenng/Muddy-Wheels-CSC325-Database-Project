import java.util.*;
public class Account
{
	public String fname;
	public String lname;
  public Date dob;
	public String street;
	public String city;
	public String state;
	public String email;
	public String ZIP;
	public String phone;
	public String password;
	public boolean admin;
  public int ticketID;
	
	public Account()
	{
		fname=null;
		lname=null;
    dob=null;
		street=null;
		city=null;
		state=null;
		email=null;
		ZIP=null;
		phone=null;
		password = null;
		admin = false;
	}

  //Create an account
	public Account(String fname, String lname, Date dob,
				   String address, String city, String state, String zip, 
				   String phone, String email, String password, boolean admin)
	{
		this.fname = fname;
    this.lname = lname;
    this.dob = dob;
    this.street = address;
    this.city = city;
    this.state = state;
    this.ZIP = zip;
    this.phone = phone;
    this.email = email;
		this.password = password;
		this.admin = admin;
	}

  // Forgot what this constructor is for...
	public Account(String fname, String lname, Date dob,
				   String address, String city, String state, String zip, 
				   String phone, String email, String password)
	{
		this.fname = fname;
    this.lname = lname;
    this.dob = dob;
    this.street = address;
    this.city = city;
    this.state = state;
    this.ZIP = zip;
    this.phone = phone;
    this.email = email;
		this.password = password;
		this.admin = admin;
	}

  //No password return
	public Account(String fname, String lname, Date dob,
				   String address, String city, String state, String zip, 
				   String phone, String email)
	{
		this.fname = fname;
    this.lname = lname;
    this.dob = dob;
    this.street = address;
    this.city = city;
    this.state = state;
    this.ZIP = zip;
    this.phone = phone;
    this.email = email;
	}
	
	/* getters and setters for the attributes */
	public Date getDOB() 
	{
        return dob;
    }

	public void setDOB(Date dob) 
	{
        this.dob = dob;
    }
	
	public String getPassword() 
	{
        return password;
    }

	public void setPassword(String newPassword) 
	{
        this.password = newPassword;
    }

	public String getStreet() 
	{
        return street;
    }

	public void setStreet(String newStreet) 
	{
        this.street = newStreet;
    }

	public String getCity() 
	{
        return city;
    }

	public void setCity(String city) 
	{
        this.city = city;
    }

	public String getState() 
	{
        return state;
    }

	public void setState(String state) 
	{
        this.state = state;
    }

	public String getZIP() 
	{
        return ZIP;
    }

	public void setZIP(String ZIP) 
	{
        this.ZIP = ZIP;
    }

	public String getEmail() 
	{
        return email;
    }

	public void setEmail(String newEmail) 
	{
        this.email = newEmail;
    }

	public String getPhone() 
	{
        return phone;
    }

	public void setPhone(String phone) 
	{
        this.phone = phone;
    }

	public String getLastName() 
	{
        return lname;
    }

	public void setLastName(String lname) 
	{
        this.lname = lname;
    }

  public String getFirstName() 
	{
    return fname;
  }

	public void setFirstName(String fname) 
	{
    this.fname = fname;
  }

  public int getTicketID()
  {
    return ticketID;
  }

  public void setTicketID(int ticketId)
  {
    this.ticketID = ticketId;
  }

  public boolean isAdmin() 
	{
    return admin;
  }

	public void setAdmin(boolean admin) 
	{
    this.admin = admin;
  }

	public String toString()
	{
		return ("First name: " + fname + "\nLast name: " + lname + 
        "\nDOB: " + dob + 
        "\nAddress: " + street + ", " + city +  
        "\nState: " + state + 
        "\nZIP: " + ZIP + 
				"\nPhone: " + phone + 
				"\nEmail: " + email + 
        //"\nAdministrator? " + admin + //always false
        "\n");
	}

  public String toString1()
	{
		return ("First name: " + fname + "\nLast name: " + lname + 
        "\nDOB: " + dob + 
        "\nAddress: " + street + ", " + city +  
        "\nState: " + state + 
        "\nZIP: " + ZIP + 
				"\nPhone: " + phone + 
				"\nEmail: " + email + "\n");
	}
}

