// Leap Year Program

public class LeapYear {
	public static void main(String[] args) {
		int year = 2100;
		leapYear(year);
	}
	public static void leapYear(int year) {
		if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) {
			System.out.print(year);
			System.out.println(" is a leap year.");}
		else {
			System.out.print(year);
			System.out.println(" is not a leap year.");
		}
	}
}