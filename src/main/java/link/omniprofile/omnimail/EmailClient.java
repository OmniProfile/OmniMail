/*
 * Copyright 2019 John Grosh & Kaidan Gustave
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package link.omniprofile.omnimail;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author John Grosh (john.a.grosh@gmail.com)
 */
public class EmailClient
{
    private final InternetAddress sender;
    private final Session session;
    
    /**
     * Creates an email client, loading the default config
     * 
     * @throws AddressException if the value at email.sender is not a valid email address
     */
    public EmailClient() throws AddressException
    {
        this(ConfigFactory.load());
    }
    
    /**
     * Creates an email client, using the provided config
     * 
     * @param config hocon config
     * @throws AddressException if the value at email.sender is not a valid email address
     */
    public EmailClient(Config config) throws AddressException
    {
        sender = new InternetAddress(config.getString("email.sender"));
        var props = System.getProperties();
        props.setProperty("mail.smtp.host", config.getString("email.host"));
        props.setProperty("mail.smtp.port", Integer.toString(config.getInt("email.port")));
        props.setProperty("mail.smtp.starttls.enable", Boolean.toString(config.getBoolean("email.starttls")));
        session = Session.getDefaultInstance(props);
    }
    
    public void sendEmail(Email email) throws MessagingException
    {
        var mime = new MimeMessage(session);
        mime.setFrom(sender);
        mime.addRecipient(Message.RecipientType.TO, email.getRecipient());
        mime.setSubject(email.getSubject());
        if(email.isHtml())
            mime.setContent(email.getContent(), "text/html");
        else
            mime.setText(email.getContent());
        Transport.send(mime); 
    }
}
