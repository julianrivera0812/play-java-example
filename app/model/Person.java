package model;

public class Person {

	private long identification;

	private String firstname;

	private String lastname;

	public Person() {
	}

	public Person(long identification, String firstname, String lastname) {
		super();
		this.identification = identification;
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public long getIdentification() {
		return identification;
	}

	public void setIdentification(long identification) {
		this.identification = identification;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Person [identification=").append(identification).append(", firstname=").append(firstname)
				.append(", lastname=").append(lastname).append("]");
		return builder.toString();
	}
}
