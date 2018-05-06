import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputChecker
{
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public int readInteger(int max, int min)
    {
        boolean hasRange = false;
        int maximum = 0;
        int minimum = 0;
        if (max != 0 && min < max)
        {
            maximum = max;
            minimum = min;
            hasRange = true;
        }
        
        int inputCheck = 0;
        boolean inputValid = false;
        while(!inputValid)
        {
            String input = null;
            try
            {
                input = br.readLine();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            try
            {
                inputCheck = Integer.parseInt(input);
                if (hasRange)
                {
                    if (inputCheck > max)
                    {
                        System.out.println("Integer out of scope, must be lower than " + maximum);
                    } else if (inputCheck < min)
                    {
                        System.out.println("Integer out of scope, must be higher than " + minimum);
                    } else
                    {
                        inputValid = true;
                    }
                } else
                {
                    inputValid = true;
                }
            } catch (NumberFormatException e)
            {
                System.out.println(input + " is not a valid integer number");
            }
        }
        
        return inputCheck;
        
        
    }
    
    public void doubleCheck(String input, int max, int min)
    {
    
    }
}
