
public class Palindrome {

	public static void main(String[] args) {
		for (int i = 0; i < args.length; i++) {
			String s = args[i];
			System.out.print(s + " - ");
			if (isPalindrome(s))
				System.out.println("Палиндром");
			else
				System.out.println("Нет, не Палиндром =З");
		}
	}

	public static String reverseString(String s) {
		String rez = "";
		for (int i = s.length() - 1; i > -1; i--) {
			rez += s.charAt(i);
		}
		return rez;
	}

	public static boolean isPalindrome(String s) {
		return (s.equals(reverseString(s)));
	}
}