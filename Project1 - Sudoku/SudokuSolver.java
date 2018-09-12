/*Stanley Ibo
* Comp 282 - Tues/Thurs
* Assignment #1
* 09/11/2018
* Contains a Spot class that is used to navigate to a specific location on 
* the board and a sudoku class that contains the methods to create and solve the puzzle.
*/

class Spot {
	private int row, col;

	public Spot(int row, int col) {
		setRow(row);
		setCol(col);
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

}

class sudoku {
	private int board[][];

	// Default constructor
	public sudoku() {
		board = new int[9][9];
	}

	// Construct a new sudoku puzzle from a string
	public sudoku(String s[]) {
		// Initialize board
		board = new int[s.length][s.length];

		for (int row = 0; row < s.length; row++) {
			for (int col = 0; col < s.length; col++) {
				board[row][col] = (int) (s[row].charAt(col + col / 3)) - 48;
			}
		}
	}

	// Copy Constructor
	public sudoku(sudoku p) {
		// Initialize board
		board = new int[p.board.length][p.board.length];

		for (int row = 0; row < p.board.length; row++) {
			for (int col = 0; col < p.board.length; col++) {
				board[row][col] = p.board[row][col];
			}
		}

	}

  //Formatting for when printing the sudoku class
	public String toString() {
		String result = new String();

		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {

				// Divides box columns
				if (col == 3 || col == 6) {
					result = result + " | ";
				}

				// Brings row down to next line
				if (col == 0) {
					result = result + "\n";
				}

				// Divides box rows
				if (row == 3 && col == 0 || row == 6 && col == 0) {
					result = result + "---------------\n";

				}

				result = result + String.valueOf(board[row][col]);
			}
		}

		return result;
	}

