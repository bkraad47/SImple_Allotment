
import java.util.Arrays;
import java.util.Scanner;
/**
 *
 * @author Badruddin Kamal
 * 
 * Based on simple bin packing algorithms, basic maths & Euclids greatest common divisor algorithm.(Greedy Soln) 
 * 
 * 
 * Euclids greatest common divisor https://en.wikipedia.org/wiki/Euclidean_algorithm
 * bin packing http://www.geeksforgeeks.org/bin-packing-problem-minimize-number-of-used-bins/
 * java scanner http://stackoverflow.com/questions/23194290/how-to-capture-enter-key-using-scanner-as-console-input
 * Some lambda expressions mapping string array to int array http://stackoverflow.com/questions/6881458/converting-a-string-array-into-an-int-array-in-java
 */
public class Simple_Allotment {

    /**
     * @param args the command line arguments
     */
    public static String pack_step(int items, int[] bin){
        
         //initialize answer
         String Answer="";
         
         // Count until the item list is empty
         int no=1;//Last space count
         while(items > 0){
             /* 
             
             Take Larger chunks out using largest bin until remainder after step by bin is not smaller than the smallest bin 
             Reason is if remainder is smaller than smallest bin allocation after step using the largest bin doesnt give an optimal solution threfore 
             we need to find a good common divisor where least number of containers are used and little space is wasted.
              
             */
             
             if(items > bin[bin.length-no] && (items-bin[bin.length-no]) >= bin[0] ){
                 
                 
                 //Taking the largest bin item chunks out
                 items = items-bin[bin.length-no];
                 Answer = Answer+" "+bin[bin.length-no];
                 
             // If item small move on tothe next large item
             }else if(items < bin[bin.length-no] && (items > bin[bin.length-(no+1)] && (items-bin[bin.length-(no+1)]) >= bin[0] ) ){
                
                 no++;//Move laspace count to previous item
                
             }else{
                 //Greedy search for best answer
                 //Containers to space wasted & hold weights(space wasted + containers used)
                 int bin_weight[] = new int[bin.length];
                 int bin_space_waste[] = new int[bin.length];
                 
                 //Initial minimal index
                 int min_index = 0;
                 
                 for(int i=0;i < bin.length;i++){
                    
                   //If the bin is larger than the items left
                   if(items < bin[i]){
                       
                       //Use a single bin
                       bin_space_waste[i] = bin[i]-items;
                       bin_weight[i] = 1+bin_space_waste[i];
                       
                   }else{
                     //common divisor leaves no remainders
                     if(items%bin[i] == 0){
                         
                         //Just take weights of divisor
                         bin_space_waste[i] = 0;
                         bin_weight[i] = items/bin[i];
                          
                     }else{
                         // If there are remainders
                         
                         //Just take weights of divisor+1 && remainder left by using extra container
                         bin_space_waste[i] = bin[i]-(items%bin[i]);
                         bin_weight[i] =((items/bin[i])+1)+bin_space_waste[i];
                         
                     }
                   }
                   
                   //Find the least wieghing item
                   if(bin_weight[i] <= bin_weight[min_index]){
                       
                       //If weight same take the one that wastes least space
                       if(bin_weight[i] == bin_weight[min_index]){
                           if(bin_space_waste[i] < bin_space_waste[min_index]){
                               min_index = i;
                           }
                           
                       //If weight lower
                       }else{
                           min_index = i;
                       }
                       
                   }
                   
                 }
                 
                //remove the least weighing bin of items & generate Answer by adding the minimum weighing bin to it
                items = items-bin[min_index];
                Answer = Answer+" "+bin[min_index]; 
                 
             }
         }
         Answer = Answer.trim();
         Answer = Answer.replace(" ", ", ");
        //Return Answer String
        return Answer;
    }
    public static void main(String[] args) {
        // Regualt expressions to check integer and integer list
        String regExIntList = "^[0-9]+(,[0-9]+)*$";
        String regExInt = "^\\d+$";
        
        //Data string defualt
        String dataString = "";
        
        System.out.println("Simple container allotment program.(To quit type exit at anytime)");
        
        // Enable scanner
        Scanner scanner = new Scanner(System.in); 
        
        try{
        // Get containers while not exit or invalid
        while(!dataString.matches(regExIntList) || dataString.compareTo("exit") != 0){
            
           // Get command input
           System.out.print("contatiners (e.g. 2,3,5):");
           dataString = scanner.nextLine();
           
           //Exit if value exit
           if(dataString.compareTo("exit") == 0){
               System.exit(0);
               
           //If value inccorect ask for correct value    
           }else if(!dataString.matches(regExIntList)){
               System.out.println("Incorrect entry detected please try again.");
               
           //break & continue to evaluation  
           }else if(dataString.matches(regExIntList)){
               break;
           }
        }
         
         // Map string to bin array
         int [] bin= Arrays.asList((dataString.trim()).split(",")).stream().mapToInt(Integer::parseInt).toArray();
        
         //Sort array to know the smallest and largest numbers (optional to optimise can add sorting algorithm such as merge sort)
         Arrays.sort(bin);
        
         //set default
         dataString ="";
         
         //While no exit command get containers for items
         while(dataString.trim().compareTo("exit") != 0){
         
         //Get items
         System.out.print("size (integer only): ");  
         dataString = scanner.next();
         
           //If exit condition then exit
           if(dataString.trim().compareTo("exit") == 0){
               System.exit(0);
              
           //If input incorrect detect it
           }else if(!dataString.matches(regExInt)){
               System.out.println("Incorrect entry detected please try again.");
               dataString = "Halt";
            
           //If input correct evaluate
           }else if(dataString.matches(regExInt)){
               System.out.println("containers required to store "+dataString.trim()+" items => " + (pack_step(Integer.parseInt(dataString),bin)));
           }
        }
         
        }catch (Exception e){
           //Throw error
           System.out.println("Exception occured. Heres why:"); 
           e.printStackTrace();
        }
        
    }
    
}
