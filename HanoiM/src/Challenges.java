/**-------------------------------------------------------

	Store/load challenges (high scores).
	
	Programming: Sandaruwan Silva (CB003484)

-------------------------------------------------------**/

import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.util.Properties;
import java.lang.String;

public class Challenges{


	public String getChallengesString(int diskCount){
		
		try {
			
			int             minMoves = 0;
			String          sLine   = null;
			String          challengeString = "(Not Available)";
            FileReader      iFile   = new FileReader("settings.txt");
            BufferedReader  iStream = new BufferedReader(iFile);
			Properties      p = new Properties();
			
			p.load(iStream);
			challengeString = p.getProperty(Integer.toString(diskCount));
			
			if(challengeString == null) challengeString = "(Not Available)";

            iStream.close();
			
			return challengeString;

        }catch(IOException e){
			/* do nothing */
        }
		return "(Not Available)";
	}
	
	
	public int getChallengesMoves(int diskCount){
		
		try {
			
			int             minMoves = 0;
			String          sLine   = null;
			String          challengeString = "0";
            FileReader      iFile   = new FileReader("settings.txt");
            BufferedReader  iStream = new BufferedReader(iFile);
			Properties      p = new Properties();
			
			p.load(iStream);
			challengeString = p.getProperty(Integer.toString(diskCount));
			
			if(challengeString == null) return 0;

            iStream.close();
			
			return Integer.parseInt(challengeString);

        }catch(IOException e){
			/* do nothing */
        }
		return 0;
	}
	
	public void setChallenges(int diskCount, int moves, String uName){
		try {
			int lastMoves = getChallengesMoves(diskCount);
			
			if(lastMoves != 0)
				if(lastMoves <= moves)return;
			
			int             minMoves = 0;
			String          sLine   = null;

            File            oFile   = new File("settings.txt");
            OutputStream    oStream = new FileOutputStream(oFile);
			Properties      p = new Properties();
			
			
			p.setProperty(Integer.toString(diskCount), Integer.toString(moves));
			p.store(oStream, "Hanoi");
            
			oStream.close();

        }catch(IOException e){
			/* do nothing */
        }
	}
}