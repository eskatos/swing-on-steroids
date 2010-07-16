/*
 * Copyright (c) 2010 Paul Merlin <paul@nosphere.org>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.codeartisans.java.sos.wizard.model;

import org.codeartisans.java.toolbox.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignupWizardModel
        implements WizardModel
{

    private static final Logger LOGGER = LoggerFactory.getLogger( SignupWizardModel.class );
    private String username;
    private char[] password;
    private PlanType plan;
    private CreditCardType creditCardType;
    private String creditCardNumber;

    public SignupWizardModel()
    {
        reset();
    }

    @Override
    public final void reset()
    {
        setUsername( StringUtils.EMPTY );
        setPassword( StringUtils.EMPTY_CHAR_ARRAY );
        setPlan( null );
        setCreditCardType( null );
        setCreditCardNumber( StringUtils.EMPTY );
        LOGGER.debug( "Wizard Model Reseted" );
    }

    @Override
    public boolean isComplete()
    {
        if ( isAccountComplete() && plan != null ) {
            switch ( plan ) {
                case FREE:
                    return true;
                case PAID:
                    return isCreditCardComplete();
            }
        }
        return false;
    }

    public boolean isAccountComplete()
    {
        return !StringUtils.isEmpty( username ) && password.length > 6 && plan != null;
    }

    public boolean isCreditCardComplete()
    {
        return creditCardType != null && !StringUtils.isEmpty( creditCardNumber );
    }

    public void setUsername( String username )
    {
        this.username = username;
        LOGGER.debug( "Wizard Model set username " + username );
    }

    public String getUsername()
    {
        return username;
    }

    @SuppressWarnings( "AssignmentToCollectionOrArrayFieldFromParameter" )
    public void setPassword( char[] password )
    {
        this.password = password;
        LOGGER.debug( "Wizard Model set password " + new String( password ) );
    }

    @SuppressWarnings( "ReturnOfCollectionOrArrayField" )
    public char[] getPassword()
    {
        return password;
    }

    public void setPlan( PlanType plan )
    {
        this.plan = plan;
        LOGGER.debug( "Wizard Model set plan " + plan );
    }

    public PlanType getPlan()
    {
        return plan;
    }

    public void setCreditCardType( CreditCardType creditCardType )
    {
        this.creditCardType = creditCardType;
        LOGGER.debug( "Wizard Model set credit card type " + creditCardType );
    }

    public CreditCardType getCreditCardType()
    {
        return creditCardType;
    }

    public void setCreditCardNumber( String creditCardNumber )
    {
        this.creditCardNumber = creditCardNumber;
        LOGGER.debug( "Wizard Model set credit card number " + creditCardNumber );
    }

    public String getCreditCardNumber()
    {
        return creditCardNumber;
    }

    @Override
    public String toString()
    {
        return "SignupWizardModel{" + "username=" + username + " password=" + password + " plan=" + plan + " creditCardType=" + creditCardType + " creditCardNumber=" + creditCardNumber + '}';
    }

}
