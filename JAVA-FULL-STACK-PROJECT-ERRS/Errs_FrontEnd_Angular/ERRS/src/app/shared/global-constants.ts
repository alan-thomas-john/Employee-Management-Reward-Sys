export class GlobalConstants{

    //message if something goes wrong
    public static genericError:string = "Something went wrong.Please try again later";

    public static unauthorized:string = "You are not authorized person to access this page.";

    public static rewarExistError:string= "Reward already exist";

    public static rewardAdded:string = "Reward added successfully";

    //regex
    public static nameRegex:string = "[a-zA-Z0-9 ]*";

    public static emailRegex:string = "[A-Za-z0-9._%-]+@gmail\\.com";

    public static contactNumberRegex:string = "^[e0-9]{10,10}$";

    public static pointsRegex: string = "^[0-9]+$"; // Regex for non-negative integers

    public static quantityAvailableRegex: string = "^[0-9]+$"; // Regex for non-negative integers

    public static recognitionNameregex: string = "^[a-zA-Z ]+$"



    //variable
    public static error:string = "error";


}