import java.util.Scanner;

public class Vinux {
    public static void main(String[] args) {
        //Level-0: Rename, Greet, Exit
        //logo of the chatbox: VINUX
        String logo = "           ________   __                         \n"
                + "|\\      /| \\__  __/  ( (    /| |\\      /| |\\      /|\n"
                + "| )    ( |    ) (    |  \\  ( | | )    ( | ( \\    / )\n"
                + "| |    | |    | |    |   \\ | | | |    | |  \\ (__) /\n"
                + "( (    ) )    | |    | (\\ \\) | | |    | |   ) __ (\n"
                + " \\ \\__/ /     | |    | | \\   | | |    | |  / (  ) \\ \n"
                + "  \\    /   ___) (___ | )  \\  | | (____) | ( /    \\ )\n"
                + "   \\__/    \\_______/ |/    )_) (________) |/      \\|\n";

        //print welcome message to user
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println(logo);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Hello! I am your favourite assistant Vinux.");
        System.out.println("I'm listening, unfortunately.");
        System.out.println("____________________________________________________________");


        //Level-1: Echo
        //initialize scanner to read user inout from the console
        Scanner scanner = new Scanner(System.in);
        String input = ""; //user input

        //continuously read and echo user input until "bye" is entered
        while (!input.equals("bye")) {
            input = scanner.nextLine(); //read input as long as condition is true

            //echo the input back to the user unless they typed "bye"
            if (!input.equals("bye")) {
                System.out.println("____________________________________________________________");
                System.out.println("    " + input); //print out user input (echo)
                System.out.println("____________________________________________________________");
            }
        }

        //print goodbye message and exit (from Level-0)
        System.out.println("Bye. Try not to miss me too much ;)");
        System.out.println("||~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~||");
        System.out.println("||~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~||");

        scanner.close(); //always close the scanner
    }
}


