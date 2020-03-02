import java.util.LinkedList;

/* This class implements a dictionary using a 
 * hash table with separate chaining */
public class Dictionary implements DictionaryADT
{
	private final int VALUE = 11;				// value used in hash function (11 seems to cause least number of collisions)
	private int size;							// size of the hash table
	private LinkedList<Record>[] hashTable;		// array of linked lists to store Record pairs
	
	/* Constructs a Dictionary object of size */
	public Dictionary(int size) 
	{	
		this.size = size;	
		this.hashTable = new LinkedList[size]; 
	}

	/* inserts the given record pair in the dictionary. Throws
	 * DictionaryException if pair.getConfig() is already in the dictionary. 
	 * Return 1 if collision, else return 0*/
	public int insert(Record pair) throws DictionaryException 
	{
		int hash = hashFunction(pair.getConfig());		// hash index to place Record pair 
		
		
		// check if null at hash index
		if (hashTable[hash] == null) 
		{
			LinkedList<Record> newList = new LinkedList<Record>();		// create list located at hash index
			hashTable[hash] = newList;								// point table to list
			newList.add(pair);									// add pair to empty Record list
			return 0;										// no collision
		}
		else 
		{
			LinkedList<Record> list = hashTable[hash];
			
			// traverse Record list and throw exception is configuration is found
			int i = 0;
			while (i < list.size())
			{
				Record temp = list.get(i);		// ith record in list
				
				// test if configurations are the same
				if (temp.getConfig().equals(pair.getConfig()))
					throw new DictionaryException();
				i++;
			}
			// add to pair to Record list
			list.add(pair);
			return 1;		// collision occurred
		}
	}

	/* Removes the entry with the given config from the dictionary. Throws a
	 * DictionaryException if the config is not in the dictionary. */
	public void remove(String config) throws DictionaryException 
	{
		// get hash index of config string
		int hash = hashFunction(config);
		// list located at hash index
		LinkedList<Record> list = hashTable[hash];
		
		// check if null at hash index
		if (hashTable[hash] == null)
			throw new DictionaryException();
		
		else	// other wise traverse the list
		{
			int i = 0;
			while (i < list.size())
			{
				Record temp = list.get(i);				// ith record in the list
				if (temp.getConfig().equals(config))
				{
					list.remove(i);
					return;
				}
				i++;
			}
			// no matching config in list
			throw new DictionaryException();
		}	
	}

	/* Returns the score stored in the dictionary for the given config, or
	 * returns -1 if the config is not in the dictionary. */
	public int get(String config) {
		int hash = hashFunction(config);
		LinkedList<Record> list = hashTable[hash];
		
		// Check if null at hash index
		if (hashTable[hash] == null) 
			return -1;
		else
		{
			int i = 0;
			while (i < list.size())
			{
				Record temp = list.get(i);				// ith record in list
				
				if (temp.getConfig().equals(config))
					return temp.getScore();
				i++;
			}
		}
		// config not in list
		return -1;
	}

	public int numElements() {
		int count = 0;				// counter for number of elements
		
		// iterate over each list in the dictionary
		int i = 0;
		while (i < size)			// loop over the hash table
		{
			LinkedList<Record> list = hashTable[i];		// ith list in the dictionary
			count += list.size();
			i++;
		}
		return count;
	}
	
	/* String hash function. returns the hash table position for str */
	private int hashFunction(String str) 
	{
		int hashVal = (int) str.charAt(0);
		
		// iterate over the board configuration string
		for (int i=1; i<str.length(); i++) 
		{
			hashVal = (hashVal * VALUE + (int) str.charAt(i)) % size;		// polynomial hash to hash each character in string
		}
		return hashVal;
	}

}
