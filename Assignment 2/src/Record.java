/* This class represents an entry in the dictionary, associating a 
 * configuration (key) with its integer scores (data). */
public class Record {
	private String config;	/* Represents board configuration stored in this Record */
	private int score;		/* Play's score stored in this Record */
	
	/* Constructor which returns a new Record with the 
	 * specified config and score */
	public Record(String config, int score) {
		this.config = config;
		this.score = score;
	}
	
	/* Returns the configuration stored in this Record */
	public String getConfig() {
		return this.config;
	}
	
	/* Returns the score in this Record */
	public int getScore() {
		return this.score;
	}
	
}
