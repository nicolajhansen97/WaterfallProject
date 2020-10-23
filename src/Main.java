import java.util.ArrayList;
import java.util.Scanner;

/**
 * Waterwork project 2020 week 43
 *
 * @author Nicolaj, Niels, Christopher, Fei
 * @since 23-10-2020
 */

public class Main {

    static int sentinel = 0;

    public static void main(String[] args) {

        int waterMenu;

        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("*-----------------------------------------------------*");
            System.out.println("|            WaterWork administration system          |");
            System.out.println("*-----------------------------------------------------*");
            System.out.println("|Press 1. to handle the reading cards                 |");
            System.out.println("|Press 2. to open ........                            |");
            System.out.println("|Press 3. to open ........                            |");
            System.out.println("|Press 4. to open ........                            |");
            System.out.println("|Press 5. to open customer modification               |");
            System.out.println("|Press 6. to open ........                            |");
            System.out.println("|                                                     |");
            System.out.println("|      If you want to exit the system press 9         |");
            System.out.println("*-----------------------------------------------------*");
            if (in.hasNextInt()) {
                waterMenu = in.nextInt();
            } else {
                System.out.println("The input you used is not valid.\nPlease try again!");
                in.next();
                continue;
            }


            if (waterMenu < 10 && waterMenu >= 1) {

                if (waterMenu == 1) {
                    handlingReadingCardsSubMenu();
                } else if (waterMenu == 2) {
                    System.out.println("This part is still under development");
                } else if (waterMenu == 3) {
                    System.out.println("This part is still under development");
                } else if (waterMenu == 4) {
                    System.out.println("This part is still under development");
                } else if (waterMenu == 5) {
                    customerModification();
                } else if (waterMenu == 6) {
                    System.out.println("This part is still under development");
                }


            } else {
                System.out.println("The input you used is not valid.\nPlease try again!");
            }

            if (waterMenu == 9) {
                break;
            }
        }
    }

    //The menu from which you access the other methods working with reading cards.
    public static void handlingReadingCardsSubMenu() {

        Scanner in = new Scanner(System.in);

        int countMenu = 0;

        while (true) {
            System.out.println("*---------------------------------------------*");
            System.out.println("|  WaterWork reading card administration      |");
            System.out.println("*---------------------------------------------*");
            System.out.println("|Press 1. to enter reading cards              |");
            System.out.println("|Press 2. to send reading cards               |");
            System.out.println("|Press 3. to see not returned reading cards   |");
            System.out.println("|                                             |");
            System.out.println("| If you want to go to the main menu press 0  |");
            System.out.println("*---------------------------------------------*");
            String menu = in.next(); //Choose what menu option you want
            if (menu.equals("1")) {
                readingCard(countMenu); //Add more reading card data to DB
                countMenu++;
            } else if (menu.equals("2")) {
                requestReadingCard(); //Send reading cards to amount of people in db
            } else if (menu.equals("0")) {
                break;
            } else if (menu.equals("3")) {
                checkMissingReadingCards();
            } else if (!menu.equals("1") && !menu.equals("2") && !menu.equals("3")) {
                System.out.println("The input you used is not valid.\nPlease try again!");
            }
        }
    }

    //This method is for enterring the reading card info into the system. This method doesnøt really return anything, but it works.
    public static int readingCard(int countReadingCard) {
        Scanner in = new Scanner(System.in);
        boolean repeat = true;
        int isRecieved = 1;


        do {
            System.out.println("Input the Serial-number of the water-meter the reading is about");
            String serialNo = in.next();
            System.out.println("Input the water consumption amount from the reading card");
            double waterUsed = in.nextDouble();
            System.out.println("Input the drainage amount from the reading card");
            double waterDrainage = in.nextDouble();

            DB.updateSQL("UPDATE tblReading SET fldDrainage = '" + waterDrainage + "', fldWaterUSED = '" + waterUsed + "', fldIsRecieved = " + isRecieved + " WHERE fldSerialNO ='" + serialNo + "' AND fldIsRecieved = '0'");

            System.out.println("*--------------------------------------------------*");
            System.out.println("|The following data have been saved to the database|");
            System.out.println("|                                                  |");
            System.out.println("|Water used:                        " + waterUsed + " m^3      ");
            System.out.println("|Water drainage:                    " + waterDrainage + " m^3  ");
            System.out.println("|To the water-meter with Serial-NO: " + serialNo);
            System.out.println("*--------------------------------------------------*");
            System.out.println("|Press 1. to add additional reading card data      |");
            System.out.println("|                                                  |");
            System.out.println("|Press 0. to exit to reading card administration   |"); //You can actually exit with any key
            System.out.println("*--------------------------------------------------*");
            String con = in.next();
            if (!con.equals("1")) {
                repeat = false;
            }

        } while (repeat);
        System.out.println("All data is saved, you will be transferred to the reading card menu");
        return 0;

    }

    //This function will send out reading cards to each water meter.
    public static void requestReadingCard() {
        Scanner in = new Scanner(System.in);

        while (true) {
            System.out.println("*------------------------------------------*");
            System.out.println("|       Reading card send out menu         |");
            System.out.println("*------------------------------------------*");
            System.out.println("|Press 1. to send out a single reading card|");
            System.out.println("|Press 2. to send reading cards to everyone|");
            System.out.println("|                                          |");
            System.out.println("|Press 0. to go to the reading card menu   |");
            System.out.println("*------------------------------------------*");

            String menuOption = in.next();

            if (!menuOption.equals("1") && !menuOption.equals("2")) {
                break;
            }

            while (true) {

                if (menuOption.equals("1")) {
                    System.out.println("Please input the Serial-NO of the water-meter you want to send out the reading card too");
                    String serialNo = in.next();

                    DB.insertSQL("Insert into tblReading (fldSerialNO, fldDrainage, fldWaterUsed, fldIsRecieved) values('" + serialNo + "',NULL,NULL,0)");

                    System.out.println("*------------------------------------------------------*");
                    System.out.println("|The reading card was sent to the customer successfully|");
                    System.out.println("*------------------------------------------------------*");
                    System.out.println("|Press 1. if you want to send out more                 |");
                    System.out.println("|Press 2. if you want to go to the menu                |");
                    System.out.println("*------------------------------------------------------*");
                    menuOption = in.next();
                }

                if (!menuOption.equals("1") && !menuOption.equals("2")) {
                    break;
                }

                if (menuOption.equals("2")) {
                    DB.selectSQL("Select Count(*) from tblWatermeter");
                    int amount = Integer.parseInt(DB.getData());

                    //This loop makes it so that each serial number in the database will get a new reading card. Using the amount of rows in the serail numbers as the amount of iterations we have
                    for (int i = 1; i <= amount; i++) {
                        DB.selectSQL("SELECT fldSerialNO FROM (select ROW_NUMBER() over (order by fldSerialNo) as RowNum,* FROM tblWatermeter) tblWatermeter WHERE RowNum = " + i);
                        String serialNo = DB.getData();
                        DB.getData(); //This will get all data, as the provided database code will get no more data before it got all.
                        DB.insertSQL("Insert into tblReading (fldSerialNO, fldDrainage, fldWaterUsed, fldIsRecieved) values('" + serialNo + "',NULL,NULL,0)");


                    }
                    System.out.println("All the readings cards have been sent to customers. You will be transported to the menu");
                    break;
                }
            }
        }

    }

    //Check missing reading will check for a boolean value in the database that will only by true if the reading card info has been input
    public static void checkMissingReadingCards() {

        Scanner in = new Scanner(System.in);
        DB.selectSQL("Select fldSerialNO from tblReading WHERE fldIsRecieved = '0'");

        //This part will enter the serial number string into an array that can get bigger as more data is fit in
        ArrayList serialNumbers = new ArrayList();
        do {
            String serialNumb = DB.getData();
            if (serialNumb.equals(DB.NOMOREDATA)) {
                break;
            } else {
                serialNumbers.add(serialNumb);
            }
        } while (true);

        System.out.println("*--------------------**-----------------------**------------------**-----------**--------------------*");
        System.out.println("|CPR-Number          || Name                  ||Address           ||  Zip-code || Serial-Number      |");
        System.out.println("*--------------------**-----------------------**------------------**-----------**--------------------*");

        //Here we read the serial number that has been put into the array serialNumbers. We then read each and find the costumer data and print it out.
        for (int i = 0; i < serialNumbers.size(); i++) {
            DB.selectSQL("Select fldCprOrCvrNo, fldName, fldAdress, fldZipCode from tblCustomers WHERE fldCprOrCvrNo =(SELECT fldCprOrCvrNo FROM tblWatermeter WHERE fldSerialNO = '" + serialNumbers.get(i) + "')");
            String CprOrCvrNo = DB.getData();
            String name = DB.getData();
            String address = DB.getData();
            String zipCode = DB.getData();

            System.out.println(CprOrCvrNo + "           " + name + "          " + address + "          " + zipCode + "          " + serialNumbers.get(i));
            System.out.println("*---------------------------------------------------------------------------------------------------*");
        }
        System.out.println("*-------------------------------------------------------------------------------*");
        System.out.println("|Do you want to send a fee to the customer and a worker to check the watermeter?|");
        System.out.println("*-------------------------------------------------------------------------------*");
        System.out.println("|Press 1. for yes                                                               |");
        System.out.println("|Press 2. for no                                                                |");
        System.out.println("*-------------------------------------------------------------------------------*");

        String yesOrNo = in.next();

        if (yesOrNo.equals("1")) {
            System.out.println("The fee was send to the customer and a message to the worker was send.");
            System.out.println("Press any key to continue");
            in.next();
        } else {
            System.out.println("The fee wasn't send out.");
            System.out.println("Press any key to continue");
            in.next();
        }

    }

    //The main menu for customer modification from here you can go into all the parts where you can edit, create, delete customers and so on.
    public static void customerModification() {
        int customerMenu = 0;

        Scanner in = new Scanner(System.in);
        boolean customerDone = false;


        while (!customerDone) {

            System.out.println("*-----------------------------------------------------*");
            System.out.println("|            WaterWork customer administration        |");
            System.out.println("*-----------------------------------------------------*");
            System.out.println("|Press 1. to create a new customer                    |");
            System.out.println("|Press 2. to open update a customers information      |");
            System.out.println("|Press 3. to open delete a customer                   |");
            System.out.println("|Press 4. to see the list of all customers            |");
            System.out.println("|                                                     |");
            System.out.println("|       If you want to go the main menu press 0       |");
            System.out.println("*-----------------------------------------------------*");

            if (in.hasNextInt()) {
                customerMenu = in.nextInt();
            } else {
                System.out.println("The input you used is not valid.\nPlease try again!");
                in.next();
                continue;
            }
            if (customerMenu == 1) {
                customerCreation();
            } else if (customerMenu == 2) {
                updateCustomerSubMenu();
            } else if (customerMenu == 3) {
                deleteCustomer();
            } else if (customerMenu == 4) {
                listCustomer();
            } else if (customerMenu == sentinel) {
                customerDone = true;
            }
        }
    }

    //A menu to navigate around in the menu about updating their information
    public static void customerCreation() {
        Scanner in = new Scanner(System.in);


        System.out.println("Please enter the CPR/CVR-number of the person/company you want to add");
        String cprOrCvrNo = in.nextLine();
        System.out.println("Please enter the name of the person/company you want to add");
        String name = in.nextLine();
        System.out.println("Please enter the address of the person/company you want to add");
        String address = in.nextLine();
        System.out.println("Please enter the Zip-Code of the person/company you want to add");
        String zipCode = in.nextLine();
        System.out.println("Please enter the e-mail of the person/company you want to add");
        String email = in.nextLine();
        System.out.println("Please enter the phone-number of the person/company you want to add");
        String phoneNo = in.nextLine();
        System.out.println("Please enter the customers segment. 1. Private, 2. Industry, 3. Agriculture");
        int segmentID = in.nextInt();
        DB.insertSQL("Insert into tblCustomers values('" + cprOrCvrNo + "','" + name + "','" + address + "','" + zipCode + "','" + email + "','" + phoneNo + "'," + segmentID + ")");

        if (segmentID == 1) {
            System.out.println("Please enter the Serial-NO of the water-meter you want to the customer");
            String serialNo = in.next();
            DB.insertSQL("Insert into tblWatermeter values('" + serialNo + "','" + cprOrCvrNo + "')");

        }
        if (segmentID == 2) {
            System.out.println("The industry can have up to 5 water-meters, how many do you want to add?");
            String amount = in.next();
            if (Integer.parseInt(amount) <= 5 && Integer.parseInt(amount) > 0) {
                for (int i = 0; i < Integer.parseInt(amount); i++) {

                    System.out.println("Please enter the Serial-NO of the water-meter you want to the customer");
                    String serialNo = in.next();
                    DB.insertSQL("Insert into tblWatermeter values('" + serialNo + "','" + cprOrCvrNo + "')");

                }
            }
        }

        if (segmentID == 3) {
            System.out.println("The agriculture can have up to 3 water-meters, how many do you want to add?");
            String amount = in.next();
            if (Integer.parseInt(amount) <= 3 && Integer.parseInt(amount) > 0) {
                for (int i = 0; i < Integer.parseInt(amount); i++) {

                    System.out.println("Please enter the Serial-NO of the water-meter you want to the customer");
                    String serialNo = in.next();
                    DB.insertSQL("Insert into tblWatermeter values('" + serialNo + "','" + cprOrCvrNo + "')");


                }

            }
        }
        if (segmentID > 4 && segmentID < 0) {
            System.out.println("This input was not valid, you will be redirected to the menu");
        }


        System.out.println("*----------------------------------------------*");
        System.out.println("|The information has been added to the database|");
        System.out.println("*----------------------------------------------*");
        System.out.println("Press any key to continue");
        in.hasNext();


    }

    //The Sub-menu for all customers edit
    public static void updateCustomerSubMenu() {
        Scanner in = new Scanner(System.in);

        while (true) {

            System.out.println("*--------------------------------------------------*");
            System.out.println("|                Edit information menu             |");
            System.out.println("*--------------------------------------------------*");
            System.out.println("|Press 1. to edit the customers name               |");
            System.out.println("|Press 2. to edit the customers address            |");
            System.out.println("|Press 3. to edit the customers e-mail             |");
            System.out.println("|Press 4. to edit the customers phone-number       |");
            System.out.println("|Press 5. to edit all the information of a customer|");
            System.out.println("|                                                  |");
            System.out.println("|Press 0. to go to the menu                        |");
            System.out.println("*--------------------------------------------------*");

            boolean getOut = false;
            int menu;
            if (in.hasNextInt()) {
                menu = in.nextInt();
            } else {
                System.out.println("The input you used is not valid.\nTry again!");
                in.next();
                continue;
            }

            switch (menu) {

                case 1:
                    updateCustomerName();
                    break;

                case 2:
                    updateCustomerAddress();
                    break;

                case 3:
                    updateCustomerEmail();
                    break;

                case 4:
                    updatePhoneNumber();
                    break;

                case 5:
                    editAllInformation();
                    break;

                default:
                    getOut = true;


            }

            if (getOut) {
                break;
            }


        }
    }

    //This part that updates the customers name (Part of updateCustomerSubMenu)
    public static void updateCustomerName() {
        Scanner in = new Scanner(System.in);

        System.out.println("Enter the CPR/CVR-Number of the person you want to edit all information of");
        if (in.hasNextLong()) {
            Long cprOrCvrNo = in.nextLong();

            String takeIt = in.nextLine();
            System.out.println("Please enter the new name of the person/company");
            String name = in.nextLine();

            DB.updateSQL("UPDATE tblCustomers SET fldName = '" + name + "' WHERE fldCprOrCvrNo ='" + cprOrCvrNo + "'");

            System.out.println("This information has been updated.");
            System.out.println("Press any key to continue");
            in.next();
        } else {
            System.out.println("This ID cant be found in the database.\nPlease try again!");
            in.next();
        }
    }

    //This part that updates the customers address (Part of updateCustomerSubMenu)
    public static void updateCustomerAddress() {
        Scanner in = new Scanner(System.in);

        System.out.println("Enter the CPR/CVR-Number of the person you want to edit all information of");
        if (in.hasNextLong()) {
            Long cprOrCvrNo = in.nextLong();

            String takeIt = in.nextLine(); //This part prevents that it dosent jump over the next part. Other vice it will just skip the PhoneNumber
            System.out.println("Please enter the new address of the person/company");
            String address = in.nextLine();
            System.out.println("Please enter the Zip-Code of the new address");
            String zipCode = in.next();

            DB.updateSQL("UPDATE tblCustomers SET fldAdress = '" + address + "', fldZipCode = '" + zipCode + "' WHERE fldCprOrCvrNo ='" + cprOrCvrNo + "'");

            System.out.println("This information has been updated.");
            System.out.println("Press any key to continue");
            in.next();
        } else {
            System.out.println("This ID cant be found in the database.\nPlease try again!");
            in.next();
        }
    }

    //This part that updates the customers E-mail (Part of updateCustomerSubMenu)
    public static void updateCustomerEmail() {
        Scanner in = new Scanner(System.in);

        System.out.println("Enter the CPR/CVR-Number of the person you want to edit all information of");
        if (in.hasNextLong()) {
            Long cprOrCvrNo = in.nextLong();

            String takeIt = in.nextLine(); //This part prevents that it dosent jump over the next part. Other vice it will just skip the PhoneNumber
            System.out.println("Please enter the new E-mail of the person/company");
            String email = in.nextLine();

            DB.updateSQL("UPDATE tblCustomers SET fldEmail = '" + email + "' WHERE fldCprOrCvrNo ='" + cprOrCvrNo + "'");

            System.out.println("This information has been updated.");
            System.out.println("Press any key to continue");
            in.next();
        } else {
            System.out.println("This ID cant be found in the database.\nPlease try again!");
            in.next();
        }
    }

    //This part that updates the customers Phone-number (Part of updateCustomerSubMenu)
    public static void updatePhoneNumber() {
        Scanner in = new Scanner(System.in);

        System.out.println("Enter the CPR/CVR-Number of the person you want to edit all information of");
        if (in.hasNextLong()) {
            Long cprOrCvrNo = in.nextLong();

            String takeIt = in.nextLine(); //This part prevents that it dosent jump over the next part. Other vice it will just skip the PhoneNumber
            System.out.println("Please enter the new phone-number of the person/company");
            String phoneNumber = in.nextLine();

            DB.updateSQL("UPDATE tblCustomers SET fldPhone = '" + phoneNumber + "' WHERE fldCprOrCvrNo ='" + cprOrCvrNo + "'");

            System.out.println("This information has been updated.");
            System.out.println("Press any key to continue");
            in.next();
        } else {
            System.out.println("This ID cant be found in the database.\nPlease try again!");
            in.next();
        }
    }

    //This part that can update every information of the customer at one time (Part of updateCustomerSubMenu) It's only used in a few situations where alot info is changed.
    public static void editAllInformation() {
        Scanner in = new Scanner(System.in);

        System.out.println("Enter the CPR/CVR-Number of the person you want to edit all information of");
        if (in.hasNextLong()) {
            Long cprOrCvrNo = in.nextLong();

            String takeIt = in.nextLine();
            System.out.println("Please enter the new name of the person/company");
            String name = in.nextLine();
            System.out.println("Please enter the new address of the person/company");
            String address = in.nextLine();
            System.out.println("Please enter the new Zip-Code of the person/company");
            String zipCode = in.nextLine();
            System.out.println("Please enter the new e-mail of the person/company");
            String email = in.nextLine();
            System.out.println("Please enter the new phone-number of the person/company");
            String phoneNo = in.nextLine();

            DB.updateSQL("UPDATE tblCustomers SET fldName = '" + name + "', fldAdress = '" + address + "', fldZipCode = '" + zipCode + "', fldEmail = '" + email + "', fldPhone = '" + phoneNo + "' WHERE fldCprOrCvrNo ='" + cprOrCvrNo + "'");

            System.out.println("This information has been updated.");
            System.out.println("Press any key to continue");
            in.next();
        } else {
            System.out.println("This ID cant be found in the database.\nPlease try again!");
            in.next();
        }

    }

    /*This part that delete a customer. Its a easy way with just the CPR/CVR-number are the secretary able to delete a customer from the system.
      It will also delete all the water-meters and readings made under that CPR/CVR-number as the customer is deleted. */
    public static void deleteCustomer() {

        Scanner in = new Scanner(System.in);

        System.out.println("Please input the CPR/CVR-number of the customer you want to delete");
        String cprOrCvrNo = in.next();

        System.out.println("Are you sure that you want to delete the customer with CPR/CVR-number: " + cprOrCvrNo);
        System.out.println("Press 1. for yes");
        System.out.println("Press 2. for no");
        String sureOrNot = in.next();


        if (sureOrNot.equals("1")) {

            DB.selectSQL("Select fldSerialNO from tblWatermeter where fldCprOrCvrNo = '" + cprOrCvrNo + "';");
            String serialNo = String.valueOf(DB.getData());
            DB.getData(); //To get null data too as the db file isn't taking it automaticly
            DB.pendingData = false; //Clear connection statement

            //Its deleting over different SQL queries as it need to delete in the right order, to not messing up the FK.
            DB.deleteSQL("Delete from tblReading where fldSerialNO ='" + serialNo + "';");
            DB.deleteSQL("Delete from tblWatermeter where fldCprOrCvrNo = '" + cprOrCvrNo + "';");
            DB.deleteSQL("Delete from tblCustomers where fldCprOrCvrNo = '" + cprOrCvrNo + "';");
            System.out.println("The customer with CPR/CVR-Number: " + cprOrCvrNo + " was deleted.");
            System.out.println("Press any key to continue.");
            in.next();


        } else if (!sureOrNot.equals("1")) {
            System.out.println("The customer wasn't deleted, you've been redirected to the menu");

        }
    }

    /* This part that show the list of all customers. It's a pretty simple list just formatted and getting all the data about the customers.
       It's made to make a overview to the secretary to get specific information about users. We was also thinking about making it so you could only search for one customer
       but the time limited us.*/

    public static void listCustomer() {

        Scanner in = new Scanner(System.in);
        System.out.println("*-------------**-------------------------------**-------------------------------**-----------**-------------------------------**------------ -----**-----------**--------*");
        System.out.println("|CPR-Number   || Name                          ||Adress                         || Zip-code  || E-mail                        || Phone            ||Segment ID || USER ID|");
        System.out.println("*-------------**-------------------------------**-------------------------------**-----------**-------------------------------**------------------**-----------**--------*");
        DB.selectSQL("Select * from tblCustomers");

        do {
            String data = DB.getDisplayData();
            if (data.equals(DB.NOMOREDATA)) {
                break;
            } else {

                //The part below is a workaround of the DB code, as we didnt want to edit the code.
                //It is removing the new line at the end of the row in the DB so it looks nice printed out.
                if (data.contains("\n")) {
                    data = data.replace("\n", "");
                    System.out.print("|" + data + "|");
                    System.out.println();
                } else {
                    System.out.print("|" + data + "|");
                }


            }
        } while (true);
        System.out.println("*-------------**-------------------------------**-------------------------------**-----------**-------------------------------**------------------**-----------**--------*");
        System.out.println("Press 0 to go to the main menu");
        in.next();
    }
}

