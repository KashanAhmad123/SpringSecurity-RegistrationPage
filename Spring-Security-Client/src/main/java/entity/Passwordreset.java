package entity;

import java.util.Calendar;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Passwordreset {
	
	private static final int exp_T=10;
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private long id;
	private String token;
	private Date expTime;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_id",
			nullable=false,
			foreignKey=@ForeignKey(name="FK_USER_VERIFY_TOKEN"))
	private User user;
	
	public Passwordreset(User user,String token) {
		super();
		this.token= token;
		this.user= user;
		this.expTime= calculateExpTime(exp_T);
	}
	
	private Date calculateExpTime(int expTime) {
		Calendar cal= Calendar.getInstance();
		cal.setTimeInMillis(new Date().getTime());
		cal.add(Calendar.MINUTE, expTime);
		return new Date(cal.getTime().getTime());
		
	

}
