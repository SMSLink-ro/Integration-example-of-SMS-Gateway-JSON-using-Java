/**
  
  Import SMSLinkSMSGateway class from SMSLink package located in SMSLinkSMSGateway.java file
    Requires org.json package

*/
import SMSLink.SMSLinkSMSGateway;

class Main {
  public static void main(String[] args) {
    System.out.println("Sending SMS from My Java Application ...");

    /**



      Get your SMSLink / SMS Gateway Connection ID and Password from 
      https://www.smslink.ro/get-api-key/



    */

    /**
      Initialize SMSLinkSMSGateway class as SMSLinkInstance
    */
    SMSLinkSMSGateway SMSLinkInstance = new SMSLinkSMSGateway("MyConnectionID", "MyConnectionPassword");
    
    /**
      Enable Logging (logging is disabled by default)
        You may disable logging also by using SMSLinkInstance.disableLogging();
    */
    SMSLinkInstance.enableLogging();

    /**
      Send SMS #1
    */
    String messageID = SMSLinkInstance.sendMessage("07xyzzzzzz", "This is my first hello world message!");

    if (messageID != null)
      System.out.println("Message #1 sent with Message ID: " + messageID + "!");

    /**
      Send SMS #2
    */
    messageID = SMSLinkInstance.sendMessage("07xyzzzzzz", "This is my second hello world message!");

    if (messageID != null)
      System.out.println("Message #2 sent with Message ID: " + messageID + "!");

    /**
      Request Account Balance
    */
    String accountBalance = SMSLinkInstance.accountBalance();
    System.out.println(accountBalance);

    System.out.println("Done sending SMS from My Java Application.");    
  }
}