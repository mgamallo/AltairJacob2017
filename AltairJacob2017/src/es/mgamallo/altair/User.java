package es.mgamallo.altair;
class User {
	private String username;
	private String password;

	public User(String user, String pass) {
		this.username = user;
		this.password = pass;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
}
