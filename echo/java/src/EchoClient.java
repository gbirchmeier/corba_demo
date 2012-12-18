import EchoDemo.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import java.io.*;
import java.util.*;

public class EchoClient
{
	static Echo echoImpl;

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

			echoImpl = EchoHelper.narrow(objRef);

			System.out.println("Obtained a handle on server object: " + echoImpl);
			System.out.println(echoImpl.echo_string("hello from java client"));
			echoImpl.shutdown();
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
}

