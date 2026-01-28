import java.util.Scanner;

public class Vinux {
    public static void main(String[] args) {
        //Level-0: Rename, Greet, Exit
        //logo of the chatbox: VINUX
        String logo = "              ________   __                         \n"
                + "   |\\      /| \\__  __/  ( (    /| |\\      /| |\\      /|\n"
                + "   | )    ( |    ) (    |  \\  ( | | )    ( | ( \\    / )\n"
                + "   | |    | |    | |    |   \\ | | | |    | |  \\ (__) /\n"
                + "   ( (    ) )    | |    | (\\ \\) | | |    | |   ) __ (\n"
                + "    \\ \\__/ /     | |    | | \\   | | |    | |  / (  ) \\ \n"
                + "     \\    /   ___) (___ | )  \\  | | (____) | ( /    \\ )\n"
                + "      \\__/    \\_______/ |/    )_) (________) |/      \\|\n";

        //print welcome message to user
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println(logo);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Hello! I am your favourite assistant Vinux.");
        System.out.println("I'm listening, unfortunately. Go on.");
        System.out.println("    ____________________________________________________________");

        //Level-2: create array to store tasks (max 100 tasks)
        String[] tasks = new String[100];
        int taskCount = 0; //counter to keep track of how many tasks are stored

        //initialize scanner to read user input from the console
        Scanner scanner = new Scanner(System.in);
        String input = ""; //user input

        //continuously read and echo user input until "bye" is entered
        while (!input.equals("bye")) {
            input = scanner.nextLine(); //read input as long as condition is true

            //process the input unless the user typed "bye"
            if (!input.equals("bye")) {
                System.out.println("    ____________________________________________________________");

                //Level-2: check if user wants to see the list
                if (input.equals("list")) {
                    //display all stored tasks
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println("    " + (i + 1) + "." + tasks[i]);
                    }
                } else {
                    //store the task in the array
                    tasks[taskCount] = input;
                    taskCount ++;
                    System.out.println("    added: " + input);
                }
                System.out.println("    ____________________________________________________________");
            }
        }

        //print goodbye message and exit (from Level-0)
        System.out.println("Bye. Try not to miss me too much ;)");
        System.out.println("||~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~||");
        System.out.println("||~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~||");

        scanner.close(); //always close the scanner
    }
}


