import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBFunctions
{
    public static Connection con;

    public DBFunctions() {
        try {
            con = DBConfig.getConnection();
            if(con.isClosed()) {
                System.out.println("Connection was closed; creating new connection to MySQL.");
                con = DBConfig.getConnection();
            }
        }
        catch (Exception ex) { ex.printStackTrace(); }
    }
    
    private InputChecker ic = new InputChecker();
    
    public void saveToCSV(ResultSet rs)
    {
        System.out.println("Would you like to save the data into a csv? ");
        System.out.println("1. Yes 2. No");
        int saveSelection = ic.readInteger(2,1);
        if (saveSelection == 1)
        {
            try
            {
                CSVWriter writer = new CSVWriter(new FileWriter("heroesDatabase.csv"), ',');
                try
                {
                    writer.writeAll(rs, true);
                    writer.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    
}
