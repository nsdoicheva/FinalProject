package iSiktir;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;

public abstract class BasicInfo {
	private int id;
	private double sum;
	private Date date;

	public BasicInfo(double sum) {
		if (sum >= 0) {
			this.sum = sum;
		}
		this.date = new Date((new java.util.Date()).getTime());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getSum() {
		return sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}

	public Date getDate() {
		return date;
	}

}
