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

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 *
 * @author John Grosh (john.a.grosh@gmail.com)
 */
public class Email
{
    private final InternetAddress recipient;
    private final String subject, content;
    private final boolean isHtml;
    
    private Email(InternetAddress recipient, String subject, String content, boolean isHtml)
    {
        this.recipient = recipient;
        this.subject = subject;
        this.content = content;
        this.isHtml = isHtml;
    }
    
    public InternetAddress getRecipient()
    {
        return recipient;
    }
    
    public String getSubject()
    {
        return subject;
    }
    
    public String getContent()
    {
        return content;
    }
    
    public boolean isHtml()
    {
        return isHtml;
    }
            
    public static boolean isValidEmailAddress(String address)
    {
        if(address == null)
            return false;
        try
        {
            var ignored = new InternetAddress(address);
            return true;
        }
        catch(AddressException ignored)
        {
            return false;
        }
    }
    
    public static class Builder
    {
        private InternetAddress recipient;
        private String subject, content;
        private boolean isHtml;
        
        public Email build()
        {
            return new Email(recipient, subject, content, isHtml);
        }
        
        public Builder setRecipient(String recipient) throws AddressException
        {
            this.recipient = new InternetAddress(recipient);
            return this;
        }
        
        public Builder setSubject(String subject)
        {
            this.subject = subject;
            return this;
        }
        
        public Builder setText(String content)
        {
            this.content = content;
            this.isHtml = false;
            return this;
        }
        
        public Builder setHtml(String html)
        {
            this.content = html;
            this.isHtml = true;
            return this;
        }
    }
}
