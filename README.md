# Integration example of SMS Gateway (JSON) using Java

This is an integration example for sending SMS using [SMSLink.ro](https://www.smslink.ro) API, called [SMS Gateway](https://www.smslink.ro/sms-gateway.html). 
SMSLink.ro allows you to send SMS to all mobile networks in Romania and also to more than 168 countries and more than 1000 mobile operators worldwide. 

## Requirements & Usage

1. Create an account on [SMSLink.ro](https://www.smslink.ro/inregistrare/)
2. Create a SMS Gateway connection at [SMSLink.ro / SMS Gateway / Configuration & Settings](https://www.smslink.ro/sms/gateway/setup.php). Each SMS Gateway connection is a pair of Connection ID and Password. 

## Supported Functions

1. *SMSLinkSMSGateway.sendMessage()* - sends an SMS
2. *SMSLinkSMSGateway.accountBalance()* - retrieve SMSLink account balance

## Usage

- See *Main.java* from the repository for usage examples
- See *SMSLinkSMSGateway.java* from the repository for class implementation

## Features

Supports HTTP and HTTPS protocols
 
## System Requirements 

Java with org.json package
    
## Documentation

The [complete documentation](https://www.smslink.ro/sms-gateway-documentatie-sms-gateway.html) of the SMSLink - SMS Gateway API can be found [here](https://www.smslink.ro/sms-gateway-documentatie-sms-gateway.html), describing all available APIs (HTTP GET / POST, SOAP / WSDL, JSON and more).

## Additional modules and integrations

SMSLink also provides modules for major eCommerce platforms (on-premise & on-demand), integrations using Microsoft Power Automate, Zapier or Integromat and many other useful features. Read more about all available features [here](https://www.smslink.ro/sms-gateway.html). 

## Support

For technical support inquiries contact us at contact@smslink.ro or by using any other available method described [here](https://www.smslink.ro/contact.php).
