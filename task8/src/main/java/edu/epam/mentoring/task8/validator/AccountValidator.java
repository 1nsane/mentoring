package edu.epam.mentoring.task8.validator;

import edu.epam.mentoring.task8.model.Account;
import edu.epam.mentoring.task8.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by eugen on 19.09.2016.
 */
@Component
public class AccountValidator implements Validator {
    @Autowired
    private AccountService accountService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Account.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Account account = (Account) o;

        long id = account.getId();
        String name = account.getName();
        int status = account.getStatus();
        long balance = account.getBalance();

        if (name == null || name.trim().length() < 2) {
            errors.rejectValue("name", "empty");
        } else if (name.trim().length() > 10) {
            errors.rejectValue("name", "too_long");
        }

        if (status < 1 || status > 4) {
            errors.rejectValue("status", "invalid");
        }

        if (balance < 0) {
            errors.rejectValue("balance", "invalid");
        }

        if (!errors.hasFieldErrors("name")) {
            if (accountService.checkExistsName(account.getName())) {
                if (id == 0) { //creating new account
                    errors.rejectValue("name", "name_exists");
                } else { //updating existing account
                    Account accountToCheckName = accountService.getById(id);

                    if (accountToCheckName == null)
                        throw new IllegalArgumentException("Wrong id parameter passed");

                    if (!accountToCheckName.getName().equals(name)) {
                        errors.rejectValue("name", "name_exists");
                    }
                }
            }
        }
    }
}
