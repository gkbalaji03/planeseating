package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TestSeating {
	
	private static final String INVALID_INPUT="Invalid input, please provide a positive integer input again.";
	private static final int WINDOW_SEAT=-10;
	private static final int AISLE_SEAT=-20;
	private static final int MIDDLE_SEAT=-10;
	private static final int INVALID_SEAT=-1;

	/**
	 * Method gets a valid input from the user
	 * @param message that needs to be prompted to the user
	 * @return number input by the user post validation
	 */
	public static int getInt(String message) {
		int number;
		Scanner scanner = new Scanner(System.in);
		do {
            System.out.print(message);
            while (!scanner.hasNextInt()) {
                scanner.next();
                System.out.print(INVALID_INPUT+"\n" +message);
            }
            number = scanner.nextInt();
        } while (number < 1);
		
		return number;
	}

	public static void main(String[] args) {

		int arr[][] = null;
		try {
			int m;
			int n;
			
			//Get the 2D input array dimensions eg. 4 * 2
			m = getInt("Enter the number of rows of the array input :: ");
			n = getInt("Enter the number of columns of the array input (Need to be 2):: ");
			arr = new int[m][n];

			
			//Get the 2D input array values eg. {{3,2}, {4,3}, {2,3}, {3,4}};
			System.out.println("**Enter the elements of the array***");
			System.out.println("eg. {{3,2}, {4,3}, {2,3}, {3,4}}");
			for (int i = 0; i < m; i++) {
				for (int j = 0; j < n; j++) {
					int val = getInt("Enter the 2D input for arr["+i+"]["+j+"]");
					arr[i][j] = val;
				}
			}

			//Get the passenger count
			int passengerCount = getInt("Enter the passenger count ");

			
			
			System.out.println("************Execution begins***********");

			int columnsList[] = new int[arr.length];
			int rowsList[] = new int[arr.length];

			/**
			 * Split the 2D array into 2 column wise
			 * eg.{{3,2}, {4,3}, {2,3}, {3,4}}
			 * columnsList will hold 3, 4, 2, 3
			 * rowsList will hold 2, 3, 3, 4
			 * Next, frame the 2D array dimension for the plane's seating layout
			 * planeColumnsCount = sum of elements in columnsList
			 * planeRowsCount = greatest of the elements in rowsList
			 */
			int planeColumnsCount = 0;
			int planeRowsCount = 0;
			for (int i = 0; i < arr.length; i++) {
				columnsList[i] = arr[i][0];
				planeColumnsCount = planeColumnsCount + arr[i][0]; 
				rowsList[i] = arr[i][1];
				if (planeRowsCount < arr[i][1]) {
					planeRowsCount = arr[i][1];
				}
			}

			System.out.println("planeColumnsCount: " + planeColumnsCount);
			System.out.println("planeRowsCount: " + planeRowsCount);

			/**
			 * 
			 * VALID/INVALID SEAT IDENTIFICATION:
			 * Until the count of every element in the column list, 
			 * take its corresponding element value in the row list
			 * and identify the valid/invalid seats. 
			 * eg. {3, 2} For first three columns - 0, 1, 2, there are
			 * valid seats for first two rows - 0, 1
			 * For remaining rows of the planes 2D, mark it as invalid seats
			 * {4, 3} - For next four columns - 3, 4, 5, 6, there are
			 * valid seats for first three rows - 0, 1, 2
			 * 
			 * WINDOW SEAT IDENTIFICATION:
			 * Any seat where the column index value is 0 or column length - 1,
			 * mark it as window seat
			 * 
			 * AISLE SEAT IDENTIFICATION:
			 * Mark the columns at the beginning and end of every set of columns
			 * mentioned in the columnList other than the window seat
			 * 
			 * MIDDLE SEAT IDENTIFICATION:
			 * Mark all other seats as middle seat
			 * 
			 */
			int planeArr[][] = new int[planeRowsCount][planeColumnsCount];
			int yCounter = 0;
			// for(Integer eachCol : columnsList) { //eachCol =4
			for (int bb = 0; bb < columnsList.length; bb++) {// eachCol =4
				int eachCol = columnsList[bb];
				for (int j = 0; j < eachCol; j++) { // 0, 1, 2, 3
					for (int i = 0; i < planeRowsCount; i++) { // i is always 0, 1, 2, 3
						// int eachColIndex = columnsList.indexOf(eachCol);
						int eachRow = rowsList[bb]; // 3
						// int xx = 0;
						if (i < eachRow) { // 0,1,2
							if (yCounter == 0 || yCounter == planeColumnsCount - 1) { // yCounter=0
								planeArr[i][yCounter] = WINDOW_SEAT; // window
							} else if (j == 0 || j == eachCol - 1) {
								planeArr[i][yCounter] = AISLE_SEAT; // this is aisleseat
							}
							// xx++;
							else {
								// MiddleSeats
								planeArr[i][yCounter] = MIDDLE_SEAT;
							}
						} else {
							// handle invalid seats
							planeArr[i][yCounter] = INVALID_SEAT; // invalid seats
						}
					}
					yCounter++;
				}
			}

			
			
			/**
			 * Sorting logic for filling up the passengers based on the seatType
			 * Search the 2D matrix and assign the passengerNumber based on the 
			 * seattype order 
			 *  
			 */
			int passengerNumber = 1;

			List<Integer> seatTypes = new ArrayList<Integer>(Arrays.asList(AISLE_SEAT, WINDOW_SEAT, MIDDLE_SEAT));

			for (Integer seatType : seatTypes) {
				if (passengerNumber <= passengerCount) {
					for (int i = 0; i < planeRowsCount; i++) {
						if (passengerNumber <= passengerCount) {
							for (int j = 0; j < planeColumnsCount; j++) {
								if (passengerNumber <= passengerCount) {
									if (planeArr[i][j] != INVALID_SEAT && planeArr[i][j] == seatType) {
										planeArr[i][j] = passengerNumber;
										passengerNumber++;
									}
								} else
									break;
							}
						} else
							break;

					}
				} else
					break;
			}

			
			/**
			 * Print the final result
			 */
			for (int i = 0; i < planeRowsCount; i++) {
				for (int j = 0; j < planeColumnsCount; j++) {
					System.out.printf("%5d", planeArr[i][j]);
				}
				System.out.println();
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
	}
}
