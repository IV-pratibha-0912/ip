import java.util.Scanner;

public class Vinux {
    public static void main(String[] args) {
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
        //Level-3: create array to track if tasks are done (true = done, false = not done)
        boolean[] isDone = new boolean[100];
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
                    System.out.println("    Why do you have so many things to do?");
                    System.out.println("    These are your tasks:");
                    for (int i = 0; i < taskCount; i++) {
                        //Level-3: display [X] if done, [] if not done
                        String status = isDone[i] ? "[X]" : "[ ]";
                        System.out.println("    " + (i + 1) + "." + status + tasks[i]);
                    }
                } else if (input.startsWith("mark ")) { //when user marks a task as done
                    //Level-3: mark a task as done
                    //parseInt --> convert string (e.g. "2" to 2)
                    //gives everything from position 5  (e.g. "mark 2" --> 2 is at position 5)
                    int taskNumber = Integer.parseInt(input.substring(5)) - 1; //get task number
                    isDone[taskNumber] = true; //set condition as true
                    System.out.println("    Solid! This task is now done (FINALLY!):");
                    System.out.println("        [X] " + tasks[taskNumber]);
                } else if (input.startsWith("unmark ")) { //when user marks task as undone
                    //Level-3: mark a task as undone
                    //parseInt --> convert string (e.g. "2" to 2)
                    //gives everything from position 7 (e.g. "unmark 2" --> 2 is at position 5)
                    int taskNumber = Integer.parseInt(input.substring(7)) - 1;
                    isDone[taskNumber] = false; //set condition as false
                    System.out.println("    Aw man! This task is still not done:");
                    System.out.println("        [ ] " + tasks[taskNumber]);
                } else {
                    //store the task in the array
                    tasks[taskCount] = input;
                    isDone[taskCount] = false; //new tasks start as not done
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


