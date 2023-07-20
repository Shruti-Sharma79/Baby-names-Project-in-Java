import edu.duke.*;

import java.util.Scanner;

import org.apache.commons.csv.*;

class Names{
    // int year;
    // public void babynames(){
    //     FileResource fr=new FileResource("yob1880s.csv");
    //     CSVParser parser=fr.getCSVParser(false);
    //     // for(CSVRecord record: parser)
    //     // {
    //     //     System.out.println(record.get(0));
    //     // }
    // }
    // private CSVRecord rec;


    //Printing total number of different girls names and total number of different boys names in the Decade 1880s
    public void TotalNum(int year){
        int countboys=0, countgirls=0;
        String fileName = "yob" + year + ".csv";
	    FileResource fr = new FileResource(fileName);
        CSVParser parser=fr.getCSVParser(false);
        for(CSVRecord record: parser){
            String gender=record.get(1);
            if(gender.equals("F")){
                countgirls++;
            }
            else{
                countboys++;
            }

        }
        System.out.println("NUMBER OF GIRLS in the year "+year+" == "+countgirls);
        System.out.println("NUMBER OF BOYS in the year "+year+"===  "+countboys);
        System.out.println("ALL CHILDREN NAMES ARE in the year "+year+" == "+(countgirls+countboys));
    }

   
    
//Method for Returning the rank of a Name
    public int getRank(int year, String name, String gender){
	    String fileName = "yob" + year + ".csv";
	    FileResource fr = new FileResource(fileName);
	    int rank = 0;
	    boolean found = false;
	    for (CSVRecord rec : fr.getCSVParser(false)) {
	         if (rec.get(1).equals(gender)){
	             if (rec.get(0).equals(name)){
	               rank += 1;//                                                                                  here<-----       
	               found = true;//                                                                                        |
	               break;//                                                                                               |
	             }//                                                                                                      |  
	             else{//                                                                                                  |
	                 rank += 1;//If the name is not found in the first row the rank keeps on increasing until it is found 
	            }
	         }
	    }
	    if (found == true ) return rank;
	    else return -1;
	}

//To find the name at a given rank
public String Rank(int year,int rank,String gender){
    String fileName = "yob" + year + ".csv";
    int newrank=1;
    int totgirls=0;
    String namee=" ";
	    FileResource fr = new FileResource(fileName);
        for (CSVRecord rec : fr.getCSVParser(false)) {
            namee=rec.get(0);
            if(rec.get(1).equals("F")){
                totgirls++;
            }

            if(gender.equals("F")&& (rank==newrank)){
                
                System.out.println("\nThe name of girls at rank "+rank +" is "+namee);
                return namee;
            }

            else if(gender.equals("M")&&((rank+totgirls)==newrank)){
                
                System.out.println("\nThe name of boy at rank "+rank+" is "+namee);
                return namee;
            }

            else{
                newrank++;
            }
        }
        System.out.println("\nNo name for this rank");
    return namee;
}

//To know name in another year
public void whatIsNameInYear(String name,int year,int newyear,String gender){
    int RRank= getRank(year, name, gender);
    String result=Rank(newyear, RRank, gender);
    System.out.println("The name "+name+" in year "+year+" would be "+result+" if born in the year "+newyear);

}

//MEthod for YearOFHIghestRANK
public int YearOfHighestRANK(String name,String gender){
    int HighRank=Integer.MAX_VALUE;
    int highYear=0;
    int count=0;
    for(int i=1880;i<2015;i++){
        int result=getRank(i, name, gender);
        count=count+result;
        if(result<HighRank && result>0){
            HighRank=result;
            highYear=i;
        }
    }
    
    System.out.println("\n\n The highest rank for the name "+name+" is "+HighRank+" in the year "+highYear);
    return count;
}


//Method for getting the average rank for a name
public void GetAverageRank(String name,String gender){
    int sum=YearOfHighestRANK(name, gender);
    double avg=(double)(sum/134);
    System.out.println("The Average Ranking for the name "+name+" all over the 1880 to 2014 is "+avg);
}


public int getTotalBirthsRankedHigher​(int year, String name, String gender){
        int yr=year;
        String nms=name;
        String gend=gender;
	    int rank = getRank(yr,nms, gend);
        System.out.println(rank);
	    int r = 0;
	    int totalBirths = 0;
	    String fileName = "yob" + yr + ".csv";
	    FileResource fr = new FileResource(fileName);
	    for (CSVRecord rec : fr.getCSVParser(false)) {
	         if (rec.get(1).equals(gender)){
	             int numBorn = Integer.parseInt(rec.get(2));
	             r = getRank(year,rec.get(0), gender);
	             if (r < rank){
	               totalBirths += numBorn;
                 }
	         }
	    }
        System.out.println("HII");
	    if(totalBirths > 0)
        {
            return totalBirths;
            
        } 
	    else{
            return 0;
        }
	}


}
//MAIN METHOD
public class Mains{
    private static int year;

    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        System.out.println("\nENTER THE YEAR OF BIRTH of the child ");
        year = sc.nextInt();
        System.out.println("Enter your first name");
        String name=sc.next();
        System.out.println("ENTER YOUR GENDER AS \"M\" For MALE And \"F\" FOR FEMALE ");
        String gender=sc.next();
        Names nm=new Names();
        
        //Calling the function to know the RANK OF YOUR NAME
        System.out.println("\nThe RANK OF YOUR NAME IS ---  "+nm.getRank(year,name,gender));

       //calling Total Calculation method
       
        nm.TotalNum(year);

        //Calling the method to know the NAME in Another YEAR
         System.out.println("\nEnter the Rank you want to know the names of --- ");
        int rank=sc.nextInt();
        System.out.println("Enter the new year you want to know the name of the child in ----");
        int newyear=sc.nextInt();
        System.out.println("Enter the gender of the child (\"M\" For MALE And \"F\" FOR FEMALE )you want to know the name of---");
        String newgender=sc.next();
        nm.Rank(newyear, rank, newgender);
        System.out.println();
        //Calling the method to know WHAT WILL BE THE NEW NAME IN ANOTHER YEAR AT THE SAME RANK
        nm.whatIsNameInYear(name,year,newyear,gender);
        System.out.println();
        //Highest Rank for a given Name is 
        nm.YearOfHighestRANK(name, gender);

        //Method for Printing the Average Rank of the Name 
        nm.GetAverageRank(name, gender);

        //Calling the method for Printing thr Ranks higher 
        System.out.println(nm.getTotalBirthsRankedHigher​(year, name, gender));
    }
}
