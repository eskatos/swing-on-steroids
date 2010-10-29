/*
 * Copyright (c) 2009, Paul Merlin. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
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
