package beans;

import java.io.Serializable;

public class MessageClient implements Serializable{
	private String username;
	private String password;
	private String type;
	public MessageClient(){};
	public MessageClient(String username, String password, String type,
			String message, String recipient) {
		super();
		this.username = username;
		this.password = password;
		this.type = type;
		this.message = message;
		this.recipient = recipient;
	}
	private String message;
	private String recipient;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
}
