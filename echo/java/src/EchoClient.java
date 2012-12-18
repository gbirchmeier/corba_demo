import EchoDemo.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import java.io.*;
import java.util.*;

public class EchoClient
{
	public static void main(String args[])
	{
		if(args.length != 1)
		{
			System.out.println("Usage: java EchoClient <iorfile>");
			return;
		}

		File iorFile = new File(args[0]);

		if ( !(iorFile.exists() && iorFile.canRead()) )
		{
			System.out.println("Can't read "+iorFile.toString());
			return;
		}


		try
		{
			ORB orb = ORB.init(args, null);

			String ior = new Scanner(iorFile).useDelimiter("\\A").next();
			org.omg.CORBA.Object objRef = orb.string_to_object(ior);

			Echo echoImpl = EchoHelper.narrow(objRef);

			userLoop(echoImpl);

			orb.destroy();
			System.out.println("terminated.");
		}
		catch (org.omg.CORBA.COMM_FAILURE e)
		{
			System.out.println("Communication failure.  You probably forgot to start the server.");
			if(e.getMessage() != null && !e.getMessage().trim().equals(""))
				System.out.println("Message: "+e.getMessage());
			else
				System.out.println("(No exception error message.)");
		}
		catch (Exception e)
		{
			System.out.println("Unknown exception.");
			e.printStackTrace(System.out);
		}
	}


	private static void userLoop(Echo echo)
	{
		System.out.println("Echo client.");
		System.out.println("Enter a string to echo.");
		System.out.println("Enter 'quit' to quit client.");
		System.out.println("Enter 'shutdown' to terminate server then quit client.");
		
		Scanner scanIn = new Scanner(System.in);

		while(true)
		{
			System.out.print("> ");
			String s = scanIn.nextLine().trim();

			if(s.equals("shutdown"))
			{
				echo.shutdown();
				break;
			}
			else if(s.equals("quit"))
			{
				break;
			}
			else
			{
				String output = echo.echo_string(s);
				System.out.println("server returned: '"+output+"'");
			}
		}

		scanIn.close();
	}
}

