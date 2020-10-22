import java.util.Scanner;

public class Main {

    static int sentinel = 0;
    public static int globalCount; //Change name to reading card counter

    public static void main(String[] args) {


        boolean done = false;
        int waterMenu = 0;


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


            if (waterMenu < 10 && waterMenu >= 0) {

                if (waterMenu == sentinel) {
                    System.out.print("If you want to get to the main menu \n   Press 0.");
                    done = true;

                } else if (waterMenu == 1) {
                    handlingReadingCardsSubMenu();
                } else if (waterMenu == 2) {
                    done = true;
                } else if (waterMenu == 3) {
                    done = true;
                } else if (waterMenu == 4) {
                    done = true;
                } else if (waterMenu == 5) {
                    customerModification();
                } else if (waterMenu == 6) {
                    done = true;
                }


            } else {
                System.out.println("The input you used is not valid.\nPlease try again!");
            }

            if (waterMenu == 9) {
                break;
            }
        }
    }


    public static int readingCard(int countReadingCard) {
        Scanner in = new Scanner(System.in);
        boolean repeat = true;
        int isRecieved = 1;


        do {
            System.out.println("Input the Serial-number of the water-meter the reading is about");
            String serialNo = in.next();
            System.out.println("Input the water consumption amount from the reading card");
            double waterDrainage = in.nextDouble();
            System.out.println("Input the drainage amount from the reading card");
            double waterUsed = in.nextDouble();

            DB.insertSQL("Insert into tblReading values('" + serialNo + "'," + waterDrainage + "," + waterUsed + "," + isRecieved + ")");


            //System.out.println("Meter number");
            //String meterSerial = in.next();


            System.out.println(waterDrainage + " " + waterUsed + " "); //Goes to database


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

            countReadingCard++;

        } while (repeat);
        System.out.println("All data is saved, you will be transfered to the reading card menu");
        globalCount = countReadingCard;
        return 0;

    }


    public static void requestReadingCard() {
        Scanner in = new Scanner(System.in);

        while(true) {
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


                    System.out.println("The reading card was sent to the customer successfully");

                    System.out.println("If you want to send out more press 1.");
                    System.out.println("If you want to go the menu press 0.");
                    menuOption = in.next();
                }

                if (!menuOption.equals("1") && !menuOption.equals("2")) {
                    break;
                }

                if (menuOption.equals("2")) {
                    DB.selectSQL("Select Count(*) from tblWatermeter");
                    int amount = Integer.parseInt(DB.getData());

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

            /*Scanner in = new Scanner(System.in);
            System.out.println("Send out reading card press 1");
            String send = in.next();
            int test = 0;

            if (send.equals("1")){
                for (int i = 0; i < globalCount; i++) {
                    test++;
                }
                System.out.println("Succefully sent to " + test + " people"); //Each card to unique user from database
            }
        }

             */

    }

    public static void handlingReadingCardsSubMenu() {

        Scanner in = new Scanner(System.in);

        int countMenu = 0;

        while (true) {
            System.out.println("*---------------------------------------------*");
            System.out.println("|  WaterWork reading card administration      |");
            System.out.println("*---------------------------------------------*");
            System.out.println("|Press 1. to enter reading cards              |");
            System.out.println("|Press 2. to send reading cards               |");
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
            } else if (!menu.equals("1") && !menu.equals("2")) {
                System.out.println("The input you used is not valid.\nPlease try again!");
            }
        }
    }


    public static void customerModification() {
        int customerMenu = 0;
        int id = 0;
        Scanner in = new Scanner(System.in);
        boolean customerDone = false;

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

        while (!customerDone) {

            if (in.hasNextInt()) {
                customerMenu = in.nextInt();

            } else {
                System.out.println("The input you used is not valid.\nPlease try again!");
                in.next();
                continue;
            }

            if (customerMenu == 1) {
                String StopIt = in.nextLine(); //This make that CprOrCvrNo is not 1 and your asked.


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

                System.out.println("The information has been added to the database");
                System.out.println("Press 1. too add more customers");
                System.out.println("Press 0. too go the main menu");


            } else if (customerMenu == 2) {
                boolean updateInformation = false;

                System.out.println("Please enter the Cpr/Cvr-Number of the person that you want to update the information about");
                while (!updateInformation) {

                    if (in.hasNextLong()) {
                        Long cprOrCvrNo = in.nextLong();
                        String StopIt = in.nextLine(); // This one is doing that it dosent skip the next part

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

                        DB.updateSQL("UPDATE tblCustomers SET fldName = '" + name + "', fldAdress = '" + address + "', fldZipCode = '" + zipCode + "', fldEmail = '" + email + "', fldPhone = '" + phoneNo + "' WHERE fldCprOrCvrNo ='" + cprOrCvrNo + "'");

                        System.out.println("This information has been updated. Press 0 to go to the main menu.");
                        updateInformation = true;
                    } else {
                        System.out.println("This ID cant be found in the database.\nPlease try again!");
                        in.next();
                        continue;
                    }

                }
            } else if (customerMenu == 3) {

            } else if (customerMenu == 4) {
                System.out.println("*-------------**-------------------------------**-------------------------------**-----------**-------------------------------**----------------**-----**");
                System.out.println("|CPR-Number   || Name                          ||Adress                         || Zip-code  || E-mail                        || Phone          || ID  ||");
                System.out.println("*-------------**-------------------------------**-------------------------------**-----------**-------------------------------**----------------**-----||");
                DB.selectSQL("Select * from tblCustomers");

                do {
                    String data = DB.getDisplayData();
                    if (data.equals(DB.NOMOREDATA)) {
                        break;
                    } else {

                        //The part below is a workaround of Tommy's DB code, as we didnt want to edit his code.
                        if (data.contains("\n")) {
                            data = data.replace("\n", "");
                            System.out.print("|" + data + "|");
                            System.out.println();
                        } else {
                            System.out.print("|" + data + "|");
                        }


                    }
                } while (true);
                System.out.println("*-------------**-------------------------------**-------------------------------**-----------**-------------------------------**----------------**-----||");
                System.out.println("\nPress 0 to go to the main menu");
            } else if (customerMenu == sentinel) {
                customerDone = true;
            }
        }
    }
}

