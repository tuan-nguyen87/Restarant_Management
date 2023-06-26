import java.util.Scanner;
import Chef.Chef;
import pos.POS;
import Server.Server;

class Main{
    public static Scanner scan=new Scanner(System.in);

    public static void main(String[] args){
        mainMenu_App();
        System.out.println("Exiting App");
    }

    // Prints the main menu of the application
    static void mainMenu_App(){
        Chef chef = new Chef();
        Server server = new Server();
        POS pos = new POS();
        pos.readMenu();
        boolean finished = false;
      
        String menu="1) Chef View\n2) Server View\n3) POS View\n4) Exit";
        while(!finished){
            System.out.println(menu);
            System.out.print("Input: ");
            int input=scan.nextInt();
            switch(input){
                case 1:
                    finished = chef.mainMenu_Chef(scan);
                    break;
                case 2:
                    finished = server.mainMenu_Server(scan);
                    break;
                case 3:
                    finished = pos.mainMenu_POS(scan);
                    break;
                case 4:
                    finished=true;
            }
            System.out.println("-----------------------------------------------------");
        }
        scan.close();
    }

    // Prints the main menu of the Server View
    static boolean mainMenu_Server(){
        return false;
    }

    // Prints the main menu of the POS
    static boolean mainMenu_POS(){
        return false;
    }
}
