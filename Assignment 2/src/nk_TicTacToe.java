/* This class implements all the methods needed by algorithm computerPlay */
public class nk_TicTacToe
{
	// instance variables
	private char[][] gameBoard;
	private int board_size;
	private int inline;
	private int max_levels;
	
	
	/* Constructor for nk_TicTacToe class. board_size specifies size of the board, 
	 * inline is the number of symbols in a row to win, max_levels is the max
	 * level of the game tree. */
	public nk_TicTacToe (int board_size, int inline, int max_levels)
	{
		// initialize instance variables
		this.board_size = board_size;
		this.inline = inline;
		this.max_levels = max_levels;
		this.gameBoard = new char[board_size][board_size];
		
		// create game board
		for (int i = 0; i < board_size; i++)
			for (int j = 0; j < board_size; j++)
				gameBoard[i][j] = ' ';
		
	}
	
	
	/* Creates an empty dictionary of size 7993 */
	public Dictionary createDictionary() 
	{
		return new Dictionary(7993);
	}
	
	/* This method first represents the content of gameBoard as a string
	 * If it is in the dictionary this method returns its associated score,
	 * otherwise it returns the value -1  */
	public int repeatedConfig (Dictionary configurations)
	{
		// get string configuration
		String config = concatenateBoardConfig();
		return configurations.get(config);
	}
	
	/* This method first represents the content of gameBoard as a string as described above; then it 
	 * inserts this string and score in the configurations dictionary */
	public void insertConfig(Dictionary configurations, int score) 
	{
		String config = concatenateBoardConfig();	// concatenate current board config
		Record pair = new Record(config, score);	// create a Record object
		configurations.insert(pair);				// insert Record obj into dict
	}
	
	/* Stores symbol in gameBoard[row][col]. */
	public void storePlay(int row, int col, char symbol) 
	{
		gameBoard[row][col] = symbol;
	}
	
	/* Returns true if gameBoard[row][col] is ’ ’; 
	 * otherwise it returns false. */
	public boolean squareIsEmpty(int row, int col)
	{
		if (gameBoard[row][col] == ' ')
			return true;
		else
			return false;
	}
	
	/* Returns true if there are k adjacent occurrences of symbol in the same row, column, or diagonal
	 * of gameBoard, where k is the number of required symbols in-line needed to win the game. */
	public boolean wins(char symbol) 
	{
		int row=0, col=0, diagonal, inline=0;		// row and col and diagonal positions, count of inline symbols
		int pos, k = board_size;				// number of required symbols inline to win
		
		// case 1: horizontal win
		for (int i = 0; i < board_size; i++)
			for (int j = 0; j < board_size; j++)
			{
				// reset count if row changes
				if(i != row)
				{
					inline = 0;
					row++;
				}
				// reset count if symbol is different
				if (symbol != gameBoard[i][j])
					inline = 0;
				else // increment count
					inline++;
				// if inline count reaches k, count as win
				if (inline == this.inline)
					return true;
			}

		// case 2: vertical win
		inline = 0;
		for (int i = 0; i < board_size; i++)
			for (int j = 0; j < board_size; j++)
			{
				// reset count if col changes
				if (i != col)
				{
					inline = 0;
					col++;
				}
				//reset count if symbol is different
				if (symbol != gameBoard[j][i])
					inline = 0;
				else // increment count
					inline++;
				// if inline count reaches k, count as win
				if (inline == this.inline)
					return true;
			}
		
		// case 3: diagonal win	
		int diagCol, diagRow;
		// case 3.1: start from top left and search diagonally to the bottom right
		for (diagRow = 0; diagRow < board_size; diagRow++)
		{	inline = 0;			// reset inline count
			for (diagCol = 0; diagCol < board_size - (k-1); diagCol++)		// board_size - (k-1) = diagonals that can fit k
			{	inline = 0;		// reset inline count
				for (int i = diagRow, j = diagCol; i < board_size - diagCol; i++,j++) 
				{
					if (symbol != gameBoard[i][j])
						inline = 0;
					else
						inline++;
					if (inline == this.inline)
						return true;
				}
			}
		}
		
		// case 3.2: start from top right and search diagonally to the bottom left
		for (diagRow = 0; diagRow < board_size; diagRow++)
		{	inline = 0;
			for (diagCol = board_size - 1; diagCol >= board_size - k; diagCol--) 
			{	inline = 0;	
				for (int i = diagRow, j = diagCol; i <= diagCol ; i++,j--)
				{
					if (symbol != gameBoard[i][j])
						inline = 0;
					else
						inline++;
					if (inline == k)
						return true;
				}
			}
		}
		
		return false;	// no win found
	}
	
	/* Returns true if gameBoard has no empty positions left and no player has won */
	public boolean isDraw() 
	{
		boolean xWin = wins('X'), oWin = wins('O');		// check if there has been a win
		boolean draw = true;		// flag
		
		// search board for any empty space
		for (int i = 0; i < board_size; i++)
			for (int j = 0; j < board_size; j++)
			{	// if space found
				if (gameBoard[i][j] == ' ')
					draw = false;	// set draw flag to false
			}
		
		// if no win and draw true, then game is a draw
		if (draw && !xWin && !oWin)
			return true;
		else
			return false;	// not a draw, still space to play
		
	}
	
	/* Returns a score based on board configuration */
	public int evalBoard()
	{
		if (wins('O'))			// comp wins
			return 3;
		else if (isDraw())		// draw
			return 2;
		else if (wins('X'))		// human wins
			return 0;
		else	// game undecided
			return 1;
	}
	
	/* Helper method to concatenate the current board configuration */
	private String concatenateBoardConfig()
	{
		String boardConfig = "";
		// concatenate current board configuration
		for (int i = 0; i < board_size; i++)
			for (int j = 0; j < board_size; j++)
				boardConfig += gameBoard[i][j];
		return boardConfig;
	}
	
	
	
	
}