	// Checks answers
	public String toString2() {
		String result = new String();
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				result = result + String.valueOf(board[row][col]);
			}
		}
		return result;
	}

	// Rotates Puzzle
	public void rotate() {
		int[][] temp = new int[9][9];
		int row, col;

		for (row = 0; row < 9; row++) {
			for (col = 0; col < 9; col++) {
				temp[col][8 - row] = board[row][col];
			}
		}

		for (row = 0; row < 9; row++) {
			for (col = 0; col < 9; col++) {
				board[row][col] = temp[row][col];
			}
		}
	}

	// Checks if the board satisfies sudoku rules
	public boolean isValid() {
		boolean rowValid = true;
		boolean colValid = true;
		boolean boxValid = true;
		boolean valid = false;

		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {

				int counter = 0;

				// If statement checks if there is a non-zero value in the spot.
				// New boards are invalid without this
				if (board[row][col] != 0) {
					int val = board[row][col];

					// Checks the row for duplicates by going right 1 col each loop
					for (int i = col + 1; i < 9; i++) {
						if (board[row][i] == val) {
							rowValid = false;
						}
					}

					// Checks the col for duplicates by going down 1 row each loop
					for (int j = row + 1; j < 9; j++) {
						if (board[j][col] == val) {
							colValid = false;
						}
					}

					// Counts how many times val appears in the box
					for (int rowBox = (row / 3) * 3; rowBox < ((row / 3) * 3) + 3; rowBox++) {
						for (int colBox = (col / 3) * 3; colBox < ((col / 3) * 3) + 3; colBox++) {
							if (board[rowBox][colBox] == val) {
								counter++;
							}

						}
					}
					if (counter > 1) {
						boxValid = false;
					}

				}
			}
		}
		if (rowValid == true && colValid == true && boxValid == true) {
			valid = true;
		} else {
			valid = false;
		}

		return valid;

	}

	// Checks if the sudoku is solved
	public boolean isComplete() {
		boolean isItComplete = true;

		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				if (board[row][col] == 0) {
					isItComplete = false;
				}
			}
		}
		return isItComplete;
	}

	// Returns true if the val appears in the row
	private boolean doesRowContain(int row, int val) {
		boolean contain = false;

		for (int col = 0; col < 9; col++) {
			if (this.board[row][col] == val) {
				contain = true;
			}
		}
		return contain;
	}

	// Returns true if the val appears in the col
	private boolean doesColContain(int col, int val) {
		boolean contain = false;

		for (int row = 0; row < 9; row++) {
			if (this.board[row][col] == val) {
				contain = true;
			}
		}

		return contain;
	}

	// Returns true if the val appears in the box
	private boolean doesBoxContain(int row, int col, int val) {
		boolean contain = false;

		for (int rowBox = (row / 3) * 3; rowBox < ((row / 3) * 3) + 3; rowBox++) {
			for (int colBox = (col / 3) * 3; colBox < ((col / 3) * 3) + 3; colBox++) {
				if (board[rowBox][colBox] == val) {
					contain = true;
					;
				}
			}
		}

		return contain;
	}

	// Counts how many numbers can go into a spot. If there's only one, sets the
	// spot to that value.
	// Returns 0 or a valid value for the spot.
	private int fillSpot(Spot sq) {

		int counter = 0;
		int spotVal = 0;

		for (int val = 1; val <= 9; val++) {

			// This if statement validates that spot is empty with a 0
			if (board[sq.getRow()][sq.getCol()] == 0) {
				if (doesRowContain(sq.getRow(), val) == false && doesColContain(sq.getCol(), val) == false
						&& doesBoxContain(sq.getRow(), sq.getCol(), val) == false) {

					counter++;
					spotVal = val;

				}
			}

		}

		if (counter != 1) {
			spotVal = 0;
		}
		return spotVal;
	}

	// Counts how many valid columns in the row a number can be entered.
	// Returns null or the spot where the value was entered.
	private Spot rowFill(int row, int val) {
		Spot spot = new Spot(0, 0);
		spot.setRow(row);
		int counter = 0;

		for (int col = 0; col < 9; col++) {
			if (board[row][col] == 0) {
				if (doesRowContain(row, val) == false && doesColContain(col, val) == false
						&& doesBoxContain(row, col, val) == false) {
					spot.setCol(col);
					counter++;
				}
			}
		}
		if (counter != 1) {
			spot = null;
		}

		return spot;

	}

	// Counts how many valid row in the column a number can be entered.
	// Returns null or the spot where the value was entered.
	private Spot colFill(int col, int val) {
		Spot spot = new Spot(0, 0);
		spot.setCol(col);
		int counter = 0;

		for (int row = 0; row < 9; row++) {
			if (board[row][col] == 0) {
				if (doesRowContain(row, val) == false && doesColContain(col, val) == false
						&& doesBoxContain(row, col, val) == false) {
					spot.setRow(row);
					counter++;
				}
			}

		}
		if (counter != 1) {
			spot = null;
		}

		return spot;
	}

	// Counts how many times a number can go into a box.
	// Returns null or the spot where the value was entered.
	private Spot boxFill(int rowbox, int colbox, int val) {
		Spot spot = new Spot(0, 0);
		int counter = 0;

		for (int row = (rowbox / 3) * 3; row < ((rowbox / 3) * 3) + 3; row++) {
			for (int col = (colbox / 3) * 3; col < ((colbox / 3) * 3) + 3; col++) {
				if (board[row][col] == 0) {
					if (doesRowContain(row, val) == false && doesColContain(col, val) == false
							&& doesBoxContain(row, col, val) == false) {
						spot.setRow(row);
						spot.setCol(col);
						counter++;
					}
				}
			}
		}
		if (counter != 1) {
			spot = null;
		}

		return spot;
	}

	// Runs all 4 methods while the board is not complete or loop has gone 81 times.
	public void solve() {
		boolean soFarSoGood = true;
		while (isComplete() == false && soFarSoGood == true) {
			int inputCounter = 0;

			for (int row = 0; row < 9; row++) {
				for (int val = 1; val <= 9; val++) {
					Spot goodSpot = rowFill(row, val);
					if (goodSpot != null) {
						board[goodSpot.getRow()][goodSpot.getCol()] = val;
						inputCounter++;
					}
				}
			}

			for (int col = 0; col < 9; col++) {
				for (int val = 1; val <= 9; val++) {
					Spot goodSpot = colFill(col, val);
					if (goodSpot != null) {
						board[goodSpot.getRow()][goodSpot.getCol()] = val;
						inputCounter++;
					}
				}
			}
			for (int row = 0; row < 9; row++) {
				for (int col = 0; col < 9; col++) {
					for (int val = 1; val <= 9; val++) {
						Spot goodSpot = boxFill(row, col, val);
						if (goodSpot != null) {
							board[goodSpot.getRow()][goodSpot.getCol()] = val;
							inputCounter++;
						}
					}
				}
			}

			Spot spot = new Spot(0, 0);
			for (int row = 0; row < 9; row++) {
				for (int col = 0; col < 9; col++) {
					spot.setRow(row);
					spot.setCol(col);
					int goodVal = fillSpot(spot);
					if (goodVal != 0) {
						board[spot.getRow()][spot.getCol()] = goodVal;
						inputCounter++;
					}
				}
			}

			if (inputCounter == 0) {
				soFarSoGood = false;
			}
		}
	}

	public static String myName() {
		return "Stanley Ibo";
	}
}
